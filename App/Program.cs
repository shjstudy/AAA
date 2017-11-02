using System;
using System.Data;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using System.Net;
using System.IO;
using System.Text;
using Newtonsoft.Json;

namespace App
{
    static class Program
    {
        public static Main mainForm;
        public static DataTable dtUserPermission;
        public static string WarehouseCode;
        public static string WcsUrl;
        public static int SendInterval;
        public static int RequireAPReady;
        public static string CurrentUser;
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            mainForm = new Main();
            
            bool ExisFlag = false;
            System.Diagnostics.Process currentProccess = System.Diagnostics.Process.GetCurrentProcess();
            System.Diagnostics.Process[] currentProccessArray = System.Diagnostics.Process.GetProcesses();
            foreach (System.Diagnostics.Process p in currentProccessArray)
            {
                if (p.ProcessName == currentProccess.ProcessName && p.Id != currentProccess.Id)
                {
                    ExisFlag = true;
                    break;
                }
            }

            if (ExisFlag)
            {
                MessageBox.Show("仓储调度监控系统！", Application.ProductName, MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            else
            {
                Account.frmLogin myLogin = new Account.frmLogin();

                if (myLogin.ShowDialog() == DialogResult.OK)
                {
                   
                    MCP.Config.Configuration conf = new MCP.Config.Configuration();
                    conf.Load("Config.xml");
                    WcsUrl = conf.Attributes["WcsUrl"];
                    SendInterval = int.Parse(conf.Attributes["SendInterval"]);
                  
                    Application.Run(mainForm);
                }
            }
        }
        public static string send(string method, string data)
        {
            try
            {
                var url = Program.WcsUrl + method;
                HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Method = "POST";
                httpWebRequest.Timeout = 1000000;
                httpWebRequest.KeepAlive = false;

                byte[] buff = Encoding.UTF8.GetBytes(data);
                httpWebRequest.ContentLength = buff.Length;
                Stream httpRequestStream = httpWebRequest.GetRequestStream();
                httpRequestStream.Write(buff, 0, buff.Length);
                httpRequestStream.Close();
                HttpWebResponse httpWebResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                StreamReader streamReader = new StreamReader(httpWebResponse.GetResponseStream());
                string responseContent = streamReader.ReadToEnd();
                streamReader.Close();
                httpWebResponse.Close();
                return responseContent;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public static void WebPost()
        { 
            //var url = "http://localhost:63045/api/storage/UpdateBinInfo";
            //HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            //httpWebRequest.ContentType = "application/json";
            //httpWebRequest.Method = "POST";
            //httpWebRequest.Timeout = 1000000;
            //httpWebRequest.KeepAlive = false;
            //var data = new ViewStorageBin() { Operation = "minus", ID = 2, Color = "light", Type = "lid", Number = 3 };//将库位2的零件数量减3个
            //string jsonData = JsonConvert.SerializeObject(data);
            //byte[] buff = Encoding.UTF8.GetBytes(jsonData);
            //httpWebRequest.ContentLength = buff.Length;
            //Stream httpRequestStream = httpWebRequest.GetRequestStream();
            //httpRequestStream.Write(buff, 0, buff.Length);
            //httpRequestStream.Close();
            //HttpWebResponse httpWebResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            //StreamReader streamReader = new StreamReader(httpWebResponse.GetResponseStream());
            //string responseContent = streamReader.ReadToEnd();
            //var result1 = JsonConvert.DeserializeObject<ResultData>(responseContent);//—解析返回的数据
            
            //streamReader.Close();
            //httpWebResponse.Close();
        }


    }
}
