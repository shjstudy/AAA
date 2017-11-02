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
    public partial class frmMonitorA : BaseForm
    {
        private Point InitialP1;
        private Point InitialP2;
        private Point InitialP3;
        private Point InitialP4;


        float colDis = 20.75f;
        float rowDis = 54.4f;

        private System.Timers.Timer tmWorkTimer = new System.Timers.Timer();
        private System.Timers.Timer tmCrane1 = new System.Timers.Timer();
        BLL.BLLBase bll = new BLL.BLLBase();
        Dictionary<int, string> dicFork = new Dictionary<int, string>();
        Dictionary<int, string> dicStatus = new Dictionary<int, string>();
        Dictionary<int, string> dicWorkMode = new Dictionary<int, string>();

        DataTable dtDeviceAlarm;

        public frmMonitorA()
        {
            InitializeComponent();
        }

        private void frmMonitorA_Load(object sender, EventArgs e)
        {
            DataTable dt = Program.dtUserPermission;
            //监控任务--取消堆垛机任务
            string filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.btnCancel1.Visible = false;
                this.btnCancel2.Visible = false;
                this.btnCancel3.Visible = false;
                this.btnCancel4.Visible = false;
            }
            else
            {
                this.btnCancel1.Visible = true;
                this.btnCancel2.Visible = true;
                this.btnCancel3.Visible = true;
                this.btnCancel4.Visible = true;
            }
            Cranes.OnCrane += new CraneEventHandler(Monitor_OnCrane);

            InitialP1 = btnSRM1.Location;
            InitialP2 = btnSRM2.Location;
            InitialP3 = btnSRM3.Location;
            InitialP4 = btnSRM4.Location;
            AddDicKeyValue();
            try
            {
                ServerInfo[] Servers = new MonitorConfig("MonitorA.xml").Servers;
                for (int i = 0; i < Servers.Length; i++)
                {
                    OPCServer opcServer = new OPCServer(Servers[i].Name);
                    opcServer.Connect(Servers[i].ProgID, Servers[i].ServerName);// opcServer.Connect(config.ConnectionString);

                    OPCGroup group = opcServer.AddGroup(Servers[i].GroupName, Servers[i].UpdateRate);
                    foreach (ItemInfo item in Servers[i].Items)
                    {
                        group.AddItem(item.ItemName, item.OpcItemName, item.ClientHandler, item.IsActive);
                    }
                    if (Servers[i].Name.IndexOf("PLC") >= 0)
                    {
                        opcServer.Groups.DefaultGroup.OnDataChanged += new OPCGroup.DataChangedEventHandler(Crane_OnDataChanged);
                    }
                }
            }
            catch (Exception ex)
            {
                Logger.Error(ex.Message);
            }

            tmWorkTimer.Interval = Program.SendInterval;
            tmWorkTimer.Elapsed += new System.Timers.ElapsedEventHandler(tmWorker);
            tmWorkTimer.Start();
        }
        private void tmWorker(object sender, System.Timers.ElapsedEventArgs e)
        {
            try
            {
                tmWorkTimer.Stop();

                foreach (KeyValuePair<string, Crane> kvp in dicCrane)
                {
                    //反馈给总控WCS设备状态
                    string id = Guid.NewGuid().ToString();
                    string deviceNo = "01" + kvp.Value.CraneNo;
                    string mode = kvp.Value.Mode.ToString();
                    string status = kvp.Value.Status.ToString();
                    string taskNo = kvp.Value.TaskNo;
                    string fork = kvp.Value.ForkStatus.ToString();
                    string load = kvp.Value.Load.ToString();
                    string aisleNo = kvp.Value.Row.ToString();
                    string column = kvp.Value.Column.ToString();
                    string layer = kvp.Value.Layer.ToString();
                    string alarmCode = kvp.Value.AlarmCode.ToString();
                    DataRow[] drs = dtDeviceAlarm.Select(string.Format("AlarmCode={0}", alarmCode));
                    string alarmDesc = "";
                    if (drs.Length > 0)
                        alarmDesc = drs[0]["AlarmDesc"].ToString();
                    else
                        alarmDesc = "堆垛机未知错误！";
                    string sender1 = "admin";

                    string Json = "[{\"id\":\"" + id + "\",\"deviceNo\":\"" + deviceNo + "\",\"mode\":\"" + mode + "\",\"status\":\"" + status + "\",\"taskNo\":\"" + taskNo + "\",\"fork\":\"" + fork + "\",\"load\":\"" + load + "\",\"aisleNo\":\"" + aisleNo + "\",\"column\":\"" + column + "\",\"layer\":\"" + layer + "\",\"alarmCode\":\"" + alarmCode + "\",\"sendDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\",\"sender\":\"" + sender1 + "\",\"field1\":\"\",\"field2\":\"" + alarmDesc + "\",\"field3\":\"\"" + "}]";
                    Logger.Info("上报设备编号[" + deviceNo + "]的状态");
                    string message = Program.send("transWCSDevice", Json);
                    App.Dispatching.Process.RtnMessage rtnMessage = JsonHelper.JSONToObject<App.Dispatching.Process.RtnMessage>(message);
                    Logger.Info("上报设备编号[" + deviceNo + "]的状态,收到反馈：" + rtnMessage.returnCode + ":" + rtnMessage.message);
                }
            }
            catch (Exception ex)
            {
                Logger.Error("frmMonitorB在上报设备状态时发生错误：" + ex.Message);
            }
            finally
            {
                tmWorkTimer.Start();
            }
        }
        //反馈给总控WCS设备状态
        //string m = "[{\"id\":\"" + id + "\",\"deviceNo\":\"" + deviceNo + "\",\"mode\":\"" + mode + "\",\"status\":\"" + status + "\",\"taskNo\":\"" + taskNo + "\",\"fork\":\"" + fork + "\",\"load\":\"" + load + "\",\"aisleNo\":\"" + aisleNo + "\",\"column\":\"" + column + "\",\"layer\":\"" + layer + "\",\"alarmCode\":\"" + alarmCode + "\",\"sendDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\",\"sender\":\"" + sender + "\",\"field1\":\"\",\"field2\":\"\",\"field3\":\"\"" + "}]";
        #region 堆垛机监控
        void Crane_OnDataChanged(object sender, DataChangedEventArgs e)
        {
            if (e.State == null)
                return;
            string CraneNo = e.ServerName.Substring(5, 2);
            GetCrane(CraneNo);
            if (e.ItemName.IndexOf("Status") >= 0)
            {
                dicCrane[CraneNo].Status = int.Parse(e.States[4].ToString());
                dicCrane[CraneNo].Row = int.Parse(e.States[5].ToString());
            }
            else if (e.ItemName.IndexOf("WorkMode") >= 0)
            {
                dicCrane[CraneNo].Mode = int.Parse(e.States[1].ToString());
                dicCrane[CraneNo].AlarmCode = int.Parse(e.States[2].ToString());
                dicCrane[CraneNo].Load = int.Parse(e.States[3].ToString());
                dicCrane[CraneNo].Column = int.Parse(e.States[4].ToString());
                dicCrane[CraneNo].Layer = int.Parse(e.States[5].ToString());
                dicCrane[CraneNo].ForkStatus = int.Parse(e.States[6].ToString());
            }
            else if (e.ItemName.IndexOf("TaskNo") >= 0)
                dicCrane[CraneNo].TaskNo = Util.ConvertStringChar.BytesToString(ObjectUtil.GetObjects(e.States));
            if (e.ItemName.IndexOf("STB") >= 0)
            {
                dicCrane[CraneNo].STB = int.Parse(e.States[0].ToString());
                dicCrane[CraneNo].ACK = int.Parse(e.States[1].ToString());
            }
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
                Button btn = GetButton(crane.CraneNo);
                TextBox txt = GetTextBox("txtTaskNo", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.TaskNo;

                txt = GetTextBox("txtStatus", crane.CraneNo);
                if (txt != null && dicStatus.ContainsKey(crane.Status))
                {
                    txt.Text = dicStatus[crane.Status];
                    if (txt.Text == "空闲")
                    {
                        txt.BackColor = Color.Lime;
                    }
                    else
                    {
                        txt.BackColor = Color.Yellow;
                    }
                }

                txt = GetTextBox("txtWorkMode", crane.CraneNo);
                if (txt != null && dicWorkMode.ContainsKey(crane.Mode))
                {
                    txt.Text = dicWorkMode[crane.Mode];
                    if (crane.Mode == 1)
                    {
                        txt.BackColor = Color.Lime;
                    }
                    else
                    {
                        txt.BackColor = Color.Yellow;
                    }
                }
                if (crane.Mode == 1)
                    btn.BackColor = Color.Lime;
                else
                    btn.BackColor = Color.Yellow;
                txt = GetTextBox("txtRow", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.Row.ToString();

                txt = GetTextBox("txtColumn", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.Column.ToString();

                //堆垛机位置
                if (crane.CraneNo == "01")
                {
                    Point P = InitialP1;
                    if (crane.Row == 1)
                        P = InitialP1;
                    else if (crane.Row == 2)
                        P = InitialP2;

                    P.X = P.X + (int)((45 - crane.Column) * colDis);
                    this.btnSRM1.Location = P;
                }
                if (crane.CraneNo == "02")
                {
                    Point P = InitialP2;
                    if (crane.Row == 1)
                        P = InitialP1;
                    else if (crane.Row == 2)
                        P = InitialP2;

                    P.X = P.X + (int)((45 - crane.Column) * colDis);
                    this.btnSRM2.Location = P;
                }
                if (crane.CraneNo == "03")
                {
                    Point P = InitialP3;
                    if (crane.Row == 3)
                        P = InitialP3;
                    else if (crane.Row == 4)
                        P = InitialP4;

                    P.X = P.X + (int)((45 - crane.Column) * colDis);
                    this.btnSRM3.Location = P;
                }
                if (crane.CraneNo == "04")
                {
                    Point P = InitialP4;
                    if (crane.Row == 3)
                        P = InitialP3;
                    else if (crane.Row == 4)
                        P = InitialP4;

                    P.X = P.X + (int)((45 - crane.Column) * colDis);
                    this.btnSRM4.Location = P;
                }

                txt = GetTextBox("txtLayer", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.Layer.ToString();

                txt = GetTextBox("txtForkStatus", crane.CraneNo);
                if (txt != null && dicFork.ContainsKey(crane.ForkStatus))
                    txt.Text = dicFork[crane.ForkStatus];
                txt = GetTextBox("txtAlarmCode", crane.CraneNo);
                if (txt != null)
                {
                    txt.Text = crane.AlarmCode.ToString();
                    if (txt.Text == "0")
                    {
                        txt.BackColor = DefaultBackColor;
                    }
                    else
                    {
                        txt.BackColor = Color.Red;
                    }
                }

                string strAlarmDesc = "";
                txt = GetTextBox("txtAlarmDesc", crane.CraneNo);
                if (txt != null)
                {
                    if (crane.AlarmCode != 0)
                    {
                        DataRow[] drs = dtDeviceAlarm.Select(string.Format("Flag=1 and AlarmCode={0}", crane.AlarmCode));
                        if (drs.Length > 0)
                            strAlarmDesc = drs[0]["AlarmDesc"].ToString();
                        else
                            strAlarmDesc = "设备未知错误！";
                    }
                    else
                    {
                        strAlarmDesc = "";
                    }
                    txt.Text = strAlarmDesc;
                    if (txt.Text == "")
                    {
                        txt.BackColor = DefaultBackColor;
                    }
                    else
                    {
                        txt.BackColor = Color.Red;
                    }
                }
                txt = GetTextBox("txtSTB", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.STB.ToString();
                txt = GetTextBox("txtACK", crane.CraneNo);
                if (txt != null)
                    txt.Text = crane.ACK.ToString();
                //更新错误代码、错误描述
                //更新任务状态为执行中
                //bll.ExecNonQuery("WCS.UpdateTaskError", new DataParameter[] { new DataParameter("@CraneErrCode", crane.ErrCode.ToString()), new DataParameter("@CraneErrDesc", dicCraneError[crane.ErrCode]), new DataParameter("@TaskNo", crane.TaskNo) });
                if (crane.AlarmCode > 0)
                {
                    //DataParameter[] param = new DataParameter[] { new DataParameter("@TaskNo", crane.TaskNo), new DataParameter("@CraneErrCode", crane.AlarmCode.ToString()), new DataParameter("@CraneErrDesc", strAlarmDesc) };
                    //bll.ExecNonQueryTran("WCS.Sp_UpdateTaskError", param);
                    //Logger.Error(crane.CraneNo.ToString() + "堆垛机执行时出现错误,代码:" + crane.AlarmCode.ToString() + ",描述:" + strAlarmDesc);
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

        private void AddDicKeyValue()
        {
            dicFork.Add(0, "货叉在原位");
            dicFork.Add(1, "货叉在左侧");
            dicFork.Add(2, "货叉在右侧");

            dicStatus.Add(0, "未知");
            dicStatus.Add(1, "空闲");
            //dicStatus.Add(2, "检查任务数据");
            //dicStatus.Add(3, "定位到取货位");
            //dicStatus.Add(4, "取货中");
            //dicStatus.Add(7, "取货完成");
            //dicStatus.Add(8, "等待调度柜");
            //dicStatus.Add(9, "取货完成");
            //dicStatus.Add(10, "取货完成");
            //dicStatus.Add(13, "取货完成");
            //dicStatus.Add(14, "取货完成");
            //dicStatus.Add(15, "取货完成");
            //dicStatus.Add(20, "检查源位置");

            dicWorkMode.Add(0, "关机");
            dicWorkMode.Add(1, "自动");
            dicWorkMode.Add(2, "手动");
            dicWorkMode.Add(3, "半自动");
            dicWorkMode.Add(4, "维修");

            dtDeviceAlarm = bll.FillDataTable("WCS.SelectDeviceAlarm", new DataParameter[] { new DataParameter("{0}", "1=1") });
        }

        
        Button GetButton(string CraneNo)
        {
            Control[] ctl = this.Controls.Find("btnSRM" + int.Parse(CraneNo), true);
            if (ctl.Length > 0)
                return (Button)ctl[0];
            else
                return null;
        }

        TextBox GetTextBox(string name, string CraneNo)
        {
            Control[] ctl = this.Controls.Find(name + int.Parse(CraneNo), true);
            if (ctl.Length > 0)
                return (TextBox)ctl[0];
            else
                return null;
        }

        private void Send2Cmd(string DeviceNo)
        {
            string serviceName = "PLC" + DeviceNo;
            int[] cellAddr = new int[12];

            object obj = MCP.ObjectUtil.GetObject(base.Context.ProcessDispatcher.WriteToService(serviceName, "AlarmCode")).ToString();
            if (obj.ToString() != "0")
            {
                cellAddr[6] = 5;
                sbyte[] taskNo = new sbyte[20];
                for (int i = 0; i < 20; i++)
                    taskNo[i] = 32;

                base.Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
                base.Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
                base.Context.ProcessDispatcher.WriteToService(serviceName, "STB", 1);

                MCP.Logger.Info("已给设备" + DeviceNo + "下发取消任务指令");
            }
        }

        private void btnCancel4_Click(object sender, EventArgs e)
        {
            Send2Cmd("0104");
        }

        private void btnCancel3_Click(object sender, EventArgs e)
        {
            Send2Cmd("0103");
        }

        private void btnCancel2_Click(object sender, EventArgs e)
        {
            Send2Cmd("0102");
        }

        private void btnCancel1_Click(object sender, EventArgs e)
        {
            Send2Cmd("0101");
        }     
    }
}