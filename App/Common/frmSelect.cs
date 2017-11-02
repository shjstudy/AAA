using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using System.Collections;
using Util;

namespace App.Common
{
    public partial class frmSelect : Form
    {
        private bool SelectOption = false;
        private int PageSize = 20;
        private int CurrentPage = 1;
        private string strWhere = " 1=1";
        private string strInitialWhere = " 1=1 ";
        private int BindCount = 0;
        private string XmltableName = "";

        private DataTable dtData;
        public DataTable dtSelect;
        private BLL.BLLBase bll;
        private string PrimaryKey;
        private string orderBy;
        Hashtable htFields;
        private string TableView;
        private string queryFields;


        public frmSelect()
        {
            InitializeComponent();
        }
        
        public frmSelect(bool SelectOption, string TabelName, string strwhere)
        {
            InitializeComponent();
            this.SelectOption = SelectOption;
            this.XmltableName = TabelName;
            if (strwhere.Trim().Length != 0)
            {
                this.strWhere = strwhere;
                this.strInitialWhere = strwhere;
            }
        }

        private void frmSelect_Load(object sender, EventArgs e)
        {
            bll = new BLL.BLLBase();
            BindComboBox();

            htFields = new Hashtable();
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(AppDomain.CurrentDomain.BaseDirectory + string.Format("\\TableXML\\{0}.xml", XmltableName));
            StringBuilder fieldList = new StringBuilder();
            XmlNode nodeTable = xmlDoc.SelectSingleNode("TABLE");
            PrimaryKey = nodeTable.Attributes["PrimaryKey"].InnerText;
            orderBy = nodeTable.Attributes["OrderBy"].InnerText;
            if (nodeTable.Attributes["ViewName"] != null && nodeTable.Attributes["ViewName"].InnerText != "")
            {
                TableView = nodeTable.Attributes["ViewName"].InnerText;
            }

            foreach (XmlNode node in nodeTable.ChildNodes)
            {
                fieldList.Append(node.ChildNodes[0].InnerText + ",");
                htFields.Add(node.ChildNodes[0].InnerText, node.ChildNodes[1].InnerText);
            }
            fieldList.Remove(fieldList.Length - 1, 1);
            queryFields = fieldList.ToString();

            SetBtnEnabled("");
            SetColumn();
        }

        private void BindComboBox()
        {
            //DataTable ProductType = bll.FillDataTable("Cmd.SelectProductCategory");
            //DataRow dr = ProductType.NewRow();
            //dr["CategoryCode"] = "";
            //dr["CategoryName"] = "请选择";
            //ProductType.Rows.InsertAt(dr, 0);

            //this.ddlProductCategory.ValueMember = "CategoryCode";
            //this.ddlProductCategory.DisplayMember = "CategoryName";
            //this.ddlProductCategory.DataSource = ProductType;
          
        }

