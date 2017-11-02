using System;
using System.Collections.Generic;
using System.Net.NetworkInformation;
using System.Text;
using System.Xml;
using Invengo.NetAPI.Core;
using IRP1 = Invengo.NetAPI.Protocol.IRP1;
using System.Net.Sockets;

namespace ReadersTest
{
    public class MyReader
    {
        public MyReader() //TCPIP_Server
        {
            
        }
        public MyReader(String readerName)//TCPIP_Client
        {
            _reader = new IRP1.Reader(readerName);
            if (_reader.ProtocolVersion == "IRP1")
            {
                XmlNode node = Common.sysit_xml.DocumentElement.SelectSingleNode("Reader[@Name='" + _reader.ReaderName + "']");
                XmlNode sn = node.SelectSingleNode("ScanMessage");
                #region ScanMessage
                IRP1.ReadTag.ReadMemoryBank rmb = (IRP1.ReadTag.ReadMemoryBank)
                    Enum.Parse(typeof(IRP1.ReadTag.ReadMemoryBank), sn.Attributes["ReadMemoryBank"].Value);
                scanMsgParam.readMemoryBank = rmb;
                int readtime = int.Parse(sn.SelectSingleNode("ReadTime").InnerText);
                int stoptime = int.Parse(sn.SelectSingleNode("StopTime").InnerText);
                scanMsgParam.readtime = readtime;
                scanMsgParam.stoptime = stoptime;
                string readertype = "800";
                if (_reader.ModelNumber.IndexOf("502E") != -1 || _reader.ModelNumber == "XC-RF811")
                    readertype = "500";
                if (readtime > 0 || stoptime > 0)
                    ScanMessage = new IRP1.ReadTag(rmb, readtime, stoptime);
                else
                    ScanMessage = new IRP1.ReadTag(rmb);

                IRP1.ReadTag rt = (IRP1.ReadTag)ScanMessage;
                switch (rmb)
                {
                    case IRP1.ReadTag.ReadMemoryBank.EPC_6C:
                    case IRP1.ReadTag.ReadMemoryBank.EPC_6C_ID_6B:
                    case IRP1.ReadTag.ReadMemoryBank.EPC_PC_6C:
                    case IRP1.ReadTag.ReadMemoryBank.EPC_TID_UserData_6C:
                    case IRP1.ReadTag.ReadMemoryBank.EPC_TID_UserData_6C_ID_UserData_6B:
                    case IRP1.ReadTag.ReadMemoryBank.TID_6C:
                    case IRP1.ReadTag.ReadMemoryBank.TID_6C_ID_6B:
                        {
                            string[] ps = sn.SelectSingleNode("Param").InnerText.Split(',');
                            scanMsgParam.antennaIndex = byte.Parse(ps[0]);
                            rt.Antenna = getAntenna(ps[0], readertype);
                            scanMsgParam.q = rt.Q = byte.Parse(ps[1]);
                            scanMsgParam.isLoop = rt.IsLoop = ((ps[2] == "1") ? true : false);
                        }
                        break;
                    case IRP1.ReadTag.ReadMemoryBank.EPC_TID_UserData_6C_2:
                        {
                            string[] ps = sn.SelectSingleNode("Param").InnerText.Split(',');
                            scanMsgParam.antennaIndex = byte.Parse(ps[0]);
                            rt.Antenna = getAntenna(ps[0], readertype);
                            scanMsgParam.q = rt.Q = byte.Parse(ps[1]);
                            scanMsgParam.isLoop = rt.IsLoop = ((ps[2] == "1") ? true : false);
                            scanMsgParam.tidLen = rt.TidLen = byte.Parse(ps[3]);
                            scanMsgParam.userdataPtr_6C = rt.UserDataPtr_6C = uint.Parse(ps[4]);
                            scanMsgParam.userdataLen_6C = rt.UserDataLen_6C = byte.Parse(ps[5]);
                        }
                        break;
                    case IRP1.ReadTag.ReadMemoryBank.EPC_TID_UserData_Reserved_6C_ID_UserData_6B:
                        {
                            string[] ps = sn.SelectSingleNode("Param").InnerText.Split(',');
                            scanMsgParam.antennaIndex = byte.Parse(ps[0]);
                            rt.Antenna = getAntenna(ps[0], readertype);
                            scanMsgParam.q = rt.Q = byte.Parse(ps[1]);
                            scanMsgParam.isLoop = rt.IsLoop = ((ps[2] == "1") ? true : false);
                            scanMsgParam.tidLen = rt.TidLen = byte.Parse(ps[3]);
                            scanMsgParam.userdataPtr_6C = rt.UserDataPtr_6C = uint.Parse(ps[4]);
                            scanMsgParam.userdataLen_6C = rt.UserDataLen_6C = byte.Parse(ps[5]);
                            scanMsgParam.userdataPtr_6B = rt.UserDataPtr_6B = byte.Parse(ps[6]);
                            scanMsgParam.userdataLen_6B = rt.UserDataLen_6B = byte.Parse(ps[7]);
                            scanMsgParam.reservedLen = rt.ReservedLen = byte.Parse(ps[8]);
                            scanMsgParam.pwd = rt.AccessPwd = Util.ConvertHexStringToByteArray(ps[9]);
                            scanMsgParam.readTimes_6C = rt.ReadTimes_6C = byte.Parse(ps[10]);
                            scanMsgParam.readTimes_6B = rt.ReadTimes_6B = byte.Parse(ps[11]);
                        }
                        break;
                    case IRP1.ReadTag.ReadMemoryBank.ID_6B:
                    case IRP1.ReadTag.ReadMemoryBank.ID_UserData_6B:
                        {
                            string[] ps = sn.SelectSingleNode("Param").InnerText.Split(',');
                            scanMsgParam.antennaIndex = byte.Parse(ps[0]);
                            rt.Antenna = getAntenna(ps[0], readertype);
                            scanMsgParam.isLoop = rt.IsLoop = ((ps[1] == "1") ? true : false);
                        }
                        break;

                }
                #endregion
                #region StopMessage
                StopMessage = new IRP1.PowerOff();
                #endregion
            }
        }
        private IRP1.Reader _reader;
        public IRP1.Reader reader
        {
            get { return _reader; }
            set { _reader = value; }
        }
        public IMessage ScanMessage = null;
        public IMessage StopMessage = null;
        public bool IsUtcEnable_800 = false;
        public bool IsRssiEnable_800 = false;
        public ScanMsgParam scanMsgParam = new ScanMsgParam();

