using System;
using System.IO.Ports;
using System.Windows.Forms;
using System.Xml;
using Invengo.NetAPI.Core;

namespace ReadersTest
{
    public partial class SysitConfigForm : Form
    {
        public String readerName = "";
        public String serverPort = "";            

        public SysitConfigForm()
        {
            InitializeComponent();            
        }

        private void SysitConfigForm_Load(object sender, EventArgs e)
        {
            #region 填充cbGroup
            XmlNode root = Common.sysit_xml.DocumentElement;
            XmlNodeList readers = root.SelectNodes("Reader");
            foreach (XmlNode node in readers)
            {
                String gn = node.Attributes["Group"].Value;
                bool isExist = false;
                if (cbGroup.Items.Count > 0)
                {
                    for (int i = 0; i < cbGroup.Items.Count; i++)
                    {
                        if (gn == cbGroup.Items[i].ToString())
                        {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist)
                        continue;
                }
                cbGroup.Items.Add(gn);
            }
            #endregion

            #region 填充cbComPort
            String[] ps = SerialPort.GetPortNames();
            foreach (String p in ps)
                cbComPort.Items.Add(p);
            if (cbComPort.Items.Count > 0)
                cbComPort.SelectedIndex = 0;
            #endregion

            cbComPort.SelectedIndex = 0;
            cbBaudRate.SelectedIndex = 2;
            
            cbMN.SelectedIndex = 0;
            rbTcpClient.Checked = true;
        }

        private void cbMN_SelectedIndexChanged(object sender, EventArgs e)
        {
            rbTcpClient.Checked = true;
            switch (cbMN.Text)
            {
                case "unknown":
                    #region 连接方式
                    {
                        rbUsb.Visible = true;
                        rbUsb.Enabled = true;

                        rbTcpServer.Visible = true;
                        rbTcpServer.Enabled = true;
                        pTcpServer.Visible = true;
                        pTcpServer.Enabled = false;
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {                            
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("EPC_6C");
                            cbReadTag.Items.Add("TID_6C");
                            cbReadTag.Items.Add("EPC_TID_UserData_6C");
                            cbReadTag.Items.Add("EPC_TID_UserData_6C_2");
                            cbReadTag.Items.Add("ID_6B");
                            cbReadTag.Items.Add("ID_UserData_6B");
                            cbReadTag.Items.Add("EPC_6C_ID_6B");
                            cbReadTag.Items.Add("TID_6C_ID_6B");
                            cbReadTag.Items.Add("EPC_TID_UserData_6C_ID_UserData_6B");
                            cbReadTag.Items.Add("EPC_TID_UserData_Reserved_6C_ID_UserData_6B");
                            cbReadTag.Items.Add("EPC_PC_6C");
                            cbReadTag.SelectedIndex = 0;
                            cbAntenna.Visible = true;
                            cbAntenna.Items.Clear();
                            cbAntenna.Items.Add("1#");
                            cbAntenna.Items.Add("2#");
                            cbAntenna.Items.Add("3#");
                            cbAntenna.Items.Add("4#");
                            cbAntenna.Items.Add("1-2#");
                            cbAntenna.Items.Add("1-3#");
                            cbAntenna.Items.Add("1-4#");
                            cbAntenna.SelectedIndex = 0;
                            pAntenna.Visible = false;
                            numTag.Enabled = true;
                        }
                        #endregion
                    }
                    #endregion
                    break;
                case "XCRF-860":
                    #region 连接方式
                    {
                        rbUsb.Visible = true;
                        rbUsb.Enabled = true;

                        rbTcpServer.Visible = false;
                        pTcpServer.Visible = false;
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {
                           
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("EPC_6C");
                            cbReadTag.Items.Add("TID_6C");
                            cbReadTag.Items.Add("EPC_TID_UserData_6C_2");
                            cbAntenna.Items.Clear();
                            cbAntenna.Items.Add("1#");
                            cbAntenna.Items.Add("2#");
                            cbAntenna.Items.Add("3#");
                            cbAntenna.Items.Add("4#");
                            cbAntenna.Items.Add("1-2#");
                            cbAntenna.Items.Add("1-3#");
                            cbAntenna.Items.Add("1-4#");
                            cbAntenna.SelectedIndex = 0;
                            cbReadTag.SelectedIndex = 0;
                            cbAntenna.Visible = true;
                            pAntenna.Visible = false;
                            numTag.Enabled = true;
                        }
                        #endregion
                    }
                    #endregion
                    break;
                case "XC-RF806":
                    #region 连接方式
                    {
                        rbUsb.Visible = false; 
                        rbTcpServer.Visible = false;
                        pTcpServer.Visible = false;             
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {
                           
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("EPC_6C");
                            cbReadTag.Items.Add("TID_6C");                            
                            cbReadTag.Items.Add("EPC_TID_UserData_6C_2");
                            cbReadTag.SelectedIndex = 0;
                            cbAntenna.Visible = false;
                            pAntenna.Visible = true;
                            numTag.Enabled = true;
                        }
                        #endregion
                    }
                    #endregion
                    break;
                case "XC-RF807":
                    #region 连接方式
                    {
                        rbUsb.Visible = false;
                        rbTcpServer.Visible = false;
                        pTcpServer.Visible = false;  
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {
                           
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("EPC_6C");
                            cbReadTag.Items.Add("TID_6C");                            
                            cbReadTag.Items.Add("EPC_TID_UserData_6C_2");
                            cbReadTag.Items.Add("ID_6B");
                            cbReadTag.Items.Add("EPC_TID_UserData_Reserved_6C_ID_UserData_6B");
                            cbReadTag.SelectedIndex = 0;
                            cbAntenna.Visible = false;
                            pAntenna.Visible = true;
                            numTag.Enabled = true;
                        }
                        #endregion
                    }
                    #endregion
                    break;
                case "XCRF-502E":
                    #region 连接方式
                    {
                        rbUsb.Visible = false;
                        rbTcpServer.Visible = false;
                        pTcpServer.Visible = false;  
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {
                           
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("ID_6B");
                            cbReadTag.SelectedIndex = 0;
                            cbAntenna.Visible = true;
                            cbAntenna.Items.Clear();
                            cbAntenna.Items.Add("1#");
                            cbAntenna.Items.Add("2#");
                            cbAntenna.Items.Add("1-2#");
                            cbAntenna.SelectedIndex = 0;
                            pAntenna.Visible = false;
                            numTag.Enabled = false;
                        }
                        #endregion
                    }
                    #endregion
                    break;
                case "XC-RF811":
                    #region 连接方式
                    {
                        rbUsb.Visible = false;
                        rbTcpServer.Visible = false;
                        pTcpServer.Visible = false;  
                    }
                    #endregion
                    #region Protocol
                    {
                        #region IRP1
                        {
                            
                            cbReadTag.Items.Clear();
                            cbReadTag.Items.Add("EPC_6C");
                            cbReadTag.Items.Add("TID_6C");                            
                            cbReadTag.Items.Add("ID_6B");
                            cbReadTag.SelectedIndex = 0;
                            
                            cbAntenna.Items.Clear();
                            cbAntenna.Items.Add("1#");
                            cbAntenna.SelectedIndex = 0;
                            pAntenna.Visible = false;
                            numTag.Enabled = true;
                        }
                        #endregion
                    }
                    #endregion
                    break;
            }
        }

        private void rbConn_CheckedChanged(object sender, EventArgs e)
        {            
            RadioButton rb = sender as RadioButton;
            if (rb.Checked)
            {
                txtReader.Enabled = true;
                cbGroup.Enabled = true;
                pIRP1.Enabled = true;
                switch (rb.Name)
                {
                    case "rbTcpClient":
                        pTcpClient.Enabled = true;
                        pRS232.Enabled = false;
                        pTcpServer.Enabled = false;
                        break;
                    case "rbRs232":
                        pTcpClient.Enabled = false;
                        pRS232.Enabled = true;
                        pTcpServer.Enabled = false;
                        break;
                    case "rbUsb":
                        pTcpClient.Enabled = false;
                        pRS232.Enabled = false;
                        pTcpServer.Enabled = false;
                        break;
                    case "rbTcpServer":
                        pTcpClient.Enabled = false;
                        pRS232.Enabled = false;
                        pTcpServer.Enabled = true;
                        txtReader.Enabled = false;
                        cbGroup.Enabled = false;
                        pIRP1.Enabled = false;                        
                        cbReadTag.SelectedIndex = 0;
                        cbAntenna.SelectedIndex = 0;
                        break;
                }
            }
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            if (txtIP.Enabled && (txtIP.Text.Trim() == "" || !Common.IsIP(txtIP.Text.Trim())))
            {
                MessageBox.Show("IP不合法");
                return;
            }
            if (txtReader.Enabled && txtReader.Text.Trim() == "")
            {
                MessageBox.Show("读写器名不能空");
                return;
            }
            if (cbGroup.Enabled && cbGroup.Text.Trim() == "")
            {
                MessageBox.Show("组名不能空");
                return;
            }

            XmlNode root = Common.sysit_xml.DocumentElement;
            if (rbTcpServer.Checked)
            {
                XmlNode node = root.SelectSingleNode("Server[@Port='" + numSerPort.Value + "']");
                if (node == null)
                {
                    addServerNode(root);
                }
                else
                {
                    MessageBox.Show("该节点已经存在");
                    return;
                }
                serverPort = numSerPort.Value.ToString();
            }
            else
            {
                XmlNode node = root.SelectSingleNode("Reader[@Name='" + txtReader.Text.Trim() + "']");
                if (node == null)
                {
                    addNode(root);
                }
                else
                {
                    MessageBox.Show("该节点已经存在");
                    return;
                }
                readerName = cbGroup.Text + "," + txtReader.Text.Trim();
            }

            this.Close();
        }

        private void btnCancle_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        #region 辅助方法
        private void addNode(XmlNode root)
        {
            XmlNode node = Common.sysit_xml.CreateElement("Reader");
            root.AppendChild(node);
            #region Reader节点属性
            {
                XmlAttribute a1 = Common.sysit_xml.CreateAttribute("Name");
                a1.Value = txtReader.Text.Trim();
                node.Attributes.Append(a1);

                XmlAttribute a2 = Common.sysit_xml.CreateAttribute("Group");
                a2.Value = cbGroup.Text.Trim();
                node.Attributes.Append(a2);

                XmlAttribute a3 = Common.sysit_xml.CreateAttribute("Enable");
                a3.Value = "true";
                node.Attributes.Append(a3);
            }
            #endregion

            XmlNode port = Common.sysit_xml.CreateElement("Port");
            #region Port节点
            {
                String type = "";
                String connStr = "";
                getConnStr(out type, out connStr);

                XmlAttribute a1 = Common.sysit_xml.CreateAttribute("Type");
                a1.Value = type;
                port.Attributes.Append(a1);

                XmlAttribute a2 = Common.sysit_xml.CreateAttribute("Protocol");
                a2.Value = tabControl2.SelectedTab.Text;
                port.Attributes.Append(a2);

                port.InnerXml = connStr;

                node.AppendChild(port);
            }
            #endregion

            #region ReadTag
            if (tabControl2.SelectedTab.Text == "IRP1")
            {
                XmlNode readTag = Common.sysit_xml.CreateElement("ScanMessage");
                node.AppendChild(readTag);
                XmlAttribute a1 = Common.sysit_xml.CreateAttribute("ReadMemoryBank");
                a1.Value = cbReadTag.Text;
                readTag.Attributes.Append(a1);                

                XmlNode param = Common.sysit_xml.CreateElement("Param");
                string pStr = "";
                switch (cbReadTag.Text)
                {
                    case "EPC_6C":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "TID_6C":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "EPC_TID_UserData_6C":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "EPC_TID_UserData_6C_2":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop() + ","
                            + "4,0,4";//tidlen,userdata ptr,userdatalen
                        break;
                    case "ID_6B":
                        pStr = getAntenna() + "," + getIsLoop();
                        break;
                    case "ID_UserData_6B":
                        pStr = getAntenna() + "," + getIsLoop();
                        break;
                    case "EPC_6C_ID_6B":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "TID_6C_ID_6B":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "EPC_TID_UserData_6C_ID_UserData_6B":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                    case "EPC_TID_UserData_Reserved_6C_ID_UserData_6B":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop() + ","
                            + "4,0,4,0,4,0,00000000,1,1";//tidlen,6c userdata ptr,6c userdatalen,6b ud ptr,6b udlen,reservedlen,pwd,6c times,6b times
                        break;
                    case "EPC_PC_6C":
                        pStr = getAntenna() + "," + numTag.Value + "," + getIsLoop();
                        break;
                }
                param.InnerText = pStr;
                readTag.AppendChild(param);

                XmlNode readTime = Common.sysit_xml.CreateElement("ReadTime");
                readTime.InnerText = numReadTime.Value.ToString();
                readTag.AppendChild(readTime);

                XmlNode stopTime = Common.sysit_xml.CreateElement("StopTime");
                stopTime.InnerText = numStopTime.Value.ToString();
                readTag.AppendChild(stopTime);
            }
            #endregion


            Common.sysit_xml.Save(APIPath.folderName + "\\Sysit.xml");
        }

        private string getIsLoop()
        {
            return ((rbLoop.Checked) ? "1" : "0");
        }

        private string getAntenna()
        {
            if (cbAntenna.Visible)
            {
                return cbAntenna.SelectedIndex.ToString();
            }
            else
            {
                byte a = 0x80;
                if (a1.Checked)
                    a += 0x01;
                if (a2.Checked)
                    a += 0x02;
                if (a3.Checked)
                    a += 0x04;
                if (a4.Checked)
                    a += 0x08;
                return a.ToString();
            }
        }

        private void addServerNode(XmlNode root)
        {
            XmlNode node = Common.sysit_xml.CreateElement("Server");
            root.AppendChild(node);
            #region Reader节点属性
            {
                XmlAttribute a1 = Common.sysit_xml.CreateAttribute("Port");
                a1.Value = numSerPort.Value.ToString();
                node.Attributes.Append(a1);

                XmlAttribute a2 = Common.sysit_xml.CreateAttribute("Protocol");
                a2.Value = tabControl2.SelectedTab.Text;
                node.Attributes.Append(a2);

                XmlAttribute a3 = Common.sysit_xml.CreateAttribute("Enable");
                a3.Value = "true";
                node.Attributes.Append(a3);                
            }
            #endregion            

            Common.sysit_xml.Save(APIPath.folderName + "\\Sysit.xml");           
        }

        private void getConnStr(out String type,out String connstr)
        {
            connstr = "192.168.0.210:7086";
            type = "TCPIP_Client";
            
            if (rbTcpClient.Checked)
            {
                connstr = txtIP.Text + ":" + numIpPort.Value;
                type = "TCPIP_Client";
            }
            if (rbRs232.Checked)
            {
                connstr = cbComPort.Text + "," + cbBaudRate.Text;
                type = "RS232";
            }
            if (rbUsb.Checked)
            {
                connstr = @"\\?\usb#vid_8086&amp;pid_feed#serialnum01#{48c602d4-c77e-45b9-8133-20c9683bd1a6}";
                type = "USB";
            }
            if (rbTcpServer.Checked)
            {
                connstr = "";
                type = "TCPIP_Server";
            }
        }
        #endregion
    }
}
