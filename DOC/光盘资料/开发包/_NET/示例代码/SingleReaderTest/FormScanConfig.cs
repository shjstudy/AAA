using System;
using System.Windows.Forms;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;

namespace SingleReaderTest
{
    public partial class FormScanConfig : Form
    {
        public IRP1.ReadTag msg = new IRP1.ReadTag(IRP1.ReadTag.ReadMemoryBank.EPC_6C);
        IRP1.Reader reader;
        bool isNewPower = false;
        Double[] list;

        public FormScanConfig(IRP1.Reader reader)
        {
            InitializeComponent();
            this.reader = reader;
            cbReadMB.SelectedIndex = 0;
            a1.Checked = true;
            btnQuery_Click(null, EventArgs.Empty);
        }

        private void btnQuery_Click(object sender, EventArgs e)
        {
            #region 天线功率查询
            {
                if (reader.ModelNumber.IndexOf("812") != -1
                    || reader.ModelNumber.IndexOf("825") != -1
                    || reader.ModelNumber.IndexOf("829") != -1
                    || reader.ModelNumber.IndexOf("853") != -1
                    || reader.ModelNumber.IndexOf("R2000") != -1
                    || reader.ModelNumber.IndexOf("802") != -1)
                {
                    lblpa2.Visible = false;
                    pa2.Visible = false;                   
                    lblpa3.Visible = false;
                    pa3.Visible = false;
                    lblpa4.Visible = false;
                    pa4.Visible = false;
                    
                }
                if (reader.ModelNumber.IndexOf("850") != -1)//850
                {
                    pa3.Visible = false;
                    lblpa4.Visible = false;
                    pa4.Visible = false;
                }                
                    Boolean isSuc = false;

                //获取功率参数表，注意：功率参数不等于功率值
                    IRP1.SysQuery_800 order = new IRP1.SysQuery_800(0x68, 0x00);
                    if (reader.Send(order))
                    {
                        isSuc = true;
                        isNewPower = true;                       

                        list = new Double[order.ReceivedMessage.QueryData.Length];
                        for (int i = 0; i < list.Length; i++)
                        {
                            list[i] = (Double)i;//填充功率值表
                        }
                        // 填充功率表
                        for (int i = 0; i < list.Length; i++)
                        {
                            //功率参数大于0才有功率值
                            if (order.ReceivedMessage.QueryData[i] != 0x00 && i < list.Length)
                            {
                                pa1.Items.Add(list[i].ToString());
                                pa2.Items.Add(list[i].ToString());
                                pa3.Items.Add(list[i].ToString());
                                pa4.Items.Add(list[i].ToString());
                            }
                        }

                        // 查询功率值
                        IRP1.SysQuery_800 order1 = new IRP1.SysQuery_800(0x65, 0x00);
                        if (reader.Send(order1))
                        {                            
                            this.pa1.Text = list[order1.ReceivedMessage.QueryData[0]].ToString();
                            this.pa2.Text = list[order1.ReceivedMessage.QueryData[1]].ToString();
                            this.pa3.Text = list[order1.ReceivedMessage.QueryData[2]].ToString();
                            this.pa4.Text = list[order1.ReceivedMessage.QueryData[3]].ToString();                            
                        }
                    }
                    else
                    {
                        IRP1.SysQuery_800 msg = new IRP1.SysQuery_800(0x0C, 0x00);
                        if (reader.Send(msg))
                        {
                            isSuc = true;
                            isNewPower = false; 
                            // 生成功率表
                            {
                                if (reader.ModelNumber.IndexOf("806") != -1 
                                    || reader.ModelNumber.IndexOf("860") != -1 
                                    || reader.ModelNumber.IndexOf("804") != -1 
                                    || reader.ModelNumber.IndexOf("803") != -1)
                                {
                                    list = new Double[msg.ReceivedMessage.QueryData.Length];
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        list[i] = 20 + (Double)i / 2;
                                    }
                                }
                                if (reader.ModelNumber.IndexOf("807") != -1)//807
                                {
                                    list = new Double[msg.ReceivedMessage.QueryData.Length];
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        list[i] = 11 + (Double)i;
                                    }
                                }
                                if (reader.ModelNumber.IndexOf("850") != -1)//850
                                {
                                    list = new Double[msg.ReceivedMessage.QueryData.Length];
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        list[i] = 9 + (Double)i;
                                    }
                                }
                                if (reader.ModelNumber.IndexOf("861") != -1)//861
                                {
                                    list = new Double[msg.ReceivedMessage.QueryData.Length];
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        list[i] = 9 + (Double)i;
                                    }
                                }
                                if (reader.ModelNumber.IndexOf("812") != -1
                                    || reader.ModelNumber.IndexOf("825") != -1
                                    || reader.ModelNumber.IndexOf("829") != -1
                                    || reader.ModelNumber.IndexOf("853") != -1
                                    || reader.ModelNumber.IndexOf("R2000") != -1
                                    || reader.ModelNumber.IndexOf("802") != -1)
                                {
                                    list = new Double[msg.ReceivedMessage.QueryData.Length];
                                    for (int i = 0; i < list.Length; i++)
                                    {
                                        list[i] = (Double)i;
                                    }
                                }                                
                            }
                            for (int i = 0; i < msg.ReceivedMessage.QueryData.Length; i++)
                            {
                                if (msg.ReceivedMessage.QueryData[i] != 0x00 && i < list.Length)
                                {
                                    pa1.Items.Add(list[i].ToString());
                                    pa2.Items.Add(list[i].ToString());
                                    pa3.Items.Add(list[i].ToString());
                                    pa4.Items.Add(list[i].ToString());
                                }
                            }

