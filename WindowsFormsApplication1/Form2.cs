using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;
using System.Windows.Forms;
using Util;
using Newtonsoft.Json;

namespace WindowsFormsApplication1
{
    public partial class Form2 : Form
    {
        private string MesMethod = "";
        public Form2()
        {
            InitializeComponent();
        }

        private void Form2_Shown(object sender, EventArgs e)
        {
            PartSupplementRequest SupplementRequest = new PartSupplementRequest()
            {
                RequestID = 10,
                RequestPart = new PartInfo { Category = PartCategory.lid, Color=PartColor.dark},
                RequestNumber = 2,
                RequestTime =DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"),
                
            };
            this.textBox1.Text = JsonConvert.SerializeObject(SupplementRequest);
            //this.textBox1.Text = "{\"RequestID\":\"10\",\"RequestPart\":{\"Category\":\"0\",\"Color\":\"0\"},\"RequestNumber\":\"2\",\"RequestTime\":\"2017-10-10 14:34:00.652\"}";
        }

        private void button1_Click(object sender, EventArgs e)
        {

            MesMethod = "transWMSSupplementRequest";
            send(MesMethod, this.textBox1.Text);
        }

        public void send(string method, string data)
        {
            var url = "http://localhost/ServiceHost/SRMDataService.svc/" + method;
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
            var result1 = JsonConvert.DeserializeObject<ResultData>(responseContent);//—解析返回的数据
            this.txtResult.Text = result1.Result;
            streamReader.Close();
            httpWebResponse.Close();


           



        }
    }

    public enum PartColor { dark, light }
    public enum PartCategory { lid, bottle }
    public class PartInfo
    {
        public PartCategory Category { get; set; }
        public PartColor Color { get; set; }
    }
    
    public class PartSupplementRequest              //发送补料请求
    {
        public int RequestID { get; set; }          //Request和Result类中各有一个该属性，用于匹配补料请求和补料结果
        public PartInfo RequestPart { get; set; }
        public int RequestNumber { get; set; }      //补料数量指的是料盘的数量，而不是物料本身的个数
        public string RequestTime { get; set; }
    }
   
    
  
}
