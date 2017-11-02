using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace WindowsFormsApplication1
{
    public class JsonTools
    {
        // 从一个对象信息生成Json串
        public static string ObjectToJson(object obj)
        {
            return Newtonsoft.Json.JsonConvert.SerializeObject(obj);
        }
        // 从一个Json串生成对象信息
        public static object JsonToObject(string jsonString, object obj)
        {
            return Newtonsoft.Json.JsonConvert.DeserializeObject(jsonString, obj.GetType());
        }

    }
}
