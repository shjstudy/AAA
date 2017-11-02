#ifndef _BASEMESSAGE_H_
#define  _BASEMESSAGE_H_

#include "MessageFrame.h"
#include "IMessage.h"
//#include "EnumClass.h"
class BaseMessage : public IMessage ,public MessageFrame 
{
	public:
	    virtual int TransmitterData(RFIDPort PortTypeBak);
		bool isReturn;
		int  timeout;
		char rxData[256];
		unsigned int  msgID;
		unsigned int statusCode;
        char txData[256];
		RFIDPort portType;
		void setStatusCode(char *buf);
		virtual void TrigerOnExecuting(void *obj);
        virtual void TrigerOnExecuted(void *obj);
		BaseMessage();
	private:
};

#endif