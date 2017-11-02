using System;
using System.Collections.Generic;
using System.Text;
using System.Net.NetworkInformation;
using Core = Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;
using System.Threading;
using MCP;

namespace App
{
    public delegate void RFIDDisplayHandle(IRP1.RXD_TagData msg);
    public class RFIDRead
    {
        public static IRP1.Reader reader;//网口
        public static IRP1.ReadTag scanMsg ;//扫描消息
        public static event RFIDDisplayHandle OnRFIDDisplay;

        public static bool isTryReconnNet;
        public static int tryReconnNetTimeSpan;


        public static void InitRFID()
        {
            MCP.Config.Configuration conf = new MCP.Config.Configuration();
            conf.Load("Config.xml");
            string RFIDConnetIP = conf.Attributes["RFIDConnetIP"];
            string Antenna = conf.Attributes["Antenna"];
            string Q = conf.Attributes["Q"];
            string IsLoop = conf.Attributes["IsLoop"];
            isTryReconnNet = conf.Attributes["isTryReconnNet"] == "1" ? true : false;
            tryReconnNetTimeSpan = int.Parse(conf.Attributes["tryReconnNetTimeSpan"]);
            reader = new IRP1.Reader("Reader1", "TCPIP_Client", RFIDConnetIP);
            IRP1.Reader.OnApiException += new Core.ApiExceptionHandle(Reader_OnApiException);

            scanMsg = new IRP1.ReadTag(IRP1.ReadTag.ReadMemoryBank.EPC_TID_UserData_6C);
            scanMsg.Q = byte.Parse(Q);
            scanMsg.IsLoop = ((IsLoop == "1") ? true : false);
            scanMsg.Antenna = byte.Parse(Antenna);

        }

        private static void Reader_OnApiException(Core.ErrInfo e)
        {
            if (e.Ei.ErrCode == "FF22")
            {
                Logger.Error(e.Ei.ErrMsg);
                if (isTryReconnNet)
                    ReConn();
            }
            else if (e.Ei.ErrCode == "FF24")//发现连接作废,不作断网恢复尝试
            {
                Logger.Error(e.Ei.ErrMsg);
                isTryReconnNet = false;
            }
        }
        private static void ReConn()
        {
            bool isSuc = false;
            using (Ping ping = new Ping())
            {
                for (int i = 0; i < tryReconnNetTimeSpan * 60; i++)
                {
                    PingReply pingReply = null;
                    try
                    {
                        pingReply = ping.Send(reader.ConnString.Substring(0,
                            reader.ConnString.IndexOf(':')), 1000);//超时为1秒

                        if (pingReply.Status != IPStatus.Success)
                        {
                            Logger.Error("Ping 不通");
                            continue;
                        }
                        else
                        {
                            isSuc = true;
                            break;
                        }
                    }
                    catch (Exception ex)
                    {
                        Logger.Error("尝试自动恢复连接失败！" + ex.Message);
                        return;
                    }
                }
            }
            //建立连接
            if (isSuc)
            {
                isSuc = false;
                for (int i = 0; i < 3; i++)//尝试3次
                {
                    if (reader.Connect())
                    {
                        Logger.Error("尝试自动恢复连接成功！");
                        isSuc = true;
                        break;
                    }
                    else
                    {
                        Thread.Sleep(2000);
                        continue;
                    }
                }
                if (!isSuc)
                    Logger.Error("尝试自动恢复连接失败！");
            }
        }


        public static bool Connect()
        {
            bool blnvalue = false;
            if (reader.Connect())
            {
                //注册接收读写器消息事件
                reader.OnMessageNotificationReceived += new Invengo.NetAPI.Core.MessageNotificationReceivedHandle(reader_OnMessageNotificationReceived);
                Logger.Info("RFID连接成功.");
                blnvalue = true;
            }
            else
            {
                Logger.Error("读写器Reader:建立连接失败!");
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
