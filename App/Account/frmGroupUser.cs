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
    public partial class frmGroupUser : Form
    {
        private BLL.BLLBase bll = new BLL.BLLBase();
        private string GroupID;
        private string GroupName;
        public frmGroupUser()
        {
            InitializeComponent();
        }
        public frmGroupUser(string groupID, string groupName)
        {
            InitializeComponent();
            GroupID = groupID;
            GroupName = groupName;
        }



        private void frmGroupUser_Load(object sender, EventArgs e)
        {
            this.Text = GroupName + " 成员设置";
            //colChk.Name = "Detail";
            //colChk.DefaultCellStyle.NullValue = "加入" + GroupName;
            colChk.HeaderText = "加入" + GroupName;
            colChk.DefaultCellStyle.NullValue = false;
            DataTable dt = bll.FillDataTable("Security.SelectAllUser");
            bsMain.DataSource = dt;
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            try
            {
                string users = "-1,";

                for (int i = 0; i < dgvMain.Rows.Count; i++)
                {
                    if (dgvMain.Rows[i].Cells[0].Value != null)
                        if (dgvMain.Rows[i].Cells[0].Value.ToString() == "True")
                            users += (dgvMain.Rows[i].DataBoundItem as DataRowView).Row["UserID"].ToString() + ",";

                }
                users += "-1";

                bll.ExecNonQuery("Security.UpdateUserGroup", new DataParameter[] { new DataParameter("@GroupID", GroupID), new DataParameter("{0}", users) });
                this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {

            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
    }
}
