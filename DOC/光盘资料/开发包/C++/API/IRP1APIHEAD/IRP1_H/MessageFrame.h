#ifndef _MESSAGEFRAME_
#define  _MESSAGEFRAME_

class MessageFrame
{

public:

protected:
	unsigned int msgType; //命令字
    unsigned  char msgLen[2]; //指令长度,2字节
    unsigned char msgBody[256];  //指令内容
    unsigned int iMsgBodyLen;//Msg body长度
	unsigned char crc[2]; //crc校验码
    
};

#endif