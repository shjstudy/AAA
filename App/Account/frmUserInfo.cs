using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;


namespace App.Account
{
    public partial class frmUserInfo : Form
    {
        private DataRow drUser;
        public frmUserInfo()
        {
            InitializeComponent();
        }
        public frmUserInfo(DataRow dr)
        {
            InitializeComponent();
            drUser = dr;
        }

        private void frmUserInfo_Load(object sender, EventArgs e)
        {
            if (drUser != null)
            {
                txtUserName.Text = drUser["UserName"].ToString();
                txtEmployeeCode.Text = drUser["EmployeeCode"].ToString();
                txtMemo.Text = drUser["Memo"].ToString();
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                BLL.UserBll ubll = new BLL.UserBll();
                if (drUser != null)
                {
                    DataTable dtTemp = ubll.GetUserList(1, 10, string.Format("UserID<>{0} and UserName='{1}'", drUser["UserID"].ToString(), this.txtUserName.Text.Trim()), "UserName");
                    if (dtTemp.Rows.Count > 0)
                    {
                        MessageBox.Show("该用户名已经存在!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    ubll.UpdateUser(this.txtUserName.Text.Trim(), this.txtEmployeeCode.Text.Trim(), this.txtMemo.Text.Trim(), int.Parse(drUser["UserID"].ToString()));
                }
                else
                {
                    DataTable dtTemp = ubll.GetUserInfo(this.txtUserName.Text.Trim());
                    if (dtTemp.Rows.Count > 0)
                    {
                        MessageBox.Show("该用户名已经存在!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    if (this.txtEmployeeCode.Text.Trim() == "")
                    {
                        txtEmployeeCode.Text = txtUserName.Text.Trim();
                    }
                    ubll.InsertUser(this.txtUserName.Text.Trim(), this.txtEmployeeCode.Text, this.txtMemo.Text);
                }
                this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
    }
}
