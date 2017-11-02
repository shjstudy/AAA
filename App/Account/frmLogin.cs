using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MCP.Config;
using Util;

namespace App.Account
{
    public partial class frmLogin : Form
    {
        Dictionary<string, string> attributes = null;
        public frmLogin()
        {
            InitializeComponent();
        }
        private void frmLogin_Load(object sender, EventArgs e)
        {
            ConfigUtil configUtil = new ConfigUtil();
            attributes = configUtil.GetAttribute();
            this.txtUserName.Text = attributes["UserName"];

            this.Show();
            if (txtUserName.Text != "")
                txtPassWord.Focus();
            else
                txtUserName.Focus();            
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (this.txtUserName.Text.Trim().Length != 0)
            {
                BLL.UserBll userBll = new BLL.UserBll();

                DataTable dtUserList = userBll.GetUserInfo(txtUserName.Text.Trim());
                if (dtUserList != null && dtUserList.Rows.Count > 0)
                {
                    if (dtUserList.Rows[0]["UserPassword"].ToString().Trim() == txtPassWord.Text.Trim())
                    {
                        //保存Context参数
                        
                        attributes["UserName"] = this.txtUserName.Text.Trim();

                        ConfigUtil configUtil = new ConfigUtil();
                        configUtil.Save(attributes);
                        Program.CurrentUser = this.txtUserName.Text;
                        BLL.BLLBase bll = new BLL.BLLBase();
                        Program.dtUserPermission = bll.FillDataTable("Security.SelectUserPermission", new DataParameter[] { new DataParameter("@UserName", this.txtUserName.Text.Trim()), new DataParameter("@SystemName", "WCS") });
                        this.DialogResult = DialogResult.OK;
                    }
                    else
                    {
                        MessageBox.Show("对不起,您输入的密码有误!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                }
                else
                {
                    MessageBox.Show("对不起,您输入的用户名不存在!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                }
            }
            else
            {
                MessageBox.Show("请输入用户名!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }


        }
        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }        
    }
}
