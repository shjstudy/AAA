using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Data;
using Util;
using System.ServiceModel.Activation;
using System.IO;
using System.Runtime.Serialization.Json;
using System.ServiceModel.Web;


namespace ServiceHost
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "SRMDataService" in code, svc and config file together.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)] 
    public class SRMDataService : ISRMDataService
    {
        public PartSupplementResult transWMSSupplementRequest(PartSupplementRequest Request)
        {

            string RecMessage = "{\"RequestID\":\"" + Request.RequestID + "\",\"Category\":\"" + Request.RequestPart.Category.ToString() + "\",\"Color\":\"" + Request.RequestPart.Color.ToString() + "\",\"RequestNumber\":\"" + Request.RequestNumber + "\",\"RequestTime\":\"" + Request.RequestTime + "\"}";
            //string RecMessage = "{\"RequestID\":\"" + Request.RequestID + "\",\"RequestNumber\":\"" + Request.RequestNumber + "\",\"RequestTime\":\"" + Request.RequestTime + "\"}";
            Log.WriteToLog("0", "transWMSSupplementRequest--Rec", RecMessage);
            string rtnMessage = "";

            PartSupplementResult PsRtn = new PartSupplementResult();
            PsRtn.RequestID = Request.RequestID;
            PsRtn.RequestResultTime = System.DateTime.Now;
            try
            {
                BLL.BLLBase bll = new BLL.BLLBase();
                DataParameter[] paras ={new DataParameter("@RequestID",Request.RequestID),
                                        new DataParameter("@Category",Request.RequestPart.Category.ToString()),
                                        new DataParameter("@Color",Request.RequestPart.Color.ToString()),
                                        new DataParameter("@RequestNumber",Request.RequestNumber)};


                DataTable dtTask = bll.FillDataTable("WCS.Sp_ImportWmsRequest", paras);

                if (dtTask.Rows.Count > 0)
                {

                    PsRtn.ResultCode = PartSupplementResultCode.e0000;
                    
                    rtnMessage = "{\"id\":\"" + Request.RequestID + "\",\"Result\":\"OK\"" + ",\"Code\":\"ErrorCode.OK\",\"finishDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\"}";
                }
                else
                {
                    PsRtn.ResultCode = PartSupplementResultCode.e0001;
                    rtnMessage = "{\"id\":\"" + Request.RequestID + "\",\"Result\":\"NA\"" + ",\"Code\":\"ErrorCode.WrongParameter\",\"finishDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\"}";

                }
            }
            catch (Exception ex)
            {
                PsRtn.ResultCode = PartSupplementResultCode.e0002;
                rtnMessage = "{\"id\":\"" + Request.RequestID + "\",\"Result\":\"NA\"" + ",\"Code\":\""+ex.Message +"\",\"finishDate\":\"" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff") + "\"}";
            }
            Log.WriteToLog("1", "transWMSSupplementRequest-Rtn", rtnMessage);
            return PsRtn;
            //return rtnMessage;
            
        }

       
        private string List2Json<T>(List<T> list)
        {
            DataContractJsonSerializer ser = new DataContractJsonSerializer(list.GetType());
            string Json = "";
            //序列化
            using (MemoryStream stream = new MemoryStream())
            {
                ser.WriteObject(stream, list);
                Json = Encoding.UTF8.GetString(stream.ToArray());
            }
            return Json;
        }
    }
}
