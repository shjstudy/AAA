using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;

namespace App.View.Dispatcher
{
    public partial class frmCellQuery : BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        
        private Dictionary<int, DataRow[]> shelf = new Dictionary<int, DataRow[]>();
        private Dictionary<int, string> ShelfCode = new Dictionary<int, string>();
        private Dictionary<int, int> ShelfRow = new Dictionary<int, int>();
        private Dictionary<int, int> ShelfColumn = new Dictionary<int, int>();

        private DataTable cellTable = null;        
        private bool needDraw = false;
        private bool filtered = false;

        private int[] Columns = new int[3];
        private int[] Rows = new int[3];
        private int[] Depth = new int[3];
        private int cellWidth = 0;
        private int cellHeight = 0;
        private int currentPage = 1;
        private int top = 15;
        private int left = 5;
        string CellCode = "";
        private bool IsWheel = true;
        private float adjustWidth;
        private Font font;

        public frmCellQuery()
        {
            InitializeComponent();
            //设置双缓冲
            SetStyle(ControlStyles.DoubleBuffer |
                ControlStyles.UserPaint |
                ControlStyles.AllPaintingInWmPaint, true);

            Filter.EnableFilter(dgvMain);
            pnlData.Visible = true;
            pnlData.Dock = DockStyle.Fill;

            pnlChart.Visible = false;
            pnlChart.Dock = DockStyle.Fill;
        
            this.PColor.Visible = false;
        }
        private void btnRefresh_Click(object sender, EventArgs e)
        {
            cellTable = bll.FillDataTable("WCS.SelectCell");
            bsMain.DataSource = cellTable;
            pnlChart.Invalidate();
        }

