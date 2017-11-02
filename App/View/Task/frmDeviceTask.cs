using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View.Task
{
    public partial class frmDeviceTask : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();

        public frmDeviceTask()
        {
            InitializeComponent();
        }

        private void frmCraneTask_Load(object sender, EventArgs e)
        {
            try
            {
                //MessageBox.Show("0");
                this.cmbTaskType.SelectedIndex = 0;
                this.txtTaskNo1.Text = "MJ2RB170605000001";
                //MessageBox.Show("1");
                BindAisleNo();
                //MessageBox.Show("2");
                BindDeviceNo();
                //MessageBox.Show("3");
            }

            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private void BindDeviceNo()
        {
            DataTable dt = bll.FillDataTable("CMD.SelectAisleDevice2", new DataParameter("{0}", string.Format("A.WarehouseCode='{0}' and A.AisleNo='{1}'", Program.WarehouseCode, this.cmbAisleNo.Text)));

            this.cmbDeviceNo.ValueMember = "ServiceName";
            if(Program.WarehouseCode=="S")
                this.cmbDeviceNo.DisplayMember = "DeviceNo2";
            else
                this.cmbDeviceNo.DisplayMember = "DeviceNo";
            this.cmbDeviceNo.DataSource = dt.DefaultView;
        }
        private void BindAisleNo()
        {
            try
            {
                DataTable dtAisle = bll.FillDataTable("CMD.SelectAisleDevice", new DataParameter[] { new DataParameter("{0}", string.Format("S1.WarehouseCode = '{0}'", Program.WarehouseCode)) });

                this.cmbAisleNo.DataSource = dtAisle.DefaultView;
                this.cmbAisleNo.ValueMember = "AisleValue";
                this.cmbAisleNo.DisplayMember = "AisleNo";                
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        
        private void BindShelf()
        {
            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = Program.WarehouseCode + "X";
                dr["dtValue"] = Program.WarehouseCode + "X";

                dt.Rows.Add(dr);
                this.cbFromRow.DataSource = dt;
                this.cbFromRow.DisplayMember = "dtText";
                this.cbFromRow.ValueMember = "dtValue";
                
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectShelf", new DataParameter("{0}", string.Format("WarehouseCode='{0}' and AisleNo='{1}'", Program.WarehouseCode, this.cmbAisleNo.Text)));

                this.cbFromRow.DataSource = dt.DefaultView;
                this.cbFromRow.ValueMember = "shelfvalue";
                this.cbFromRow.DisplayMember = "shelfname";
                
            }

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = Program.WarehouseCode + "X";
                dr["dtValue"] = Program.WarehouseCode + "X";
                dt.Rows.Add(dr);

                if (Program.WarehouseCode != "S")
                {
                    dr = dt.NewRow();
                    dr["dtText"] = Program.WarehouseCode + "Y";
                    dr["dtValue"] = Program.WarehouseCode + "Y";
                    dt.Rows.Add(dr);
                }

                this.cbToRow.DataSource = dt;
                this.cbToRow.DisplayMember = "dtText";
                this.cbToRow.ValueMember = "dtValue";
                

            }
            else if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = bll.FillDataTable("CMD.SelectShelf", new DataParameter("{0}", string.Format("WarehouseCode='{0}' and AisleNo='{1}'", Program.WarehouseCode, this.cmbAisleNo.Text)));

                if (Program.WarehouseCode != "S")
                {
                    DataRow dr = dt.NewRow();
                    dr["shelfname"] = Program.WarehouseCode + "Y";
                    if (this.cmbAisleNo.Text.Trim().Length > 0)
                        dr["shelfvalue"] = int.Parse(this.cmbAisleNo.Text);
                    else
                        dr["shelfvalue"] = 0;
                    dt.Rows.Add(dr);
                }

                this.cbToRow.DataSource = dt.DefaultView;
                this.cbToRow.ValueMember = "shelfvalue";
                this.cbToRow.DisplayMember = "shelfname";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectShelf", new DataParameter("{0}", string.Format("WarehouseCode='{0}' and AisleNo='{1}'", Program.WarehouseCode, this.cmbAisleNo.Text)));

                this.cbToRow.DataSource = dt.DefaultView;
                this.cbToRow.ValueMember = "shelfvalue";
                this.cbToRow.DisplayMember = "shelfname";
            }
        }

        private void btnAction_Click(object sender, EventArgs e)
        {
            string serviceName = this.cmbDeviceNo.SelectedValue.ToString();
            string carNo = this.cmbDeviceNo.Text.Substring(2,2);

            string FromStationNo = "";
            string FromStationAdd = "";
            string ToStationNo = "";
            string ToStationAdd = "";
            int[] cellAddr = new int[12];

            FromStationNo = this.cbFromRow.Text + "-" + (100 + int.Parse(this.cbFromColumn.Text)).ToString().Substring(1, 2) + "-" + (100 + int.Parse(this.cbFromHeight.Text)).ToString().Substring(1, 2);
            FromStationAdd = GetStationAdd(FromStationNo);
            ToStationNo = this.cbToRow.Text + "-" + (100 + int.Parse(this.cbToColumn.Text)).ToString().Substring(1, 2) + "-" + (100 + int.Parse(this.cbToHeight.Text)).ToString().Substring(1, 2);
            ToStationAdd = GetStationAdd(ToStationNo);
            if (Program.WarehouseCode != "S")
            {
                cellAddr[0] = int.Parse(FromStationAdd.Substring(4, 3));
                cellAddr[1] = int.Parse(FromStationAdd.Substring(7, 3));
                cellAddr[2] = int.Parse(FromStationAdd.Substring(1, 3));
                
                cellAddr[3] = int.Parse(ToStationAdd.Substring(4, 3));
                cellAddr[4] = int.Parse(ToStationAdd.Substring(7, 3));
                cellAddr[5] = int.Parse(ToStationAdd.Substring(1, 3));

                if (this.cmbTaskType.SelectedIndex == 3)
                    cellAddr[6] = 3;
                else
                    cellAddr[6] = 1;

                sbyte[] taskNo = new sbyte[20];
                Util.ConvertStringChar.stringToBytes(this.txtTaskNo1.Text, 20).CopyTo(taskNo, 0);
                Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
                Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
                Context.ProcessDispatcher.WriteToService(serviceName, "STB", 1);
            }
            else
            {
                object[] obj = MCP.ObjectUtil.GetObjects(Context.ProcessDispatcher.WriteToService(serviceName, "CarStatus" + carNo));
                int Layer = int.Parse(obj[3].ToString());

                cellAddr[3] = int.Parse(FromStationAdd.Substring(1, 3));
                cellAddr[4] = int.Parse(FromStationAdd.Substring(4, 3));
                cellAddr[5] = int.Parse(FromStationAdd.Substring(7, 3));

                cellAddr[6] = int.Parse(ToStationAdd.Substring(1, 3));
                cellAddr[7] = int.Parse(ToStationAdd.Substring(4, 3));
                cellAddr[8] = int.Parse(ToStationAdd.Substring(7, 3));

                if (this.cmbTaskType.SelectedIndex == 0)
                {
                    cellAddr[9] = 10;
                    cellAddr[4] = 0;
                    cellAddr[5] = int.Parse(obj[3].ToString());
                }
                else if (this.cmbTaskType.SelectedIndex == 1)
                {
                    cellAddr[9] = 11;
                    cellAddr[7] = 0;
                    cellAddr[8] = cellAddr[5];
                }
                else if (this.cmbTaskType.SelectedIndex == 2)
                {
                    cellAddr[9] = 9;
                }
                else if (this.cmbTaskType.SelectedIndex == 3)
                {
                    cellAddr[3] = 0;
                    cellAddr[4] = 0;
                    cellAddr[5] = 0;
                    cellAddr[9] = 1;
                }

                cellAddr[10] = int.Parse(carNo);
                sbyte[] taskNo = new sbyte[30];
                Util.ConvertStringChar.stringToBytes(this.txtTaskNo1.Text, 30).CopyTo(taskNo, 0);
                Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
                Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
                Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);
            }

            MCP.Logger.Info("测试任务已下发给" + this.cmbDeviceNo.Text + "设备;起始地址:" + FromStationAdd + ",目标地址:" + ToStationAdd);
        }
        private string GetStationAdd(string StationNo)
        {
            string StationAdd = "";
            //获取站台地址
            
            DataTable dt = bll.FillDataTable("CMD.SelectStation", new DataParameter("{0}", string.Format("StationNo='{0}'", StationNo)));
            if (dt.Rows.Count > 0)
                StationAdd = dt.Rows[0]["StationAddress"].ToString();
            return StationAdd;
        }
        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            string serviceName = this.cmbDeviceNo.SelectedValue.ToString();
            string carNo = this.cmbDeviceNo.Text.Substring(2, 2);
            object obj = MCP.ObjectUtil.GetObject(Context.ProcessDispatcher.WriteToService(serviceName, "AlarmCode")).ToString();
            int[] cellAddr = new int[12];

            if (obj.ToString() != "0")
            {
                if (Program.WarehouseCode != "S")
                {

                    cellAddr[6] = 5;
                    //sbyte[] taskNo = new sbyte[20];
                    //Util.ConvertStringChar.stringToBytes("", 20).CopyTo(taskNo, 0);
                    //Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
                    Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
                    Context.ProcessDispatcher.WriteToService(serviceName, "STB", 1);
                }
                else
                {
                    cellAddr[1] = 1;
                    cellAddr[10] = int.Parse(carNo);

                    Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
                    Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);
                }

                MCP.Logger.Info("取消任务已下发给" + this.cmbDeviceNo.Text + "取消任务指令");
            }
        }

        private void cmbTaskType_SelectedIndexChanged(object sender, EventArgs e)
        {
            BindShelf();
        }

        private void cbFromRow_SelectedValueChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow.SelectedValue.ToString() == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CMD_Shelf.ShelfName='{0}'",this.cbFromRow.Text))
            };


            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = this.cmbAisleNo.Text;
                dr["dtValue"] = this.cmbAisleNo.SelectedValue;

                dt.Rows.Add(dr);
                this.cbFromColumn.DataSource = dt;
                this.cbFromColumn.DisplayMember = "dtText";
                this.cbFromColumn.ValueMember = "dtValue";

            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);
                this.cbFromColumn.DataSource = dt.DefaultView;
                this.cbFromColumn.ValueMember = "CellColumn";
                this.cbFromColumn.DisplayMember = "CellColumn";
            }
        }

        private void cbToRow_SelectedValueChanged(object sender, EventArgs e)
        {
            if (this.cbToRow.SelectedValue==null)
                return;
            if (this.cbToRow.SelectedValue.ToString() == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CMD_Shelf.ShelfName='{0}'",this.cbToRow.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = this.cmbAisleNo.Text;
                dr["dtValue"] = this.cmbAisleNo.SelectedValue;

                dt.Rows.Add(dr);
                this.cbToColumn.DataSource = dt;
                this.cbToColumn.DisplayMember = "dtText";
                this.cbToColumn.ValueMember = "dtValue";

            }
            else if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);
                if (dt.Rows.Count <= 0)
                {
                    dt = new DataTable("dt");
                    dt.Columns.Add("dtText");
                    dt.Columns.Add("dtValue");
                    DataRow dr = dt.NewRow();

                    dr["dtText"] = this.cmbAisleNo.Text;
                    dr["dtValue"] = this.cmbAisleNo.Text;
                    dt.Rows.Add(dr);
                    this.cbToColumn.DataSource = dt;
                    this.cbToColumn.DisplayMember = "dtText";
                    this.cbToColumn.ValueMember = "dtValue";
                }
                else
                {
                    this.cbToColumn.DataSource = dt.DefaultView;
                    this.cbToColumn.ValueMember = "CellColumn";
                    this.cbToColumn.DisplayMember = "CellColumn";
                }
                
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

                this.cbToColumn.DataSource = dt.DefaultView;
                this.cbToColumn.ValueMember = "CellColumn";
                this.cbToColumn.DisplayMember = "CellColumn";
            }
        }

        private void cbFromColumn_SelectedValueChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow.SelectedValue.ToString() == "System.Data.DataRowView")
                return;
            if (this.cbFromColumn.SelectedValue == null)
                return;
            if (this.cbFromColumn.SelectedValue.ToString() == "")
                return;
            if (this.cbFromColumn.SelectedValue.ToString() == "System.Data.DataRowView")
                return;
            
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CMD_Shelf.ShelfName='{0}' and CellColumn={1}",this.cbFromRow.Text,this.cbFromColumn.SelectedValue.ToString()))
            };

            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                dr["dtText"] = "01";
                dr["dtValue"] = "1";
                dt.Rows.Add(dr);

                this.cbFromHeight.DataSource = dt;
                this.cbFromHeight.DisplayMember = "dtText";
                this.cbFromHeight.ValueMember = "dtValue";

            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCell", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";

                this.cbFromHeight.DataSource = dv;
                this.cbFromHeight.ValueMember = "CellRow";
                this.cbFromHeight.DisplayMember = "CellRow";

            }
        }

        private void cbToColumn_SelectedValueChanged(object sender, EventArgs e)
        {
            if (this.cbToRow.SelectedValue == null)
                return;
            if (this.cbToColumn.SelectedValue == null)
                return;
            if (this.cbToColumn.SelectedValue.ToString() == "")
                return;
            if (this.cbToRow.SelectedValue.ToString() == "System.Data.DataRowView")
                return;
            if (this.cbToColumn.SelectedValue.ToString() == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CMD_Shelf.ShelfName='{0}' and CellColumn={1}",this.cbToRow.Text,this.cbToColumn.SelectedValue.ToString()))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "02";
                dr["dtValue"] = "02";

                dt.Rows.Add(dr);

                this.cbToHeight.DataSource = dt;
                this.cbToHeight.DisplayMember = "dtText";
                this.cbToHeight.ValueMember = "dtValue";
            }
            else if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCell", param);               

                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";

                if (dt.Rows.Count <= 0)
                {
                    dt = new DataTable("dt");
                    dt.Columns.Add("dtText");
                    dt.Columns.Add("dtValue");
                    DataRow dr = dt.NewRow();

                    dr["dtText"] = "02";
                    dr["dtValue"] = "02";
                    dt.Rows.Add(dr);
                    this.cbToHeight.DataSource = dt;
                    this.cbToHeight.DisplayMember = "dtText";
                    this.cbToHeight.ValueMember = "dtValue";
                }
                else
                {
                    this.cbToHeight.DataSource = dv;
                    this.cbToHeight.ValueMember = "CellRow";
                    this.cbToHeight.DisplayMember = "CellRow";
                }

                
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCell", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";

                this.cbToHeight.DataSource = dv;
                this.cbToHeight.ValueMember = "CellRow";
                this.cbToHeight.DisplayMember = "CellRow";

            }
        }

        private void cmbAisleNo_SelectedValueChanged(object sender, EventArgs e)
        {
            if (this.cmbAisleNo.SelectedValue == null)
                return;
            if (this.cmbAisleNo.SelectedValue.ToString() == "System.Data.DataRowView")
                return;
            BindDeviceNo();
            BindShelf();
        }
    }
}
