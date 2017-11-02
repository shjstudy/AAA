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
		bool ParaeXmlReadInfo(CString FilePath,CString ReaderName); 
		
		
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
		CString			ReaderEnable; //= "Enable";
		CString			PortType;// = "RS232";
		CString			ProtocolVersion;// = "IRP1_H";
		CString			connStr;
		CString			UCfgProtocolVersion;	
		CString			UCfgReaderType;		
		CString			UCfgReadMemoryBank;	
		CString			UCfgAntenna;		
		CString			UCfgIsLoop;		
		CString		    UCfgTagNum;			
		CString			UCfgStopType;		
		CString			UCfgReadTime;		
		CString			UCfgStopTime;		

		CString readerType;
		bool isRssiEnable;
		bool isUtcEnable;

		static int		CriSecNum; 
        BOOL			isExistReaderConfig;// = true;
		ICommunication *pCon;
		TcpIpListener  *pListener; 
	protected:
		
	private:
		int sendComnend();

};


#endif