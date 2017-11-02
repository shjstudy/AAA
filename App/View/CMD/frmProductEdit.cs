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

namespace App.View.CMD
{
    
    public partial class frmProductEdit : BaseForm
    {
        public DataRow drEdit;
        private string State;
        BLL.BLLBase bll = new BLL.BLLBase();
        public frmProductEdit()
        {
            InitializeComponent();
        }
        public frmProductEdit(string state)
        {
            InitializeComponent();
            State = state;
        }

         

        private void btnSave_Click(object sender, EventArgs e)
        {

            string CategoryCode = this.txtProductCode.Text.Trim();
            string CategoryName = this.txtProductName.Text.Trim();
            if (CategoryCode == "")
            {
                Logger.Error("产品编号不能为空，请输入");
                this.txtProductCode.Focus();
                return;
            }
            if (CategoryName == "")
            {
                Logger.Error("产品名称不能为空，请输入");
                this.txtProductName.Focus();
                return;
            }
            string Comd = "cmd.UpdateProduct";

            if (State == "add")
            {
                Comd = "cmd.InsertProduct";
                if (bll.GetRowCount("CMD_Product", "ProductCode='" + CategoryCode + "'") > 0)
                {
                    Logger.Error(CategoryCode + " 类别编号重复，请输入新编号");
                    return;
                }
            }
            DataParameter[] paras = new DataParameter[] { new DataParameter("@ProductCode",this.txtProductCode.Text.Trim()),
                                                          new DataParameter("@ProductName",this.txtProductName.Text.Trim()),
                                                          new DataParameter("@CategoryCode",this.cmbCategoryCode.SelectedValue.ToString()),
                                                          new DataParameter("@Spec",this.txtSpec.Text.Trim()),
                                                          new DataParameter("@SafeQty",this.txtSafeQty.Text.Trim()),
                                                          new DataParameter("@Unit",this.txtUnit.Text.Trim()),
                                                          new DataParameter("@Description",this.txtDescription.Text.Trim()), 
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
            DataTable dtCategoryCode = bll.FillDataTable("Cmd.SelectProductCategory", new DataParameter[] { new DataParameter("{0}", "1=1") });
            this.cmbCategoryCode.DisplayMember = "CategoryName";
            this.cmbCategoryCode.ValueMember = "CategoryCode";
            this.cmbCategoryCode.DataSource = dtCategoryCode;
            if (State == "add")
            {
                txtProductCode.Text = bll.GetNewID("CMD_Product", "ProductCode", "1=1");
                this.txtSafeQty.Text = "0";
                this.txtCreator.Text = App.Program.CurrentUser;
                this.txtCreateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
                this.txtUpdater.Text = Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");

            }
            else
            {
                this.txtProductCode.ReadOnly = true;
                this.txtProductCode.Text = drEdit["ProductCode"].ToString();
                this.txtProductName.Text = drEdit["ProductName"].ToString();
                this.cmbCategoryCode.SelectedValue = drEdit["CategoryCode"].ToString();
                this.txtUnit.Text = drEdit["Unit"].ToString();
                this.txtSpec.Text = drEdit["Spec"].ToString();
                this.txtSafeQty.Text = drEdit["SafeQty"].ToString();
                this.txtDescription.Text = drEdit["Description"].ToString();
                this.txtMemo.Text = drEdit["Memo"].ToString();
                this.txtCreator.Text = drEdit["Creator"].ToString();
                this.txtCreateDate.Text = drEdit["CreateDate"].ToString();
                this.txtUpdater.Text = App.Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
            }
            txtProductName.Focus();
        }

        private void txtSafeQty_KeyPress(object sender, KeyPressEventArgs e)
        {

            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }
    }
}
