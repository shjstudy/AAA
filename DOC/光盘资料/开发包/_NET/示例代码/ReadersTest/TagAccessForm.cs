using System;
using System.Drawing;
using System.Windows.Forms;
using Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;

namespace ReadersTest
{
    public partial class TagAccessForm : Form
    {
        String tagIDStr;
        Byte[] tagID;
        String tagType;        
        MyReader myReader;
        Byte antenna;
        MemoryBank mb;

        public TagAccessForm(MyReader myReader, Byte antenna, String tagIDStr, String tagType, MemoryBank mb)
        {
            InitializeComponent();
            this.myReader = myReader;
            this.antenna = antenna;
            this.tagIDStr = tagIDStr;
            this.tagType = tagType;
            

            tagID = Util.ConvertHexStringToByteArray(this.tagIDStr);
            this.mb = mb;
        }

        private void TagAccessForm_Load(object sender, EventArgs e)
        {
            this.lblReader.Text = myReader.reader.ReaderName;
            this.lblAntenna.Text = antenna.ToString();
            this.lblTagType.Text = tagType;
            this.txtTag.Text = tagIDStr;
            if (tagType == "6B")
            {
                tabControl1.TabPages.RemoveAt(8);
                tabControl1.TabPages.RemoveAt(6);
                tabControl1.TabPages.RemoveAt(5);
                tabControl1.TabPages.RemoveAt(2);
                tabControl1.TabPages.RemoveAt(1);
                tabControl1.TabPages.RemoveAt(0);
                txtPwd.Enabled = false;
            }
            if (tagType == "6C")
            {
                tabControl1.TabPages.RemoveAt(7);
                tabControl1.TabPages.RemoveAt(4);
                tabControl1.TabPages.RemoveAt(3);
                
            }
            
            InitializeLockPanel();
        }

