using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Util;
using DataGridViewAutoFilter;
using MCP;

namespace App.Account
{
    public partial class frmGroupManage :View.BaseForm
    {
        BLL.BLLBase bll = new BLL.BLLBase();
        private string GroupID;
        private string GroupName;
        private bool blnBindCheck = false;

        public frmGroupManage()
        {
            InitializeComponent();
        }

        private void frmUserList_Load(object sender, EventArgs e)
        {
            bll.ExecNonQuery("Security.InsertGroupOperationList");
            colbtn.Name = "Detail";
            colbtn.DefaultCellStyle.NullValue = "删除用户";
            InitSmartTree();

            DataTable dt = Program.dtUserPermission;
            //用户资料
            string filter = "SubModuleCode='MNU_W00C_00E' and OperatorCode='1'";
            DataRow[] drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.toolStripButton_AddUser.Visible = false;
            else
                this.toolStripButton_AddUser.Visible = true;
            filter = "SubModuleCode='MNU_W00C_00E' and OperatorCode='2'";
            drs = dt.Select(filter);
            if (drs.Length <= 0)
                this.dgvGroupUser.Columns[2].Visible = false;
            else
                this.dgvGroupUser.Columns[2].Visible = true;
        }
        private void frmUserList_Activated(object sender, EventArgs e)
        {
            this.BindData();
        }

        private void BindData()
        {
            DataTable dt = bll.FillDataTable("Security.SelectGroup");
            bsMain.DataSource = dt;
        }
        private void BindDataSub()
        {
            DataTable dtSub = bll.FillDataTable("Security.SelectGroupUser", new DataParameter[] { new DataParameter("@GroupID", GroupID) });

            bsSub.DataSource = dtSub;
        }

