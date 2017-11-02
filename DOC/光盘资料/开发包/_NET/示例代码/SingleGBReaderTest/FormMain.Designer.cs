namespace SingleReaderTest
{
    partial class FormMain
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.btnConn = new System.Windows.Forms.Button();
            this.btnDisconn = new System.Windows.Forms.Button();
            this.btnScan = new System.Windows.Forms.Button();
            this.btnClean = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.lblMsg = new System.Windows.Forms.ToolStripStatusLabel();
            this.btnStop = new System.Windows.Forms.Button();
            this.btnExit = new System.Windows.Forms.Button();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.MI_Config = new System.Windows.Forms.ToolStripMenuItem();
            this.MI_ScanConfig = new System.Windows.Forms.ToolStripMenuItem();
            this.MI_ReaderConfig = new System.Windows.Forms.ToolStripMenuItem();
            this.MI_Help = new System.Windows.Forms.ToolStripMenuItem();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.p_Antenna = new System.Windows.Forms.Panel();
            this.a4 = new System.Windows.Forms.CheckBox();
            this.a3 = new System.Windows.Forms.CheckBox();
            this.a2 = new System.Windows.Forms.CheckBox();
            this.a1 = new System.Windows.Forms.CheckBox();
            this.label5 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.statusStrip1.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.p_Antenna.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnConn
            // 
            this.btnConn.Location = new System.Drawing.Point(3, 27);
            this.btnConn.Name = "btnConn";
            this.btnConn.Size = new System.Drawing.Size(75, 23);
            this.btnConn.TabIndex = 0;
            this.btnConn.Text = "连接";
            this.btnConn.UseVisualStyleBackColor = true;
            this.btnConn.Click += new System.EventHandler(this.btnConn_Click);
            // 
            // btnDisconn
            // 
            this.btnDisconn.Enabled = false;
            this.btnDisconn.Location = new System.Drawing.Point(3, 56);
            this.btnDisconn.Name = "btnDisconn";
            this.btnDisconn.Size = new System.Drawing.Size(75, 23);
            this.btnDisconn.TabIndex = 0;
            this.btnDisconn.Text = "断开";
            this.btnDisconn.UseVisualStyleBackColor = true;
            this.btnDisconn.Click += new System.EventHandler(this.btnDisconn_Click);
            // 
            // btnScan
            // 
            this.btnScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnScan.Enabled = false;
            this.btnScan.Location = new System.Drawing.Point(424, 27);
            this.btnScan.Name = "btnScan";
            this.btnScan.Size = new System.Drawing.Size(75, 23);
            this.btnScan.TabIndex = 0;
            this.btnScan.Text = "扫描";
            this.btnScan.UseVisualStyleBackColor = true;
            this.btnScan.Click += new System.EventHandler(this.btnScan_Click);
            // 
            // btnClean
            // 
            this.btnClean.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnClean.Location = new System.Drawing.Point(586, 27);
            this.btnClean.Name = "btnClean";
            this.btnClean.Size = new System.Drawing.Size(75, 23);
            this.btnClean.TabIndex = 0;
            this.btnClean.Text = "清空";
            this.btnClean.UseVisualStyleBackColor = true;
            this.btnClean.Click += new System.EventHandler(this.btnClean_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.AllowUserToDeleteRows = false;
            this.dataGridView1.AllowUserToOrderColumns = true;
            this.dataGridView1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(84, 56);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.ReadOnly = true;
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(577, 275);
            this.dataGridView1.TabIndex = 21;
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lblMsg});
            this.statusStrip1.Location = new System.Drawing.Point(0, 334);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(664, 22);
            this.statusStrip1.TabIndex = 22;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // lblMsg
            // 
            this.lblMsg.Name = "lblMsg";
            this.lblMsg.Size = new System.Drawing.Size(0, 17);
            // 
            // btnStop
            // 
            this.btnStop.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnStop.Enabled = false;
            this.btnStop.Location = new System.Drawing.Point(505, 27);
            this.btnStop.Name = "btnStop";
            this.btnStop.Size = new System.Drawing.Size(75, 23);
            this.btnStop.TabIndex = 0;
            this.btnStop.Text = "停止";
            this.btnStop.UseVisualStyleBackColor = true;
            this.btnStop.Click += new System.EventHandler(this.btnStop_Click);
            // 
            // btnExit
            // 
            this.btnExit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnExit.Location = new System.Drawing.Point(3, 308);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(75, 23);
            this.btnExit.TabIndex = 0;
            this.btnExit.Text = "退出";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.MI_Config,
            this.MI_Help});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(664, 25);
            this.menuStrip1.TabIndex = 23;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // MI_Config
            // 
            this.MI_Config.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.MI_ScanConfig,
            this.MI_ReaderConfig});
            this.MI_Config.Name = "MI_Config";
            this.MI_Config.Size = new System.Drawing.Size(44, 21);
            this.MI_Config.Text = "配置";
            // 
            // MI_ScanConfig
            // 
            this.MI_ScanConfig.Enabled = false;
            this.MI_ScanConfig.Name = "MI_ScanConfig";
            this.MI_ScanConfig.Size = new System.Drawing.Size(136, 22);
            this.MI_ScanConfig.Text = "功率配置";
            this.MI_ScanConfig.Click += new System.EventHandler(this.MI_ScanConfig_Click);
            // 
            // MI_ReaderConfig
            // 
            this.MI_ReaderConfig.Enabled = false;
            this.MI_ReaderConfig.Name = "MI_ReaderConfig";
            this.MI_ReaderConfig.Size = new System.Drawing.Size(136, 22);
            this.MI_ReaderConfig.Text = "读写器配置";
            this.MI_ReaderConfig.Click += new System.EventHandler(this.MI_ReaderConfig_Click);
            // 
            // MI_Help
            // 
            this.MI_Help.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.MI_Help.Name = "MI_Help";
            this.MI_Help.Size = new System.Drawing.Size(44, 21);
            this.MI_Help.Text = "帮助";
            this.MI_Help.Click += new System.EventHandler(this.MI_Help_Click);
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "信息区",
            "编码区",
            "安全区",
            "用户区1",
            "用户区2",
            "用户区3",
            "用户区4",
            "用户区5"});
            this.comboBox1.Location = new System.Drawing.Point(321, 28);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(82, 20);
            this.comboBox1.TabIndex = 47;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(273, 32);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(53, 12);
            this.label6.TabIndex = 46;
            this.label6.Text = "数据区：";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // p_Antenna
            // 
            this.p_Antenna.Controls.Add(this.a4);
            this.p_Antenna.Controls.Add(this.a3);
            this.p_Antenna.Controls.Add(this.a2);
            this.p_Antenna.Controls.Add(this.a1);
            this.p_Antenna.Location = new System.Drawing.Point(129, 24);
            this.p_Antenna.Name = "p_Antenna";
            this.p_Antenna.Size = new System.Drawing.Size(138, 26);
            this.p_Antenna.TabIndex = 49;
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
            this.a1.Location = new System.Drawing.Point(6, 5);
            this.a1.Name = "a1";
            this.a1.Size = new System.Drawing.Size(30, 16);
            this.a1.TabIndex = 9;
            this.a1.Text = "1";
            this.a1.UseVisualStyleBackColor = true;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(93, 31);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(41, 12);
            this.label5.TabIndex = 48;
            this.label5.Text = "天线：";
            // 
            // FormMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(664, 356);
            this.Controls.Add(this.p_Antenna);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.menuStrip1);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.btnClean);
            this.Controls.Add(this.btnStop);
            this.Controls.Add(this.btnScan);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.btnDisconn);
            this.Controls.Add(this.btnConn);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "FormMain";
            this.Text = "主窗体";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormMain_FormClosing);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.p_Antenna.ResumeLayout(false);
            this.p_Antenna.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnConn;
        private System.Windows.Forms.Button btnDisconn;
        private System.Windows.Forms.Button btnScan;
        private System.Windows.Forms.Button btnClean;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.Button btnStop;
        private System.Windows.Forms.Button btnExit;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem MI_Config;
        private System.Windows.Forms.ToolStripMenuItem MI_ReaderConfig;
        private System.Windows.Forms.ToolStripMenuItem MI_Help;
        private System.Windows.Forms.ToolStripMenuItem MI_ScanConfig;
        private System.Windows.Forms.ToolStripStatusLabel lblMsg;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Panel p_Antenna;
        private System.Windows.Forms.CheckBox a4;
        private System.Windows.Forms.CheckBox a3;
        private System.Windows.Forms.CheckBox a2;
        private System.Windows.Forms.CheckBox a1;
        private System.Windows.Forms.Label label5;
    }
}