        #region 写EPC
        private void btnWriteEpc_Click(object sender, EventArgs e)
        {
            Byte[] pwd = getPwd(txtPwd.Text.Trim());            

            Byte[] wd = getWriteData(txtEpc.Text.Trim());

            #region IRP1 写EPC数据指令
            IRP1.WriteEpc msg =
                new IRP1.WriteEpc(
                    this.antenna, pwd, wd, tagID, mb);
            #endregion

            if (this.myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }       

        #endregion

        #region 读用户数据6C

        private void btnReadUd_6C_Click(object sender, EventArgs e)
        {
            #region IRP1 读用户数据6C指令
            IRP1.ReadUserData_6C msg =
                new IRP1.ReadUserData_6C(
                    this.antenna,
                    (Byte)numPtr_ReadUd_6C.Value,
                    (Byte)numLen_ReadUd_6C.Value,
                    tagID,
                    mb);
            #endregion

            if (this.myReader.reader.Send(msg))
            {
                txtReadUd_6C.Text = Util.ConvertByteArrayToHexWordString(((IRP1.ReadUserData_6C)msg).ReceivedMessage.UserData);
                MessageBox.Show("成功");
            }
            else
                MessageBox.Show("失败!" + msg.ErrInfo);
        }

        #endregion

        #region 写用户数据6C

        private void btnWriteUd_6C_Click(object sender, EventArgs e)
        {
            #region IRP1 写用户数据6C指令
            IRP1.WriteUserData_6C msg =
                new IRP1.WriteUserData_6C(
                    this.antenna,
                    getPwd(txtPwd.Text.Trim()),
                    (Byte)numPtr_WriteUd_6C.Value,
                    getWriteData(txtWriteUd_6C.Text.Trim()),
                    tagID,
                    mb);
            #endregion

            if (this.myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }

        #endregion

        #region 读用户数据6B

        private void btnReadUd_6B_Click(object sender, EventArgs e)
        { 
            IMessage msg = null;
            #region IRP1 读用户数据6B指令
            if (checkBox1.Checked)
            {
                msg = new IRP1.ReadUserData2_6B(
                     this.antenna,
                     Util.ConvertHexStringToByteArray(this.tagIDStr),
                     (Byte)numPtr_ReadUd_6B.Value,
                     (Byte)numLen_ReadUd_6B.Value);
            }
            else
            {
                msg = new IRP1.ReadUserData_6B(
                    this.antenna,
                    Util.ConvertHexStringToByteArray(this.tagIDStr),
                    (Byte)numPtr_ReadUd_6B.Value,
                    (Byte)numLen_ReadUd_6B.Value);
            }
           
           
            #endregion

            if (this.myReader.reader.Send(msg))
            {
                if(msg is IRP1.ReadUserData2_6B)
                    txtReadUd_6B.Text = Util.ConvertByteArrayToHexString(((IRP1.ReadUserData2_6B)msg).ReceivedMessage.UserData);
                else if (msg is IRP1.ReadUserData_6B)
                    txtReadUd_6B.Text = Util.ConvertByteArrayToHexString(((IRP1.ReadUserData_6B)msg).ReceivedMessage.UserData);
                MessageBox.Show("成功");
            }
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }

        #endregion

        #region 写用户数据6B

        private void btnWriteUd_6B_Click(object sender, EventArgs e)
        {
            Byte[] wd = Util.ConvertHexStringToByteArray(txtWriteUd_6B.Text.Trim());
            IMessage msg = null;
            #region IRP1 写用户数据6B指令
            if (checkBox2.Checked)
            {
                msg = new IRP1.WriteUserData2_6B(
                        this.antenna,
                        Util.ConvertHexStringToByteArray(this.tagIDStr),
                        (Byte)numPtr_WriteUd_6B.Value,
                        wd);
            }
            else
            {
                msg = new IRP1.WriteUserData_6B(
                        this.antenna,
                        Util.ConvertHexStringToByteArray(this.tagIDStr),
                        (Byte)numPtr_WriteUd_6B.Value,
                        wd);
            }
            #endregion

            if (this.myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }

        #endregion

        #region 锁6C标签
        private void btnLock6C_Click(object sender, EventArgs e)
        {
            Byte[] pwd = new Byte[4];
            if(txtPwd.Text.Trim()!= "")
            {
                Byte[] p = Util.ConvertHexStringToByteArray(txtPwd.Text.Trim());
                if (p.Length < 4 && p.Length > 0)
                    Array.Copy(p, 0, pwd, 4 - p.Length, p.Length);
                if (p.Length == 4)
                    pwd = p;
            }
            IRP1.LockMemoryBank_6C msg = new IRP1.LockMemoryBank_6C(
                antenna, 
                pwd, 
                (Byte)cbLock.SelectedIndex, 
                (Byte)cbType.SelectedIndex,
                tagID,
                this.mb);
            if (this.myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }
        #endregion

        #region 销毁标签
        private void btnKillTag_Click(object sender, EventArgs e)
        {
            if (this.mb != MemoryBank.EPCMemory)
            {
                MessageBox.Show("请选择标签EPC码！");
                return;
            }
            if (MessageBox.Show("确定销毁标签？","系统提示", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) 
                == System.Windows.Forms.DialogResult.OK)
            {

                Byte[] pwd = new Byte[4];
                if (txtKillPwd.Text.Trim() != "")
                {
                    Byte[] p = Util.ConvertHexStringToByteArray(txtKillPwd.Text.Trim());
                    if (p.Length < 4 && p.Length > 0)
                        Array.Copy(p, 0, pwd, 4 - p.Length, p.Length);
                    if (p.Length == 4)
                        pwd = p;
                }
                IRP1.KillTag_6C msg = new IRP1.KillTag_6C(
                    antenna,
                    pwd,
                    tagID
                    );
                if (this.myReader.reader.Send(msg))
                    MessageBox.Show("成功");
                else
                    MessageBox.Show("失败" + msg.ErrInfo);
            }
        }
        #endregion

        #region 锁6B标签
        private void btnLock6BQuery_Click(object sender, EventArgs e)
        {
            IMessage msg = null;
            #region IRP1
            Byte[] qd = new Byte[216];
            int count = 0;
            foreach (Control ctrl in tableLayoutPanel1.Controls)
            {
                if (ctrl is CheckBox)
                {
                    CheckBox c = ctrl as CheckBox;
                    if (c.Checked)
                    {
                        int index = int.Parse(c.Name.Replace("ptr", "")) - 1;
                        qd[index] = 0x01;
                        count++;
                    }
                }
            }
            msg = new IRP1.LockStateQuery_6B(this.antenna, tagID, qd);
            #endregion
            int timeout = count * 50;
            if (timeout < 1000)
                timeout = 1000;
            if (this.myReader.reader.Send(msg, timeout))
            {            
                IRP1.LockStateQuery_6B m = msg as IRP1.LockStateQuery_6B;
                Byte[] s = m.ReceivedMessage.LockResult;
                if (s != null)
                {
                    for (int i = 0; i < s.Length; i++)
                    {
                        if (qd[i] == 0x01 && s[i] == 0x01)                        
                        {
                            Control[] cs = tabControl1.Controls.Find("ptr" + (i + 1), true);
                            if (cs != null && cs.Length > 0)
                            {
                                CheckBox c = cs[0] as CheckBox;
                                c.BackColor = Color.Red;
                            }
                        }
                    }
                }
                MessageBox.Show("成功");
            }
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }

        private void btnLock6B_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("确定要永久锁定选定数据吗？", "系统提示", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) 
                == System.Windows.Forms.DialogResult.OK)
            {
                IMessage msg = null;
                #region IRP1
                Byte[] qd = new Byte[216];
                int count = 0;
                foreach (Control ctrl in tableLayoutPanel1.Controls)
                {
                    if (ctrl is CheckBox)
                    {
                        CheckBox c = ctrl as CheckBox;
                        if (c.Checked)
                        {
                            int index = int.Parse(c.Name.Replace("ptr", "")) - 1;
                            qd[index] = 0x01;
                            count++;
                        }
                    }
                }
                msg = new IRP1.LockUserData_6B(this.antenna, tagID, qd);
                #endregion
                int timeout = count * 50;
                if (timeout < 1000)
                    timeout = 1000;
                if (this.myReader.reader.Send(msg, timeout))
                {
                    MessageBox.Show("成功");
                }
                else
                    MessageBox.Show("失败" + msg.ErrInfo);
            }
        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {
            foreach (Control ctrl in tableLayoutPanel1.Controls)
            {
                if (ctrl is CheckBox)
                {
                    CheckBox c = ctrl as CheckBox;
                    c.Checked = checkBox3.Checked;                    
                }
            }
        }
        #endregion

        #region 标签密码
        // 访问密码
        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text.Trim() != textBox2.Text.Trim())
            {
                MessageBox.Show("密码不一致!");
                return;
            }

            IRP1.AccessPwdConfig_6C msg = new IRP1.AccessPwdConfig_6C(
                antenna, 
                getPwd(txtPwd.Text.Trim()), 
                getPwd(textBox1.Text.Trim()),
                tagID,
                mb);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);           
        }

