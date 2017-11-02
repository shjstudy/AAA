using System;
using System.Collections.Generic;
using System.Text;
using System.Net.NetworkInformation;
using Core = Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;
using System.Threading;

namespace WindowsFormsApplication1
{
    public delegate void RFIDDisplayHandle(IRP1.RXD_TagData msg);
    public class RFIDRead
    {
        public  static IRP1.Reader reader = new IRP1.Reader("Reader1", "TCPIP_Client", "192.168.1.230:7086");//网口
        public  static IRP1.ReadTag scanMsg = new IRP1.ReadTag(IRP1.ReadTag.ReadMemoryBank.EPC_6C);//扫描消息
        public static event RFIDDisplayHandle OnRFIDDisplay;

        public RFIDRead()
        {

        }

        public static bool Connect()
        {
            bool blnvalue = false;
            if (reader.Connect())
            {
                //注册接收读写器消息事件
                reader.OnMessageNotificationReceived += new Invengo.NetAPI.Core.MessageNotificationReceivedHandle(reader_OnMessageNotificationReceived);
                blnvalue = true;
            }
            return blnvalue;
        }
        static void reader_OnMessageNotificationReceived(Invengo.NetAPI.Core.BaseReader reader, Invengo.NetAPI.Core.IMessageNotification msg)
        {
            if (msg.StatusCode != 0)
            {
                //显示错误信息
                throw (new Exception(msg.ErrInfo));
            }
            String msgType = msg.GetMessageType();
            msgType = msgType.Substring(msgType.LastIndexOf('.') + 1);
            switch (msgType)
            {
                #region RXD_TagData
                case "RXD_TagData":
                    {
                        IRP1.RXD_TagData m = (IRP1.RXD_TagData)msg;
                        if (OnRFIDDisplay != null)
                            OnRFIDDisplay(m);
                    }
                    break;
                #endregion
                #region RXD_IOTriggerSignal_800
                case "RXD_IOTriggerSignal_800":
                    {
                        IRP1.RXD_IOTriggerSignal_800 m = (IRP1.RXD_IOTriggerSignal_800)msg;
                        if (m.ReceivedMessage.IsStart)
                        {
                        }
                        else
                        {
                        }
                    }
                    break;
                #endregion
            }
        }
        public static void Scan()
        {
            if (reader != null && reader.IsConnected)
            {
                if (!reader.Send(scanMsg))
                {
                    throw (new Exception("开启扫描不成功!"));
                }
            }
        }
        public static void StopScan()
        {
            if (reader != null && reader.IsConnected)
            {
                if (!reader.Send(new IRP1.PowerOff()))//发送关功放消息
                {
                    throw (new Exception("停止扫描不成功!"));
                }
            }
        }
        public static void Close()
        {
            if (reader != null)
            {
                reader.OnMessageNotificationReceived -= new Invengo.NetAPI.Core.MessageNotificationReceivedHandle(reader_OnMessageNotificationReceived);
                reader.Disconnect();
            }
        }
    }
}
