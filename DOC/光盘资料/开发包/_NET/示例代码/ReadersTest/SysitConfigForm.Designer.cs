namespace ReadersTest
{
    partial class SysitConfigForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SysitConfigForm));
            this.label1 = new System.Windows.Forms.Label();
            this.txtReader = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.pTcpServer = new System.Windows.Forms.Panel();
            this.label14 = new System.Windows.Forms.Label();
            this.numSerPort = new System.Windows.Forms.NumericUpDown();
            this.pRS232 = new System.Windows.Forms.Panel();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.cbComPort = new System.Windows.Forms.ComboBox();
            this.cbBaudRate = new System.Windows.Forms.ComboBox();
            this.pTcpClient = new System.Windows.Forms.Panel();
            this.txtIP = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.numIpPort = new System.Windows.Forms.NumericUpDown();
            this.rbTcpServer = new System.Windows.Forms.RadioButton();
            this.rbUsb = new System.Windows.Forms.RadioButton();
            this.rbRs232 = new System.Windows.Forms.RadioButton();
            this.rbTcpClient = new System.Windows.Forms.RadioButton();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.tabControl2 = new System.Windows.Forms.TabControl();
            this.tpIRP1 = new System.Windows.Forms.TabPage();
            this.pIRP1 = new System.Windows.Forms.Panel();
            this.pAntenna = new System.Windows.Forms.Panel();
            this.a4 = new System.Windows.Forms.CheckBox();
            this.a3 = new System.Windows.Forms.CheckBox();
            this.a2 = new System.Windows.Forms.CheckBox();
            this.a1 = new System.Windows.Forms.CheckBox();
            this.cbReadTag = new System.Windows.Forms.ComboBox();
            this.numStopTime = new System.Windows.Forms.NumericUpDown();
            this.label13 = new System.Windows.Forms.Label();
            this.numReadTime = new System.Windows.Forms.NumericUpDown();
            this.label12 = new System.Windows.Forms.Label();
            this.numTag = new System.Windows.Forms.NumericUpDown();
            this.label8 = new System.Windows.Forms.Label();
            this.rbSingle = new System.Windows.Forms.RadioButton();
            this.label7 = new System.Windows.Forms.Label();
            this.rbLoop = new System.Windows.Forms.RadioButton();
            this.cbAntenna = new System.Windows.Forms.ComboBox();
            this.label11 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.tpIRP2 = new System.Windows.Forms.TabPage();
            this.btnSave = new System.Windows.Forms.Button();
            this.btnCancle = new System.Windows.Forms.Button();
            this.label15 = new System.Windows.Forms.Label();
            this.cbGroup = new System.Windows.Forms.ComboBox();
            this.label16 = new System.Windows.Forms.Label();
            this.cbMN = new System.Windows.Forms.ComboBox();
            this.groupBox1.SuspendLayout();
            this.pTcpServer.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numSerPort)).BeginInit();
            this.pRS232.SuspendLayout();
            this.pTcpClient.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numIpPort)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.tabControl2.SuspendLayout();
            this.tpIRP1.SuspendLayout();
            this.pIRP1.SuspendLayout();
            this.pAntenna.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numStopTime)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numReadTime)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numTag)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(11, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "读写器名称：";
            // 
            // txtReader
            // 
            this.txtReader.Location = new System.Drawing.Point(94, 11);
            this.txtReader.Name = "txtReader";
            this.txtReader.Size = new System.Drawing.Size(128, 21);
            this.txtReader.TabIndex = 1;
            this.txtReader.Text = "Reader1";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.pTcpServer);
            this.groupBox1.Controls.Add(this.pRS232);
            this.groupBox1.Controls.Add(this.pTcpClient);
            this.groupBox1.Controls.Add(this.rbTcpServer);
            this.groupBox1.Controls.Add(this.rbUsb);
            this.groupBox1.Controls.Add(this.rbRs232);
            this.groupBox1.Controls.Add(this.rbTcpClient);
            this.groupBox1.Location = new System.Drawing.Point(13, 40);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(199, 283);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "连接方式：";
            // 
            // pTcpServer
            // 
            this.pTcpServer.Controls.Add(this.label14);
            this.pTcpServer.Controls.Add(this.numSerPort);
            this.pTcpServer.Enabled = false;
            this.pTcpServer.Location = new System.Drawing.Point(17, 249);
            this.pTcpServer.Name = "pTcpServer";
            this.pTcpServer.Size = new System.Drawing.Size(163, 28);
            this.pTcpServer.TabIndex = 7;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(3, 3);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(41, 12);
            this.label14.TabIndex = 0;
            this.label14.Text = "Port：";
            // 
            // numSerPort
            // 
            this.numSerPort.Location = new System.Drawing.Point(57, 1);
            this.numSerPort.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.numSerPort.Name = "numSerPort";
            this.numSerPort.Size = new System.Drawing.Size(100, 21);
            this.numSerPort.TabIndex = 1;
            this.numSerPort.Value = new decimal(new int[] {
            12800,
            0,
            0,
            0});
            // 
            // pRS232
            // 
            this.pRS232.Controls.Add(this.label4);
            this.pRS232.Controls.Add(this.label5);
            this.pRS232.Controls.Add(this.cbComPort);
            this.pRS232.Controls.Add(this.cbBaudRate);
            this.pRS232.Enabled = false;
            this.pRS232.Location = new System.Drawing.Point(17, 124);
            this.pRS232.Name = "pRS232";
            this.pRS232.Size = new System.Drawing.Size(163, 58);
            this.pRS232.TabIndex = 6;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(3, 9);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(41, 12);
            this.label4.TabIndex = 0;
            this.label4.Text = "端口：";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(3, 35);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(53, 12);
            this.label5.TabIndex = 0;
            this.label5.Text = "波特率：";
            // 
            // cbComPort
            // 
            this.cbComPort.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbComPort.FormattingEnabled = true;
            this.cbComPort.Location = new System.Drawing.Point(57, 6);
            this.cbComPort.Name = "cbComPort";
            this.cbComPort.Size = new System.Drawing.Size(100, 20);
            this.cbComPort.TabIndex = 3;
            // 
            // cbBaudRate
            // 
            this.cbBaudRate.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbBaudRate.FormattingEnabled = true;
            this.cbBaudRate.Items.AddRange(new object[] {
            "9600",
            "19200",
            "115200"});
            this.cbBaudRate.Location = new System.Drawing.Point(57, 32);
            this.cbBaudRate.Name = "cbBaudRate";
            this.cbBaudRate.Size = new System.Drawing.Size(100, 20);
            this.cbBaudRate.TabIndex = 3;
            // 
            // pTcpClient
            // 
            this.pTcpClient.Controls.Add(this.txtIP);
            this.pTcpClient.Controls.Add(this.label2);
            this.pTcpClient.Controls.Add(this.label3);
            this.pTcpClient.Controls.Add(this.numIpPort);
            this.pTcpClient.Location = new System.Drawing.Point(17, 43);
            this.pTcpClient.Name = "pTcpClient";
            this.pTcpClient.Size = new System.Drawing.Size(163, 61);
            this.pTcpClient.TabIndex = 5;
            // 
            // txtIP
            // 
            this.txtIP.Location = new System.Drawing.Point(57, 4);
            this.txtIP.Name = "txtIP";
            this.txtIP.Size = new System.Drawing.Size(100, 21);
            this.txtIP.TabIndex = 0;
            this.txtIP.Text = "192.168.0.210";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 7);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(29, 12);
            this.label2.TabIndex = 0;
            this.label2.Text = "IP：";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(3, 34);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 12);
            this.label3.TabIndex = 0;
            this.label3.Text = "Port：";
            // 
            // numIpPort
            // 
            this.numIpPort.Location = new System.Drawing.Point(57, 32);
            this.numIpPort.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.numIpPort.Name = "numIpPort";
            this.numIpPort.Size = new System.Drawing.Size(100, 21);
            this.numIpPort.TabIndex = 1;
            this.numIpPort.Value = new decimal(new int[] {
            7086,
            0,
            0,
            0});
            // 
            // rbTcpServer
            // 
            this.rbTcpServer.AutoSize = true;
            this.rbTcpServer.Location = new System.Drawing.Point(17, 227);
            this.rbTcpServer.Name = "rbTcpServer";
            this.rbTcpServer.Size = new System.Drawing.Size(101, 16);
            this.rbTcpServer.TabIndex = 4;
            this.rbTcpServer.Text = "TCP/IP Server";
            this.rbTcpServer.UseVisualStyleBackColor = true;
            this.rbTcpServer.CheckedChanged += new System.EventHandler(this.rbConn_CheckedChanged);
            // 
            // rbUsb
            // 
            this.rbUsb.AutoSize = true;
            this.rbUsb.Location = new System.Drawing.Point(17, 188);
            this.rbUsb.Name = "rbUsb";
            this.rbUsb.Size = new System.Drawing.Size(41, 16);
            this.rbUsb.TabIndex = 4;
            this.rbUsb.Text = "USB";
            this.rbUsb.UseVisualStyleBackColor = true;
            this.rbUsb.CheckedChanged += new System.EventHandler(this.rbConn_CheckedChanged);
            // 
            // rbRs232
            // 
            this.rbRs232.AutoSize = true;
            this.rbRs232.Location = new System.Drawing.Point(17, 110);
            this.rbRs232.Name = "rbRs232";
            this.rbRs232.Size = new System.Drawing.Size(53, 16);
            this.rbRs232.TabIndex = 4;
            this.rbRs232.Text = "RS232";
            this.rbRs232.UseVisualStyleBackColor = true;
            this.rbRs232.CheckedChanged += new System.EventHandler(this.rbConn_CheckedChanged);
            // 
            // rbTcpClient
            // 
            this.rbTcpClient.AutoSize = true;
            this.rbTcpClient.Location = new System.Drawing.Point(17, 22);
            this.rbTcpClient.Name = "rbTcpClient";
            this.rbTcpClient.Size = new System.Drawing.Size(101, 16);
            this.rbTcpClient.TabIndex = 4;
            this.rbTcpClient.Text = "TCP/IP Client";
            this.rbTcpClient.UseVisualStyleBackColor = true;
            this.rbTcpClient.CheckedChanged += new System.EventHandler(this.rbConn_CheckedChanged);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.tabControl2);
            this.groupBox2.Location = new System.Drawing.Point(218, 40);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(286, 283);
            this.groupBox2.TabIndex = 3;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "读写器默认扫描配置：";
            // 
            // tabControl2
            // 
            this.tabControl2.Controls.Add(this.tpIRP1);
            this.tabControl2.Controls.Add(this.tpIRP2);
            this.tabControl2.Location = new System.Drawing.Point(6, 22);
            this.tabControl2.Name = "tabControl2";
            this.tabControl2.SelectedIndex = 0;
            this.tabControl2.Size = new System.Drawing.Size(270, 252);
            this.tabControl2.TabIndex = 0;
            // 
            // tpIRP1
            // 
            this.tpIRP1.Controls.Add(this.pIRP1);
            this.tpIRP1.Location = new System.Drawing.Point(4, 21);
            this.tpIRP1.Name = "tpIRP1";
            this.tpIRP1.Padding = new System.Windows.Forms.Padding(3);
            this.tpIRP1.Size = new System.Drawing.Size(262, 227);
            this.tpIRP1.TabIndex = 0;
            this.tpIRP1.Text = "IRP1";
            this.tpIRP1.UseVisualStyleBackColor = true;
            // 
            // pIRP1
            // 
            this.pIRP1.Controls.Add(this.pAntenna);
            this.pIRP1.Controls.Add(this.cbReadTag);
            this.pIRP1.Controls.Add(this.numStopTime);
            this.pIRP1.Controls.Add(this.label13);
            this.pIRP1.Controls.Add(this.numReadTime);
            this.pIRP1.Controls.Add(this.label12);
            this.pIRP1.Controls.Add(this.numTag);
            this.pIRP1.Controls.Add(this.label8);
            this.pIRP1.Controls.Add(this.rbSingle);
            this.pIRP1.Controls.Add(this.label7);
            this.pIRP1.Controls.Add(this.rbLoop);
            this.pIRP1.Controls.Add(this.cbAntenna);
            this.pIRP1.Controls.Add(this.label11);
            this.pIRP1.Controls.Add(this.label10);
            this.pIRP1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pIRP1.Location = new System.Drawing.Point(3, 3);
            this.pIRP1.Name = "pIRP1";
            this.pIRP1.Size = new System.Drawing.Size(256, 221);
            this.pIRP1.TabIndex = 6;
            // 
            // pAntenna
            // 
            this.pAntenna.Controls.Add(this.a4);
            this.pAntenna.Controls.Add(this.a3);
            this.pAntenna.Controls.Add(this.a2);
            this.pAntenna.Controls.Add(this.a1);
            this.pAntenna.Location = new System.Drawing.Point(111, 56);
            this.pAntenna.Name = "pAntenna";
            this.pAntenna.Size = new System.Drawing.Size(138, 26);
            this.pAntenna.TabIndex = 25;
            this.pAntenna.Visible = false;
            // 
            // a4
            // 
            this.a4.AutoSize = true;
            this.a4.Location = new System.Drawing.Point(103, 5);
            this.a4.Name = "a4";
            this.a4.Size = new System.Drawing.Size(30, 16);
            this.a4.TabIndex = 9;
            this.a4.Text = "4";
            this.a4.UseVisualStyleBackColor = true;
            // 
            // a3
            // 
            this.a3.AutoSize = true;
            this.a3.Location = new System.Drawing.Point(72, 5);
            this.a3.Name = "a3";
            this.a3.Size = new System.Drawing.Size(30, 16);
            this.a3.TabIndex = 9;
            this.a3.Text = "3";
            this.a3.UseVisualStyleBackColor = true;
            // 
            // a2
            // 
            this.a2.AutoSize = true;
            this.a2.Location = new System.Drawing.Point(37, 5);
            this.a2.Name = "a2";
            this.a2.Size = new System.Drawing.Size(30, 16);
            this.a2.TabIndex = 9;
            this.a2.Text = "2";
            this.a2.UseVisualStyleBackColor = true;
            // 
            // a1
            // 
            this.a1.AutoSize = true;
            this.a1.Checked = true;
            this.a1.CheckState = System.Windows.Forms.CheckState.Checked;
            this.a1.Location = new System.Drawing.Point(6, 5);
            this.a1.Name = "a1";
            this.a1.Size = new System.Drawing.Size(30, 16);
            this.a1.TabIndex = 9;
            this.a1.Text = "1";
            this.a1.UseVisualStyleBackColor = true;
            // 
            // cbReadTag
            // 
            this.cbReadTag.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbReadTag.FormattingEnabled = true;
            this.cbReadTag.Location = new System.Drawing.Point(91, 30);
            this.cbReadTag.Name = "cbReadTag";
            this.cbReadTag.Size = new System.Drawing.Size(159, 20);
            this.cbReadTag.TabIndex = 23;
            // 
            // numStopTime
            // 
            this.numStopTime.Location = new System.Drawing.Point(129, 196);
            this.numStopTime.Maximum = new decimal(new int[] {
            32768,
            0,
            0,
            0});
            this.numStopTime.Name = "numStopTime";
            this.numStopTime.Size = new System.Drawing.Size(120, 21);
            this.numStopTime.TabIndex = 20;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(8, 33);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(65, 12);
            this.label13.TabIndex = 8;
            this.label13.Text = "读卡方式：";
            // 
            // numReadTime
            // 
            this.numReadTime.Location = new System.Drawing.Point(129, 169);
            this.numReadTime.Maximum = new decimal(new int[] {
            32768,
            0,
            0,
            0});
            this.numReadTime.Name = "numReadTime";
            this.numReadTime.Size = new System.Drawing.Size(120, 21);
            this.numReadTime.TabIndex = 21;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(8, 63);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(41, 12);
            this.label12.TabIndex = 11;
            this.label12.Text = "天线：";
            // 
            // numTag
            // 
            this.numTag.Location = new System.Drawing.Point(91, 109);
            this.numTag.Maximum = new decimal(new int[] {
            15,
            0,
            0,
            0});
            this.numTag.Name = "numTag";
            this.numTag.Size = new System.Drawing.Size(158, 21);
            this.numTag.TabIndex = 22;
            this.numTag.Value = new decimal(new int[] {
            3,
            0,
            0,
            0});
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(8, 87);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(65, 12);
            this.label8.TabIndex = 14;
            this.label8.Text = "读取方式：";
            // 
            // rbSingle
            // 
            this.rbSingle.AutoSize = true;
            this.rbSingle.Location = new System.Drawing.Point(144, 85);
            this.rbSingle.Name = "rbSingle";
            this.rbSingle.Size = new System.Drawing.Size(47, 16);
            this.rbSingle.TabIndex = 18;
            this.rbSingle.Text = "单次";
            this.rbSingle.UseVisualStyleBackColor = true;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(7, 115);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(23, 12);
            this.label7.TabIndex = 13;
            this.label7.Text = "Q：";
            // 
            // rbLoop
            // 
            this.rbLoop.AutoSize = true;
            this.rbLoop.Checked = true;
            this.rbLoop.Location = new System.Drawing.Point(91, 85);
            this.rbLoop.Name = "rbLoop";
            this.rbLoop.Size = new System.Drawing.Size(47, 16);
            this.rbLoop.TabIndex = 19;
            this.rbLoop.TabStop = true;
            this.rbLoop.Text = "循环";
            this.rbLoop.UseVisualStyleBackColor = true;
            // 
            // cbAntenna
            // 
            this.cbAntenna.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbAntenna.FormattingEnabled = true;
            this.cbAntenna.Items.AddRange(new object[] {
            "1#",
            "2#",
            "3#",
            "4#",
            "1-2#",
            "1-3#",
            "1-4#"});
            this.cbAntenna.Location = new System.Drawing.Point(91, 59);
            this.cbAntenna.Name = "cbAntenna";
            this.cbAntenna.Size = new System.Drawing.Size(83, 20);
            this.cbAntenna.TabIndex = 16;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(7, 200);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(113, 12);
            this.label11.TabIndex = 15;
            this.label11.Text = "停止时间：（毫秒）";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(7, 173);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(113, 12);
            this.label10.TabIndex = 12;
            this.label10.Text = "读卡时间：（毫秒）";
            // 
            // tpIRP2
            // 
            this.tpIRP2.Location = new System.Drawing.Point(4, 21);
            this.tpIRP2.Name = "tpIRP2";
            this.tpIRP2.Padding = new System.Windows.Forms.Padding(3);
            this.tpIRP2.Size = new System.Drawing.Size(262, 227);
            this.tpIRP2.TabIndex = 1;
            this.tpIRP2.Text = "IRP2";
            this.tpIRP2.UseVisualStyleBackColor = true;
            // 
            // btnSave
            // 
            this.btnSave.Location = new System.Drawing.Point(338, 330);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(75, 23);
            this.btnSave.TabIndex = 4;
            this.btnSave.Text = "保存";
            this.btnSave.UseVisualStyleBackColor = true;
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // btnCancle
            // 
            this.btnCancle.Location = new System.Drawing.Point(419, 329);
            this.btnCancle.Name = "btnCancle";
            this.btnCancle.Size = new System.Drawing.Size(75, 23);
            this.btnCancle.TabIndex = 4;
            this.btnCancle.Text = "取消";
            this.btnCancle.UseVisualStyleBackColor = true;
            this.btnCancle.Click += new System.EventHandler(this.btnCancle_Click);
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(232, 15);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(41, 12);
            this.label15.TabIndex = 0;
            this.label15.Text = "组名：";
            // 
            // cbGroup
            // 
            this.cbGroup.FormattingEnabled = true;
            this.cbGroup.Location = new System.Drawing.Point(279, 11);
            this.cbGroup.Name = "cbGroup";
            this.cbGroup.Size = new System.Drawing.Size(85, 20);
            this.cbGroup.TabIndex = 5;
            this.cbGroup.Text = "800";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(370, 15);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(41, 12);
            this.label16.TabIndex = 0;
            this.label16.Text = "型号：";
            // 
            // cbMN
            // 
            this.cbMN.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbMN.FormattingEnabled = true;
            this.cbMN.Items.AddRange(new object[] {
            "unknown",
            "XCRF-860",
            "XC-RF806",
            "XC-RF807",
            "XCRF-502E",
            "XC-RF811"});
            this.cbMN.Location = new System.Drawing.Point(417, 11);
            this.cbMN.Name = "cbMN";
            this.cbMN.Size = new System.Drawing.Size(85, 20);
            this.cbMN.TabIndex = 5;
            this.cbMN.SelectedIndexChanged += new System.EventHandler(this.cbMN_SelectedIndexChanged);
            // 
            // SysitConfigForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(514, 366);
            this.Controls.Add(this.cbMN);
            this.Controls.Add(this.cbGroup);
            this.Controls.Add(this.btnCancle);
            this.Controls.Add(this.btnSave);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.txtReader);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "SysitConfigForm";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "读写器连接配置";
            this.Load += new System.EventHandler(this.SysitConfigForm_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.pTcpServer.ResumeLayout(false);
            this.pTcpServer.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numSerPort)).EndInit();
            this.pRS232.ResumeLayout(false);
            this.pRS232.PerformLayout();
            this.pTcpClient.ResumeLayout(false);
            this.pTcpClient.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numIpPort)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.tabControl2.ResumeLayout(false);
            this.tpIRP1.ResumeLayout(false);
            this.pIRP1.ResumeLayout(false);
            this.pIRP1.PerformLayout();
            this.pAntenna.ResumeLayout(false);
            this.pAntenna.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numStopTime)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numReadTime)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numTag)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtReader;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.NumericUpDown numIpPort;
        private System.Windows.Forms.TextBox txtIP;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cbBaudRate;
        private System.Windows.Forms.ComboBox cbComPort;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TabControl tabControl2;
        private System.Windows.Forms.TabPage tpIRP1;
        private System.Windows.Forms.RadioButton rbTcpServer;
        private System.Windows.Forms.RadioButton rbUsb;
        private System.Windows.Forms.RadioButton rbRs232;
        private System.Windows.Forms.RadioButton rbTcpClient;
        private System.Windows.Forms.NumericUpDown numSerPort;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.ComboBox cbReadTag;
        private System.Windows.Forms.NumericUpDown numStopTime;
        private System.Windows.Forms.NumericUpDown numReadTime;
        private System.Windows.Forms.NumericUpDown numTag;
        private System.Windows.Forms.RadioButton rbSingle;
        private System.Windows.Forms.RadioButton rbLoop;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ComboBox cbAntenna;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.Button btnCancle;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.ComboBox cbGroup;
        private System.Windows.Forms.TabPage tpIRP2;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.ComboBox cbMN;
        private System.Windows.Forms.Panel pAntenna;
        private System.Windows.Forms.CheckBox a4;
        private System.Windows.Forms.CheckBox a3;
        private System.Windows.Forms.CheckBox a2;
        private System.Windows.Forms.CheckBox a1;
        private System.Windows.Forms.Panel pTcpServer;
        private System.Windows.Forms.Panel pRS232;
        private System.Windows.Forms.Panel pTcpClient;
        private System.Windows.Forms.Panel pIRP1;
    }
}