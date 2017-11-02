#ifndef  _RS232_H_
#define  _RS232_H_

//#include "ICommunication.h"
#include "ICommunication.h"
class CCom: public ICommunication
{

public:
	virtual bool Open(HANDLE &hnd);
	virtual bool close(HANDLE hnd);
	virtual int recvData(HANDLE hnd, char *buf,int bufSize);
	virtual bool wirteData(HANDLE hnd, char *buf,int bufSize);	
protected:
	
private:

};

#endif
