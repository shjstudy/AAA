using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace App.View.Task
{
    public partial class frmTaskDialog : Form
    {
        private string TaskType;
        public string filter = "1=1";
        public frmTaskDialog()
        {
            InitializeComponent();
        }
        public frmTaskDialog(string TaskType)
        {
            InitializeComponent();
            this.TaskType = TaskType;
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            filter = string.Format("convert(varchar(10),WCS_Task.TaskDate,120) between '{0}' and '{1}'", this.dtpTaskDate1.Value.ToString("yyyy-MM-dd"), this.dtpTaskDate2.Value.ToString("yyyy-MM-dd"));
            if (this.txtPalletBarcode1.Text.Trim().Length > 0 && this.txtPalletBarcode2.Text.Trim().Length <= 0)
                filter += string.Format(" and WCS_Task.PalletBarcode like '%{0}%'", this.txtPalletBarcode1.Text.Trim());
            else if (this.txtPalletBarcode1.Text.Trim().Length <= 0 && this.txtPalletBarcode2.Text.Trim().Length > 0)
                filter += string.Format(" and WCS_Task.PalletBarcode like '%{0}%'", this.txtPalletBarcode2.Text.Trim());
            else if (this.txtPalletBarcode1.Text.Trim().Length > 0 && this.txtPalletBarcode2.Text.Trim().Length > 0)
                filter += string.Format(" and WCS_Task.PalletBarcode between '{0}' and '{1}'", this.txtPalletBarcode1.Text.Trim(), this.txtPalletBarcode2.Text.Trim());
            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }
    }
}