        // 销毁密码
        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox3.Text.Trim() != textBox4.Text.Trim())
            {
                MessageBox.Show("密码不一致!");
                return;
            }
            IRP1.KillPwdConfig_6C msg = new IRP1.KillPwdConfig_6C(
                antenna, 
                getPwd(txtPwd.Text.Trim()), 
                getPwd(textBox3.Text.Trim()),
                tagID,
                mb);
            if (myReader.reader.Send(msg))
                MessageBox.Show("成功");
            else
                MessageBox.Show("失败" + msg.ErrInfo);
        }
        #endregion

        #region 辅助方法

        bool isHex(String str)
        {
            bool b = false;
            char[] c = str.ToUpper().ToCharArray();
            for (int i = 0; i < c.Length; i++)
            {
                if (System.Text.RegularExpressions.Regex.IsMatch((c[i]).ToString(), "[0-9]") || System.Text.RegularExpressions.Regex.IsMatch((c[i]).ToString(), "[A-F]"))
                    b = true;
                else
                {
                    b = false;
                    break;
                }
            }
            return b;
        }

        private bool nonNumberEntered = false;

        private void textBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode != Keys.Enter)
            {
                // Initialize the flag to false.
                nonNumberEntered = false;

                // Determine whether the keystroke is a number from the top of the keyboard.
                if (e.KeyCode < Keys.D0 || e.KeyCode > Keys.D9)
                {
                    // Determine whether the keystroke is a number from the keypad.
                    if (e.KeyCode < Keys.NumPad0 || e.KeyCode > Keys.NumPad9)
                    {
                        if (e.KeyCode < Keys.A || e.KeyCode > Keys.F)
                        {
                            // Determine whether the keystroke is a backspace.
                            if (e.KeyCode != Keys.Back)
                            {
                                // A non-numerical keystroke was pressed.
                                // Set the flag to true and evaluate in KeyPress event.
                                nonNumberEntered = true;
                            }
                        }
                    }
                }

            }
        }

        private void textBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            // Check for the flag being set in the KeyDown event.
            if (nonNumberEntered == true)
            {
                // Stop the character from being entered into the control since it is non-numerical.
                e.Handled = true;
            }
        }

        Byte[] getPwd(String pwdText)
        {
            if (pwdText == "")
                return new Byte[4];
            Byte[] pwd = Util.ConvertHexStringToByteArray(pwdText);
            if (pwd.Length < 4)
            {
                Byte[] p = new Byte[4];
                Array.Copy(pwd, 0, p, 4 - pwd.Length, pwd.Length);
                return p;
            }
            return pwd;
        }

        Byte[] getWriteData(String str)
        {
            Byte[] data = Util.ConvertHexStringToByteArray(str);
            if (data.Length % 2 == 1)
            {
                Byte[] d = new Byte[data.Length + 1];
                Array.Copy(data, 0, d, 0, data.Length);
                return d;
            }
            return data;
        }

        private bool InitializeLockPanel()
        {
            //重新设置表格            
            tableLayoutPanel1.Controls.Clear();
            tableLayoutPanel1.RowCount = 13;
            tableLayoutPanel1.ColumnCount = 36;
            tableLayoutPanel1.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            tableLayoutPanel1.Refresh();

            tableLayoutPanel1.ColumnStyles[0].SizeType = SizeType.Percent;

            for (int i = 1; i < tableLayoutPanel1.ColumnStyles.Count; i++)
            {
                tableLayoutPanel1.ColumnStyles[i].SizeType = SizeType.Absolute;
                tableLayoutPanel1.ColumnStyles[i].Width = 16;
            }
            for (int i = 0; i < tableLayoutPanel1.RowStyles.Count; i++)
            {
                tableLayoutPanel1.RowStyles[i].SizeType = SizeType.Absolute;
                tableLayoutPanel1.RowStyles[i].Height = 16;
            }

            //添加控件
            int p = 0;
            for (int i = 0; i < 7; i++)
            {
                if (i < 6)
                {
                    Label lbl = new Label();
                    lbl.Padding = lbl.Margin = new Padding(0);
                    lbl.Name = "lbl" + p.ToString();
                    lbl.Width = lbl.Height = 5;
                    lbl.Dock = DockStyle.Fill;
                    lbl.BackgroundImageLayout = ImageLayout.Stretch;
                    tableLayoutPanel1.Controls.Add(lbl, 0, i * 2 + 1);
                }
                for (int j = 0; j < 36; j++)
                {
                    if (p >= 216)
                        break;
                    if (j % 9 == 0)
                    {
                        Label lbl = new Label();
                        lbl.Padding = lbl.Margin = new Padding(0);
                        lbl.Name = "lbl" + p.ToString();
                        if (j == 0)
                            lbl.Text = "地址：(" + i * 32 + "-" + ((i + 1) * 32 - 1) + ")";
                        if (j == 0 && i == 6)
                            lbl.Text = "地址：(" + i * 32 + "-215)";
                        lbl.Width = lbl.Height = 5;
                        lbl.Dock = DockStyle.Fill;
                        lbl.BackgroundImageLayout = ImageLayout.Stretch;
                        tableLayoutPanel1.Controls.Add(lbl, j, i * 2);
                    }
                    else
                    {
                        p++;

                        CheckBox checkBox = new CheckBox();
                        checkBox.Padding = checkBox.Margin = new Padding(0);
                        checkBox.Name = "ptr" + p.ToString();
                        checkBox.Width = checkBox.Height = 16;
                        checkBox.Dock = DockStyle.Fill;
                        checkBox.BackgroundImageLayout = ImageLayout.Stretch;

                        tableLayoutPanel1.Controls.Add(checkBox, j, i * 2);
                    }
                }
                if (p >= 216)
                    break;
            }


            return true;
        }
        #endregion       

        private void button3_Click(object sender, EventArgs e)
        {
            Byte[] pwd = getPwd(txtPwd.Text.Trim());
            Byte flag = 0x00;//取消
            if (rb_EasSet.Checked)
                flag = 0x01;//设置
            IRP1.EasConfig_6C msg = new IRP1.EasConfig_6C(antenna, pwd, flag, tagID, mb);
            if (myReader.reader.Send(msg))
            {
                MessageBox.Show("成功");
            }
            else
            {
                MessageBox.Show("失败!" + msg.ErrInfo);
            }
        }
    }
}
