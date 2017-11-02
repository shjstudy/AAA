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
using Core = Invengo.NetAPI.Core;

namespace App.View.CMD
{
    
    public partial class frmRFIDEdit : BaseForm
    {
        public DataRow drEdit;
        private string State;
        BLL.BLLBase bll = new BLL.BLLBase();
        public frmRFIDEdit()
        {
            InitializeComponent();
        }
        public frmRFIDEdit(string state)
        {
            InitializeComponent();
            State = state;
        }

         

        private void btnSave_Click(object sender, EventArgs e)
        {

            string PalletID = this.txtPalletID.Text.Trim();
            string RFIDCode = this.txtRFIDCode.Text.Trim();

            if (RFIDCode == "")
            {
                Logger.Error("RFID编码不能为空，请读取RFID编号！");
                this.txtRFIDCode.Focus();
                return;
            }
            string Comd = "cmd.UpdatePallet";


            string OtherID = bll.GetFieldValue("CMD_Pallet", "PalletID", "RFIDCode='" + RFIDCode + "' and PalletID!=" + this.txtPalletID.Text);
            if (OtherID.Trim() != "")
            {
                Logger.Error("该RFID编号：" + RFIDCode + " 已经被其他料箱号( " + OtherID + " )使用，请更换新的RFID。");
                return;
            }
            
            DataParameter[] paras = new DataParameter[] { new DataParameter("@PalletID",this.txtPalletID.Text.Trim()),
                                                          new DataParameter("@RFIDCode",this.txtRFIDCode.Text.Trim()),
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
            this.txtPalletID.Text = drEdit["PalletID"].ToString();
            this.txtRFIDCode.Text = drEdit["RFIDCode"].ToString();

            this.txtCreator.Text = drEdit["Creator"].ToString();
            this.txtCreateDate.Text = drEdit["CreateDate"].ToString();
            this.txtUpdater.Text = App.Program.CurrentUser;
            this.txtUpdateDate.Text = DateTime.Now.ToString("yyyy/MM/dd");

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
                this.txtRFIDCode.Text = Core.Util.ConvertByteArrayToHexString(msg.ReceivedMessage.EPC);
            }
        }

        private void frmRFIDEdit_FormClosing(object sender, FormClosingEventArgs e)
        {
            RFIDRead.StopScan();
            RFIDRead.OnRFIDDisplay -= new RFIDDisplayHandle(Rfid_OnRFIDDisplay);
        }
    }
}
