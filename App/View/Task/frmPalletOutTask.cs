using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View.Task
{
    public partial class frmPalletOutTask : Form
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CraneNo = "01";

        public frmPalletOutTask()
        {
            InitializeComponent();
        }

        private void btnRequest_Click(object sender, EventArgs e)
        {            
            DataParameter[] param;
            int TaskCount = 0;
            int.TryParse(this.txtTaskCount.Text.Trim(), out TaskCount);
            this.txtCellCode.Text = this.cbRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbHeight.Text)).ToString().Substring(1, 3);

            param = new DataParameter[] 
            { 
                new DataParameter("@AreaCode", this.cmbAreaCode.SelectedValue.ToString()), 
                new DataParameter("@TaskCount", TaskCount),
                new DataParameter("@Barcode", this.txtBarcode.Text),
                new DataParameter("@CellCode", this.txtCellCode.Text),
            };

            if (this.radioButton1.Checked)
            {
                if(TaskCount<=0)
                {
                    MessageBox.Show("请输入正确的任务数量！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
                }
                param = new DataParameter[] 
                { 
                    new DataParameter("@AreaCode", this.cmbAreaCode.SelectedValue.ToString()), 
                    new DataParameter("@TaskCount", TaskCount),
                    new DataParameter("@Barcode", ""),
                    new DataParameter("@CellCode", ""),
                };
            }
            else if(this.radioButton2.Checked)
            {
                if (this.txtBarcode.Text.Trim().Length <= 0)
                {
                    MessageBox.Show("请输入条码编号！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                }
                param = new DataParameter[] 
                { 
                    new DataParameter("@AreaCode", this.cmbAreaCode.SelectedValue.ToString()), 
                    new DataParameter("@TaskCount", 0),
                    new DataParameter("@Barcode", this.txtBarcode.Text.Trim()),
                    new DataParameter("@CellCode", ""),
                };
            }
            else if (this.radioButton3.Checked)
            {
                this.txtCellCode.Text = this.cbRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbHeight.Text)).ToString().Substring(1, 3) + this.cbToDepth.Text;
                if (this.txtCellCode.Text.Trim().Length <= 0)
                {
                    MessageBox.Show("请选择正确的货位！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                }
                param = new DataParameter[] 
                { 
                    new DataParameter("@AreaCode", this.cmbAreaCode.SelectedValue.ToString()), 
                    new DataParameter("@TaskCount", 0),
                    new DataParameter("@Barcode", this.txtBarcode.Text.Trim()),
                    new DataParameter("@CellCode", ""),
                };
            }
            DataTable dt = bll.FillDataTable("WCS.Sp_CreatePalletOutTask", param);
            if (dt.Rows.Count > 0)
            {
                if (int.Parse(dt.Rows[0][0].ToString()) > 0)
                    this.DialogResult = System.Windows.Forms.DialogResult.OK;
                else
                    MessageBox.Show("没有产生盘/箱出库任务，请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            else
                MessageBox.Show("没有产生盘/箱出库任务，请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void frmPalletOutTask_Load(object sender, EventArgs e)
        {
            DataTable dt = bll.FillDataTable("CMD.SelectArea");
            this.cmbAreaCode.DataSource = dt.DefaultView;
            this.cmbAreaCode.ValueMember = "AreaCode";
            this.cmbAreaCode.DisplayMember = "AreaName";           
        }        

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.No;
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            if (this.radioButton3.Checked)
            {
                this.cbRow.Enabled = true;
                this.cbColumn.Enabled = true;
                this.cbHeight.Enabled = true;
            }
            else
            {
                this.cbRow.Enabled = false;
                this.cbColumn.Enabled = false;
                this.cbHeight.Enabled = false;
            }
        }

        private void cmbAreaCode_SelectedIndexChanged(object sender, EventArgs e)
        {
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("AreaCode='{0}'", this.cmbAreaCode.SelectedValue.ToString()))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectShelf", param);
            this.cbRow.DataSource = dt.DefaultView;
            this.cbRow.ValueMember = "shelfcode";
            this.cbRow.DisplayMember = "shelfcode";


            DataTable dtDepth = new DataTable("dt");
            dtDepth.Columns.Add("dtText");
            dtDepth.Columns.Add("dtValue");
            DataRow dr = dtDepth.NewRow();
            dr["dtText"] = "1";
            dr["dtValue"] = "1";
            dtDepth.Rows.Add(dr);
            if (this.cmbAreaCode.SelectedIndex == 2)
            {
                dr = dtDepth.NewRow();
                dr["dtText"] = "2";
                dr["dtValue"] = "2";
                dtDepth.Rows.Add(dr);
            }
            this.cbToDepth.DataSource = dtDepth;
            this.cbToDepth.DisplayMember = "dtText";
            this.cbToDepth.ValueMember = "dtValue";
        }
        private void cbRow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbRow.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

            this.cbColumn.DataSource = dt.DefaultView;
            this.cbColumn.ValueMember = "CellColumn";
            this.cbColumn.DisplayMember = "CellColumn";
        }

        private void cbColumn_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;
            if (this.cbColumn.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbRow.Text,this.cbColumn.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
            DataView dv = dt.DefaultView;
            dv.Sort = "CellRow";
            this.cbHeight.DataSource = dv;
            this.cbHeight.ValueMember = "CellRow";
            this.cbHeight.DisplayMember = "CellRow";
        }        
    }
}