        public class ScanMsgParam
        {
            public IRP1.ReadTag.ReadMemoryBank readMemoryBank; 
            public byte antennaIndex = 0x00;
            public byte q = 0x03;
            public bool isLoop = true;
            public byte tidLen = 0x04;
            public UInt32 userdataPtr_6C = 0x00;
            public byte userdataLen_6C = 0x04;
            public byte userdataPtr_6B = 0x00;
            public byte userdataLen_6B = 0x04;
            public byte reservedLen = 0x04;
            public byte[] pwd = new byte[4];
            public byte readTimes_6C = 0x01;
            public byte readTimes_6B = 0x01;

            public int readtime = 0;
            public int stoptime = 0;
        }

        byte getAntenna(string a, string readertype)
        {
            byte antenna = 0x01;
            byte ab = byte.Parse(a);
            if (ab > 0x80)
                antenna = ab;
            else
            {
                if (readertype == "500")
                {
                    switch (ab)
                    {
                        case 0:
                            antenna = 1;
                            break;
                        case 1:
                            antenna = 2;
                            break;
                        case 2:
                            antenna = 0;
                            break;
                    }
                }
                else
                {
                    switch (ab)
                    {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            antenna = (byte)(ab + 1);
                            break;
                        default:
                            antenna = 0;
                            break;
                    }
                }
            }
            return antenna;
        }
    }

