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
    public partial class frmGroupInfo : Form
    {
        private DataRow drGroup;
        public frmGroupInfo()
        {
            InitializeComponent();
        }
        public frmGroupInfo(DataRow dr)
        {
            InitializeComponent();
            drGroup = dr;
        }

        private void frmUserInfo_Load(object sender, EventArgs e)
        {
            if (drGroup != null)
            {

                txtGroupName.Text = drGroup["GroupName"].ToString();
                txtMemo.Text = drGroup["Memo"].ToString();
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            

            try
            {
                BLL.BLLBase bll = new BLL.BLLBase();
                if (drGroup != null)
                {
                    int Count = bll.GetRowCount("sys_GroupList", string.Format("GroupID<>{0} and GroupName='{1}'", drGroup["GroupID"].ToString(), this.txtGroupName.Text.Trim()));
                    if (Count > 0)
                    {
                        MessageBox.Show("该用户组名称已经存在!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    bll.ExecNonQuery("Security.UpdateGroupInfo", new DataParameter[] { new DataParameter("@GroupName", this.txtGroupName.Text.Trim()), new DataParameter("@Memo", this.txtMemo.Text.Trim()), new DataParameter("@GroupID", drGroup["GroupID"].ToString()) });

                }
                else
                {
                    int Count = bll.GetRowCount("sys_GroupList", string.Format("GroupName='{0}'", this.txtGroupName.Text.Trim()));
                    if (Count > 0)
                    {
                        MessageBox.Show("该用户组名称已经存在!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        return;
                    }
                    bll.ExecNonQuery("Security.InsertGroup", new DataParameter[] { new DataParameter("@GroupName", this.txtGroupName.Text.Trim()), new DataParameter("@Memo", this.txtMemo.Text.Trim()), new DataParameter("@State", 1) });
                    
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
