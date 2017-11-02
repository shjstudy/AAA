using System;
using System.Collections.Generic;
using System.Text;
using MCP;
using System.Data;
using Util;
using System.Timers;

namespace App.Dispatching.Process
{
    public class OutStockFinishProcess : AbstractProcess
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        BLL.BLLBase bllAGV = new BLL.BLLBase("AGVDB");
        string ConveyServer = "Convey";
          private Timer tmWorkTimer = new Timer();
          
        public override void Initialize(Context context)
        {
            base.Initialize(context);
            tmWorkTimer.Interval = 2000;
            tmWorkTimer.Elapsed += new ElapsedEventHandler(tmWorker);

        }

        protected override void StateChanged(StateItem stateItem, IProcessDispatcher dispatcher)
        {
            object[] obj = ObjectUtil.GetObjects(stateItem.State);
            if (obj == null)
                return;
            string TaskNo = obj[0].ToString() + obj[1].ToString().PadLeft(4, '0') + obj[2].ToString().PadLeft(4, '0');
            if (TaskNo.Length > 0)
            {
                try
                {
                    DataTable dt = bll.FillDataTable("WCS.SelectTask", new DataParameter[] { new DataParameter("{0}", string.Format("WCS.TaskNo='{0}'", TaskNo)) });
                    if (dt.Rows.Count > 0)
                    {
                        string TaskType = dt.Rows[0]["TaskType"].ToString();
                        string PalletBarcode = dt.Rows[0]["PalletCode"].ToString();
                        string StationNo = stateItem.ItemName.Substring(0, 3);
                        string NextState = "10";
                        if (TaskType == "15")
                            NextState = "7";
                        if (TaskType == "14")
                            NextState = "8";
                        DataParameter[] param = new DataParameter[] { new DataParameter("{0}", string.Format("State='{0}',Covey_FinishDate=getdate()", NextState)), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) };
                        bll.ExecNonQueryTran("WCS.UpdateTaskState", param);
                        Logger.Info("任务号:" + TaskNo + " 料箱号:" + PalletBarcode + " 到达出库口：" + StationNo);
                        if (TaskType == "14")
                        {
                            string strValue = "";
                            string[] str = new string[1];
                            str[0] = "2";

                            while ((strValue = FormDialog.ShowDialog(str, dt)) != "")
                            {
                                //写入盘点入库
                                //DataParameter[] parameter = new DataParameter[] { new DataParameter("{0}", string.Format("WCS_Task.TaskNo='{0}'", TaskNo)) };
                                //DataTable dtTask = bll.FillDataTable("WCS.SelectTask", parameter);
                                //if (dt.Rows.Count > 0)
                                //{
                                //    string ConveyServer = "";
                                //    string ConveyID = dtTask.Rows[0]["ConveyFromStation"].ToString();
                                //    string PalletCode = dtTask.Rows[0]["PalletCode"].ToString();
                                //    string Destination = dtTask.Rows[0]["ConveyToStation"].ToString();
                                //    sbyte[] sTaskNo = new sbyte[20];
                                //    Util.ConvertStringChar.stringToBytes(TaskNo, 20).CopyTo(sTaskNo, 0);

                                //    WriteToService(ConveyServer, ConveyID + "_WTaskNo", sTaskNo);
                                //    WriteToService(ConveyServer, ConveyID + "_WPalletCode", PalletCode);
                                //    WriteToService(ConveyServer, ConveyID + "_Destination", Destination); //目的地
                                //    if (WriteToService(ConveyServer, ConveyID + "_WriteFinished", 1))
                                //    {
                                //        bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", "State=1,Convey_StartDate=getdate()"), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) });
                                //        Logger.Info("任务号:" + TaskNo + " 料箱号:" + PalletCode + " 已下输送线:" + ConveyID + " 目的地址:" + Destination);
                                //    }
                                //}
                                break;
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    Logger.Error("OutStockFinishProcess出错,原因:" + ex.Message);
                }
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
                    DataTable dt = bllAGV.FillDataTable("WCS.SelectAGVTask", new DataParameter[] { new DataParameter("{0}", "Status=2") });
                    for (int i = 0; i < dt.Rows.Count; i++)
                    {
                        string TaskNo = dt.Rows[i]["Call_from"].ToString();
                        int count = bll.GetRowCount("WCS_Task", "TaskNo='{0}' and State not in (7,9)");
                        if (count == 1)
                        {
                            bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", "State=7,AGV_FinishDate=getdate()"), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) });
                            App.Dispatching.Process.Report rpt = new Report();
                            rpt.Send2MES(TaskNo);
                            Logger.Info("任务号:" + TaskNo + " 出库完成!");
                        }
                    }

                }
                finally
                {
                    tmWorkTimer.Start();
                }
            }
        }
        
    }
}