        private void SetColumn()
        {
            if (!SelectOption)
            {
                this.colCheck.Visible = false;
                this.dgView.ReadOnly = true;
                 
                
            }
            else
            {
               

                this.colCheck.Visible = true;
                this.colCheck.ReadOnly = false;
                this.dgView.ReadOnly = true;
            }

            this.dgView.Columns[1].Frozen = true;
            this.dgView.Columns[2].Frozen = true;
            //dgView.RowHeadersWidthSizeMode = DataGridViewRowHeadersWidthSizeMode.AutoSizeToAllHeaders;
            //dgView.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }
        private void SetColumnHeadName()
        {
            foreach (DictionaryEntry de in htFields)
            {
                this.dgView.Columns[de.Key.ToString()].HeaderText = de.Value.ToString();
            }
            if (SelectOption)
            {
                for (int i = 0; i < dgView.Rows.Count; i++)
                {
                    DataRow dr;
                    if (DataTableContainsDataRow(dtSelect, dtData.Rows[i], out dr))
                    {
                        this.dgView.Rows[i].Cells[0].Value = true;
                    }

                }
            }



        }

        private void BindGridView(int pageIndex)
        {
            BindCount++;

           
            int RecordCount, PageCount;
            DataParameter[] paras = new DataParameter[] { 
                                                            new DataParameter("{0}",queryFields),
                                                            new DataParameter("{1}",TableView),
                                                            new DataParameter("{2}",strWhere),
                                                            new DataParameter("{3}",orderBy)
                                                        };
            dtData = bll.GetDataPage("Security.SelectDataPage", pageIndex, PageSize, out RecordCount, out PageCount, paras);

        
            if (CurrentPage == 0)
                CurrentPage = PageCount;
            if (RecordCount != 0)
            {
                this.btnLast.Enabled = true;
                this.btnFirst.Enabled = true;
                this.btnToPage.Enabled = true;

                if (CurrentPage > 1)
                    this.btnPre.Enabled = true;
                else
                    this.btnPre.Enabled = false;

                if (CurrentPage < PageCount)
                    this.btnNext.Enabled = true;
                else
                    this.btnNext.Enabled = false;

                lblCurrentPage.Visible = true;
                lblCurrentPage.Text = "共 [" + RecordCount.ToString() + "] 笔记录  第 [" + CurrentPage.ToString() + "] 页  共 [" + PageCount.ToString() + "] 页";
            }
            else
            {
                this.btnFirst.Enabled = false;
                this.btnPre.Enabled = false;
                this.btnNext.Enabled = false;
                this.btnLast.Enabled = false;
                this.btnToPage.Enabled = false;
                lblCurrentPage.Visible = false;
            }
            if (BindCount == 1)
                dtSelect = dtData.Clone();
            this.dgView.DataSource = dtData.DefaultView;
            SetColumnHeadName();
            
        }
        #region Button Event
        private void btnFirst_Click(object sender, EventArgs e)
        {
            SetBtnEnabled("F");
        }

        private void btnPre_Click(object sender, EventArgs e)
        {
            SetBtnEnabled("P");
        }

        private void btnNext_Click(object sender, EventArgs e)
        {
            SetBtnEnabled("N");
        }

        private void btnLast_Click(object sender, EventArgs e)
        {
            SetBtnEnabled("L");
        }

        private void SetBtnEnabled(string movePage)
        {
            switch (movePage)
            {
                case "F":
                    CurrentPage = 1;
                    break;
                case "P":
                    CurrentPage = CurrentPage - 1;
                    break;
                case "N":
                    CurrentPage = CurrentPage + 1;
                    break;
                case "L":
                    CurrentPage = 0;
                    break;
                default:
                    CurrentPage = 1;
                    break;
            }

            BindGridView(CurrentPage);
        }

         private void btnToPage_Click(object sender, System.EventArgs e)
        {
            try
            {
                CurrentPage = int.Parse(txtPageNo.Text);
                SetBtnEnabled("");
            }
            catch
            {
                MessageBox.Show("请输入正确页码!");
                txtPageNo.Text = "";
                txtPageNo.Focus();
            }

        }
        private void txtPageNo_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnToPage_Click(sender, e);
            }

        }
        #endregion

        #region Search
        private void btnSearch_Click(object sender, System.EventArgs e)
        {
             
            strWhere = strInitialWhere;
            

            //if (this.ddlProductCategory.SelectedValue.ToString() != "")
            //{
            //    strWhere += " and CategoryCode='" + this.ddlProductCategory.SelectedValue.ToString() + "'";
            //}
            //if (this.txtProductCode.Text.Trim() != "")
            //{
            //    strWhere += " and ProductCode like '%" + this.txtProductCode.Text + "%'";
            //}
            //if (this.txtProductName.Text.Trim() != "")
            //{
            //    strWhere += " and ProductName like '%" + this.txtProductName.Text + "%'";
            //}
            //if (this.txtModelNo.Text.Trim() != "")
            //{
            //    strWhere += " and ModelNo like '%" + this.txtModelNo.Text + "%'";
            //}
            SetBtnEnabled("F");

            
        }

       
        
        #endregion

