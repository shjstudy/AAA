using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Util;


namespace App.View
{
    public partial class frmReassignEmptyCell : Form
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CraneNo = "01";
        int Layer = 0;

        public frmReassignEmptyCell()
        {
            InitializeComponent();
        }
        public frmReassignEmptyCell(DataRow dr)
        {
            InitializeComponent();
            this.dr = dr;
        }
        private void frmReassignEmptyCell_Load(object sender, EventArgs e)
        {
            this.txtTaskNo.Text = dr["TaskNo"].ToString();
            this.txtCellCode.Text = dr["CellCode"].ToString();
            this.txtAisleNo.Text = dr["AisleNo"].ToString();
            this.txtCraneNo.Text = dr["CraneNo"].ToString();
            this.txtCarNo.Text = dr["CarNo"].ToString();
            this.txtAreaName.Text = dr["AreaName"].ToString();
            this.txtPalletCode.Text = dr["PalletCode"].ToString();
            this.txtAreaCode.Text = dr["AreaCode"].ToString();
            if (this.txtAreaCode.Text=="002")
                Layer = int.Parse(this.txtCellCode.Text.Substring(6, 3));

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("AreaCode='{0}' and AisleNo='{1}'", this.txtAreaCode.Text,this.txtAisleNo.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectCellShelf", param);
            this.cbRow.DataSource = dt.DefaultView;
            this.cbRow.ValueMember = "shelfcode";
            this.cbRow.DisplayMember = "shelfcode";
        }
        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            if (this.radioButton1.Checked)
            {
                this.cbRow.Enabled = false;
                this.cbColumn.Enabled = false;
                this.cbHeight.Enabled = false;
                this.cbDepth.Enabled = false;
            }
            else
            {
                this.cbRow.Enabled = true;
                this.cbColumn.Enabled = true;
                this.cbHeight.Enabled = true;
                this.cbDepth.Enabled = true;
            }
        }
        private void btnSearch_Click(object sender, EventArgs e)
        {
            string strWhere = "1=1 ";
            if (this.cbRow.Text.Length > 0)
                strWhere += string.Format("AND SHELF_CODE='001001{0}'", this.cbRow.Text);
            if (this.cbColumn.Text.Length > 0)
                strWhere += string.Format("AND CELL_COLUMN='{0}'", this.cbColumn.Text);
            if (this.cbHeight.Text.Length > 0)
                strWhere += string.Format("AND CELL_ROW='{0}'", this.cbHeight.Text);

        }
        private void cbRow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and AreaCode='{1}'",this.cbRow.Text,this.txtAreaCode.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

            this.cbColumn.DataSource = dt.DefaultView;
            this.cbColumn.ValueMember = "CellColumn";
            this.cbColumn.DisplayMember = "CellColumn";

            dt = new DataTable("dt");
            dt.Columns.Add("dtText");
            dt.Columns.Add("dtValue");
            DataRow dr = dt.NewRow();
            dr["dtText"] = "1";
            dr["dtValue"] = "1";
            dt.Rows.Add(dr);
            if (cbRow.Text == "001005")
            {
                dr = dt.NewRow();
                dr["dtText"] = "2";
                dr["dtValue"] = "2";
                dt.Rows.Add(dr);
            }
            this.cbDepth.DataSource = dt.DefaultView;
            this.cbDepth.DisplayMember = "dtText";
            this.cbDepth.ValueMember = "dtValue";
        }

        private void cbColumn_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;
            if (this.cbColumn.Text == "System.Data.DataRowView")
                return;

            string filter = string.Format("ShelfCode='{0}' and CellColumn={1} and AreaCode='{2}'",this.cbRow.Text,this.cbColumn.Text,this.txtAreaCode.Text);
            //如果是小车任务分配货位要与重入任务在同一层
            if (this.txtAreaCode.Text=="002")
                filter = string.Format("ShelfCode='{0}' and CellColumn={1} and AreaCode='{2}' and CellRow={3}", this.cbRow.Text, this.cbColumn.Text, this.txtAreaCode.Text, Layer);
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", filter)
            };

            DataTable dt = bll.FillDataTable("CMD.SelectCellHeight", param);
            DataView dv = dt.DefaultView;
            dv.Sort = "CellRow";
            this.cbHeight.DataSource = dv;
            this.cbHeight.ValueMember = "CellRow";
            this.cbHeight.DisplayMember = "CellRow";
        }        

        private void btnCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnOK_Click(object sender, EventArgs e)
        {
            string TaskNo = this.txtTaskNo.Text;

            DataTable dt;
            DataParameter[] param;
            param = new DataParameter[] 
            { 
                new DataParameter("@AisleNo", this.txtCraneNo.Text), 
                new DataParameter("@AreaCode", this.txtAreaCode.Text), 
                new DataParameter("@Layer", Layer) 
            };
            if (this.radioButton1.Checked)
            {
                dt = bll.FillDataTable("WCS.sp_GetCellByAisle", param);
                if (dt.Rows.Count > 0)
                    this.txtNewCellCode.Text = dt.Rows[0][0].ToString();
                else
                    this.txtNewCellCode.Text = "";
            }
            else
            {
                this.txtNewCellCode.Text = this.cbRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbHeight.Text)).ToString().Substring(1, 3) + this.cbDepth.Text;
            }

            //判断货位是否空闲，且只有空托盘
            param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CellCode='{0}' and ProductCode='' and IsActive='1' and IsLock='0' and ErrorFlag!='1'",this.txtNewCellCode.Text))
            };
            dt = bll.FillDataTable("CMD.SelectCell", param);
            if (dt.Rows.Count <= 0)
            {
                MessageBox.Show("此货位非空货位,请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            param = new DataParameter[] 
            { 
                new DataParameter("@TaskNo", TaskNo), 
                new DataParameter("@NewCellCode", this.txtNewCellCode.Text),
                new DataParameter("@IsTarget", "0")
            };

            bll.ExecNonQuery("WCS.Sp_UpdateTaskCellCode", param);

            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }
    }
}
