using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Core = Invengo.NetAPI.Core;

namespace WindowsFormsApplication1
{
    public partial class Form5 : Form
    {
        public Form5()
        {
            InitializeComponent();
        }

        private void Form5_Shown(object sender, EventArgs e)
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
                this.textBox1.Text = Core.Util.ConvertByteArrayToHexString(msg.ReceivedMessage.EPC);
            }
        }
        private void Form5_FormClosing(object sender, FormClosingEventArgs e)
        {
            RFIDRead.StopScan();
            RFIDRead.OnRFIDDisplay -= new RFIDDisplayHandle(Rfid_OnRFIDDisplay);
        }
    }
}
