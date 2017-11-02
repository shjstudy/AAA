namespace App.View.Task
{
    partial class frmInventorEdit
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
            this.btndelDetail = new System.Windows.Forms.Button();
            this.btnAddDetail = new System.Windows.Forms.Button();
            this.btnSave = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.txtUpdateDate = new System.Windows.Forms.TextBox();
            this.lblUpdator = new System.Windows.Forms.Label();
            this.txtCreateDate = new System.Windows.Forms.TextBox();
            this.txtUpdate = new System.Windows.Forms.Label();
            this.txtUpdater = new System.Windows.Forms.TextBox();
            this.lblCreateDate = new System.Windows.Forms.Label();
            this.txtCreator = new System.Windows.Forms.TextBox();
            this.lblCreator = new System.Windows.Forms.Label();
            this.txtMemo = new System.Windows.Forms.TextBox();
            this.lblMemo = new System.Windows.Forms.Label();
            this.dtpBillDate = new System.Windows.Forms.DateTimePicker();
            this.txtBillID = new System.Windows.Forms.TextBox();
            this.lblProductName = new System.Windows.Forms.Label();
            this.lblProductCode = new System.Windows.Forms.Label();
            this.panel3 = new System.Windows.Forms.Panel();
            this.dgView = new System.Windows.Forms.DataGridView();
            this.colCheck = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.colRowID = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCellCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCellName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colPalletBarcode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colQty = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnClose = new System.Windows.Forms.Button();
            this.panel2.SuspendLayout();
            this.panel3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // btndelDetail
            // 
            this.btndelDetail.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btndelDetail.Location = new System.Drawing.Point(89, 4);
            this.btndelDetail.Name = "btndelDetail";
            this.btndelDetail.Size = new System.Drawing.Size(80, 30);
            this.btndelDetail.TabIndex = 86;
            this.btndelDetail.Text = "删除明细";
            this.btndelDetail.Click += new System.EventHandler(this.btndelDetail_Click);
            // 
            // btnAddDetail
            // 
            this.btnAddDetail.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnAddDetail.Location = new System.Drawing.Point(3, 4);
            this.btnAddDetail.Name = "btnAddDetail";
            this.btnAddDetail.Size = new System.Drawing.Size(80, 30);
            this.btnAddDetail.TabIndex = 85;
            this.btnAddDetail.Text = "新增明细";
            this.btnAddDetail.Click += new System.EventHandler(this.btnAddDetail_Click);
            // 
            // btnSave
            // 
            this.btnSave.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnSave.Location = new System.Drawing.Point(482, 3);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(80, 30);
            this.btnSave.TabIndex = 83;
            this.btnSave.Text = "保存";
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.txtUpdateDate);
            this.panel2.Controls.Add(this.lblUpdator);
            this.panel2.Controls.Add(this.txtCreateDate);
            this.panel2.Controls.Add(this.txtUpdate);
            this.panel2.Controls.Add(this.txtUpdater);
            this.panel2.Controls.Add(this.lblCreateDate);
            this.panel2.Controls.Add(this.txtCreator);
            this.panel2.Controls.Add(this.lblCreator);
            this.panel2.Controls.Add(this.txtMemo);
            this.panel2.Controls.Add(this.lblMemo);
            this.panel2.Controls.Add(this.dtpBillDate);
            this.panel2.Controls.Add(this.txtBillID);
            this.panel2.Controls.Add(this.lblProductName);
            this.panel2.Controls.Add(this.lblProductCode);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Font = new System.Drawing.Font("微软雅黑", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(661, 95);
            this.panel2.TabIndex = 102;
            // 
            // txtUpdateDate
            // 
            this.txtUpdateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdateDate.Location = new System.Drawing.Point(538, 65);
            this.txtUpdateDate.Name = "txtUpdateDate";
            this.txtUpdateDate.ReadOnly = true;
            this.txtUpdateDate.Size = new System.Drawing.Size(107, 23);
            this.txtUpdateDate.TabIndex = 95;
            // 
            // lblUpdator
            // 
            this.lblUpdator.AutoSize = true;
            this.lblUpdator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblUpdator.Location = new System.Drawing.Point(332, 69);
            this.lblUpdator.Name = "lblUpdator";
            this.lblUpdator.Size = new System.Drawing.Size(56, 17);
            this.lblUpdator.TabIndex = 96;
            this.lblUpdator.Text = "修改人员";
            // 
            // txtCreateDate
            // 
            this.txtCreateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtCreateDate.Location = new System.Drawing.Point(214, 65);
            this.txtCreateDate.Name = "txtCreateDate";
            this.txtCreateDate.ReadOnly = true;
            this.txtCreateDate.Size = new System.Drawing.Size(109, 23);
            this.txtCreateDate.TabIndex = 93;
            // 
            // txtUpdate
            // 
            this.txtUpdate.AutoSize = true;
            this.txtUpdate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdate.Location = new System.Drawing.Point(483, 69);
            this.txtUpdate.Name = "txtUpdate";
            this.txtUpdate.Size = new System.Drawing.Size(56, 17);
            this.txtUpdate.TabIndex = 94;
            this.txtUpdate.Text = "修改日期";
            // 
            // txtUpdater
            // 
            this.txtUpdater.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdater.Location = new System.Drawing.Point(387, 65);
            this.txtUpdater.Name = "txtUpdater";
            this.txtUpdater.ReadOnly = true;
            this.txtUpdater.Size = new System.Drawing.Size(90, 23);
            this.txtUpdater.TabIndex = 97;
            // 
            // lblCreateDate
            // 
            this.lblCreateDate.AutoSize = true;
            this.lblCreateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblCreateDate.Location = new System.Drawing.Point(157, 69);
            this.lblCreateDate.Name = "lblCreateDate";
            this.lblCreateDate.Size = new System.Drawing.Size(56, 17);
            this.lblCreateDate.TabIndex = 92;
            this.lblCreateDate.Text = "建单日期";
            // 
            // txtCreator
            // 
            this.txtCreator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtCreator.Location = new System.Drawing.Point(62, 65);
            this.txtCreator.Name = "txtCreator";
            this.txtCreator.ReadOnly = true;
            this.txtCreator.Size = new System.Drawing.Size(90, 23);
            this.txtCreator.TabIndex = 91;
            // 
            // lblCreator
            // 
            this.lblCreator.AutoSize = true;
            this.lblCreator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblCreator.Location = new System.Drawing.Point(7, 69);
            this.lblCreator.Name = "lblCreator";
            this.lblCreator.Size = new System.Drawing.Size(56, 17);
            this.lblCreator.TabIndex = 90;
            this.lblCreator.Text = "建单人员";
            // 
            // txtMemo
            // 
            this.txtMemo.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtMemo.Location = new System.Drawing.Point(39, 41);
            this.txtMemo.Name = "txtMemo";
            this.txtMemo.Size = new System.Drawing.Size(607, 23);
            this.txtMemo.TabIndex = 89;
            // 
            // lblMemo
            // 
            this.lblMemo.AutoSize = true;
            this.lblMemo.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblMemo.Location = new System.Drawing.Point(7, 45);
            this.lblMemo.Name = "lblMemo";
            this.lblMemo.Size = new System.Drawing.Size(32, 17);
            this.lblMemo.TabIndex = 88;
            this.lblMemo.Text = "备注";
            // 
            // dtpBillDate
            // 
            this.dtpBillDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dtpBillDate.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dtpBillDate.Location = new System.Drawing.Point(39, 12);
            this.dtpBillDate.Name = "dtpBillDate";
            this.dtpBillDate.Size = new System.Drawing.Size(109, 23);
            this.dtpBillDate.TabIndex = 64;
            // 
            // txtBillID
            // 
            this.txtBillID.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtBillID.Location = new System.Drawing.Point(515, 12);
            this.txtBillID.Name = "txtBillID";
            this.txtBillID.Size = new System.Drawing.Size(131, 23);
            this.txtBillID.TabIndex = 63;
            // 
            // lblProductName
            // 
            this.lblProductName.AutoSize = true;
            this.lblProductName.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblProductName.ForeColor = System.Drawing.Color.Blue;
            this.lblProductName.Location = new System.Drawing.Point(459, 17);
            this.lblProductName.Name = "lblProductName";
            this.lblProductName.Size = new System.Drawing.Size(56, 17);
            this.lblProductName.TabIndex = 62;
            this.lblProductName.Text = "出库单号";
            // 
            // lblProductCode
            // 
            this.lblProductCode.AutoSize = true;
            this.lblProductCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblProductCode.ForeColor = System.Drawing.Color.Blue;
            this.lblProductCode.Location = new System.Drawing.Point(7, 17);
            this.lblProductCode.Name = "lblProductCode";
            this.lblProductCode.Size = new System.Drawing.Size(32, 17);
            this.lblProductCode.TabIndex = 61;
            this.lblProductCode.Text = "日期";
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.dgView);
            this.panel3.Controls.Add(this.panel1);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel3.Font = new System.Drawing.Font("微软雅黑", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.panel3.Location = new System.Drawing.Point(0, 95);
            this.panel3.Name = "panel3";
            this.panel3.Padding = new System.Windows.Forms.Padding(3);
            this.panel3.Size = new System.Drawing.Size(661, 320);
            this.panel3.TabIndex = 103;
            // 
            // dgView
            // 
            this.dgView.AllowUserToAddRows = false;
            this.dgView.AllowUserToDeleteRows = false;
            this.dgView.AllowUserToResizeRows = false;
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(255)))), ((int)(((byte)(192)))));
            this.dgView.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.dgView.AutoGenerateColumns = false;
            this.dgView.BackgroundColor = System.Drawing.Color.WhiteSmoke;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle2.BackColor = System.Drawing.SystemColors.Control;
            dataGridViewCellStyle2.Font = new System.Drawing.Font("微软雅黑", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            dataGridViewCellStyle2.ForeColor = System.Drawing.SystemColors.WindowText;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.SystemColors.Highlight;
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.SystemColors.HighlightText;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgView.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.dgView.ColumnHeadersHeight = 30;
            this.dgView.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.colCheck,
            this.colRowID,
            this.colCellCode,
            this.colCellName,
            this.colPalletBarcode,
            this.colProductCode,
            this.colProductName,
            this.colQty});
            this.dgView.DataSource = this.bsMain;
            this.dgView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgView.Location = new System.Drawing.Point(3, 3);
            this.dgView.Name = "dgView";
            this.dgView.RowHeadersVisible = false;
            this.dgView.RowTemplate.Height = 30;
            this.dgView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgView.Size = new System.Drawing.Size(655, 277);
            this.dgView.TabIndex = 102;
            // 
            // colCheck
            // 
            this.colCheck.DataPropertyName = "chk";
            this.colCheck.Frozen = true;
            this.colCheck.HeaderText = "选取";
            this.colCheck.Name = "colCheck";
            this.colCheck.Width = 50;
            // 
            // colRowID
            // 
            this.colRowID.DataPropertyName = "RowID";
            this.colRowID.FilteringEnabled = false;
            this.colRowID.Frozen = true;
            this.colRowID.HeaderText = "序号";
            this.colRowID.Name = "colRowID";
            this.colRowID.ReadOnly = true;
            this.colRowID.Width = 60;
            // 
            // colCellCode
            // 
            this.colCellCode.DataPropertyName = "CellCode";
            this.colCellCode.FilteringEnabled = false;
            this.colCellCode.Frozen = true;
            this.colCellCode.HeaderText = "货位编码";
            this.colCellCode.Name = "colCellCode";
            this.colCellCode.ReadOnly = true;
            // 
            // colCellName
            // 
            this.colCellName.DataPropertyName = "CellName";
            this.colCellName.FilteringEnabled = false;
            this.colCellName.Frozen = true;
            this.colCellName.HeaderText = "货位名称";
            this.colCellName.Name = "colCellName";
            this.colCellName.ReadOnly = true;
            // 
            // colPalletBarcode
            // 
            this.colPalletBarcode.DataPropertyName = "BarCode";
            this.colPalletBarcode.FilteringEnabled = false;
            this.colPalletBarcode.Frozen = true;
            this.colPalletBarcode.HeaderText = "料箱RFID";
            this.colPalletBarcode.Name = "colPalletBarcode";
            this.colPalletBarcode.ReadOnly = true;
            // 
            // colProductCode
            // 
            this.colProductCode.DataPropertyName = "ProductCode";
            this.colProductCode.FilteringEnabled = false;
            this.colProductCode.Frozen = true;
            this.colProductCode.HeaderText = "产品编码";
            this.colProductCode.Name = "colProductCode";
            this.colProductCode.ReadOnly = true;
            // 
            // colProductName
            // 
            this.colProductName.DataPropertyName = "ProductName";
            this.colProductName.FilteringEnabled = false;
            this.colProductName.HeaderText = "产品名称";
            this.colProductName.Name = "colProductName";
            this.colProductName.ReadOnly = true;
            this.colProductName.Width = 150;
            // 
            // colQty
            // 
            this.colQty.DataPropertyName = "Quantity";
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            this.colQty.DefaultCellStyle = dataGridViewCellStyle3;
            this.colQty.FilteringEnabled = false;
            this.colQty.HeaderText = "数量";
            this.colQty.Name = "colQty";
            this.colQty.ReadOnly = true;
            this.colQty.Width = 80;
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.btnAddDetail);
            this.panel1.Controls.Add(this.btndelDetail);
            this.panel1.Controls.Add(this.btnClose);
            this.panel1.Controls.Add(this.btnSave);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(3, 280);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(655, 37);
            this.panel1.TabIndex = 103;
            // 
            // btnClose
            // 
            this.btnClose.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.btnClose.Location = new System.Drawing.Point(566, 3);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(80, 30);
            this.btnClose.TabIndex = 87;
            this.btnClose.Text = "关闭";
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // frmInventorEdit
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(661, 415);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "frmInventorEdit";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "盘点编辑";
            this.Shown += new System.EventHandler(this.frmInventorEdit_Shown);
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.panel3.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.Button btndelDetail;
        private System.Windows.Forms.Button btnAddDetail;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.BindingSource bsMain;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.DataGridView dgView;
        private System.Windows.Forms.DateTimePicker dtpBillDate;
        private System.Windows.Forms.TextBox txtBillID;
        private System.Windows.Forms.Label lblProductName;
        private System.Windows.Forms.Label lblProductCode;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox txtMemo;
        private System.Windows.Forms.Label lblMemo;
        private System.Windows.Forms.TextBox txtUpdateDate;
        private System.Windows.Forms.Label lblUpdator;
        private System.Windows.Forms.TextBox txtCreateDate;
        private System.Windows.Forms.Label txtUpdate;
        private System.Windows.Forms.TextBox txtUpdater;
        private System.Windows.Forms.Label lblCreateDate;
        private System.Windows.Forms.TextBox txtCreator;
        private System.Windows.Forms.Label lblCreator;
        private System.Windows.Forms.DataGridViewCheckBoxColumn colCheck;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colRowID;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCellCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCellName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colPalletBarcode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colQty;
       
    }
}