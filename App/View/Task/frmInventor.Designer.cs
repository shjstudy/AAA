namespace App.View.Task
{
    partial class frmInventor
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmInventor));
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlMain = new System.Windows.Forms.Panel();
            this.pnlContent = new System.Windows.Forms.Panel();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.Column5 = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.colBillID = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column1 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colState = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column14 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column15 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column10 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column7 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column8 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column13 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column12 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.dataGridViewAutoFilterTextBoxColumn1 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.dataGridViewAutoFilterTextBoxColumn2 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.dataGridViewAutoFilterTextBoxColumn3 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column2 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.Column3 = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.bsDetail = new System.Windows.Forms.BindingSource(this.components);
            this.pnlTool = new System.Windows.Forms.Panel();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButton_Close = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Task = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Del = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Edit = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Add = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Refresh = new System.Windows.Forms.ToolStripButton();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_BillID = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel4 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_CategoryCode = new System.Windows.Forms.ToolStripComboBox();
            this.toolStripLabel2 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_Product = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel3 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_Spec = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripButton_Query = new System.Windows.Forms.ToolStripButton();
            this.pnlBottom = new System.Windows.Forms.Panel();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.pnlMain.SuspendLayout();
            this.pnlContent.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsDetail)).BeginInit();
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
            this.pnlMain.Size = new System.Drawing.Size(1222, 421);
            this.pnlMain.TabIndex = 5;
            // 
            // pnlContent
            // 
            this.pnlContent.Controls.Add(this.splitContainer1);
            this.pnlContent.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlContent.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlContent.Location = new System.Drawing.Point(0, 56);
            this.pnlContent.Name = "pnlContent";
            this.pnlContent.Size = new System.Drawing.Size(1222, 309);
            this.pnlContent.TabIndex = 1;
            // 
            // splitContainer1
            // 
            this.splitContainer1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.dgvMain);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.dataGridView1);
            this.splitContainer1.Size = new System.Drawing.Size(1222, 309);
            this.splitContainer1.SplitterDistance = 780;
            this.splitContainer1.TabIndex = 0;
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
            this.Column5,
            this.colBillID,
            this.Column1,
            this.colState,
            this.Column14,
            this.Column15,
            this.Column10,
            this.Column7,
            this.Column8,
            this.Column13,
            this.Column12});
            this.dgvMain.DataSource = this.bsMain;
            this.dgvMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMain.Location = new System.Drawing.Point(0, 0);
            this.dgvMain.MultiSelect = false;
            this.dgvMain.Name = "dgvMain";
            this.dgvMain.RowHeadersWidth = 20;
            this.dgvMain.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.RowTemplate.Height = 23;
            this.dgvMain.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvMain.Size = new System.Drawing.Size(778, 307);
            this.dgvMain.TabIndex = 6;
            this.dgvMain.RowEnter += new System.Windows.Forms.DataGridViewCellEventHandler(this.dgvMain_RowEnter);
            // 
            // Column5
            // 
            this.Column5.Frozen = true;
            this.Column5.HeaderText = "选取";
            this.Column5.Name = "Column5";
            this.Column5.Width = 50;
            // 
            // colBillID
            // 
            this.colBillID.DataPropertyName = "BillID";
            this.colBillID.FilteringEnabled = false;
            this.colBillID.Frozen = true;
            this.colBillID.HeaderText = "盘点单号";
            this.colBillID.Name = "colBillID";
            this.colBillID.ReadOnly = true;
            this.colBillID.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // Column1
            // 
            this.Column1.DataPropertyName = "BillDate";
            this.Column1.FilteringEnabled = false;
            this.Column1.Frozen = true;
            this.Column1.HeaderText = "日期";
            this.Column1.Name = "Column1";
            this.Column1.ReadOnly = true;
            this.Column1.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // colState
            // 
            this.colState.DataPropertyName = "StateDesc";
            this.colState.FilteringEnabled = false;
            this.colState.Frozen = true;
            this.colState.HeaderText = "状态";
            this.colState.Name = "colState";
            this.colState.ReadOnly = true;
            this.colState.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colState.Width = 80;
            // 
            // Column14
            // 
            this.Column14.DataPropertyName = "Memo";
            this.Column14.FilteringEnabled = false;
            this.Column14.HeaderText = "备注";
            this.Column14.Name = "Column14";
            this.Column14.ReadOnly = true;
            this.Column14.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // Column15
            // 
            this.Column15.DataPropertyName = "Creator";
            this.Column15.FilteringEnabled = false;
            this.Column15.HeaderText = "建单人员";
            this.Column15.Name = "Column15";
            this.Column15.ReadOnly = true;
            this.Column15.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // Column10
            // 
            this.Column10.DataPropertyName = "CreateDate";
            this.Column10.FilteringEnabled = false;
            this.Column10.HeaderText = "建单时间";
            this.Column10.Name = "Column10";
            this.Column10.ReadOnly = true;
            this.Column10.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // Column7
            // 
            this.Column7.DataPropertyName = "Updater";
            this.Column7.FilteringEnabled = false;
            this.Column7.HeaderText = "修改人员";
            this.Column7.Name = "Column7";
            this.Column7.ReadOnly = true;
            // 
            // Column8
            // 
            this.Column8.DataPropertyName = "UpdateDate";
            this.Column8.FilteringEnabled = false;
            this.Column8.HeaderText = "修改日期";
            this.Column8.Name = "Column8";
            this.Column8.ReadOnly = true;
            // 
            // Column13
            // 
            this.Column13.DataPropertyName = "Tasker";
            this.Column13.FilteringEnabled = false;
            this.Column13.HeaderText = "作业人员";
            this.Column13.Name = "Column13";
            this.Column13.ReadOnly = true;
            this.Column13.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // Column12
            // 
            this.Column12.DataPropertyName = "TaskDate";
            this.Column12.FilteringEnabled = false;
            this.Column12.HeaderText = "作业日期";
            this.Column12.Name = "Column12";
            this.Column12.ReadOnly = true;
            this.Column12.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.AllowUserToDeleteRows = false;
            dataGridViewCellStyle3.BackColor = System.Drawing.Color.LightSkyBlue;
            dataGridViewCellStyle3.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dataGridView1.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle3;
            this.dataGridView1.AutoGenerateColumns = false;
            this.dataGridView1.BackgroundColor = System.Drawing.Color.WhiteSmoke;
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle4.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle4.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            dataGridViewCellStyle4.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle4.SelectionBackColor = System.Drawing.Color.Orange;
            dataGridViewCellStyle4.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle4.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dataGridView1.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle4;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.dataGridViewAutoFilterTextBoxColumn1,
            this.dataGridViewAutoFilterTextBoxColumn2,
            this.dataGridViewAutoFilterTextBoxColumn3,
            this.Column2,
            this.colProductCode,
            this.Column3});
            this.dataGridView1.DataSource = this.bsDetail;
            this.dataGridView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridView1.Location = new System.Drawing.Point(0, 0);
            this.dataGridView1.MultiSelect = false;
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 20;
            this.dataGridView1.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(436, 307);
            this.dataGridView1.TabIndex = 8;
            // 
            // dataGridViewAutoFilterTextBoxColumn1
            // 
            this.dataGridViewAutoFilterTextBoxColumn1.DataPropertyName = "RowID";
            this.dataGridViewAutoFilterTextBoxColumn1.FilteringEnabled = false;
            this.dataGridViewAutoFilterTextBoxColumn1.Frozen = true;
            this.dataGridViewAutoFilterTextBoxColumn1.HeaderText = "序号";
            this.dataGridViewAutoFilterTextBoxColumn1.Name = "dataGridViewAutoFilterTextBoxColumn1";
            this.dataGridViewAutoFilterTextBoxColumn1.ReadOnly = true;
            this.dataGridViewAutoFilterTextBoxColumn1.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.dataGridViewAutoFilterTextBoxColumn1.Width = 60;
            // 
            // dataGridViewAutoFilterTextBoxColumn2
            // 
            this.dataGridViewAutoFilterTextBoxColumn2.DataPropertyName = "BarCode";
            this.dataGridViewAutoFilterTextBoxColumn2.FilteringEnabled = false;
            this.dataGridViewAutoFilterTextBoxColumn2.Frozen = true;
            this.dataGridViewAutoFilterTextBoxColumn2.HeaderText = "料箱RFID";
            this.dataGridViewAutoFilterTextBoxColumn2.Name = "dataGridViewAutoFilterTextBoxColumn2";
            this.dataGridViewAutoFilterTextBoxColumn2.ReadOnly = true;
            this.dataGridViewAutoFilterTextBoxColumn2.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // dataGridViewAutoFilterTextBoxColumn3
            // 
            this.dataGridViewAutoFilterTextBoxColumn3.DataPropertyName = "CellCode";
            this.dataGridViewAutoFilterTextBoxColumn3.FilteringEnabled = false;
            this.dataGridViewAutoFilterTextBoxColumn3.Frozen = true;
            this.dataGridViewAutoFilterTextBoxColumn3.HeaderText = "货位";
            this.dataGridViewAutoFilterTextBoxColumn3.Name = "dataGridViewAutoFilterTextBoxColumn3";
            this.dataGridViewAutoFilterTextBoxColumn3.ReadOnly = true;
            // 
            // Column2
            // 
            this.Column2.DataPropertyName = "CellName";
            this.Column2.FilteringEnabled = false;
            this.Column2.Frozen = true;
            this.Column2.HeaderText = "货位名称";
            this.Column2.Name = "Column2";
            // 
            // colProductCode
            // 
            this.colProductCode.DataPropertyName = "ProductCode";
            this.colProductCode.FilteringEnabled = false;
            this.colProductCode.HeaderText = "产品编号";
            this.colProductCode.Name = "colProductCode";
            // 
            // Column3
            // 
            this.Column3.DataPropertyName = "ProductName";
            this.Column3.FilteringEnabled = false;
            this.Column3.HeaderText = "产品名称";
            this.Column3.Name = "Column3";
            // 
            // pnlTool
            // 
            this.pnlTool.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlTool.Controls.Add(this.toolStrip1);
            this.pnlTool.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlTool.Location = new System.Drawing.Point(0, 0);
            this.pnlTool.Name = "pnlTool";
            this.pnlTool.Size = new System.Drawing.Size(1222, 56);
            this.pnlTool.TabIndex = 0;
            // 
            // toolStrip1
            // 
            this.toolStrip1.AutoSize = false;
            this.toolStrip1.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButton_Close,
            this.toolStripButton_Task,
            this.toolStripButton_Del,
            this.toolStripButton_Edit,
            this.toolStripButton_Add,
            this.toolStripButton_Refresh,
            this.toolStripLabel1,
            this.toolStrip_BillID,
            this.toolStripLabel4,
            this.toolStrip_CategoryCode,
            this.toolStripLabel2,
            this.toolStrip_Product,
            this.toolStripLabel3,
            this.toolStrip_Spec,
            this.toolStripButton_Query});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(1222, 52);
            this.toolStrip1.TabIndex = 3;
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
            // toolStripButton_Task
            // 
            this.toolStripButton_Task.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Task.AutoSize = false;
            this.toolStripButton_Task.Image = global::App.Properties.Resources.process;
            this.toolStripButton_Task.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Task.Name = "toolStripButton_Task";
            this.toolStripButton_Task.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Task.Text = "盘点作业";
            this.toolStripButton_Task.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Task.Click += new System.EventHandler(this.toolStripButton_Task_Click);
            // 
            // toolStripButton_Del
            // 
            this.toolStripButton_Del.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Del.AutoSize = false;
            this.toolStripButton_Del.Image = global::App.Properties.Resources.del;
            this.toolStripButton_Del.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Del.Name = "toolStripButton_Del";
            this.toolStripButton_Del.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Del.Text = "删除";
            this.toolStripButton_Del.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Del.Click += new System.EventHandler(this.toolStripButton_Del_Click);
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
            // toolStripButton_Add
            // 
            this.toolStripButton_Add.Alignment = System.Windows.Forms.ToolStripItemAlignment.Right;
            this.toolStripButton_Add.AutoSize = false;
            this.toolStripButton_Add.Image = global::App.Properties.Resources.Add;
            this.toolStripButton_Add.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton_Add.Name = "toolStripButton_Add";
            this.toolStripButton_Add.Size = new System.Drawing.Size(60, 50);
            this.toolStripButton_Add.Text = "添加";
            this.toolStripButton_Add.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.toolStripButton_Add.Click += new System.EventHandler(this.toolStripButton_Add_Click);
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
            this.toolStripLabel1.Size = new System.Drawing.Size(56, 49);
            this.toolStripLabel1.Text = "出库单号";
            // 
            // toolStrip_BillID
            // 
            this.toolStrip_BillID.Name = "toolStrip_BillID";
            this.toolStrip_BillID.Size = new System.Drawing.Size(100, 52);
            // 
            // toolStripLabel4
            // 
            this.toolStripLabel4.Name = "toolStripLabel4";
            this.toolStripLabel4.Size = new System.Drawing.Size(56, 49);
            this.toolStripLabel4.Text = "产品类别";
            // 
            // toolStrip_CategoryCode
            // 
            this.toolStrip_CategoryCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.toolStrip_CategoryCode.Name = "toolStrip_CategoryCode";
            this.toolStrip_CategoryCode.Size = new System.Drawing.Size(121, 52);
            // 
            // toolStripLabel2
            // 
            this.toolStripLabel2.Name = "toolStripLabel2";
            this.toolStripLabel2.Size = new System.Drawing.Size(32, 49);
            this.toolStripLabel2.Text = "产品";
            // 
            // toolStrip_Product
            // 
            this.toolStrip_Product.Name = "toolStrip_Product";
            this.toolStrip_Product.Size = new System.Drawing.Size(100, 52);
            // 
            // toolStripLabel3
            // 
            this.toolStripLabel3.Name = "toolStripLabel3";
            this.toolStripLabel3.Size = new System.Drawing.Size(32, 49);
            this.toolStripLabel3.Text = "规格";
            // 
            // toolStrip_Spec
            // 
            this.toolStrip_Spec.Name = "toolStrip_Spec";
            this.toolStrip_Spec.Size = new System.Drawing.Size(100, 52);
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
            this.toolStripButton_Query.Click += new System.EventHandler(this.toolStripButton_Query_Click);
            // 
            // pnlBottom
            // 
            this.pnlBottom.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.pnlBottom.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlBottom.Location = new System.Drawing.Point(0, 365);
            this.pnlBottom.Name = "pnlBottom";
            this.pnlBottom.Size = new System.Drawing.Size(1222, 56);
            this.pnlBottom.TabIndex = 2;
            this.pnlBottom.Visible = false;
            // 
            // frmInventor
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1222, 421);
            this.ControlBox = false;
            this.Controls.Add(this.pnlMain);
            this.Name = "frmInventor";
            this.Text = "盘点任务";
            this.Shown += new System.EventHandler(this.frmInventor_Shown);
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.pnlMain.ResumeLayout(false);
            this.pnlContent.ResumeLayout(false);
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsDetail)).EndInit();
            this.pnlTool.ResumeLayout(false);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel pnlMain;
        private System.Windows.Forms.Panel pnlContent;
        private System.Windows.Forms.Panel pnlTool;
        private System.Windows.Forms.Panel pnlBottom;
        private System.Windows.Forms.BindingSource bsMain;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButton_Refresh;
        private System.Windows.Forms.ToolStripButton toolStripButton_Query;
        private System.Windows.Forms.ToolStripButton toolStripButton_Add;
        private System.Windows.Forms.ToolStripButton toolStripButton_Edit;
        private System.Windows.Forms.ToolStripButton toolStripButton_Del;
        private System.Windows.Forms.ToolStripButton toolStripButton_Task;
        private System.Windows.Forms.ToolStripButton toolStripButton_Close;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.DataGridView dgvMain;
        private System.Windows.Forms.BindingSource bsDetail;
        private System.Windows.Forms.DataGridViewCheckBoxColumn Column5;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colBillID;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column1;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colState;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column14;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column15;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column10;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column7;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column8;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column13;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column12;
        private System.Windows.Forms.DataGridView dataGridView1;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn dataGridViewAutoFilterTextBoxColumn1;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn dataGridViewAutoFilterTextBoxColumn2;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn dataGridViewAutoFilterTextBoxColumn3;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column2;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn Column3;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.ToolStripTextBox toolStrip_BillID;
        private System.Windows.Forms.ToolStripLabel toolStripLabel4;
        private System.Windows.Forms.ToolStripComboBox toolStrip_CategoryCode;
        private System.Windows.Forms.ToolStripLabel toolStripLabel2;
        private System.Windows.Forms.ToolStripTextBox toolStrip_Product;
        private System.Windows.Forms.ToolStripLabel toolStripLabel3;
        private System.Windows.Forms.ToolStripTextBox toolStrip_Spec;
    }
}