                            // 查询天线功率
                            IRP1.SysQuery_800 order1 = new IRP1.SysQuery_800(0x03);
                            if (reader.Send(order1))
                            {                                
                                if (order1.ReceivedMessage.QueryData[0] < list.Length)
                                    this.pa1.Text = list[order1.ReceivedMessage.QueryData[0]].ToString();
                                if (order1.ReceivedMessage.QueryData[1] < list.Length)
                                    this.pa2.Text = list[order1.ReceivedMessage.QueryData[1]].ToString();
                                if (order1.ReceivedMessage.QueryData[2] < list.Length)
                                    this.pa3.Text = list[order1.ReceivedMessage.QueryData[2]].ToString();
                                if (order1.ReceivedMessage.QueryData[3] < list.Length)
                                    this.pa4.Text = list[order1.ReceivedMessage.QueryData[3]].ToString();                                
                            }
                        }
                    }
                    if (isSuc)
                        this.groupBox1.Enabled = true;
                    else
                        this.groupBox1.Enabled = false;               
            }
            #endregion 
        }

        private void btnSet_Click(object sender, EventArgs e)
        {
            #region 天线功率设置
            String strSuc = "";
            String strFai = "";
            if (pa1.Text.Trim() != String.Empty && pa1.Visible)
            {
                Byte[] aData = new Byte[2];
                aData[0] = 0x00;//1#
                for (int i = 0; i < list.Length; i++)
                {
                    if (list[i].ToString() == pa1.Text)
                        aData[1] = (Byte)i;
                }
                if (!this.isNewPower)
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                    if (reader.Send(order))
                        strSuc +=  "1,";
                    else
                        strFai +=  "1,";
                }
                else
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x65, aData);
                    if (reader.Send(order))
                        strSuc += "1,";
                    else
                        strFai += "1,";
                }
            }
            if (pa2.Text.Trim() != String.Empty && pa2.Visible)
            {
                Byte[] aData = new Byte[2];
                aData[0] = 0x01;//2#
                for (int i = 0; i < list.Length; i++)
                {
                    if (list[i].ToString() == pa2.Text)
                        aData[1] = (Byte)i;
                }
                if (!this.isNewPower)
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                    if (reader.Send(order))
                        strSuc += "2,";
                    else
                        strFai += "2,";
                }
                else
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x65, aData);
                    if (reader.Send(order))
                        strSuc += "2,";
                    else
                        strFai += "2,";
                }
            }

            if (pa3.Text.Trim() != String.Empty && pa3.Visible)
            {
                Byte[] aData = new Byte[2];
                aData[0] = 0x02;//3#
                for (int i = 0; i < list.Length; i++)
                {
                    if (list[i].ToString() == pa3.Text)
                        aData[1] = (Byte)i;
                }
                if (!this.isNewPower)
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                    if (reader.Send(order))
                        strSuc += "3,";
                    else
                        strFai += "3,";
                }
                else
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x65, aData);
                    if (reader.Send(order))
                        strSuc += "3,";
                    else
                        strFai += "3,";
                }
            }
            if (pa4.Text.Trim() != String.Empty && pa4.Visible)
            {
                Byte[] aData = new Byte[2];
                aData[0] = 0x03;//4#
                for (int i = 0; i < list.Length; i++)
                {
                    if (list[i].ToString() == pa4.Text)
                        aData[1] = (Byte)i;
                }
                if (!this.isNewPower)
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x03, aData);
                    if (reader.Send(order))
                        strSuc += "4,";
                    else
                        strFai += "4,";
                }
                else
                {
                    IRP1.SysConfig_800 order = new IRP1.SysConfig_800(0x65, aData);
                    if (reader.Send(order))
                        strSuc += "4,";
                    else
                        strFai += "4,";
                }
            }

            if (strSuc != "")
                strSuc = "天线" + strSuc.TrimEnd(',') + "设置成功";
            if (strFai != "")
                strFai = "天线" + strFai.TrimEnd(',') + "设置失败";
            MessageBox.Show(strSuc + "\r\n" + strFai);


            #endregion
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            IRP1.ReadTag.ReadMemoryBank rmb = (IRP1.ReadTag.ReadMemoryBank)Enum.Parse(typeof(IRP1.ReadTag.ReadMemoryBank), cbReadMB.Items[cbReadMB.SelectedIndex].ToString());
            msg = new IRP1.ReadTag(rmb);
            byte a = 0x80;//天线
            if (a1.Checked)
                a += 0x01;
            if (a2.Checked)
                a += 0x02;
            if (a3.Checked)
                a += 0x04;
            if (a4.Checked)
                a += 0x08;
            msg.Antenna = a;
            msg.IsLoop = (rbLoop.Checked) ? true : false;
            msg.Q = (byte)numQ.Value;

            
            this.Close();
        }
    }
}
