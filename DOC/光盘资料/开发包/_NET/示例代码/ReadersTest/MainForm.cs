using System;
using System.Data;
using System.Drawing;
using System.Windows.Forms;
using System.Xml;
using Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;

namespace ReadersTest
{
    public partial class MainForm : Form
    {
        ImageList myImageList = new ImageList();
        volatile int totalCount = 0;
        System.Timers.Timer myStatTimer = new System.Timers.Timer(1000);
        DateTime dateTime;

        public MainForm()
        {
            InitializeComponent();

            #region treeview
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.root_1);  //0
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.group_1); //1
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.group_2); //2
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.group_3); //3
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.reader_1);//4
            myImageList.Images.Add((Image)ReadersTest.Properties.Resources.reader_2);//5        
            tvReaders.ImageList = myImageList;
            #endregion

            this.dgvScanData.DataSource = Common.Dt_Display;
            myStatTimer.Elapsed += new System.Timers.ElapsedEventHandler(myStatTimer_Elapsed);
        }

        #region 窗体控件
        private void MainForm_Load(object sender, EventArgs e)
        {
            int x = 42;
            Console.WriteLine(x++);
            XmlNodeList clientList = Common.sysit_xml.DocumentElement.SelectNodes("Reader");
            XmlNodeList serverList = Common.sysit_xml.DocumentElement.SelectNodes("Server");
            #region Client
            if (clientList.Count > 0)
            {
                TreeNode clientNode = tvReaders.Nodes.Add("Client", "Client");
                clientNode.ImageIndex = 0;
                TreeNode group = null;

                foreach (XmlNode n in clientList)
                {
                    string gn = n.Attributes["Group"].Value;
                    TreeNode[] gs = clientNode.Nodes.Find(gn, true);
                    if (gs == null || gs.Length == 0)
                    {
                        group = clientNode.Nodes.Add(gn, gn);
                        group.ImageIndex = 3;
                    }
                    else
                        group = gs[0];
                    string rn = n.Attributes["Name"].Value;
                    TreeNode r = group.Nodes.Add(rn, rn);
                    r.ImageIndex = 4;
                }
            }
            #endregion
            #region Server
            if (serverList.Count > 0)
            {
                TreeNode serverNode = tvReaders.Nodes.Add("Server", "Server");
                serverNode.ImageIndex = 0;
                foreach (XmlNode n in serverList)
                {
                    string gn = "Port:" + n.Attributes["Port"].Value;
                    TreeNode g = serverNode.Nodes.Add(gn, gn);
                    g.ImageIndex = 1;
                }
            }
            #endregion
            tvReaders.ExpandAll();

            IRP1.Reader.OnApiException += new ApiExceptionHandle(Reader_OnApiException);
            IRP1.Reader.OnErrorMessage += new ErrorMessageHandle(Reader_OnErrorMessage);
            Common.myReaders.OnReaderClientConn += new MyReaders.OnReaderClientConnHandle(myReaders_OnReaderClientConn);
        }

        private void tvReaders_AfterSelect(object sender, TreeViewEventArgs e)
        {            
            tvReaders.SelectedNode.SelectedImageIndex = e.Node.ImageIndex;
        }

        private void tvReaders_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (e.Node != null && e.Node.ImageIndex == 5)
            {
                if (btnScan.Enabled)
                {
                    ReaderConfigForm frm = new ReaderConfigForm(Common.myReaders.Find(e.Node.Name));
                    frm.ShowDialog();
                }
                else
                    MessageBox.Show("扫描过程中不允许其他操作");
            }
        }

        private void btnAddReader_Click(object sender, EventArgs e)
        {
            SysitConfigForm frm = new SysitConfigForm();
            frm.ShowDialog();

            #region Client
            if (frm.readerName != "")
            {
                String[] infos = frm.readerName.Split(',');

                TreeNode[] tns1 = tvReaders.Nodes.Find("Client", true);
                TreeNode c = null;
                TreeNode g = null;
                if (tns1.Length > 0)
                    c = tns1[0];
                else
                {
                    c = tvReaders.Nodes.Add("Client", "Client");
                    c.ImageIndex = 0;
                }

                TreeNode[] tns = c.Nodes.Find(infos[0], true);
                if (tns.Length > 0)
                    g = tns[0];
                else
                {
                    g = c.Nodes.Add(infos[0], infos[0]);
                    g.ImageIndex = 3;
                }

                TreeNode r = g.Nodes.Add(infos[1], infos[1]);
                r.ImageIndex = 4;
                if (!r.Parent.IsExpanded)
                    r.Parent.Expand();
                if (!r.Parent.Parent.IsExpanded)
                    r.Parent.Parent.Expand();
            }
            #endregion

            #region Server
            if (frm.serverPort != "")
            {
                TreeNode[] tns1 = tvReaders.Nodes.Find("Server", false);
                TreeNode s = null;
                if (tns1.Length == 0)
                {
                    s = tvReaders.Nodes.Add("Server", "Server");
                    s.ImageIndex = 0;
                }
                else
                    s = tns1[0];
                String nodeName = "Port:" + frm.serverPort;
                TreeNode n = s.Nodes.Add(nodeName, nodeName);
                n.ImageIndex = 1;
                if (!n.Parent.IsExpanded)
                    n.Parent.Expand();
            }
            #endregion
        }

        private void btnRemoveReader_Click(object sender, EventArgs e)
        {
            TreeNode node = tvReaders.SelectedNode;
            if (node != null)
            {
                if (node.Name == "Client")
                {
                    foreach (XmlNode n in Common.sysit_xml.DocumentElement.SelectNodes("Reader"))
                    {
                        Common.sysit_xml.DocumentElement.RemoveChild(n);
                    }
                }
                else if (node.Name == "Server")
                {
                    foreach (XmlNode n in Common.sysit_xml.DocumentElement.SelectNodes("Server"))
                    {
                        Common.sysit_xml.DocumentElement.RemoveChild(n);
                    }
                }
                else if (node.Name.IndexOf("Port:") != -1)
                {
                    foreach (XmlNode n in Common.sysit_xml.DocumentElement.SelectNodes("Server"))
                    {
                        if (n.Attributes["Port"].Value == node.Name.Substring(5))
                        {
                            Common.sysit_xml.DocumentElement.RemoveChild(n);
                            break;
                        }
                    }
                }
                else if (node.Parent.Name == "Client")
                {
                    foreach (XmlNode n in Common.sysit_xml.DocumentElement.SelectNodes("Reader[@Group='" + node.Name + "']"))
                    {
                        Common.sysit_xml.DocumentElement.RemoveChild(n);
                    }
                }
                else
                {
                    Common.sysit_xml.DocumentElement.RemoveChild(
                        Common.sysit_xml.DocumentElement.SelectSingleNode("Reader[@Name='" + node.Name + "']"));
                }

                Common.sysit_xml.Save(APIPath.folderName + "\\Sysit.xml");
                tvReaders.Nodes.Remove(tvReaders.SelectedNode);
            }
        }

        private void btnConn_Click(object sender, EventArgs e)
        { 
            #region
            btnAddReader.Enabled = false;
            btnRemoveReader.Enabled = false;
            btnConn.Enabled = false;
            btnDisconn.Enabled = true;
            btnEasScan.Enabled = true;
            btnScan.Enabled = true;
            btnStop.Enabled = true;
            btnTagAccess.Enabled = true;
            #endregion           

            Common.myReaders.OnReaderNotificationMsg += 
                new MyReaders.OnReaderNotificationMsgHandle(myReaders_OnReaderNotificationMsg);
            Common.myReaders.OnReaderErrorMsg += 
                new MyReaders.OnReaderErrorMsgHandle(myReaders_OnReaderErrorMsg);
            Common.myReaders.OnTagAlarm += new MyReaders.OnTagAlarmHandle(myReaders_OnTagAlarm);            
            
            Common.myReaders.Connect();

            foreach (MyReader mr in Common.myReaders.ReaderList)
            {
                if (mr.reader.IsConnected)
                    tvReaders.Nodes.Find(mr.reader.ReaderName, true)[0].ImageIndex = 5;
            }
            foreach (TcpIpListener sl in Common.myReaders.ServerList)
            {
                tvReaders.Nodes.Find("Port:" + sl.Port.ToString(), true)[0].ImageIndex = 2;                
            }

            if (tvReaders.SelectedNode != null)
            {
                tvReaders.SelectedNode.SelectedImageIndex = tvReaders.SelectedNode.ImageIndex;
            }
        }

        

            

        private void btnDisconn_Click(object sender, EventArgs e)
        {
            #region
            btnAddReader.Enabled = true;
            btnRemoveReader.Enabled = true;
            btnConn.Enabled = true;
            btnDisconn.Enabled = false;
            btnEasScan.Enabled = false;
            btnScan.Enabled = false;
            btnStop.Enabled = false;
            btnTagAccess.Enabled = false;
            #endregion      

            foreach (MyReader mr in Common.myReaders.ReaderList)
            {
                TreeNode[] trs = tvReaders.Nodes.Find(mr.reader.ReaderName, true);
                if (trs.Length > 0)
                {
                    if (mr.reader.IsConnected)
                        trs[0].ImageIndex = 4;
                    if (trs[0].Parent.Parent.Name == "Server")
                        tvReaders.Nodes.Remove(trs[0]);
                }
            }
            foreach (TcpIpListener sl in Common.myReaders.ServerList)
            {
                tvReaders.Nodes.Find("Port:" + sl.Port.ToString(), true)[0].ImageIndex = 1;
            }
            if (tvReaders.SelectedNode != null)
            {
                tvReaders.SelectedNode.SelectedImageIndex = tvReaders.SelectedNode.ImageIndex;
            }

            Common.myReaders.Disconnect();

            Common.myReaders.OnReaderNotificationMsg -=
                new MyReaders.OnReaderNotificationMsgHandle(myReaders_OnReaderNotificationMsg);
            Common.myReaders.OnReaderErrorMsg -=
                new MyReaders.OnReaderErrorMsgHandle(myReaders_OnReaderErrorMsg);
            Common.myReaders.OnTagAlarm -= new MyReaders.OnTagAlarmHandle(myReaders_OnTagAlarm);
            
        }        

        private void btnEasScan_Click(object sender, EventArgs e)
        {
            #region
            btnAddReader.Enabled = false;
            btnRemoveReader.Enabled = false;
            btnConn.Enabled = false;
            btnDisconn.Enabled = false;
            btnEasScan.Enabled = false;
            btnScan.Enabled = false;
            btnStop.Enabled = true;
            btnTagAccess.Enabled = false;
            #endregion
            Common.myReaders.EasScan();
        }

        private void btnScan_Click(object sender, EventArgs e)
        {
            #region
            btnAddReader.Enabled = false;
            btnRemoveReader.Enabled = false;
            btnConn.Enabled = false;
            btnDisconn.Enabled = false;
            btnEasScan.Enabled = false;
            btnScan.Enabled = false;
            btnStop.Enabled = true;
            btnTagAccess.Enabled = false;
            #endregion
            totalCount = 0;
            dateTime = DateTime.Now;
            myStatTimer.Enabled = true;
            Common.myReaders.Scan();
        }

        private void btnStop_Click(object sender, EventArgs e)
        {
            #region
            btnAddReader.Enabled = false;
            btnRemoveReader.Enabled = false;
            btnConn.Enabled = false;
            btnDisconn.Enabled = true;
            btnEasScan.Enabled = true;
            btnScan.Enabled = true;
            btnStop.Enabled = true;
            btnTagAccess.Enabled = true;
            #endregion
            Common.myReaders.Stop();
            myStatTimer.Enabled = false;
        }

        private void btnTagAccess_Click(object sender, EventArgs e)
        {
            if (dgvScanData.CurrentRow != null)
            {
                MyReader mr = Common.myReaders.Find(dgvScanData.CurrentRow.Cells[0].Value.ToString());
                if (!btnScan.Enabled)
                {
                    MessageBox.Show("读卡时不能进行标签操作");
                    return;
                }
                byte antenna = 0x01;
                int aCount = (int)dgvScanData.CurrentRow.Cells[8].Value;
                for (int i = 9; i < 12; i++)
                {
                    int count = (int)dgvScanData.CurrentRow.Cells[i].Value;
                    if (count > aCount)
                    {
                        aCount = count;
                        antenna = (byte)(i - 7);
                    }
                }
                string tagid = dgvScanData.CurrentRow.Cells[2].Value.ToString();                
                string tagtype = dgvScanData.CurrentRow.Cells[1].Value.ToString();
                MemoryBank mb = MemoryBank.EPCMemory;
                if (tagid == "")
                { 
                    tagid = dgvScanData.CurrentRow.Cells[3].Value.ToString(); 
                    mb = MemoryBank.TIDMemory; 
                }
                TagAccessForm frm = new TagAccessForm(
                    mr,
                    antenna,
                    tagid,
                    tagtype,
                    mb);
                frm.ShowDialog();
            }          
        }

        private void btnClear_Click(object sender, EventArgs e)
        {
            Common.Dt_Display.Rows.Clear();
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            IRP1.Reader.OnApiException -= new ApiExceptionHandle(Reader_OnApiException);
            IRP1.Reader.OnErrorMessage -= new ErrorMessageHandle(Reader_OnErrorMessage);
            Common.myReaders.OnReaderClientConn -= new MyReaders.OnReaderClientConnHandle(myReaders_OnReaderClientConn);
            Environment.Exit(Environment.ExitCode);
        }
        #endregion

        #region 数据处理
        void Reader_OnErrorMessage(IMessageNotification e)
        {
            if (e.StatusCode != 0 && e.StatusCode != -2)
            {
                Common.AppendText(this, txtMsg, e.ErrInfo + "\r\n");
                Log.Debug(e.ErrInfo);
                return;
            }
        }

        void Reader_OnApiException(ErrInfo e)
        {
            apierr(e);
        }

        void apierr(ErrInfo e)
        {
            if (this.InvokeRequired)
            {
                MethodInvoker invoker = new MethodInvoker(
                    delegate()
                    {
                        apierrMethod(e);
                    });

                this.BeginInvoke(invoker, e);
            }
            else
            {
                apierrMethod(e);
            }
        }
        void apierrMethod(ErrInfo e)
        {
            if (e.Ei.ErrCode == "FF22")
            {
                TreeNode[] tns = tvReaders.Nodes.Find(e.ReaderName, true);
                if (tns.Length > 0)
                {
                    if (tns[0].Parent.Parent.Name == "Server")
                        tvReaders.Nodes.Remove(tns[0]);
                    else
                    {
                        tns[0].ImageIndex = 4;
                        tvReaders.SelectedNode.SelectedImageIndex = tvReaders.ImageIndex;
                    }
                    txtMsg.AppendText(e.ReaderName + "断开连接");
                }
            }
            else if (e.Ei.ErrCode == "FF24")//发现连接作废
            {
                TreeNode[] tns = tvReaders.Nodes.Find(e.ReaderName, true);
                if (tns.Length > 0)
                {
                    if (tns[0].Parent.Parent.Name != "Server")
                    {
                        tns[0].ImageIndex = 4;
                        tvReaders.SelectedNode.SelectedImageIndex = tvReaders.ImageIndex;
                    }
                    txtMsg.AppendText(e.ReaderName + "断开连接");
                }
            }   
        }

        void myReaders_OnReaderClientConn(MyReader myReader, EventArgs arg)
        {
            addTreeNode(tvReaders, myReader);
        }

        void addTreeNode(Control ctrl, MyReader myReader)
        {
            if (this.InvokeRequired)
            {
                MethodInvoker invoker = new MethodInvoker(
                    delegate()
                    {
                        addTreeNodeMethod(ctrl, myReader);
                    });

                this.BeginInvoke(invoker, ctrl, myReader);
            }
            else
            {
                addTreeNodeMethod(ctrl, myReader);
            }
        }
        void addTreeNodeMethod(Control ctrl, MyReader myReader)
        {
            TreeNode node = tvReaders.Nodes.Find("Port:" + myReader.reader.ReaderGroup, true)[0];
            node.Nodes.Add(myReader.reader.ReaderName, myReader.reader.ReaderName).ImageIndex = 5;
            node.Expand();
            node.Parent.Expand();
        }

        void myStatTimer_Elapsed(object sender, System.Timers.ElapsedEventArgs e)
        {
            TimeSpan ts = new TimeSpan();
            ts = DateTime.Now.Subtract(this.dateTime);
            setCtrlText(lblScanTime,
                String.Format("{0}:{1}:{2}",
                (ts.Days * 24 + ts.Hours).ToString().PadLeft(2, '0'),
                ts.Minutes.ToString().PadLeft(2, '0'),
                ts.Seconds.ToString().PadLeft(2, '0')));


            if (totalCount > 0)
                Common.SetText(this, this.lblSpeed,
                    String.Format("{0:D}", (int)((Double)totalCount / ts.TotalMilliseconds * 1000)));

            int speed = (int)((Double)totalCount / ts.TotalMilliseconds * 1000);
            setCtrlText(lblSpeed, speed.ToString());            
        }

        void myReaders_OnTagAlarm(IRP1.Reader reader, EventArgs arg)
        {
            Console.Beep(4500, 1);
        }

        delegate void DisplayReaderErrorMsgDelegate(string myArg2);
        void myReaders_OnReaderErrorMsg(IRP1.Reader reader, string errString)
        {
            object[] myArray = new object[1];
            string str = errString + "\r\n";
            if (reader != null)
                str = reader.ReaderName + ": " + str;
            myArray[0] = str;
            txtMsg.BeginInvoke(new DisplayReaderErrorMsgDelegate(DisplayReaderErrorMsgMethod), myArray);            
        }
        void DisplayReaderErrorMsgMethod(string myCaption)
        {
            txtMsg.AppendText(myCaption);
        }

        delegate void setCtrlTextHandle(Control ctrl, string txt);
        void setCtrlText(Control ctrl, string txt)
        {
            if (this.InvokeRequired)
            {
                MethodInvoker invoker = new MethodInvoker(
                    delegate()
                    {
                        setCtrlTextMethod(ctrl, txt);
                    });

                this.BeginInvoke(invoker, ctrl, txt);
            }
            else
            {
                setCtrlTextMethod(ctrl, txt);
            }
        }
        void setCtrlTextMethod(Control ctrl, string txt)
        {
            ctrl.Text = txt;
        }

        void myReaders_OnReaderNotificationMsg(IRP1.Reader reader, NotificationMessage nMsg)
        {
            if (nMsg != null)
                displayDataGridView(nMsg.ReaderName, nMsg.TagType, nMsg.EPC, nMsg.TID, nMsg.Userdata, nMsg.Antenna, nMsg.RSSI, nMsg.ReadTime, nMsg.Reserved);
        }
        delegate void ShowDataHandle(String readerName, String tagType, String epc, String tid, String userData, int antenna, String rssi, String readTime, String reserve);
        private void displayDataGridView(String readerName, String tagType, String epc, String tid, String userData, int antenna, String rssi, String readTime, String reserve)
        {
            if (this.InvokeRequired)
            {
                MethodInvoker invoker = new MethodInvoker(
                    delegate()
                    {
                        ShowDataGridView(readerName, tagType, epc, tid, userData, antenna, rssi, readTime, reserve);
                    });

                this.BeginInvoke(invoker, readerName, tagType, epc, tid, userData, antenna, rssi, readTime, reserve);
            }
            else
            {
                ShowDataGridView(readerName, tagType, epc, tid, userData, antenna, rssi, readTime, reserve);
            }
        }
        private object objLock = new object();
        private void ShowDataGridView(string readerName, string tagType, string epc, string tid, string userData, int antenna, string rssi, string readTime, string reserve)
        {
            this.totalCount++; 
            bool isAdd = true;            
            lock (objLock)
            {
                if (Common.Dt_Display.Rows.Count > 0)
                {
                    foreach (DataRow dr in Common.Dt_Display.Rows)
                    {
                        if (dr[0].ToString() == readerName
                                && dr[1].ToString() == tagType
                                && dr[2].ToString() == epc
                                && dr[3].ToString() == tid)
                            isAdd = false;

                        if (!isAdd)
                        {
                            dr[5] = reserve; 
                            dr[7] = int.Parse(dr[7].ToString()) + 1;
                            dr[7 + antenna] = int.Parse(dr[7 + antenna].ToString()) + 1;
                            dr[6] = rssi;
                            dr[12] = readTime;                                                       
                            break;
                        }
                    }
                }
                if (isAdd)
                {
                    DataRow mydr = Common.Dt_Display.NewRow();
                    mydr[0] = readerName;
                    mydr[1] = tagType;
                    mydr[2] = epc;
                    mydr[3] = tid;
                    mydr[4] = userData;
                    mydr[5] = reserve;
                    mydr[6] = rssi;
                    mydr[7] = 1;
                    mydr[8] = 0;
                    mydr[9] = 0;
                    mydr[10] = 0;
                    mydr[11] = 0;
                    mydr[7 + antenna] = 1;                    
                    mydr[12] = readTime;

                    Common.Dt_Display.Rows.Add(mydr);
                    int tagCount = Common.Dt_Display.Rows.Count;
                    setCtrlText(lblTotal, tagCount.ToString());
                }
            }   
        }
        #endregion
    }
}
