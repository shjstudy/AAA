using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;
using DataGridViewAutoFilter;
using MCP;
using OPC;
using MCP.Service.Siemens.Config;
namespace App.View
{
    public partial class frmMonitor : BaseForm
    {
        float colDis = 12.68f;
        float rowDis = 56f;

        Point P2 ;
        
        BLL.BLLBase bll = new BLL.BLLBase();
        Dictionary<int, string> dicCraneFork = new Dictionary<int, string>();
        Dictionary<int, string> dicCraneState = new Dictionary<int, string>();
        Dictionary<int, string> dicCraneMode = new Dictionary<int, string>();
        Dictionary<int, int> dicLocationX = new Dictionary<int, int>();

        DataTable dtDeviceAlarm;

        public frmMonitor()
        {
            InitializeComponent();
        }

        private void frmMonitor_Load(object sender, EventArgs e)
        {
            DataTable dt = Program.dtUserPermission;
            //监控任务--取消堆垛机任务
            string filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.btnCancel0101.Visible = false;
            }
            else
            {
                this.btnCancel0101.Visible = true;
            }
            P2 = pictureBox2.Location;
            AddDicKeyValue();

            Cranes.OnCrane += new CraneEventHandler(Monitor_OnCrane);
            Conveyors.OnConveyor+=new ConveyorEventHandler(Conveyors_OnConveyor);

            try
            {
                ServerInfo[] Servers = new MonitorConfig("Monitor.xml").Servers;
                for (int i = 0; i < Servers.Length; i++)
                {
                    OPCServer opcServer = new OPCServer(Servers[i].Name);
                    opcServer.Connect(Servers[i].ProgID, Servers[i].ServerName);// opcServer.Connect(config.ConnectionString);

                    OPCGroup group = opcServer.AddGroup(Servers[i].GroupName, Servers[i].UpdateRate);
                    foreach (ItemInfo item in Servers[i].Items)
                    {
                        group.AddItem(item.ItemName, item.OpcItemName, item.ClientHandler, item.IsActive);
                    }
                    if (Servers[i].Name.IndexOf("CranePLC") >= 0)
                    {
                        opcServer.Groups.DefaultGroup.OnDataChanged += new OPCGroup.DataChangedEventHandler(CranePLC_OnDataChanged);
                    }
                    if (Servers[i].Name.IndexOf("Convey") >= 0)
                    {
                        opcServer.Groups.DefaultGroup.OnDataChanged += new OPCGroup.DataChangedEventHandler(Convey_OnDataChanged);
                    }
                }
            }
            catch (Exception ex)
            {
                Logger.Error(ex.Message);
            }

            
        }


