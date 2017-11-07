using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MCP;
using Util;
using DataGridViewAutoFilter;
using System.Threading;

namespace App
{
    public partial class Main : Form
    {
        private bool IsActiveForm = false;
        public bool IsActiveTab = false;
        private Context context = null;
        private System.Timers.Timer tmWorkTimer = new System.Timers.Timer();
       
        BLL.BLLBase bll = new BLL.BLLBase();

        public Main()
        {
            InitializeComponent();
        }

        #region Main窗体
        private void Main_Shown(object sender, EventArgs e)
        {
            try
            {
                lbLog.Scrollable = true;
                Logger.OnLog += new LogEventHandler(Logger_OnLog);
                FormDialog.OnDialog += new DialogEventHandler(FormDialog_OnDialog);

                MainData.OnTask += new TaskEventHandler(Data_OnTask);
                this.BindData();
                for (int i = 0; i < this.dgvMain.Columns.Count - 1; i++)
                    if (this.dgvMain.Columns[i].DataPropertyName.ToLower().IndexOf("date") == -1)
                        ((DataGridViewAutoFilterTextBoxColumn)this.dgvMain.Columns[i]).FilteringEnabled = true;

                tmWorkTimer.Interval = 3000;
                tmWorkTimer.Elapsed += new System.Timers.ElapsedEventHandler(tmWorker);
                tmWorkTimer.Start();



                toolStripButton_Login.Image = App.Properties.Resources.user_remove;
                toolStripButton_Login.Text = "注销用户";
                PermissionControl();

                //连接RFID
                Thread firstChild = new Thread(new ParameterizedThreadStart(ThreadProc));
                firstChild.IsBackground = true;
                firstChild.Start(firstChild.Name);

                //初始化OPC
                context = new Context();
                ContextInitialize initialize = new ContextInitialize();
                initialize.InitializeContext(context);



                View.frmMonitor f = new View.frmMonitor();
                ShowForm(f);
            }
            catch (Exception ee)
            {
                Logger.Error("初始化处理失败请检查配置，原因：" + ee.Message);
            }
        }
        private static void ThreadProc(object str)
        {
            RFIDRead.InitRFID();
            RFIDRead.Connect();
        }


        private void Main_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (DialogResult.Yes == MessageBox.Show("您确定要退出调度系统吗？", "询问", MessageBoxButtons.YesNo, MessageBoxIcon.Question))
            {
                Logger.Info("退出系统");
                RFIDRead.Close();

                System.Environment.Exit(0);
            }
            else
                e.Cancel = true;
        }
        #endregion

