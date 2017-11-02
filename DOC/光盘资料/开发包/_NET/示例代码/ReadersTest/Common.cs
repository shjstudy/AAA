using System;
using System.Data;
using System.Windows.Forms;
using System.Xml;
using Invengo.NetAPI.Core;

namespace ReadersTest
{
    public class Common
    {
        public static MyReaders myReaders = new MyReaders();
        public static DataTable Dt_Display = new DataTable();
        public static XmlDocument sysit_xml = new XmlDocument();

        static Common()
        {
            sysit_xml.Load(APIPath.folderName + "\\Sysit.xml");

            #region Dt_Display
            Dt_Display.Columns.Add("ReaderName");
            Dt_Display.Columns.Add("Tag");
            Dt_Display.Columns.Add("EPC(PC)");
            Dt_Display.Columns.Add("TID/ID");
            Dt_Display.Columns.Add("UserData");
            Dt_Display.Columns.Add("Reserved");
            Dt_Display.Columns.Add("RSSI");
            Dt_Display.Columns.Add("TotalCount", Type.GetType("System.Int32"));
            Dt_Display.Columns.Add("ANT1", Type.GetType("System.Int32"));
            Dt_Display.Columns.Add("ANT2", Type.GetType("System.Int32"));
            Dt_Display.Columns.Add("ANT3", Type.GetType("System.Int32"));
            Dt_Display.Columns.Add("ANT4", Type.GetType("System.Int32"));
            Dt_Display.Columns.Add("ReadTime");
            #endregion
        }

        public static bool IsIP(String ip)
        {
            //判断是否为IP
            return System.Text.RegularExpressions.Regex.IsMatch(ip, @"^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$");
        }

        /// <summary>
        /// 获取读标签时间字符串
        /// </summary>
        public static String ReadTimeToString(Byte[] readTime)
        {
            String str = "";
            if (readTime == null)
                return "";
            if (readTime.Length == 6)
            {
                str = "20" + readTime[0].ToString("X2") + "-"
                    + readTime[1].ToString("X2") + "-"
                    + readTime[2].ToString("X2") + " "
                    + readTime[3].ToString("X2") + ":"
                    + readTime[4].ToString("X2") + ":"
                    + readTime[5].ToString("X2") + "(BCD)";
            }
            else if (readTime.Length == 8)
            {
                DateTime dt = DateTime.Parse("1970-01-01").
                    AddSeconds((readTime[0] << 24) + (readTime[1] << 16) + (readTime[2] << 8) + readTime[3]);
                str = dt.ToString("yyyy-MM-dd HH:mm:ss");
                UInt32 ms = ((UInt32)((readTime[4] << 24) + (readTime[5] << 16) + (readTime[6] << 8) + (readTime[7])) / 1000);
                if (ms < 1000)
                    str += "." + ms.ToString().PadLeft(3, '0') + "(UTC)";
                else
                    str = "";
            }
            return str;
        }

        public static String ReadUtcTimeFix(Byte[] epc)
        {
            try
            {
                Byte[] utc = new Byte[8];
                Array.Copy(epc, epc.Length - 8, utc, 0, 8);
                double dec = (utc[0] << 24) + (utc[1] << 16) + (utc[2] << 8) + (utc[3]);
                DateTime utcd = DateTime.Parse("1970-01-01").AddSeconds(dec);
                String mainTime = utcd.ToString("yyyy-MM-dd HH:mm:ss");
                UInt32 ms = ((UInt32)((utc[4] << 24) + (utc[5] << 16) + (utc[6] << 8) + (utc[7])) / 1000);
                if (ms > 999)
                    return "";
                String msTime = ms.ToString().PadLeft(3, '0');  //ms  
                if (msTime.Length > 3) msTime = msTime.Substring(0, 3);
                return mainTime + "." + msTime + "(UTC)";
            }
            catch
            {
                return DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff");
            }
        }

        #region
        private delegate void SetTextHandler(Control ctrl, String value);
        public static void SetText(UserControl parentCtrl, Control ctrl, String value)
        {
            //转到主线程
            if (parentCtrl.InvokeRequired)
            {
                SetTextHandler handler = new SetTextHandler(SetTextMethod);
                parentCtrl.BeginInvoke(handler, ctrl, value);
            }
            else
                SetTextMethod(ctrl, value);
        }
        public static void SetText(Form parentForm, Control ctrl, String value)
        {
            //转到主线程
            if (parentForm.InvokeRequired)
            {
                SetTextHandler handler = new SetTextHandler(SetTextMethod);
                parentForm.BeginInvoke(handler, ctrl, value);
            }
            else
                SetTextMethod(ctrl, value);
        }
        private static void SetTextMethod(Control ctrl, String value)
        {
            ctrl.Text = value;
        }

        private delegate void AppendTextHandler(Control ctrl, String value);
        public static void AppendText(UserControl parentCtrl, Control ctrl, String value)
        {
            //转到主线程
            if (parentCtrl.InvokeRequired)
            {
                AppendTextHandler handler = new AppendTextHandler(AppendTextMethod);
                parentCtrl.BeginInvoke(handler, ctrl, value);
            }
            else
                AppendTextMethod(ctrl, value);
        }
        public static void AppendText(Form parentForm, Control ctrl, String value)
        {
            //转到主线程
            if (parentForm.InvokeRequired)
            {
                AppendTextHandler handler = new AppendTextHandler(AppendTextMethod);
                parentForm.BeginInvoke(handler, ctrl, value);
            }
            else
                AppendTextMethod(ctrl, value);
        }
        private static void AppendTextMethod(Control ctrl, String value)
        {
            ctrl.Text += value;
        }

        private delegate void SetEnableHandler(Control ctrl, bool value);
        public static void SetEnable(UserControl parentCtrl, Control ctrl, bool value)
        {
            //转到主线程
            if (parentCtrl.InvokeRequired)
            {
                SetEnableHandler handler = new SetEnableHandler(SetEnableMethod);
                parentCtrl.BeginInvoke(handler, ctrl, value);
            }
            else
                SetEnableMethod(ctrl, value);
        }
        public static void SetEnable(Form parentForm, Control ctrl, bool value)
        {
            //转到主线程
            if (parentForm.InvokeRequired)
            {
                SetEnableHandler handler = new SetEnableHandler(SetEnableMethod);
                parentForm.BeginInvoke(handler, ctrl, value);
            }
            else
                SetEnableMethod(ctrl, value);
        }
        private static void SetEnableMethod(Control ctrl, bool value)
        {
            ctrl.Enabled = value;
        }
        #endregion
    }
}
