#ifndef _IMESSAGE_H_ 
#define  _IMESSAGE_H_ 
#include "IMessageNotification.h"
#include "EnumClass.h"

class  IMessage : public IMessageNotification
{
	public:
		/*Boolean IsReturn { get; set; }
        Int32 Timeout { get; set; }
        Byte[] TransmitterData { get; set; }
        RFIDPort PortType{ get; set; }
		
        Byte[] FromXml(String xmlStr);
        String ToXml();*/
		bool bReturn;
		int Timeout;	
		RFIDPort PortType;
		virtual int TransmitterData(RFIDPort PortTypeBak);
		virtual void TrigerOnExecuting(void *obj);
        virtual void TrigerOnExecuted(void *obj);
	protected:
	private:
		

};



#endif