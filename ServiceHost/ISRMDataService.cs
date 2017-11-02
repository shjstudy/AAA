using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.IO;

namespace ServiceHost
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ISRMDataService" in both code and config file together.
    [ServiceContract]
    public interface ISRMDataService
    {
        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "transWMSSupplementRequest", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json, BodyStyle = WebMessageBodyStyle.Bare)]
        //string transWCSTask(string taskData);
        ResultData transWMSSupplementRequest(PartSupplementRequest Request);
    }
   
    public enum PartColor { dark, light }
    public enum PartCategory { lid, bottle }

    [DataContract]
    public class PartInfo
    {
        [DataMember]
        public PartCategory Category { get; set; }
        [DataMember]
        public PartColor Color { get; set; }
    }
    [DataContract]
    public class PartSupplementRequest              //发送补料请求
    {
        [DataMember]
        public int RequestID { get; set; }          //Request和Result类中各有一个该属性，用于匹配补料请求和补料结果
        [DataMember]
        public PartInfo RequestPart { get; set; }
        [DataMember]
        public int RequestNumber { get; set; }      //补料数量指的是料盘的数量，而不是物料本身的个数
        [DataMember]
        public string RequestTime { get; set; }
    }
    
    [DataContract]
    public class ResultData
    {
        [DataMember]
        public string Result { get; set; }
        [DataMember]
        public ErrorCode Code { get; set; }
    }
    public enum ErrorCode
    {
        OK = 0,
        UserNotLogined,
        WrongParameter,
        Exception
    }
}
