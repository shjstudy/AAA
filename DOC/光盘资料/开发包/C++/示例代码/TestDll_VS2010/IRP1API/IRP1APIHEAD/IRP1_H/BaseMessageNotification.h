#ifndef _BASEMESSAGENOTIFICATION_
#define  _BASEMESSAGENOTIFICATION_
#include "MessageFrame.h"
#include "IMessageNotification.h"

class BaseMessageNotification : public MessageFrame, public IMessageNotification
{
	public:
		char  rxData[128];
		unsigned int msgID;
	private:    
};
#endif



