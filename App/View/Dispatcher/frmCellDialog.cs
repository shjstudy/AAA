using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Util;
using System.Reflection;

namespace App.View.Dispatcher
{
    public partial class frmCellDialog : Form
    {
        public frmCellDialog(Dictionary<string, Dictionary<string, object>> properties)
        {
            InitializeComponent();
            DictionaryPropertyGridAdapter adapter = new DictionaryPropertyGridAdapter();
            foreach (string key in properties.Keys)
            {
                adapter.Add(key, properties[key], true);
            }
            pgCell.SelectedObject = adapter;
        }
    }
}