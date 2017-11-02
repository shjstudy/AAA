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
using Core = Invengo.NetAPI.Core;

namespace App.View.Task
{
    public partial class frmInStockTask :BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        private string RFIDCode = "";
        public frmInStockTask()
        {
            InitializeComponent();
        }

        private void frmInStockTask_Shown(object sender, EventArgs e)
        {
            RFIDRead.OnRFIDDisplay += new RFIDDisplayHandle(Rfid_OnRFIDDisplay);
            RFIDRead.Scan();

        }

        void Rfid_OnRFIDDisplay(Invengo.NetAPI.Protocol.IRP1.RXD_TagData msg)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new RFIDDisplayHandle(Rfid_OnRFIDDisplay), msg);
            }
            else
            {
                this.txtBarcode.Text = Core.Util.ConvertByteArrayToHexString(msg.ReceivedMessage.EPC);
                if (RFIDCode != this.txtBarcode.Text)
                {
                    RFIDCode = this.txtBarcode.Text;
                    this.txtPalletCode.Text = bll.GetFieldValue("cmd_Pallet", "PalletID", string.Format("RFIDCode='{0}'", this.txtBarcode.Text));
                    if (this.txtPalletCode.Text == "")
                    {
                        Logger.Info("RFID编号未绑定箱号，请先绑定后再入库！");
                    }
                }
            }
        }

        private void btnRequest_Click(object sender, EventArgs e)
        {
            if (this.txtPalletCode.Text == "")
            {
                Logger.Info("料箱号为空，请读取RFID资料！");
                return;
            }

            if (!chk1.Checked)
            {
                if (this.txtProductName.Text == "")
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
            //判断是否已经产生入库任务
            int count = bll.GetRowCount("WCS_Task", string.Format("State not in ('7','9') and TaskType='11' and PalletCode='{0}'", this.txtPalletCode.Text));
            if (count > 0)
            {
                Logger.Info("该料箱已经产生入库任务，不能再产生入库任务！");
                return;
            }
            try
            {
                DataParameter[] parameter = new DataParameter[] { new DataParameter("{0}", string.Format("WCS_Task.State=8 and WCS_Task.TaskType='14' and WCS_Task.PalletCode='{0}'", this.txtPalletCode.Text)) };
                DataTable dt = bll.FillDataTable("WCS.SelectTask", parameter);
                if (dt.Rows.Count == 0)
                {
                    DataParameter[] paras = new DataParameter[] { new DataParameter("@PalletCode",this.txtPalletCode.Text),
                                                          new DataParameter("@ProductCode",this.txtProductCode.Text),
                                                          new DataParameter("@Qty",this.txtQty.Text),
                                                          new DataParameter("@UserName",Program.CurrentUser)
                                                        };
                    dt = bll.FillDataTable("WMS.Sp_CreateInStockTask", paras);
                }

                if (dt.Rows.Count > 0)
                {
                    Context.ProcessDispatcher.WriteToProcess("InStockToStationProcess", "TaskNo", dt.Rows[0]["TaskNo"].ToString());
                }
                this.txtBarcode.Text = "";
                this.txtPalletCode.Text = "";
                this.txtProductCode.Text = "";
                this.txtProductName.Text = "";
                this.txtQty.Text = "0";


            }
            catch (Exception ex)
            {
                Logger.Error("扫码入库中入库请求出现异常,错误:" + ex.Message);
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.No;
        }

        private void chk1_CheckedChanged(object sender, EventArgs e)
        {
            if (this.chk1.Checked)
            {
                this.txtProductCode.Text = "";
                this.txtQty.Text = "0";
                this.txtQty.ReadOnly = true;
                this.txtProductCode.ReadOnly = true;
                this.txtProductName.Text = "";
            }
            else
            {
              
                this.txtQty.ReadOnly = false;
                this.txtProductCode.ReadOnly = false;
            }
        }

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
        private void txtQty_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar != 8 && !Char.IsDigit(e.KeyChar))
            {
                e.Handled = true;
            }
        }

        private void frmInStockTask_FormClosing(object sender, FormClosingEventArgs e)
        {
            RFIDRead.StopScan();
            RFIDRead.OnRFIDDisplay -= new RFIDDisplayHandle(Rfid_OnRFIDDisplay);
        }

    }

}