        private void tmWorker(object sender, System.Timers.ElapsedEventArgs e)
        {
            //try
            //{
            //    tmWorkTimer.Stop();

            //    foreach (KeyValuePair<string, Car> kvp in dicCar)
            //    {
            //        //反馈给总控WCS设备状态
            //        string id = Guid.NewGuid().ToString();
            //        string deviceNo = kvp.Value.CarNo;
            //        string mode = kvp.Value.Mode.ToString();
            //        string status = kvp.Value.Status.ToString();
            //        string taskNo = kvp.Value.TaskNo;
            //        string fork = kvp.Value.ForkStatus.ToString();
            //        string load = kvp.Value.Load.ToString();
            //        string aisleNo = kvp.Value.CarNo.Substring(0,2);
            //        string column = kvp.Value.Column.ToString();
            //        string layer = kvp.Value.Layer.ToString();
            //        string alarmCode = kvp.Value.AlarmCode.ToString();
            //        DataRow[] drs = dtDeviceAlarm.Select(string.Format("Flag=2 and AlarmCode={0}", alarmCode));
            //        string alarmDesc = "";
            //        if (alarmCode != "0")
            //        {
            //            if (drs.Length > 0)
            //                alarmDesc = drs[0]["AlarmDesc"].ToString();
            //            else
            //                alarmDesc = "穿梭车未知错误！";
            //        }
            //        string sender1 = "admin";

            //        string Json = "[{\"id\":\"" + id + "\",\"deviceNo\":\"" + Program.WarehouseCode + deviceNo + "\",\"mode\":\"" + mode + "\",\"status\":\"" + status + "\",\"taskNo\":\"" + taskNo + "\",\"fork\":\"" + fork + "\",\"load\":\"" + load + "\",\"aisleNo\":\"" + aisleNo + "\",\"column\":\"" + column + "\",\"layer\":\"" + layer + "\",\"alarmCode\":\"" + alarmCode + "\",\"sendDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\",\"sender\":\"" + sender1 + "\",\"field1\":\"\",\"field2\":\"" + alarmDesc + "\",\"field3\":\"\"" + "}]";
            //        Logger.Info("上报设备编号[" + deviceNo + "]的状态");
            //        string message = Program.send("transWCSDevice", Json);
            //        //App.Dispatching.Process.RtnMessage rtnMessage = JsonHelper.JSONToObject<App.Dispatching.Process.RtnMessage>(message);
            //        //Logger.Info("上报设备编号[" + deviceNo + "]的状态,收到反馈：" + rtnMessage.returnCode + ":" + rtnMessage.message);
            //    }
            //}
            //catch (Exception ex)
            //{
            //    Logger.Error("frmMonitor在上报设备状态时发生错误：" + ex.Message);
            //}
            //finally
            //{
            //    tmWorkTimer.Start();
            //}
        }
      
