#ifndef _IPROCESS_H_
#define  _IPROCESS_H_

#include "IMessageNotification.h"
#include "EnumClass.h"
#include <list>

class IProcess
{
	public:
		virtual void Parse(MSG_NET net, std::list<MSG_LIST> &mytlist);
		RFIDPort PortType;
		virtual IMessageNotification * ParseMessageNoticefaction(char recvMsg[],unsigned int Msglen);      
		int GetMessageID(char msg[]);
};

#endif