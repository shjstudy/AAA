using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace WindowsFormsApplication1
{
    public enum HttpVerb
    {
        GET,            //method  常用的就这几样，当然你也可以添加其他的   get：获取    post：修改    put：写入    delete：删除  
        POST,
        PUT,
        DELETE
    }

    public class RestClient
    {

        public string EndPoint { get; set; }    //请求的url地址  eg：   http://215.23.12.45:8080/order/order_id=1&isdel=0  
        public HttpVerb Method { get; set; }    //请求的方法  
        public string ContentType { get; set; } //格式类型：我用的是application/json，text/xml具体使用什么，看需求吧  
        public string PostData { get; set; }    //传送的数据，当然了我使用的是json字符串  

        public RestClient()
        {
            EndPoint = "";
            Method = HttpVerb.GET;
            ContentType = "application/json";
            PostData = "";
        }
        public RestClient(string endpoint)
        {
            EndPoint = endpoint;
            Method = HttpVerb.GET;
            ContentType = "application/json";
            PostData = "";
        }
        public RestClient(string endpoint, HttpVerb method)
        {
            EndPoint = endpoint;
            Method = method;
            ContentType = "application/json";
            PostData = "";
        }

        public RestClient(string endpoint, HttpVerb method, string postData)
        {
            EndPoint = endpoint;
            Method = method;
            ContentType = "application/json";
            PostData = postData;
        }

        public string MakeRequest()
        {
            return MakeRequest("");
        }

        public string MakeRequest(string parameters)
        {
            var request = (HttpWebRequest)WebRequest.Create(EndPoint + parameters);

            request.Method = Method.ToString();
            request.ContentLength = 0;
            request.ContentType = ContentType;

            if (!string.IsNullOrEmpty(PostData) && Method == HttpVerb.POST)//如果传送的数据不为空，并且方法是post  
            {
                var encoding = new UTF8Encoding();
                var bytes = Encoding.GetEncoding("UTF-8").GetBytes(PostData);//编码方式按自己需求进行更改，我在项目中使用的是UTF-8  
                request.ContentLength = bytes.Length;

                using (var writeStream = request.GetRequestStream())
                {
                    writeStream.Write(bytes, 0, bytes.Length);
                }
            }

            if (!string.IsNullOrEmpty(PostData) && Method == HttpVerb.PUT)//如果传送的数据不为空，并且方法是put  
            {
                var encoding = new UTF8Encoding();
                var bytes = Encoding.GetEncoding("iso-8859-1").GetBytes(PostData);//编码方式按自己需求进行更改，我在项目中使用的是UTF-8  
                request.ContentLength = bytes.Length;

                using (var writeStream = request.GetRequestStream())
                {
                    writeStream.Write(bytes, 0, bytes.Length);
                }
            }
            //HttpWebResponse response;
            //try
            //{
            //    response = (HttpWebResponse)request.GetResponse();
            //}
            //catch (WebException ex)
            //{
            //    response = (HttpWebResponse)ex.Response;
            //}

            using (var response = (HttpWebResponse)request.GetResponse())
            {
                var responseValue = string.Empty;

                if (response.StatusCode != HttpStatusCode.OK)
                {
                    var message = String.Format("Request failed. Received HTTP {0}", response.StatusCode);
                    throw new ApplicationException(message);
                }

                // grab the response  
                using (var responseStream = response.GetResponseStream())
                {
                    if (responseStream != null)
                        using (var reader = new StreamReader(responseStream))
                        {
                            responseValue = reader.ReadToEnd();
                        }
                }

                return responseValue;
            }

        }
    }
}
//1，基本的调用：
//01.var client = new RestClient();  
//02.string endPoint = @"http:\\myRestService.com\api\";  
//03.var client = new RestClient(endPoint);  
//04.var json = client.MakeRequest();


//2，如果你想带入参数

//01.var json = client.MakeRequest("?param=0"); 

// 3，使用最多的方式 
//01.var client = new RestClient();  
//02.client.EndPoint = @"http:\\myRestService.com\api\"; ;  
//03.client.ContentType = "application/json";  
//04.client.Method = HttpVerb.POST;  
//05.client.PostData = "{postData: value}";  
//06.var json = client.MakeRequest(); 

//一般情况，MakeRequest返回的json格式的字符串（不排除有的接口开发商返回xml，html），
//我们可以把他转化为json对象，然后通过C#代码操控json对象，就会方便的很。
//当然jquery也很方便。看个人喜好。接下来依次总结“json，linq to json ，linq to xml”等操作。
