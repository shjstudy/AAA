#ifndef  _TCP_IP_CLIENT_
#define  _TCP_IP_CLIENT_
#include "ICommunication.h"

#ifdef WIN32
#include "Winsock2.h"
//#include <afxsock.h>
typedef int socklen_t;
#else
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#if defined(_BEOS_) || defined (_INNOTEK_LIBC_)
typedef int socklen_t;
#endif
#ifndef _BEOS_
#include <arpa/inet.h>
#else
#include "barpainet.h"
#endif
#include <netdb.h>
#include <sys/time.h>
#include <fcntl.h>
#include <pthread.h>
#endif
#ifdef WIN32
#define CONNECTING WSAEINPROGRESS
#else
#define CONNECTING AVERROR_CONNSERVER
#endif
#define MAXCLIENT 2048 //最多连接的客户端
#define MAXRECVLEN 204800
typedef int SocketHandle;

#define MAX_DELAY 1
#define NET_DELAY 50
#define MIN_COMM_NUM 6
#define MAX_REC_NUM 256
#define XC610_PORT 7086
#define MAX_REC_NUM_NET 1024
#define MAX_USER_DATA_PERPAGE 6
#define MAX_USER_GROUP  14
#define MAX_SYSPARAR_LEN 52

class TcpIpClient : public ICommunication
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