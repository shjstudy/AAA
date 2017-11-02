using System;
using System.Collections.Generic;
using System.Text;
using MCP;
using System.Data;
using Util;

namespace App.Dispatching.Process
{
    public class InStockToStationProcess : AbstractProcess
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        string ConveyServer = "Convey";
        protected override void StateChanged(StateItem stateItem, IProcessDispatcher dispatcher)
        {
            switch (stateItem.ItemName)
            {
                case "101_TaskNo":
                    object[] obj = ObjectUtil.GetObjects(stateItem.State);
                    if (obj == null)
                        return;
                    string TaskNo = obj[0].ToString() + obj[1].ToString().PadLeft(4, '0') + obj[2].ToString().PadLeft(4, '0');
                    if (TaskNo.Length > 0)
                    {
                        string StationNo = stateItem.ItemName.Substring(0, 3);
                        string PalletBarcode = ConvertStringChar.BytesToString(ObjectUtil.GetObjects(WriteToService(stateItem.Name, StationNo + "_RPalletCode")));

                        try
                        {
                            DataParameter[] param = new DataParameter[] { new DataParameter("{0}", "State='2',Covey_FinishDate=getdate()"), new DataParameter("{1}", string.Format("TaskNo='{0}'", TaskNo)) };
                            bll.ExecNonQueryTran("WCS.UpdateTaskState", param);
                            Logger.Info("任务号:" + TaskNo + " 聊箱号:" + PalletBarcode + " 到达入库站台：" + StationNo);
                        }
                        catch (Exception ex)
                        {
                            Logger.Error("InStockToStationProcess出错，原因：" + ex.Message);
                        }
                    }
                    break;

                case "TaskNo":
                    string ReadTaskNo = stateItem.State.ToString();
                    //下达输送线入库任务
                   
                    try
                    {
                        DataParameter[] parameter = new DataParameter[] { new DataParameter("{0}", string.Format("WCS_Task.TaskType='11'and WCS_Task.State='0' and TaskNo='{0}'", ReadTaskNo)) };
                        DataTable dt = bll.FillDataTable("WCS.SelectTask", parameter);
                        if (dt.Rows.Count > 0)
                        {
                            string ConveyID = dt.Rows[0]["ConveyFromStation"].ToString();
                            string PalletCode = dt.Rows[0]["PalletCode"].ToString();
                            string Destination = dt.Rows[0]["ConveyToStation"].ToString();
                            int[] iTaskNo = new int[3];
                            iTaskNo[0] = int.Parse(ReadTaskNo.Substring(0, 2));
                            iTaskNo[1] = int.Parse(ReadTaskNo.Substring(2, 4));
                            iTaskNo[2] = int.Parse(ReadTaskNo.Substring(6, 4));
                            WriteToService(ConveyServer, ConveyID + "_WTaskNo", iTaskNo);
                            WriteToService(ConveyServer, ConveyID + "_WPalletCode", PalletCode);
                            WriteToService(ConveyServer, ConveyID + "_Destination", Destination); //目的地
                            if (WriteToService(ConveyServer, ConveyID + "_WriteFinished", 1))
                            {
                                bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", "State=1,Convey_StartDate=getdate()"), new DataParameter("{1}", string.Format("TaskNo='{0}'", ReadTaskNo)) });
                                Logger.Info("任务号:" + ReadTaskNo + " 料箱号:" + PalletCode + " 已下输送线:" + ConveyID + " 目的地址:" + Destination);
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        Logger.Error("InStockToStationProcess出错,下达输送入库任务出错,原因：" + ex.Message);
                    }


                    break;
            }

           
        }
    }
}
