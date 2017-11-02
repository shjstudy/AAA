#ifndef  _RFID_USB_H_
#define  _RFID_USB_H_

//#include "ICommunication.h"
#include "ICommunication.h"
#include <afxmt.h>


class CMyUsb : public ICommunication
{
public:
		virtual bool Open(HANDLE &hnd);
		virtual bool close(HANDLE hnd);
		virtual int recvData(HANDLE hnd, char *buf,int bufSize);
		virtual bool wirteData(HANDLE hnd, char *buf,int bufSize);	

		int usb_rev ( HANDLE hCom, unsigned char *p_out );
		int  usb_getch(HANDLE hComPart);
		int usb_frame_rev ( HANDLE hCom, unsigned char *p_out, int buffer_size, unsigned char uCmdWord=0x00 );
		
		static DWORD WINAPI revDataThread(LPVOID pParma);
		
		void  OpenThread(HANDLE hCom);
		
		CEvent event;
		
		unsigned char m_buf[256];
	int m_bufSize;

protected:
	
private:
};

#endif