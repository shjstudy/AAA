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

namespace App.View.Base
{
    public partial class frmDevices : BaseForm
    {

        BLL.BLLBase bll = new BLL.BLLBase();

        public frmDevices()
        {
            InitializeComponent();
        }
        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void toolStripButton_Refresh_Click(object sender, EventArgs e)
        {
            BindData();

        }

        private void BindData()
        {
            DataParameter[] param = new DataParameter[] { new DataParameter("{0}", string.Format("WarehouseCode = '{0}'",Program.WarehouseCode)) };
            DataTable dt = bll.FillDataTable("CMD.SelectDevice", param);
            bsMain.DataSource = dt;
        }


        private void frmProducts_Activated(object sender, EventArgs e)
        {
            this.BindData();
        }

        private void toolStripButton_Add_Click(object sender, EventArgs e)
        {
            frmDeviceEdit f = new frmDeviceEdit();
            if (f.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                this.BindData();
            }
        }

        private void toolStripButton_Delete_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentRow == null)
                return;
            if (this.dgvMain.CurrentRow.Index >= 0)
            {
                string DeviceNo = this.dgvMain.SelectedRows[0].Cells[1].Value.ToString();

                //if (this.dgvMain.SelectedRows[0].Cells["colFixed"].Value.ToString() == "0")
                //{
                //    //删除产品，先判断此产品是否被用到
                //    //判断能否删除
                //    int Count = bll.GetRowCount("VUsed_CMD_Product", string.Format("ProductCode='{0}'", ProductCode));
                //    if (Count > 0)
                //    {
                //        MessageBox.Show("此产品已被单据使用，不可删除！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                //        return;
                //    }
                //    if (MessageBox.Show("您确定要删除此产品吗?", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == System.Windows.Forms.DialogResult.Yes)
                //    {
                //        bll.ExecNonQuery("Cmd.DeleteProduct", new DataParameter[] { new DataParameter("{0}", ProductCode) });
                //        this.BindData();
                //    }
                //}
                //else
                //{
                    MessageBox.Show("此产品为系统内定，不可删除！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                //}
            }
        }

        private void toolStripButton_Modify_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentRow == null)
                return;
            if (this.dgvMain.CurrentRow.Index >= 0)
            {
                string DeviceNo = this.dgvMain.SelectedRows[0].Cells[1].Value.ToString();
                DataRowView drv = dgvMain.SelectedRows[0].DataBoundItem as DataRowView;
                DataRow dr = drv.Row;

                frmDeviceEdit f = new frmDeviceEdit(dr);
                if (f.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    this.BindData();
                }
            }
        }

    }
}


