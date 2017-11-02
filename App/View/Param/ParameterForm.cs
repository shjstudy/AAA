using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using MCP.Config;
using Util;


namespace App.View.Param
{
    public partial class ParameterForm : View.BaseForm
    {
        private App.View.Param.Parameter parameter = new App.View.Param.Parameter();
        //private DBConfig config = new DBConfig();
        MCP.Service.Siemens.Config.Configuration PLC1 = new MCP.Service.Siemens.Config.Configuration("PLC0101.xml");
        MCP.Service.Siemens.Config.Configuration PLC2 = new MCP.Service.Siemens.Config.Configuration("PLC0102.xml");
        MCP.Service.Siemens.Config.Configuration PLC3 = new MCP.Service.Siemens.Config.Configuration("PLC0103.xml");
        MCP.Service.Siemens.Config.Configuration PLC4 = new MCP.Service.Siemens.Config.Configuration("PLC0104.xml");
        MCP.Service.Siemens.Config.Configuration PLC5 = new MCP.Service.Siemens.Config.Configuration("PLC0105.xml");
        MCP.Service.Siemens.Config.Configuration PLC6 = new MCP.Service.Siemens.Config.Configuration("PLC0106.xml");
        MCP.Service.Siemens.Config.Configuration PLC7 = new MCP.Service.Siemens.Config.Configuration("PLC0107.xml");
       
        private Dictionary<string, string> attributes = null;

        public ParameterForm()
        {
            InitializeComponent();
            ReadParameter();
        }

        private void ReadParameter()
        {

            //扫描枪--由于使用USB接口，而屏蔽
            ConfigUtil configUtil = new ConfigUtil();
            attributes = configUtil.GetAttribute();
            parameter.WarehouseCode = attributes["WarehouseCode"];
            parameter.WcsUrl = attributes["WcsUrl"];
            parameter.SendInterval = attributes["SendInterval"];
            parameter.RequireAPReady = attributes["RequireAPReady"];
            //PLC1
            parameter.PLC1ServerName = PLC1.ProgID;
            parameter.PLC1ServerIP = PLC1.ServerName;
            parameter.PLC1GroupString = PLC1.GroupString;
            parameter.PLC1UpdateRate = PLC1.UpdateRate;
            //PLC2
            parameter.PLC2ServerName = PLC2.ProgID;
            parameter.PLC2ServerIP = PLC2.ServerName;
            parameter.PLC2GroupString = PLC2.GroupString;
            parameter.PLC2UpdateRate = PLC2.UpdateRate;
            //PLC3
            parameter.PLC3ServerName = PLC3.ProgID;
            parameter.PLC3ServerIP = PLC3.ServerName;
            parameter.PLC3GroupString = PLC3.GroupString;
            parameter.PLC3UpdateRate = PLC3.UpdateRate;
            //PLC4
            parameter.PLC4ServerName = PLC4.ProgID;
            parameter.PLC4ServerIP = PLC4.ServerName;
            parameter.PLC4GroupString = PLC4.GroupString;
            parameter.PLC4UpdateRate = PLC4.UpdateRate;
            //PLC5
            parameter.PLC5ServerName = PLC5.ProgID;
            parameter.PLC5ServerIP = PLC5.ServerName;
            parameter.PLC5GroupString = PLC5.GroupString;
            parameter.PLC5UpdateRate = PLC5.UpdateRate;
            //PLC6
            parameter.PLC6ServerName = PLC6.ProgID;
            parameter.PLC6ServerIP = PLC6.ServerName;
            parameter.PLC6GroupString = PLC6.GroupString;
            parameter.PLC6UpdateRate = PLC6.UpdateRate;
            //PLC7
            parameter.PLC7ServerName = PLC7.ProgID;
            parameter.PLC7ServerIP = PLC7.ServerName;
            parameter.PLC7GroupString = PLC7.GroupString;
            parameter.PLC7UpdateRate = PLC7.UpdateRate;
           
            propertyGrid.SelectedObject = parameter;
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            try
            {
                //保存本机数据库连接参数
                //config.Parameters["server"] = parameter.ServerName;
                //config.Parameters["uid"] = parameter.DBUser;
                //config.Parameters["pwd"] = parameter.Password;
                //config.Save();


                //由于扫码枪使用USB接口，而屏蔽。
                //保存Context参数
                attributes["WarehouseCode"] = parameter.WarehouseCode;
                attributes["InTaskCount"] = "2";
                attributes["WcsUrl"] = parameter.WcsUrl;
                attributes["UserName"] = "admin";
                attributes["SendInterval"] = parameter.SendInterval;
                attributes["RequireAPReady"] = parameter.RequireAPReady;
                ConfigUtil configUtil = new ConfigUtil();
                configUtil.Save(attributes);

                //PLC1
                PLC1.GroupString = parameter.PLC1GroupString;
                PLC1.ProgID = parameter.PLC1ServerName;
                PLC1.UpdateRate = parameter.PLC1UpdateRate;
                PLC1.ServerName = parameter.PLC1ServerIP;
                PLC1.Save();
                //PLC2
                PLC2.GroupString = parameter.PLC2GroupString;
                PLC2.ProgID = parameter.PLC2ServerName;
                PLC2.UpdateRate = parameter.PLC2UpdateRate;
                PLC2.ServerName = parameter.PLC2ServerIP;
                PLC2.Save();
                //PLC3
                PLC3.GroupString = parameter.PLC3GroupString;
                PLC3.ProgID = parameter.PLC3ServerName;
                PLC3.UpdateRate = parameter.PLC3UpdateRate;
                PLC3.ServerName = parameter.PLC3ServerIP;
                PLC3.Save();
                //PLC4
                PLC4.GroupString = parameter.PLC4GroupString;
                PLC4.ProgID = parameter.PLC4ServerName;
                PLC4.UpdateRate = parameter.PLC4UpdateRate;
                PLC4.ServerName = parameter.PLC4ServerIP;
                PLC4.Save();
                //PLC5
                PLC5.GroupString = parameter.PLC5GroupString;
                PLC5.ProgID = parameter.PLC5ServerName;
                PLC5.UpdateRate = parameter.PLC5UpdateRate;
                PLC5.ServerName = parameter.PLC5ServerIP;
                PLC5.Save();
                //PLC6
                PLC6.GroupString = parameter.PLC6GroupString;
                PLC6.ProgID = parameter.PLC6ServerName;
                PLC6.UpdateRate = parameter.PLC6UpdateRate;
                PLC6.ServerName = parameter.PLC6ServerIP;
                PLC6.Save();
                //PLC7
                PLC7.GroupString = parameter.PLC7GroupString;
                PLC7.ProgID = parameter.PLC7ServerName;
                PLC7.UpdateRate = parameter.PLC7UpdateRate;
                PLC7.ServerName = parameter.PLC7ServerIP;
                PLC7.Save();
                 

                MessageBox.Show("系统参数保存成功，请重新启动本系统。", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (Exception exp)
            {
                MessageBox.Show("保存系统参数过程中出现异常，原因：" + exp.Message, "错误", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}

