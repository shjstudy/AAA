namespace App.View.Query
{
    partial class frmSafeQtyQuery
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
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlMain = new System.Windows.Forms.Panel();
            this.pnlContent = new System.Windows.Forms.Panel();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.colProductCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCategoryCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colSpec = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colQty = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colSafeQty = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.pnlTool = new System.Windows.Forms.Panel();
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
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).BeginInit();
            this.pnlMain.SuspendLayout();
            this.pnlContent.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).BeginInit();
            this.pnlTool.SuspendLayout();
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
            this.pnlMain.Size = new System.Drawing.Size(940, 421);
            this.pnlMain.TabIndex = 5;
            // 
            // pnlContent
            // 
            this.pnlContent.Controls.Add(this.dgvMain);
            this.pnlContent.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pnlContent.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlContent.Location = new System.Drawing.Point(0, 37);
            this.pnlContent.Name = "pnlContent";
            this.pnlContent.Size = new System.Drawing.Size(940, 328);
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
            this.colProductCode,
            this.colProductName,
            this.colCategoryCode,
            this.colSpec,
            this.colQty,
            this.colSafeQty});
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
            this.dgvMain.Size = new System.Drawing.Size(940, 328);
            this.dgvMain.TabIndex = 7;
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
            this.colQty.HeaderText = "现有数量";
            this.colQty.Name = "colQty";
            this.colQty.ReadOnly = true;
            this.colQty.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            // 
            // colSafeQty
            // 
            this.colSafeQty.DataPropertyName = "SafeQty";
            this.colSafeQty.FilteringEnabled = false;
            this.colSafeQty.HeaderText = "安全存量";
            this.colSafeQty.Name = "colSafeQty";
            this.colSafeQty.ReadOnly = true;
            this.colSafeQty.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.colSafeQty.Width = 110;
            // 
            // pnlTool
            // 
            this.pnlTool.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
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
            this.pnlTool.Size = new System.Drawing.Size(940, 37);
            this.pnlTool.TabIndex = 0;
            // 
            // btnExPort
            // 
            this.btnExPort.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnExPort.Location = new System.Drawing.Point(656, 5);
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
            this.txtSpec.Location = new System.Drawing.Point(328, 8);
            this.txtSpec.Name = "txtSpec";
            this.txtSpec.Size = new System.Drawing.Size(90, 23);
            this.txtSpec.TabIndex = 15;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(297, 11);
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
            this.cmbCategoryCode.Location = new System.Drawing.Point(64, 8);
            this.cmbCategoryCode.Name = "cmbCategoryCode";
            this.cmbCategoryCode.Size = new System.Drawing.Size(100, 25);
            this.cmbCategoryCode.TabIndex = 13;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.Location = new System.Drawing.Point(11, 11);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(56, 17);
            this.label3.TabIndex = 12;
            this.label3.Text = "产品类别";
            // 
            // btnClose
            // 
            this.btnClose.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnClose.Location = new System.Drawing.Point(737, 5);
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
            this.btnQuery.Location = new System.Drawing.Point(576, 5);
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
            this.txtProductCode.Location = new System.Drawing.Point(201, 8);
            this.txtProductCode.Name = "txtProductCode";
            this.txtProductCode.Size = new System.Drawing.Size(90, 23);
            this.txtProductCode.TabIndex = 7;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(170, 11);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(32, 17);
            this.label1.TabIndex = 6;
            this.label1.Text = "产品";
            // 
            // pnlBottom
            // 
            this.pnlBottom.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.pnlBottom.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.pnlBottom.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlBottom.Location = new System.Drawing.Point(0, 365);
            this.pnlBottom.Name = "pnlBottom";
            this.pnlBottom.Size = new System.Drawing.Size(940, 56);
            this.pnlBottom.TabIndex = 2;
            this.pnlBottom.Visible = false;
            // 
            // frmSafeQtyQuery
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(940, 421);
            this.ControlBox = false;
            this.Controls.Add(this.pnlMain);
            this.Name = "frmSafeQtyQuery";
            this.Text = "现有库存查询";
            this.Shown += new System.EventHandler(this.frmStockQuery_Shown);
            ((System.ComponentModel.ISupportInitialize)(this.bsMain)).EndInit();
            this.pnlMain.ResumeLayout(false);
            this.pnlContent.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvMain)).EndInit();
            this.pnlTool.ResumeLayout(false);
            this.pnlTool.PerformLayout();
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
        private System.Windows.Forms.Button btnExPort;
        private System.Windows.Forms.DataGridView dgvMain;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCategoryCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colSpec;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colQty;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colSafeQty;
        
    }
}