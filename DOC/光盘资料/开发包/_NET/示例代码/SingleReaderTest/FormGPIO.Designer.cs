namespace SingleReaderTest
{
    partial class FormGPIO
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
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.numTime = new System.Windows.Forms.NumericUpDown();
            this.cmbox2 = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.rbTime = new System.Windows.Forms.RadioButton();
            this.radioButton1 = new System.Windows.Forms.RadioButton();
            this.label4 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.btnIOTriggerQuery = new System.Windows.Forms.Button();
            this.btnIOTriggerConfig = new System.Windows.Forms.Button();
            this.cmbox1 = new System.Windows.Forms.ComboBox();
            this.label13 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.textBox9 = new System.Windows.Forms.TextBox();
            this.btn_Query = new System.Windows.Forms.Button();
            this.comboBox10 = new System.Windows.Forms.ComboBox();
            this.comboBox5 = new System.Windows.Forms.ComboBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBox8 = new System.Windows.Forms.TextBox();
            this.comboBox9 = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.comboBox7 = new System.Windows.Forms.ComboBox();
            this.label7 = new System.Windows.Forms.Label();
            this.btn_Config = new System.Windows.Forms.Button();
            this.cbIO4 = new System.Windows.Forms.CheckBox();
            this.cbIO3 = new System.Windows.Forms.CheckBox();
            this.cbIO2 = new System.Windows.Forms.CheckBox();
            this.comboBox4 = new System.Windows.Forms.ComboBox();
            this.comboBox2 = new System.Windows.Forms.ComboBox();
            this.comboBox3 = new System.Windows.Forms.ComboBox();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.cbIO1 = new System.Windows.Forms.CheckBox();
            this.label5 = new System.Windows.Forms.Label();
            this.groupBox3.SuspendLayout();
            this.groupBox4.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numTime)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.label5);
            this.groupBox3.Controls.Add(this.groupBox4);
            this.groupBox3.Controls.Add(this.btnIOTriggerQuery);
            this.groupBox3.Controls.Add(this.btnIOTriggerConfig);
            this.groupBox3.Controls.Add(this.cmbox1);
            this.groupBox3.Controls.Add(this.label13);
            this.groupBox3.Location = new System.Drawing.Point(12, 180);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(398, 176);
            this.groupBox3.TabIndex = 13;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "I/O触发读标签配置：";
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.numTime);
            this.groupBox4.Controls.Add(this.cmbox2);
            this.groupBox4.Controls.Add(this.label3);
            this.groupBox4.Controls.Add(this.rbTime);
            this.groupBox4.Controls.Add(this.radioButton1);
            this.groupBox4.Controls.Add(this.label4);
            this.groupBox4.Controls.Add(this.label18);
            this.groupBox4.Location = new System.Drawing.Point(11, 46);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(373, 92);
            this.groupBox4.TabIndex = 13;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "触发配置：";
            // 
            // numTime
            // 
            this.numTime.Location = new System.Drawing.Point(173, 46);
            this.numTime.Maximum = new decimal(new int[] {
            65535,
            0,
            0,
            0});
            this.numTime.Name = "numTime";
            this.numTime.Size = new System.Drawing.Size(98, 21);
            this.numTime.TabIndex = 13;
            // 
            // cmbox2
            // 
            this.cmbox2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbox2.FormattingEnabled = true;
            this.cmbox2.Items.AddRange(new object[] {
            "关闭",
            "上升沿触发",
            "下降沿触发",
            "高电平触发",
            "低电平触发"});
            this.cmbox2.Location = new System.Drawing.Point(95, 20);
            this.cmbox2.Name = "cmbox2";
            this.cmbox2.Size = new System.Drawing.Size(176, 20);
            this.cmbox2.TabIndex = 12;
            this.cmbox2.SelectedIndexChanged += new System.EventHandler(this.cmbox2_SelectedIndexChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(277, 52);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 12);
            this.label3.TabIndex = 3;
            this.label3.Text = "(0.1s)";
            // 
            // rbTime
            // 
            this.rbTime.AutoSize = true;
            this.rbTime.Location = new System.Drawing.Point(95, 48);
            this.rbTime.Name = "rbTime";
            this.rbTime.Size = new System.Drawing.Size(71, 16);
            this.rbTime.TabIndex = 0;
            this.rbTime.Text = "延时时间";
            this.rbTime.UseVisualStyleBackColor = true;
            this.rbTime.CheckedChanged += new System.EventHandler(this.rbTime_CheckedChanged);
            // 
            // radioButton1
            // 
            this.radioButton1.AutoSize = true;
            this.radioButton1.Checked = true;
            this.radioButton1.Location = new System.Drawing.Point(95, 72);
            this.radioButton1.Name = "radioButton1";
            this.radioButton1.Size = new System.Drawing.Size(71, 16);
            this.radioButton1.TabIndex = 0;
            this.radioButton1.TabStop = true;
            this.radioButton1.Text = "端口电平";
            this.radioButton1.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(24, 48);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(65, 12);
            this.label4.TabIndex = 9;
            this.label4.Text = "停止条件：";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Location = new System.Drawing.Point(24, 24);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(65, 12);
            this.label18.TabIndex = 9;
            this.label18.Text = "触发条件：";
            // 
            // btnIOTriggerQuery
            // 
            this.btnIOTriggerQuery.Location = new System.Drawing.Point(231, 144);
            this.btnIOTriggerQuery.Name = "btnIOTriggerQuery";
            this.btnIOTriggerQuery.Size = new System.Drawing.Size(75, 23);
            this.btnIOTriggerQuery.TabIndex = 8;
            this.btnIOTriggerQuery.Text = "查询";
            this.btnIOTriggerQuery.UseVisualStyleBackColor = true;
            this.btnIOTriggerQuery.Click += new System.EventHandler(this.btnIOTriggerQuery_Click);
            // 
            // btnIOTriggerConfig
            // 
            this.btnIOTriggerConfig.Location = new System.Drawing.Point(309, 144);
            this.btnIOTriggerConfig.Name = "btnIOTriggerConfig";
            this.btnIOTriggerConfig.Size = new System.Drawing.Size(75, 23);
            this.btnIOTriggerConfig.TabIndex = 8;
            this.btnIOTriggerConfig.Text = "配置";
            this.btnIOTriggerConfig.UseVisualStyleBackColor = true;
            this.btnIOTriggerConfig.Click += new System.EventHandler(this.btnIOTriggerConfig_Click);
            // 
            // cmbox1
            // 
            this.cmbox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbox1.FormattingEnabled = true;
            this.cmbox1.Items.AddRange(new object[] {
            "1#",
            "2#",
            "3#",
            "4#"});
            this.cmbox1.Location = new System.Drawing.Point(106, 19);
            this.cmbox1.Name = "cmbox1";
            this.cmbox1.Size = new System.Drawing.Size(71, 20);
            this.cmbox1.TabIndex = 6;
            this.cmbox1.SelectedIndexChanged += new System.EventHandler(this.cmbox1_SelectedIndexChanged);
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(35, 23);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(65, 12);
            this.label13.TabIndex = 8;
            this.label13.Text = "触发端口：";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.textBox2);
            this.groupBox2.Controls.Add(this.textBox9);
            this.groupBox2.Controls.Add(this.btn_Query);
            this.groupBox2.Controls.Add(this.comboBox10);
            this.groupBox2.Controls.Add(this.comboBox5);
            this.groupBox2.Controls.Add(this.textBox1);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.textBox8);
            this.groupBox2.Controls.Add(this.comboBox9);
            this.groupBox2.Controls.Add(this.label8);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Controls.Add(this.comboBox7);
            this.groupBox2.Controls.Add(this.label7);
            this.groupBox2.Controls.Add(this.btn_Config);
            this.groupBox2.Controls.Add(this.cbIO4);
            this.groupBox2.Controls.Add(this.cbIO3);
            this.groupBox2.Controls.Add(this.cbIO2);
            this.groupBox2.Controls.Add(this.comboBox4);
            this.groupBox2.Controls.Add(this.comboBox2);
            this.groupBox2.Controls.Add(this.comboBox3);
            this.groupBox2.Controls.Add(this.comboBox1);
            this.groupBox2.Controls.Add(this.cbIO1);
            this.groupBox2.Location = new System.Drawing.Point(12, 13);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(398, 161);
            this.groupBox2.TabIndex = 12;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "I/O操作:";
            // 
            // textBox2
            // 
            this.textBox2.BackColor = System.Drawing.Color.White;
            this.textBox2.Location = new System.Drawing.Point(122, 72);
            this.textBox2.Name = "textBox2";
            this.textBox2.ReadOnly = true;
            this.textBox2.Size = new System.Drawing.Size(43, 21);
            this.textBox2.TabIndex = 15;
            // 
            // textBox9
            // 
            this.textBox9.BackColor = System.Drawing.Color.White;
            this.textBox9.Location = new System.Drawing.Point(122, 18);
            this.textBox9.Name = "textBox9";
            this.textBox9.ReadOnly = true;
            this.textBox9.Size = new System.Drawing.Size(43, 21);
            this.textBox9.TabIndex = 15;
            // 
            // btn_Query
            // 
            this.btn_Query.Location = new System.Drawing.Point(90, 126);
            this.btn_Query.Name = "btn_Query";
            this.btn_Query.Size = new System.Drawing.Size(75, 23);
            this.btn_Query.TabIndex = 1;
            this.btn_Query.Text = "查询";
            this.btn_Query.UseVisualStyleBackColor = true;
            this.btn_Query.Click += new System.EventHandler(this.btn_Query_Click);
            // 
            // comboBox10
            // 
            this.comboBox10.Enabled = false;
            this.comboBox10.FormattingEnabled = true;
            this.comboBox10.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox10.Location = new System.Drawing.Point(122, 73);
            this.comboBox10.Name = "comboBox10";
            this.comboBox10.Size = new System.Drawing.Size(43, 20);
            this.comboBox10.TabIndex = 9;
            // 
            // comboBox5
            // 
            this.comboBox5.Enabled = false;
            this.comboBox5.FormattingEnabled = true;
            this.comboBox5.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox5.Location = new System.Drawing.Point(122, 19);
            this.comboBox5.Name = "comboBox5";
            this.comboBox5.Size = new System.Drawing.Size(43, 20);
            this.comboBox5.TabIndex = 9;
            // 
            // textBox1
            // 
            this.textBox1.BackColor = System.Drawing.Color.White;
            this.textBox1.Location = new System.Drawing.Point(122, 99);
            this.textBox1.Name = "textBox1";
            this.textBox1.ReadOnly = true;
            this.textBox1.Size = new System.Drawing.Size(43, 21);
            this.textBox1.TabIndex = 15;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(9, 103);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(71, 12);
            this.label2.TabIndex = 14;
            this.label2.Text = "输入端口4：";
            // 
            // textBox8
            // 
            this.textBox8.BackColor = System.Drawing.Color.White;
            this.textBox8.Location = new System.Drawing.Point(122, 45);
            this.textBox8.Name = "textBox8";
            this.textBox8.ReadOnly = true;
            this.textBox8.Size = new System.Drawing.Size(43, 21);
            this.textBox8.TabIndex = 15;
            // 
            // comboBox9
            // 
            this.comboBox9.Enabled = false;
            this.comboBox9.FormattingEnabled = true;
            this.comboBox9.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox9.Location = new System.Drawing.Point(122, 100);
            this.comboBox9.Name = "comboBox9";
            this.comboBox9.Size = new System.Drawing.Size(43, 20);
            this.comboBox9.TabIndex = 7;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(9, 49);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(71, 12);
            this.label8.TabIndex = 14;
            this.label8.Text = "输入端口2：";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(9, 76);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 12);
            this.label1.TabIndex = 14;
            this.label1.Text = "输入端口3：";
            // 
            // comboBox7
            // 
            this.comboBox7.Enabled = false;
            this.comboBox7.FormattingEnabled = true;
            this.comboBox7.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox7.Location = new System.Drawing.Point(122, 46);
            this.comboBox7.Name = "comboBox7";
            this.comboBox7.Size = new System.Drawing.Size(43, 20);
            this.comboBox7.TabIndex = 7;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(9, 22);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(71, 12);
            this.label7.TabIndex = 14;
            this.label7.Text = "输入端口1：";
            // 
            // btn_Config
            // 
            this.btn_Config.Location = new System.Drawing.Point(309, 125);
            this.btn_Config.Name = "btn_Config";
            this.btn_Config.Size = new System.Drawing.Size(75, 23);
            this.btn_Config.TabIndex = 1;
            this.btn_Config.Text = "设置";
            this.btn_Config.UseVisualStyleBackColor = true;
            this.btn_Config.Click += new System.EventHandler(this.btn_Config_Click);
            // 
            // cbIO4
            // 
            this.cbIO4.AutoSize = true;
            this.cbIO4.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.cbIO4.Location = new System.Drawing.Point(228, 101);
            this.cbIO4.Name = "cbIO4";
            this.cbIO4.Size = new System.Drawing.Size(78, 16);
            this.cbIO4.TabIndex = 13;
            this.cbIO4.Text = "输出端口4";
            this.cbIO4.UseVisualStyleBackColor = true;
            // 
            // cbIO3
            // 
            this.cbIO3.AutoSize = true;
            this.cbIO3.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.cbIO3.Location = new System.Drawing.Point(228, 74);
            this.cbIO3.Name = "cbIO3";
            this.cbIO3.Size = new System.Drawing.Size(78, 16);
            this.cbIO3.TabIndex = 12;
            this.cbIO3.Text = "输出端口3";
            this.cbIO3.UseVisualStyleBackColor = true;
            // 
            // cbIO2
            // 
            this.cbIO2.AutoSize = true;
            this.cbIO2.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.cbIO2.Location = new System.Drawing.Point(228, 47);
            this.cbIO2.Name = "cbIO2";
            this.cbIO2.Size = new System.Drawing.Size(78, 16);
            this.cbIO2.TabIndex = 11;
            this.cbIO2.Text = "输出端口2";
            this.cbIO2.UseVisualStyleBackColor = true;
            // 
            // comboBox4
            // 
            this.comboBox4.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox4.FormattingEnabled = true;
            this.comboBox4.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox4.Location = new System.Drawing.Point(341, 99);
            this.comboBox4.Name = "comboBox4";
            this.comboBox4.Size = new System.Drawing.Size(43, 20);
            this.comboBox4.TabIndex = 10;
            // 
            // comboBox2
            // 
            this.comboBox2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox2.FormattingEnabled = true;
            this.comboBox2.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox2.Location = new System.Drawing.Point(341, 45);
            this.comboBox2.Name = "comboBox2";
            this.comboBox2.Size = new System.Drawing.Size(43, 20);
            this.comboBox2.TabIndex = 7;
            // 
            // comboBox3
            // 
            this.comboBox3.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox3.FormattingEnabled = true;
            this.comboBox3.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox3.Location = new System.Drawing.Point(341, 72);
            this.comboBox3.Name = "comboBox3";
            this.comboBox3.Size = new System.Drawing.Size(43, 20);
            this.comboBox3.TabIndex = 8;
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "低",
            "高"});
            this.comboBox1.Location = new System.Drawing.Point(341, 18);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(43, 20);
            this.comboBox1.TabIndex = 9;
            // 
            // cbIO1
            // 
            this.cbIO1.AutoSize = true;
            this.cbIO1.ImeMode = System.Windows.Forms.ImeMode.NoControl;
            this.cbIO1.Location = new System.Drawing.Point(228, 20);
            this.cbIO1.Name = "cbIO1";
            this.cbIO1.Size = new System.Drawing.Size(78, 16);
            this.cbIO1.TabIndex = 6;
            this.cbIO1.Text = "输出端口1";
            this.cbIO1.UseVisualStyleBackColor = true;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(181, 23);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(101, 12);
            this.label5.TabIndex = 14;
            this.label5.Text = "（选择端口查询）";
            // 
            // FormGPIO
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(422, 368);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FormGPIO";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "I/O控制";
            this.Load += new System.EventHandler(this.FormGPIO_Load);
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numTime)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.ComboBox cmbox2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.RadioButton rbTime;
        private System.Windows.Forms.RadioButton radioButton1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Button btnIOTriggerQuery;
        private System.Windows.Forms.Button btnIOTriggerConfig;
        private System.Windows.Forms.ComboBox cmbox1;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.TextBox textBox9;
        private System.Windows.Forms.Button btn_Query;
        private System.Windows.Forms.ComboBox comboBox10;
        private System.Windows.Forms.ComboBox comboBox5;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBox8;
        private System.Windows.Forms.ComboBox comboBox9;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox comboBox7;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Button btn_Config;
        private System.Windows.Forms.CheckBox cbIO4;
        private System.Windows.Forms.CheckBox cbIO3;
        private System.Windows.Forms.CheckBox cbIO2;
        private System.Windows.Forms.ComboBox comboBox4;
        private System.Windows.Forms.ComboBox comboBox2;
        private System.Windows.Forms.ComboBox comboBox3;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.CheckBox cbIO1;
        private System.Windows.Forms.NumericUpDown numTime;
        private System.Windows.Forms.Label label5;
    }
}