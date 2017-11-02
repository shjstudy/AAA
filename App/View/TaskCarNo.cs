using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View
{
    public partial class frmTaskCarNo : Form
    {
        public string carNo = "";
        private string CraneNo = "";
        BLL.BLLBase bll = new BLL.BLLBase();

        public frmTaskCarNo()
        {
            InitializeComponent();
        }
        public frmTaskCarNo(string CraneNo)
        {
            InitializeComponent();
            this.CraneNo = CraneNo;
        }
        private void btnOK_Click(object sender, EventArgs e)
        {
            carNo = this.cmbCarNo.Text;

            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        }

        private void frmTaskCarNo_Load(object sender, EventArgs e)
        {
            this.txtCraneNo.Text = CraneNo;
            DataTable dt = bll.FillDataTable("CMD.SelectCar", new DataParameter[] { new DataParameter("{0}", string.Format("CraneNo='{0}'", CraneNo)) });
            this.cmbCarNo.DataSource = dt.DefaultView;
            this.cmbCarNo.ValueMember = "CarNo";
            this.cmbCarNo.DisplayMember = "CarNo";
        }
    }
}
