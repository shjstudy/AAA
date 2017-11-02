using System;
using System.Windows.Forms;
using System.Xml;
using Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;

namespace ReadersTest
{
    public partial class ReaderConfigForm : Form
    {
        private MyReader myReader;
        private String readerName;
        private String protocol;

        private bool isEnable = false;//读变长TID配置
        private decimal tidLen = 0;//TID长度值
        private bool isFastReadEnable = false;//快读TID配置
        private decimal epcLen = 0;//EPC长度值
        private Byte[] infoParam_6c;
        private Byte infoParam_6b;
        Double[] list = null;//功率表

        public ReaderConfigForm(MyReader myReader)
        {
            InitializeComponent();
            this.myReader = myReader;
            readerName = txtReader.Text = this.myReader.reader.ReaderName;
            txtMN.Text = this.myReader.reader.ModelNumber;
            protocol = this.myReader.reader.ProtocolVersion;            
            for (int i = 0; i < tabControl2.TabPages.Count; i++)
            {
                if (tabControl2.TabPages[i].Name == protocol)
                {
                    tabControl2.SelectedIndex = i;
                }
            }

            if (this.myReader.reader.ModelNumber == "XC-RF807")
            {
                list = new Double[26];
                for (int i = 0; i < list.Length; i++)
                {
                    list[i] = 11 + i;
                }
            }
            else
            {
                list = new IRP1.PowerTable().List;
            }
        }

        private void ReaderConfigForm_Load(object sender, EventArgs e)
        {
            button1_Click(sender, e);
        }

        private void tabControl1_Selected(object sender, TabControlEventArgs e)
        {
            switch (e.TabPage.Text)
            {
                case "扫描配置":
                    button1_Click(sender, EventArgs.Empty);
                    break;
                case "标签过滤":
                    if (protocol == "IRP1")
                    {
                        if (myReader.reader.ModelNumber == "XCRF-860")
                        {
                            groupBox10.Enabled = false;
                        }
                        else if(myReader.reader.ModelNumber == "XC-RF806"
                            || myReader.reader.ModelNumber == "XC-RF807")
                        {
                            groupBox10.Enabled = false;
                            groupBox9.Enabled = false;
                        }
                        else if (myReader.reader.ModelNumber == "XCRF-502E")
                        {
                            groupBox8.Enabled = false;
                            groupBox9.Enabled = false;
                        }
                    }
                    break;
                case "天线配置":
                    if (protocol == "IRP1")
                    {
                        #region 查询 天线端口功率
                        {

                            if (myReader.reader.ModelNumber == "XCRF-860"
                        || myReader.reader.ModelNumber == "XC-RF806"
                        || myReader.reader.ModelNumber == "XC-RF807")
                            {
                                gB_Antenna.Enabled = true;
                                groupBox11.Enabled = false;
                                IRP1.SysQuery_800 order = new IRP1.SysQuery_800(0x0C, 0x00);
                                if (myReader.reader.Send(order, 100))
                                {
                                    ant1.Items.Clear();
                                    ant2.Items.Clear();
                                    ant3.Items.Clear();
                                    ant4.Items.Clear();
                                    for (int i = 0; i < order.ReceivedMessage.QueryData.Length; i++)
                                    {
                                        if (order.ReceivedMessage.QueryData[i] != 0x00)
                                        {
                                            ant1.Items.Add(list[i].ToString());
                                            ant2.Items.Add(list[i].ToString());
                                            ant3.Items.Add(list[i].ToString());
                                            ant4.Items.Add(list[i].ToString());
                                        }
                                    }
                                }
                                else
                                {
                                    ant1.Items.Clear();
                                    ant2.Items.Clear();
                                    ant3.Items.Clear();
                                    ant4.Items.Clear();
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        ant1.Items.Add(list[i].ToString());
                                        ant2.Items.Add(list[i].ToString());
                                        ant3.Items.Add(list[i].ToString());
                                        ant4.Items.Add(list[i].ToString());
                                    }
                                }

                                IRP1.SysQuery_800 order1 = new IRP1.SysQuery_800(0x03);
                                if (myReader.reader.Send(order1))
                                {
                                    this.gB_Antenna.Enabled = true;
                                    this.antennaPowers[0] = list[order1.ReceivedMessage.QueryData[0]];
                                    this.antennaPowers[1] = list[order1.ReceivedMessage.QueryData[1]];
                                    this.antennaPowers[2] = list[order1.ReceivedMessage.QueryData[2]];
                                    this.antennaPowers[3] = list[order1.ReceivedMessage.QueryData[3]];
                                    this.ant1.Text = antennaPowers[0].ToString();
                                    this.ant2.Text = antennaPowers[1].ToString();
                                    this.ant3.Text = antennaPowers[2].ToString();
                                    this.ant4.Text = antennaPowers[3].ToString();
                                }
                            }
                            else if (myReader.reader.ModelNumber == "XCRF-502E-F6G"                        
                        || myReader.reader.ModelNumber == "XC-RF811")
                            {
                                gB_Antenna.Enabled = false;
                                groupBox11.Enabled = true;
                                IRP1.PowerParamConfig_500 order = new IRP1.PowerParamConfig_500(0x01, new Byte[2] { 0x2f, 0x00 });//查询
                                if (myReader.reader.Send(order))
                                {
                                    int value = order.ReceivedMessage.QueryData[0] * 256 + order.ReceivedMessage.QueryData[1];
                                    lblValue.Text = value + "";
                                    trackBar1.Value = value;
                                }
                            }
                        }
                        #endregion
                    }
                    break;
                case "读写器网址":
                    button3_Click(sender, EventArgs.Empty);
                    break;
            }
        }

