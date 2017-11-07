namespace App.Account
{
    partial class frmGroupList
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
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmGroupList));
            this.pnlMain = new System.Windows.Forms.Panel();
            this.pnlContent = new System.Windows.Forms.Panel();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.colGroupName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colmemo = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlTool = new System.Windows.Forms.Panel();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButton_Refresh = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Add = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Edit = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Del = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Close = new System.Windows.Forms.ToolStripButton();
            this.pnlBottom = new System.Windows.Forms.Panel();
            this.pnlMain.SuspendLayout();
            this.pnlContent.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.pnlTool.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnlMain
            // 
            this.pnlMain.Controls.Add(this.pnlContent);
            this.pnlMain.Controls.Add(this.pnlTool);
            this.pnlMain.Controls.Add(this.pnlBottom);
            this.pnlMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlMain.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlMain.Location = new System.Drawing.Point(0, 0);
            this.pnlMain.Name = "pnlMain";
            this.pnlMain.Size = new System.Drawing.Size(800, 421);
            this.pnlMain.TabIndex = 5;
            // 
            // pnlContent
            // 
            this.pnlContent.Controls.Add(this.dgvMain);
            this.pnlContent.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlContent.Location = new System.Drawing.Point(0, 56);
            this.pnlContent.Name = "pnlContent";
            this.pnlContent.Size = new System.Drawing.Size(800, 309);
            this.pnlContent.TabIndex = 1;
            // 
            // dgvMain
            // 
            this.dgvMain.AllowUserToAddRows = false;
            this.dgvMain.AllowUserToDeleteRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.LightSkyBlue;
            dataGridViewCellStyle1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.dgvMain.AutoGenerateColumns = false;
            this.dgvMain.BackgroundColor = System.Drawing.Color.WhiteSmoke;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle2.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvMain.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.dgvMain.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvMain.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.colGroupName,
            this.colmemo});
            this.dgvMain.DataSource = this.bsMain;
            this.dgvMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMain.Location = new System.Drawing.Point(0, 0);
            this.dgvMain.MultiSelect = false;
            this.dgvMain.Name = "dgvMain";
            this.dgvMain.ReadOnly = true;
            this.dgvMain.RowHeadersWidth = 40;
            this.dgvMain.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.RowTemplate.Height = 23;
            this.dgvMain.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvMain.Size = new System.Drawing.Size(800, 309);
            this.dgvMain.TabIndex = 5;
            this.dgvMain.CellMouseClick += new System.Windows.Forms.DataGridViewCellMouseEventHandler(this.dgvMain_CellMouseClick);
            // 
            // colGroupName
            // 
            this.colGroupName.DataPropertyName = "GroupName";
            this.colGroupName.FilteringEnabled = false;
            this.colGroupName.HeaderText = "用户组名称";
            this.colGroupName.Name = "colGroupName";
            this.colGroupName.ReadOnly = true;
            this.colGroupName.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colGroupName.Width = 200;
            // 
            // colmemo
            // 
            this.colmemo.DataPropertyName = "Memo";
            this.colmemo.FilteringEnabled = false;
            this.colmemo.HeaderText = "备注";
            this.colmemo.Name = "colmemo";
            this.colmemo.ReadOnly = true;
            this.colmemo.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colmemo.Width = 260;
            // 
            // pnlTool
            // 
            this.pnlTool.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlTool.Controls.Add(this.toolStrip1);
            this.pnlTool.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlTool.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlTool.Location = new System.Drawing.Point(0, 0);
            this.pnlTool.Name = "pnlTool";
            this.pnlTool.Size = new System.Drawing.Size(800, 56);
            this.pnlTool.TabIndex = 0;
            // 
            // toolStrip1
            // 
            this.toolStrip1.AutoSize = false;
            this.toolStrip1.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButton_Refresh,
            this.toolStripButton_Add,
            this.toolStripButton_Edit,
            this.toolStripButton_Del,
            this.toolStripButton_Close});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(800, 52);
            this.toolStrip1.TabIndex = 2;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripButton_Refresh
            // 
            this.toolStripButton_Refresh.AutoSize = false;
            this.toolStripButton_Refresh.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton_Refresh.Image")));
            this.toolStripButton_Refresh.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Refresh.Name = "toolStripButton_Refresh";
            this.toolStripButton_Refresh.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Refresh.Text = "刷新";
            this.toolStripButton_Refresh.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Refresh.Click += new System.EventHandler(this.toolStripButton_Refresh_Click);
            // 
            // toolStripButton_Add
            // 
            this.toolStripButton_Add.AutoSize = false;
            this.toolStripButton_Add.Image = global::App.Properties.Resources.Add;
            this.toolStripButton_Add.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Add.Name = "toolStripButton_Add";
            this.toolStripButton_Add.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Add.Text = "新增";
            this.toolStripButton_Add.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Add.Click += new System.EventHandler(this.toolStripButton_Add_Click);
            // 
            // toolStripButton_Edit
            // 
            this.toolStripButton_Edit.AutoSize = false;
            this.toolStripButton_Edit.Image = global::App.Properties.Resources.edit;
            this.toolStripButton_Edit.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Edit.Name = "toolStripButton_Edit";
            this.toolStripButton_Edit.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Edit.Text = "修改";
            this.toolStripButton_Edit.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Edit.Click += new System.EventHandler(this.toolStripButton_Edit_Click);
            // 
            // toolStripButton_Del
            // 
            this.toolStripButton_Del.AutoSize = false;
            this.toolStripButton_Del.Image = global::App.Properties.Resources.del;
            this.toolStripButton_Del.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Del.Name = "toolStripButton_Del";
            this.toolStripButton_Del.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Del.Text = "删除";
            this.toolStripButton_Del.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Del.Click += new System.EventHandler(this.toolStripButton_Del_Click);
            // 
            // toolStripButton_Close
            // 
            this.toolStripButton_Close.AutoSize = false;
            this.toolStripButton_Close.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton_Close.Image")));
            this.toolStripButton_Close.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Close.Name = "toolStripButton_Close";
            this.toolStripButton_Close.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Close.Text = "关闭";
            this.toolStripButton_Close.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Close.Click += new System.EventHandler(this.toolStripButton_Close_Click);
            // 
            // pnlBottom
            // 
            this.pnlBottom.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.pnlBottom.Location = new System.Drawing.Point(0, 365);
            this.pnlBottom.Name = "pnlBottom";
            this.pnlBottom.Size = new System.Drawing.Size(800, 56);
            this.pnlBottom.TabIndex = 2;
            this.pnlBottom.Visible = false;
            // 
            // frmGroupList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 421);
            this.ControlBox = false;
            this.Controls.Add(this.pnlMain);
            this.Name = "frmGroupList";
            this.Text = "用户组管理";
            this.Activated += new System.EventHandler(this.frmUserList_Activated);
            this.Load += new System.EventHandler(this.frmUserList_Load);
            this.pnlMain.ResumeLayout(false);
            this.pnlContent.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.pnlTool.ResumeLayout(false);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel pnlMain;
        private System.Windows.Forms.Panel pnlContent;
        private System.Windows.Forms.Panel pnlTool;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButton_Refresh;
        private System.Windows.Forms.ToolStripButton toolStripButton_Edit;
        private System.Windows.Forms.ToolStripButton toolStripButton_Add;
        private System.Windows.Forms.ToolStripButton toolStripButton_Del;
        private System.Windows.Forms.ToolStripButton toolStripButton_Close;
        private System.Windows.Forms.Panel pnlBottom;
        private System.Windows.Forms.DataGridView dgvMain;
        private System.Windows.Forms.BindingSource bsMain;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colGroupName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colmemo;
    }
}