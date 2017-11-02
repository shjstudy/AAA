using System;
using System.Collections.Generic;
using System.Text;
using MCP;
using System.Data;
using Util;
using System.Timers;

namespace App.Dispatching.Process
{
    public class CraneProcess : AbstractProcess
    {
        private class rCrnStatus
        {
            public int io_flag { get; set; }
            public string ServiceName { get; set; }
            public string DeviceNo { get; set; }
            public rCrnStatus()
            {
                io_flag = 0;
                ServiceName = "";
                DeviceNo = "";
            }
        }

        // 记录堆垛机当前状态及任务相关信息
        BLL.BLLBase bll = new BLL.BLLBase();
        private Dictionary<int, rCrnStatus> dCrnStatus = new Dictionary<int, rCrnStatus>();
        private int InTaskCount = 2;
        private Timer tmWorkTimer = new Timer();
        private bool blRun = false;
        private DataTable dtDeviceAlarm;
        Report report = new Report();
        private string ConveyServer = "Convey";

        public override void Initialize(Context context)
        {
            try
            {
                DataTable dt = bll.FillDataTable("WCS.SelectDevice", new DataParameter[] { new DataParameter("{0}", "Flag =1") });
                for (int i = 1; i <= dt.Rows.Count; i++)
                {
                    if (!dCrnStatus.ContainsKey(i))
                    {
                        rCrnStatus crnsta = new rCrnStatus();
                        dCrnStatus.Add(i, crnsta); 
                        dCrnStatus[i].io_flag = 0;
                        dCrnStatus[i].ServiceName = dt.Rows[i - 1]["ServiceName"].ToString();
                        dCrnStatus[i].DeviceNo = dt.Rows[i - 1]["DeviceNo"].ToString();
                    }
                }

                dtDeviceAlarm = bll.FillDataTable("WCS.SelectDeviceAlarm", new DataParameter[] { new DataParameter("{0}", "Flag=1") });

                tmWorkTimer.Interval = 2000;
                tmWorkTimer.Elapsed += new ElapsedEventHandler(tmWorker);
                base.Initialize(context);
            }
            catch (Exception ex)
            {
                Logger.Error("CraneProcess堆垛机初始化出错，原因：" + ex.Message);
            }
        }
        protected override void StateChanged(StateItem stateItem, IProcessDispatcher dispatcher)
        {
            try
            {
                switch (stateItem.ItemName)
                {
                    case "TaskFinish":
                        object obj = ObjectUtil.GetObject(stateItem.State);
                        if (obj == null)
                            return;
                        string TaskFinish = obj.ToString();
                        if (TaskFinish.Equals("True") || TaskFinish.Equals("1"))
                        {
                            string TaskNo = Util.ConvertStringChar.BytesToString(ObjectUtil.GetObjects(Context.ProcessDispatcher.WriteToService(stateItem.Name, "CraneTaskNo")));
                            if (TaskNo == "")
                                return;
                            DataParameter[] para = new DataParameter[] { new DataParameter("{0}", string.Format("WCS_Task.TaskNo='{0}'", TaskNo)) };
                            DataTable dt = bll.FillDataTable("WCS.SelectTask", para);

                            
                            if (dt.Rows.Count > 0)
                            {
                                string TaskType = dt.Rows[0]["TaskType"].ToString();
                                string State = dt.Rows[0]["State"].ToString();
                                string PalletCode=dt.Rows[0]["PalletCode"].ToString();
                                
                                bll.ExecNonQuery("WCS.Sp_TaskProcess", new DataParameter[] { new DataParameter("@TaskNo", TaskNo) });
                                Logger.Info("堆垛机任务完成,任务号:" + TaskNo + " 料箱号:" + PalletCode);
                                if (TaskType == "11" || TaskType == "14" && State == "3") //入库,盘点上架
                                {
                                    report.Send2MES(TaskNo);
                                }
                                else if (TaskType == "12" || TaskType=="15" || TaskType=="14" && State=="4") //出库,空箱出库，盘点下架
                                {
                                    //下达输送线
                                    string ConveyID = dt.Rows[0]["ConveyFromStation"].ToString();
                                    string Destination = dt.Rows[0]["ConveyToStation"].ToString();
                                    int[] iTaskNo = new int[3];
                                    iTaskNo[0] = int.Parse(TaskNo.Substring(0, 2));
                                    iTaskNo[1] = int.Parse(TaskNo.Substring(2, 4));
                                    iTaskNo[2] = int.Parse(TaskNo.Substring(6, 4));
                                    WriteToService(ConveyServer, ConveyID + "_WTaskNo", iTaskNo);
                                    WriteToService(ConveyServer, ConveyID + "_WPalletCode", PalletCode);
                                    WriteToService(ConveyServer, ConveyID + "_Destination", Destination); //目的地
                                    if (WriteToService(ConveyServer, ConveyID + "_WriteFinished", 1))
                                    {
                                        bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", "State=6,Convey_StartDate=getdate()"), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) });
                                        Logger.Info("任务号:" + TaskNo + " 料箱号:" + PalletCode + " 已下输送线:" + ConveyID + " 目的地址:" + Destination);
                                    }
                                    if (TaskType == "12")
                                    {
                                        //插入AGV出库任务
                                        BLL.BLLBase bllAGV = new BLL.BLLBase("AGVDB");
                                        bllAGV.ExecNonQuery("WCS.InsertAGVTask", new DataParameter[] { new DataParameter("@from_station", ""), //填入实际值,在Main.cs中，重下AGV任务Send2PLCAGV 也同时修改
                                                                                                       new DataParameter("@to_station", ""),
                                                                                                       new DataParameter("@TaskNo", TaskNo)});
                                    }

                                }
                                
                               
                                sbyte[] taskNo = new sbyte[20];
                                Util.ConvertStringChar.stringToBytes("", 20).CopyTo(taskNo, 0);
                                WriteToService(stateItem.Name, "TaskNo", taskNo);
                            }
                        }
                        break;
                    case "PLCCheck":
                        obj = ObjectUtil.GetObject(stateItem.State);
                        if (obj == null)
                            return;
                        string ack = obj.ToString();
                        if (ack.Equals("True") || ack.Equals("1"))
                        {
                            WriteToService(stateItem.Name, "WriteFinished", 0);
                            Logger.Debug(stateItem.Name + " Receive ACK 1");
                        }
                        break;
                    case "CraneAlarmCode":
                        if (ObjectUtil.GetObject(stateItem.State) == null)
                            return;
                        if (ObjectUtil.GetObject(stateItem.State).ToString() == "0")
                            return;
                        string strWarningCode = ObjectUtil.GetObject(stateItem.State).ToString();
                        DataRow[] drs = null;
                        string strError = "";
                        if (drs.Length > 0)
                        {
                            strError = drs[0]["AlarmDesc"].ToString();
                        }
                        else
                        {
                            strError = "未知错误，错误号:" + strWarningCode;
                        }
                        report.SendCraneError(int.Parse(strWarningCode), strError);
                        Logger.Error("堆垛机发生错误： " + strError);
                        break;
                    case "Run":
                        blRun = (int)stateItem.State == 1;
                        if (blRun)
                        {
                            tmWorkTimer.Start();
                            Logger.Info("堆垛机联机");
                        }
                        else
                        {
                            tmWorkTimer.Stop();
                            Logger.Info("堆垛机脱机");
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                Logger.Error("CraneProcess StateChanged方法出错，原因：" + ex.Message);
            }
        }
           

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void tmWorker(object sender, ElapsedEventArgs e)
        {
            lock (this)
            {
                try
                {
                    if (!blRun)
                    {
                        tmWorkTimer.Stop();
                        return;
                    }
                    tmWorkTimer.Stop();

                    DataTable dt = bll.FillDataTable("WCS.SelectDevice", new DataParameter[] { new DataParameter("{0}", "Flag =1") });
                    for (int i = 1; i <= dt.Rows.Count; i++)
                    {
                        
                        if (int.Parse(dt.Rows[i - 1]["State"].ToString()) != 1)
                            continue;
                        if (dCrnStatus[i].io_flag == 0)
                        {
                            CraneOut(i);
                        }
                        else
                        {
                            CraneIn(i);
                        }
                    }

                }
                finally
                {
                    tmWorkTimer.Start();
                }
            }
        }
        /// <summary>
        /// 检查堆垛机入库状态
        /// </summary>
        /// <param name="piCrnNo"></param>
        /// <returns></returns>
        private bool Check_Device_Status_IsOk(string ServiceName)
        {
            try
            {
                string plcTaskNo = Util.ConvertStringChar.BytesToString(ObjectUtil.GetObjects(Context.ProcessDispatcher.WriteToService(ServiceName, "CraneTaskNo")));
                string craneMode = ObjectUtil.GetObject(Context.ProcessDispatcher.WriteToService(ServiceName, "CraneMode")).ToString();
                string CraneState = ObjectUtil.GetObject(Context.ProcessDispatcher.WriteToService(ServiceName, "CraneState")).ToString();
                string CraneAlarmCode = ObjectUtil.GetObject(Context.ProcessDispatcher.WriteToService(ServiceName, "CraneAlarmCode")).ToString();
                string CraneLoad = ObjectUtil.GetObject(Context.ProcessDispatcher.WriteToService(ServiceName, "CraneLoad")).ToString();

                if (plcTaskNo == "" && craneMode == "1" && CraneAlarmCode == "0" && CraneState == "1" && CraneLoad == "0")
                    return true;
                else
                    return false;
            }
            catch (Exception ex)
            {
                Logger.Error( "堆垛机错误:" + ex.Message);
                return false;
            }            
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="craneNo"></param>
        private void CraneOut(int CraneNo)
        {
            string ServiceName = dCrnStatus[CraneNo].ServiceName;
            if (!Check_Device_Status_IsOk(ServiceName))
            {
                return;
            }
            dCrnStatus[CraneNo].io_flag = 1;
            string serviceName = dCrnStatus[CraneNo].ServiceName;
            DataParameter[] parameter = new DataParameter[] { new DataParameter("{0}", "WCS_Task.TaskType in ('12','14','15') and WCS_Task.State='0' ") };
            DataTable dt = bll.FillDataTable("WCS.SelectTask", parameter);
            if (dt.Rows.Count > 0)
            {
                DataRow dr = dt.Rows[0];
                string TaskType = dr["TaskType"].ToString();
                //判断出库站台是否有货


                //若有正在进行的盘点任务，则不出库
                if (TaskType == "14")
                {
                    int count = bll.GetRowCount("WCS_Task", "TaskType='14' and State not in (0,7,9)");
                    if (count > 0)
                        return;
                }
                Send2PLC(dr, ServiceName);
            }
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="craneNo"></param>
        private void CraneIn(int CraneNo)
        {
            // 判断堆垛机的状态 自动  空闲
            string ServiceName = dCrnStatus[CraneNo].ServiceName;
            if (!Check_Device_Status_IsOk(ServiceName))
            {
                return;
            }
            dCrnStatus[CraneNo].io_flag = 0;
            //获取任务,排序优先等级、任务时间
            DataParameter[] parameter = new DataParameter[] { new DataParameter("{0}","(WCS_TASK.TaskType='11' or WCS_TASK.TaskType='14') and WCS_TASK.State='2'") };
            DataTable dt = bll.FillDataTable("WCS.SelectTask", parameter);
            //入庫
            if (dt.Rows.Count > 0)
            {
                DataRow dr = dt.Rows[0];
                Send2PLC(dr, ServiceName);
            }
        }
        private void Send2PLC(DataRow dr, string serviceName)
        {
            string TaskNo = dr["TaskNo"].ToString();
            string TaskType = dr["TaskType"].ToString();
            string PalletCode=dr["PalletCode"].ToString();
            string CellCode=dr["CellCode"].ToString();
            string Msg="下架";
            string NextState = "4";
            if (TaskType == "11")
            {
                NextState = "3";
                Msg="上架";
            }

           

            string fromStation = dr["FromStation"].ToString();
            string toStation = dr["ToStation"].ToString();
         
            int[] cellAddr = new int[6];
            cellAddr[0] = int.Parse(fromStation.Substring(4, 3));
            cellAddr[1] = int.Parse(fromStation.Substring(7, 3));
            cellAddr[2] = int.Parse(fromStation.Substring(0, 3));
            cellAddr[3] = int.Parse(toStation.Substring(4, 3));
            cellAddr[4] = int.Parse(toStation.Substring(7, 3));
            cellAddr[5] = int.Parse(toStation.Substring(0, 3));

            sbyte[] sTaskNo = new sbyte[20];
            Util.ConvertStringChar.stringToBytes(TaskNo, 20).CopyTo(sTaskNo, 0);
            WriteToService(serviceName, "TaskNo", sTaskNo);
            WriteToService(serviceName, "TaskAddress", cellAddr);

            WriteToService(serviceName, "TaskType", 1);
            if (WriteToService(serviceName, "WriteFinished", 1))
            {
                bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", string.Format("State='{0}',Crane_StartDate=getdate()", NextState)), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) });
            }
            Logger.Info(Msg + "任务:" + TaskNo + " 料箱：" + PalletCode + " 货位：" + CellCode + "已下发给堆垛机");
        }    
    }
}