        #region 扫描配置
        // 查询
        private void button1_Click(object sender, EventArgs e)
        {
            switch (myReader.reader.ProtocolVersion)
            {
                case "IRP1":
                    tabControl2.SelectedIndex = 0;
                    {                        
                        #region 控件显示
                        switch (myReader.reader.ModelNumber)
                        {
                            case "XCRF-502E-F6G":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("EPC_6C");
                                    cbReadMB.Items.Add("TID_6C");
                                    cbReadMB.Items.Add("EPC_TID_UserData_6C");                                    
                                    cbReadMB.Items.Add("ID_6B");                                    
                                    cbReadMB.Items.Add("EPC_6C_ID_6B");
                                    cbReadMB.Items.Add("TID_6C_ID_6B");
                                    cbReadMB.Items.Add("EPC_TID_UserData_6C_ID_UserData_6B");                                    
                                    cbReadMB.Items.Add("EPC_PC_6C");
                                    cbReadMB.SelectedIndex = 0;

                                    cbAntenna.Items.Clear();
                                    cbAntenna.Items.Add("1#");
                                    cbAntenna.Items.Add("2#");
                                    cbAntenna.Items.Add("1-2#");
                                    cbAntenna.SelectedIndex = 0;
                                    pAntenna.Visible = false;                                    
                                }
                                #endregion
                                break;
                            case "XCRF-860":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("EPC_6C");
                                    cbReadMB.Items.Add("TID_6C");
                                    cbReadMB.Items.Add("EPC_TID_UserData_6C_2");

                                    cbAntenna.Items.Clear();
                                    cbAntenna.Items.Add("1#");
                                    cbAntenna.Items.Add("2#");
                                    cbAntenna.Items.Add("3#");
                                    cbAntenna.Items.Add("4#");
                                    cbAntenna.Items.Add("1-2#");
                                    cbAntenna.Items.Add("1-3#");
                                    cbAntenna.Items.Add("1-4#");

                                    pAntenna.Visible = false;                                   
                                }
                                #endregion
                                break;
                            case "XC-RF806":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("EPC_6C");
                                    cbReadMB.Items.Add("TID_6C");
                                    cbReadMB.Items.Add("EPC_TID_UserData_6C_2");

                                    cbAntenna.Visible = false;                                    
                                }
                                #endregion
                                break;
                            case "XC-RF807":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("EPC_6C");
                                    cbReadMB.Items.Add("TID_6C");
                                    cbReadMB.Items.Add("EPC_TID_UserData_6C_2");                                    
                                    cbReadMB.Items.Add("EPC_TID_UserData_Reserved_6C_ID_UserData_6B");
                                    cbAntenna.Visible = false;
                                }
                                #endregion
                                break;
                            case "XCRF-502E":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("ID_6B");
                                    cbReadMB.SelectedIndex = 0;

                                    cbAntenna.Items.Clear();
                                    cbAntenna.Items.Add("1#");
                                    cbAntenna.Items.Add("2#");
                                    cbAntenna.Items.Add("1-2#");

