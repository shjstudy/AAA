using System;
using System.Windows.Forms;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;
using MCP.Config;

namespace App.View.Param
{
    public partial class FormReaderConfig : Form
    {
        IRP1.Reader reader;
        public bool isTryReconnNet;
        public int tryReconnNetTimeSpan;
        public FormReaderConfig(IRP1.Reader reader)
        {
            InitializeComponent();
            this.reader = reader;
            this.cbkListenNet.Checked = RFIDRead.isTryReconnNet;
            this.tryReconnNetTimeSpan = RFIDRead.tryReconnNetTimeSpan;
        }

        private void FormReaderConfig_Load(object sender, EventArgs e)
        {
            btnQuery_Click(null, EventArgs.Empty);
        }

        private void btnQuery_Click(object sender, EventArgs e)
        {
            IRP1.SysQuery_800 msg = new IRP1.SysQuery_800(0x06);
            if (reader.Send(msg))
            {
                getIP(msg.ReceivedMessage.QueryData);
            }
            else
            {
                groupBox1.Enabled = false;                
            }
        }

        // IP查询成功
        void getIP(Byte[] data)
        {
            if (data.Length < 12) return;
            // 整理数据
            String ip = data[0].ToString() + "."
                + data[1].ToString() + "."
                + data[2].ToString() + "."
                + data[3].ToString();
            String subnet = data[4].ToString() + "."
                + data[5].ToString() + "."
                + data[6].ToString() + "."
                + data[7].ToString();
            String gateway = data[8].ToString() + "."
                + data[9].ToString() + "."
                + data[10].ToString() + "."
                + data[11].ToString();
            // 显示
            this.textBox1.Text = ip;
            this.textBox2.Text = subnet;
            this.textBox3.Text = gateway;           

        }
        private void btnSet_Click(object sender, EventArgs e)
        {
            if (!isIP(textBox1.Text.Trim())
               || !isIP(textBox2.Text.Trim())
               || !isIP(textBox3.Text.Trim()))
            {
                MCP.Logger.Error("请输入正确ip");
                return;
            }

            #region 取IP
            Byte[] ipData = new Byte[12];
            int p = 0;

            String[] aryip = textBox1.Text.Split('.');
            foreach (String str in aryip)
            {
                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            aryip = textBox2.Text.Split('.');
            foreach (String str in aryip)
            {
                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            aryip = textBox3.Text.Split('.');
            foreach (String str in aryip)
            {

                ipData[p] = (Byte)int.Parse(str);
                p++;
            }
            #endregion

            IRP1.SysConfig_800 order = new IRP1.SysConfig_800(
                0x06,//设置ip
                ipData);
            if (reader.Send(order))
            {
                //保存到Config.xml
                System.Collections.Generic.Dictionary<string, string> attributes = null;
                ConfigUtil configUtil = new ConfigUtil();
                attributes = configUtil.GetAttribute();

                attributes["isTryReconnNet"] = this.cbkListenNet.Checked ? "1" : "0";
                attributes["tryReconnNetTimeSpan"] = this.numReConnTime.Value.ToString();

                attributes["RFIDConnetIP"] = this.textBox1.Text.Trim() + ":7086" ;
                configUtil.Save(attributes);

                RFIDRead.InitRFID();
                RFIDRead.Connect();

                MCP.Logger.Info("设置成功");
                this.DialogResult = DialogResult.OK;
            }
            else
            {
                MCP.Logger.Error("设置失败");
            }
        }

        public bool isIP(String ip)
        {
            //判断是否为IP
            return System.Text.RegularExpressions.Regex.IsMatch(ip, @"^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$");
        }

        // 设置断网重连
        private void button8_Click(object sender, EventArgs e)
        {
            if (cbkListenNet.Checked)
            {
                this.isTryReconnNet = true;
                this.tryReconnNetTimeSpan = (Int32)numReConnTime.Value;
            }
            else
            {
                this.isTryReconnNet = false;
                this.tryReconnNetTimeSpan = 10;
            }
            System.Collections.Generic.Dictionary<string, string> attributes = null;
            ConfigUtil configUtil = new ConfigUtil();
            attributes = configUtil.GetAttribute();

            attributes["isTryReconnNet"] = this.cbkListenNet.Checked ? "1" : "0";
            attributes["tryReconnNetTimeSpan"] = this.numReConnTime.Value.ToString() ;
            configUtil.Save(attributes);

            MCP.Logger.Info("设置成功");
        }
    }
}
