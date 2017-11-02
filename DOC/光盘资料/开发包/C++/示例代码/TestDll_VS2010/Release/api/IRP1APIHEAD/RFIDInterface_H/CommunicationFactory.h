#ifndef _COMUNICATION_FACTORY_H_
#define _COMUNICATION_FACTORY_H_
#include "ICommunication.h"

class CommunicationFactory
{
	public:
		
		static ICommunication *createInstance(int type);

	protected:
	
	private:
		
	//	ICommunication *icon;


};



#endif