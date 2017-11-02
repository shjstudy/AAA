namespace SingleReaderTest
{
    partial class FormScanConfig
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
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.pa4 = new System.Windows.Forms.ComboBox();
            this.btnQuery = new System.Windows.Forms.Button();
            this.btnSet = new System.Windows.Forms.Button();
            this.pa3 = new System.Windows.Forms.ComboBox();
            this.pa2 = new System.Windows.Forms.ComboBox();
            this.lblpa4 = new System.Windows.Forms.Label();
            this.lblpa3 = new System.Windows.Forms.Label();
            this.lblpa2 = new System.Windows.Forms.Label();
            this.lblpa1 = new System.Windows.Forms.Label();
            this.pa1 = new System.Windows.Forms.ComboBox();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.pa4);
            this.groupBox1.Controls.Add(this.btnQuery);
            this.groupBox1.Controls.Add(this.btnSet);
            this.groupBox1.Controls.Add(this.pa3);
            this.groupBox1.Controls.Add(this.pa2);
            this.groupBox1.Controls.Add(this.lblpa4);
            this.groupBox1.Controls.Add(this.lblpa3);
            this.groupBox1.Controls.Add(this.lblpa2);
            this.groupBox1.Controls.Add(this.lblpa1);
            this.groupBox1.Controls.Add(this.pa1);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(197, 113);
            this.groupBox1.TabIndex = 33;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "天线功率：(dBm)";
            // 
            // pa4
            // 
            this.pa4.FormattingEnabled = true;
            this.pa4.Location = new System.Drawing.Point(126, 45);
            this.pa4.Name = "pa4";
            this.pa4.Size = new System.Drawing.Size(44, 20);
            this.pa4.TabIndex = 34;
            // 
            // btnQuery
            // 
            this.btnQuery.Location = new System.Drawing.Point(16, 71);
            this.btnQuery.Name = "btnQuery";
            this.btnQuery.Size = new System.Drawing.Size(75, 23);
            this.btnQuery.TabIndex = 32;
            this.btnQuery.Text = "查询";
            this.btnQuery.UseVisualStyleBackColor = true;
            this.btnQuery.Click += new System.EventHandler(this.btnQuery_Click);
            // 
            // btnSet
            // 
            this.btnSet.Location = new System.Drawing.Point(97, 71);
            this.btnSet.Name = "btnSet";
            this.btnSet.Size = new System.Drawing.Size(75, 23);
            this.btnSet.TabIndex = 32;
            this.btnSet.Text = "设置";
            this.btnSet.UseVisualStyleBackColor = true;
            this.btnSet.Click += new System.EventHandler(this.btnSet_Click);
            // 
            // pa3
            // 
            this.pa3.FormattingEnabled = true;
            this.pa3.Location = new System.Drawing.Point(126, 19);
            this.pa3.Name = "pa3";
            this.pa3.Size = new System.Drawing.Size(44, 20);
            this.pa3.TabIndex = 34;
            // 
            // pa2
            // 
            this.pa2.FormattingEnabled = true;
            this.pa2.Location = new System.Drawing.Point(41, 45);
            this.pa2.Name = "pa2";
            this.pa2.Size = new System.Drawing.Size(44, 20);
            this.pa2.TabIndex = 34;
            // 
            // lblpa4
            // 
            this.lblpa4.AutoSize = true;
            this.lblpa4.Location = new System.Drawing.Point(90, 49);
            this.lblpa4.Name = "lblpa4";
            this.lblpa4.Size = new System.Drawing.Size(29, 12);
            this.lblpa4.TabIndex = 30;
            this.lblpa4.Text = "4#：";
            // 
            // lblpa3
            // 
            this.lblpa3.AutoSize = true;
            this.lblpa3.Location = new System.Drawing.Point(91, 23);
            this.lblpa3.Name = "lblpa3";
            this.lblpa3.Size = new System.Drawing.Size(29, 12);
            this.lblpa3.TabIndex = 30;
            this.lblpa3.Text = "3#：";
            // 
            // lblpa2
            // 
            this.lblpa2.AutoSize = true;
            this.lblpa2.Location = new System.Drawing.Point(6, 49);
            this.lblpa2.Name = "lblpa2";
            this.lblpa2.Size = new System.Drawing.Size(29, 12);
            this.lblpa2.TabIndex = 30;
            this.lblpa2.Text = "2#：";
            // 
            // lblpa1
            // 
            this.lblpa1.AutoSize = true;
            this.lblpa1.Location = new System.Drawing.Point(6, 23);
            this.lblpa1.Name = "lblpa1";
            this.lblpa1.Size = new System.Drawing.Size(29, 12);
            this.lblpa1.TabIndex = 30;
            this.lblpa1.Text = "1#：";
            // 
            // pa1
            // 
            this.pa1.FormattingEnabled = true;
            this.pa1.Location = new System.Drawing.Point(41, 19);
            this.pa1.Name = "pa1";
            this.pa1.Size = new System.Drawing.Size(44, 20);
            this.pa1.TabIndex = 34;
            // 
            // FormScanConfig
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(233, 150);
            this.Controls.Add(this.groupBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormScanConfig";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "功率配置";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.ComboBox pa4;
        private System.Windows.Forms.ComboBox pa3;
        private System.Windows.Forms.ComboBox pa2;
        private System.Windows.Forms.Label lblpa4;
        private System.Windows.Forms.Label lblpa3;
        private System.Windows.Forms.Label lblpa2;
        private System.Windows.Forms.Label lblpa1;
        private System.Windows.Forms.ComboBox pa1;
        private System.Windows.Forms.Button btnSet;
        private System.Windows.Forms.Button btnQuery;
    }
}