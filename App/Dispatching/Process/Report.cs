using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Net;
using System.IO;
using Util;
using MCP;
using Newtonsoft.Json;

namespace App.Dispatching.Process
{
    public class Report
    {
        BLL.BLLBase bll = new BLL.BLLBase();

        ResultData rtnMessage;
        //库存更新上报
        public void Send2MES(string TaskNo)
        {
            DataParameter[] para = new DataParameter[] { new DataParameter("{0}", string.Format("WCS_Task.TaskNo='{0}'", TaskNo)) };
            DataTable dt = bll.FillDataTable("WCS.SelectTask", para);
            if (dt.Rows.Count > 0)
            {
                string TaskType = dt.Rows[0]["TaskType"].ToString();
                string ProductCode = dt.Rows[0]["ProductCode"].ToString();
                int Qty = int.Parse(dt.Rows[0]["Qty"].ToString());
                string Category = dt.Rows[0]["CategoryCode"].ToString();
                string Spec = dt.Rows[0]["Spec"].ToString();
                int SendQty = 0;
                int Id = int.Parse(dt.Rows[0]["CellName"].ToString());
                int PalletID = int.Parse(dt.Rows[0]["PalletID"].ToString());

                string operation = "";
                if(TaskType=="11" && ProductCode!="")
                {
                    operation = "add";
                    SendQty = Qty;
                }
                else if(TaskType=="12")
                {
                    operation = "minus";
                    SendQty = Qty;

                }
                else if (TaskType == "14")
                {
                    string NewProductCode = dt.Rows[0]["NewProductCode"].ToString();
                    int NewQty = int.Parse(dt.Rows[0]["NewQty"].ToString());
                    if (ProductCode == "") //原有无货
                    {
                        if (NewProductCode != "")
                        {
                            operation = "add";
                            Category = dt.Rows[0]["NewCategoryCode"].ToString();
                            Spec = dt.Rows[0]["NewSpec"].ToString();
                            SendQty = int.Parse(dt.Rows[0]["NewQty"].ToString());
                        }
                    }
                    else
                    {
                        if (NewProductCode == "") //有货变无货
                        {
                            operation = "minus";
                            SendQty = Qty;
                        }
                        else  //有货货物不一样
                        {
                            if (ProductCode != NewProductCode)
                            {
                                //先将原有库存减少
                                ViewStorageBin StorageBinD = new ViewStorageBin() { Operation = "minus", ID = Id, PlateID = PalletID, Color = Spec, Type = Category, Number = Qty };
                                string JsonD = JsonConvert.SerializeObject(StorageBinD);
                                string messageD = Program.send("UpdateBinInfo", JsonD);
                                rtnMessage = JsonHelper.JSONToObject<ResultData>(messageD);
                                Logger.Info("任务" + TaskNo + "库存开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());

                                //增加新库存
                                operation = "add";
                                Category = dt.Rows[0]["NewCategoryCode"].ToString();
                                Spec = dt.Rows[0]["NewSpec"].ToString();
                                SendQty = int.Parse(dt.Rows[0]["NewQty"].ToString());
                            }
                            else if (Qty != NewQty)
                            {
                                if (Qty > NewQty)
                                    operation = "minus";
                                else
                                    operation = "add";
                                SendQty = Math.Abs(Qty - NewQty);
                            }
                        }
                    }
                }
                if (operation != "")
                {
                    ViewStorageBin StorageBin = new ViewStorageBin() { Operation = operation, ID = Id, PlateID = PalletID, Color = Spec, Type = Category, Number = SendQty };
                    string Json = JsonConvert.SerializeObject(StorageBin);
                    string message = Program.send("UpdateBinInfo", Json);
                    rtnMessage = JsonHelper.JSONToObject<ResultData>(message);
                    DataParameter[] param = new DataParameter[] { new DataParameter("@TaskNo", TaskNo), new DataParameter("@ReturnCode", rtnMessage.Code.ToString()) };
                    bll.ExecNonQueryTran("WCS.UpdateTaskReturnCode", param);
                    Logger.Info("任务" + TaskNo + "库存开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());
                }
                else
                {
                    DataParameter[] param = new DataParameter[] { new DataParameter("@TaskNo", TaskNo), new DataParameter("@ReturnCode", "0") };
                    bll.ExecNonQueryTran("WCS.UpdateTaskReturnCode", param);
                }
            }
        }
        public void SendCraneStatus(Context context, string ServiceName, string AlarmDesc)
        {
            ViewStackerState StackerState = new ViewStackerState
            {
                PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"),
                StackerPositon = "AAAA",
                StackerWalkingCurrent = 1.1,
                StackerWalkingSpeed = 2.2,
                StackerLiftingCurrent = 3.3,
                StackerLiftingSpeed = 4.4,
                StackerStackingCurrent = 5.5,
                StackerStackingSpeed = 6.6
            };
            string Json = JsonConvert.SerializeObject(StackerState);
            string message = Program.send("UpdateStackerState", Json);
            rtnMessage = JsonHelper.JSONToObject<ResultData>(message);
            Logger.Debug("堆垛机状态开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());
        }
        public void SendCraneError(int alarmType, string alarmDesc)
        {
            ViewStackerAlarmInfo ViewStackerAlarm = new ViewStackerAlarmInfo { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AlarmType = alarmType, AlarmInfo = alarmDesc };

            string Json = JsonConvert.SerializeObject(ViewStackerAlarm);
            string message = Program.send("UpdateStackerAlarmInfo", Json);
            rtnMessage = JsonHelper.JSONToObject<ResultData>(message);
            Logger.Debug("堆垛机错误开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());
        }
        public void SendAGVStatus()
        {
            ViewAGVState AGVState = new ViewAGVState { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AGVPosition = 1, AGVPower = 2 };

            string Json = JsonConvert.SerializeObject(AGVState);
            string message = Program.send("UpdateAGVState", Json);
            rtnMessage = JsonHelper.JSONToObject<ResultData>(message);
            Logger.Debug("AGV状态开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());


        }
        public void SendAGVError(int alarmType, string alarmDesc)
        {
            ViewStackerAlarmInfo ViewAGVAlarm = new ViewStackerAlarmInfo { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AlarmType = alarmType, AlarmInfo = alarmDesc };

            string Json = JsonConvert.SerializeObject(ViewAGVAlarm);
            string message = Program.send("UpdateAGVAlarmInfo", Json);
            rtnMessage = JsonHelper.JSONToObject<ResultData>(message);
            Logger.Debug("AGV错误开始上报，收到反馈:" + rtnMessage.Result + ":" + rtnMessage.Code.ToString());

        }

        public void Send2MesRequest()  
        {
            PartSupplementResult PartSupplement = new PartSupplementResult { RequestID = 10, ResultCode = PartSupplementResultCode.e0000, RequestResultTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") };
            
        }
         

    }
}
