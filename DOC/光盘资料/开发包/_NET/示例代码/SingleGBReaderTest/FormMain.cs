using System;
using System.Data;
using System.Net.NetworkInformation;
using System.Windows.Forms;
using Core = Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;
using System.Threading;
using Invengo.NetAPI.Protocol.IRP1;
using Invengo.NetAPI.Core;

namespace SingleReaderTest
{
    public partial class FormMain : Form
    {
        // 实例化读写器类
        IRP1.Reader reader = new IRP1.Reader("Reader1", "RS232", "COM1,115200");//串口
        //IRP1.Reader reader = new IRP1.Reader("Reader1", "TCPIP_Client", "192.168.1.210:7086");//网口
        //IRP1.GB_ReadTag scanMsg = new IRP1.GB_ReadTag(0x81, 0x00, 0x00, 0x00);//扫描消息
        DataTable myDt = new DataTable();//显示扫描数据
        object lockobj = new object();//显示数据锁定
        bool isTryReconnNet = false;
        int tryReconnNetTimeSpan;
        public Byte type = 0x01;
        public string accessPwd = "00000000";
        public GBMemoryBank readmemoryBank = GBMemoryBank.InformationMemory;
        public UInt32 ptr = 0;
        public int length = 8;

        public FormMain()
        {
            InitializeComponent();
            myDt.Columns.Add("Data");
            myDt.Columns.Add("Count");
            dataGridView1.DataSource = myDt;
            dataGridView1.Columns[0].HeaderText = "数据";
            dataGridView1.Columns[1].HeaderText = "读取次数";
            this.dataGridView1.Columns[0].Width = 360;
            this.dataGridView1.Columns[1].Width = 80;
            IRP1.Reader.OnApiException += new Core.ApiExceptionHandle(Reader_OnApiException);
            comboBox1.SelectedIndex = 0;
        }

        void Reader_OnApiException(Core.ErrInfo e)
        {
            if (e.Ei.ErrCode == "FF22")
            {
                changeCtrlEnable("disconn");
                showMsg(e.Ei.ErrMsg);
                if (isTryReconnNet)
                    ReConn();
            }
            else if (e.Ei.ErrCode == "FF24")//发现连接作废,不作断网恢复尝试
            {
                isTryReconnNet = false;
                changeCtrlEnable("disconn");
                showMsg(e.Ei.ErrMsg);
            }
        }

        #region 重连接
        private void ReConn()
        {
            bool isSuc = false;
            using (Ping ping = new Ping())
            {
                for (int i = 0; i < tryReconnNetTimeSpan * 60; i++)
                {
                    PingReply pingReply = null;
                    try
                    {
                        pingReply = ping.Send(reader.ConnString.Substring(0,
                            reader.ConnString.IndexOf(':')), 1000);//超时为1秒

                        if (pingReply.Status != IPStatus.Success)
                        {
                            showMsg("Ping 不通");
                            continue;
                        }
                        else
                        {
                            isSuc = true;
                            break;
                        }
                    }
                    catch (Exception ex)
                    {
                        showMsg("尝试自动恢复连接失败！" + ex.Message);
                        return;
                    }
                }
            }
            //建立连接
            if (isSuc)
            {
                isSuc = false;
                for (int i = 0; i < 3; i++)//尝试3次
                {
                    if (reader.Connect())
                    {
                        showMsg("尝试自动恢复连接成功！");
                        changeCtrlEnable("conn");
                        isSuc = true;
                        break;
                    }
                    else
                    {
                        Thread.Sleep(2000);
                        continue;
                    }
                }
                if (!isSuc)
                    showMsg("尝试自动恢复连接失败！");
            }
        }
        #endregion

        // 关闭窗体
        private void FormMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            Environment.Exit(Environment.ExitCode);
        }

