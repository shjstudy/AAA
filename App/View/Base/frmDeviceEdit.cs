using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View.Base
{
    public partial class frmDeviceEdit : Form
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        string DeviceNo = "";
        DataRow dr;

        public frmDeviceEdit()
        {
            InitializeComponent();
        }
        public frmDeviceEdit(DataRow dr)
        {
            InitializeComponent();
            this.dr = dr;
            this.DeviceNo = dr["DeviceNo"].ToString();
        }
        private void btnOK_Click(object sender, EventArgs e)
        {
            if (this.txtDeviceNo.Text.Trim().Length <= 0)
            {
                MessageBox.Show("设备编号不能为空,请输入！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.txtDeviceNo.Focus();
                return;
            }
            if (this.txtDeviceName.Text.Trim().Length <= 0)
            {
                MessageBox.Show("设备名称不能为空,请输入！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.txtDeviceName.Focus();
                return;
            }            

            if (DeviceNo.Length <= 0) //新增
            {
                int Count = bll.GetRowCount("CMD_Device", string.Format("DeviceNo='{0}'", this.txtDeviceNo.Text.Trim()));
                if (Count > 0)
                {
                    MessageBox.Show("该设备编号已经存在,请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    this.txtDeviceNo.Focus();
                    return;
                }

                bll.ExecNonQuery("Cmd.InsertDevice", new DataParameter[] { 
                            new DataParameter("@Flag", dr["Flag"]),
                            new DataParameter("@DeviceType", dr["DeviceType"]),
                            new DataParameter("@DeviceNo", this.txtDeviceNo.Text.Trim()),
                            new DataParameter("@DeviceNo2", this.txtDeviceNo2.Text.Trim()),
                            new DataParameter("@DeviceName", this.txtDeviceName.Text),                           
                            new DataParameter("@State", this.cmbState.SelectedIndex),
                            new DataParameter("@AlarmCode", this.txtAlarmCode.Text),
                            new DataParameter("@ServiceName", this.txtServiceName.Text),
                            new DataParameter("@Memo", this.txtMemo.Text.Trim()),
                            new DataParameter("@Creator", "admin"),
                            new DataParameter("@Updater", "admin")
                          
                });
            }
            else //修改
            {
                bll.ExecNonQuery("Cmd.UpdateDevice", new DataParameter[] {  
                            new DataParameter("@Flag", this.txtFlag.Text),
                            new DataParameter("@DeviceType", this.txtDeviceType.Text),
                            new DataParameter("@DeviceNo", this.txtDeviceNo.Text.Trim()),
                            new DataParameter("@DeviceNo2", this.txtDeviceNo2.Text.Trim()),
                            new DataParameter("@DeviceName", this.txtDeviceName.Text),                           
                            new DataParameter("@State", this.cmbState.SelectedIndex),
                            new DataParameter("@AlarmCode", this.txtAlarmCode.Text),
                            new DataParameter("@ServiceName", this.txtServiceName.Text),
                            new DataParameter("@Memo", this.txtMemo.Text.Trim()),
                            new DataParameter("@Updater", "admin")
                 });
            }
            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }

        private void frmDeviceEdit_Load(object sender, EventArgs e)
        {
            if (this.DeviceNo.Length > 0)
            {
                this.txtDeviceType.Text = dr["DeviceType"].ToString();
                this.txtFlag.Text = dr["Flag"].ToString();
                this.txtDeviceNo.Text = dr["DeviceNo"].ToString();
                this.txtDeviceNo2.Text = dr["DeviceNo2"].ToString();
                this.txtDeviceName.Text = dr["DeviceName"].ToString();
                this.cmbState.SelectedIndex = int.Parse(dr["State"].ToString());
                this.txtAlarmCode.Text = dr["AlarmCode"].ToString();                
                this.txtServiceName.Text = dr["ServiceName"].ToString();
                this.txtMemo.Text = dr["Memo"].ToString();                
                this.txtDeviceNo.ReadOnly = true;
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        }
    }
}
