using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View
{
    public partial class frmReassign : Form
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CraneNo = "01";
        int Layer = 0;

        public frmReassign()
        {
            InitializeComponent();
        }
        public frmReassign(DataRow dr)
        {
            InitializeComponent();
            this.dr = dr;
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (this.dgvMain.CurrentRow == null)
                return;

            if (this.dgvMain.CurrentRow.Index == -1)
            {
                MessageBox.Show("请选择货位", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            string CellCode = this.dgvMain.Rows[this.dgvMain.CurrentRow.Index].Cells[0].Value.ToString();
            string TaskNo = this.txtTaskNo.Text;

            DataParameter[] param;
            param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("AreaCode='{0}' and CellCode='{0}' and IsActive='1' and IsLock='0' and ErrorFlag!='1'",this.txtAreaCode.Text,CellCode))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectCell", param);
            if (dt.Rows.Count <= 0)
            {
                MessageBox.Show("此货位非指定出库产品货位,请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            param = new DataParameter[] 
            { 
                new DataParameter("@TaskNo", TaskNo), 
                new DataParameter("@NewCellCode", CellCode),
                new DataParameter("@IsTarget", "1")
            };

            bll.ExecNonQuery("WCS.Sp_UpdateTaskCellCode", param);
            
            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }

        private void frmReassign_Load(object sender, EventArgs e)
        {
            this.txtTaskNo.Text = dr["TaskNo"].ToString();
            this.txtCellCode.Text = dr["CellCode"].ToString();
            this.txtAisleNo.Text = dr["AisleNo"].ToString();
            this.txtCraneNo.Text = dr["CraneNo"].ToString();
            this.txtCarNo.Text = dr["CarNo"].ToString();
            this.txtAreaCode.Text = dr["AreaCode"].ToString();
            this.txtAreaName.Text = dr["AreaName"].ToString();
            this.txtAreaCode.Text = dr["AreaCode"].ToString();
            this.txtPalletCode.Text = dr["PalletCode"].ToString();
            

            string filter = string.Format("CMD_Cell.IsLock='0' and CMD_Cell.IsActive='1' and CMD_Cell.ErrorFlag!='1' and PalletBarcode!='' and CMD_Shelf.AisleNo='{0}' and CMD_Cell.AreaCode='{1}'", this.txtAisleNo.Text, this.txtAreaCode.Text);

            if (this.txtAreaCode.Text == "002")
            {
                Layer = int.Parse(this.txtCellCode.Text.Substring(6, 3));
                filter = string.Format("CMD_Cell.IsLock='0' and CMD_Cell.IsActive='1' and CMD_Cell.ErrorFlag!='1' and PalletBarcode!='' and CMD_Shelf.AisleNo='{0}' and CMD_Cell.AreaCode='{1}' and CMD_Cell.CellRow={2}", this.txtAisleNo.Text, this.txtAreaCode.Text,Layer);
            }
            DataTable dt = bll.FillDataTable("WCS.SelectCellByFilter", new DataParameter[] { new DataParameter("{0}", filter) });

            this.bsMain.DataSource = dt;
        }

        private void dgvMain_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex != -1 && e.ColumnIndex != -1)
            {                
                this.DialogResult = System.Windows.Forms.DialogResult.OK;
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        }

        private void dgvMain_RowEnter(object sender, DataGridViewCellEventArgs e)
        {
            if (dgvMain.Rows[e.RowIndex].Cells[1].Value == null)
                return;
            string PalletBarcode = dgvMain.Rows[e.RowIndex].Cells[1].Value.ToString();
            DataTable dt = bll.FillDataTable("WMS.SelectWmsPallet", new DataParameter[] { new DataParameter("{0}", string.Format("PalletCode='{0}'", PalletBarcode)) });
            this.bsDetail.DataSource = dt;  
        }        
    }    
}
