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

namespace App.View.Task
{
    public partial class frmInventor : BaseForm
    {
        
        BLL.BLLBase bll = new BLL.BLLBase();

        public frmInventor()
        {
            InitializeComponent();
        }
        private void toolStripButton_Refresh_Click(object sender, EventArgs e)
        {
            this.toolStrip_CategoryCode.SelectedIndex = 0;
            this.toolStrip_BillID.Text = "";
            this.toolStrip_Product.Text = "";
            this.toolStrip_Spec.Text = "";

            BindData();
        }      

        private void BindData()
        {
            DataTable dt = bll.FillDataTable("WMS.SelectBillMaster", new DataParameter[] { new DataParameter("{0}", "Main.BillID like 'IN%'") });
            bsMain.DataSource = dt;
            if (dt.Rows.Count == 0)
                BindDetail("");
        }
        private void BindData(string filter)
        {
            DataTable dt = bll.FillDataTable("WMS.SelectBillMaster", new DataParameter[] { new DataParameter("{0}", string.Format("Main.BillID like 'OS%' and {0} ", filter)) });
            bsMain.DataSource = dt;
            if (dt.Rows.Count == 0)
                BindDetail("");
        }
        private void BindDetail(string BillID)
        {
            DataTable dt = bll.FillDataTable("WMS.SelectBillDetail", new DataParameter[] { new DataParameter("{0}", string.Format("BillID ='{0}' ", BillID)) });
            bsDetail.DataSource = dt;
        }
        
       

        private void frmOutStock_Activated(object sender, EventArgs e)
        {
            this.BindData();
        }

        private void toolStripButton_Query_Click(object sender, EventArgs e)
        {
           string filter = "1=1";
            if (this.toolStrip_CategoryCode.SelectedText != "请选择")
            {
                filter += string.Format(" and CategoryCode='{0}'", this.toolStrip_CategoryCode.SelectedItem.ToString());
            }
            if (this.toolStrip_BillID.Text.Trim() != "")
            {
                filter += " and Main.BillID like '%" + this.toolStrip_BillID.Text.Trim() + "%'";
            }
            if (this.toolStrip_Product.Text.Trim() != "")
            {
                filter += " and (ProductCode like '%" + this.toolStrip_Product.Text.Trim() + "%' or ProductName like '%" + this.toolStrip_Product.Text.Trim() + "%')";
            }
            if (this.toolStrip_Spec.Text.Trim() != "")
            {
                filter += " and Spec like '%" + this.toolStrip_Spec.Text.Trim() + "%'";
            }
            this.BindData(filter);
            
        }
        private void dgvMain_RowPostPaint(object sender, DataGridViewRowPostPaintEventArgs e)
        {
            Rectangle rectangle = new Rectangle(e.RowBounds.Location.X,
                e.RowBounds.Location.Y,
                this.dgvMain.RowHeadersWidth - 4,
                e.RowBounds.Height);

            TextRenderer.DrawText(e.Graphics, (e.RowIndex + 1).ToString(),
                dgvMain.RowHeadersDefaultCellStyle.Font,
                rectangle,
                dgvMain.RowHeadersDefaultCellStyle.ForeColor,
                TextFormatFlags.VerticalCenter | TextFormatFlags.Right);

        }

        private void toolStripButton_Add_Click(object sender, EventArgs e)
        {
            

            frmInventorEdit f = new frmInventorEdit("add");
            f.Owner = this;
            if (f.ShowDialog() == DialogResult.OK)
            {
                this.BindData();
            }
        }

        private void toolStripButton_Edit_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.SelectedRows.Count > 0)
            {
                DataRow dr = ((DataRowView)dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem).Row;
                string BillID = dr["BillID"].ToString();
                DataTable dt = bll.FillDataTable("WMS.SelectBillMaster", new DataParameter[] { new DataParameter("{0}", "Main.BillID='" + BillID + "'") });
                if (int.Parse(dt.Rows[0]["State"].ToString()) > 0)
                {
                    Logger.Info("盘点单号 " + dr["BillID"].ToString() + " 已经"+dt.Rows[0]["StateDesc"].ToString()+",不能修改!");
                    return;
                }
                frmInventorEdit f = new frmInventorEdit("edit");
                f.Owner = this;
                f.drEdit = dr;
                if (f.ShowDialog() == DialogResult.OK)
                {
                    this.BindData();
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

                        DataTable dt = bll.FillDataTable("WMS.SelectBillMaster", new DataParameter[] { new DataParameter("{0}", "Main.BillID='" + dr["BillID"].ToString() + "'") });
                        if (int.Parse(dt.Rows[0]["State"].ToString()) > 0)
                        {
                            Logger.Info("盘点单号 " + dr["BillID"].ToString() + " 已经" + dt.Rows[0]["StateDesc"].ToString() + ",不能修改!");
                            return;
                        }
                        strCode += "'" + dr["BillID"].ToString() + "',";
                    }
                }

