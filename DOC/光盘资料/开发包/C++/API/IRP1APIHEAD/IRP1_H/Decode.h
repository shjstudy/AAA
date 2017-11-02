#ifndef  _DECODE_H_
#define  _DECODE_H_
#include "IProcess.h"
#include "Messages.h"
class Decode : public IProcess
{
	public:
		
		virtual IMessageNotification *ParseMessageNoticefaction(char recvMsg[],unsigned int Msglen); 
		int OutTransform(char *out, char *in, int size);
		int formatData(char *out,char *in, int size);
		virtual void Parse(MSG_NET net, std::list<MSG_LIST> &mytlist);
		void AddList(unsigned char type,IMessageNotification *msg);
		unsigned int i_lLen ;	
		Decode();
		~Decode();

	public:

		CString m_CurPortType;//在Decode类保存一下当前的通信类型hanyj;
		unsigned char  m_EPC_6C;
		unsigned char  m_ID_6B;
		unsigned char  m_TID_6C;
		unsigned char  m_EPC_TID_UserData_6C;
		unsigned char  m_EPC_TID_UserData_6C_2;
		unsigned char  m_ID_UserData_6B;
		unsigned char  m_EPC_PC_6C;
		unsigned char  m_TriggerSignal_800;
		unsigned char  m_AutoReadTagSignal_800;
		unsigned char  m_IOSignal_800;
		unsigned char  m_TID_6C_2;//加变量，双工添加2012-5-16
		unsigned char m_ReadTagTriggerSignal_800;
		unsigned char m_EPC_TID_UserData_Received_6C;
		unsigned char m_ID_UserData_6B_2;	
		unsigned char m_Keepalive;	
	protected:
	
	private:
	MSG_NET stru_let_msg;
	IMessageNotification * m_msg_poweroff_800;
	IMessageNotification * m_msg_read_tag_6c;

	IMessageNotification * m_RXD_EPC_6C;
	IMessageNotification * m_RXD_ID_6B;
	IMessageNotification * m_RXD_TID_6C;
	IMessageNotification * m_RXD_EPC_TID_UserData_6C;
	IMessageNotification * m_RXD_EPC_TID_UserData_6C_2;
	IMessageNotification * m_RXD_ID_UserData_6B;
	IMessageNotification * m_RXD_EPC_PC_6C;
	IMessageNotification * m_RXD_TriggerSignal_800;
	IMessageNotification * m_RXD_AutoReadTagSignal_800;
	IMessageNotification * m_RXD_IOSignal_800;
	IMessageNotification * m_RXD_TID_6C_2;//加变量，双工添加2012-5-16
	IMessageNotification * m_RXD_ReadTagTriggerSignal_800;
	IMessageNotification * m_RXD_EPC_TID_UserData_Received_6C;
	IMessageNotification * m_RXD_ID_UserData_6B_2;
	Keepalive * m_RXD_Keepalive;
	
	/*unsigned char  m_EPC_6C;
	unsigned char  m_ID_6B;
	unsigned char  m_TID_6C;
	unsigned char  m_EPC_TID_UserData_6C;
	unsigned char  m_EPC_TID_UserData_6C_2;
	unsigned char  m_ID_UserData_6B;
	unsigned char  m_EPC_PC_6C;*/

};

#endif