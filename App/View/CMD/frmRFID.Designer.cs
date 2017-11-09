namespace App.View.CMD
{
    partial class frmRFID
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmRFID));
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlContext = new System.Windows.Forms.Panel();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.colCellCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colRFID = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCreator = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCreateDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColUpdater = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColUpdateDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.pnlTop = new System.Windows.Forms.Panel();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButton_Close = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Edit = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Refresh = new System.Windows.Forms.ToolStripButton();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_RFIDCode = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel3 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_PalletID = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripButton_Query = new System.Windows.Forms.ToolStripButton();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.pnlContext.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).BeginInit();
            this.pnlTop.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // pnlContext
            // 
            this.pnlContext.Controls.Add(this.dgvMain);
            this.pnlContext.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlContext.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlContext.Location = new System.Drawing.Point(0, 78);
            this.pnlContext.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.pnlContext.Name = "pnlContext";
            this.pnlContext.Size = new System.Drawing.Size(1845, 831);
            this.pnlContext.TabIndex = 2;
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
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.Color.Orange;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvMain.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.dgvMain.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvMain.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.colCellCode,
            this.colRFID,
            this.colCreator,
            this.colCreateDate,
            this.ColUpdater,
            this.ColUpdateDate});
            this.dgvMain.DataSource = this.bsMain;
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle3.BackColor = System.Drawing.SystemColors.Window;
            dataGridViewCellStyle3.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            dataGridViewCellStyle3.ForeColor = System.Drawing.SystemColors.ControlText;
            dataGridViewCellStyle3.SelectionBackColor = System.Drawing.Color.Orange;
            dataGridViewCellStyle3.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvMain.DefaultCellStyle = dataGridViewCellStyle3;
            this.dgvMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMain.Location = new System.Drawing.Point(0, 0);
            this.dgvMain.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.dgvMain.MultiSelect = false;
            this.dgvMain.Name = "dgvMain";
            this.dgvMain.ReadOnly = true;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle4.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle4.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.Color.Orange;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvMain.RowHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.dgvMain.RowHeadersWidth = 20;
            this.dgvMain.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.RowTemplate.DefaultCellStyle.SelectionBackColor = System.Drawing.Color.Orange;
            this.dgvMain.RowTemplate.Height = 23;
            this.dgvMain.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvMain.Size = new System.Drawing.Size(1845, 831);
            this.dgvMain.TabIndex = 6;
            // 
            // colCellCode
            // 
            this.colCellCode.DataPropertyName = "PalletID";
            this.colCellCode.FilteringEnabled = false;
            this.colCellCode.HeaderText = "料箱号";
            this.colCellCode.Name = "colCellCode";
            this.colCellCode.ReadOnly = true;
            this.colCellCode.Width = 120;
            // 
            // colRFID
            // 
            this.colRFID.DataPropertyName = "RFIDCode";
            this.colRFID.FilteringEnabled = false;
            this.colRFID.HeaderText = "RFID编号";
            this.colRFID.Name = "colRFID";
            this.colRFID.ReadOnly = true;
            this.colRFID.Width = 280;
            // 
            // colCreator
            // 
            this.colCreator.DataPropertyName = "Creator";
            this.colCreator.FilteringEnabled = false;
            this.colCreator.HeaderText = "建单人员";
            this.colCreator.Name = "colCreator";
            this.colCreator.ReadOnly = true;
            // 
            // colCreateDate
            // 
            this.colCreateDate.DataPropertyName = "CreateDate";
            this.colCreateDate.FilteringEnabled = false;
            this.colCreateDate.HeaderText = "建单日期";
            this.colCreateDate.Name = "colCreateDate";
            this.colCreateDate.ReadOnly = true;
            // 
            // ColUpdater
            // 
            this.ColUpdater.DataPropertyName = "Updater";
            this.ColUpdater.FilteringEnabled = false;
            this.ColUpdater.HeaderText = "修改人员";
            this.ColUpdater.Name = "ColUpdater";
            this.ColUpdater.ReadOnly = true;
            // 
            // ColUpdateDate
            // 
            this.ColUpdateDate.DataPropertyName = "UpdateDate";
            this.ColUpdateDate.FilteringEnabled = false;
            this.ColUpdateDate.HeaderText = "修改日期";
            this.ColUpdateDate.Name = "ColUpdateDate";
            this.ColUpdateDate.ReadOnly = true;
            // 
            // pnlTop
            // 
            this.pnlTop.Controls.Add(this.toolStrip1);
            this.pnlTop.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlTop.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlTop.Location = new System.Drawing.Point(0, 0);
            this.pnlTop.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.pnlTop.Name = "pnlTop";
            this.pnlTop.Size = new System.Drawing.Size(1845, 78);
            this.pnlTop.TabIndex = 0;
            // 
            // toolStrip1
            // 
            this.toolStrip1.AutoSize = false;
            this.toolStrip1.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButton_Close,
            this.toolStripButton_Edit,
            this.toolStripButton_Refresh,
            this.toolStripLabel1,
            this.toolStrip_RFIDCode,
            this.toolStripLabel3,
            this.toolStrip_PalletID,
            this.toolStripButton_Query});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Padding = new System.Windows.Forms.Padding(0, 0, 2, 0);
            this.toolStrip1.Size = new System.Drawing.Size(1845, 78);
            this.toolStrip1.TabIndex = 5;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripButton_Close
            // 
            this.toolStripButton_Close.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Close.AutoSize = false;
            this.toolStripButton_Close.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton_Close.Image")));
            this.toolStripButton_Close.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Close.Name = "toolStripButton_Close";
            this.toolStripButton_Close.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Close.Text = "关闭";
            this.toolStripButton_Close.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Close.Click += new System.EventHandler(this.toolStripButton_Close_Click);
            // 
            // toolStripButton_Edit
            // 
            this.toolStripButton_Edit.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Edit.AutoSize = false;
            this.toolStripButton_Edit.Image = global::App.Properties.Resources.edit;
            this.toolStripButton_Edit.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Edit.Name = "toolStripButton_Edit";
            this.toolStripButton_Edit.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Edit.Text = "编辑";
            this.toolStripButton_Edit.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Edit.Click += new System.EventHandler(this.toolStripButton_Edit_Click);
            // 
            // toolStripButton_Refresh
            // 
            this.toolStripButton_Refresh.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Refresh.AutoSize = false;
            this.toolStripButton_Refresh.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton_Refresh.Image")));
            this.toolStripButton_Refresh.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Refresh.Name = "toolStripButton_Refresh";
            this.toolStripButton_Refresh.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Refresh.Text = "刷新";
            this.toolStripButton_Refresh.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Refresh.Click += new System.EventHandler(this.toolStripButton_Refresh_Click);
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(87, 75);
            this.toolStripLabel1.Text = "RFID编号";
            // 
            // toolStrip_RFIDCode
            // 
            this.toolStrip_RFIDCode.Name = "toolStrip_RFIDCode";
            this.toolStrip_RFIDCode.Size = new System.Drawing.Size(148, 78);
            // 
            // toolStripLabel3
            // 
            this.toolStripLabel3.Name = "toolStripLabel3";
            this.toolStripLabel3.Size = new System.Drawing.Size(64, 75);
            this.toolStripLabel3.Text = "料箱号";
            // 
            // toolStrip_PalletID
            // 
            this.toolStrip_PalletID.Name = "toolStrip_PalletID";
            this.toolStrip_PalletID.Size = new System.Drawing.Size(148, 78);
            // 
            // toolStripButton_Query
            // 
            this.toolStripButton_Query.AutoSize = false;
            this.toolStripButton_Query.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.toolStripButton_Query.Image = global::App.Properties.Resources.zoom;
            this.toolStripButton_Query.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Query.Name = "toolStripButton_Query";
            this.toolStripButton_Query.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Query.Text = "查询";
            this.toolStripButton_Query.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            // 
            // frmRFID
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 18F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1845, 909);
            this.ControlBox = false;
            this.Controls.Add(this.pnlContext);
            this.Controls.Add(this.pnlTop);
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "frmRFID";
            this.Text = "料箱RFID设定";
            this.Shown += new System.EventHandler(this.frmProduct_Shown);
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.pnlContext.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).EndInit();
            this.pnlTop.ResumeLayout(false);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel pnlTop;
        private System.Windows.Forms.Panel pnlContext;
        private System.Windows.Forms.BindingSource bsMain;
        private System.Windows.Forms.DataGridView dgvMain;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButton_Close;
        private System.Windows.Forms.ToolStripButton toolStripButton_Edit;
        private System.Windows.Forms.ToolStripButton toolStripButton_Refresh;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.ToolStripTextBox toolStrip_RFIDCode;
        private System.Windows.Forms.ToolStripLabel toolStripLabel3;
        private System.Windows.Forms.ToolStripTextBox toolStrip_PalletID;
        private System.Windows.Forms.ToolStripButton toolStripButton_Query;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCellCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colRFID;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCreator;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCreateDate;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColUpdater;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColUpdateDate;
    }
}