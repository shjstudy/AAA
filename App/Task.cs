using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;

namespace App
{
    public delegate void TaskEventHandler(TaskEventArgs args);
    public class TaskEventArgs
    {
        private DataTable datatable;

        public DataTable datatTable 
        {
            get
            {
                return datatable;
            }
        }        

        public TaskEventArgs(DataTable dt)
        {
            this.datatable = dt;
        }
    }
    public class MainData
    {
        public static event TaskEventHandler OnTask = null;

        public static void TaskInfo(DataTable dt)
        {
            if (OnTask != null)
            {
                OnTask(new TaskEventArgs(dt));
            }
        }
    }

}
