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
        //PartSupplementResult transWMSSupplementRequest(PartSupplementRequest Request);
        PartSupplementResult transWMSSupplementRequest(PartSupplementRequest Request);
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


    public enum PartSupplementResultCode
    {
        e0000,      //补料成功，无错误
        e0001,      //仓库中缺乏指定物料
        e0002,      //立体仓库错误，从立体仓库中出料失败
        e0003,      //AGV错误，无法完成补料
        //...补充其余可能出现的错误
    }
    //[DataContract]
    public class PartSupplementResult              //在补料结束或者出现错误时返回补料结果
    {
        public int RequestID { get; set; }
        public PartSupplementResultCode ResultCode { get; set; }            //如果补料不成功，MES会过一段时间再发送同一ID的Request，直到错误解决
        public DateTime RequestResultTime { get; set; }        //只有成功完成补料，该属性才有值，否则为null
    }

}
