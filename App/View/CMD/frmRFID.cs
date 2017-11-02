using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DataGridViewAutoFilter;
using Util;
using MCP;

namespace App.View.CMD
{
    public partial class frmRFID : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();

        

        List<string> comds = new List<string>();
        List<DataParameter[]> paras = new List<DataParameter[]>();

        public frmRFID()
        {
            InitializeComponent();
        }

        private void frmProduct_Shown(object sender, EventArgs e)
        {
            for (int i = 1; i < this.dgvMain.Columns.Count; i++)
                ((DataGridViewAutoFilterTextBoxColumn)this.dgvMain.Columns[i]).FilteringEnabled = true;
            this.BindData("1=1");
            DataTable dt = Program.dtUserPermission;
            string filter = "SubModuleCode='MNU_M00A_00C' and OperatorCode='2'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Edit.Visible = false;
            else
                this.toolStripButton_Edit.Visible = true;
        }

        private void BindData(string BingFilter)
        {
            DataTable dt = bll.FillDataTable("Cmd.SelectPallet", new DataParameter[] { new DataParameter("{0}", BingFilter) });
            bsMain.DataSource = dt;
            
        }
        private void toolStripButton_Query_Click(object sender, EventArgs e)
        {
            string filter = "1=1";
            if (toolStrip_RFIDCode.Text.Trim() != "")
            {
                filter += " and RFIDCode like '%" + toolStrip_RFIDCode.Text.Trim() + "%'";
            }
             
            if (toolStrip_PalletID.Text.Trim() != "")
            {
                filter += " and PalletID like '%" + toolStrip_PalletID.Text.Trim() + "%'";
            }
            BindData(filter);
        }

        private void toolStripButton_Add_Click(object sender, EventArgs e)
        {
            frmProductEdit f = new frmProductEdit("add");
            f.Owner = this;
            if (f.ShowDialog() == DialogResult.OK)
            {
                this.BindData("1=1");
            }
        }

        private void toolStripButton_Edit_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.SelectedRows.Count > 0)
            {
                DataRow dr = ((DataRowView)dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem).Row;
                frmRFIDEdit f = new frmRFIDEdit("edit");
                f.Owner = this;
                f.drEdit = dr;
                if (f.ShowDialog() == DialogResult.OK)
                {
                    this.BindData("1=1");
                }

            }
            else
            {
                Logger.Info("请选择要修改的数据行");
            }
        }

        private void toolStripButton_Del_Click(object sender, EventArgs e)
        {

        }

        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void toolStripButton_Refresh_Click(object sender, EventArgs e)
        {
            this.BindData("1=1");
        }

       

       

       
    }
}