    public class MyReaders
    {
        public List<MyReader> ReaderList = new List<MyReader>();
        public List<TcpIpListener> ServerList = new List<TcpIpListener>();
        public delegate void OnReaderErrorMsgHandle(IRP1.Reader reader, string errString);
        public event OnReaderErrorMsgHandle OnReaderErrorMsg;
        public delegate void OnReaderNotificationMsgHandle(IRP1.Reader reader, NotificationMessage nMsg);
        public event OnReaderNotificationMsgHandle OnReaderNotificationMsg;
        public delegate void OnTagAlarmHandle(IRP1.Reader reader, EventArgs arg);
        public event OnTagAlarmHandle OnTagAlarm;
        public delegate void OnReaderClientConnHandle(MyReader myReader, EventArgs arg);
        public event OnReaderClientConnHandle OnReaderClientConn;

        public void Connect()
        {
            #region Client
            foreach (XmlNode node in Common.sysit_xml.DocumentElement.SelectNodes("Reader"))
            {
                MyReader r = new MyReader(node.Attributes["Name"].Value);               
                try
                {
                    if (!r.reader.IsConnected)
                    {
                        if (r.reader.PortType == "TCPIP_Client")
                        {
                            string ip = r.reader.ConnString.Substring(0, r.reader.ConnString.IndexOf(':'));
                            using (Ping ping = new Ping())
                            {
                                PingReply pingReply = null;
                                pingReply = ping.Send(ip, 100);
                                if (pingReply.Status != IPStatus.Success)
                                {
                                    if (OnReaderErrorMsg != null)
                                        OnReaderErrorMsg(r.reader, "网络不通");
                                    continue;
                                }
                            }
                        }
                        if (r.reader.Connect())
                        {
                            ReaderList.Add(r);
                            if (r.reader.ProtocolVersion == "IRP1")
                            {
                                #region 查询型号
                                if (r.reader.ModelNumber == "unknown")
                                {
                                    String mn = "";
                                    IRP1.SysQuery_800 msg = new IRP1.SysQuery_800(0x20);
                                    if (r.reader.Send(msg, 200))
                                    {
                                        mn = Encoding.ASCII.GetString(msg.ReceivedMessage.QueryData).Trim('\0');
                                        r.reader.ModelNumber = mn;
                                    }
                                    if (mn != "XCRF-502E-F6G" && mn != "XC-RF806")
                                    {
                                        IRP1.SysQuery_800 msg1 = new IRP1.SysQuery_800(0x21);
                                        if (r.reader.Send(msg1, 200))
                                        {
                                            mn = Encoding.ASCII.GetString(msg1.ReceivedMessage.QueryData);
                                            if (mn.IndexOf("807") != -1)
                                                r.reader.ModelNumber = "XC-RF807";
                                            else if (mn.IndexOf("806") != -1)
                                                r.reader.ModelNumber = "XC-RF806";
                                            else if (mn.IndexOf("860") != -1)
                                                r.reader.ModelNumber = "XC-RF860";                                            
                                        }
                                        else
                                            r.reader.ModelNumber = "XCRF-502E";
                                    }
                                    r.reader.ModelNumber = r.reader.ModelNumber.Trim('\0');
                                }
                                #endregion

                                #region 查询RSSI功能
                                {
                                    switch (r.reader.ModelNumber)
                                    {
                                        case "XC-RF807":
                                        case "XC-RF806":                                           
                                        case "XCRF-860":                                        
                                            IRP1.SysQuery_800 order = new IRP1.SysQuery_800(0x14);
                                        if (r.reader.Send(order, 200))
                                        {
                                            if (order.ReceivedMessage.QueryData != null && order.ReceivedMessage.QueryData.Length > 0)
                                            {
                                                if (order.ReceivedMessage.QueryData[0] == 0x01)
                                                    r.IsRssiEnable_800 = true;
                                                else
                                                    r.IsRssiEnable_800 = false;
                                            }
                                        }
                                            break;
                                        case "XCRF-502E-F6G":
                                        case "XC-RF811":
                                            r.IsRssiEnable_800 = true;
                                            break;
                                    }                                    
                                }
                                #endregion

                                #region 查询UTC功能
                                {
                                    switch (r.reader.ModelNumber)
                                    {
                                        case "XC-RF807":
                                        case "XC-RF806":
                                        case "XCRF-860":
                                            IRP1.SysQuery_800 order = new IRP1.SysQuery_800(0x18);
                                            if (r.reader.Send(order, 200))
                                            {
                                                if (order.ReceivedMessage.QueryData != null && order.ReceivedMessage.QueryData.Length > 0)
                                                {
                                                    if (order.ReceivedMessage.QueryData[0] == 0x01)
                                                        r.IsUtcEnable_800 = true;
                                                    else
                                                        r.IsUtcEnable_800 = false;
                                                }
                                            }
                                            else
                                                r.IsUtcEnable_800 = false;
                                            break;                                       
                                    }
                                }
                                #endregion

                                #region 激活天线
                                if (r.scanMsgParam.antennaIndex < 0x80)
                                {
                                    if (r.reader.ModelNumber == "XCRF-860")
                                    {
                                        byte a = 0x01;
                                        switch (r.scanMsgParam.antennaIndex)
                                        {
                                            case 0:
                                                a = 0x01;
                                                break;
                                            case 1:
                                            case 4:
                                                a = 0x02;
                                                break;
                                            case 2:
                                            case 5:
                                                a = 0x03;
                                                break;
                                            case 3:
                                            case 6:
                                                a = 0x04;
                                                break;
                                        }
                                        r.reader.Send(new IRP1.SysConfig_800(0x02, new Byte[] { a }),200);

                                    }
                                    else if (r.reader.ModelNumber == "XCRF-502E-F6G"
                                        || r.reader.ModelNumber == "XCRF-502E"
                                        || r.reader.ModelNumber == "XC-RF811")
                                    {
                                        byte a = r.scanMsgParam.antennaIndex;                                      
                                        r.reader.Send(new IRP1.SysConfig_500(0x02, 0x01, new Byte[] { a }),200);
                                    }                                
                                }
                                #endregion
                            }
                            r.reader.OnMessageNotificationReceived +=
                                new MessageNotificationReceivedHandle(r_OnMessageNotificationReceived);                            
                            if (OnReaderErrorMsg != null)
                                OnReaderErrorMsg(r.reader, "建立连接成功");
                        }
                        else
                        {
                            r.reader.Disconnect();
                            if (OnReaderErrorMsg != null)
                                OnReaderErrorMsg(r.reader, "建立连接失败");
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.Info(ex.Message);
                }
            }
            #endregion

            #region Server
            foreach (XmlNode node in Common.sysit_xml.DocumentElement.SelectNodes("Server"))
            { 
                String port = node.Attributes["Port"].Value;
                String pVer = node.Attributes["Protocol"].Value;
                TcpIpListener listener = new TcpIpListener(int.Parse(port), pVer);
                try
                {
                    listener.OnClientConn += new OnClientConnHandle(listener_OnClientConn);
                    listener.Run();
                    ServerList.Add(listener);
                    if (OnReaderErrorMsg != null)
                        OnReaderErrorMsg(null, port + "端口:" + "启动监听服务");
                }
                catch (Exception ex)
                {
                    if (OnReaderErrorMsg != null)
                        OnReaderErrorMsg(null, port + "端口:" + "启动监听服务失败");
                    Log.Info(port + "端口:" + "启动监听服务失败！" + ex.Message);
                }
            }
            #endregion
        }

        void listener_OnClientConn(Socket socket,String pVer)
        {
            if (pVer == "IRP1")
            {
                IRP1.Reader reader = new IRP1.Reader(socket);

                if (reader.Connect())
                {
                    #region 激活天线
                    reader.Send(new IRP1.SysConfig_500(0x02, 0x01, new Byte[] { 0x01 }), 200);
                    #endregion
                    if (reader.IsConnected)
                    {
                        MyReader r = new MyReader();
                        r.reader = reader;
                        if (r.reader.ProtocolVersion == "IRP1")
                        {
                            r.ScanMessage = new IRP1.ReadTag(IRP1.ReadTag.ReadMemoryBank.EPC_6C);
                            r.StopMessage = new IRP1.PowerOff();//XC-RF502E-F6G  
                        }
                        reader.OnMessageNotificationReceived += new MessageNotificationReceivedHandle(r_OnMessageNotificationReceived);
                        ReaderList.Add(r);
                        if (OnReaderClientConn != null)
                            OnReaderClientConn(r, EventArgs.Empty);
                    }
                }
            }
        }

        void r_OnMessageNotificationReceived(BaseReader reader, IMessageNotification msg)
        {
            String nowString = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");
            String utcString = nowString;
            String reserve = string.Empty;
            NotificationMessage nmsg = null;
            switch (reader.ProtocolVersion)
            {
                case "IRP1":
                    {
                        if (msg.StatusCode != 0)
                        {
                            if (OnReaderErrorMsg != null)
                            {
                                OnReaderErrorMsg((IRP1.Reader)reader, msg.ErrInfo);
                            }
                            Log.Debug(reader.ReaderName + ":" + msg.ErrInfo);
                            return;
                        }
                        String msgType = msg.GetMessageType();
                        msgType = msgType.Substring(msgType.LastIndexOf('.') + 1);
                        switch (msgType)
                        {

                            #region RXD_TagData
                            case "RXD_TagData":
                                {
                                    IRP1.RXD_TagData m = (IRP1.RXD_TagData)msg;
                                    Byte[] bRssi = m.ReceivedMessage.RSSI;
                                    string rssi = "";
                                    if (bRssi != null)
                                    {
                                        if (reader.ModelNumber.IndexOf("502E") != -1 || reader.ModelNumber.IndexOf("811") != -1)
                                        {
                                            rssi = bRssi[0].ToString("X2") + bRssi[1].ToString("X2");
                                        }
                                        else
                                        {
                                            rssi = bRssi[0].ToString("X2");
                                        }
                                    }
                                    string rxdTime = Common.ReadTimeToString(m.ReceivedMessage.RXDTime);
                                    if (rxdTime != "")
                                        nowString = rxdTime;
                                    nmsg = new NotificationMessage(
                                        m.ReceivedMessage.ReaderName,
                                        m.ReceivedMessage.TagType,
                                        Util.ConvertByteArrayToHexWordString(m.ReceivedMessage.EPC),
                                        Util.ConvertByteArrayToHexWordString(m.ReceivedMessage.TID),
                                        Util.ConvertByteArrayToHexWordString(m.ReceivedMessage.UserData),
                                        Util.ConvertByteArrayToHexWordString(m.ReceivedMessage.Reserved),                                        
                                        rssi,
                                        m.ReceivedMessage.Antenna,
                                        nowString
                                        );                                    
                                }
                                break;
                            #endregion                           
                            #region EasAlarm_6C
                            case "EasAlarm_6C":
                                {
                                    IRP1.EasAlarm_6C m = (IRP1.EasAlarm_6C)msg;
                                    if (m.ReceivedMessage.AnswerType == 0xa0)
                                    {
                                        if (OnTagAlarm != null)
                                            OnTagAlarm(null, EventArgs.Empty);
                                    }
                                }
                                break;
                            #endregion
                            #region PcSendTime_500(805)
                            case "PcSendTime_500":
                                {
                                    IRP1.PcSendTime_500 m = (IRP1.PcSendTime_500)msg;
                                    DateTime now = DateTime.Now;
                                    TimeSpan ts = now - new DateTime(1970, 1, 1);
                                    int d = (int)ts.TotalSeconds;
                                    Byte[] bytes = BitConverter.GetBytes(d);
                                    Byte[] time = new Byte[4];


                                    time[0] = bytes[3];
                                    time[1] = bytes[2];
                                    time[2] = bytes[1];
                                    time[3] = bytes[0];

                                    IRP1.PcSendTime_500 order = new IRP1.PcSendTime_500(m.ReceivedMessage.ReaderID, time);
                                    reader.Send(order);
                                }
                                break;
                            #endregion                            
                            #region RXD_IOTriggerSignal_800
                            case "RXD_IOTriggerSignal_800":
                                {
                                    IRP1.RXD_IOTriggerSignal_800 m = (IRP1.RXD_IOTriggerSignal_800)msg;
                                    if (m.ReceivedMessage.IsStart)
                                    {
                                        //TODO:
                                    }
                                    else
                                    {
                                        //TODO:
                                    }
                                }
                                break;
                            #endregion
                            #region Keepalive
                            case "Keepalive":
                                {
                                    reader.Send(new IRP1.Keepalive());
                                }
                                break;
                            #endregion
                        }
                    }
                    break;
                case "IRP2":
                    // TODO:
                    break;
            }
            if (OnReaderNotificationMsg != null)
                OnReaderNotificationMsg((IRP1.Reader)reader, nmsg);
        }

        public void Disconnect()
        {
            foreach (MyReader r in ReaderList)
            {
                try
                {
                    if (r.reader.IsConnected)
                    {
                        r.reader.OnMessageNotificationReceived -=
                                new MessageNotificationReceivedHandle(r_OnMessageNotificationReceived);
                        r.reader.Disconnect();
                        if (OnReaderErrorMsg != null)
                            OnReaderErrorMsg(r.reader, "断开连接");
                    }
                }
                catch (Exception ex)
                {
                    Log.Info(ex.Message);
                }
            }
            ReaderList.Clear();
            foreach (TcpIpListener sl in ServerList)
            {
                try
                {
                    string port = sl.ToString();
                    sl.OnClientConn -= new OnClientConnHandle(listener_OnClientConn);
                    sl.Stop();
                    
                    if (OnReaderErrorMsg != null)
                        OnReaderErrorMsg(null, sl.Port + "端口：停止服务");
                   
                }
                catch (Exception ex)
                {
                    Log.Info(ex.Message);
                }
            }
            ServerList.Clear();
        }

        public void EasScan()
        {
            foreach (MyReader r in ReaderList)
            {
                if (r.reader.IsConnected)
                {
                    if (r.reader.ModelNumber == "XC-RF807"
                        || r.reader.ModelNumber == "XC-RF806"
                        || r.reader.ModelNumber == "XCRF-860")
                    {
                        byte a = r.scanMsgParam.antennaIndex;
                        if (a < 0x80)
                        {
                            if (a > 0x04)
                                a = 0x00;
                        }
                        r.reader.Send(new IRP1.EasAlarm_6C(a, 0x01));
                    }
                }
            }
        }

        public void Scan()
        {
            foreach (MyReader r in ReaderList)
            {
                if (r.reader.IsConnected)
                    r.reader.Send(r.ScanMessage);
            }
        }

        public void Stop()
        {
            foreach (MyReader r in ReaderList)
            {
                if (r.reader.IsConnected)
                    r.reader.Send(r.StopMessage);
            }
        }

        public MyReader Find(String readerName)
        {
            foreach (MyReader r in ReaderList)
            {
                if (r == null)
                    continue;
                if (readerName == r.reader.ReaderName)
                {
                    return r;
                }
            }
            return null;
        }
    }

    public class NotificationMessage : EventArgs
    {
        string rName;
        public string ReaderName
        {
            get { return rName; }
            set { rName = value; }
        }

        string tagType;
        public string TagType
        {
            get { return tagType; }
            set { tagType = value; }
        }

        string epc;
        public string EPC
        {
            get { return epc; }
            set { epc = value; }
        }

        string tid;
        public string TID
        {
            get { return tid; }
            set { tid = value; }
        }

        string ud;
        public string Userdata
        {
            get { return ud; }
            set { ud = value; }
        }

        string reserved;
        public string Reserved
        {
            get { return reserved; }
            set { reserved = value; }
        }

        string rssi;
        public string RSSI
        {
            get { return rssi; }
            set { rssi = value; }
        }

        byte ant;
        public byte Antenna
        {
            get { return ant; }
            set { ant = value; }
        }
        
        string nowString;
        public string ReadTime
        {
            get { return nowString; }
            set { nowString = value; }
        }

        public NotificationMessage(
            string rName,
            string tagType,
            string epc,
            string tid,
            string ud,
            string reserved,
            string rssi,
            byte ant,            
            string nowString)
        {
            this.rName = rName;
            this.tagType = tagType;
            this.epc = epc;
            this.tid = tid;
            this.ud = ud;
            this.reserved = reserved;
            this.rssi = rssi;
            this.ant = ant;            
            this.nowString = nowString;
        }
    }
}
