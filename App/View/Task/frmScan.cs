using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Util;
using MCP;

namespace App.View.Task
{
    public partial class frmScan : Form
    {
        public string strValue;
        BLL.BLLBase bll = new BLL.BLLBase();
        private DataRow drCell;
        private bool blnOK = false;
        private string productCode = "";
        private string Qty = "";

        private const int CP_NOCLOSE_BUTTON = 0x200;
        protected override CreateParams CreateParams
        {
            get
            {

                CreateParams myCp = base.CreateParams;
                myCp.ClassStyle = myCp.ClassStyle | CP_NOCLOSE_BUTTON;

                return myCp;

            }
        }

        public frmScan()
        {
            InitializeComponent();
        }
        public frmScan( DataTable dt)
        {
            InitializeComponent();
            drCell = dt.Rows[0];
           
        }

        private void frmCellOpDialog_Shown(object sender, EventArgs e)
        {
            this.txtTaskNo.Text = drCell["TaskNo"].ToString();
           
            this.txtPalletCode.Text = drCell["PalletBarCode"].ToString();
            this.txtProductCode.Text = drCell["ProductCode"].ToString();
            this.txtProductName.Text = drCell["ProductName"].ToString();
            this.txtQty.Text = drCell["Qty"].ToString();  
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (!chk1.Checked)
            {
                if (this.txtProductName.Text != "")
                {
                    Logger.Info("非空箱，请输入产品资料！");
                    this.txtProductCode.Focus();
                    return;
                }
                if (this.txtQty.Text.Trim() == "0" || this.txtQty.Text.Trim() == "")
                {
                    Logger.Info("非空箱，请输入产品数量！");
                    this.txtQty.Focus();
                    return;
                }
            }
            bll.ExecNonQuery("WCS.UpdateTaskState", new DataParameter[] { new DataParameter("{0}", string.Format("NewProductCode='{0}',NewQty={1}", this.txtProductCode.Text.Trim(),this.txtQty.Text.Trim())), new DataParameter("{1}", string.Format("TaskNo='{0}'", this.txtTaskNo.Text)) });
            strValue = "1";
            this.DialogResult = DialogResult.OK;
        }

        
        private void btnCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
        }

        private void txtQty_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        //选择物料
        private void txtProductCode_DoubleClick(object sender, EventArgs e)
        {
            Common.frmSelect frm = new Common.frmSelect(false, "CMD_Product", "1=1");
            if (frm.ShowDialog() == DialogResult.OK)
            {
                this.txtProductCode.Text = frm.dtSelect.Rows[0]["ProductCode"].ToString();
                this.txtProductName.Text = frm.dtSelect.Rows[0]["ProductName"].ToString();
                if (this.txtQty.Text.Trim() == "" || this.txtQty.Text == "0")
                    this.txtQty.Text = "12";
            }
        }

        private void txtProductCode_TextChanged(object sender, EventArgs e)
        {
            string Productname = bll.GetFieldValue("CMD_Product", "ProductName", string.Format("ProductCode='{0}'", this.txtProductCode.Text.Trim()));
            if (Productname != "")
            {
                this.txtProductName.Text = Productname;
                if (this.txtQty.Text.Trim() == "" || this.txtQty.Text == "0")
                    this.txtQty.Text = "12";
            }

        }


        private void chk1_CheckedChanged(object sender, EventArgs e)
        {
            if (this.chk1.Checked)
            {
                this.txtProductCode.Text = "";
                this.txtProductName.Text = "";
                this.txtQty.Text = "0";
                this.txtQty.ReadOnly = true;
                this.txtProductCode.ReadOnly = true;
            }
            else
            {

                this.txtQty.ReadOnly = false;
                this.txtProductCode.ReadOnly = false;
            }

        }

        
       
          
    }
}