        // 建立连接
        private void btnConn_Click(object sender, EventArgs e)
        {
            if (reader.Connect())
            {
                changeCtrlEnable("conn");
                //注册接收读写器消息事件
                reader.OnMessageNotificationReceived += new Invengo.NetAPI.Core.MessageNotificationReceivedHandle(reader_OnMessageNotificationReceived);
                lblMsg.Text = "连接成功";
            }
            else
            {
                lblMsg.Text = "连接失败";
                MessageBox.Show("建立连接失败");
            }
        }

        /// <summary>
        /// 接收到读写器消息触发事件
        /// </summary>
        /// <param name="reader">读写器类</param>
        /// <param name="msg">消息内容</param>
        void reader_OnMessageNotificationReceived(Invengo.NetAPI.Core.BaseReader reader, Invengo.NetAPI.Core.IMessageNotification msg)
        {
            if (msg.StatusCode != 0)
            {
                //显示错误信息
                showMsg(msg.ErrInfo);
                return;
            }
            String msgType = msg.GetMessageType();
            msgType = msgType.Substring(msgType.LastIndexOf('.') + 1);
            switch (msgType)
            {
                #region GB_ReadTag
                case "GB_ReadTag":
                    {

                        IRP1.GB_ReadTag m = (IRP1.GB_ReadTag)msg;
                        //string tagType = m.ReceivedMessage.TagType;
                        display(m);

                    }
                    break;
                #endregion
                #region RXD_IOTriggerSignal_800
                case "RXD_IOTriggerSignal_800":
                    {
                        IRP1.RXD_IOTriggerSignal_800 m = (IRP1.RXD_IOTriggerSignal_800)msg;
                        if (m.ReceivedMessage.IsStart)
                        {
                            changeCtrlEnable("scan");
                            showMsg(" I/O 触发，正在读卡...");
                        }
                        else
                        {
                            changeCtrlEnable("conn");
                            showMsg(" I/O 触发，停止读卡");
                        }
                    }
                    break;
                #endregion
            }
        }

        #region 状态栏显示信息
        private delegate void showMsgHandle(string str);

        private void showMsg(string str)
        {
            if (this.InvokeRequired)
            {
                showMsgHandle h = new showMsgHandle(showMsgMethod);
                this.BeginInvoke(h, str);
            }
            else
            {
                showMsgMethod(str);
            }
        }

        private void showMsgMethod(string str)
        {
            lblMsg.Text = str;
        }
        #endregion

        #region 显示扫描标签数据
        private delegate void displayHandle(IRP1.GB_ReadTag msg);

        private void display(GB_ReadTag msg)
        {
            if (dataGridView1.InvokeRequired)
            {
                displayHandle h = new displayHandle(displayMethod);
                dataGridView1.BeginInvoke(h, msg);
            }
            else
            {
                displayMethod(msg);
            }
        }

        private void displayMethod(IRP1.GB_ReadTag msg)
        {
            lock (lockobj)
            {
                bool isAdd = true;
                string data = Core.Util.ConvertByteArrayToHexString(msg.ReceivedMessage.Data);

                foreach (DataRow dr in myDt.Rows)
                {
                    if (dr["Data"] != null && dr["Data"].ToString() != "" && dr["Data"].ToString() == data)
                    {
                        isAdd = false;
                        dr["Count"] = int.Parse(dr["Count"].ToString()) + 1;
                    }
                }

                if (isAdd)
                {
                    DataRow mydr = myDt.NewRow();
                    mydr["Data"] = data;
                    mydr["Count"] = 1;
                    myDt.Rows.Add(mydr);
                }
            }
        }
        #endregion

        // 改变界面按钮状态
        private void changeCtrlEnable(string state)
        {
            if (this.InvokeRequired)
            {
                changeCtrlEnableHandle h = new changeCtrlEnableHandle(changeCtrlEnableMethod);
                this.BeginInvoke(h, state);
            }
            else
                changeCtrlEnableMethod(state);
        }

        private delegate void changeCtrlEnableHandle(string state);

