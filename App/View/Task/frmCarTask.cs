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
    public partial class frmCarTask : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CarNo = "01";

        public frmCarTask()
        {
            InitializeComponent();
        }

        private void frmCarTask_Load(object sender, EventArgs e)
        {
            DataTable dt = bll.FillDataTable("CMD.SelectElevatorCar", new DataParameter[] { new DataParameter("{0}", "CarState='1'") });
            this.cmbCarNo.DataSource = dt.DefaultView;
            this.cmbCarNo.ValueMember = "CarNo";
            this.cmbCarNo.DisplayMember = "CarNo";

            //this.cmbTaskType.SelectedIndex = 0;
            this.txtTaskNo1.Text = "0000000001";
            this.cmbTaskType.SelectedIndex = 9;
        }

        private void cmbCraneNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            string CarNo = this.cmbCarNo.Text;
            DataTable dt = bll.FillDataTable("WCS.SelectStationNo", new DataParameter[] { new DataParameter("{0}","AisleNo='02'") });
            this.cmbStationNo.DataSource = dt.DefaultView;
            this.cmbStationNo.ValueMember = "StationNo";
            this.cmbStationNo.DisplayMember = "StationNo";
        }

        private void cmbStationNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            BindAisleNo();
            
        }
        private void BindAisleNo()
        {
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("StationNo='{0}'", this.cmbStationNo.Text))
            };

            DataTable dt = bll.FillDataTable("CMD.SelectAisle", param);
            this.cmbAisleNo.DataSource = dt.DefaultView;
            this.cmbAisleNo.ValueMember = "AisleNo";
            this.cmbAisleNo.DisplayMember = "AisleNo";
        }      
        private void cmbTaskType_SelectedIndexChanged(object sender, EventArgs e)
        {
            BindShelf();            
        }
        private void BindShelf()
        {
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("StationNo='{1}' and AisleNo='{2}'", this.cmbCarNo.Text,this.cmbStationNo.Text,this.cmbAisleNo.Text))
            };
            DataParameter[] param1 = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("StationNo='{1}' and AisleNo='{2}' and IsStation='1'", this.cmbCarNo.Text,this.cmbStationNo.Text,this.cmbAisleNo.Text))
            };
            if (this.cmbTaskType.SelectedIndex == 9)
            {
                //DataTable dt = bll.FillDataTable("CMD.SelectShelf", param1);
                //this.cbFromRow.DataSource = dt;
                //this.cbFromRow.DisplayMember = "shelfcode";
                //this.cbFromRow.ValueMember = "shelfcode";
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                 
                dr["dtText"] = "001004";
                dr["dtValue"] = "001004";
                
                dt.Rows.Add(dr);
                this.cbFromRow.DataSource = dt;
                this.cbFromRow.DisplayMember = "dtText";
                this.cbFromRow.ValueMember = "dtValue";
            }
            else 
            {
                DataTable dt = bll.FillDataTable("CMD.SelectShelf", param);
                this.cbFromRow.DataSource = dt.DefaultView;
                this.cbFromRow.ValueMember = "shelfcode";
                this.cbFromRow.DisplayMember = "shelfcode";
            }

            if (this.cmbTaskType.SelectedIndex == 10)
            {
                //DataTable dt = bll.FillDataTable("CMD.SelectShelf", param1);
                //this.cbToRow.DataSource = dt;
                //this.cbToRow.DisplayMember = "shelfcode";
                //this.cbToRow.ValueMember = "shelfcode";
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "001003";
                dr["dtValue"] = "001003";

                dt.Rows.Add(dr);
                this.cbToRow.DataSource = dt;
                this.cbToRow.DisplayMember = "dtText";
                this.cbToRow.ValueMember = "dtValue";

            }
            else
            {
                DataTable dtt = bll.FillDataTable("CMD.SelectShelf", param);
                this.cbToRow.DataSource = dtt.DefaultView;
                this.cbToRow.ValueMember = "shelfcode";
                this.cbToRow.DisplayMember = "shelfcode";
            }
        }
        
        private void cbFromRow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbFromRow.Text))
            };


            if (this.cmbTaskType.SelectedIndex == 9)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                //if (this.cbFromRow.Text == "001005")
                //{
                //    dr["dtText"] = "12";
                //    dr["dtValue"] = "12";
                //}
                //else
                //{
                //    dr["dtText"] = "1";
                //    dr["dtValue"] = "1";
                //}

                dr["dtText"] = "0";
                dr["dtValue"] = "0";

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
        private void cbToRow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbToRow.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 10)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                //dr["dtText"] = "1";
                //dr["dtValue"] = "1";

                dr["dtText"] = "0";
                dr["dtValue"] = "0";

                dt.Rows.Add(dr);
                this.cbToColumn.DataSource = dt;
                this.cbToColumn.DisplayMember = "dtText";
                this.cbToColumn.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

                this.cbToColumn.DataSource = dt.DefaultView;
                this.cbToColumn.ValueMember = "CellColumn";
                this.cbToColumn.DisplayMember = "CellColumn";
            }
        }

        private void cbFromColumn_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow.Text == "System.Data.DataRowView")
                return;
            if (this.cbFromColumn.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbFromRow.Text,this.cbFromColumn.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 9)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                dr["dtText"] = "1";
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
        private void cbToColumn_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow.Text == "System.Data.DataRowView")
                return;
            if (this.cbToColumn.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbToRow.Text,this.cbToColumn.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 10)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "1";
                dr["dtValue"] = "1";

                dt.Rows.Add(dr);
                this.cbToHeight.DataSource = dt;
                this.cbToHeight.DisplayMember = "dtText";
                this.cbToHeight.ValueMember = "dtValue";
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

        private void btnAction_Click(object sender, EventArgs e)
        {
            string serviceName = "CarPLC01" + this.cmbCarNo.Text;

            int[] cellAddr = new int[10];

            cellAddr[0] = 0;
            cellAddr[1] = 0;
            cellAddr[2] = 0;

            cellAddr[3] = int.Parse(this.cbFromRow.Text.Substring(3, 3));
            cellAddr[4] = byte.Parse(this.cbFromColumn.Text);
            cellAddr[5] = int.Parse(this.cbFromHeight.Text);
            cellAddr[6] = int.Parse(this.cbToRow.Text.Substring(3, 3));
            cellAddr[7] = byte.Parse(this.cbToColumn.Text);
            cellAddr[8] = int.Parse(this.cbToHeight.Text);
            cellAddr[9] = this.cmbTaskType.SelectedIndex + 1;

            int taskNo = int.Parse(this.txtTaskNo1.Text);

            Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
            Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
            Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);
            

            string fromStation = this.cbFromRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbFromColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbFromHeight.Text)).ToString().Substring(1, 3);
            string toStation = this.cbToRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbToColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbToHeight.Text)).ToString().Substring(1, 3);
            MCP.Logger.Info("测试任务已下发给" + this.cmbCarNo.Text + "堆垛机;起始地址:" + fromStation + ",目标地址:" + toStation);
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            string serviceName = "CranePLC01" + this.cmbCarNo.Text;
            int[] cellAddr = new int[10];
            cellAddr[0] = 0;
            cellAddr[1] = 1;

            Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
            Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 0);

            MCP.Logger.Info("任务已下发给" + this.cmbCarNo.Text + "取消任务指令");
        }

        private void cmbAisleNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            BindShelf();
        }        

        private void btnOutTask_Click(object sender, EventArgs e)
        {
            BLL.BLLBase bll = new BLL.BLLBase();
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("@AreaCode", "002") 
            };
            string TaskNo = "";
            DataTable dt = bll.FillDataTable("WCS.Sp_CreateCarOutTask", param);
            if (dt.Rows.Count > 0)
                TaskNo = dt.Rows[0][0].ToString();
        }

        private void btnStationNo2_Click(object sender, EventArgs e)
        {
            sbyte[] staskNo = new sbyte[20];
            Util.ConvertStringChar.stringToBytes("TEST", 20).CopyTo(staskNo, 0);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutTaskNo2", staskNo);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutFinish2", 1);
        }
        
    }
}