                strCode += "'-1'";
                try
                {
                    bll.ExecNonQuery("WMS.DeleteBillMaster", new DataParameter[] { new DataParameter("{0}", strCode) });
                    bll.ExecNonQuery("WMS.DeleteBillDetail", new DataParameter[] { new DataParameter("{0}", string.Format("Billid in ({0})", strCode)) });
                    Logger.Info("删除盘点单号：" + strCode.Replace("'-1',", "").Replace(",'-1'", "").Replace("'", ""));
                }
                catch (Exception ex)
                {
                    Logger.Error("删除盘点单号出错，出错原因：" + ex.Message);
                    return;
                }
                this.BindData();

            }
        }

        private void toolStripButton_Close_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void toolStripButton_Task_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.SelectedRows.Count > 0)
            {
                try
                {
                    DataRow dr = ((DataRowView)dgvMain.Rows[this.dgvMain.CurrentCell.RowIndex].DataBoundItem).Row;
                    string BillID = dr["BillID"].ToString();
                    DataTable dt = bll.FillDataTable("WMS.SelectBillMaster", new DataParameter[] { new DataParameter("{0}", "Main.BillID='" + BillID + "'") });
                    if (int.Parse(dt.Rows[0]["State"].ToString()) > 0)
                    {
                        Logger.Info("盘点单号 " + dr["BillID"].ToString() + " 已经" + dt.Rows[0]["StateDesc"].ToString() + ",不能修改!");
                        return;
                    }
                    bll.ExecNonQuery("WMS.SpInventoryStockTask", new DataParameter[] { new DataParameter("@Bill", BillID), new DataParameter("@UserName", Program.CurrentUser) });
                    this.BindData();
                }
                catch (Exception ex)
                {
                    Logger.Error("盘点作业出错，原因:" + ex.Message);
                }

            }
            else
            {
                Logger.Info("请选择要盘点作业的数据行");
            }
        }

        private void dgvMain_CellMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {

        }

        private void dgvMain_RowEnter(object sender, DataGridViewCellEventArgs e)
        {
            DataRow dr = ((DataRowView)dgvMain.Rows[e.RowIndex].DataBoundItem).Row;
            BindDetail(dr["BillID"].ToString());
        }

        private void frmInventor_Shown(object sender, EventArgs e)
        {
            BindDropDown();
            this.toolStrip_CategoryCode.SelectedIndex = 0;
            this.BindData();
            for (int i = 1; i < this.dgvMain.Columns.Count - 1; i++)
                if (this.dgvMain.Columns[i].DataPropertyName.ToLower().IndexOf("date") == -1)
                    ((DataGridViewAutoFilterTextBoxColumn)this.dgvMain.Columns[i]).FilteringEnabled = true;

            DataTable dt = Program.dtUserPermission;
            string filter = "SubModuleCode='MNU_M00B_00C' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Add.Visible = false;
            else
                this.toolStripButton_Add.Visible = true;

            filter = "SubModuleCode='MNU_M00B_00C' and OperatorCode='2'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Edit.Visible = false;
            else
                this.toolStripButton_Edit.Visible = true;

            filter = "SubModuleCode='MNU_M00B_00C' and OperatorCode='3'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Del.Visible = false;
            else
                this.toolStripButton_Del.Visible = true;

            filter = "SubModuleCode='MNU_M00B_00C' and OperatorCode='4'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_Task.Visible = false;
            else
                this.toolStripButton_Task.Visible = true;
        }

        private void BindDropDown()
        {
            DataTable dt = bll.FillDataTable("CMD.SelectProductCategory");
            this.toolStrip_CategoryCode.Items.Add("请选择");
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                this.toolStrip_CategoryCode.Items.Add(dt.Rows[i]["CategoryCode"].ToString());
            }
        }
    }
}
