#ifndef _IMESSAGENOTIFICATION_H
#define _IMESSAGENOTIFICATION_H


class IMessageNotification 
{
	public:	
		CString msgName;
		unsigned int StatusCode;
		unsigned int MessageID;    
		unsigned char ReceivedData[512];
		unsigned int ReceivedDataLen;
		CString GetMessageType();
		CString ErrInfo();
};

#endif