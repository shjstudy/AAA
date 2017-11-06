namespace App.View.Query
{
    partial class frmTaskQuery
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
            this.pnlMain = new System.Windows.Forms.Panel();
            this.pnlContent = new System.Windows.Forms.Panel();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.colTaskNo = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colBillTypeName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colStateDesc = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colTaskDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colStartDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colFinishedDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCellCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCellName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colPalletBarCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCategoryCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colSpec = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colQty = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlTool = new System.Windows.Forms.Panel();
            this.chk1 = new System.Windows.Forms.CheckBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.rbtAll = new System.Windows.Forms.RadioButton();
            this.rbtInvonter = new System.Windows.Forms.RadioButton();
            this.rbtOutStock = new System.Windows.Forms.RadioButton();
            this.rbtInStcok = new System.Windows.Forms.RadioButton();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.dateTimePicker2 = new System.Windows.Forms.DateTimePicker();
            this.dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
            this.btnExPort = new System.Windows.Forms.Button();
            this.txtSpec = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cmbCategoryCode = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.btnClose = new System.Windows.Forms.Button();
            this.btnQuery = new System.Windows.Forms.Button();
            this.txtProductCode = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.pnlBottom = new System.Windows.Forms.Panel();
            this.pnlMain.SuspendLayout();
            this.pnlContent.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.pnlTool.SuspendLayout();
            this.panel1.SuspendLayout();
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
            this.pnlMain.Size = new System.Drawing.Size(1166, 421);
            this.pnlMain.TabIndex = 5;
            // 
            // pnlContent
            // 
            this.pnlContent.Controls.Add(this.dgvMain);
            this.pnlContent.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlContent.Location = new System.Drawing.Point(0, 37);
            this.pnlContent.Name = "pnlContent";
            this.pnlContent.Size = new System.Drawing.Size(1166, 328);
            this.pnlContent.TabIndex = 1;
            // 
            // dgvMain
            // 
            this.dgvMain.AllowUserToAddRows = false;
            this.dgvMain.AllowUserToDeleteRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(255)))), ((int)(((byte)(192)))));
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
            this.colTaskNo,
            this.colBillTypeName,
            this.colStateDesc,
            this.colTaskDate,
            this.colStartDate,
            this.colFinishedDate,
            this.colCellCode,
            this.colCellName,
            this.colPalletBarCode,
            this.colProductCode,
            this.colProductName,
            this.colCategoryCode,
            this.colSpec,
            this.colQty});
            this.dgvMain.DataSource = this.bsMain;
            this.dgvMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMain.Location = new System.Drawing.Point(0, 0);
            this.dgvMain.MultiSelect = false;
            this.dgvMain.Name = "dgvMain";
            this.dgvMain.ReadOnly = true;
            this.dgvMain.RowHeadersWidth = 20;
            this.dgvMain.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.RowTemplate.Height = 23;
            this.dgvMain.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvMain.Size = new System.Drawing.Size(1166, 328);
            this.dgvMain.TabIndex = 7;
            // 
            // colTaskNo
            // 
            this.colTaskNo.DataPropertyName = "TaskNo";
            this.colTaskNo.FilteringEnabled = false;
            this.colTaskNo.HeaderText = "任务号";
            this.colTaskNo.Name = "colTaskNo";
            this.colTaskNo.ReadOnly = true;
            // 
            // colBillTypeName
            // 
            this.colBillTypeName.DataPropertyName = "BillTypeName";
            this.colBillTypeName.FilteringEnabled = false;
            this.colBillTypeName.HeaderText = "任务类型";
            this.colBillTypeName.Name = "colBillTypeName";
            this.colBillTypeName.ReadOnly = true;
            // 
            // colStateDesc
            // 
            this.colStateDesc.DataPropertyName = "StateDesc";
            this.colStateDesc.FilteringEnabled = false;
            this.colStateDesc.HeaderText = "状态";
            this.colStateDesc.Name = "colStateDesc";
            this.colStateDesc.ReadOnly = true;
            // 
            // colTaskDate
            // 
            this.colTaskDate.DataPropertyName = "TaskDate";
            this.colTaskDate.FilteringEnabled = false;
            this.colTaskDate.HeaderText = "作业时间";
            this.colTaskDate.Name = "colTaskDate";
            this.colTaskDate.ReadOnly = true;
            // 
            // colStartDate
            // 
            this.colStartDate.DataPropertyName = "StartDate";
            this.colStartDate.FilteringEnabled = false;
            this.colStartDate.HeaderText = "开始时间";
            this.colStartDate.Name = "colStartDate";
            this.colStartDate.ReadOnly = true;
            // 
            // colFinishedDate
            // 
            this.colFinishedDate.DataPropertyName = "FinishDate";
            this.colFinishedDate.FilteringEnabled = false;
            this.colFinishedDate.HeaderText = "完成时间";
            this.colFinishedDate.Name = "colFinishedDate";
            this.colFinishedDate.ReadOnly = true;
            // 
            // colCellCode
            // 
            this.colCellCode.DataPropertyName = "CellCode";
            this.colCellCode.FilteringEnabled = false;
            this.colCellCode.HeaderText = "货位编号";
            this.colCellCode.Name = "colCellCode";
            this.colCellCode.ReadOnly = true;
            this.colCellCode.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colCellCode.Width = 110;
            // 
            // colCellName
            // 
            this.colCellName.DataPropertyName = "CellName";
            this.colCellName.FilteringEnabled = false;
            this.colCellName.HeaderText = "货位名称";
            this.colCellName.Name = "colCellName";
            this.colCellName.ReadOnly = true;
            this.colCellName.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colCellName.Width = 110;
            // 
            // colPalletBarCode
            // 
            this.colPalletBarCode.DataPropertyName = "PalletBarCode";
            this.colPalletBarCode.FilteringEnabled = false;
            this.colPalletBarCode.HeaderText = "料箱号";
            this.colPalletBarCode.Name = "colPalletBarCode";
            this.colPalletBarCode.ReadOnly = true;
            this.colPalletBarCode.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colPalletBarCode.Width = 90;
            // 
            // colProductCode
            // 
            this.colProductCode.DataPropertyName = "ProductCode";
            this.colProductCode.FilteringEnabled = false;
            this.colProductCode.HeaderText = "产品编号";
            this.colProductCode.Name = "colProductCode";
            this.colProductCode.ReadOnly = true;
            this.colProductCode.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colProductCode.Width = 110;
            // 
            // colProductName
            // 
            this.colProductName.DataPropertyName = "ProductName";
            this.colProductName.FilteringEnabled = false;
            this.colProductName.HeaderText = "产品名称";
            this.colProductName.Name = "colProductName";
            this.colProductName.ReadOnly = true;
            this.colProductName.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colProductName.Width = 110;
            // 
            // colCategoryCode
            // 
            this.colCategoryCode.DataPropertyName = "CategoryCode";
            this.colCategoryCode.FilteringEnabled = false;
            this.colCategoryCode.HeaderText = "产品类别";
            this.colCategoryCode.Name = "colCategoryCode";
            this.colCategoryCode.ReadOnly = true;
            this.colCategoryCode.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colCategoryCode.Width = 110;
            // 
            // colSpec
            // 
            this.colSpec.DataPropertyName = "Spec";
            this.colSpec.FilteringEnabled = false;
            this.colSpec.HeaderText = "规格";
            this.colSpec.Name = "colSpec";
            this.colSpec.ReadOnly = true;
            this.colSpec.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colSpec.Width = 110;
            // 
            // colQty
            // 
            this.colQty.DataPropertyName = "Qty";
            this.colQty.FilteringEnabled = false;
            this.colQty.HeaderText = "数量";
            this.colQty.Name = "colQty";
            this.colQty.ReadOnly = true;
            this.colQty.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // pnlTool
            // 
            this.pnlTool.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlTool.Controls.Add(this.chk1);
            this.pnlTool.Controls.Add(this.panel1);
            this.pnlTool.Controls.Add(this.label6);
            this.pnlTool.Controls.Add(this.label5);
            this.pnlTool.Controls.Add(this.label4);
            this.pnlTool.Controls.Add(this.dateTimePicker2);
            this.pnlTool.Controls.Add(this.dateTimePicker1);
            this.pnlTool.Controls.Add(this.btnExPort);
            this.pnlTool.Controls.Add(this.txtSpec);
            this.pnlTool.Controls.Add(this.label2);
            this.pnlTool.Controls.Add(this.cmbCategoryCode);
            this.pnlTool.Controls.Add(this.label3);
            this.pnlTool.Controls.Add(this.btnClose);
            this.pnlTool.Controls.Add(this.btnQuery);
            this.pnlTool.Controls.Add(this.txtProductCode);
            this.pnlTool.Controls.Add(this.label1);
            this.pnlTool.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlTool.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlTool.Location = new System.Drawing.Point(0, 0);
            this.pnlTool.Name = "pnlTool";
            this.pnlTool.Size = new System.Drawing.Size(1166, 37);
            this.pnlTool.TabIndex = 0;
            // 
            // chk1
            // 
            this.chk1.AutoSize = true;
            this.chk1.Checked = true;
            this.chk1.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chk1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.chk1.Location = new System.Drawing.Point(840, 10);
            this.chk1.Name = "chk1";
            this.chk1.Size = new System.Drawing.Size(63, 21);
            this.chk1.TabIndex = 57;
            this.chk1.Text = "含空箱";
            this.chk1.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel1.Controls.Add(this.rbtAll);
            this.panel1.Controls.Add(this.rbtInvonter);
            this.panel1.Controls.Add(this.rbtOutStock);
            this.panel1.Controls.Add(this.rbtInStcok);
            this.panel1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.panel1.Location = new System.Drawing.Point(620, 4);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(213, 28);
            this.panel1.TabIndex = 56;
            // 
            // rbtAll
            // 
            this.rbtAll.AutoSize = true;
            this.rbtAll.Location = new System.Drawing.Point(3, 4);
            this.rbtAll.Name = "rbtAll";
            this.rbtAll.Size = new System.Drawing.Size(50, 21);
            this.rbtAll.TabIndex = 7;
            this.rbtAll.TabStop = true;
            this.rbtAll.Text = "全含";
            this.rbtAll.UseVisualStyleBackColor = true;
            // 
            // rbtInvonter
            // 
            this.rbtInvonter.AutoSize = true;
            this.rbtInvonter.Location = new System.Drawing.Point(161, 4);
            this.rbtInvonter.Name = "rbtInvonter";
            this.rbtInvonter.Size = new System.Drawing.Size(50, 21);
            this.rbtInvonter.TabIndex = 6;
            this.rbtInvonter.TabStop = true;
            this.rbtInvonter.Text = "盘点";
            this.rbtInvonter.UseVisualStyleBackColor = true;
            // 
            // rbtOutStock
            // 
            this.rbtOutStock.AutoSize = true;
            this.rbtOutStock.Location = new System.Drawing.Point(108, 4);
            this.rbtOutStock.Name = "rbtOutStock";
            this.rbtOutStock.Size = new System.Drawing.Size(50, 21);
            this.rbtOutStock.TabIndex = 5;
            this.rbtOutStock.TabStop = true;
            this.rbtOutStock.Text = "出库";
            this.rbtOutStock.UseVisualStyleBackColor = true;
            // 
            // rbtInStcok
            // 
            this.rbtInStcok.AutoSize = true;
            this.rbtInStcok.Location = new System.Drawing.Point(55, 4);
            this.rbtInStcok.Name = "rbtInStcok";
            this.rbtInStcok.Size = new System.Drawing.Size(50, 21);
            this.rbtInStcok.TabIndex = 4;
            this.rbtInStcok.TabStop = true;
            this.rbtInStcok.Text = "入库";
            this.rbtInStcok.UseVisualStyleBackColor = true;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label6.Location = new System.Drawing.Point(584, 11);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(32, 17);
            this.label6.TabIndex = 55;
            this.label6.Text = "类型";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label5.Location = new System.Drawing.Point(135, 8);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(17, 17);
            this.label5.TabIndex = 54;
            this.label5.Text = "~";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label4.Location = new System.Drawing.Point(7, 11);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(32, 17);
            this.label4.TabIndex = 19;
            this.label4.Text = "日期";
            // 
            // dateTimePicker2
            // 
            this.dateTimePicker2.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dateTimePicker2.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePicker2.Location = new System.Drawing.Point(154, 8);
            this.dateTimePicker2.Name = "dateTimePicker2";
            this.dateTimePicker2.Size = new System.Drawing.Size(97, 23);
            this.dateTimePicker2.TabIndex = 18;
            // 
            // dateTimePicker1
            // 
            this.dateTimePicker1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dateTimePicker1.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dateTimePicker1.Location = new System.Drawing.Point(39, 8);
            this.dateTimePicker1.Name = "dateTimePicker1";
            this.dateTimePicker1.Size = new System.Drawing.Size(97, 23);
            this.dateTimePicker1.TabIndex = 17;
            // 
            // btnExPort
            // 
            this.btnExPort.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnExPort.Location = new System.Drawing.Point(987, 4);
            this.btnExPort.Name = "btnExPort";
            this.btnExPort.Size = new System.Drawing.Size(75, 27);
            this.btnExPort.TabIndex = 16;
            this.btnExPort.Text = "抛出Excel";
            this.btnExPort.UseVisualStyleBackColor = true;
            this.btnExPort.Click += new System.EventHandler(this.btnExPort_Click);
            // 
            // txtSpec
            // 
            this.txtSpec.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtSpec.Location = new System.Drawing.Point(516, 8);
            this.txtSpec.Name = "txtSpec";
            this.txtSpec.Size = new System.Drawing.Size(63, 23);
            this.txtSpec.TabIndex = 15;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(485, 11);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(32, 17);
            this.label2.TabIndex = 14;
            this.label2.Text = "规格";
            // 
            // cmbCategoryCode
            // 
            this.cmbCategoryCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbCategoryCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cmbCategoryCode.FormattingEnabled = true;
            this.cmbCategoryCode.Location = new System.Drawing.Point(307, 8);
            this.cmbCategoryCode.Name = "cmbCategoryCode";
            this.cmbCategoryCode.Size = new System.Drawing.Size(72, 25);
            this.cmbCategoryCode.TabIndex = 13;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.Location = new System.Drawing.Point(254, 11);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(56, 17);
            this.label3.TabIndex = 12;
            this.label3.Text = "产品类别";
            // 
            // btnClose
            // 
            this.btnClose.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnClose.Location = new System.Drawing.Point(1068, 4);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(75, 27);
            this.btnClose.TabIndex = 11;
            this.btnClose.Text = "关闭";
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // btnQuery
            // 
            this.btnQuery.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnQuery.Location = new System.Drawing.Point(906, 4);
            this.btnQuery.Name = "btnQuery";
            this.btnQuery.Size = new System.Drawing.Size(75, 27);
            this.btnQuery.TabIndex = 10;
            this.btnQuery.Text = "查询";
            this.btnQuery.UseVisualStyleBackColor = true;
            this.btnQuery.Click += new System.EventHandler(this.btnQuery_Click);
            // 
            // txtProductCode
            // 
            this.txtProductCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtProductCode.Location = new System.Drawing.Point(416, 8);
            this.txtProductCode.Name = "txtProductCode";
            this.txtProductCode.Size = new System.Drawing.Size(63, 23);
            this.txtProductCode.TabIndex = 7;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(385, 11);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(32, 17);
            this.label1.TabIndex = 6;
            this.label1.Text = "产品";
            // 
            // pnlBottom
            // 
            this.pnlBottom.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.pnlBottom.Location = new System.Drawing.Point(0, 365);
            this.pnlBottom.Name = "pnlBottom";
            this.pnlBottom.Size = new System.Drawing.Size(1166, 56);
            this.pnlBottom.TabIndex = 2;
            this.pnlBottom.Visible = false;
            // 
            // frmTaskQuery
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1166, 421);
            this.ControlBox = false;
            this.Controls.Add(this.pnlMain);
            this.Name = "frmTaskQuery";
            this.Text = "现有库存查询";
            this.Shown += new System.EventHandler(this.frmStockQuery_Shown);
            this.pnlMain.ResumeLayout(false);
            this.pnlContent.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.pnlTool.ResumeLayout(false);
            this.pnlTool.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel pnlMain;
        private System.Windows.Forms.Panel pnlContent;
        private System.Windows.Forms.Panel pnlTool;
        private System.Windows.Forms.Panel pnlBottom;
        private System.Windows.Forms.BindingSource bsMain;
        private System.Windows.Forms.TextBox txtSpec;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cmbCategoryCode;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Button btnQuery;
        private System.Windows.Forms.TextBox txtProductCode;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.RadioButton rbtOutStock;
        private System.Windows.Forms.RadioButton rbtInStcok;
        private System.Windows.Forms.Button btnExPort;
        private System.Windows.Forms.DataGridView dgvMain;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.DateTimePicker dateTimePicker2;
        private System.Windows.Forms.DateTimePicker dateTimePicker1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.CheckBox chk1;
        private System.Windows.Forms.RadioButton rbtAll;
        private System.Windows.Forms.RadioButton rbtInvonter;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colTaskNo;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colBillTypeName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colStateDesc;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colTaskDate;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colStartDate;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colFinishedDate;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCellCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCellName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colPalletBarCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCategoryCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colSpec;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colQty;
    }
}