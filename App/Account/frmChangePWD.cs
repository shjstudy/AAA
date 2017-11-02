using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.Account
{
    public partial class frmChangePWD : Form
    {
        public frmChangePWD()
        {
            InitializeComponent();
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (this.txtUser.Text.Trim().Length == 0)
            {
                MessageBox.Show("请输入用户！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (this.txtPWD.Text.Trim().Length == 0)
            {
                MessageBox.Show("请输入密码！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (this.txtNewPWD.Text.Trim().Length == 0)
            {
                MessageBox.Show("请输入新密码！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            if (this.txtNewPWD.Text.Trim()!=this.txtNewPWD2.Text.Trim())
            {
                MessageBox.Show("密码不一致！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }


            BLL.UserBll userBll = new BLL.UserBll();

            DataTable dtUserList = userBll.GetUserInfo(txtUser.Text.Trim());

            if (dtUserList.Rows[0]["UserPassword"].ToString().Trim() == this.txtPWD.Text.Trim())
            {

                userBll.UpdateUserPWD(this.txtUser.Text.Trim(), this.txtNewPWD.Text.Trim());
                MessageBox.Show("密码修改成功!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.DialogResult = DialogResult.OK;
                
            }
            else
            {
                MessageBox.Show("原密码错误!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
            }

        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
        private void txtPWD_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnOK_Click(null, null);
            }
        }
    }
}
