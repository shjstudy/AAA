#ifndef _READER_H
#define  _READER_H

#include "../IRP1_H/BaseMessage.h"
#include "../IRP1_H/Messages.h"
#include "ICommunication.h"
#include "CommunicationFactory.h"
class Reader;
#include "../Communication_H/TcpIpListener.h"

class Reader
{
	public:

		Reader();
		Reader(CString readerName);
		Reader(CString readerName, CString protocolVersion, CString portType, CString connStr);
		virtual bool connect();
		virtual bool disconnect();
		virtual bool send(IMessage *msg);
		bool ParaeXmlReadInfo(CString FilePath,CString ReaderName); //根据传进来的读写器名称，去XML配置文件读它相应参数。add by hanyejun
		
		//回调函数....收数据
		//BaseMessage *m_message;
		//BaseMessage * create_message();
		HANDLE hand;
		IMessage * m_message;

		virtual IMessage * create_message();
		
		void (*onMessageEvent)(IMessageNotification *msg,void *context);
		virtual void setCallBack( void (*fun)(IMessageNotification *,void *),void *m_p_reader_context,void *reader);
		void *m_reader_context;
		void *reader;
		bool			IsConnected; //= false;
        CString			ModelNumber; //= "unknown";
        CString			ReaderName; //= "Reader1";
        CString			ReaderGroup; //= "Group1";
		CString			ReaderEnable; //= "Enable";//读写器是否启动 add by hanyejun
		CString			PortType;// = "RS232";
		CString			ProtocolVersion;// = "IRP1_H";
		CString			connStr;
		CString			UCfgProtocolVersion;	//保存用户选择的协议版本 add by hanyejun
		CString			UCfgReaderType;			//保存用户选择的读写器类型 add by hanyejun
		CString			UCfgReadMemoryBank;		//读取标签类型 
		CString			UCfgAntenna;			//天线号
		CString			UCfgIsLoop;				//是否循环读取
		CString		    UCfgTagNum;				//预读标签数
		CString			UCfgStopType;			//读写器停止类型
		CString			UCfgReadTime;			//读标签时间
		CString			UCfgStopTime;			//停止读标签前的停留时间

		CString readerType;
		bool isRssiEnable;
		bool isUtcEnable;

		static int		CriSecNum; //保存当前连接的读写器个数  add by hanyejun
        BOOL			isExistReaderConfig;// = true;
		ICommunication *pCon;
		TcpIpListener  *pListener; //在读写器类增加服务器监听变量 hanyejun
	protected:
		
	private:
		int sendComnend();

};


#endif