        #region 堆垛机监控
        void CranePLC_OnDataChanged(object sender, DataChangedEventArgs e)
        {
            if (e.State == null)
                return;
            string CraneNo = e.ServerName.Replace("CranePLC", "");
            GetCrane(CraneNo);
            if (e.ItemName.IndexOf("Mode") >= 0)
                dicCrane[CraneNo].Mode = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("State1") >= 0)
                dicCrane[CraneNo].State1 = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("Fork1") >= 0)
                dicCrane[CraneNo].Fork1 = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("TaskNo1") >= 0)
                dicCrane[CraneNo].TaskNo1 = Util.ConvertStringChar.BytesToString(ObjectUtil.GetObjects(e.States));
            else if (e.ItemName.IndexOf("State2") >= 0)
                dicCrane[CraneNo].State2 = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("Fork2") >= 0)
                dicCrane[CraneNo].Fork2 = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("TaskNo2") >= 0)
                dicCrane[CraneNo].TaskNo2 = Util.ConvertStringChar.BytesToString(ObjectUtil.GetObjects(e.States));
            else if (e.ItemName.IndexOf("AlarmCode") >= 0)
                dicCrane[CraneNo].AlarmCode = int.Parse(e.State.ToString());
            else if (e.ItemName.IndexOf("Station") >= 0)
                dicCrane[CraneNo].Station = e.States;

            Cranes.CraneInfo(dicCrane[CraneNo]);
            
           
        }

        void Monitor_OnCrane(CraneEventArgs args)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new CraneEventHandler(Monitor_OnCrane), args);
            }
            else
            {
                Crane crane = args.crane;
                try
                {

                    SetTextBoxText("txtMode{0}", crane.CraneNo, dicCraneMode[crane.Mode]);
                    SetTextBoxText("txtState{0}1", crane.CraneNo, dicCraneState[crane.State1]);
                    SetTextBoxText("txtFork{0}1", crane.CraneNo, dicCraneFork[crane.Fork1]);
                    SetTextBoxText("txtTaskNo{0}1", crane.CraneNo, crane.TaskNo1);
                    string strErrMsg = "";
                    if (crane.AlarmCode > 0)
                    {
                        DataRow[] drs = dtDeviceAlarm.Select(string.Format("Flag=1 and AlarmCode={0}", crane.AlarmCode));
                        if (drs.Length > 0)
                            strErrMsg = drs[0]["AlarmDesc"].ToString();
                        else
                            strErrMsg = "设备未知错误！";
                        SetControlColor("txtAlarmCode{0}", crane.CraneNo, Color.Red);
                        SetControlColor("btnClearAlarm{0}", crane.CraneNo, Color.Red);
                    }
                    else
                    {
                        SetControlColor("txtAlarmCode{0}", crane.CraneNo, SystemColors.Control);
                        SetControlColor("btnClearAlarm{0}", crane.CraneNo, SystemColors.Control);
                    }
                    if (int.Parse(crane.Station[0].ToString()) < 7)
                    {
                        Point P = new Point();
                        P = P2;
                        P.X = dicLocationX[int.Parse(crane.Station[0].ToString())];
                        pictureBox2.Location = P;

                    }
                    SetTextBoxText("txtAlarmCode{0}", crane.CraneNo, crane.AlarmCode.ToString());
                    SetTextBoxText("txtAlarmDesc{0}", crane.CraneNo, strErrMsg);
                    SetTextBoxText("txtColumn{0}", crane.CraneNo, crane.Station[0].ToString());
                    SetTextBoxText("txtRow{0}", crane.CraneNo, crane.Station[1].ToString());

                }
                catch (Exception ex)
                {
                    MCP.Logger.Error("监控界面中Monitor_OnCrane出现异常,错误内容:" + ex.Message);
                }
            }
        }
        private Dictionary<string, Crane> dicCrane = new Dictionary<string, Crane>();
        private Crane GetCrane(string craneno)
        {
            Crane crane = null;
            if (dicCrane.ContainsKey(craneno))
            {
                crane = dicCrane[craneno];
            }
            else
            {
                crane = new Crane();
                crane.CraneNo = craneno;
                dicCrane.Add(craneno, crane);
            }
            return crane;
        }
        #endregion


        #region 输送线监控

        void Convey_OnDataChanged(object sender, DataChangedEventArgs e)
        {
            if (e.State == null)
                return;
            string[] str = e.ItemName.Split(new string[] { "_" }, StringSplitOptions.RemoveEmptyEntries);
            string ConveyID = str[0];
            string ChangeName = str[1];
            GetConveyor(ConveyID);
            if (ChangeName.IndexOf("TaskNo") >= 0)
            {
                object[] obj = ObjectUtil.GetObjects(e.State);
                string TaskNo = obj[0].ToString() + obj[1].ToString().PadLeft(4, '0') + obj[2].ToString().PadLeft(4, '0');
                dicConvey[ConveyID].TaskNo = TaskNo;
            }
            else if (ChangeName.IndexOf("PalletCode") >= 0)
                dicConvey[ConveyID].PalletCode = e.State.ToString();
            Conveyors.ConveyorInfo(dicConvey[ConveyID]);
        }

        void Conveyors_OnConveyor(ConveyorEventArgs args)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new CraneEventHandler(Monitor_OnCrane), args);
            }
            else
            {
                Conveyor Convey = args.conveyor;
                try
                {

                    SetTextBoxText("txtTaskNo{0}", Convey.ID, Convey.TaskNo);
                    SetTextBoxText("txtPalletCode{0}", Convey.ID, Convey.PalletCode);
                    SetTextBoxText("txtAlarmDesc{0}1", Convey.ID, Convey.AlarmCode.ToString());


                }
                catch (Exception ex)
                {
                    MCP.Logger.Error("监控界面中Conveyors_OnConveyor出现异常" + Convey.ID + " 错误内容:" + ex.Message);
                }
            }
        }
         
     

        private Dictionary<string, Conveyor> dicConvey = new Dictionary<string, Conveyor>();
        private Conveyor GetConveyor(string ConveyID)
        {
            Conveyor Convey = null;
            if (dicConvey.ContainsKey(ConveyID))
            {
                Convey = dicConvey[ConveyID];
            }
            else
            {
                Convey = new Conveyor();
                Convey.ID = ConveyID;
                dicConvey.Add(ConveyID, Convey);
            }
            return Convey;
        }
      
        #endregion

        private void AddDicKeyValue()
        {
            dicCraneFork.Add(0, "原点");
            dicCraneFork.Add(1, "左侧");
            dicCraneFork.Add(2, "右侧");

            dicCraneState.Add(0, "未知");
            dicCraneState.Add(1, "空闲");
            dicCraneState.Add(2, "检查任务数据");
            dicCraneState.Add(3, "定位到取货位");
            dicCraneState.Add(4, "取货中");
            dicCraneState.Add(7, "取货完成");
            dicCraneState.Add(8, "等待调度柜允许");
            dicCraneState.Add(9, "移动到放货位置");
            dicCraneState.Add(10, "放货中");
            dicCraneState.Add(13, "搬运完成");
            dicCraneState.Add(14, "空载避让");
            dicCraneState.Add(15, "检查任务数据");
            dicCraneState.Add(20, "检查源位置");
            dicCraneState.Add(21, "检查目标位置");
            dicCraneState.Add(99, "报警");

            dicCraneMode.Add(0, "关机模式");
            dicCraneMode.Add(1, "自动模式");
            dicCraneMode.Add(2, "手动模式");
            dicCraneMode.Add(3, "半制动模式");
            dicCraneMode.Add(4, "维修模式");

            dicLocationX.Add(0, 300);
            dicLocationX.Add(1, 225);
            dicLocationX.Add(2, 180);
            dicLocationX.Add(3, 134);
            dicLocationX.Add(4, 73);
            dicLocationX.Add(5, 27);
            dicLocationX.Add(6, -19);
            

            dtDeviceAlarm = bll.FillDataTable("WCS.SelectDeviceAlarm", new DataParameter[] { new DataParameter("{0}", "Flag=1") });
        }


        Button GetButton(string CarNo)
        {
            Control[] ctl = this.Controls.Find("btnCar" + CarNo, true);
            if (ctl.Length > 0)
                return (Button)ctl[0];
            else
                return null;
        }


        private void SetTextBoxText(string name, string CraneNo, string msg)
        {
            TextBox txt;
            Control[] ctl = this.Controls.Find(string.Format(name, CraneNo), true);
            if (ctl.Length > 0)
                txt = (TextBox)ctl[0];
            else
                txt = null;
            if (txt != null)
                txt.Text = msg;
        }
        private void SetControlColor(string name, string CraneNo, Color red)
        {
            Control txt;
            Control[] ctl = this.Controls.Find(string.Format(name, CraneNo), true);
            if (ctl.Length > 0)
                txt = ctl[0];
            else
                txt = null;
            if (txt != null)
                txt.BackColor = red;
        }


        private void Send2Cmd(int CmdType)
        {
            string serviceName = "CranePLC01";
            object obj = MCP.ObjectUtil.GetObject(base.Context.ProcessDispatcher.WriteToService(serviceName, "CraneAlarmCode")).ToString();
            if (obj.ToString() != "0")
            {
                string ItemName = "Stop";
                int Value = 1;
                string MSG = "堆垛机下发急停命令";
                if (CmdType == 1)
                {
                    ItemName = "TaskType";
                    Value = 5;
                    MSG="已给堆垛机下发取消任务指令,任务号:" + this.txtTaskNo01.Text;
                }

                base.Context.ProcessDispatcher.WriteToService(serviceName, ItemName, Value);
                base.Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);
                Logger.Info(MSG);
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            Send2Cmd(1);
        }

        private void btnStop_Click(object sender, EventArgs e)
        {
            Send2Cmd(0);
        }

        private void btnClearAlarm_Click(object sender, EventArgs e)
        {
            Context.ProcessDispatcher.WriteToService("CranePLC01", "Reset", 1);
            Logger.Info("堆垛机解警");
        }

    }
}