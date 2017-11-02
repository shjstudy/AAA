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
    public partial class frmPalletInTask : Form
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        DataRow dr;
        string CraneNo = "01";

        public frmPalletInTask()
        {
            InitializeComponent();
        }

        private void btnRequest_Click(object sender, EventArgs e)
        {
            DataTable dt;
            DataParameter[] param;
            param = new DataParameter[] 
            { 
                new DataParameter("@CraneNo", this.cmbCraneNo.Text), 
                new DataParameter("@CarNo", this.cmbCarNo.Text) 
            };

            if (this.radioButton1.Checked)
            {
                dt = bll.FillDataTable("WCS.sp_GetEmptyCell", param);
                if (dt.Rows.Count > 0)
                    this.txtCellCode.Text = dt.Rows[0][0].ToString();
                else
                    this.txtCellCode.Text = "";
            }
            else
            {
                this.txtCellCode.Text = this.cbRow.Text.Substring(3, 3) + (1000 + int.Parse(this.cbColumn.Text)).ToString().Substring(1, 3) + (1000 + int.Parse(this.cbHeight.Text)).ToString().Substring(1, 3);
            }
            string ProductCode = "00" + CraneNo;
            
            //判断货位是否空闲，且只有空托盘
            param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ProductCode='' and IsActive='1' and IsLock='0' and ErrorFlag!='1' and CellCode='{0}'",this.txtCellCode.Text))
            };
            dt = bll.FillDataTable("CMD.SelectCell", param);
            if (dt.Rows.Count <= 0)
            {
                MessageBox.Show("自动获取或指定货位非空货位或已没有空货位,请确认！", "提示", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            //锁定货位
            param = new DataParameter[] 
            { 
                new DataParameter("@CarNo", this.cmbCarNo.Text),
                new DataParameter("@CraneNo", this.cmbCraneNo.Text),
                new DataParameter("@CellCode", this.txtCellCode.Text)
            };
            bll.ExecNonQueryTran("WCS.Sp_CreatePalletInTask", param);
            this.DialogResult = System.Windows.Forms.DialogResult.OK;
        }

        private void frmPalletInTask_Load(object sender, EventArgs e)
        {
            DataTable dt = bll.FillDataTable("CMD.SelectCrane", new DataParameter[] { new DataParameter("{0}", "CMD_Crane.State='1'")});
            this.cmbCraneNo.DataSource = dt.DefaultView;
            this.cmbCraneNo.ValueMember = "CraneNo";
            this.cmbCraneNo.DisplayMember = "CraneNo";            
        }

        private void cmbCraneNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            string CraneNo = this.cmbCraneNo.Text;
            DataTable dt = bll.FillDataTable("CMD.SelectCar", new DataParameter[] { new DataParameter("{0}", string.Format("CraneNo='{0}'", CraneNo)) });
            this.cmbCarNo.DataSource = dt.DefaultView;
            this.cmbCarNo.ValueMember = "CarNo";
            this.cmbCarNo.DisplayMember = "CarNo";

            this.txtProductCode.Text = "00" + this.cmbCraneNo.Text;
            this.txtProductName.Text = "空托盘";
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = System.Windows.Forms.DialogResult.No;
        }


        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            if (this.radioButton1.Checked)
            {
                this.cbRow.Enabled = false;
                this.cbColumn.Enabled = false;
                this.cbHeight.Enabled = false;
            }
            else
            {
                this.cbRow.Enabled = true;
                this.cbColumn.Enabled = true;
                this.cbHeight.Enabled = true;
            }
        }

        private void cmbCarNo_SelectedIndexChanged(object sender, EventArgs e)
        {
            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("CraneNo='{0}'", this.cmbCraneNo.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectShelf", param);
            this.cbRow.DataSource = dt.DefaultView;
            this.cbRow.ValueMember = "shelfcode";
            this.cbRow.DisplayMember = "shelfcode";
        }
        private void cbRow_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}'",this.cbRow.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectColumn", param);

            this.cbColumn.DataSource = dt.DefaultView;
            this.cbColumn.ValueMember = "CellColumn";
            this.cbColumn.DisplayMember = "CellColumn";
        }

        private void cbColumn_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (this.cbRow.Text == "System.Data.DataRowView")
                return;
            if (this.cbColumn.Text == "System.Data.DataRowView")
                return;

            DataParameter[] param = new DataParameter[] 
            { 
                new DataParameter("{0}", string.Format("ShelfCode='{0}' and CellColumn={1}",this.cbRow.Text,this.cbColumn.Text))
            };
            DataTable dt = bll.FillDataTable("CMD.SelectCell", param);
            DataView dv = dt.DefaultView;
            dv.Sort = "CellRow";
            this.cbHeight.DataSource = dv;
            this.cbHeight.ValueMember = "CellRow";
            this.cbHeight.DisplayMember = "CellRow";
            
            //大型集成项目可以学到很多东西
            //增强团队管理、团队协作的能力
            //项目进度管控进一步加强
            //学习到行业内先进的技术、先进的设备
            //要以用户体验至上的标准去要求自己
        }
    }
}
