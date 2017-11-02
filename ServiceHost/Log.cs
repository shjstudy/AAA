using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ServiceHost
{
    public static class Log
    {
        public static void WriteToLog(string Flag, string Method, string Msg)
        {
            string Folder = "WMS";
            if (Flag == "2")

                Folder = "WCS";
            string path = System.AppDomain.CurrentDomain.BaseDirectory + @"\" + Folder;

            if (!System.IO.Directory.Exists(path))
                System.IO.Directory.CreateDirectory(path);
            path = path + @"\" + DateTime.Now.ToString("yyyyMMdd") + ".txt";
            System.IO.File.AppendAllText(path, string.Format("{0} , {1} :  {2}", DateTime.Now, Method, Msg + "\r\n"));
        }
    }
}
