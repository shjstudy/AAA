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
    public partial class frmMiniLoadTask : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CraneNo = "02";
        string serviceName = "MiniLoad02";

        public frmMiniLoadTask()
        {
            InitializeComponent();
        }

        private void frmMiniLoadTask_Load(object sender, EventArgs e)
        {
            DataTable dt = bll.FillDataTable("CMD.SelectCrane", new DataParameter[] { new DataParameter("{0}", "CMD_Crane.State='1' and CMD_Crane.CraneNo='02'") });
            this.cmbCraneNo.DataSource = dt.DefaultView;
            this.cmbCraneNo.ValueMember = "CraneNo";
            this.cmbCraneNo.DisplayMember = "CraneNo";

            this.cmbTaskType.SelectedIndex = 0;

            BindAisleNo();
            //this.txtTaskNo1.Text = "0000000001";

            DataTable dtt = new DataTable("dt");
            dtt.Columns.Add("dtText");
            dtt.Columns.Add("dtValue");
            DataRow dr = dtt.NewRow();
            dr["dtText"] = "1";
            dr["dtValue"] = "1";
            dtt.Rows.Add(dr);
            dr = dtt.NewRow();
            dr["dtText"] = "2";
            dr["dtValue"] = "2";
            dtt.Rows.Add(dr);

            this.cbFromDepth1.DataSource = dtDepth();
            this.cbFromDepth1.DisplayMember = "dtText";
            this.cbFromDepth1.ValueMember = "dtValue";

            this.cbFromDepth2.DataSource = dtDepth();
            this.cbFromDepth2.DisplayMember = "dtText";
            this.cbFromDepth2.ValueMember = "dtValue";

            this.cbToDepth1.DataSource = dtDepth();
            this.cbToDepth1.DisplayMember = "dtText";
            this.cbToDepth1.ValueMember = "dtValue";

            this.cbToDepth2.DataSource = dtDepth();
            this.cbToDepth2.DisplayMember = "dtText";
            this.cbToDepth2.ValueMember = "dtValue";
            
        }
        private DataTable dtDepth()
        {
            DataTable dt = new DataTable("dt");
            dt.Columns.Add("dtText");
            dt.Columns.Add("dtValue");
            DataRow dr = dt.NewRow();
            dr["dtText"] = "1";
            dr["dtValue"] = "1";
            dt.Rows.Add(dr);
            dr = dt.NewRow();
            dr["dtText"] = "2";
            dr["dtValue"] = "2";
            dt.Rows.Add(dr);
            return dt;
        }        
        
        private void BindAisleNo()
        {
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CraneNo='{0}'", this.cmbCraneNo.Text))
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
                new DataParameter("{0}", string.Format("CraneNo='{0}' and AisleNo='{1}'", this.cmbCraneNo.Text,this.cmbAisleNo.Text))
            };
            DataParameter[] param1 = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CraneNo='{0}' and AisleNo='{1}' and IsStation='1'", this.cmbCraneNo.Text,this.cmbAisleNo.Text))
            };
            if (this.cmbTaskType.SelectedIndex == 0)
            {
                //DataTable dt = bll.FillDataTable("CMD.SelectShelf", param1);
                //this.cbFromRow.DataSource = dt;
                //this.cbFromRow.DisplayMember = "shelfcode";
                //this.cbFromRow.ValueMember = "shelfcode";
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                 
                dr["dtText"] = "001006";
                dr["dtValue"] = "001006";
                
                dt.Rows.Add(dr);
                this.cbFromRow1.DataSource = dt;
                this.cbFromRow1.DisplayMember = "dtText";
                this.cbFromRow1.ValueMember = "dtValue";

                this.cbFromRow2.DataSource = dt;
                this.cbFromRow2.DisplayMember = "dtText";
                this.cbFromRow2.ValueMember = "dtValue";
            }
            else 
            {
                DataTable dt = bll.FillDataTable("CMD.SelectShelf", param);
                this.cbFromRow1.DataSource = dt.DefaultView;
                this.cbFromRow1.ValueMember = "shelfcode";
                this.cbFromRow1.DisplayMember = "shelfcode";

                this.cbFromRow2.DataSource = dt.DefaultView;
                this.cbFromRow2.ValueMember = "shelfcode";
                this.cbFromRow2.DisplayMember = "shelfcode";
            }

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                //DataTable dt = bll.FillDataTable("CMD.SelectShelf", param1);
                //this.cbToRow.DataSource = dt;
                //this.cbToRow.DisplayMember = "shelfcode";
                //this.cbToRow.ValueMember = "shelfcode";
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "001005";
                dr["dtValue"] = "001005";

                dt.Rows.Add(dr);
                this.cbToRow1.DataSource = dt;
                this.cbToRow1.DisplayMember = "dtText";
                this.cbToRow1.ValueMember = "dtValue";

                this.cbToRow2.DataSource = dt;
                this.cbToRow2.ValueMember = "dtText";
                this.cbToRow2.DisplayMember = "dtValue";

            }
            else
            {
                DataTable dtt = bll.FillDataTable("CMD.SelectShelf", param);
                this.cbToRow1.DataSource = dtt.DefaultView;
                this.cbToRow1.ValueMember = "shelfcode";
                this.cbToRow1.DisplayMember = "shelfcode";

                this.cbToRow2.DataSource = dtt.DefaultView;
                this.cbToRow2.ValueMember = "shelfcode";
                this.cbToRow2.DisplayMember = "shelfcode";
            }
        }

        private void cbFromRow1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow1.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbFromRow1.Text))
            };


            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "37";
                dr["dtValue"] = "37";

                dt.Rows.Add(dr);
                this.cbFromColumn1.DataSource = dt;
                this.cbFromColumn1.DisplayMember = "dtText";
                this.cbFromColumn1.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);
                this.cbFromColumn1.DataSource = dt.DefaultView;
                this.cbFromColumn1.ValueMember = "CellColumn";
                this.cbFromColumn1.DisplayMember = "CellColumn";
            }
        }
        private void cbFromRow2_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow2.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbFromRow2.Text))
            };


            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "37";
                dr["dtValue"] = "37";

                dt.Rows.Add(dr);

                this.cbFromColumn2.DataSource = dt;
                this.cbFromColumn2.DisplayMember = "dtText";
                this.cbFromColumn2.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

                this.cbFromColumn2.DataSource = dt.DefaultView;
                this.cbFromColumn2.ValueMember = "CellColumn";
                this.cbFromColumn2.DisplayMember = "CellColumn";
            }
        }
        private void cbToRow1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow1.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbToRow1.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                //dr["dtText"] = "1";
                //dr["dtValue"] = "1";

                dr["dtText"] = "37";
                dr["dtValue"] = "37";

                dt.Rows.Add(dr);
                this.cbToColumn1.DataSource = dt;
                this.cbToColumn1.DisplayMember = "dtText";
                this.cbToColumn1.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

                this.cbToColumn1.DataSource = dt.DefaultView;
                this.cbToColumn1.ValueMember = "CellColumn";
                this.cbToColumn1.DisplayMember = "CellColumn";
            }
        }
        private void cbToRow2_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow2.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbToRow2.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                //dr["dtText"] = "1";
                //dr["dtValue"] = "1";

                dr["dtText"] = "37";
                dr["dtValue"] = "37";

                dt.Rows.Add(dr);
                this.cbToColumn2.DataSource = dt;
                this.cbToColumn2.DisplayMember = "dtText";
                this.cbToColumn2.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

                this.cbToColumn2.DataSource = dt.DefaultView;
                this.cbToColumn2.ValueMember = "CellColumn";
                this.cbToColumn2.DisplayMember = "CellColumn";
            }
        }
        private void cbFromColumn1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow1.Text == "System.Data.DataRowView")
                return;
            if (this.cbFromColumn1.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbFromRow1.Text,this.cbFromColumn1.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                dr["dtText"] = "1";
                dr["dtValue"] = "1";
                dt.Rows.Add(dr);
                this.cbFromHeight1.DataSource = dt;
                this.cbFromHeight1.DisplayMember = "dtText";
                this.cbFromHeight1.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";
                this.cbFromHeight1.DataSource = dv;
                this.cbFromHeight1.ValueMember = "CellRow";
                this.cbFromHeight1.DisplayMember = "CellRow";
            }
        }
        private void cbFromColumn2_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbFromRow2.Text == "System.Data.DataRowView")
                return;
            if (this.cbFromColumn2.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbFromRow2.Text,this.cbFromColumn2.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 0)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();
                dr["dtText"] = "1";
                dr["dtValue"] = "1";
                dt.Rows.Add(dr);
                this.cbFromHeight2.DataSource = dt;
                this.cbFromHeight2.DisplayMember = "dtText";
                this.cbFromHeight2.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";
                this.cbFromHeight2.DataSource = dv;
                this.cbFromHeight2.ValueMember = "CellRow";
                this.cbFromHeight2.DisplayMember = "CellRow";
            }
        }
        private void cbToColumn1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow1.Text == "System.Data.DataRowView")
                return;
            if (this.cbToColumn1.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbToRow1.Text,this.cbToColumn1.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "1";
                dr["dtValue"] = "1";

                dt.Rows.Add(dr);
                this.cbToHeight1.DataSource = dt;
                this.cbToHeight1.DisplayMember = "dtText";
                this.cbToHeight1.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";
                this.cbToHeight1.DataSource = dv;
                this.cbToHeight1.ValueMember = "CellRow";
                this.cbToHeight1.DisplayMember = "CellRow";
            }
        }
        private void cbToColumn2_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbToRow2.Text == "System.Data.DataRowView")
                return;
            if (this.cbToColumn2.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbToRow2.Text,this.cbToColumn2.Text))
            };

            if (this.cmbTaskType.SelectedIndex == 1)
            {
                DataTable dt = new DataTable("dt");
                dt.Columns.Add("dtText");
                dt.Columns.Add("dtValue");
                DataRow dr = dt.NewRow();

                dr["dtText"] = "1";
                dr["dtValue"] = "1";

                dt.Rows.Add(dr);
                this.cbToHeight2.DataSource = dt;
                this.cbToHeight2.DisplayMember = "dtText";
                this.cbToHeight2.ValueMember = "dtValue";
            }
            else
            {
                DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
                DataView dv = dt.DefaultView;
                dv.Sort = "CellRow";
                this.cbToHeight2.DataSource = dv;
                this.cbToHeight2.ValueMember = "CellRow";
                this.cbToHeight2.DisplayMember = "CellRow";
            }
        }
        private void btnAction_Click(object sender, EventArgs e)
        {
            if (!this.ckbTaskA.Checked && !this.ckbTaskB.Checked)
            {
                MessageBox.Show("请先勾选要执行的任务!", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            
            int[] cellAddr = new int[19];
            cellAddr[0] = 0;
            cellAddr[1] = 0;
            cellAddr[2] = 0;

            if (this.ckbTaskA.Checked)
            {
                cellAddr[3] = int.Parse(this.cbFromColumn1.Text);
                cellAddr[4] = int.Parse(this.cbFromHeight1.Text);
                cellAddr[5] = int.Parse(this.cbFromRow1.Text.Substring(3, 3));
                cellAddr[6] = int.Parse(this.cbFromDepth1.Text);
                cellAddr[7] = int.Parse(this.cbToColumn1.Text);
                cellAddr[8] = int.Parse(this.cbToHeight1.Text);
                cellAddr[9] = int.Parse(this.cbToRow1.Text.Substring(3, 3));
                cellAddr[10] = int.Parse(this.cbToDepth1.Text);
            }
            if (this.ckbTaskB.Checked)
            {
                cellAddr[11] = int.Parse(this.cbFromColumn2.Text);
                cellAddr[12] = int.Parse(this.cbFromHeight2.Text);
                cellAddr[13] = int.Parse(this.cbFromRow2.Text.Substring(3, 3));
                cellAddr[14] = int.Parse(this.cbFromDepth2.Text);
                cellAddr[15] = int.Parse(this.cbToColumn2.Text);
                cellAddr[16] = int.Parse(this.cbToHeight2.Text);
                cellAddr[17] = int.Parse(this.cbToRow2.Text.Substring(3, 3));
                cellAddr[18] = int.Parse(this.cbToDepth2.Text);
            }
            string fromStation1 = "";
            string toStation1 = "";
            string fromStation2 = "";
            string toStation2 = "";
            if (this.ckbTaskA.Checked)
            {
                fromStation1 = this.cbFromRow1.Text.Substring(3, 3) + (1000 + int.Parse(this.cbFromColumn1.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbFromHeight1.Text)).ToString().Substring(1, 3) + this.cbFromDepth1.Text;
                toStation1 = this.cbToRow1.Text.Substring(3, 3) + (1000 + int.Parse(this.cbToColumn1.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbToHeight1.Text)).ToString().Substring(1, 3) + this.cbToDepth1.Text;
            }
            if (this.ckbTaskB.Checked)
            {
                fromStation2 = this.cbFromRow2.Text.Substring(3, 3) + (1000 + int.Parse(this.cbFromColumn2.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbFromHeight2.Text)).ToString().Substring(1, 3) + this.cbFromDepth2.Text;
                toStation2 = this.cbToRow2.Text.Substring(3, 3) + (1000 + int.Parse(this.cbToColumn2.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbToHeight2.Text)).ToString().Substring(1, 3) + this.cbToDepth2.Text;
            }

            DataParameter[] param = new DataParameter[]
            { 
                new DataParameter("@AreaCode", "003"),
                new DataParameter("@TaskType", this.cmbTaskType.SelectedIndex.ToString()), 
                new DataParameter("@CraneNo", "03"), 
                new DataParameter("@AisleNo", "03"),
                new DataParameter("@FromStation1",fromStation1),
                new DataParameter("@toStation1",toStation1),
                new DataParameter("@fromStation2",fromStation2),
                new DataParameter("@toStation2",toStation2)
            };

            DataTable dt = bll.FillDataTable("WCS.Sp_CreateTestTask", param);
            int[] taskNo = new int[2];
            for(int i=0;i<dt.Rows.Count;i++)
                taskNo[i] = int.Parse(dt.Rows[i][0].ToString());

            this.txtTaskNo1.Text = taskNo[0].ToString();
            this.txtTaskNo2.Text = taskNo[1].ToString();

            //sbyte[] taskNo = new sbyte[10];
            //Util.ConvertStringChar.stringToBytes(this.txtTaskNo1.Text, 10).CopyTo(taskNo, 0);

            Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);            
            Context.ProcessDispatcher.WriteToService(serviceName, "TaskNo", taskNo);
            if (int.Parse((this.cmbTaskType.SelectedIndex).ToString()) == 3)
                Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 2);
            else
                Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);

        
            //MCP.Logger.Info("测试任务已下发给" + this.cmbCraneNo.Text + "堆垛机;起始地址:" + fromStation + ",目标地址:" + toStation);
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }
          
        private void btnCancel_Click(object sender, EventArgs e)
        {            
            int[] cellAddr = new int[19];
            cellAddr[0] = 0;
            cellAddr[1] = 1;

            Context.ProcessDispatcher.WriteToService(serviceName, "TaskAddress", cellAddr);
            Context.ProcessDispatcher.WriteToService(serviceName, "WriteFinished", 1);

            MCP.Logger.Info("取消任务已下发给" + this.cmbCraneNo.Text + "取消任务指令");
        }

        private void cmbAisleNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            BindShelf();
        }

        
        private void ckbTaskA_CheckedChanged(object sender, EventArgs e)
        {
            this.GroupA.Enabled = this.ckbTaskA.Checked;
        }

        private void ckbTaskB_CheckedChanged(object sender, EventArgs e)
        {
            this.GroupB.Enabled = this.ckbTaskB.Checked;
        }

        private void btnStationNo01_Click(object sender, EventArgs e)
        {
            sbyte[] staskNo = new sbyte[20];
            Util.ConvertStringChar.stringToBytes("TEST", 20).CopyTo(staskNo, 0);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutTaskNo1", staskNo);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutFinish1", 1);
        }

        private void btnStationNo3_Click(object sender, EventArgs e)
        {
            sbyte[] staskNo = new sbyte[20];
            Util.ConvertStringChar.stringToBytes("TEST", 20).CopyTo(staskNo, 0);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutTaskNo3", staskNo);
            Context.ProcessDispatcher.WriteToService("TranLine", "OutFinish3", 1);
        }       
    }
}
