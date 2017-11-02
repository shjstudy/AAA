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
    public partial class frmProduct : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();

       

        List<string> comds = new List<string>();
        List<DataParameter[]> paras = new List<DataParameter[]>();

        public frmProduct()
        {
            InitializeComponent();
        }

        private void frmProduct_Shown(object sender, EventArgs e)
        {
            for (int i = 1; i < this.dgvMain.Columns.Count; i++)
                if (this.dgvMain.Columns[i].DataPropertyName.ToLower().IndexOf("date") == -1)
                    ((DataGridViewAutoFilterTextBoxColumn)this.dgvMain.Columns[i]).FilteringEnabled = true;
            BindData("1=1");

            DataTable dt = Program.dtUserPermission;
            string filter = "SubModuleCode='MNU_M00A_00B' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Add.Visible = false;
            else
                this.toolStripButton_Add.Visible = true;

            filter = "SubModuleCode='MNU_M00A_00B' and OperatorCode='2'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Edit.Visible = false;
            else
                this.toolStripButton_Edit.Visible = true;

            filter = "SubModuleCode='MNU_M00A_00B' and OperatorCode='3'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Del.Visible = false;
            else
                this.toolStripButton_Del.Visible = true;

        }

        private void BindData(string BingFilter)
        {
            DataTable dt = bll.FillDataTable("Cmd.SelectProduct", new DataParameter[] { new DataParameter("{0}", BingFilter) });
            bsMain.DataSource = dt;

        }
        private void toolStripButton_Query_Click(object sender, EventArgs e)
        {
            string filter = "1=1";
            if (toolStrip_ProductCode.Text != "")
            {
                filter += " and ProductCode like '%" + toolStrip_ProductCode.Text.Trim() + "%'";
            }
            if (toolStrip_ProductName.Text != "")
            {
                filter += " and ProductName like '%" + toolStrip_ProductName.Text.Trim() + "%'";
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
                frmProductEdit f = new frmProductEdit("edit");
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
            if (MessageBox.Show("确定要删除数据吗？", "询问", MessageBoxButtons.OKCancel, MessageBoxIcon.Question) == DialogResult.OK)
            {
                string strCode = "'-1',";
                for (int i = 0; i < dgvMain.RowCount; i++)
                {
                    if (dgvMain.Rows[i].Cells[0].EditedFormattedValue.ToString() == "True")
                    {
                        DataRow dr = ((DataRowView)dgvMain.Rows[i].DataBoundItem).Row;

                        if (bll.GetRowCount("VUsed_CMD_Product", "ProductCode='" + dr["ProductCode"].ToString() + "'") > 0)
                        {
                            Logger.Info("产品编号 " + dr["ProductCode"].ToString() + " 已经被其它单据使用，无法删除！");
                            return;
                        }
                        strCode += "'" + dr["ProductCode"].ToString() + "',";
                    }
                }

                strCode += "'-1'";
                bll.ExecNonQuery("Cmd.DeleteProduct", new DataParameter[] { new DataParameter("{0}", strCode) });
                Logger.Info("删除产品编号：" + strCode.Replace("'-1',", "").Replace(",'-1'", "").Replace("'", ""));
                this.BindData("1=1");

            }
        }

        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void toolStripButton_Refresh_Click(object sender, EventArgs e)
        {
            this.toolStrip_ProductCode.Text = "";
            this.toolStrip_ProductName.Text = "";
            this.BindData("1=1");
        }

       

       
    }
}
