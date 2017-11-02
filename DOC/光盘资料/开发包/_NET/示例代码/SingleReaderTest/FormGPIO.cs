using System;
using System.Windows.Forms;
using Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;

namespace SingleReaderTest
{
    public partial class FormGPIO : Form
    {
        IRP1.Reader reader;
        IRP1.ReadTag scanMsg;
        private String rd1Text;
        public FormGPIO(IRP1.Reader reader,IRP1.ReadTag scanMsg)
        {
            InitializeComponent();
            this.reader = reader;
            this.scanMsg = scanMsg;
            if (cmbox1.Items.Count > 0)
                cmbox1.SelectedIndex = 0;
        }

        private void FormGPIO_Load(object sender, EventArgs e)
        {
            btn_Query_Click(sender, e);
            btnIOTriggerQuery_Click(sender, e);
            rd1Text = radioButton1.Text;
        }

        private void btn_Query_Click(object sender, EventArgs e)
        {
            IRP1.Gpi_800 order = new IRP1.Gpi_800();
            if (reader.Send(order, 1000))
            {
                Byte b = order.ReceivedMessage.QueryData[0];
                textBox8.Text = comboBox7.Items[(b & 0x02) >> 1].ToString();
                textBox9.Text = comboBox5.Items[b & 0x01].ToString();
                if (textBox2.Enabled)
                    textBox2.Text = comboBox10.Items[(b & 0x04) >> 2].ToString();
                if (textBox1.Enabled)
                    textBox1.Text = comboBox9.Items[(b & 0x08) >> 3].ToString();
            }
            else
            {
                groupBox2.Enabled = false;
            }
        }

        private void btn_Config_Click(object sender, EventArgs e)
        {
            String ms = "";
            String mf = "";
            #region 设置 IO输出状态
            if (cbIO1.Checked)
            {
                if (comboBox1.Text.Trim() == String.Empty)
                {
                    MessageBox.Show("请设置输出端口1");                    
                    return;
                }

                Byte d = (Byte)comboBox1.SelectedIndex;
                IRP1.Gpo_800 msg = new IRP1.Gpo_800(0x01, d);
                if (reader.Send(msg))
                    ms = "1,";
                else
                    mf = "1,";
            }
            if (cbIO2.Checked)
            {
                if (comboBox2.Text.Trim() == String.Empty)
                {
                    MessageBox.Show("请设置输出端口2");
                    return;
                }

                Byte d = (Byte)comboBox2.SelectedIndex;
                IRP1.Gpo_800 msg = new IRP1.Gpo_800(0x02, d);
                if (reader.Send(msg))
                    ms += "2,";
                else
                    mf += "2,";
            }
            if (cbIO3.Checked)
            {
                if (comboBox3.Text.Trim() == String.Empty)
                {
                    MessageBox.Show("请设置输出端口3");
                    return;
                }

                Byte d = (Byte)comboBox3.SelectedIndex;
                IRP1.Gpo_800 msg = new IRP1.Gpo_800(0x03, d);
                if (reader.Send(msg))
                    ms += "3,";
                else
                    mf += "3,";
            }
            if (cbIO4.Checked)
            {
                if (comboBox4.Text.Trim() == String.Empty)
                {
                    MessageBox.Show("请设置输出端口4");
                    return;
                }

                Byte d = (Byte)comboBox4.SelectedIndex;
                IRP1.Gpo_800 msg = new IRP1.Gpo_800(0x04, d);
                if (reader.Send(msg))
                    ms += "4,";
                else
                    mf += "4,";
            }
            #endregion
            String m = "";
            if (ms != "")
                m = "输出端口" + ms.Trim(',') + "设置成功";
            if (mf != "")
                m += "\r\n" + "输出端口" + mf.Trim(',') + "设置失败";
            if (m.Trim() != String.Empty)
            {
                MessageBox.Show(m);
            }
        }
       
        private void btnIOTriggerQuery_Click(object sender, EventArgs e)
        {
            IRP1.SysQuery_800 order = new IRP1.SysQuery_800(0xE2, (byte)(cmbox1.SelectedIndex + 1));
            if (reader.Send(order))
            {
                if (order.ReceivedMessage == null || order.ReceivedMessage.QueryData == null)
                    return;

                //触发方式

                cmbox1.SelectedIndex = order.ReceivedMessage.QueryData[0] - 1;
                cmbox2.SelectedIndex = order.ReceivedMessage.QueryData[1];

                //停止条件
                if (order.ReceivedMessage.QueryData[2] == 0)
                {
                    radioButton1.Checked = true;
                    numTime.Enabled = false;
                }
                else
                {
                    rbTime.Checked = true;
                    numTime.Enabled = true;
                }
                numTime.Value = (order.ReceivedMessage.QueryData[3] << 8) + order.ReceivedMessage.QueryData[4];
            }
            else
            {
                groupBox3.Enabled = false;
            }
        }
       
        private void btnIOTriggerConfig_Click(object sender, EventArgs e)
        {
            IMessage msg1 = this.scanMsg;
            IMessage msg2 = new IRP1.PowerOff_800();
            msg1.PortType = "";
            byte[] msg1Buff = msg1.TransmitterData;
            msg2.PortType = "";
            byte[] msg2Buff = msg2.TransmitterData;
            byte[] pData = new byte[5 + msg1Buff.Length + msg2Buff.Length];
            pData[0] = (byte)(cmbox1.SelectedIndex + 1);
            pData[1] = (byte)(cmbox2.SelectedIndex);
            pData[2] = (byte)(radioButton1.Checked ? 0 : 1);
            pData[3] = (byte)(numTime.Value / 256);
            pData[4] = (byte)(numTime.Value % 256);
            Array.Copy(msg1Buff, 0, pData, 5, msg1Buff.Length);
            Array.Copy(msg2Buff, 0, pData, 5 + msg1Buff.Length, msg2Buff.Length);
            IRP1.SysConfig_800 msg = new IRP1.SysConfig_800(0xE2, pData);
            if (reader.Send(msg))
            {
                MessageBox.Show("配置成功");
            }
            else
            {
                MessageBox.Show("配置失败");
            }
        }

        private void rbTime_CheckedChanged(object sender, EventArgs e)
        {
            numTime.Enabled = rbTime.Checked;
        }

        private void cmbox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            switch (cmbox2.SelectedIndex)
            {
                case 1:
                    radioButton1.Text = rd1Text + "(" + cmbox2.Items[2].ToString() + ")";
                    break;
                case 2:
                    radioButton1.Text = rd1Text + "(" + cmbox2.Items[1].ToString() + ")";
                    break;
                case 3:
                    radioButton1.Text = rd1Text + "(" + cmbox2.Items[4].ToString() + ")";
                    break;
                case 4:
                    radioButton1.Text = rd1Text + "(" + cmbox2.Items[3].ToString() + ")";
                    break;
            }
        }

        private void cmbox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            btnIOTriggerQuery_Click(null, EventArgs.Empty);
        }
    }
}