        private void changeCtrlEnableMethod(string state)
        {
            switch (state)
            {
                case "conn":
                    btnConn.Enabled = false;
                    btnDisconn.Enabled = true;
                    btnScan.Enabled = true;
                    btnStop.Enabled = false;
                    MI_ScanConfig.Enabled = true;
                    MI_ReaderConfig.Enabled = true;
                  
                    break;
                case "disconn":
                    btnConn.Enabled = true;
                    btnDisconn.Enabled = false;
                    btnScan.Enabled = false;
                    btnStop.Enabled = false;
                    MI_ScanConfig.Enabled = false;
                    MI_ReaderConfig.Enabled = false;
                   
                    break;
                case "scan":
                    btnConn.Enabled = false;
                    btnDisconn.Enabled = true;
                    btnScan.Enabled = false;
                    btnStop.Enabled = true;
                    MI_ScanConfig.Enabled = false;
                    MI_ReaderConfig.Enabled = false;
                   
                    break;
            }
        }

        // 断开连接
        private void btnDisconn_Click(object sender, EventArgs e)
        {
            if (reader != null)
            {
                reader.OnMessageNotificationReceived -= new Invengo.NetAPI.Core.MessageNotificationReceivedHandle(reader_OnMessageNotificationReceived);
                reader.Disconnect();
            }
            changeCtrlEnable("disconn");
            lblMsg.Text = "断开连接";
        }

        // 退出
        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        // 扫描标签
        private void btnScan_Click(object sender, EventArgs e)
        {
            if (!a1.Checked && !a2.Checked && !a3.Checked && !a4.Checked)
            {
                MessageBox.Show("请选择一个天线！");
                return;
            }
            byte ant = 0x80;
            if (a1.Checked)
                ant += 0x01;
            if (a2.Checked)
                ant += 0x02;
            if (a3.Checked)
                ant += 0x04;
            if (a4.Checked)
                ant += 0x08;
            btnClean_Click(sender, e);
            byte[] pwd = Util.ConvertHexStringToByteArray(accessPwd);
            readmemoryBank = (GBMemoryBank)Enum.GetValues(typeof(GBMemoryBank)).GetValue(this.comboBox1.SelectedIndex);
            GB_ReadTag scanMsg = new IRP1.GB_ReadTag(ant, type, pwd, readmemoryBank, ptr, (byte)length);//扫描消息
            if (reader != null && reader.IsConnected)
            {
                if (reader.Send(scanMsg))
                {
                    changeCtrlEnable("scan");
                    lblMsg.Text = "正在读卡...";
                }
            }
        }

        // 停止扫描
        private void btnStop_Click(object sender, EventArgs e)
        {
            if (reader != null && reader.IsConnected)
            {
                if (reader.Send(new IRP1.PowerOff()))//发送关功放消息
                {
                    changeCtrlEnable("conn");
                    lblMsg.Text = "停止读卡";
                }
            }
        }

        // 清空数据
        private void btnClean_Click(object sender, EventArgs e)
        {
            lock (lockobj)
            {
                myDt.Rows.Clear();
            }
        }


        // 扫描配置
        private void MI_ScanConfig_Click(object sender, EventArgs e)
        {

            FormScanConfig frmScanConfig = new FormScanConfig(reader);
            frmScanConfig.ShowDialog();
        }

        // 读写器配置
        private void MI_ReaderConfig_Click(object sender, EventArgs e)
        {
            FormReaderConfig frm = new FormReaderConfig(reader);
            frm.isTryReconnNet = this.isTryReconnNet;
            frm.ShowDialog();
            this.isTryReconnNet = frm.isTryReconnNet;
            this.tryReconnNetTimeSpan = frm.tryReconnNetTimeSpan;
            frm.Dispose();
        }

        // GPIO
        private void MI_GPIO_Click(object sender, EventArgs e)
        {
            //FormGPIO frm = new FormGPIO(reader,scanMsg);
            //frm.ShowDialog();
            //frm.Dispose();
        }

        private void MI_Help_Click(object sender, EventArgs e)
        {
            FormHelp frm = new FormHelp();
            frm.ShowDialog();
            frm.Dispose();
        }
    }
}