                                    pAntenna.Visible = false;
                                }
                                #endregion
                                break;
                            case "XC-RF811":
                                #region
                                {
                                    cbReadMB.Items.Clear();
                                    cbReadMB.Items.Add("EPC_6C");
                                    cbReadMB.Items.Add("TID_6C");
                                    cbReadMB.Items.Add("ID_6B");

                                    cbAntenna.Items.Clear();
                                    cbAntenna.Items.Add("1#");

                                    pAntenna.Visible = false;
                                }
                                #endregion
                                break;
                        }
                        #endregion                        

                        #region 控件赋值
                        cbReadMB.Text = myReader.scanMsgParam.readMemoryBank.ToString();//mb
                        byte a = myReader.scanMsgParam.antennaIndex;//antenna
                        if (a > 0x80)
                        {
                            a1.Checked = (((a & 0x01) > 0) ? true : false);
                            a2.Checked = (((a & 0x02) > 0) ? true : false);
                            a3.Checked = (((a & 0x04) > 0) ? true : false);
                            a4.Checked = (((a & 0x08) > 0) ? true : false);
                        }
                        else
                            cbAntenna.SelectedIndex = a;
                        numTag.Value = myReader.scanMsgParam.q;//Q
                        rbSingle.Checked = !(rbLoop.Checked = myReader.scanMsgParam.isLoop);//isLoop
                        numTidLen.Value = myReader.scanMsgParam.tidLen;
                        numUdPtr6C.Value = myReader.scanMsgParam.userdataPtr_6C;
                        numUdLen6C.Value = myReader.scanMsgParam.userdataLen_6C;
                        numUdPtr6B.Value = myReader.scanMsgParam.userdataPtr_6B;
                        numUdLen6B.Value = myReader.scanMsgParam.userdataLen_6B;
                        numReservedLen.Value = myReader.scanMsgParam.reservedLen;
                        txtPwd.Text = Util.ConvertByteArrayToHexString(myReader.scanMsgParam.pwd);
                        numReadTimes6C.Value = myReader.scanMsgParam.readTimes_6C;
                        numReadTimes6B.Value = myReader.scanMsgParam.readTimes_6B;

                        numReadTime.Value = myReader.scanMsgParam.readtime;
                        numStopTime.Value = myReader.scanMsgParam.stoptime;
                        #endregion

                        #region RSSI功能
                        {
                            switch (myReader.reader.ModelNumber)
                            {
                                case "XC-RF807":
                                case "XC-RF806":
                                case "XCRF-860":
                                    cbRSSI.Checked = myReader.IsRssiEnable_800;                                    
                                    break;
                                default:
                                    cbRSSI.Visible = false;
                                    break;
                            }
                        }
                        #endregion

                        #region UTC功能
                        {
                            switch (myReader.reader.ModelNumber)
                            {
                                case "XC-RF807":
                                case "XC-RF806":
                                case "XCRF-860":
                                    cbUTC.Checked = myReader.IsUtcEnable_800;
                                    break;
                                default:
                                    cbUTC.Visible = false;
                                    break;
                            }
                        }
                        #endregion

                        #region 6C读标签配置
                        {
                            Byte[] infoParam = new Byte[5];
                            IRP1.ReadTagConfig_6C msg = new IRP1.ReadTagConfig_6C(0x01, infoParam);
                            if (myReader.reader.Send(msg, 100))
                            {
                                infoParam_6c = new Byte[5];
                                infoParam_6c[0] = msg.ReceivedMessage.InfoParam[0];
                                infoParam_6c[1] = msg.ReceivedMessage.InfoParam[1];
                                infoParam_6c[2] = msg.ReceivedMessage.InfoParam[2];
                                infoParam_6c[3] = msg.ReceivedMessage.InfoParam[3];
                                infoParam_6c[4] = msg.ReceivedMessage.InfoParam[4];
                                checkBox1.Checked = (msg.ReceivedMessage.InfoParam[0] == 0x01) ? true : false;
                                checkBox2.Checked = (msg.ReceivedMessage.InfoParam[1] == 0x01) ? true : false;
                                checkBox3.Checked = (msg.ReceivedMessage.InfoParam[3] == 0x01) ? true : false;
                                numericUpDown5.Value = (decimal)msg.ReceivedMessage.InfoParam[2];
                                numericUpDown4.Value = (decimal)msg.ReceivedMessage.InfoParam[4];
                            }
                            else
                            {
                                groupBox6.Enabled = false;
                            }
                        }
                        #endregion

                        #region 6B读标签配置
                        {
                            IRP1.ReadTagConfig_6B msg = new IRP1.ReadTagConfig_6B(0x01, 0x00);
                            if (myReader.reader.Send(msg, 100))
                            {
                                numericUpDown3.Value = (decimal)(msg.ReceivedMessage.InfoParam / 2);
                                infoParam_6b = (Byte)(msg.ReceivedMessage.InfoParam / 2);
                            }
                            else
                            {
                                groupBox5.Enabled = false;
                            }
                        }
                        #endregion

                        #region 读变长TID配置
                        #region 查询读变长TID开关
                        {
                            IRP1.ReadUnfixedTidConfig_6C msg = new IRP1.ReadUnfixedTidConfig_6C(0x01, 0x00);
                            if (myReader.reader.Send(msg, 100))
                            {
                                if (msg.ReceivedMessage.QueryData[0] == 0x00)
                                {
                                    radioButton2.Checked = true;
                                }
                                else
                                {
                                    radioButton1.Checked = true;
                                    isEnable = true;
                                }
                            }
                            else
                                groupBox1.Enabled = false;
                        }
                        #endregion

                        #region 查询固定TID长度
                        {
                            IRP1.FixedTidLengthConfig_6C msg = new IRP1.FixedTidLengthConfig_6C(0x01, 0x00);
                            if (myReader.reader.Send(msg, 100))
                            {
                                numericUpDown1.Value = (decimal)msg.ReceivedMessage.QueryData[0];
                                tidLen = numericUpDown1.Value;
                            }
                            else
                                groupBox1.Enabled = false;
                        }
                        #endregion
                        #endregion

                        #region 快读TID配置
                        #region 查询快读TID开关
                        {
                            IRP1.FastReadTIDConfig_6C msg = new IRP1.FastReadTIDConfig_6C(0x01, 0x00);
                            if (myReader.reader.Send(msg, 100))
                            {
                                if (msg.ReceivedMessage.QueryData[0] == 0x00)
                                {
                                    radioButton4.Checked = true;
                                }
                                else
                                {
                                    radioButton3.Checked = true;
                                    isFastReadEnable = true;
                                }
                            }
                            else
                                groupBox3.Enabled = false;
                        }
                        #endregion

                        #region 查询快读TID时EPC相对长度
                        {
                            IRP1.FastReadTIDConfig_EpcLength msg = new IRP1.FastReadTIDConfig_EpcLength(0x01, new Byte[] { 0x00, 0x00 });
                            if (myReader.reader.Send(msg, 100))
                            {
                                numericUpDown2.Value = (decimal)(((int)msg.ReceivedMessage.QueryData[0] << 8) + (int)msg.ReceivedMessage.QueryData[0]);
                                epcLen = numericUpDown2.Value;
                            }
                            else
                                groupBox3.Enabled = false;
                        }
                        #endregion
                        #endregion
                    }
                    break;
            }
        }

        // 配置
        private void button2_Click(object sender, EventArgs e)
        {
            String str = "";
            switch (myReader.reader.ProtocolVersion)
            {
                case "IRP1":
                    #region
                    byte antenna = (byte)cbAntenna.SelectedIndex;
                    if (!cbAntenna.Visible)
                    {
                        antenna = 0x80;
                        if (a1.Checked)
                            antenna += 0x01;
                        if (a2.Checked)
                            antenna += 0x02;
                        if (a3.Checked)
                            antenna += 0x04;
                        if (a4.Checked)
                            antenna += 0x08;
                    }
                    string paramAntenna = antenna.ToString();
                    
                    int readtime = (int)numReadTime.Value;
                    int stoptime = (int)numStopTime.Value;
                    IRP1.ReadTag.ReadMemoryBank rmb = (IRP1.ReadTag.ReadMemoryBank)
                        Enum.Parse(typeof(IRP1.ReadTag.ReadMemoryBank), cbReadMB.Text);
                    string stoptype = "500";
                    if (myReader.reader.ModelNumber == "XCRF-860"
                        || myReader.reader.ModelNumber == "XC-RF806"
                        || myReader.reader.ModelNumber == "XC-RF807")
                        stoptype = "800";
                    string param = "";

                    if (antenna < 0x80)
                    {
                        if (stoptype == "500")
                        {
                            byte a = 0x01;
                            switch (antenna)
                            {
                                case 0:
                                    a = 1;
                                    break;
                                case 1:
                                    a = 2;
                                    break;
                                case 2:
                                    a = 0;
                                    break;
                            }
                            antenna = a;
                        }
                        else
                        {
                            byte a = 0x01;
                            switch (antenna)
                            {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                    a = (byte)(antenna + 1);
                                    break;
                                default:
                                    a = 0;
                                    break;
                            }
                            antenna = a;
                        }
                    }
                    #endregion

                    #region 扫描配置
                    #region ScanMessage
                    IRP1.ReadTag rt = null;
                    if (readtime > 0 || stoptime > 0)
                        rt = new IRP1.ReadTag(rmb, readtime, stoptime);
                    else
                        rt = new IRP1.ReadTag(rmb);

                    switch (cbReadMB.Text)
                    {
                        case "EPC_6C":
                        case "EPC_6C_ID_6B":
                        case "EPC_PC_6C":
                        case "EPC_TID_UserData_6C":
                        case "EPC_TID_UserData_6C_ID_UserData_6B":
                        case "TID_6C":
                        case "TID_6C_ID_6B":
                            {
                                rt.Antenna = antenna;
                                rt.Q = (byte)numTag.Value;
                                rt.IsLoop = rbLoop.Checked;
                                param = paramAntenna + "," + numTag.Value + "," + ((rbLoop.Checked) ? "1" : "0");
                            }
                            break;
                        case "EPC_TID_UserData_6C_2":
                            {
                                rt.Antenna = antenna;
                                rt.Q = (byte)numTag.Value;
                                rt.IsLoop = rbLoop.Checked;
                                rt.TidLen = (byte)numTidLen.Value;
                                rt.UserDataPtr_6C = (uint)numUdPtr6C.Value;
                                rt.UserDataLen_6C = (byte)numUdLen6C.Value;
                                param = paramAntenna + "," + numTag.Value + "," + ((rbLoop.Checked) ? "1" : "0")
                                     + "," + numTidLen.Value + "," + numUdPtr6C.Value + "," + numUdLen6C.Value;
                            }
                            break;
                        case "EPC_TID_UserData_Reserved_6C_ID_UserData_6B":
                            {
                                rt.Antenna = antenna;
                                rt.Q = (byte)numTag.Value;
                                rt.IsLoop = rbLoop.Checked;
                                rt.TidLen = (byte)numTidLen.Value;
                                rt.UserDataPtr_6C = (uint)numUdPtr6C.Value;
                                rt.UserDataLen_6C = (byte)numUdLen6C.Value;
                                rt.UserDataPtr_6B = (byte)numUdPtr6B.Value;
                                rt.UserDataLen_6B = (byte)numUdLen6B.Value;
                                rt.ReservedLen = (byte)numReservedLen.Value;
                                rt.AccessPwd = Util.ConvertHexStringToByteArray(txtPwd.Text);
                                rt.ReadTimes_6C = (byte)numReadTimes6C.Value;
                                rt.ReadTimes_6B = (byte)numReadTimes6B.Value;
                                param = paramAntenna + "," + numTag.Value + "," + ((rbLoop.Checked) ? "1" : "0")
                                    + "," + numTidLen.Value + "," + numUdPtr6C.Value + "," + numUdLen6C.Value
                                    + "," + numUdPtr6B.Value + "," + numUdLen6B.Value + "," + numReservedLen.Value
                                    + "," + txtPwd.Text + "," + numReadTimes6C.Value + "," + numReadTimes6B.Value;
                            }
                            break;
                        case "ID_6B":
                        case "ID_UserData_6B":
                            {
                                rt.Antenna = antenna;
                                rt.IsLoop = rbLoop.Checked;
                                param = paramAntenna + "," + ((rbLoop.Checked) ? "1" : "0");
                            }
                            break;
                    }
                    myReader.ScanMessage = rt;
                    #endregion
                    #region StopMessage
                    myReader.StopMessage = new IRP1.PowerOff();                   
                    #endregion
                    #region 保存到Sysit.xml
                    if (myReader.reader.PortType != "TCPIP_Server")
                    {
                        XmlNode node = Common.sysit_xml.DocumentElement.SelectSingleNode("Reader[@Name='" + myReader.reader.ReaderName + "']/ScanMessage");
                        node.Attributes["ReadMemoryBank"].Value = cbReadMB.Text;
                        node.SelectSingleNode("Param").InnerText = param;
                        node.SelectSingleNode("ReadTime").InnerText = numReadTime.Value.ToString();
                        node.SelectSingleNode("StopTime").InnerText = numStopTime.Value.ToString();
                        Common.sysit_xml.Save(APIPath.folderName + "\\Sysit.xml");
                    }
                    #endregion

                    #region 保存到myReader.scanMsgParam
                    myReader.scanMsgParam.readMemoryBank = rmb;
                    myReader.scanMsgParam.antennaIndex = byte.Parse(paramAntenna);
                    myReader.scanMsgParam.q = (byte)numTag.Value;
                    myReader.scanMsgParam.isLoop = rbLoop.Checked;
                    myReader.scanMsgParam.tidLen = (byte)numTidLen.Value;
                    myReader.scanMsgParam.userdataPtr_6C = (uint)numUdPtr6C.Value;
                    myReader.scanMsgParam.userdataLen_6C = (byte)numUdLen6C.Value;
                    myReader.scanMsgParam.userdataPtr_6B = (byte)numUdPtr6B.Value;
                    myReader.scanMsgParam.userdataLen_6B = (byte)numUdLen6B.Value;
                    myReader.scanMsgParam.reservedLen = (byte)numReservedLen.Value;
                    myReader.scanMsgParam.pwd = Util.ConvertHexStringToByteArray(txtPwd.Text);
                    myReader.scanMsgParam.readTimes_6C = (byte)numReadTimes6C.Value;
                    myReader.scanMsgParam.readTimes_6B = (byte)numReadTimes6B.Value;
                    myReader.scanMsgParam.readtime = (int)numReadTime.Value;
                    myReader.scanMsgParam.stoptime = (int)numStopTime.Value;
                    #endregion
                    #endregion

                    #region RSSI配置
                    {
                        switch (myReader.reader.ModelNumber)
                        {
                            case "XC-RF807":
                            case "XC-RF806":
                            case "XCRF-860":
                                Byte[] rssiConfig = new Byte[1];
                                if (cbRSSI.Checked)
                                    rssiConfig[0] = 0x01;//配置RSSI功能
                                IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x14, rssiConfig);//配置RSSI功能
                                if (myReader.reader.Send(order))
                                    myReader.IsRssiEnable_800 = cbRSSI.Checked;
                                break;
                        }
                    }
                    #endregion

                    #region UTC配置
                    {
                        switch (myReader.reader.ModelNumber)
                        {
                            case "XC-RF807":
                            case "XC-RF806":
                            case "XCRF-860":
                                Byte[] utcConfig = new Byte[1];
                                if (cbUTC.Checked)
                                    utcConfig[0] = 0x01;//配置UTC功能
                                IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x18, utcConfig);//配置UTC功能
                                if (myReader.reader.Send(order))
                                    myReader.IsUtcEnable_800 = cbUTC.Checked;
                                break;
                        }
                    }
                    #endregion

                    #region 激活天线
                    if (cbAntenna.Visible)
                    {
                        if (myReader.reader.ModelNumber == "XCRF-860")
                        {
                            byte a = 0x01;
                            switch (cbAntenna.Text)
                            {
                                case "1#":
                                    a = 0x01;
                                    break;
                                case "2#":
                                case "1-2#":
                                    a = 0x02;
                                    break;
                                case "3#":
                                case "1-3#":
                                    a = 0x03;
                                    break;
                                case "4#":
                                case "1-4#":
                                    a = 0x04;
                                    break;
                            }
                            myReader.reader.Send(new IRP1.SysConfig_800(0x02, new Byte[] { a }), 200);
                            
                        }
                        else if (stoptype == "500")
                        {
                            byte a = 0x01;
                            switch (cbAntenna.Text)
                            {
                                case "1#":
                                    a = 0x01;
                                    break;
                                case "2#":                               
                                    a = 0x02;
                                    break;
                                case "1-2#":
                                    a = 0x03;
                                    break;                               
                            }
                            myReader.reader.Send(new IRP1.SysConfig_500(0x02, 0x01, new Byte[] { a }), 200);
                        }                       
                    }
                    #endregion

                    #region 6C读标签配置
                    if (groupBox6.Enabled)
                    {
                        Byte[] infoParam = new Byte[5];
                        infoParam[0] = (Byte)((checkBox1.Checked) ? 1 : 0);
                        infoParam[1] = (Byte)((checkBox2.Checked) ? 1 : 0);
                        infoParam[2] = (Byte)numericUpDown5.Value;
                        infoParam[3] = (Byte)((checkBox3.Checked) ? 1 : 0);
                        infoParam[4] = (Byte)numericUpDown4.Value;

                        bool isSame = true;
                        for (int i = 0; i < 5; i++)
                        {
                            if (infoParam[i] != infoParam_6c[i])
                            {
                                isSame = false;
                                break;
                            }
                        }

                        if (!isSame)
                        {
                            IRP1.ReadTagConfig_6C order = new IRP1.ReadTagConfig_6C(0x00, infoParam);
                            if (myReader.reader.Send(order))
                                infoParam_6c = infoParam;
                            else
                                str = "6C读标签配置失败！\r\n";
                        }
                    }
                    #endregion

                    #region 6B读标签配置
                    if (groupBox5.Enabled && infoParam_6b != (Byte)(numericUpDown3.Value * 2))
                    {
                        IRP1.ReadTagConfig_6B order1 = new IRP1.ReadTagConfig_6B(0x00, (Byte)(numericUpDown3.Value * 2));
                        if (myReader.reader.Send(order1))
                            infoParam_6b = (Byte)(numericUpDown3.Value * 2);
                        else
                            str += "6B读标签配置失败！\r\n";
                    }
                    #endregion

                    #region 读变长TID配置
                    #region 设置读变长TID开关
                    if (groupBox1.Enabled && isEnable != radioButton1.Checked)
                    {
                        Byte infoParam = 0x00;
                        if (radioButton1.Checked)
                            infoParam = 0x01;
                        IRP1.ReadUnfixedTidConfig_6C order = new IRP1.ReadUnfixedTidConfig_6C(0x00, infoParam);
                        if (myReader.reader.Send(order))
                        {
                            isEnable = radioButton1.Checked;
                        }
                        else
                        {
                            str += "设置读变长TID开关失败！\r\n";
                        }

                    }
                    #endregion

                    #region 设置固定TID长度
                    if (groupBox2.Enabled && tidLen != numericUpDown1.Value)
                    {
                        IRP1.FixedTidLengthConfig_6C order = new IRP1.FixedTidLengthConfig_6C(0x00, (Byte)numericUpDown1.Value);
                        if (myReader.reader.Send(order))
                        {
                            tidLen = numericUpDown1.Value;
                        }
                        else
                        {
                            str += "设置固定TID长度失败！\r\n";
                        }
                    }
                    #endregion
                    #endregion

                    #region 快读TID配置
                    #region 设置快读TID开关
                    if (groupBox3.Enabled && isFastReadEnable != radioButton3.Checked)
                    {
                        Byte infoParam = 0x00;
                        if (radioButton3.Checked)
                            infoParam = 0x01;
                        IRP1.FastReadTIDConfig_6C order = new IRP1.FastReadTIDConfig_6C(0x00, infoParam);
                        if (myReader.reader.Send(order))
                        {
                            isFastReadEnable = radioButton3.Checked;
                        }
                        else
                        {
                            str += "设置快读TID开关失败！\r\n";
                        }

                    }
                    #endregion

                    #region 设置快读TID时EPC相对长度
                    if (groupBox4.Enabled && epcLen != numericUpDown2.Value)
                    {
                        Byte[] infoParam = new Byte[2];
                        infoParam[0] = (Byte)((int)numericUpDown2.Value >> 8);
                        infoParam[1] = (Byte)(numericUpDown2.Value % 256);
                        IRP1.FastReadTIDConfig_EpcLength order = new IRP1.FastReadTIDConfig_EpcLength(0x00, infoParam);
                        if (myReader.reader.Send(order))
                        {
                            epcLen = numericUpDown2.Value;
                        }
                        else
                        {
                            str += "设置快读TID时EPC相对长度失败！\r\n";
                        }
                    }
                    #endregion
                    #endregion

                    break;
            }
            MessageBox.Show("配置完成\r\n" + str);
        }

        private void tabControl2_SelectedIndexChanged(object sender, EventArgs e)
        {
            button1_Click(sender, e);
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            groupBox2.Enabled = !radioButton1.Enabled;
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            groupBox2.Enabled = radioButton2.Enabled;
        }

        private void radioButton3_CheckedChanged(object sender, EventArgs e)
        {
            groupBox4.Enabled = radioButton3.Enabled;
        }

        private void radioButton4_CheckedChanged(object sender, EventArgs e)
        {
            groupBox4.Enabled = !radioButton4.Enabled;
        }
        #endregion

        #region 读写器网址
        private String[] ips = new String[3];

        // 查询
        private void button3_Click(object sender, EventArgs e)
        {
            switch (protocol)
            {
                case "IRP1":
                    if (myReader.reader.ModelNumber == "XCRF-502E-F6G"
                        || myReader.reader.ModelNumber == "XCRF-502E"
                        || myReader.reader.ModelNumber == "XC-RF811")
                    {
                        IRP1.SysQuery_500 msg = new IRP1.SysQuery_500(0x00, 0x0c);
                        if (myReader.reader.Send(msg))
                        {
                            getIP(msg.ReceivedMessage.QueryData);
                        }
                        else
                        {
                            MessageBox.Show("查询失败");
                        }
                    }
                    else if (myReader.reader.ModelNumber == "XCRF-860"
                        || myReader.reader.ModelNumber == "XC-RF806"
                        || myReader.reader.ModelNumber == "XC-RF807")
                    {
                        IRP1.SysQuery_800 msg = new IRP1.SysQuery_800(0x06);
                        if (myReader.reader.Send(msg))
                        {
                            getIP(msg.ReceivedMessage.QueryData);
                        }
                        else
                        {
                            MessageBox.Show("查询失败");
                        }
                    }
                    break;
            }
        }

        // IP查询成功
        void getIP(Byte[] data)
        {

            // 整理数据
            String ip = data[0].ToString() + "."
                + data[1].ToString() + "."
                + data[2].ToString() + "."
                + data[3].ToString();
            String subnet = data[4].ToString() + "."
                + data[5].ToString() + "."
                + data[6].ToString() + "."
                + data[7].ToString();
            String gateway = data[8].ToString() + "."
                + data[9].ToString() + "."
                + data[10].ToString() + "."
                + data[11].ToString();
            // 显示
            this.textBox1.Text = ip;
            this.textBox2.Text = subnet;
            this.textBox3.Text = gateway;
            // 设置成员变量
            this.ips[0] = ip;
            this.ips[1] = subnet;
            this.ips[2] = gateway;

        }

        // 设置
        private void button4_Click(object sender, EventArgs e)
        {
            if (!Common.IsIP(textBox1.Text.Trim())
                || !Common.IsIP(textBox1.Text.Trim())
                || !Common.IsIP(textBox1.Text.Trim()))
            {
                MessageBox.Show("IP不合法");
                return;
            }
            if ((ips[0] == textBox1.Text &&
                 ips[1] == textBox2.Text &&
                 ips[2] == textBox3.Text))
            {
                MessageBox.Show("未修改配置");
                return;
            }

            #region 取IP
            Byte[] ipData = new Byte[12];
            int p = 0;

            String[] aryip = textBox1.Text.Split('.');
            foreach (String str in aryip)
            {
                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            aryip = textBox2.Text.Split('.');
            foreach (String str in aryip)
            {
                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            aryip = textBox3.Text.Split('.');
            foreach (String str in aryip)
            {

                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            #endregion

            switch (protocol)
            {
                case "IRP1":
                    if (myReader.reader.ModelNumber == "XCRF-502E-F6G"
                        || myReader.reader.ModelNumber == "XCRF-502E"
                        || myReader.reader.ModelNumber == "XC-RF811")
                    {

                        IRP1.SysConfig_500 order = new IRP1.SysConfig_500(
                            0x00,
                            0x0c,
                            ipData);
                        if (myReader.reader.Send(order))
                        {
                            // 设置成员变量
                            this.ips[0] = this.textBox1.Text;
                            this.ips[1] = this.textBox2.Text;
                            this.ips[2] = this.textBox3.Text;
                            MessageBox.Show("设置成功");
                        }
                        else
                        {
                            MessageBox.Show("设置失败");
                        }

                    }
                    else if (myReader.reader.ModelNumber == "XCRF-860"
                        || myReader.reader.ModelNumber == "XC-RF806"
                        || myReader.reader.ModelNumber == "XC-RF807")
                    {
                        IRP1.SysConfig_800 order = new IRP1.SysConfig_800(
                            0x06,                           
                            ipData);
                        if (myReader.reader.Send(order))
                        {
                            // 设置成员变量
                            this.ips[0] = this.textBox1.Text;
                            this.ips[1] = this.textBox2.Text;
                            this.ips[2] = this.textBox3.Text;
                            MessageBox.Show("设置成功");
                        }
                        else
                        {
                            MessageBox.Show("设置失败");
                        }
                    }
                    break;
            }
        }
        #endregion

        #region 标签过滤
        // 标签选择匹配
        private void button5_Click(object sender, EventArgs e)
        {

            MemoryBank matchingArea = (MemoryBank)(cbData.SelectedIndex + 1);
            Byte[] tagID = Util.ConvertHexStringToByteArray(this.txtData.Text.Trim());

            IRP1.SelectTag_6C msg = new IRP1.SelectTag_6C(
                matchingArea,
                (Byte)(num_StartAdd.Value),
                (Byte)(tagID.Length * 8),
                tagID);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败");
        }
        // EPC_6C过滤设置
        private void button6_Click(object sender, EventArgs e)
        {
            Byte[] tagID = Util.ConvertHexStringToByteArray(this.txtTID.Text.Trim());
            Byte[] tagMask = Util.ConvertHexStringToByteArray(this.textBox4.Text.Trim());
            IRP1.EpcFilter_6C msg = new IRP1.EpcFilter_6C(0x00, tagID, tagMask);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败");
        }
        // EPC_6C过滤取消
        private void button8_Click(object sender, EventArgs e)
        {
            IRP1.EpcFilter_6C msg = new IRP1.EpcFilter_6C(0x01, null, null);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败");
        }

        // ID_6B过滤
        private void button7_Click(object sender, EventArgs e)
        {
            if (this.textBox6.Text.Trim().Length != 16)
            {
                MessageBox.Show("数据应为8字节");
                return;
            }
            if (this.textBox5.Text.Trim().Length != 16)
            {
                this.textBox5.Text = this.textBox5.Text.PadRight(16, 'f');               
            }
            Byte[] tagID = Util.ConvertHexStringToByteArray(this.textBox6.Text.Trim());
            Byte[] tagMask = Util.ConvertHexStringToByteArray(this.textBox5.Text.Trim());
            
            IRP1.IDFilter_6B msg = new IRP1.IDFilter_6B(tagID, tagMask);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败");
        }
        #endregion

        #region 天线配置
       
        Double[] antennaPowers = new Double[4];
        IRP1.FrequencyTable ft = null;

        private void button9_Click(object sender, EventArgs e)
        {
            if (protocol == "IRP1")
            {
                if (myReader.reader.ModelNumber == "XCRF-860"
                        || myReader.reader.ModelNumber == "XC-RF806"
                        || myReader.reader.ModelNumber == "XC-RF807")
                {
                    #region 配置天线端口功率
                    String strSuc = "";
                    String strFai = "";
                    {
                        Byte[] aData = new Byte[2];
                        aData[0] = 0x00;
                        for (int i = 0; i < list.Length; i++)
                        {
                            if (list[i].ToString() == ant1.Text)
                                aData[1] = (Byte)i;
                        }

                        IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                        if (myReader.reader.Send(order))
                            strSuc = "1#,";
                        else
                            strFai = "1#,";
                    }
                    {
                        Byte[] aData = new Byte[2];
                        aData[0] = 0x01;
                        for (int i = 0; i < list.Length; i++)
                        {
                            if (list[i].ToString() == ant2.Text)
                                aData[1] = (Byte)i;
                        }

                        IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                        if (myReader.reader.Send(order))
                            strSuc += "2#,";
                        else
                            strFai += "2#,";
                    }
                    {
                        Byte[] aData = new Byte[2];
                        aData[0] = 0x02;
                        for (int i = 0; i < list.Length; i++)
                        {
                            if (list[i].ToString() == ant3.Text)
                                aData[1] = (Byte)i;
                        }

                        IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                        if (myReader.reader.Send(order))
                            strSuc += "3#,";
                        else
                            strFai += "3#,";
                    }

                    {
                        Byte[] aData = new Byte[2];
                        aData[0] = 0x03;
                        for (int i = 0; i < list.Length; i++)
                        {
                            if (list[i].ToString() == ant4.Text)
                                aData[1] = (Byte)i;
                        }

                        IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                        if (myReader.reader.Send(order))
                            strSuc += "4#";
                        else
                            strFai += "4#";
                    }
                    if (strSuc != "")
                        strSuc = strSuc.TrimEnd(',') + "设置成功";
                    if (strFai != "")
                        strFai = strFai.TrimEnd(',') + "设置失败";
                    MessageBox.Show(strSuc + "\r\n" + strFai);
                    #endregion
                }
            }
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            lblValue.Text = trackBar1.Value.ToString();
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (protocol == "IRP1")
            {
                if (myReader.reader.ModelNumber == "XCRF-502E-F6G"                       
                       || myReader.reader.ModelNumber == "XC-RF811")
                {
                    Byte[] param = new Byte[2];
                    param[0] = (Byte)(trackBar1.Value / 256);
                    param[1] = (Byte)(trackBar1.Value % 256);
                    IRP1.PowerParamConfig_500 order = new IRP1.PowerParamConfig_500(0x00, param);//设置
                    if (myReader.reader.Send(order))
                        MessageBox.Show("成功");
                    else
                        MessageBox.Show("失败");
                }
            }
        }
      
        #endregion

        private void cbReadMB_SelectedIndexChanged(object sender, EventArgs e)
        {
            foreach (Control ctrl in gbScanConfig.Controls)
            {
                ctrl.Enabled = true;
            }
            #region 控件显示
            switch (cbReadMB.Text)
            {
                case "EPC_6C":
                case "EPC_6C_ID_6B":
                case "EPC_PC_6C":
                case "EPC_TID_UserData_6C":
                case "EPC_TID_UserData_6C_ID_UserData_6B":
                case "TID_6C":
                case "TID_6C_ID_6B":
                    {
                        gb6C.Enabled = false;
                        gb6B.Enabled = false;
                    }
                    break;
                case "EPC_TID_UserData_6C_2":
                    {
                        if (myReader.reader.ModelNumber != "XCRF-502E-F6G" && myReader.reader.ModelNumber != "XC-RF811")
                        {
                            numReadTimes6C.Enabled = false;
                            numReservedLen.Enabled = false;
                            txtPwd.Enabled = false;
                        }
                        else
                            gb6C.Enabled = false;
                        gb6B.Enabled = false;
                    }
                    break;
                case "EPC_TID_UserData_Reserved_6C_ID_UserData_6B":
                    {
                        
                    }
                    break;
                case "ID_6B":
                case "ID_UserData_6B":
                    {
                        numTag.Enabled = false;

                        gb6C.Enabled = false;
                        gb6B.Enabled = false;
                    }
                    break;

            }
            #endregion                        
        }
    }
}
