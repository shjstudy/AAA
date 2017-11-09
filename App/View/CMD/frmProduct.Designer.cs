namespace App.View.CMD
{
    partial class frmProduct
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmProduct));
            this.bsMain = new System.Windows.Forms.BindingSource(this.components);
            this.pnlContext = new System.Windows.Forms.Panel();
            this.dgvMain = new System.Windows.Forms.DataGridView();
            this.ColCheck = new System.Windows.Forms.DataGridViewCheckBoxColumn();
            this.colProductCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colProductName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCategoryCode = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colCategoryName = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colSpec = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColUnit = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColMemo = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.colSafeQty = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColCreator = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColCreateDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColUpdater = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.ColUpdateDate = new DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn();
            this.pnlTop = new System.Windows.Forms.Panel();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripButton_Close = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Del = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Edit = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Add = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton_Refresh = new System.Windows.Forms.ToolStripButton();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_ProductCode = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel2 = new System.Windows.Forms.ToolStripLabel();
            this.toolStrip_ProductName = new System.Windows.Forms.ToolStripTextBox();
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
            this.pnlContext.Location = new System.Drawing.Point(0, 52);
            this.pnlContext.Name = "pnlContext";
            this.pnlContext.Size = new System.Drawing.Size(1230, 554);
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
            this.ColCheck,
            this.colProductCode,
            this.colProductName,
            this.colCategoryCode,
            this.colCategoryName,
            this.colSpec,
            this.ColUnit,
            this.ColMemo,
            this.colSafeQty,
            this.ColCreator,
            this.ColCreateDate,
            this.ColUpdater,
            this.ColUpdateDate});
            this.dgvMain.DataSource = this.bsMain;
            this.dgvMain.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvMain.Location = new System.Drawing.Point(0, 0);
            this.dgvMain.MultiSelect = false;
            this.dgvMain.Name = "dgvMain";
            this.dgvMain.RowHeadersWidth = 20;
            this.dgvMain.RowTemplate.DefaultCellStyle.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.dgvMain.RowTemplate.Height = 23;
            this.dgvMain.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvMain.Size = new System.Drawing.Size(1230, 554);
            this.dgvMain.TabIndex = 6;
            // 
            // ColCheck
            // 
            this.ColCheck.HeaderText = "选择";
            this.ColCheck.Name = "ColCheck";
            this.ColCheck.Width = 50;
            // 
            // colProductCode
            // 
            this.colProductCode.DataPropertyName = "ProductCode";
            this.colProductCode.FilteringEnabled = false;
            this.colProductCode.HeaderText = "产品编号";
            this.colProductCode.Name = "colProductCode";
            this.colProductCode.ReadOnly = true;
            this.colProductCode.Width = 110;
            // 
            // colProductName
            // 
            this.colProductName.DataPropertyName = "ProductName";
            this.colProductName.FilteringEnabled = false;
            this.colProductName.HeaderText = "产品名称";
            this.colProductName.Name = "colProductName";
            this.colProductName.ReadOnly = true;
            this.colProductName.Width = 120;
            // 
            // colCategoryCode
            // 
            this.colCategoryCode.DataPropertyName = "CategoryCode";
            this.colCategoryCode.FilteringEnabled = false;
            this.colCategoryCode.HeaderText = "产品类别";
            this.colCategoryCode.Name = "colCategoryCode";
            this.colCategoryCode.ReadOnly = true;
            this.colCategoryCode.Width = 110;
            // 
            // colCategoryName
            // 
            this.colCategoryName.DataPropertyName = "CategoryName";
            this.colCategoryName.FilteringEnabled = false;
            this.colCategoryName.HeaderText = "类别名称";
            this.colCategoryName.Name = "colCategoryName";
            this.colCategoryName.ReadOnly = true;
            this.colCategoryName.Width = 110;
            // 
            // colSpec
            // 
            this.colSpec.DataPropertyName = "Spec";
            this.colSpec.FilteringEnabled = false;
            this.colSpec.HeaderText = "规格";
            this.colSpec.Name = "colSpec";
            this.colSpec.ReadOnly = true;
            // 
            // ColUnit
            // 
            this.ColUnit.DataPropertyName = "Unit";
            this.ColUnit.FilteringEnabled = false;
            this.ColUnit.HeaderText = "单位";
            this.ColUnit.Name = "ColUnit";
            this.ColUnit.ReadOnly = true;
            this.ColUnit.Resizable = System.Windows.Forms.DataGridViewTriState.True;
            this.ColUnit.Width = 80;
            // 
            // ColMemo
            // 
            this.ColMemo.DataPropertyName = "Memo";
            this.ColMemo.FilteringEnabled = false;
            this.ColMemo.HeaderText = "备注";
            this.ColMemo.Name = "ColMemo";
            this.ColMemo.ReadOnly = true;
            // 
            // colSafeQty
            // 
            this.colSafeQty.DataPropertyName = "SafeQty";
            this.colSafeQty.FilteringEnabled = false;
            this.colSafeQty.HeaderText = "安全存量";
            this.colSafeQty.Name = "colSafeQty";
            this.colSafeQty.ReadOnly = true;
            // 
            // ColCreator
            // 
            this.ColCreator.DataPropertyName = "Creator";
            this.ColCreator.FilteringEnabled = false;
            this.ColCreator.HeaderText = "建单人员";
            this.ColCreator.Name = "ColCreator";
            this.ColCreator.ReadOnly = true;
            this.ColCreator.Width = 110;
            // 
            // ColCreateDate
            // 
            this.ColCreateDate.DataPropertyName = "CreateDate";
            this.ColCreateDate.FilteringEnabled = false;
            this.ColCreateDate.HeaderText = "建单日期";
            this.ColCreateDate.Name = "ColCreateDate";
            this.ColCreateDate.ReadOnly = true;
            this.ColCreateDate.Width = 110;
            // 
            // ColUpdater
            // 
            this.ColUpdater.DataPropertyName = "Updater";
            this.ColUpdater.FilteringEnabled = false;
            this.ColUpdater.HeaderText = "修改人员";
            this.ColUpdater.Name = "ColUpdater";
            this.ColUpdater.ReadOnly = true;
            this.ColUpdater.Width = 110;
            // 
            // ColUpdateDate
            // 
            this.ColUpdateDate.DataPropertyName = "UpdateDate";
            this.ColUpdateDate.FilteringEnabled = false;
            this.ColUpdateDate.HeaderText = "修改日期";
            this.ColUpdateDate.Name = "ColUpdateDate";
            this.ColUpdateDate.ReadOnly = true;
            this.ColUpdateDate.Width = 110;
            // 
            // pnlTop
            // 
            this.pnlTop.Controls.Add(this.toolStrip1);
            this.pnlTop.Dock = System.Windows.Forms.DockStyle.Top;
            this.pnlTop.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.pnlTop.Location = new System.Drawing.Point(0, 0);
            this.pnlTop.Name = "pnlTop";
            this.pnlTop.Size = new System.Drawing.Size(1230, 52);
            this.pnlTop.TabIndex = 0;
            // 
            // toolStrip1
            // 
            this.toolStrip1.AutoSize = false;
            this.toolStrip1.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButton_Close,
            this.toolStripButton_Del,
            this.toolStripButton_Edit,
            this.toolStripButton_Add,
            this.toolStripButton_Refresh,
            this.toolStripLabel1,
            this.toolStrip_ProductCode,
            this.toolStripLabel2,
            this.toolStrip_ProductName,
            this.toolStripButton_Query});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(1230, 52);
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
            this.toolStripLabel1.Text = "产品编号";
            // 
            // toolStrip_ProductCode
            // 
            this.toolStrip_ProductCode.Name = "toolStrip_ProductCode";
            this.toolStrip_ProductCode.Size = new System.Drawing.Size(100, 52);
            // 
            // toolStripLabel2
            // 
            this.toolStripLabel2.Name = "toolStripLabel2";
            this.toolStripLabel2.Size = new System.Drawing.Size(56, 49);
            this.toolStripLabel2.Text = "产品名称";
            // 
            // toolStrip_ProductName
            // 
            this.toolStrip_ProductName.Name = "toolStrip_ProductName";
            this.toolStrip_ProductName.Size = new System.Drawing.Size(100, 52);
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
            // frmProduct
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1230, 606);
            this.ControlBox = false;
            this.Controls.Add(this.pnlContext);
            this.Controls.Add(this.pnlTop);
            this.Name = "frmProduct";
            this.Text = "产品资料";
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
        private System.Windows.Forms.DataGridViewCheckBoxColumn ColCheck;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colProductName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCategoryCode;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colCategoryName;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colSpec;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColUnit;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColMemo;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn colSafeQty;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColCreator;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColCreateDate;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColUpdater;
        private DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn ColUpdateDate;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton toolStripButton_Refresh;
        private System.Windows.Forms.ToolStripButton toolStripButton_Query;
        private System.Windows.Forms.ToolStripButton toolStripButton_Add;
        private System.Windows.Forms.ToolStripButton toolStripButton_Edit;
        private System.Windows.Forms.ToolStripButton toolStripButton_Del;
        private System.Windows.Forms.ToolStripButton toolStripButton_Close;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.ToolStripTextBox toolStrip_ProductCode;
        private System.Windows.Forms.ToolStripLabel toolStripLabel2;
        private System.Windows.Forms.ToolStripTextBox toolStrip_ProductName;
    }
}