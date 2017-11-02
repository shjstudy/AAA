namespace ReadersTest
{
    partial class MainForm
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
            this.components = new System.ComponentModel.Container();
            this.连接ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.dgvScanData = new System.Windows.Forms.DataGridView();
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.断开ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.启用ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.禁用ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.txtMsg = new System.Windows.Forms.TextBox();
            this.btnRemoveReader = new System.Windows.Forms.Button();
            this.btnClear = new System.Windows.Forms.Button();
            this.btnAddReader = new System.Windows.Forms.Button();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.lblMsg = new System.Windows.Forms.ToolStripStatusLabel();
            this.tvReaders = new System.Windows.Forms.TreeView();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.btnConn = new System.Windows.Forms.Button();
            this.btnScan = new System.Windows.Forms.Button();
            this.btnTagAccess = new System.Windows.Forms.Button();
            this.btnDisconn = new System.Windows.Forms.Button();
            this.btnStop = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.lblTotal = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lblSpeed = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.lblScanTime = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.btnEasScan = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.dgvScanData)).BeginInit();
            this.contextMenuStrip1.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // 连接ToolStripMenuItem
            // 
            this.连接ToolStripMenuItem.Name = "连接ToolStripMenuItem";
            this.连接ToolStripMenuItem.Size = new System.Drawing.Size(94, 22);
            this.连接ToolStripMenuItem.Text = "连接";
            // 
            // dgvScanData
            // 
            this.dgvScanData.AllowUserToAddRows = false;
            this.dgvScanData.AllowUserToDeleteRows = false;
            this.dgvScanData.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.dgvScanData.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvScanData.Location = new System.Drawing.Point(202, 56);
            this.dgvScanData.MultiSelect = false;
            this.dgvScanData.Name = "dgvScanData";
            this.dgvScanData.ReadOnly = true;
            this.dgvScanData.RowTemplate.Height = 23;
            this.dgvScanData.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvScanData.Size = new System.Drawing.Size(740, 368);
            this.dgvScanData.TabIndex = 17;
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.连接ToolStripMenuItem,
            this.断开ToolStripMenuItem,
            this.启用ToolStripMenuItem,
            this.禁用ToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(95, 92);
            // 
            // 断开ToolStripMenuItem
            // 
            this.断开ToolStripMenuItem.Name = "断开ToolStripMenuItem";
            this.断开ToolStripMenuItem.Size = new System.Drawing.Size(94, 22);
            this.断开ToolStripMenuItem.Text = "断开";
            // 
            // 启用ToolStripMenuItem
            // 
            this.启用ToolStripMenuItem.Name = "启用ToolStripMenuItem";
            this.启用ToolStripMenuItem.Size = new System.Drawing.Size(94, 22);
            this.启用ToolStripMenuItem.Text = "启用";
            // 
            // 禁用ToolStripMenuItem
            // 
            this.禁用ToolStripMenuItem.Name = "禁用ToolStripMenuItem";
            this.禁用ToolStripMenuItem.Size = new System.Drawing.Size(94, 22);
            this.禁用ToolStripMenuItem.Text = "禁用";
            // 
            // txtMsg
            // 
            this.txtMsg.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.txtMsg.Location = new System.Drawing.Point(0, 442);
            this.txtMsg.Multiline = true;
            this.txtMsg.Name = "txtMsg";
            this.txtMsg.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtMsg.Size = new System.Drawing.Size(942, 109);
            this.txtMsg.TabIndex = 19;
            // 
            // btnRemoveReader
            // 
            this.btnRemoveReader.Location = new System.Drawing.Point(47, 27);
            this.btnRemoveReader.Name = "btnRemoveReader";
            this.btnRemoveReader.Size = new System.Drawing.Size(41, 23);
            this.btnRemoveReader.TabIndex = 21;
            this.btnRemoveReader.Text = "删除";
            this.btnRemoveReader.UseVisualStyleBackColor = true;
            this.btnRemoveReader.Click += new System.EventHandler(this.btnRemoveReader_Click);
            // 
            // btnClear
            // 
            this.btnClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnClear.Location = new System.Drawing.Point(867, 27);
            this.btnClear.Name = "btnClear";
            this.btnClear.Size = new System.Drawing.Size(75, 23);
            this.btnClear.TabIndex = 16;
            this.btnClear.Text = "清空数据";
            this.btnClear.UseVisualStyleBackColor = true;
            this.btnClear.Click += new System.EventHandler(this.btnClear_Click);
            // 
            // btnAddReader
            // 
            this.btnAddReader.Location = new System.Drawing.Point(0, 27);
            this.btnAddReader.Name = "btnAddReader";
            this.btnAddReader.Size = new System.Drawing.Size(41, 23);
            this.btnAddReader.TabIndex = 20;
            this.btnAddReader.Text = "添加";
            this.btnAddReader.UseVisualStyleBackColor = true;
            this.btnAddReader.Click += new System.EventHandler(this.btnAddReader_Click);
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lblMsg});
            this.statusStrip1.Location = new System.Drawing.Point(0, 551);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(942, 22);
            this.statusStrip1.TabIndex = 10;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // lblMsg
            // 
            this.lblMsg.Name = "lblMsg";
            this.lblMsg.Size = new System.Drawing.Size(0, 17);
            // 
            // tvReaders
            // 
            this.tvReaders.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)));
            this.tvReaders.BackColor = System.Drawing.Color.White;
            this.tvReaders.HotTracking = true;
            this.tvReaders.Location = new System.Drawing.Point(0, 56);
            this.tvReaders.Name = "tvReaders";
            this.tvReaders.Size = new System.Drawing.Size(199, 347);
            this.tvReaders.TabIndex = 18;
            this.tvReaders.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.tvReaders_AfterSelect);
            this.tvReaders.NodeMouseDoubleClick += new System.Windows.Forms.TreeNodeMouseClickEventHandler(this.tvReaders_NodeMouseDoubleClick);
            // 
            // menuStrip1
            // 
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(942, 24);
            this.menuStrip1.TabIndex = 9;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // btnConn
            // 
            this.btnConn.Location = new System.Drawing.Point(111, 27);
            this.btnConn.Name = "btnConn";
            this.btnConn.Size = new System.Drawing.Size(41, 23);
            this.btnConn.TabIndex = 11;
            this.btnConn.Text = "连接";
            this.btnConn.UseVisualStyleBackColor = true;
            this.btnConn.Click += new System.EventHandler(this.btnConn_Click);
            // 
            // btnScan
            // 
            this.btnScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnScan.Enabled = false;
            this.btnScan.Location = new System.Drawing.Point(624, 27);
            this.btnScan.Name = "btnScan";
            this.btnScan.Size = new System.Drawing.Size(75, 23);
            this.btnScan.TabIndex = 14;
            this.btnScan.Text = "标签扫描";
            this.btnScan.UseVisualStyleBackColor = true;
            this.btnScan.Click += new System.EventHandler(this.btnScan_Click);
            // 
            // btnTagAccess
            // 
            this.btnTagAccess.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnTagAccess.Enabled = false;
            this.btnTagAccess.Location = new System.Drawing.Point(786, 27);
            this.btnTagAccess.Name = "btnTagAccess";
            this.btnTagAccess.Size = new System.Drawing.Size(75, 23);
            this.btnTagAccess.TabIndex = 12;
            this.btnTagAccess.Text = "标签操作";
            this.btnTagAccess.UseVisualStyleBackColor = true;
            this.btnTagAccess.Click += new System.EventHandler(this.btnTagAccess_Click);
            // 
            // btnDisconn
            // 
            this.btnDisconn.Enabled = false;
            this.btnDisconn.Location = new System.Drawing.Point(158, 27);
            this.btnDisconn.Name = "btnDisconn";
            this.btnDisconn.Size = new System.Drawing.Size(41, 23);
            this.btnDisconn.TabIndex = 13;
            this.btnDisconn.Text = "断开";
            this.btnDisconn.UseVisualStyleBackColor = true;
            this.btnDisconn.Click += new System.EventHandler(this.btnDisconn_Click);
            // 
            // btnStop
            // 
            this.btnStop.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnStop.Enabled = false;
            this.btnStop.Location = new System.Drawing.Point(705, 27);
            this.btnStop.Name = "btnStop";
            this.btnStop.Size = new System.Drawing.Size(75, 23);
            this.btnStop.TabIndex = 15;
            this.btnStop.Text = "停止扫描";
            this.btnStop.UseVisualStyleBackColor = true;
            this.btnStop.Click += new System.EventHandler(this.btnStop_Click);
            // 
            // label1
            // 
            this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(205, 427);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(89, 12);
            this.label1.TabIndex = 22;
            this.label1.Text = "扫描标签总数：";
            // 
            // lblTotal
            // 
            this.lblTotal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.lblTotal.AutoSize = true;
            this.lblTotal.Location = new System.Drawing.Point(300, 427);
            this.lblTotal.Name = "lblTotal";
            this.lblTotal.Size = new System.Drawing.Size(0, 12);
            this.lblTotal.TabIndex = 22;
            // 
            // label2
            // 
            this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(368, 427);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(131, 12);
            this.label2.TabIndex = 22;
            this.label2.Text = "扫描平均速率：(次/秒)";
            // 
            // lblSpeed
            // 
            this.lblSpeed.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.lblSpeed.AutoSize = true;
            this.lblSpeed.Location = new System.Drawing.Point(505, 427);
            this.lblSpeed.Name = "lblSpeed";
            this.lblSpeed.Size = new System.Drawing.Size(0, 12);
            this.lblSpeed.TabIndex = 22;
            // 
            // label3
            // 
            this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(818, 427);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 12);
            this.label3.TabIndex = 22;
            this.label3.Text = "扫描时间：";
            // 
            // lblScanTime
            // 
            this.lblScanTime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.lblScanTime.AutoSize = true;
            this.lblScanTime.Location = new System.Drawing.Point(889, 427);
            this.lblScanTime.Name = "lblScanTime";
            this.lblScanTime.Size = new System.Drawing.Size(53, 12);
            this.lblScanTime.TabIndex = 22;
            this.lblScanTime.Text = "00:00:00";
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(-1, 427);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(53, 12);
            this.label4.TabIndex = 22;
            this.label4.Text = "运行消息";
            // 
            // btnEasScan
            // 
            this.btnEasScan.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnEasScan.Enabled = false;
            this.btnEasScan.Location = new System.Drawing.Point(543, 27);
            this.btnEasScan.Name = "btnEasScan";
            this.btnEasScan.Size = new System.Drawing.Size(75, 23);
            this.btnEasScan.TabIndex = 14;
            this.btnEasScan.Text = "EAS扫描";
            this.btnEasScan.UseVisualStyleBackColor = true;
            this.btnEasScan.Click += new System.EventHandler(this.btnEasScan_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(0, 402);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(199, 21);
            this.textBox1.TabIndex = 23;
            this.textBox1.Text = "双击已连接读写器可进行读写器配置";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(942, 573);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.lblScanTime);
            this.Controls.Add(this.lblTotal);
            this.Controls.Add(this.lblSpeed);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dgvScanData);
            this.Controls.Add(this.txtMsg);
            this.Controls.Add(this.btnRemoveReader);
            this.Controls.Add(this.btnClear);
            this.Controls.Add(this.btnAddReader);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.tvReaders);
            this.Controls.Add(this.menuStrip1);
            this.Controls.Add(this.btnConn);
            this.Controls.Add(this.btnScan);
            this.Controls.Add(this.btnEasScan);
            this.Controls.Add(this.btnTagAccess);
            this.Controls.Add(this.btnDisconn);
            this.Controls.Add(this.btnStop);
            this.Name = "MainForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "远望谷读写器示例-仅供参考";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
            this.Load += new System.EventHandler(this.MainForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dgvScanData)).EndInit();
            this.contextMenuStrip1.ResumeLayout(false);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ToolStripMenuItem 连接ToolStripMenuItem;
        private System.Windows.Forms.DataGridView dgvScanData;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 断开ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 启用ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 禁用ToolStripMenuItem;
        private System.Windows.Forms.TextBox txtMsg;
        private System.Windows.Forms.Button btnRemoveReader;
        private System.Windows.Forms.Button btnClear;
        private System.Windows.Forms.Button btnAddReader;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel lblMsg;
        private System.Windows.Forms.TreeView tvReaders;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.Button btnConn;
        private System.Windows.Forms.Button btnScan;
        private System.Windows.Forms.Button btnTagAccess;
        private System.Windows.Forms.Button btnDisconn;
        private System.Windows.Forms.Button btnStop;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblTotal;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lblSpeed;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblScanTime;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnEasScan;
        private System.Windows.Forms.TextBox textBox1;
    }
}

