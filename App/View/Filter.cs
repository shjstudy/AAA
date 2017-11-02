using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace App.View
{
    public class Filter
    {
        public static void EnableFilter(DataGridView gridView)
        {
            if (gridView.DataSource is BindingSource)
                ((BindingSource)gridView.DataSource).Filter = "";

            foreach (DataGridViewColumn column in gridView.Columns)
            {
                if (column is DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn)
                    ((DataGridViewAutoFilter.DataGridViewAutoFilterTextBoxColumn)column).FilteringEnabled = true;
            }
        }
    }
}
