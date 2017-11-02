namespace App.View.Task
{
    partial class frmCarTask
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
            this.btnOutTask = new System.Windows.Forms.Button();
            this.btnCancel = new System.Windows.Forms.Button();
            this.btnStationNo2 = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.cmbAisleNo = new System.Windows.Forms.ComboBox();
            this.label13 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.txtTaskNo1 = new System.Windows.Forms.TextBox();
            this.cmbTaskType = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.cmbCarNo = new System.Windows.Forms.ComboBox();
            this.cmbStationNo = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.cbFromRow = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.cbFromColumn = new System.Windows.Forms.ComboBox();
            this.label4 = new System.Windows.Forms.Label();
            this.cbFromHeight = new System.Windows.Forms.ComboBox();
            this.label9 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.cbToHeight = new System.Windows.Forms.ComboBox();
            this.label6 = new System.Windows.Forms.Label();
            this.cbToColumn = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cbToRow = new System.Windows.Forms.ComboBox();
            this.label10 = new System.Windows.Forms.Label();
            this.btnClose = new System.Windows.Forms.Button();
            this.btnAction = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnOutTask
            // 
            this.btnOutTask.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnOutTask.Location = new System.Drawing.Point(314, 354);
            this.btnOutTask.Name = "btnOutTask";
            this.btnOutTask.Size = new System.Drawing.Size(75, 32);
            this.btnOutTask.TabIndex = 99;
            this.btnOutTask.Text = "生成出库任务";
            this.btnOutTask.UseVisualStyleBackColor = true;
            this.btnOutTask.Click += new System.EventHandler(this.btnOutTask_Click);
            // 
            // btnCancel
            // 
            this.btnCancel.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnCancel.Location = new System.Drawing.Point(10, 354);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(75, 32);
            this.btnCancel.TabIndex = 98;
            this.btnCancel.Text = "取消任务";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // btnStationNo2
            // 
            this.btnStationNo2.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnStationNo2.Location = new System.Drawing.Point(162, 354);
            this.btnStationNo2.Name = "btnStationNo2";
            this.btnStationNo2.Size = new System.Drawing.Size(86, 32);
            this.btnStationNo2.TabIndex = 97;
            this.btnStationNo2.Text = "出库站台2";
            this.btnStationNo2.UseVisualStyleBackColor = true;
            this.btnStationNo2.Click += new System.EventHandler(this.btnStationNo2_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.cmbAisleNo);
            this.groupBox1.Controls.Add(this.label13);
            this.groupBox1.Controls.Add(this.label11);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Controls.Add(this.txtTaskNo1);
            this.groupBox1.Controls.Add(this.cmbTaskType);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.cmbCarNo);
            this.groupBox1.Controls.Add(this.cmbStationNo);
            this.groupBox1.Controls.Add(this.label8);
            this.groupBox1.Controls.Add(this.label12);
            this.groupBox1.Controls.Add(this.cbFromRow);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.cbFromColumn);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.cbFromHeight);
            this.groupBox1.Controls.Add(this.label9);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.cbToHeight);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.cbToColumn);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.cbToRow);
            this.groupBox1.Controls.Add(this.label10);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(382, 336);
            this.groupBox1.TabIndex = 96;
            this.groupBox1.TabStop = false;
            // 
            // cmbAisleNo
            // 
            this.cmbAisleNo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbAisleNo.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cmbAisleNo.FormattingEnabled = true;
            this.cmbAisleNo.Location = new System.Drawing.Point(97, 87);
            this.cmbAisleNo.Name = "cmbAisleNo";
            this.cmbAisleNo.Size = new System.Drawing.Size(118, 28);
            this.cmbAisleNo.TabIndex = 96;
            this.cmbAisleNo.SelectedIndexChanged += new System.EventHandler(this.cmbAisleNo_SelectedIndexChanged);
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label13.Location = new System.Drawing.Point(23, 91);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(65, 20);
            this.label13.TabIndex = 95;
            this.label13.Text = "巷道编号";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label11.Location = new System.Drawing.Point(23, 26);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(65, 20);
            this.label11.TabIndex = 91;
            this.label11.Text = "小车编号";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label5.Location = new System.Drawing.Point(23, 157);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(65, 20);
            this.label5.TabIndex = 24;
            this.label5.Text = "任务类型";
            // 
            // txtTaskNo1
            // 
            this.txtTaskNo1.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.txtTaskNo1.Location = new System.Drawing.Point(98, 122);
            this.txtTaskNo1.Name = "txtTaskNo1";
            this.txtTaskNo1.ReadOnly = true;
            this.txtTaskNo1.Size = new System.Drawing.Size(117, 26);
            this.txtTaskNo1.TabIndex = 21;
            this.txtTaskNo1.Text = "2015101101";
            // 
            // cmbTaskType
            // 
            this.cmbTaskType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbTaskType.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cmbTaskType.FormattingEnabled = true;
            this.cmbTaskType.Items.AddRange(new object[] {
            "1定位",
            "2左伸取货",
            "3右伸取货",
            "4左伸放货",
            "5右伸放货",
            "6左伸",
            "7右伸",
            "8收叉",
            "9移库",
            "10入库",
            "11出库"});
            this.cmbTaskType.Location = new System.Drawing.Point(99, 153);
            this.cmbTaskType.Name = "cmbTaskType";
            this.cmbTaskType.Size = new System.Drawing.Size(118, 28);
            this.cmbTaskType.TabIndex = 93;
            this.cmbTaskType.SelectedIndexChanged += new System.EventHandler(this.cmbTaskType_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.Location = new System.Drawing.Point(23, 125);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 20);
            this.label1.TabIndex = 22;
            this.label1.Text = "任务编号";
            // 
            // cmbCarNo
            // 
            this.cmbCarNo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbCarNo.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cmbCarNo.FormattingEnabled = true;
            this.cmbCarNo.Location = new System.Drawing.Point(97, 22);
            this.cmbCarNo.Name = "cmbCarNo";
            this.cmbCarNo.Size = new System.Drawing.Size(118, 28);
            this.cmbCarNo.TabIndex = 92;
            this.cmbCarNo.SelectedIndexChanged += new System.EventHandler(this.cmbCraneNo_SelectedIndexChanged);
            // 
            // cmbStationNo
            // 
            this.cmbStationNo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbStationNo.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cmbStationNo.FormattingEnabled = true;
            this.cmbStationNo.Location = new System.Drawing.Point(97, 54);
            this.cmbStationNo.Name = "cmbStationNo";
            this.cmbStationNo.Size = new System.Drawing.Size(118, 28);
            this.cmbStationNo.TabIndex = 90;
            this.cmbStationNo.SelectedIndexChanged += new System.EventHandler(this.cmbStationNo_SelectedIndexChanged);
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label8.Location = new System.Drawing.Point(99, 198);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(37, 20);
            this.label8.TabIndex = 63;
            this.label8.Text = "货架";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label12.Location = new System.Drawing.Point(23, 58);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(65, 20);
            this.label12.TabIndex = 89;
            this.label12.Text = "站台编号";
            // 
            // cbFromRow
            // 
            this.cbFromRow.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbFromRow.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbFromRow.FormattingEnabled = true;
            this.cbFromRow.Items.AddRange(new object[] {
            "001",
            "002",
            "003",
            "004",
            "005",
            "006",
            "007",
            "008",
            "009",
            "010",
            "011",
            "012"});
            this.cbFromRow.Location = new System.Drawing.Point(98, 221);
            this.cbFromRow.Name = "cbFromRow";
            this.cbFromRow.Size = new System.Drawing.Size(76, 28);
            this.cbFromRow.TabIndex = 64;
            this.cbFromRow.SelectedIndexChanged += new System.EventHandler(this.cbFromRow_SelectedIndexChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.Location = new System.Drawing.Point(23, 286);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 20);
            this.label3.TabIndex = 76;
            this.label3.Text = "目标位置";
            // 
            // cbFromColumn
            // 
            this.cbFromColumn.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbFromColumn.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbFromColumn.FormattingEnabled = true;
            this.cbFromColumn.Location = new System.Drawing.Point(178, 221);
            this.cbFromColumn.Name = "cbFromColumn";
            this.cbFromColumn.Size = new System.Drawing.Size(52, 28);
            this.cbFromColumn.TabIndex = 65;
            this.cbFromColumn.SelectedIndexChanged += new System.EventHandler(this.cbFromColumn_SelectedIndexChanged);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label4.Location = new System.Drawing.Point(232, 263);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(23, 20);
            this.label4.TabIndex = 75;
            this.label4.Text = "层";
            // 
            // cbFromHeight
            // 
            this.cbFromHeight.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbFromHeight.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbFromHeight.FormattingEnabled = true;
            this.cbFromHeight.Location = new System.Drawing.Point(234, 221);
            this.cbFromHeight.Name = "cbFromHeight";
            this.cbFromHeight.Size = new System.Drawing.Size(50, 28);
            this.cbFromHeight.TabIndex = 66;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label9.Location = new System.Drawing.Point(175, 263);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(23, 20);
            this.label9.TabIndex = 74;
            this.label9.Text = "列";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label7.Location = new System.Drawing.Point(175, 198);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(23, 20);
            this.label7.TabIndex = 67;
            this.label7.Text = "列";
            // 
            // cbToHeight
            // 
            this.cbToHeight.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbToHeight.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbToHeight.FormattingEnabled = true;
            this.cbToHeight.Location = new System.Drawing.Point(234, 286);
            this.cbToHeight.Name = "cbToHeight";
            this.cbToHeight.Size = new System.Drawing.Size(50, 28);
            this.cbToHeight.TabIndex = 73;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label6.Location = new System.Drawing.Point(232, 198);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(23, 20);
            this.label6.TabIndex = 68;
            this.label6.Text = "层";
            // 
            // cbToColumn
            // 
            this.cbToColumn.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbToColumn.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbToColumn.FormattingEnabled = true;
            this.cbToColumn.Location = new System.Drawing.Point(178, 286);
            this.cbToColumn.Name = "cbToColumn";
            this.cbToColumn.Size = new System.Drawing.Size(52, 28);
            this.cbToColumn.TabIndex = 72;
            this.cbToColumn.SelectedIndexChanged += new System.EventHandler(this.cbToColumn_SelectedIndexChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.Location = new System.Drawing.Point(23, 221);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 20);
            this.label2.TabIndex = 69;
            this.label2.Text = "起始位置";
            // 
            // cbToRow
            // 
            this.cbToRow.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cbToRow.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.cbToRow.FormattingEnabled = true;
            this.cbToRow.Items.AddRange(new object[] {
            "001",
            "002",
            "003",
            "004",
            "005",
            "006",
            "007",
            "008",
            "009",
            "010",
            "011",
            "012"});
            this.cbToRow.Location = new System.Drawing.Point(98, 286);
            this.cbToRow.Name = "cbToRow";
            this.cbToRow.Size = new System.Drawing.Size(76, 28);
            this.cbToRow.TabIndex = 71;
            this.cbToRow.SelectedIndexChanged += new System.EventHandler(this.cbToRow_SelectedIndexChanged);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label10.Location = new System.Drawing.Point(99, 263);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(37, 20);
            this.label10.TabIndex = 70;
            this.label10.Text = "货架";
            // 
            // btnClose
            // 
            this.btnClose.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnClose.Location = new System.Drawing.Point(249, 354);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(62, 32);
            this.btnClose.TabIndex = 95;
            this.btnClose.Text = "关闭";
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // btnAction
            // 
            this.btnAction.Font = new System.Drawing.Font("Microsoft YaHei", 10.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.btnAction.Location = new System.Drawing.Point(87, 354);
            this.btnAction.Name = "btnAction";
            this.btnAction.Size = new System.Drawing.Size(75, 32);
            this.btnAction.TabIndex = 94;
            this.btnAction.Text = "下达任务";
            this.btnAction.UseVisualStyleBackColor = true;
            this.btnAction.Click += new System.EventHandler(this.btnAction_Click);
            // 
            // frmCarTask
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(414, 390);
            this.Controls.Add(this.btnOutTask);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.btnStationNo2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.btnAction);
            this.Name = "frmCarTask";
            this.Text = "穿梭车任务操作";
            this.Load += new System.EventHandler(this.frmCarTask_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtTaskNo1;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.ComboBox cbFromHeight;
        private System.Windows.Forms.ComboBox cbFromColumn;
        private System.Windows.Forms.ComboBox cbFromRow;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ComboBox cbToHeight;
        private System.Windows.Forms.ComboBox cbToColumn;
        private System.Windows.Forms.ComboBox cbToRow;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ComboBox cmbCarNo;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.ComboBox cmbStationNo;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.ComboBox cmbTaskType;
        private System.Windows.Forms.Button btnAction;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.ComboBox cmbAisleNo;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Button btnOutTask;
        private System.Windows.Forms.Button btnStationNo2;
    }
}