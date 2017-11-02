using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Util;
using MCP;

namespace App.View.Dispatcher
{
    public partial class frmCellOpDialog : Form
    {
        public string strValue;
        BLL.BLLBase bll = new BLL.BLLBase();
        private DataRow drCell;
      

        public frmCellOpDialog()
        {
            InitializeComponent();
        }
        public frmCellOpDialog(DataRow dr)
        {
            InitializeComponent();
            drCell = dr;
           
        }

        private void frmCellOpDialog_Shown(object sender, EventArgs e)
        {
            this.txtCellCode.Text = drCell["CellCode"].ToString();
            this.txtCellName.Text = drCell["CellName"].ToString();
            this.txtCellRow.Text = drCell["CellRow"].ToString();
            this.txtCellColumn.Text = drCell["CellColumn"].ToString();
            this.chkIsLock.Checked = drCell["IsLock"].ToString() == "0" ? false : true;
            this.chkIsActive.Checked = drCell["IsActive"].ToString() == "0" ? false : true;
            this.chkErrorFlag.Checked = drCell["ErrorFlag"].ToString() == "0" ? false : true;
            this.txtPalletCode.Text = drCell["PalletBarCode"].ToString();
            this.txtProductCode.Text = drCell["ProductCode"].ToString();
            this.txtProductName.Text = drCell["ProductName"].ToString();
            this.txtQty.Text = drCell["Qty"].ToString();
            if (drCell["InDate"].ToString() == "")
            {
                this.dtpInDate.Checked = false;
            }
            else
            {
                this.dtpInDate.Checked = true;
                this.dtpInDate.Value = DateTime.Parse(drCell["InDate"].ToString());
            }
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("您确定要对货位" + this.txtCellCode.Text + "修改吗?", "提示", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == System.Windows.Forms.DialogResult.Yes)
            {
               
                //判断货位名称是否重复
                if (this.txtCellName.Text.Trim() == "")
                {
                    this.txtCellName.Focus();
                    Logger.Error("货位名称不能为空,请输入!");
                    return;
                }
                else
                {
                    DataTable dtRow = bll.FillDataTable("CMD.SelectCell", new DataParameter[] { new DataParameter("{0}", string.Format("CellName='{0}' and CellCode!='{1}'", this.txtCellName.Text.Trim(), this.txtCellCode.Text)) });
                    if (dtRow.Rows.Count > 0)
                    {
                        this.txtCellName.Focus();
                        Logger.Error("货位名称 " + this.txtCellName.Text.Trim() + " 重复，已被货位 " + dtRow.Rows[0]["CellCode"].ToString() + " 使用,请重新输入!");
                        return;
                    }
                }

                if (!this.dtpInDate.Checked)
                {
                    if (this.txtProductName.Text != "")
                    {
                        Logger.Info("请确认货位是否有货，如有物料请将入库日期打勾，若无货请将产品资料清除！");
                        return;
                    }
                }
                else
                {
                    if (this.txtProductName.Text == "")
                    {
                        if (MessageBox.Show("请确认货位上的周转箱是否为空箱？", "询问", MessageBoxButtons.YesNo) == DialogResult.No)
                        {
                            Logger.Info("请输入产品信息！");
                            this.txtProductCode.Focus();
                            return;
                        }
                    }
                    else
                    {
                        if (this.txtQty.Text == "" ||this.txtQty.Text == "0")
                        {
                            Logger.Info("请输入产品数量！");
                            this.txtQty.Focus();
                            return;
                        }
                    }
                    //获取固定的托盘条码
                    if (this.txtPalletCode.Text == "")
                    {
                        this.txtPalletCode.Text = this.txtCellName.Text;
                    }
                }
                //更新货位信息
                string IsLock = this.chkIsLock.Checked ? "1" : "0";
                string IsActive = this.chkIsActive.Checked ? "0" : "1";
                string ErrorFlag = this.chkErrorFlag.Checked ? "1" : "0";

                string sql = string.Format("IsLock='{0}'", IsLock);
                sql += string.Format(",IsActive='{0}'", IsActive);
                sql += string.Format(",ErrorFlag='{0}'", ErrorFlag);

                sql += string.Format(",CellName='{0}'", this.txtCellName.Text.Trim());
                //if (this.txtProductCode.Text.Trim().Length > 0)
                sql += string.Format(",PalletBarcode='{0}'", this.txtPalletCode.Text.Trim());
                sql += string.Format(",ProductCode='{0}'", this.txtProductCode.Text.Trim());
                sql += string.Format(",Qty='{0}'", this.txtQty.Text.Trim());

                if (this.dtpInDate.Checked)
                    sql += string.Format(",InDate='{0}'", this.dtpInDate.Value);

                DataParameter[] param = new DataParameter[] { new DataParameter("{0}", sql), new DataParameter("{1}", string.Format("CellCode='{0}'", this.txtCellCode.Text)) };
                bll.ExecNonQuery("WCS.UpdateCellByFilter", param);
            }
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
            }
        }

        private void txtProductCode_TextChanged(object sender, EventArgs e)
        {
            string Productname = bll.GetFieldValue("CMD_Product", "ProductName", string.Format("ProductCode='{0}'", this.txtProductCode.Text.Trim()));
            if (Productname != "")
            {
                this.txtProductName.Text = Productname;
            }

        }
       
          
    }
}