        #region 权限
        private void PermissionControl()
        {
             
          
            DataTable dt = Program.dtUserPermission;
            //产品类别
            string filter = "SubModuleCode='MNU_M00A_00A' and OperatorCode='0'"; 
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ProductClassToolStripMenuItem.Visible = false;
            else
                this.ProductClassToolStripMenuItem.Visible = true;
            //产品资料
             filter = "SubModuleCode='MNU_M00A_00B' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ProductToolStripMenuItem.Visible = false;
            else
                this.ProductToolStripMenuItem.Visible = true;
            //RFID
            filter = "SubModuleCode='MNU_M00A_00C' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.RFIDToolStripMenuItem.Visible = false;
            else
                this.RFIDToolStripMenuItem.Visible = true;
            //接口测试
            filter = "SubModuleCode='MNU_M00A_00D' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_Interface.Visible = false;
            else
                this.ToolStripMenuItem_Interface.Visible = true;

            //出库单
            filter = "SubModuleCode='MNU_M00B_00A' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.OutStockToolStripMenuItem.Visible = false;
                this.toolStripButton_OutStock.Visible = false;
            }
            else
            {
                this.OutStockToolStripMenuItem.Visible = true;
                this.toolStripButton_OutStock.Visible = true;
            }
            //托盘出库单
            filter = "SubModuleCode='MNU_M00B_00B' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.PalletOutStockToolStripMenuItem.Visible = false;
                this.toolStripButton_PalletOutStock.Visible = false;
            }
            else
            {
                this.PalletOutStockToolStripMenuItem.Visible = true;
                this.toolStripButton_PalletOutStock.Visible = true;
            }
            //盘点单
            filter = "SubModuleCode='MNU_M00B_00C' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.InventortoolStripMenuItem.Visible = false;
                this.toolStripButton_Inventor.Visible = false;
            }
            else
            {
                this.InventortoolStripMenuItem.Visible = true;
                this.toolStripButton_Inventor.Visible = true;
            }

            //扫码入库
            filter = "SubModuleCode='MNU_M00B_00D' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.toolStripButton_Scan.Visible = false;
            }
            else
            {
                this.toolStripButton_Scan.Visible = true;
            }

            //现有库存查询
            filter = "SubModuleCode='MNU_M00C_00A' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.ToolStripMenuItem_Stock.Visible = false;
            }
            else
            {
                this.ToolStripMenuItem_Stock.Visible = true;
            }

            //任务查询
            filter = "SubModuleCode='MNU_M00C_00B' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.ToolStripMenuItem_Task.Visible = false;
            }
            else
            {
                this.ToolStripMenuItem_Task.Visible = true;
            }
            //安全存量预警
            filter = "SubModuleCode='MNU_M00C_00C' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.ToolStripMenuItem_SafeQty.Visible = false;
            }
            else
            {
                this.ToolStripMenuItem_SafeQty.Visible = true;
            }
            //货位监控
            filter = "SubModuleCode='MNU_M00C_00D' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
            {
                this.ToolStripMenuItem_Cell.Visible = false;
                this.toolStripButton_CellMonitor.Visible = false;
            }
            else
            {
                this.ToolStripMenuItem_Cell.Visible = true;
                this.toolStripButton_CellMonitor.Visible = true;
            }

            this.ToolStripMenuItemDelCraneTask.Visible = false;

            //监控任务--重新下发堆垛机任务
            filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='2'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItemReassign.Visible = false;
            else
                this.ToolStripMenuItemReassign.Visible = true;


            filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='4'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItemReassignConvey.Visible = false;
            else
                this.ToolStripMenuItemReassignConvey.Visible = true;



            filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='5'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItemReassignAGV.Visible = false;
            else
                this.ToolStripMenuItemReassignAGV.Visible = true;


            //监控任务--任务状态切换
            filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='3'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItemStateChange.Visible = false;
            else
                this.ToolStripMenuItemStateChange.Visible = true;
            //监控任务--重新申请货位
            //filter = "SubModuleCode='MNU_W00A_00A' and OperatorCode='4'";
            //drs = dt.Select(filter);
            //if (drs.Length <= 0)
            //    this.ToolStripMenuItemCellCode.Visible = true;
            //else
            //    this.ToolStripMenuItemCellCode.Visible = false;
            
            //联机自动
            filter = "SubModuleCode='MNU_W00A_00B' and OperatorCode='1'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_StartCrane.Visible = false;
            else
                this.toolStripButton_StartCrane.Visible = true;
            ////退出系统
            //filter = "SubModuleCode='MNU_W00B_00E' and OperatorCode='1'";
            //drs = dt.Select(filter);
            //if (drs.Length <= 0)
            //    this.toolStripButton_Close.Visible = false;
            //else
            //    this.toolStripButton_Close.Visible = true;
            ////参数设定
            //filter = "SubModuleCode='MNU_W00C_00A' and OperatorCode='1'";
            //drs = dt.Select(filter);
            //if (drs.Length <= 0)
            //    this.ToolStripMenuItem_Param.Visible = false;
            //else
            //    this.ToolStripMenuItem_Param.Visible = true;
            ////设备管理
            //filter = "SubModuleCode='MNU_W00C_00B' and OperatorCode='1'";
            //drs = dt.Select(filter);
            //if (drs.Length <= 0)
            //    this.ToolStripMenuItem_Device.Visible = false;
            //else
            //    this.ToolStripMenuItem_Device.Visible = true;
            //用户资料
            filter = "SubModuleCode='MNU_W00C_00C' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_User.Visible = false;
            else
                this.ToolStripMenuItem_User.Visible = true;
            //用户组管理
            filter = "SubModuleCode='MNU_W00C_00D' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_Group.Visible = false;
            else
                this.ToolStripMenuItem_Group.Visible = true;
            //权限管理
            filter = "SubModuleCode='MNU_W00C_00E' and OperatorCode='0'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_Power.Visible = false;
            else
                this.ToolStripMenuItem_Power.Visible = true;
            //密码修改
            filter = "SubModuleCode='MNU_W00C_00F' and OperatorCode='1'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_ChangPwd.Visible = false;
            else
                this.ToolStripMenuItem_ChangPwd.Visible = true;

            filter = "SubModuleCode='MNU_W00C_00G'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.ToolStripMenuItem_RFID.Visible = false;
            else
            {
                this.ToolStripMenuItem_ChangPwd.Visible = true;
                filter = "SubModuleCode='MNU_W00C_00G' and OperatorCode='0' ";
                drs = dt.Select(filter);
                if (drs.Length > 0)
                    ToolStripMenuItem_ScanConfig.Visible = true;
                else
                    ToolStripMenuItem_ScanConfig.Visible = false;

                filter = "SubModuleCode='MNU_W00C_00G' and OperatorCode='1' ";
                drs = dt.Select(filter);
                if (drs.Length > 0)
                    ToolStripMenuItem_ReaderConfig.Visible = true;
                else
                    ToolStripMenuItem_ReaderConfig.Visible = false;
            }
        }
        #endregion

        #region 日志

        void Logger_OnLog(MCP.LogEventArgs args)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new LogEventHandler(Logger_OnLog), args);
            }
            else
            {
                lock (lbLog)
                {
                    string msg1 = string.Format("[{0}] ", args.LogLevel);
                    string msg2 = string.Format("{0} ", DateTime.Now);
                    string msg3 = string.Format("{0} ", args.Message);
                    this.lbLog.BeginUpdate();
                    if (args.LogLevel != LogLevel.DEBUG)
                    {
                        ListViewItem item = new ListViewItem(new string[] { msg2, msg3 });
                        if (msg1.Contains("[ERROR]"))
                        {
                            //item.ForeColor = Color.Red;
                            item.BackColor = Color.Red;
                        }
                        lbLog.Items.Insert(0, item);
                        this.lbLog.EndUpdate();
                    }
                    WriteLoggerFile(msg1 + msg2 + msg3);
                }
            }
        }
       

        private void CreateDirectory(string directoryName)
        {
            if (!System.IO.Directory.Exists(directoryName))
                System.IO.Directory.CreateDirectory(directoryName);
        }

        private void WriteLoggerFile(string text)
        {
            try
            {
                string path = "";
                CreateDirectory("log");
                path = "log";
                path = path + @"/" + DateTime.Now.ToString().Substring(0, 4).Trim();
                CreateDirectory(path);
                path = path + @"/" + DateTime.Now.ToString("yyyy-MM-dd").Substring(0, 7).Trim();
                path = path.TrimEnd(new char[] { '-' });
                CreateDirectory(path);
                path = path + @"/" + DateTime.Now.ToString("yyyy-MM-dd") + ".txt";
                System.IO.File.AppendAllText(path, string.Format("{0}", text + "\r\n"));
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
            }
        }
        #endregion

        #region Process窗体显示
        string FormDialog_OnDialog(DialogEventArgs args)
        {

            string strValue = "";
            if (InvokeRequired)
            {
                return (string)this.Invoke(new DialogEventHandler(FormDialog_OnDialog), args);
            }
            else
            {
                if (args.Message[0] == "1") //出库
                {
                    View.Dispatcher.frmOutView frm = new View.Dispatcher.frmOutView(int.Parse(args.Message[0]), args.dtInfo, context);
                    if (frm.ShowDialog() == DialogResult.OK)
                    {
                        strValue = frm.strValue;
                        //bool tt = context.ProcessDispatcher.WriteToService("CarPLC2", "PickFinished", 1);
                    }
                }
                if (args.Message[0] == "2")//盘点
                {
                    View.Task.frmScan frm = new View.Task.frmScan(args.dtInfo);
                    if (frm.ShowDialog() == DialogResult.OK)
                    {
                        strValue = frm.strValue;
                    }
                }
            }
            return strValue;
        }
        #endregion

        #region 公共方法
        /// <summary>
        /// 打开一个窗体

        /// </summary>
        /// <param name="frm"></param>
        private void ShowForm(Form frm)
        {
            if (OpenOnce(frm))
            {
                frm.MdiParent = this;
                ((View.BaseForm)frm).Context = context;
                frm.Show();
                frm.WindowState = FormWindowState.Maximized;
                AddTabPage(frm.Handle.ToString(), frm.Text);
            }
        }
        /// <summary>
        /// 判断窗体是否已打开
        /// </summary>
        /// <param name="frm"></param>
        /// <returns></returns>
        private bool OpenOnce(Form frm)
        {
            foreach (Form mdifrm in this.MdiChildren)
            {
                int index = mdifrm.Text.IndexOf(" ");
                if (index > 0)
                {
                    if (frm.Name == mdifrm.Name && frm.Text == mdifrm.Text.Substring(0, index))
                    {
                        mdifrm.Activate();
                        return false;
                    }
                }
                else
                {
                    if (frm.Name == mdifrm.Name && frm.Text == mdifrm.Text)
                    {
                        mdifrm.Activate();
                        return false;
                    }
                }
            }
            return true;
        }
        
        private void AddTabPage(string strKey, string strText)
        {
            IsActiveForm = true;
            TabPage tab = new TabPage();
            tab.Name = strKey.ToString();
            tab.Text = strText;
            tabForm.TabPages.Add(tab);
            tabForm.SelectedTab = tab;
            this.pnlTab.Visible = true;
            IsActiveForm = false;
        }
        
        public void SetActiveTab(string strKey, bool blnActive)
        {
            foreach (TabPage tab in this.tabForm.TabPages)
            {
                if (tab.Name == strKey)
                {
                    IsActiveForm = true;

                    if (blnActive)
                        tabForm.SelectedTab = tab;
                    else
                    {
                        tabForm.TabPages.Remove(tab);
                        if (this.MdiChildren.Length > 1)
                            this.pnlTab.Visible = true;
                        else
                            this.pnlTab.Visible = false;
                    }

                    IsActiveForm = false; ;
                }
            }
        }
        private void tabForm_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (IsActiveForm)
                return;
            foreach (Form mdifrm in this.MdiChildren)
            {
                if (mdifrm.Handle.ToInt32() == int.Parse(((TabControl)sender).SelectedTab.Name))
                {
                    IsActiveTab = true;
                    mdifrm.Activate();
                    IsActiveTab = false;
                }
            }
        }
        #endregion

        #region toolStripButton Click

        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            if (DialogResult.Yes == MessageBox.Show("您确定要退出调度系统吗？", "询问", MessageBoxButtons.YesNo, MessageBoxIcon.Question))
            {
                Logger.Info("退出系统");
                System.Environment.Exit(0);
            }
        }

        private void toolStripButton_OutStock_Click(object sender, EventArgs e)
        {
            App.View.Task.frmOutStock f = new View.Task.frmOutStock();
            ShowForm(f);
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            View.Task.frmDeviceTask f = new View.Task.frmDeviceTask();
            ShowForm(f);
        }
        private void toolStripButton_StartCrane_Click(object sender, EventArgs e)
        {
            if (this.toolStripButton_StartCrane.Text == "联机自动")
            {
                context.ProcessDispatcher.WriteToProcess("CraneProcess", "Run", 1);
                this.toolStripButton_StartCrane.Image = App.Properties.Resources.process_accept;
                this.toolStripButton_StartCrane.Text = "脱机";
            }
            else
            {
                context.ProcessDispatcher.WriteToProcess("CraneProcess", "Run", 0);
                this.toolStripButton_StartCrane.Image = App.Properties.Resources.process_remove;
                this.toolStripButton_StartCrane.Text = "联机自动";
            }
        }


        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            View.Task.frmCarTask f = new View.Task.frmCarTask();
            ShowForm(f);
        }

        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            View.Task.frmMiniLoadTask f = new View.Task.frmMiniLoadTask();
            ShowForm(f);
        }

        private void toolStripButton_Inventor_Click(object sender, EventArgs e)
        {
            App.View.Task.frmInventor f = new View.Task.frmInventor();
            ShowForm(f);
        }


        private void toolStripButton_CellMonitor_Click(object sender, EventArgs e)
        {
            App.View.Dispatcher.frmCellQuery f = new App.View.Dispatcher.frmCellQuery();
            ShowForm(f);
        }

        private void toolStripButton_Scan_Click(object sender, EventArgs e)
        {
            App.View.Task.frmInStockTask f = new App.View.Task.frmInStockTask();
            ((View.BaseForm)f).Context = context;
            f.ShowDialog();
        }

        private void toolStripButton_Barcode_Click(object sender, EventArgs e)
        {
            //View.Base.frmBarcode f = new View.Base.frmBarcode();
            //ShowForm(f);
        }

        private void toolStripButton_Login_Click(object sender, EventArgs e)
        {
            if (this.toolStripButton_Login.Text == "用户登录")
            {
                App.Account.frmLogin frm = new Account.frmLogin();
                if (frm.ShowDialog() == DialogResult.OK)
                {
                    this.toolStripButton_Login.Image = App.Properties.Resources.user_remove;
                    this.toolStripButton_Login.Text = "注销用户";
                    PermissionControl();
                }
            }
            else
            {
                this.toolStripButton_Login.Image = App.Properties.Resources.user;
                this.toolStripButton_Login.Text = "用户登录";
                DataTable dt = Program.dtUserPermission.Clone();
                Program.dtUserPermission = dt;
                PermissionControl();
                foreach (Form mdifrm in this.MdiChildren)
                {
                    if (mdifrm.Text != "监控")
                    {
                        mdifrm.Close();
                    }
                }
            }
        }

        private void toolStripButton_PalletOutStock_Click(object sender, EventArgs e)
        {
            App.View.Task.frmPalletOut f = new App.View.Task.frmPalletOut();
            ShowForm(f);
        }

        #endregion

        #region ToolStripMenuItem Click
        private void ToolStripMenuItem_Cell_Click(object sender, EventArgs e)
        {
            App.View.Dispatcher.frmCellQuery f = new App.View.Dispatcher.frmCellQuery();
            ShowForm(f);
        }

        private void OutStockToolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.Task.frmOutStock f = new View.Task.frmOutStock();
            ShowForm(f);
        }

        private void InventortoolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.Task.frmInventor f = new View.Task.frmInventor();
            ShowForm(f);
        }

        private void ToolStripMenuItem_Param_Click(object sender, EventArgs e)
        {
            App.View.Param.ParameterForm f = new App.View.Param.ParameterForm();
            ShowForm(f);
        }

        private void ToolStripMenuItem_User_Click(object sender, EventArgs e)
        {
            App.Account.frmUserList f = new App.Account.frmUserList();
            ShowForm(f);
        }

        private void ToolStripMenuItem_Group_Click(object sender, EventArgs e)
        {
            App.Account.frmGroupList f = new App.Account.frmGroupList();
            ShowForm(f);
        }

        private void ToolStripMenuItem_Power_Click(object sender, EventArgs e)
        {
            App.Account.frmGroupManage f = new App.Account.frmGroupManage();
            ShowForm(f);
        }

        private void ToolStripMenuItem_ChangPwd_Click(object sender, EventArgs e)
        {
            App.Account.frmChangePWD f = new Account.frmChangePWD();
            f.ShowDialog();
        }

        private void ToolStripMenuItem_Device_Click(object sender, EventArgs e)
        {
            App.View.Base.frmDevices f = new App.View.Base.frmDevices();
            ShowForm(f);
        }

        private void ProductClassToolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.CMD.frmProductCls f = new View.CMD.frmProductCls();
            ShowForm(f);
        }

        private void ProductToolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.CMD.frmProduct f = new View.CMD.frmProduct();
            ShowForm(f);
        }

        private void RFIDToolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.CMD.frmRFID f = new View.CMD.frmRFID();
            ShowForm(f);
        }

        private void PalletOutStockToolStripMenuItem_Click(object sender, EventArgs e)
        {
            App.View.Task.frmPalletOut f = new App.View.Task.frmPalletOut();
            ShowForm(f);
        }

        private void ToolStripMenuItem_Stock_Click(object sender, EventArgs e)
        {
            App.View.Query.frmStockQuery f = new View.Query.frmStockQuery();
            ShowForm(f);
        }

        private void ToolStripMenuItem_Task_Click(object sender, EventArgs e)
        {
            App.View.Query.frmTaskQuery f = new View.Query.frmTaskQuery();
            ShowForm(f);
        }

        private void ToolStripMenuItem_SafeQty_Click(object sender, EventArgs e)
        {
            App.View.Query.frmSafeQtyQuery f = new View.Query.frmSafeQtyQuery();
            ShowForm(f);

        }

        private void ToolStripMenuItem_Interface_Click(object sender, EventArgs e)
        {
            App.View.Param.Form1 f = new View.Param.Form1();
            ShowForm(f);

        }

        private void ToolStripMenuItem_ReaderConfig_Click(object sender, EventArgs e)
        {
            App.View.Param.FormReaderConfig frm = new App.View.Param.FormReaderConfig(RFIDRead.reader);
            frm.isTryReconnNet = RFIDRead.isTryReconnNet;
            frm.ShowDialog();
            RFIDRead.isTryReconnNet = frm.isTryReconnNet;
             RFIDRead.tryReconnNetTimeSpan = frm.tryReconnNetTimeSpan;
            frm.Dispose();
        }

        private void ToolStripMenuItem_ScanConfig_Click(object sender, EventArgs e)
        {
            App.View.Param.FormScanConfig frmScanConfig = new App.View.Param.FormScanConfig(RFIDRead.reader);
            frmScanConfig.ShowDialog();
        }

        #endregion

        #region 正执行任务处理
        private void tmWorker(object sender, System.Timers.ElapsedEventArgs e)
        {
            try
            {
                tmWorkTimer.Stop();
                DataTable dt = GetMonitorData();
                MainData.TaskInfo(dt);
            }
            catch (Exception ex)
            {
                Logger.Error(ex.Message);
            }
            finally
            {
                tmWorkTimer.Start();
            }
        }      

        void Data_OnTask(TaskEventArgs args)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new TaskEventHandler(Data_OnTask), args);
            }
            else
            {
                lock (this.dgvMain)
                {
                    DataTable dt = args.datatTable;
                    this.bsMain.DataSource = dt;
                    for (int i = 0; i < this.dgvMain.Rows.Count; i++)
                    {
                        if (dgvMain.Rows[i].Cells["colAlarmCode"].Value.ToString() != "0")
                            this.dgvMain.Rows[i].DefaultCellStyle.BackColor = Color.Red;
                        else
                        {
                            if (i % 2 == 0)
                                this.dgvMain.Rows[i].DefaultCellStyle.BackColor = Color.White;
                            else
                                this.dgvMain.Rows[i].DefaultCellStyle.BackColor = Color.LightSkyBlue;

                        }
                    }
                }
            }
        }


        private void BindData()
        {
            bsMain.DataSource = GetMonitorData();
        }
        private DataTable GetMonitorData()
        {
            string filter = string.Format(" WCS_TASK.State not in('7','9')", Program.WarehouseCode);
            DataTable dt = bll.FillDataTable("WCS.SelectTask", new DataParameter[] { new DataParameter("{0}", filter) });
            return dt;
        }

        private void dgvMain_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                if (e.RowIndex >= 0 && e.ColumnIndex >= 0)
                {
                    //若行已是选中状态就不再进行设置
                    if (dgvMain.Rows[e.RowIndex].Selected == false)
                    {
                        dgvMain.ClearSelection();
                        dgvMain.Rows[e.RowIndex].Selected = true;
                    }
                    //只选中一行时设置活动单元格
                    if (dgvMain.SelectedRows.Count == 1)
                    {
                        dgvMain.CurrentCell = dgvMain.Rows[e.RowIndex].Cells[e.ColumnIndex];
                    }
                    DataRow dr = ((DataRowView)dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem).Row;
                    string TaskType = dr["TaskType"].ToString();
                    if (TaskType == "11" ) //入库
                    {
                        this.ToolStripMenuItem0.Visible = true;
                        this.ToolStripMenuItem1.Visible = true;
                        this.ToolStripMenuItem2.Visible = true;
                        this.ToolStripMenuItem3.Visible = true;
                        this.ToolStripMenuItem4.Visible = false;
                        this.ToolStripMenuItem5.Visible = false;
                        this.ToolStripMenuItem6.Visible = false;
                        this.ToolStripMenuItem7.Visible = true;
                        this.ToolStripMenuItem8.Visible = false;
                        this.ToolStripMenuItem9.Visible = true;
                        this.ToolStripMenuItem10.Visible = false;
                        this.ToolStripMenuItem11.Visible = false;
                    }
                    else if (TaskType == "12" ) //出库
                    {
                        this.ToolStripMenuItem0.Visible = true;
                        this.ToolStripMenuItem1.Visible = false;
                        this.ToolStripMenuItem2.Visible = false;
                        this.ToolStripMenuItem3.Visible = false;
                        this.ToolStripMenuItem4.Visible = true;
                        this.ToolStripMenuItem5.Visible = true;
                        this.ToolStripMenuItem6.Visible = true;
                        this.ToolStripMenuItem7.Visible = true;
                        this.ToolStripMenuItem8.Visible = false;
                        this.ToolStripMenuItem9.Visible = true;
                        this.ToolStripMenuItem10.Visible = true;
                        this.ToolStripMenuItem11.Visible = true;
                    }
                    else if (TaskType == "15")//空箱出库
                    {
                        this.ToolStripMenuItem0.Visible = true;
                        this.ToolStripMenuItem1.Visible = false;
                        this.ToolStripMenuItem2.Visible = false;
                        this.ToolStripMenuItem3.Visible = false;
                        this.ToolStripMenuItem4.Visible = true;
                        this.ToolStripMenuItem5.Visible = true;
                        this.ToolStripMenuItem6.Visible = true;
                        this.ToolStripMenuItem7.Visible = true;
                        this.ToolStripMenuItem8.Visible = false;
                        this.ToolStripMenuItem9.Visible = true;
                        this.ToolStripMenuItem10.Visible = false;
                        this.ToolStripMenuItem11.Visible = false;
                    } 
                    else if (TaskType == "14") //盘点
                    {
                        this.ToolStripMenuItem0.Visible = true;
                        this.ToolStripMenuItem1.Visible = true;
                        this.ToolStripMenuItem2.Visible = true;
                        this.ToolStripMenuItem3.Visible = true;
                        this.ToolStripMenuItem4.Visible = true;
                        this.ToolStripMenuItem5.Visible = true;
                        this.ToolStripMenuItem6.Visible = true;
                        this.ToolStripMenuItem7.Visible = true;
                        this.ToolStripMenuItem8.Visible = true;
                        this.ToolStripMenuItem9.Visible = true;
                    }
                    //弹出操作菜单
                    contextMenuStrip1.Show(MousePosition.X, MousePosition.Y);
                }
            }
        }

        /// <summary>
        /// 重新分配货位
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItemCellCode_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentCell != null)
            {
                BLL.BLLBase bll = new BLL.BLLBase();
                string TaskNo = this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].Cells[0].Value.ToString();
                DataRow dr = ((DataRowView)dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem).Row;
               
                string TaskType = dr["TaskType"].ToString();
                string AlarmCode = dr["AlarmCode"].ToString();
                if (AlarmCode == "503" || AlarmCode == "504")
                {
                    App.Dispatching.Process.Report report = new Dispatching.Process.Report();
                  // report.Send2MJWcs(context, 4, TaskNo);
                }
                else
                    Logger.Info("非重入或入库货位阻塞不可重新申请货位");
            }
        }
        /// <summary>
        /// 重下堆垛机任务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItemReassign_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentCell != null)
            {
                DataRow dr = (this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem as DataRowView).Row;
                string State = dr["State"].ToString();
                string TaskNo = dr["TaskNo"].ToString();
                if (dr["FromStation"].ToString().Trim() == "" || dr["ToStation"].ToString().Trim() == "")
                {
                    Logger.Info(TaskNo + "目标位置或者起始位置错误,无法重新下达任务！");
                    return;
                }
                if (State == "3" || State == "4")
                    Send2PLC(dr);
                else
                {
                    Logger.Info("非正在上下架的任务无法重新下发");
                    return;
                }
                this.BindData();
            }
        }

        private void Send2PLC(DataRow dr)
        {
            string serviceName = "CranePLC01";

            string TaskNo = dr["TaskNo"].ToString();
            string fromStation = dr["FromStation"].ToString();
            string toStation = dr["ToStation"].ToString();
            int[] cellAddr = new int[6];
            cellAddr[0] = int.Parse(fromStation.Substring(4, 3));
            cellAddr[1] = int.Parse(fromStation.Substring(7, 3));
            cellAddr[2] = int.Parse(fromStation.Substring(0, 3));
            cellAddr[3] = int.Parse(toStation.Substring(4, 3));
            cellAddr[4] = int.Parse(toStation.Substring(7, 3));
            cellAddr[5] = int.Parse(toStation.Substring(0, 3));
            string TaskTypeValue = "1";
            if (ObjectUtil.GetObject(context.ProcessDispatcher.WriteToService(serviceName, "CraneLoad")).ToString() == "1")
                TaskTypeValue = "2";

            sbyte[] sTaskNo = new sbyte[20];
            Util.ConvertStringChar.stringToBytes(TaskNo, 20).CopyTo(sTaskNo, 0);
            context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", sTaskNo);

            context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);

            context.ProcessDispatcher.WriteToService(serviceName, "TaskType", TaskTypeValue);
            context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);

            Logger.Info("重新下发堆垛机任务,任务号:" + TaskNo);
        }
        /// <summary>
        /// 状态切换
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            string ItemName = ((ToolStripMenuItem)sender).Name;
            string State = ItemName.Replace("ToolStripMenuItem", "");

            if (this.dgvMain.CurrentCell != null)
            {
                BLL.BLLBase bll = new BLL.BLLBase();
                string TaskNo = this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].Cells[0].Value.ToString();

                DataParameter[] param = new DataParameter[] { new DataParameter("@TaskNo", TaskNo), new DataParameter("@State", State) };
                bll.ExecNonQueryTran("WCS.Sp_UpdateTaskState", param);

                BindData();

                try
                {
                    App.Dispatching.Process.Report report = new Dispatching.Process.Report();
                    if (State == "7")
                        report.Send2MES(TaskNo);
                     
                }
                catch (Exception ex)
                {
                    Logger.Error("切换状态，上报MES时发生错误：" + ex.Message);
                }
            }
        }

        /// <summary>
        /// 取消堆垛机任务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItemDelCraneTask_Click(object sender, EventArgs e)
        {
            string serviceName = "CranePLC01";
            object obj = MCP.ObjectUtil.GetObject(context.ProcessDispatcher.WriteToService(serviceName, "AlarmCode")).ToString();
            if (obj.ToString() != "0")
            {
                DataRow dataRow = (this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem as DataRowView).Row;
                string TaskNo = dataRow["TaskNo"].ToString();
                context.ProcessDispatcher.WriteToService(serviceName, "TaskType", 5);
                context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);
                Logger.Info("已给堆垛机下发取消任务指令,任务号:" + TaskNo);
            }
            else
            {
                Logger.Info("堆垛机未发现错误，不能取消任务." );
            }
        }

      
        /// <summary>
        /// 重下输送线任务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItemReassignConvey_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentCell != null)
            {
                DataRow dr = (this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem as DataRowView).Row;
                string State = dr["State"].ToString();
                string TaskNo = dr["TaskNo"].ToString();
                if (dr["ConveyFromStation"].ToString().Trim() == "" || dr["ConveyToStation"].ToString().Trim() == "")
                {
                    Logger.Info(TaskNo + "目标位置或者起始位置错误,无法重新下达任务！");
                    return;
                }
                if (State == "1" || State == "6")
                    Send2PLCConvey(dr);
                else
                {
                    Logger.Info("非输送线任务无法重新下发！");
                    return;
                }
                this.BindData();
            }
        }
        private void Send2PLCConvey(DataRow dr)
        {
            string ConveyServer = "Convey";
            string ConveyID = dr["ConveyFromStation"].ToString();
            string Destination = dr["ConveyToStation"].ToString();
            string PalletCode = dr["PalletCode"].ToString();
            string TaskNo = dr["TaskNo"].ToString();
            int[] iTaskNo = new int[3];
            iTaskNo[0] = int.Parse(TaskNo.Substring(0, 2));
            iTaskNo[1] = int.Parse(TaskNo.Substring(2, 4));
            iTaskNo[2] = int.Parse(TaskNo.Substring(6, 4));
            context.ProcessDispatcher.WriteToService(ConveyServer, ConveyID + "_WTaskNo", iTaskNo);
            context.ProcessDispatcher.WriteToService(ConveyServer, ConveyID + "_WPalletCode", PalletCode);
            context.ProcessDispatcher.WriteToService(ConveyServer, ConveyID + "_Destination", Destination); //目的地
            if (context.ProcessDispatcher.WriteToService(ConveyServer, ConveyID + "_WriteFinished", 1))
            {
                Logger.Info("输送线" + ConveyID + "重新下发任务，任务号:" + TaskNo);
            }
        }
        /// <summary>
        /// 重下AGV任务
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ToolStripMenuItemReassignAGV_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentCell != null)
            {
                DataRow dr = (this.dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem as DataRowView).Row;
                string TaskType = dr["TaskType"].ToString();
                string TaskNo = dr["TaskNo"].ToString();
                int State = int.Parse(dr["State"].ToString());
                if (TaskType != "12")
                {
                    Logger.Info("非出库任务，不能下达AGV任务！");
                    return;
                }
                if (State >= 5)
                    Send2PLCAGV(dr);
                else
                {
                    Logger.Info("出库任务还未到达输送线，不能下发AGV任务！");
                    return;
                }
                this.BindData();
            }
        }
        private void Send2PLCAGV(DataRow dr)
        {
            string TaskNo = dr["TaskNo"].ToString();
            BLL.BLLBase bllAGV = new BLL.BLLBase("AGVDB");
            DataTable dt = bllAGV.FillDataTable("WCS.SelectAGVTask", new DataParameter[] { new DataParameter("{0}", string.Format("call_from='{0}'",TaskNo)) }); //将A改成 中间库的实际表明
            bool blnInsert = false;
            if (dt.Rows.Count == 0)
                blnInsert = true;
            if (dt.Rows.Count > 0)
            {
                DataRow[] drs = dt.Select("Status in (0,1,5)");
                if (drs.Length == 0)
                    blnInsert = true;
            }
            if (blnInsert)
            {
                bllAGV.ExecNonQuery("WCS.InsertAGVTask", new DataParameter[] { new DataParameter("@from_station", ""),  
                                                                               new DataParameter("@to_station", ""),
                                                                               new DataParameter("@TaskNo", TaskNo)});

                Logger.Info("重新下发AGV任务，任务号:" + TaskNo);
            }
        }
        #endregion

      
       
        

      
    }
}
