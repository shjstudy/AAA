using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MCP;

namespace App.View
{
    public partial class BaseForm : Form
    {
        private Context context;
        public BaseForm()
        {
            InitializeComponent();
        }
        
        public Context Context
        {
            get
            {
                return this.context;
            }
            set
            {
                this.context = value;
            }
        }
        private void BaseForm_Activated(object sender, EventArgs e)
        {
            if (this.DesignMode)
                return;

            if (!Program.mainForm.IsActiveTab)
                Program.mainForm.SetActiveTab(this.Handle.ToString(), true);
        }

        private void BaseForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (!Program.mainForm.IsActiveTab)
                Program.mainForm.SetActiveTab(this.Handle.ToString(), false);
        }
    }
}
