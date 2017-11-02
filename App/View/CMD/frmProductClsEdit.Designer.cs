namespace App.View.CMD
{
    partial class frmProductClsEdit
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
            this.btnClose = new System.Windows.Forms.Button();
            this.btnSave = new System.Windows.Forms.Button();
            this.txtUpdater = new System.Windows.Forms.TextBox();
            this.lblUpdator = new System.Windows.Forms.Label();
            this.txtUpdateDate = new System.Windows.Forms.TextBox();
            this.txtUpdate = new System.Windows.Forms.Label();
            this.txtCreateDate = new System.Windows.Forms.TextBox();
            this.lblCreateDate = new System.Windows.Forms.Label();
            this.txtCreator = new System.Windows.Forms.TextBox();
            this.lblCreator = new System.Windows.Forms.Label();
            this.txtMemo = new System.Windows.Forms.TextBox();
            this.lblMemo = new System.Windows.Forms.Label();
            this.txtProductTypeName = new System.Windows.Forms.TextBox();
            this.lblProductName = new System.Windows.Forms.Label();
            this.txtProductTypeCode = new System.Windows.Forms.TextBox();
            this.lblProductCode = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txtErpCode = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // btnClose
            // 
            this.btnClose.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnClose.Location = new System.Drawing.Point(292, 151);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(75, 34);
            this.btnClose.TabIndex = 23;
            this.btnClose.Text = "关闭";
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // btnSave
            // 
            this.btnSave.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnSave.Location = new System.Drawing.Point(110, 151);
            this.btnSave.Name = "btnSave";
            this.btnSave.Size = new System.Drawing.Size(75, 34);
            this.btnSave.TabIndex = 22;
            this.btnSave.Text = "保存";
            this.btnSave.UseVisualStyleBackColor = true;
            this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
            // 
            // txtUpdater
            // 
            this.txtUpdater.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdater.Location = new System.Drawing.Point(69, 97);
            this.txtUpdater.Name = "txtUpdater";
            this.txtUpdater.ReadOnly = true;
            this.txtUpdater.Size = new System.Drawing.Size(142, 23);
            this.txtUpdater.TabIndex = 15;
            // 
            // lblUpdator
            // 
            this.lblUpdator.AutoSize = true;
            this.lblUpdator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblUpdator.Location = new System.Drawing.Point(11, 101);
            this.lblUpdator.Name = "lblUpdator";
            this.lblUpdator.Size = new System.Drawing.Size(56, 17);
            this.lblUpdator.TabIndex = 14;
            this.lblUpdator.Text = "修改人员";
            // 
            // txtUpdateDate
            // 
            this.txtUpdateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdateDate.Location = new System.Drawing.Point(292, 97);
            this.txtUpdateDate.Name = "txtUpdateDate";
            this.txtUpdateDate.ReadOnly = true;
            this.txtUpdateDate.Size = new System.Drawing.Size(195, 23);
            this.txtUpdateDate.TabIndex = 13;
            // 
            // txtUpdate
            // 
            this.txtUpdate.AutoSize = true;
            this.txtUpdate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtUpdate.Location = new System.Drawing.Point(236, 101);
            this.txtUpdate.Name = "txtUpdate";
            this.txtUpdate.Size = new System.Drawing.Size(56, 17);
            this.txtUpdate.TabIndex = 12;
            this.txtUpdate.Text = "修改日期";
            // 
            // txtCreateDate
            // 
            this.txtCreateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtCreateDate.Location = new System.Drawing.Point(292, 70);
            this.txtCreateDate.Name = "txtCreateDate";
            this.txtCreateDate.ReadOnly = true;
            this.txtCreateDate.Size = new System.Drawing.Size(195, 23);
            this.txtCreateDate.TabIndex = 11;
            // 
            // lblCreateDate
            // 
            this.lblCreateDate.AutoSize = true;
            this.lblCreateDate.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblCreateDate.Location = new System.Drawing.Point(236, 74);
            this.lblCreateDate.Name = "lblCreateDate";
            this.lblCreateDate.Size = new System.Drawing.Size(56, 17);
            this.lblCreateDate.TabIndex = 10;
            this.lblCreateDate.Text = "建单日期";
            // 
            // txtCreator
            // 
            this.txtCreator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtCreator.Location = new System.Drawing.Point(69, 70);
            this.txtCreator.Name = "txtCreator";
            this.txtCreator.ReadOnly = true;
            this.txtCreator.Size = new System.Drawing.Size(142, 23);
            this.txtCreator.TabIndex = 9;
            // 
            // lblCreator
            // 
            this.lblCreator.AutoSize = true;
            this.lblCreator.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblCreator.Location = new System.Drawing.Point(12, 74);
            this.lblCreator.Name = "lblCreator";
            this.lblCreator.Size = new System.Drawing.Size(56, 17);
            this.lblCreator.TabIndex = 8;
            this.lblCreator.Text = "建单人员";
            // 
            // txtMemo
            // 
            this.txtMemo.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtMemo.Location = new System.Drawing.Point(292, 43);
            this.txtMemo.Name = "txtMemo";
            this.txtMemo.Size = new System.Drawing.Size(195, 23);
            this.txtMemo.TabIndex = 20;
            // 
            // lblMemo
            // 
            this.lblMemo.AutoSize = true;
            this.lblMemo.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblMemo.Location = new System.Drawing.Point(260, 47);
            this.lblMemo.Name = "lblMemo";
            this.lblMemo.Size = new System.Drawing.Size(32, 17);
            this.lblMemo.TabIndex = 19;
            this.lblMemo.Text = "备注";
            // 
            // txtProductTypeName
            // 
            this.txtProductTypeName.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtProductTypeName.Location = new System.Drawing.Point(292, 16);
            this.txtProductTypeName.Name = "txtProductTypeName";
            this.txtProductTypeName.Size = new System.Drawing.Size(195, 23);
            this.txtProductTypeName.TabIndex = 18;
            // 
            // lblProductName
            // 
            this.lblProductName.AutoSize = true;
            this.lblProductName.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblProductName.ForeColor = System.Drawing.Color.Blue;
            this.lblProductName.Location = new System.Drawing.Point(236, 20);
            this.lblProductName.Name = "lblProductName";
            this.lblProductName.Size = new System.Drawing.Size(56, 17);
            this.lblProductName.TabIndex = 17;
            this.lblProductName.Text = "类别名称";
            // 
            // txtProductTypeCode
            // 
            this.txtProductTypeCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtProductTypeCode.Location = new System.Drawing.Point(69, 16);
            this.txtProductTypeCode.Name = "txtProductTypeCode";
            this.txtProductTypeCode.Size = new System.Drawing.Size(142, 23);
            this.txtProductTypeCode.TabIndex = 16;
            // 
            // lblProductCode
            // 
            this.lblProductCode.AutoSize = true;
            this.lblProductCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.lblProductCode.ForeColor = System.Drawing.Color.Blue;
            this.lblProductCode.Location = new System.Drawing.Point(12, 20);
            this.lblProductCode.Name = "lblProductCode";
            this.lblProductCode.Size = new System.Drawing.Size(56, 17);
            this.lblProductCode.TabIndex = 15;
            this.lblProductCode.Text = "类别编号";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(12, 47);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(56, 17);
            this.label1.TabIndex = 24;
            this.label1.Text = "上传编码";
            // 
            // txtErpCode
            // 
            this.txtErpCode.Font = new System.Drawing.Font("微软雅黑", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtErpCode.Location = new System.Drawing.Point(69, 43);
            this.txtErpCode.Name = "txtErpCode";
            this.txtErpCode.Size = new System.Drawing.Size(142, 23);
            this.txtErpCode.TabIndex = 25;
            // 
            // frmProductClsEdit
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(499, 202);
            this.Controls.Add(this.txtUpdateDate);
            this.Controls.Add(this.lblUpdator);
            this.Controls.Add(this.txtCreateDate);
            this.Controls.Add(this.txtUpdate);
            this.Controls.Add(this.txtUpdater);
            this.Controls.Add(this.txtErpCode);
            this.Controls.Add(this.lblCreateDate);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.btnSave);
            this.Controls.Add(this.txtMemo);
            this.Controls.Add(this.txtCreator);
            this.Controls.Add(this.lblMemo);
            this.Controls.Add(this.lblCreator);
            this.Controls.Add(this.txtProductTypeName);
            this.Controls.Add(this.lblProductName);
            this.Controls.Add(this.txtProductTypeCode);
            this.Controls.Add(this.lblProductCode);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "frmProductClsEdit";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "产品类别编辑";
            this.Load += new System.EventHandler(this.frmProductClsEdit_Load);
            this.Shown += new System.EventHandler(this.frmProductClsEdit_Shown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Button btnSave;
        private System.Windows.Forms.TextBox txtUpdater;
        private System.Windows.Forms.Label lblUpdator;
        private System.Windows.Forms.TextBox txtUpdateDate;
        private System.Windows.Forms.Label txtUpdate;
        private System.Windows.Forms.TextBox txtCreateDate;
        private System.Windows.Forms.Label lblCreateDate;
        private System.Windows.Forms.TextBox txtCreator;
        private System.Windows.Forms.Label lblCreator;
        private System.Windows.Forms.TextBox txtMemo;
        private System.Windows.Forms.Label lblMemo;
        private System.Windows.Forms.TextBox txtProductTypeName;
        private System.Windows.Forms.Label lblProductName;
        private System.Windows.Forms.TextBox txtProductTypeCode;
        private System.Windows.Forms.Label lblProductCode;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtErpCode;
    }
}