        private void dgvMain_RowEnter(object sender, DataGridViewCellEventArgs e)
        {
             GroupID = this.dgvMain.Rows[e.RowIndex].Cells["colID"].Value.ToString();
             GroupName = this.dgvMain.Rows[e.RowIndex].Cells["colGroupName"].Value.ToString();
             //this.lblGroupName.Text = "用户组 " + this.dgvMain.Rows[e.RowIndex].Cells["colGroupName"].Value.ToString() + " 权限设置";
             if (GroupName == "admin")
                 this.toolStripButton_Save.Enabled = false;
             else
             {
                 DataTable dt = Program.dtUserPermission;
                 //用户资料
                 string filter = "SubModuleCode='MNU_W00C_00E' and OperatorCode='4'";
                 DataRow[] drs = dt.Select(filter);
                 if (drs.Length <= 0)
                     this.toolStripButton_Save.Enabled = false;
                 else
                     this.toolStripButton_Save.Enabled = true;
                  
             }
             blnBindCheck = true;
             GroupOperationBind();
             blnBindCheck = false;
             BindDataSub();
        }

       
        private void dgvGroupUser_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 2)
            {
                if ((this.dgvGroupUser.Rows[e.RowIndex].DataBoundItem as DataRowView).Row["UserName"].ToString() != "admin")
                {
                    bll.ExecNonQuery("Security.UpdateUserGroup", new DataParameter[] { new DataParameter("@GroupID", 0), new DataParameter("{0}", (this.dgvGroupUser.Rows[e.RowIndex].DataBoundItem as DataRowView).Row["UserID"].ToString()) });
                    BindDataSub();
                }
                else
                {
                    Logger.Info("用户组不能删除管理员账号!");
                }
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void InitSmartTree()
        {
            this.treeView1.Nodes.Clear();
           
            DataTable dtModules = bll.FillDataTable("Security.SelectSystemModules", new DataParameter[] { new DataParameter("@SystemName", "WCS") });
            DataTable dtSubModules = bll.FillDataTable("Security.SelectSystemSubModules", new DataParameter[] { new DataParameter("@SystemName", "WCS") });
            DataTable dtOperations = bll.FillDataTable("Security.SelectSystemOperations", new DataParameter[] { new DataParameter("@SystemName", "WCS") });
            foreach (DataRow dr in dtModules.Rows)
            {
                TreeNode tnRoot = new TreeNode();
                tnRoot.Name = dr["ModuleCode"].ToString().Trim();
                tnRoot.Text = dr["MenuTitle"].ToString();
              
                this.treeView1.Nodes.Add(tnRoot);
            }
            //为第一级菜单增加子级菜单


            if (dtModules.Rows.Count > 0)
            {
                foreach (DataRow drSub in dtSubModules.Rows)
                {
                    for (int i = 0; i < treeView1.Nodes.Count; i++)
                    {
                        if (treeView1.Nodes[i].Name == drSub["ModuleCode"].ToString().Trim())
                        {
                            TreeNode tnChild = new TreeNode();
                            tnChild.Name = drSub["SubModuleCode"].ToString().Trim();
                            tnChild.Text = drSub["MenuTitle"].ToString();
                            this.treeView1.Nodes[i].Nodes.Add(tnChild); 
                           
                            break;
                        }

                    }
                }
            }

            foreach (DataRow drOp in dtOperations.Rows)
            {
                for (int i = 0; i < treeView1.Nodes.Count; i++)
                {
                    for (int j = 0; j < treeView1.Nodes[i].Nodes.Count; j++)
                    {
                        if (treeView1.Nodes[i].Nodes[j].Name == drOp["SubModuleCode"].ToString().Trim())
                        {
                            TreeNode tnOp = new TreeNode();
                            tnOp.Name = drOp["ModuleID"].ToString().Trim();
                            tnOp.Text = drOp["OperatorDescription"].ToString();
                            treeView1.Nodes[i].Nodes[j].Nodes.Add(tnOp);
                        }
                    }
                }
            }
            treeView1.ExpandAll();
            treeView1.Nodes[0].EnsureVisible(); 
            

        }
        private void GroupOperationBind()
        {
            DataTable dtOP = bll.FillDataTable("Security.SelectGroupOperation", new DataParameter[] { new DataParameter("@GroupID", GroupID) });


            foreach (TreeNode tnRoot in this.treeView1.Nodes)
            {
                bool IsAllSelected = false;
                foreach (TreeNode tnSub in tnRoot.Nodes)
                {
                    bool IsSubAllSelected = false;
                    foreach (TreeNode tnOp in tnSub.Nodes)
                    {
                        tnOp.Checked = false;
                        DataRow[] drs = dtOP.Select(string.Format("ModuleID={0}", tnOp.Name));
                        if (drs.Length > 0)
                        {
                            tnOp.Checked = true;
                        }
                        
                        if (tnOp.Checked)
                        {
                            IsSubAllSelected = true;
                        }
                         
                    }
                    if (IsSubAllSelected)
                    {
                        tnSub.Checked = true;
                        IsAllSelected = true;
                    }
                    else
                    {
                        tnSub.Checked = false;
                    }
                }
                if (IsAllSelected)
                {
                    tnRoot.Checked = true;
                }
                else
                {
                    tnRoot.Checked = false;
                }
            }
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
         
            bll.ExecNonQuery("Security.DeleteGroupOperation", new DataParameter[] { new DataParameter("@GroupID", GroupID), new DataParameter("@SystemName", "WCS") });

            foreach (TreeNode tnRoot in this.treeView1.Nodes)
            {
                foreach (TreeNode tnSub in tnRoot.Nodes)
                {
                    foreach (TreeNode tnOp in tnSub.Nodes)
                    {
                        if (tnOp.Checked)
                        {
                            string ModuleID = tnOp.Name;
                            bll.ExecNonQuery("Security.InsertGroupOperation", new DataParameter[] { new DataParameter("@GroupID", GroupID), new DataParameter("@ModuleID", ModuleID) });
                        }
                    }
                }
            }
            Logger.Info("保存成功!");
        }

        private void btnAddUser_Click(object sender, EventArgs e)
        {
            frmGroupUser frm = new frmGroupUser(GroupID, GroupName);
            if (frm.ShowDialog() == DialogResult.OK)
            {
                BindDataSub();
            }
        }

        private void treeView1_AfterCheck(object sender, TreeViewEventArgs e)
        {
            if (blnBindCheck)
                return;

            try
            {
                if (e.Node.Nodes.Count > 0)
                {
                    bool NoFalse = true;
                    foreach (TreeNode tn in e.Node.Nodes)
                    {
                        if (tn.Checked == false)
                        {
                            NoFalse = false;
                        }
                    }
                    if (e.Node.Checked == true || NoFalse)
                    {
                        foreach (TreeNode tn in e.Node.Nodes)
                        {
                            if (tn.Checked != e.Node.Checked)
                            {
                                tn.Checked = e.Node.Checked;
                            }
                        }
                    }
                }
                if (e.Node.Parent != null && e.Node.Parent is TreeNode)
                {
                    bool ParentNode = true;
                    foreach (TreeNode tn in e.Node.Parent.Nodes)
                    {
                        if (tn.Checked == false)
                        {
                            ParentNode = false;
                        }
                    }
                    if (e.Node.Parent.Checked != ParentNode && (e.Node.Checked == false || e.Node.Checked == true && e.Node.Parent.Checked == false))
                    {
                        e.Node.Parent.Checked = ParentNode;
                    }
                }
            }
            catch (Exception ex)
            {

            }
        
        }
    
    }
}
