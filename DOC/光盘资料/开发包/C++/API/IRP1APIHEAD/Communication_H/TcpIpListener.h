#ifndef TCPIPLISTENER_H
#define  TCPIPLISTENER_H

// #include "../IRP1_H/BaseMessage.h"
// #include "../IRP1_H/Messages.h"
// #include "ICommunication.h"
// #include "CommunicationFactory.h"
#include <Winsock2.h>
#include "ICommunication.h"
class TcpIpListener;
#include "Reader.h"

//回调读写器函数，DealType为读写器处理类型，1表明是新增读写器，2为要删除的读写器
typedef void (CALLBACK *LPCallBackReader)(Reader *pReader,int DealType); //声明读写器的回调函数指针

class TcpIpListener : public ICommunication
{

public:
	TcpIpListener();
	virtual bool Open(int Port,CString protocol);
	virtual bool close(HANDLE hnd);
	virtual int recvData(HANDLE hnd, char *buf,int bufSize);
	virtual bool wirteData(HANDLE hnd, char *buf,int bufSize);
	
	LPCallBackReader lpCallBackReader;
	virtual void SetCallBackReader(LPCallBackReader lpCallBackFun);
	static DWORD WINAPI ConnectThread(LPVOID lpParameter);
	static DWORD WINAPI DealExceptionThread(LPVOID lpParameter);
//	static void DealNetException(HANDLE hnd);

	CString m_ReaderIp; //保存监听有连接后的读写器IP      add by hanyejun
	int m_ReaderPort;   //保存保存监听有连接后的读写器端口
	HANDLE m_hComSock;  //保存服务器监听时，有连接后返回来的通信socket

	unsigned int iSerSockHandle; //保存服务器打开、绑定并监听的socket handle
	int m_LocalPort;             //保存服务器本地的监听port
protected:
	
private:
};


struct ServerInfo 
{
	TcpIpListener *lpListener;
	unsigned int iSocketHandle;
	CString Protocol;
	int Port;
};



// #define  SIO_RCVALL  IOC_IN | IOC_VENDOR | 1
// struct tcp_keepalive 
// {
//     u_long  onoff;
//     u_long  keepalivetime;
//     u_long  keepaliveinterval;
// };


#endif
