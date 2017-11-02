using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MCP;
using Util;

namespace App.View.Task
{
    public partial class frmOutStockEdit : Form
    {


        public DataRow drEdit;
        private string State;
        BLL.BLLBase bll = new BLL.BLLBase();
        public frmOutStockEdit()
        {
            InitializeComponent();
        }
        public frmOutStockEdit(string state)
        {
            InitializeComponent();
            State = state;
        }



        private void btnSave_Click(object sender, EventArgs e)
        {

            string BillID = this.txtBillID.Text.Trim();
            string ProductCode = this.txtProductName.Text.Trim();
            string Qty = this.txtQty.Text.Trim();
            if (BillID == "")
            {
                Logger.Error("出库单号不能为空，请输入");
                this.txtBillID.Focus();
                return;
            }
            if (ProductCode == "")
            {
                Logger.Error("产品编号不能为空，请输入");
                this.txtProductCode.Focus();
                return;
            }
            if (Qty == "" || Qty == "0")
            {
                Logger.Error("料箱数量不能为空，请输入");
                this.txtQty.Focus();
                return;
            }

            //判断现有库存是否大于出库数量
            int count = bll.GetRowCount("CMD_Cell", string.Format("ProductCode='{0}' and IsActive='1' and IsLock=0 and ErrorFlag=0 and Indate is not Null", this.txtProductCode.Text));
            if (count < int.Parse(Qty))
            {
                Logger.Error("现有库存数量小于出库数量，不能出库!");
                this.txtQty.Focus();
                return;
            }

            DataTable dt = bll.FillDataTable("WMS.SelectOutStock", new DataParameter[] { new DataParameter("{0}", "BillID like 'OS%' and State=0 and BillID!='" + BillID + "' and ProductCode='" + this.txtProductCode.Text + "'") });
            if (dt.Rows.Count > 0)
            {
                int LockCount = int.Parse(dt.Compute("sum(qty)", "").ToString());
                if (count - LockCount < int.Parse(Qty))
                {
                    Logger.Error("现有库存数量小于出库数量，不能出库!");
                    this.txtQty.Focus();
                    return;
                }
            }

            string Comd = "WMS.UpdateOutStock";

            if (State == "add")
            {
                Comd = "WMS.InsertOutStock";
                if (bll.GetRowCount("WMS_BILL", "BillID='" + BillID + "'") > 0)
                {
                    Logger.Error(BillID + " 出库单号重复，请输入新编号");
                    return;
                }
            }
            DataParameter[] paras = new DataParameter[] { new DataParameter("@BillDate",this.dtpBillDate.Value),
                                                          new DataParameter("@BillID",this.txtBillID.Text.Trim()),
                                                          new DataParameter("@ProductCode",this.txtProductCode.Text),
                                                          new DataParameter("@Qty",this.txtQty.Text.Trim()),
                                                          new DataParameter("@Memo",this.txtMemo.Text.Trim()),
                                                          new DataParameter("@Creator",Program.CurrentUser),
                                                          new DataParameter("@Updater",Program.CurrentUser)};


            bll.ExecNonQuery(Comd, paras);

            this.DialogResult = DialogResult.OK;

        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
        private void frmProductEdit_Shown(object sender, EventArgs e)
        {

            if (State == "add")
            {
                this.dtpBillDate.ValueChanged += new EventHandler(dtpBillDate_ValueChanged);
                this.txtBillID.Text = bll.GetAutoCodeByTableName("OS", "WMS_Bill", this.dtpBillDate.Value, "1=1");

                this.txtCreator.Text = App.Program.CurrentUser;
                this.txtCreateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
                this.txtUpdater.Text = Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");

            }
            else
            {
               
                this.txtBillID.ReadOnly = true;
                this.dtpBillDate.Value = (DateTime)drEdit["BillDate"];
                this.txtBillID.Text = drEdit["BillID"].ToString();
                this.txtProductCode.Text = drEdit["ProductCode"].ToString();
                this.txtProductName.Text = drEdit["ProductName"].ToString();
                this.txtQty.Text = drEdit["Qty"].ToString();
                this.txtMemo.Text = drEdit["Memo"].ToString();
                this.txtCreator.Text = drEdit["Creator"].ToString();
                this.txtCreateDate.Text = drEdit["CreateDate"].ToString();
                this.txtUpdater.Text = App.Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
            }
            txtProductName.Focus();
        }


        private void txtQty_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private void dtpBillDate_ValueChanged(object sender, EventArgs e)
        {
            this.txtBillID.Text = bll.GetAutoCodeByTableName("OS", "WMS_Bill", this.dtpBillDate.Value, "1=1");
        }

        private void txtProductCode_TextChanged(object sender, EventArgs e)
        {
            string Productname = bll.GetFieldValue("VCMD_ProductInStock", "ProductName", string.Format("ProductCode='{0}' and ProductCode!=''", this.txtProductCode.Text.Trim()));
            if (Productname != "")
            {
                this.txtProductName.Text = Productname;
            }
        }

        private void txtProductCode_DoubleClick(object sender, EventArgs e)
        {
            Common.frmSelect frm = new Common.frmSelect(false, "CMD_ProductInStock", "ProductCode!=''");
            if (frm.ShowDialog() == DialogResult.OK)
            {
                this.txtProductCode.Text = frm.dtSelect.Rows[0]["ProductCode"].ToString();
                this.txtProductName.Text = frm.dtSelect.Rows[0]["ProductName"].ToString();
            }
        }
    }
}