        #region DataGridView Event
        private void dgView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex == -1)
                return;
            if (e.ColumnIndex == 0 && SelectOption)
            {
                DataRow dr;
                DataGridViewCheckBoxCell CheckCell = (DataGridViewCheckBoxCell)this.dgView.Rows[e.RowIndex].Cells[0];
                if ((bool)CheckCell.EditedFormattedValue)
                {
                    //dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                    this.dgView.Rows[e.RowIndex].Cells[0].Value = false;
                    if (DataTableContainsDataRow(dtSelect, dtData.Rows[e.RowIndex], out dr))
                    {
                        dtSelect.Rows.Remove(dr);
                    }
                    //if (dtSelect.Rows.IndexOf(dtData.Rows[e.RowIndex]) != -1)
                    //    dtSelect.Rows.Remove(dtData.Rows[e.RowIndex]);
                }
                else
                {
                    if (DataTableContainsDataRow(dtSelect, dtData.Rows[e.RowIndex], out dr))
                    {
                        dtSelect.Rows.Remove(dr);
                        this.dgView.Rows[e.RowIndex].Cells[0].Value = false;
                    }
                    else
                    {
                        dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                        this.dgView.Rows[e.RowIndex].Cells[0].Value = true;
                    }
                    
                    //if (dtSelect.Rows.IndexOf(dtData.Rows[e.RowIndex]) != -1)
                    //{
                    //    dtSelect.Rows.Remove(dtData.Rows[e.RowIndex]);
                    //    this.dgView.Rows[e.RowIndex].Cells[0].Value = false;
                    //}
                    //else
                    //{
                    //    dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                    //    this.dgView.Rows[e.RowIndex].Cells[0].Value = true;
                    //}
                   
                }
            }
        }
         

        private void dgView_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex == -1)
                return;
            if (SelectOption)
            {
                DataRow dr;
                DataGridViewCheckBoxCell CheckCell = (DataGridViewCheckBoxCell)this.dgView.Rows[e.RowIndex].Cells[0];
                if ((bool)CheckCell.EditedFormattedValue)
                {
                    this.dgView.Rows[e.RowIndex].Cells[0].Value = false;
                    if (DataTableContainsDataRow(dtSelect, dtData.Rows[e.RowIndex], out dr))
                    {
                        dtSelect.Rows.Remove(dr);
                    }
                    //if (dtSelect.Rows.IndexOf(dtData.Rows[e.RowIndex]) != -1)
                    //    dtSelect.Rows.Remove(dtData.Rows[e.RowIndex]);
                }
                else
                {
                    this.dgView.Rows[e.RowIndex].Cells[0].Value = true;
                    dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                    //dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                }
            }  
            else
            {
                dtSelect.ImportRow(dtData.Rows[e.RowIndex]);
                this.DialogResult = DialogResult.OK;
            }
        }

        private bool CompareDataRow(DataRow dr1, DataRow dr2)
        {
            //行里只要有一项不一样，整个行就不一样,无需比较其它
            object val1;
            object val2;
            for (int i = 1; i < dr1.ItemArray.Length; i++)
            {
                val1 = dr1[i];
                val2 = dr2[i];
                if (!val1.Equals(val2))
                {
                    return false;
                }
            }
            return true;
        }

        private bool DataTableContainsDataRow(DataTable dt, DataRow dr,out DataRow dtRow)
        {
            bool blnValue = false;
            dtRow = null;
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                if (CompareDataRow(dt.Rows[i], dr))
                {
                    blnValue = true;
                    dtRow = dt.Rows[i];
                    break;
                }
            }
            return blnValue;
 
        }

        #endregion

        #region Button Event
         
        private void btnGetBack_Click(object sender, EventArgs e)
        {
            if (!SelectOption && this.dgView.Rows.Count > 0)
            {
                if (this.dgView.CurrentRow == null)
                {
                    MessageBox.Show("请选取资料!", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                    return;
                }
                DataRow dr = dtData.Rows[this.dgView.CurrentRow.Index];
                dtSelect.ImportRow(dr);
            }
            this.DialogResult = DialogResult.OK;
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }
        #endregion

        private void txt_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                //btnSearch.Focus();
                btnSearch_Click(sender, e);
            }
        }
    }
}
