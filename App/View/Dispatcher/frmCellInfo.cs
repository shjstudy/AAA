using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View.Dispatcher
{
    public partial class frmCellInfo : Form
    {
        private string PalletBarcode;
        private string CellCode;
        public frmCellInfo()
        {
            InitializeComponent();
        }
        public frmCellInfo(string PalletBarcode)
        {
            InitializeComponent();
            this.PalletBarcode = PalletBarcode;
        }
        public frmCellInfo(string PalletBarcode, string CellCode)
        {
            InitializeComponent();
            this.PalletBarcode = PalletBarcode;
            this.CellCode = CellCode;
        }
        private void frmCellInfo_Load(object sender, EventArgs e)
        {
            BLL.BLLBase bll = new BLL.BLLBase();
            DataTable dt = bll.FillDataTable("WMS.SelectWmsPallet", new DataParameter[] { new DataParameter("{0}", string.Format("PalletCode='{0}'", PalletBarcode)) });
            bsMain.DataSource = dt;
            if (dt.Rows.Count > 0)
            {
                this.txtCellCode.Text = dt.Rows[0]["CellCode"].ToString();
                this.txtPalletBarcode.Text = dt.Rows[0]["PalletCode"].ToString();
            }
            else
            {
                this.txtCellCode.Text = CellCode;
                this.txtPalletBarcode.Text = PalletBarcode;
            }
        }
    }
}
