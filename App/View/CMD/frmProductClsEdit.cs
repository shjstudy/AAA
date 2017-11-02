using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;
using MCP;

namespace App.View.CMD
{
    public partial class frmProductClsEdit : BaseForm
    {
        public DataRow drEdit;
        private string State;
        BLL.BLLBase bll = new BLL.BLLBase();
        public frmProductClsEdit()
        {
            InitializeComponent();
        }
        public frmProductClsEdit(string state)
        {
            InitializeComponent();
            State = state;
        }

        private void frmProductClsEdit_Load(object sender, EventArgs e)
        {
            
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            
            string CategoryCode = this.txtProductTypeCode.Text.Trim();
            string CategoryName = this.txtProductTypeName.Text.Trim();
            if (CategoryCode == "")
            {
                Logger.Error("类别编号不能为空，请输入");
                this.txtProductTypeCode.Focus();
                return;
            }
            if (CategoryName == "")
            {
                Logger.Error("类别名称不能为空，请输入");
                this.txtProductTypeName.Focus();
                return;
            }
            string Comd = "cmd.UpdateProductCategory";

            if (State == "add")
            {
                Comd = "cmd.InsertProductCategory";
                if (bll.GetRowCount("CMD_ProductCategory", "CategoryCode='" + CategoryCode + "'") > 0)
                {
                    Logger.Error(CategoryCode + " 类别编号重复，请输入新编号");
                    return;
                }
            }
            DataParameter[] paras = new DataParameter[] { new DataParameter("@CategoryCode",this.txtProductTypeCode.Text.Trim()),
                                                          new DataParameter("@CategoryName",this.txtProductTypeName.Text.Trim()),
                                                          new DataParameter("@ErpCode",this.txtErpCode.Text.Trim()),
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

        private void frmProductClsEdit_Shown(object sender, EventArgs e)
        {
            if (State == "add")
            {
                txtProductTypeCode.Text = bll.GetNewID("CMD_ProductCategory", "CategoryCode", "1=1");
                this.txtCreator.Text = App.Program.CurrentUser;
                this.txtCreateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
                this.txtUpdater.Text = Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");

            }
            else
            {
                this.txtProductTypeCode.ReadOnly = true;
                this.txtProductTypeCode.Text = drEdit["CategoryCode"].ToString();
                this.txtProductTypeName.Text = drEdit["CategoryName"].ToString();
                this.txtErpCode.Text = drEdit["ERPCode"].ToString();
                this.txtMemo.Text = drEdit["Memo"].ToString();
                this.txtCreator.Text = drEdit["Creator"].ToString();
                this.txtCreateDate.Text = drEdit["CreateDate"].ToString();
                this.txtUpdater.Text = App.Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
            }
            txtProductTypeName.Focus();
        }
    }
}
