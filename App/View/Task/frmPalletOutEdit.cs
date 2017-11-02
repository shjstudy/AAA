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

namespace App.View.Task
{
    public partial class frmPalletOutEdit: Form
    {
       
        public string strValue;
        private DataTable dtTask;
       
        private DataTable dtDetail;
        public DataRow drEdit;

        private string State;
        BLL.BLLBase bll = new BLL.BLLBase();
        
        public frmPalletOutEdit()
        {
            InitializeComponent();
        }
        public frmPalletOutEdit(string state)
        {
            InitializeComponent();
            State = state;
        }

        private void frmInventorEdit_Shown(object sender, EventArgs e)
        {
            if (State == "add")
            {
                this.dtpBillDate.ValueChanged += new EventHandler(dtpBillDate_ValueChanged);
                this.txtBillID.Text = bll.GetAutoCodeByTableName("PO", "WMS_BillMaster", this.dtpBillDate.Value, "1=1");

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
                this.txtMemo.Text = drEdit["Memo"].ToString();
                this.txtCreator.Text = drEdit["Creator"].ToString();
                this.txtCreateDate.Text = drEdit["CreateDate"].ToString();
                this.txtUpdater.Text = App.Program.CurrentUser;
                this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");
            }
            BindData(this.txtBillID.Text);


        }
        private void BindData(string filter)
        {
            dtDetail = bll.FillDataTable("WMS.SelectBillDetail", new DataParameter[] { new DataParameter("{0}", string.Format("BillID='{0}'", filter)) });
            bsMain.DataSource = dtDetail;
        }

        private void btnAddDetail_Click(object sender, EventArgs e)
        {
            Common.frmSelect frm = new Common.frmSelect(true, "CMD_Cell", "IsLock=0 and ProductCode=''");
            if (frm.ShowDialog() == DialogResult.OK)
            {
                for (int i = 0; i < frm.dtSelect.Rows.Count; i++)
                {
                    DataRow dr=frm.dtSelect.Rows[i];
                    DataRow drNew = dtDetail.NewRow();
                    drNew.BeginEdit();
                   
                    drNew["RowID"] = dtDetail.Rows.Count + 1;
                    drNew["BillID"] = this.txtBillID.Text.Trim();
                    drNew["BarCode"] = dr["PalletBarCode"].ToString();
                    drNew["ProductCode"] = dr["ProductCode"];
                    drNew["ProductName"] = dr["ProductName"];
                    drNew["CellCode"] = dr["CellCode"];
                    drNew["CellName"] = dr["CellName"];
                    drNew["Quantity"] = dr["Qty"];
                    
                    drNew.EndEdit();
                    dtDetail.Rows.Add(drNew);
                }


            }
        }

        private void btndelDetail_Click(object sender, EventArgs e)
        {
            for (int i = this.dgView.Rows.Count - 1; i >= 0; i--)
            {


                DataGridViewCheckBoxCell CheckCell = (DataGridViewCheckBoxCell)this.dgView.Rows[i].Cells[0];
                if ((bool)CheckCell.EditedFormattedValue)
                {

                    DataRow[] drs = dtDetail.Select(string.Format("RowID ={0}", this.dgView.Rows[i].Cells["colRowID"].Value.ToString()));
                    for (int j = 0; j < drs.Length; j++)
                        dtDetail.Rows.Remove(drs[j]);
                }
            }
            DataRow[] drsExist = dtDetail.Select("", "RowID");
            for (int i = 0; i < drsExist.Length; i++)
            {
                drsExist[i]["RowID"] = i + 1;
            }


        }
      

        private void btnSave_Click(object sender, EventArgs e)
        {

            string BillID = this.txtBillID.Text.Trim();
            
            if (BillID == "")
            {
                Logger.Error("出库单号不能为空，请输入");
                this.txtBillID.Focus();
                return;
            }

            if (dtDetail.Rows.Count == 0)
            {
                Logger.Error("明细不能为空.");
                return;
            }


            string[] Commands = new string[3];
            Commands[0] = "WMS.UpdateOutPallet";
            if (State == "add")
            {
                Commands[0] = "WMS.InsertPalletOut";
                if (bll.GetRowCount("WMS_BILLMaster", "BillID='" + BillID + "'") > 0)
                {
                    Logger.Error(BillID + " 空箱出库单号重复，请输入新编号");
                    return;
                }
                for (int i = 0; i < dtDetail.Rows.Count; i++)
                {
                    dtDetail.Rows[i]["BillID"] = this.txtBillID.Text.Trim();
                }
            }


            DataParameter[] para = new DataParameter[] { new DataParameter("@BillID", this.txtBillID.Text.Trim()),
                                                         new DataParameter("@BillDate", this.dtpBillDate.Value),
                                                         new DataParameter("@Memo", this.txtMemo.Text.Trim()),
                                                         new DataParameter("@Creator",Program.CurrentUser),
                                                         new DataParameter("@Updater", Program.CurrentUser)};
            Commands[1] = "WMS.DeleteBillDetail";
            Commands[2] = "WMS.InsertOutPalletDetail";
            try
            {
                bll.ExecTran(Commands, para, "BillID", new DataTable[] { dtDetail });
            }
            catch (Exception ex)
            {
                Logger.Error("空箱出库单保存失败，错误原因:" + ex.Message);
                return;
            }
            this.DialogResult = DialogResult.OK;

        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
        
        private void dtpBillDate_ValueChanged(object sender, EventArgs e)
        {
            this.txtBillID.Text = bll.GetAutoCodeByTableName("PO", "WMS_BillMaster", this.dtpBillDate.Value, "1=1");
        }

       

      
         
    }
}
