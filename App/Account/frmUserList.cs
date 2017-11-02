using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;
using DataGridViewAutoFilter;
using MCP;

namespace App.Account
{
    public partial class frmUserList :View.BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();

        public frmUserList()
        {
            InitializeComponent();
        }

        private void frmUserList_Load(object sender, EventArgs e)
        {
            DataTable dt = Program.dtUserPermission;
            //用户资料
            string filter = "SubModuleCode='MNU_W00C_00C' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Add.Visible = false;
            else
                this.toolStripButton_Add.Visible = true;
            filter = "SubModuleCode='MNU_W00C_00C' and OperatorCode='2'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Edit.Visible = false;
            else
                this.toolStripButton_Edit.Visible = true;
            filter = "SubModuleCode='MNU_W00C_00C' and OperatorCode='3'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Del.Visible = false;
            else
                this.toolStripButton_Del.Visible = true;
        }
        private void frmUserList_Activated(object sender, EventArgs e)
        {
            this.BindData();
        }
        private void dgvMain_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            //if (e.Button == MouseButtons.Right)
            //{
            //    if (e.RowIndex >= 0 && e.ColumnIndex >= 0)
            //    {
            //        //若行已是选中状态就不再进行设置
            //        if (dgvMain.Rows[e.RowIndex].Selected == false)
            //        {
            //            dgvMain.ClearSelection();
            //            dgvMain.Rows[e.RowIndex].Selected = true;
            //        }
            //        //只选中一行时设置活动单元格
            //        if (dgvMain.SelectedRows.Count == 1)
            //        {
            //            dgvMain.CurrentCell = dgvMain.Rows[e.RowIndex].Cells[e.ColumnIndex];
            //        }                    
            //        //弹出操作菜单
            //        contextMenuStrip1.Show(MousePosition.X, MousePosition.Y);
            //    }
            //}
        }


       

        private void toolStripButton_Refresh_Click(object sender, EventArgs e)
        {
            BindData();

        }
        private void toolStripButton_Add_Click(object sender, EventArgs e)
        {
            frmUserInfo frm = new frmUserInfo();
            if (frm.ShowDialog() == DialogResult.OK)
            {
                BindData();
            }
        }
        private void toolStripButton_Edit_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentRow == null)
                return;
            if (this.dgvMain.CurrentRow.Index >= 0)
            {
                DataRow druser = (this.dgvMain.CurrentRow.DataBoundItem as DataRowView).Row;
                frmUserInfo frm = new frmUserInfo(druser);
                if (frm.ShowDialog() == DialogResult.OK)
                {
                    BindData();
                } 
            }
        }
        private void toolStripButton_Del_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentRow == null)
                return;
            if (this.dgvMain.CurrentRow.Index >= 0)
            {
                if (this.dgvMain.CurrentRow.Cells["colUserName"].Value.ToString() != "admin")
                {
                    if (DialogResult.Yes == MessageBox.Show("您确定要删除此用户账号吗？", "询问", MessageBoxButtons.YesNo, MessageBoxIcon.Question))
                    {


                        bll.ExecNonQuery("Security.DeleteUser", new DataParameter[] { new DataParameter("{0}", (this.dgvMain.CurrentRow.DataBoundItem as DataRowView).Row["UserID"].ToString()) });
                        this.BindData();
                    }
                }
                else
                {
                    Logger.Info("管理员帐号不能删除！");
                    return;
                }
            }
        }
        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void BindData()
        {
            DataTable dt = bll.FillDataTable("Security.SelectUser");
            bsMain.DataSource = dt;
        }
    }
}
