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
using App.Dispatching.Process;


namespace App.View.Param
{
    public partial class Form1 : BaseForm
    {
        private string MesMethod = "";
        
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Shown(object sender, EventArgs e)
        {
            ViewStorageBin StorageBin = new ViewStorageBin() { Operation = "minus", ID = 2, PlateID = 2, Color = "dark", Type = "lid", Number = 12 };
            this.textBox1.Text = JsonConvert.SerializeObject(StorageBin);

            ViewStackerState StackerState = new ViewStackerState
            {
                PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"),
                StackerPositon = "AAAA",
                StackerWalkingCurrent = 1.1,
                StackerWalkingSpeed = 2.2,
                StackerLiftingCurrent = 3.3,
                StackerLiftingSpeed = 4.4,
                StackerStackingCurrent = 5.5,
                StackerStackingSpeed = 6.6
            };
            this.textBox2.Text = JsonConvert.SerializeObject(StackerState);

            ViewStackerAlarmInfo ViewStackerAlarm = new ViewStackerAlarmInfo { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AlarmType = 1, AlarmInfo = "取货后无货" };
            this.textBox3.Text = JsonConvert.SerializeObject(ViewStackerAlarm);


            ViewAGVState AGVState = new ViewAGVState { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AGVPosition = 1, AGVPower = 2 };
            this.textBox4.Text = JsonConvert.SerializeObject(AGVState);

            ViewStackerAlarmInfo ViewAGVAlarm = new ViewStackerAlarmInfo { PickTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff"), AlarmType = 1, AlarmInfo = "行走异常" };
            this.textBox5.Text = JsonConvert.SerializeObject(ViewAGVAlarm);


            PartSupplementResult PartSupplement = new PartSupplementResult { RequestID = 10, ResultCode = PartSupplementResultCode.e0000, RequestResultTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") };
            this.textBox6.Text = JsonConvert.SerializeObject(PartSupplement);
        }
       
        private void button1_Click(object sender, EventArgs e)
        {
            MesMethod = "UpdateBinInfo";
            send(MesMethod, this.textBox1.Text);
           
        }

        private void button2_Click(object sender, EventArgs e)
        {
            MesMethod = "UpdateStackerState";
            send(MesMethod, this.textBox2.Text);
           
        }

        private void button3_Click(object sender, EventArgs e)
        {
            MesMethod = "UpdateStackerAlarmInfo";
            send(MesMethod, this.textBox3.Text);
           
        }
      

        private void button4_Click(object sender, EventArgs e)
        {
            MesMethod = "UpdateAGVState";
            send(MesMethod, this.textBox4.Text);
           

        }

        private void button5_Click(object sender, EventArgs e)
        {
            MesMethod = "UpdateAGVAlarmInfo";
            send(MesMethod, this.textBox5.Text);
           
        }

        private void button6_Click(object sender, EventArgs e)
        {
            MesMethod = "";
            send(MesMethod, this.textBox6.Text);
           
        }

        public void send(string method, string data)
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
            var result1 = JsonConvert.DeserializeObject<ResultData>(responseContent);//—解析返回的数据
            this.txtResult.Text = result1.Result;
            streamReader.Close();
            httpWebResponse.Close();
        }

       
       
        
    }
  
}