        private void btnQuery_Click(object sender, EventArgs e)
        {

            try
            {
                if (bsMain.Filter.Trim().Length != 0)
                {
                    DialogResult result = MessageBox.Show("重新读入数据请选择'是(Y)',清除过滤条件请选择'否(N)'", "询问", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);
                    switch (result)
                    {
                        case DialogResult.No:
                            DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn.RemoveFilter(dgvMain);
                            return;
                        case DialogResult.Cancel:
                            return;
                    }
                }
                ShelfCode.Clear();

                DataTable dtShelf = bll.FillDataTable("CMD.SelectShelf");
                for (int i = 0; i < dtShelf.Rows.Count; i++)
                {
                    ShelfCode.Add(i + 1, dtShelf.Rows[i]["ShelfCode"].ToString());
                }
               
                btnQuery.Enabled = false;
                btnChart.Enabled = false;

                pnlProgress.Top = (pnlMain.Height - pnlProgress.Height) / 3;
                pnlProgress.Left = (pnlMain.Width - pnlProgress.Width) / 2;
                pnlProgress.Visible = true;
                Application.DoEvents();

                cellTable = bll.FillDataTable("WCS.SelectCell");
                bsMain.DataSource = cellTable;

                pnlProgress.Visible = false;
                btnQuery.Enabled = true;
                btnChart.Enabled = true;
            }
            catch (Exception exp)
            {
                MessageBox.Show("读入数据失败，原因：" + exp.Message);
            }
        }       
        private void btnChart_Click(object sender, EventArgs e)
        {
            if (cellTable != null && cellTable.Rows.Count != 0)
            {
                if (pnlData.Visible)
                {
                    this.PColor.Visible = true;
                    filtered = bsMain.Filter != null;
                    needDraw = true;
                    btnQuery.Enabled = false;
                    pnlData.Visible = false;
                    pnlChart.Visible = true;
                    btnChart.Text = "列表";
                }
                else
                {
                    this.PColor.Visible = false;
                    needDraw = false;
                    btnQuery.Enabled = true;
                    pnlData.Visible = true;
                    pnlChart.Visible = false;
                    btnChart.Text = "图形";
                }
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void pnlChart_Paint(object sender, PaintEventArgs e)
        {
            try
            {
                if (needDraw)
                {
                    for (int i = 1; i <= 2; i++)
                    {
                        string shelfCode = "001" + i.ToString().PadLeft(3, '0');
                        DataRow[] drs = cellTable.Select(string.Format("ShelfCode='{0}'", shelfCode), "CellCode desc");
                        int left = 0;
                        if (i == 2)
                            left = 5 + (int)adjustWidth + 6 * cellWidth + 200;
                        DrawShelf(drs, e.Graphics, top, font, adjustWidth, left, i.ToString());

                    }

                }
                PColor.Refresh();
                IsWheel = false;
            }
            catch (Exception ex)
            {
                string str = ex.Message;
            }
        }

        private void DrawShelf(DataRow[] cellRows, Graphics g, int top, Font font, float adjustWidth, int shelfleft, string ShelfName)
        {
            string shelfCode = cellRows[0]["ShelfCode"].ToString();
            for (int j = 5; j >= 1; j--)
            {
                string s = string.Format("{0}排{1}层", ShelfName, j);
                g.DrawString(s, font, Brushes.DarkCyan, shelfleft + 5, top + (5 - j) * cellHeight + cellHeight / 2 - 5);
            }
            foreach (DataRow cellRow in cellRows)
            {

                int column = Convert.ToInt32(cellRow["CellColumn"]);
                string cellCode = cellRow["CellCode"].ToString();

                int row = Convert.ToInt32(cellRow["CellRow"]);
                int quantity = ReturnColorFlag(cellRow["ProductCode"].ToString(), cellRow["PalletBarCode"].ToString(), cellRow["IsActive"].ToString(), cellRow["IsLock"].ToString(), cellRow["ErrorFlag"].ToString(), cellRow["InDate"].ToString());

                int x = left + (int)adjustWidth + shelfleft + (column - 1) * cellWidth;
                int y = top + (5 - row) * cellHeight;

                Pen pen = new Pen(Color.DarkCyan, 2);
                g.DrawRectangle(pen, new Rectangle(x, y, cellWidth, cellHeight));
              
                FillCell(g, x, y, quantity);
                string strMsg = "";
                if (cellRow["Qty"].ToString() != "0")
                    strMsg = cellRow["ProductName"].ToString() + Environment.NewLine + cellRow["Qty"].ToString() + cellRow["Unit"].ToString();
                g.DrawString(strMsg, new Font("微软雅黑", 9), Brushes.Red, new Point(x + 4, y + 4));

            }
            for (int j = 1; j <= 6; j++)
            {
                g.DrawString(Convert.ToString(j), new Font("微软雅黑", 10), Brushes.DarkCyan, left + (j - 1) * cellWidth + shelfleft + adjustWidth + cellWidth / 2 - 2, top + cellHeight * 5 + 5);
            }
        }

        private void FillCell(Graphics g, int x, int y,  int quantity)
        {

            //0:空货位，1:空货位锁定 2:有货,3:出库锁定 4:禁用 ,5:有问题 6:入库锁定 7:空箱
            if (quantity == 0)  //空货位锁定
                g.FillRectangle(Brushes.White, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            if (quantity == 1)  //空货位锁定
                g.FillRectangle(Brushes.Yellow, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 2) //有货
                g.FillRectangle(Brushes.Blue, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 3) //出库锁定
                g.FillRectangle(Brushes.Green, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 4) //禁用
                g.FillRectangle(Brushes.Gray, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 5) //有问题
                g.FillRectangle(Brushes.Red, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 6) //入库锁定
                g.FillRectangle(Brushes.LawnGreen, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
            else if (quantity == 7) //空箱
                g.FillRectangle(Brushes.BlueViolet, new Rectangle(x + 2, y + 2, cellWidth - 3, cellHeight - 3));
        }

        private void pnlChart_Resize(object sender, EventArgs e)
        {


            cellWidth = (pnlContent.Width - 210 - (int)adjustWidth * 2) / 12;
            cellHeight = (pnlContent.Height) / 6;
        }

        private void SetCellSize(int Columns, int Rows)
        {
            
        }

        private void pnlChart_MouseClick(object sender, MouseEventArgs e)
        {
            int Shelfleft = (int)adjustWidth + 6 * cellWidth + 200;
            int i = e.X < Shelfleft ? 0 : 1;
            string ShelfCode = "001001";


         

            int column = (int)Math.Ceiling((e.X - left - adjustWidth) / cellWidth);
            if (i == 1)
            {
                column = (int)Math.Ceiling((e.X - left - adjustWidth - Shelfleft) / cellWidth);
                ShelfCode = "001002";
            }
            int row = 6 - (int)Math.Ceiling((double)(e.Y - top) / cellHeight);
            if (column <= 6 && row <= 5 && row > 0 && column > 0)
            {
                string filter = string.Format("ShelfCode='{0}' AND CellColumn='{1}' AND CellRow='{2}'", ShelfCode, column, row);

                DataRow[] cellRows = cellTable.Select(filter);
                if (cellRows.Length != 0)
                {
                    CellCode = cellRows[0]["CellCode"].ToString();
                    //if (e.Button == System.Windows.Forms.MouseButtons.Left)
                    //{
                    //    if (cellRows.Length != 0)
                    //    {
                    //        if (cellRows[0]["PalletBarCode"].ToString() != "")
                    //        {
                    //            frmCellInfo f = new frmCellInfo(cellRows[0]["PalletBarCode"].ToString(), CellCode);
                    //            f.ShowDialog();
                    //        }
                    //    }
                    //}
                    if (e.Button == System.Windows.Forms.MouseButtons.Right)
                    {
                        DataTable dt = Program.dtUserPermission;
                        string strPermission = "SubModuleCode='MNU_M00C_00D' and OperatorCode='2'";
                        DataRow[] drs = dt.Select(strPermission);
                        if (drs.Length <= 0)
                            return;

                        drs = cellTable.Select(string.Format("CellCode='{0}'", CellCode));
                        if (drs.Length > 0)
                        {
                            DataRow dr = drs[0];
                            frmCellOpDialog cellDialog = new frmCellOpDialog(dr);
                            if (cellDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                            {
                                cellTable = bll.FillDataTable("WCS.SelectCell");
                                bsMain.DataSource = cellTable;
                                pnlChart.Invalidate();
                            }
                        }
                    }
                }
            }

        }        

        private void pnlChart_MouseEnter(object sender, EventArgs e)
        {
            pnlChart.Focus();
        }

        
         

        private void dgvMain_RowPostPaint(object sender, DataGridViewRowPostPaintEventArgs e)
        {
            Rectangle rectangle = new Rectangle(e.RowBounds.Location.X, e.RowBounds.Location.Y, dgvMain.RowHeadersWidth - 4, e.RowBounds.Height);
            TextRenderer.DrawText(e.Graphics, (e.RowIndex + 1).ToString(), dgvMain.RowHeadersDefaultCellStyle.Font, rectangle, dgvMain.RowHeadersDefaultCellStyle.ForeColor, TextFormatFlags.VerticalCenter | TextFormatFlags.Right);
        }

        private int ReturnColorFlag(string ProductCode,string PalletCode, string IsActive, string IsLock, string ErrFlag,string indate)
        {
             //0:空货位，1:空货位锁定 2:有货,3:出库锁定 4:禁用 ,5:有问题 6:入库锁定 7:空箱
                
            int Flag = 0;
            if (indate == "")
            {
                if (IsLock == "1" && (ProductCode != "" || PalletCode != ""))
                    Flag = 6;
                else
                    Flag = 0;
            }
            else
            {
                if (ProductCode == "")
                {
                    if (PalletCode != "" &&  IsLock=="0")
                        Flag = 7;
                    else if (IsLock == "1")
                        Flag = 3;

                }
                else
                {
                    if (IsLock == "1")
                        Flag = 3;
                    else
                        Flag = 2;       
                }
            }

            if (IsActive == "0")
                Flag = 4;
            if (ErrFlag == "1")
                Flag = 5;
            return Flag;
        }

     

        private int X, Y;
        private void pnlChart_MouseMove(object sender, MouseEventArgs e)
        {

            if (IsWheel) return;
            if (X != e.X || Y != e.Y)
            {

                int Shelfleft = (int)adjustWidth + 6 * cellWidth + 200;
                int i = e.X < Shelfleft ? 0 : 1;


                int column = (int)Math.Ceiling((e.X - left - adjustWidth) / cellWidth);
                if (i == 1)
                    column = (int)Math.Ceiling((e.X - left - adjustWidth  - Shelfleft) / cellWidth);
                int row = 6 - (int)Math.Ceiling((double)(e.Y - top) / cellHeight);
                if (column <= 6 && row <= 5 && row > 0 && column > 0)
                {
                    string tip = "货架:" + (i + 1).ToString() + ";列:" + column.ToString() + ";层:" + row.ToString();
                    toolTip1.SetToolTip(pnlChart, tip);
                }
                else
                    toolTip1.SetToolTip(pnlChart, null);

                X = e.X;
                Y = e.Y;
            }
        }


        private void ToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            
        }
        private void ToolStripMenuItem2_Click(object sender, EventArgs e)
        {
            cellTable = bll.FillDataTable("WCS.SelectCell");
            bsMain.DataSource = cellTable;
            pnlChart.Invalidate();
        }

        private void frmCellQuery_Shown(object sender, EventArgs e)
        {
            font = new Font("微软雅黑", 9);
            SizeF size = this.CreateGraphics().MeasureString("1排1层", font);
            adjustWidth = size.Width;
        }

     
    }
}
