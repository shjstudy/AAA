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

namespace App.View.Dispatcher
{
    public partial class frmOutView : Form
    {
        private System.Timers.Timer tmWorkTimer = new System.Timers.Timer();
        private Context context;
        public string strValue;
        private DataTable dtTask;
        BLL.BLLBase bll = new BLL.BLLBase();
        private DataTable dtPallet;
        private bool blnOk = false;
        private string PalletCode;

        public frmOutView()
        {
            InitializeComponent();
        }
        public frmOutView(int flag, DataTable dtInfo)
        {
            InitializeComponent();
            dtTask = dtInfo;
        }
        public frmOutView(int flag, DataTable dtInfo,Context context)
        {
            InitializeComponent();
            dtTask = dtInfo;
            this.context = context;
        }
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

        private void frmOutView_Load(object sender, EventArgs e)
        {
            DataRow dr = dtTask.Rows[0];

            this.txtBillID.Text = dr["BillID"].ToString();
            PalletCode = dr["PalletCode"].ToString();
            this.txtPalletCode.Text = dr["PalletCode"].ToString();
            this.txtTaskNo.Text = dr["TaskNo"].ToString();
            this.txtCellCode.Text = dr["CellCode"].ToString();


            //this.txtBillID.Text = "OS20161211001";
            //PalletCode = "A003";
            //this.txtPalletCode.Text = "A003";
            //this.txtTaskNo.Text = "A003";
            //this.txtCellCode.Text = "003023003";

            dtPallet = bll.FillDataTable("WCS.SelectTaskProductDetail", new DataParameter[] { new DataParameter("@TaskNo", this.txtTaskNo.Text) });
            this.bsMain.DataSource = dtPallet;

            tmWorkTimer.Interval = 1000;
            tmWorkTimer.Elapsed += new System.Timers.ElapsedEventHandler(tmWorker);
            tmWorkTimer.Start();
        }

        private void tmWorker(object sender, System.Timers.ElapsedEventArgs e)
        {
            try
            {
                tmWorkTimer.Stop();
                object obj = ObjectUtil.GetObject(context.ProcessDispatcher.WriteToService("TranLine", "StockRequest"));
                if (obj == null)
                    return;
                string TaskFinish = obj.ToString();
                Logger.Info(TaskFinish);
                if (TaskFinish.Equals("True") || TaskFinish.Equals("1"))
                    this.DialogResult = DialogResult.OK;
            }
            catch (Exception ex)
            {
                Logger.Error(ex.Message);
            }
            finally
            {
                tmWorkTimer.Start();
            }
        }     

        private void btnGetBack_Click(object sender, EventArgs e)
        {
            strValue = "1";
            tmWorkTimer.Stop();
            this.DialogResult = DialogResult.OK;
            
        }

        private void frmScan_FormClosing(object sender, FormClosingEventArgs e)
        {
            
        }

    }
}
