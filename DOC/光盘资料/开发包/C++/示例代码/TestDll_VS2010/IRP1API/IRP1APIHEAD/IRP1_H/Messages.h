#ifndef _MESSAGES_H
#define  _MESSAGES_H

#include "BaseMessage.h"
#include "BaseMessageNotification.h"
#include <list>

class RXD_TagData : public BaseMessageNotification
{
public:
	RXD_TagData ();
	CString readerName ;
	CString msgType;
	virtual CString  GetReaderName();
	CString tagType;
	virtual CString  GetTagType();
	unsigned char epc[128];
	unsigned char epcLen;
	virtual bool GetEPC(unsigned char *epc,unsigned char &epcLen);
	
	unsigned char  epc_pc[128] ;
	unsigned char epc_pc_len;
	virtual bool GetEPCPC(unsigned char *epcPc,unsigned char &epcPcLen);
	
	unsigned char  tid[128];
	unsigned char tidLen;
	virtual bool GetTID(unsigned char *tid,unsigned char &tidLen);
	
	unsigned char  userdata[256] ;
	unsigned char userdataLen;
	virtual bool GetUserdata(unsigned char *userdata,unsigned char &userdataLen);

	unsigned char  reserved[128] ;
	unsigned char reservedLen;
	virtual bool GetReserved(unsigned char *reserved,unsigned char &reservedLen);
	unsigned char antenna;
	virtual unsigned char GetAntenna();

	unsigned char  rssi[128];
	unsigned char rssiLen;
	virtual bool GetRssi(unsigned char *rssi,unsigned char &rssiLen);
	
	unsigned char rxdtime[128];
	unsigned char rxdtimeLen;
	virtual bool GetRxdtime(unsigned char *rxdtime,unsigned char &rxdtimeLen);

};




class RXD_EPC_6C : public BaseMessageNotification
{
public:
	RXD_EPC_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagAntenna;
};

class RXD_ID_6B : public BaseMessageNotification
{
public:
	RXD_ID_6B(); 
	virtual unsigned char GetAntenna();
	virtual unsigned char GetTagType();
	virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
	virtual bool GetRSSI(unsigned char *pRSSI,unsigned char RSSILen);
	unsigned char tagID[256];
	unsigned char tagIDLen;
	unsigned char tagAntenna;
	unsigned char ucTagType;
	unsigned char RSSI[256];
	unsigned char RSSILen;
};

class RXD_TID_6C  : public BaseMessageNotification
{
public:	
	RXD_TID_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
};

class RXD_EPC_TID_UserData_6C : public BaseMessageNotification
{
public:
	RXD_EPC_TID_UserData_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
};

class RXD_EPC_TID_UserData_6C_2 : public BaseMessageNotification
{
public:
	RXD_EPC_TID_UserData_6C_2();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
};


class RXD_EPC_TID_UserData_Received_6C  : public BaseMessageNotification
{
public:
	RXD_EPC_TID_UserData_Received_6C ();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	virtual bool GetReserved(unsigned char *pReserved,unsigned char &ReservedLen);
	virtual bool GetRSSI(unsigned char *pRSSI,unsigned char &RSSILen);
	virtual bool GetReadTime(unsigned char *pReadTime,unsigned char &ReadTimeLen);

	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
	unsigned char Reserved[256];
	unsigned char ReservedLen;
	unsigned char RSSI[256];
	unsigned char RSSILen;
	unsigned char ReadTime[256];
	unsigned char ReadTimeLen;

};


class RXD_ID_UserData_6B : public BaseMessageNotification
{
public:	
	RXD_ID_UserData_6B();
	virtual unsigned char GetAntenna();
	virtual unsigned char GetTagType();
	virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
	virtual bool GetUserData(unsigned char *pUserData,unsigned char &userdataLen);

	unsigned char tagID[256];
	unsigned char tagIDLen;
	unsigned char tagAntenna;	

	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
	unsigned char ucTagType;

};
class RXD_ID_UserData_6B_2 : public BaseMessageNotification
{
public:
	RXD_ID_UserData_6B_2();
	virtual unsigned char GetAntenna();
	virtual unsigned char GetTagType();
	virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
	virtual bool GetUserData(unsigned char *pUserData,unsigned char &userdataLen);
	virtual bool GetRSSI(unsigned char *pRSSI,unsigned char &RSSILen);
	virtual bool GetReadTime(unsigned char *pReadTime,unsigned char &ReadTimeLen);

	unsigned char tagID[256];
	unsigned char tagIDLen;
	unsigned char tagAntenna;	

	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
	unsigned char ucTagTyp;

	unsigned char RSSI[256];
	unsigned char RSSILen;
	unsigned char ReadTime[256];
	unsigned char ReadTimeLen;
	unsigned char ucTagType;

};
class RXD_EPC_PC_6C : public BaseMessageNotification
{
public:
	RXD_EPC_PC_6C();
	virtual unsigned char GetAntenna();
	virtual BOOL GetPC(unsigned char *pc, unsigned char &pcLen);
	virtual BOOL GetEPC(unsigned char *epc,unsigned char &epcLen);
	virtual BOOL GetRSSI(unsigned char *rssi,unsigned char &rssiLen);

	unsigned char tagPC[256];
	unsigned char tagPCLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagRSSI[256];
	unsigned char tagRSSILen;

};


 class PowerOn_800 : public BaseMessage
 {	 

	 /// <param name="antenna">天线号/param>
 public:
	 PowerOn_800(unsigned char antenna);
	
};


 class ReadTag : public BaseMessage
 {
	public:
		ReadMemoryBank rmb;
        int readTime;
        int stopTime;
        CString stopType;
        volatile bool isStop;
		
		//配置扫描数据
		unsigned char Antenna;  //天线号
		unsigned char q;        //q值
		unsigned char isLoop;    //是否循环
		unsigned char tidLen;     //tid长度
		unsigned char userdataPtr;  //用户数据起启地址
		unsigned char userdataLen;   //用户数据长度

		virtual void SetAntenna(unsigned char ucAntenna);
		virtual void SetQ (unsigned char ucQ);
		virtual void SetIsLoop (unsigned char ucIsLoop);
		virtual	void SetTidLen (unsigned char ucTidLen);
		virtual	void SetUserdataPtr_6C(unsigned int ucUserdataPtr_6C);
		virtual	void SetUserDataLen_6C (unsigned char ucUserDataLen_6C);
		virtual	void SetUserdataPtr_6B (unsigned char ucUserDataLen_6C);
		virtual	void SetUserDataLen_6B (unsigned char ucUserDataLen_6B);
		virtual	void SetReservedLen (unsigned char ucReservedLen);
		virtual	void SetAccessPwd (unsigned char* ucAccessPwd,unsigned char ucAccessPwdLen);
		virtual	void SetReadTimes_6C(unsigned char ucReadTimes_6C);
		virtual	void SetReadTimes_6B(unsigned char ucReadTimes_6B);

		ReadTag();
		ReadTag(ReadMemoryBank rmb);        
        ReadTag(ReadMemoryBank rmb, int readTime, int stopTime, CString stopType);
        ReadTag(unsigned char tidLen, unsigned char userdataPtr, unsigned char userdataLen);
		ReadTag(unsigned char tidLen, unsigned char userdataPtr, unsigned char userdataLen, int readTime, int stopTime);
		ReadTag(ReadMemoryBank rmb,CString stopType,BOOL isGetOneTag);

		
		RXD_EPC_6C *m_p_RXD_EPC_6C;
		RXD_ID_6B  *m_p_RXD_ID_6B;
		RXD_TID_6C *m_p_RXD_TID_6C;
		RXD_EPC_TID_UserData_6C *m_p_RXD_EPC_TID_UserData_6C;
		RXD_EPC_TID_UserData_6C_2 *m_p_RXD_EPC_TID_UserData_6C_2;
		RXD_EPC_TID_UserData_Received_6C *m_p_RXD_EPC_TID_UserData_Received_6C;
		RXD_ID_UserData_6B *m_p_RXD_ID_UserData_6B;
		RXD_ID_UserData_6B_2 *m_p_RXD_ID_UserData_6B_2;
		RXD_EPC_PC_6C *m_p_RXD_EPC_PC_6C;
		
		std::list<RXD_EPC_6C *>m_RXD_EPC_6C_list;
		std::list<RXD_ID_6B *>m_RXD_ID_6B_list;
		std::list<RXD_TID_6C *>m_RXD_TID_6C_list;
		std::list<RXD_EPC_TID_UserData_6C *>m_RXD_EPC_TID_UserData_6C_list;
		std::list<RXD_EPC_TID_UserData_6C_2 *>m_RXD_EPC_TID_UserData_6C_2_list;
		std::list<RXD_EPC_TID_UserData_Received_6C *>m_RXD_EPC_TID_UserData_Received_6C_list;
		std::list<RXD_ID_UserData_6B *>m_RXD_ID_UserData_6B_list;
		std::list<RXD_ID_UserData_6B_2 *>m_RXD_ID_UserData_6B_2_list;
		std::list<RXD_EPC_PC_6C *>m_RXD_EPC_PC_6C_list;
		std::list<RXD_TagData*>m_RXD_TagData_list;
	    
		virtual RXD_EPC_6C * List_RXD_EPC_6C(unsigned int &len);
		virtual RXD_ID_6B  *List_RXD_ID_6B(unsigned int &len);
		virtual RXD_TID_6C *List_RXD_TID_6C(unsigned int &len);
		virtual RXD_EPC_TID_UserData_6C * List_RXD_EPC_TID_UserData_6C(unsigned int &len);
		virtual RXD_EPC_TID_UserData_6C_2 * List_RXD_EPC_TID_UserData_6C_2(unsigned int &len);
		virtual RXD_EPC_TID_UserData_Received_6C * List_RXD_EPC_TID_UserData_Received_6C(unsigned int &len);
		virtual RXD_ID_UserData_6B * List_RXD_ID_UserData_6B(unsigned int &len);
		virtual RXD_ID_UserData_6B_2 * List_RXD_ID_UserData_6B_2(unsigned int &len);
		virtual RXD_EPC_PC_6C * List_RXD_EPC_PC_6C(unsigned int &len);
		virtual RXD_TagData * List_RXD_TagData(unsigned int &len);

		void addList(unsigned char type,unsigned char *buf,unsigned char bufLen);
		void add_List_RXD_TagData(unsigned char type,unsigned char *buf,unsigned char bufLen);

		virtual void releaseMen();

		virtual void TrigerOnExecuted(void *obj);
	private:
		bool bTrigerOnExecuted;
		void setData(ReadMemoryBank rmb);

		void Clear_List_RXD_EPC_6C();
		void Clear_List_RXD_ID_6B();
		void Clear_List_RXD_TID_6C();
		void Clear_List_RXD_EPC_TID_UserData_6C();
		void Clear_List_RXD_EPC_TID_UserData_6C_2();
		void Clear_List_RXD_EPC_TID_UserData_Received_6C();
		void Clear_List_RXD_ID_UserData_6B();
		void Clear_List_RXD_ID_UserData_6B_2();
		void Clear_List_RXD_EPC_PC_6C();
		void clear_list_RXD_TagData();
 };


 /*class PowerOff_500 :  private BaseMessage
 {
	public: 
		 PowerOff_500();
		
  };*/


class TagOperationQuery_6C : public  BaseMessage
{	
	 public:
	TagOperationQuery_6C(unsigned char parameter);
	virtual BOOL GetQueryData(char *data,unsigned char &dataLen);
};


/// 配置标签操作配置指令
class TagOperationConfig_6C : public BaseMessage
{	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="parameter">配置标签操作配置指令参数类型</param>
	/// <param name="pData">配置数据</param>
	///<param nanem="dataLen">配置数据长度</param> 
	 public:
		TagOperationConfig_6C(unsigned char parameter, unsigned char *pData,unsigned char dataLen);
};


/// <summary>
/// IMPINJ QT指令
/// </summary>
class QT_6C : public BaseMessage
{

	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="antenna">天线端口</param>
	/// <param name="pwd">密码(4字节)</param>
	/// <param name="opType">操作类型（R/W）:0，查询标签的原有QT属性;1，设置标签的QT属性</param>
	/// <param name="persistent">配置持久性（persistent）：0，配置仅在标签本次上电过程中有效;1，配置可一直保存</param>
	/// <param name="payload">配置参数（payload）：
	/// 第一字节常用位控制模式意义如下表，第二字节保留
	/// 比特	7	6	5	4	3	2	1	0
	/// 定义	QT_SR	QT_MEM	保留	保留	保留	保留	间隔	保留
	
	/// QT_SR：1：标签在开状态和安全态时会减少响应距离（或仅在近距离响应）
	///         0：标签不减少响应距离
	/// QT_MEM 1：标签存储器映射为公共模式
	/// 		 0:  标签存储器映射为私密模式</param>
 public:
	QT_6C(unsigned char antenna, 
			unsigned char *accessPwd,
			unsigned char accessPwdLen, 
			unsigned char opType, 
			unsigned char persistent,
			unsigned char *payload,
			unsigned char payloadLen);
	
	virtual unsigned char GetAntenna();
	virtual bool GetPayload(unsigned char *data, unsigned char &dataLen);
	
};


 /// 快读TID时EPC相对长度
class FastReadTIDConfig_EpcLength : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">信息类型</param>
	/// <param name="infoParam">信息参数</param>
	 public:
	FastReadTIDConfig_EpcLength(unsigned char infoType, unsigned char *infoParam,unsigned char infoParamLen);
	virtual bool GetQueryData(unsigned char *pData,unsigned char &dataLen);	

};

/// 快读TID开关
class FastReadTIDConfig_6C : public BaseMessage
{
	/// </summary>
	/// <param name="infoType">信息类型</param>
    /// <param name="infoParam">信息参数</param>
	 public:
	FastReadTIDConfig_6C(unsigned char infoType, unsigned char infoParam);
	virtual bool GetQueryData(unsigned char *pData,unsigned char &dataLen);	
};

/// <summary>
/// 读变长TID开关设置/查询
/// </summary>
 class ReadUnfixedTidConfig_6C : public BaseMessage
{
	/// </summary>       
	/// <param name="infoType">信息类型：0x00设置，0x01查询</param>
	/// <param name="infoParam">信息参数:0x00关闭读变长TID功能,0x01开启读变长TID功能</param>      
 public:
	 ReadUnfixedTidConfig_6C(unsigned char infoType, unsigned char infoParam);
	virtual bool GetQueryData(unsigned char *pData,unsigned char &dataLen);

};

/// 固定TID长度设置/查询
class FixedTidLengthConfig_6C : public BaseMessage
{
	/// <param name="infoType">信息类型：0x00设置，0x01查询</param>
	/// <param name="infoParam">信息参数：读取固定长度TID的字个数（需大于0）</param>      
 public:
	FixedTidLengthConfig_6C(unsigned char infoType, unsigned char infoParam);
	virtual bool GetQueryData(unsigned char *pData,unsigned char &dataLen);
};


/// 读所6C有数据信息参数设置/查询
class ReadTagConfig_6C : public BaseMessage
{
	/// </summary>
	/// <param name="infoType">信息类型:0为设置，1为查询</param>
	/// <param name="infoParam">信息参数</param>
	/// 信息参数：
	/// 第1个字节读EPC开关，0=不读取EPC码，1=读取EPC码
	/// 第2个字节读TID开关，0=不读取TID码，1=读取TID码
	/// 第3个字节读TID码的字长度
	/// 第4个字节读USR数据开关，0=不读取USR，1=读取USR
	/// 第5个字节读USR数据区的字长度
     public:
	ReadTagConfig_6C(unsigned char infoType, unsigned char *infoParam,unsigned char infoParamLen);
	virtual unsigned char GetInfoType();
	virtual bool GetInfoParam(unsigned char *data,unsigned char &dataLen);

};


/// 读6B标签数据区信息参数设置/查询
class ReadTagConfig_6B : public BaseMessage
{
	/// <param name="infoType">信息类型:0为设置，1为查询</param>
	/// <param name="infoParam">信息参数：需要读取6B标签数据区的字节数</param>
    public:
	ReadTagConfig_6B(unsigned char infoType, unsigned char infoParam);
	virtual unsigned char GetInfoType();
	virtual bool GetInfoParam(unsigned char *data,unsigned char &dataLen);

};

/// ID数据过滤指令
class IDFilter_6B : public BaseMessage
{
	/// <param name="tagID">ID(8字节)</param>
	/// <param name="tagMask">匹配参数(8字节)</param>
	 public:
	IDFilter_6B(unsigned char  *tagID, unsigned char tagIDLen,unsigned char *tagMask,unsigned char tagMaskLen);
	
};

/// EAS标志配置指令
class EasConfig_6C : public BaseMessage
{
	/// <param name="antenna">天线端口</param>
	/// <param name="accessPwd">访问密码</param>
	/// <param name="flag">EAS标志配置</param>
 public:
	EasConfig_6C(unsigned char antenna, unsigned char *accessPwd, unsigned char accessPwdLen, unsigned char flag);
	EasConfig_6C(unsigned char antenna, unsigned char* accessPwd,unsigned char accessPwdLen, unsigned char flag, unsigned char *tagID,unsigned char tagIDLen, MemoryBank tagIDType);

	virtual void TrigerOnExecuting(void *obj);

private:
	bool bTrigerOnExecuting;
	unsigned char  tagID[128];
	unsigned char tagIDType;
	unsigned char tagIDLen;


 };

/// EPC匹配过滤指令
class EpcFilter_6C : public  BaseMessage
{
	/// <param name="opType">类型</param>
	/// <param name="epcData">匹配EPC码</param>
	/// <param name="epcMask">要匹配掩码数据</param>
	 public:
	EpcFilter_6C(unsigned char  opType, unsigned char *epcData,unsigned char epcDataLen, unsigned char *epcMask,unsigned char epcMaskLen);

};

/// 重复标签按时间过滤指令
class FilterByTime : public BaseMessage
{	/// <param name="opType">类型</param>
	/// <param name="time">时间</param>
 public:
	FilterByTime(unsigned char opType, unsigned char *time,unsigned char timeLen);
};

 /// EAS监控功能设置指令
class EasAlarm_6C : public BaseMessage
{
	/// <param name="antenna">天线端口</param>
	/// <param name="easCfg">EAS监控配置</param>       
 public:
	EasAlarm_6C(unsigned char antenna, unsigned char easCfg);
	virtual unsigned char GetAntenna();
	virtual unsigned char GetAnswerType();
};	

/*
class RXD_EPC_6C : public BaseMessageNotification
{
 public:
	RXD_EPC_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagAntenna;
};

class RXD_ID_6B : public BaseMessageNotification
{
 public:
	RXD_ID_6B(); 
	virtual unsigned char GetAntenna();
	virtual unsigned char GetTagType();
	virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
	virtual bool GetRSSI(unsigned char *pRSSI,unsigned char RSSILen);
	unsigned char tagID[256];
	unsigned char tagIDLen;
	unsigned char tagAntenna;
	unsigned char ucTagType;
	unsigned char RSSI[256];
	unsigned char RSSILen;
};

class RXD_TID_6C  : public BaseMessageNotification
{
 public:	
	RXD_TID_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
};

class RXD_EPC_TID_UserData_6C : public BaseMessageNotification
{
 public:
	RXD_EPC_TID_UserData_6C();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
};

class RXD_EPC_TID_UserData_6C_2 : public BaseMessageNotification
{
public:
	RXD_EPC_TID_UserData_6C_2();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
};


class RXD_EPC_TID_UserData_Received_6C  : public BaseMessageNotification
{
 public:
	RXD_EPC_TID_UserData_Received_6C ();
	virtual unsigned char GetAntenna();
	virtual bool GetEPC(unsigned char *Epc,unsigned char &epcLen);
	virtual bool GetTID(unsigned char *TID,unsigned char &TIDLen);
	virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	virtual bool GetReserved(unsigned char *pReserved,unsigned char &ReservedLen);
	virtual bool GetRSSI(unsigned char *pRSSI,unsigned char &RSSILen);
	virtual bool GetReadTime(unsigned char *pReadTime,unsigned char &ReadTimeLen);

	unsigned char tagTID[256];
	unsigned char tagTIDLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
	unsigned char Reserved[256];
	unsigned char ReservedLen;
	unsigned char RSSI[256];
	unsigned char RSSILen;
	unsigned char ReadTime[256];
	unsigned char ReadTimeLen;

};


class RXD_ID_UserData_6B : public BaseMessageNotification
{
 public:	
	RXD_ID_UserData_6B();
	virtual unsigned char GetAntenna();
	virtual unsigned char GetTagType();
	virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
	virtual bool GetUserData(unsigned char *pUserData,unsigned char &userdataLen);
	
	unsigned char tagID[256];
	unsigned char tagIDLen;
	unsigned char tagAntenna;	

	unsigned char tagUserData[256];
	unsigned char tagUserDataLen;
	unsigned char ucTagType;

};
class RXD_ID_UserData_6B_2 : public BaseMessageNotification
{
	public:
		RXD_ID_UserData_6B_2();
		virtual unsigned char GetAntenna();
		virtual unsigned char GetTagType();
		virtual bool GetID(unsigned char *ID,unsigned char &IDLen);
		virtual bool GetUserData(unsigned char *pUserData,unsigned char &userdataLen);
		virtual bool GetRSSI(unsigned char *pRSSI,unsigned char &RSSILen);
		virtual bool GetReadTime(unsigned char *pReadTime,unsigned char &ReadTimeLen);

		unsigned char tagID[256];
		unsigned char tagIDLen;
		unsigned char tagAntenna;	

		unsigned char tagUserData[256];
		unsigned char tagUserDataLen;
		unsigned char ucTagTyp;

		unsigned char RSSI[256];
		unsigned char RSSILen;
		unsigned char ReadTime[256];
		unsigned char ReadTimeLen;
		unsigned char ucTagType;

};
class RXD_EPC_PC_6C : public BaseMessageNotification
{
 public:
	RXD_EPC_PC_6C();
	virtual unsigned char GetAntenna();
	virtual BOOL GetPC(unsigned char *pc, unsigned char &pcLen);
	virtual BOOL GetEPC(unsigned char *epc,unsigned char &epcLen);
	virtual BOOL GetRSSI(unsigned char *rssi,unsigned char &rssiLen);

	unsigned char tagPC[256];
	unsigned char tagPCLen;
	unsigned char tagAntenna;	
	unsigned char tagEpc[256];
	unsigned char tagEpcLen;
	unsigned char tagRSSI[256];
	unsigned char tagRSSILen;

};*/

 /// 标签选择指令
class SelectTag_6C : public BaseMessage
{
	/// <param name="memoryBank">匹配数据区</param>
	/// <param name="startAdd">匹配数据起始地址(EVB格式)</param>
	/// <param name="matchBitLength">匹配比特数</param>
    /// <param name="tagData">匹配数据</param>
public:
	SelectTag_6C();
	SelectTag_6C(MemoryBank memoryBank, unsigned char ptr, unsigned char matchBitLength, unsigned char *tagData,unsigned char tagDataLen);
	unsigned char select(void * reader, unsigned char *tagID,unsigned char tagIDLen, unsigned char tagIDType);

};

/// <summary>
/// 通用写标签指令
/// </summary>
class WriteTag_6C : public  BaseMessage
{
	/// <param name="antenna">天线端口</param>
	/// <param name="accessPwd">密码(4字节)</param>
	/// <param name="memoryBank">操作Bank</param>
	/// <param name="ptr">首地址</param>
	/// <param name="length">写入数据长度</param>
	/// <param name="data">写入数据</param>
 public:
	WriteTag_6C(unsigned char antenna, 
				unsigned char *accessPwd,
				unsigned char accessPwdLen, 
				MemoryBank memoryBank, 
				unsigned char ptr, 
				unsigned char length, 
				unsigned char *data,
				unsigned char dataLen);

	WriteTag_6C(unsigned char antenna, 
		unsigned char *accessPwd,
		unsigned char accessPwdLen, 
		MemoryBank memoryBank, 
		unsigned char ptr, 
		unsigned char length, 
		unsigned char *data,
		unsigned char dataLen,
		unsigned char *tagID,
		unsigned char tagIDLen,
		MemoryBank tagIDType);

	virtual void TrigerOnExecuting(void *obj);

private:
	bool bTrigerOnExecuting;
	unsigned char  tagID[128];
	unsigned char tagIDType;
	unsigned char tagIDLen;
               
};


/// <summary>
/// 写EPC数据指令
/// </summary>
 class WriteEpc : public BaseMessage
{
	//Byte[] tagID;
	//Byte tagIDType;	
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="antenna">天线端口</param>
	/// <param name="pwd">标签访问密码</param>        
	/// <param name="epcData">写入标签EPC数据</param>
   public: 
	WriteEpc(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char *epcData,unsigned char epcDataLen);
	WriteEpc(unsigned char  antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char *epcData,
			unsigned char epcDataLen, unsigned char *tagID,unsigned char tagIDLen, unsigned char tagIDType);

	virtual void TrigerOnExecuting(void *obj);
	private:
		bool bTrigerOnExecuting;
		unsigned char  tagID[128];
		unsigned char tagIDType;
		unsigned char tagIDLen;
 };


 /// <summary>
 /// 读用户数据区指令
 /// </summary>
  class ReadUserData_6C : public BaseMessage
 {
	 
	 public: 
		unsigned char antenna;
		unsigned char ptr;
		unsigned char UserDatalenBak; //保存当前要求读取用户数据的长度
		// List<Byte> udBuff = new List<byte>();
		int maxReadLen;//单次最大写入word数
	 
		unsigned char  tagID[128];
		unsigned char tagIDLen;//标签ID长度
		unsigned  char tagIDType;
	 /// <param name="antenna">天线端口</param>        
	 /// <param name="ptr">标签数据区首地址（EVB格式）</param>
	 /// <param name="length">标签数据读取长度</param>
		ReadUserData_6C(unsigned char  antenna, unsigned char ptr, unsigned char  length);
		ReadUserData_6C(unsigned char antenna, unsigned char ptr, unsigned char length, unsigned char *tagID, unsigned char tagIDLen,unsigned char  tagIDType);
		virtual void TrigerOnExecuting(void *obj);	
		virtual unsigned char GetAntenna();
		virtual bool GetUserData(unsigned char *usderData,unsigned char &userDataLen);
	 
	 private:
		 bool bTrigerOnExecuting;
		 char ReadUserDatabuf[512];
		 unsigned char bufLen;
  };


  /// <summary>
  /// 写用户数据区指令
  /// </summary>
  class WriteUserData_6C : public  BaseMessage
  {
  public:
	  unsigned char antenna;
	  unsigned char ptr;
	  unsigned char pwd[4];
	  unsigned char userData[512];
	  unsigned char userDataLenBak; //保存写用户数据的长度 add by hanyj
	  int maxWriteLen;//单次最大写入word数
	  unsigned char tagID[128];
	  unsigned char tagIDLen;
	  unsigned char tagIDType;
	
	  /// <param name="antenna">天线端口</param>
	  /// <param name="pwd">标签访问密码</param>
	  /// <param name="ptr">数据区写入首地址</param>
	  /// <param name="userData">写标签数据区数据</param>
      WriteUserData_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char ptr, unsigned char *userData,unsigned char userDataLen);
	  WriteUserData_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char ptr, unsigned char *userData,unsigned char userDataLen,unsigned char* tagID,unsigned char tagIDLen, MemoryBank  tagIDType);
      virtual void TrigerOnExecuting(void *obj);	
  private: 
	  void setData(unsigned char antenna, unsigned char *pwd, unsigned char ptr, unsigned char *userData,unsigned char userDataLen);
	  bool bTrigerOnExecuting;
	


  };


 class BlockWrite_6C : public BaseMessage
  {
	 public:
		unsigned char tagID[128];
		unsigned char tagIDLen;
		unsigned char tagIDType;
	  /// <param name="antenna">天线端口</param>
	  /// <param name="accessPwd">标签访问密码</param>
	  /// <param name="memoryBank">操作块</param>
	  /// <param name="ptr">标签数据区首地址（EVB格式）</param>
	  /// <param name="dataLength">写数据区长度</param>
	  /// <param name="data">写数据区数据</param>
		BlockWrite_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char memoryBank, unsigned char ptr, unsigned char *data,unsigned char dataLen);
		BlockWrite_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char memoryBank, unsigned char ptr, unsigned char *data,unsigned char dataLen,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
		virtual void TrigerOnExecuting(void *obj);
	 private:
		 bool bTrigerOnExecuting;
		 void setData(unsigned char antenna, unsigned char *accessPwd, unsigned char memoryBank, unsigned char ptr, unsigned char *data,unsigned char dataLen);

  };


 /// <summary>
 /// 块擦Bank数据指令
 /// </summary>
 class BlockErase_6C : public BaseMessage
 {
	public:	
		unsigned char tagID[128];
		unsigned char tagIDLen;
		unsigned char tagIDType;
	 /// <param name="antenna">天线端口</param>
	 /// <param name="accessPwd">标签访问密码</param>
	 /// <param name="memoryBank">操作Bank </param>
	 /// <param name="ptr">擦除标签数据区首地址（EVB格式）</param>
	 /// <param name="dataLength">擦除标签数据区长度</param>
        BlockErase_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char memoryBank, unsigned char ptr, unsigned char dataLength);
		BlockErase_6C(unsigned char antenna, unsigned char *accessPwd, unsigned char accessPwdLen,unsigned char memoryBank, unsigned char ptr, unsigned char dataLength,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
		virtual void TrigerOnExecuting(void *obj);
	private:
		bool bTrigerOnExecuting;
		void setData(unsigned char antenna, unsigned char *accessPwd, unsigned char memoryBank, unsigned char ptr, unsigned char dataLength);
 };


 /// <summary>
 /// 配置访问密码指令
 /// </summary>
  class AccessPwdConfig_6C : public BaseMessage
 {
	public:	
		unsigned char tagID[128];
		unsigned char tagIDLen;
		unsigned char tagIDType;
	 /// <param name="antenna">天线端口</param>
	 /// <param name="oldPwd">原访问密码</param>
	 /// <param name="newPwd">新访问密码</param>
		AccessPwdConfig_6C(unsigned char antenna, unsigned char  *oldPwd,unsigned char oldPwdLen, unsigned char *newPwd,unsigned char newPwdLen);
		AccessPwdConfig_6C(unsigned char antenna, unsigned char  *oldPwd,unsigned char oldPwdLen, unsigned char *newPwd,unsigned char newPwdLen,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
		virtual void TrigerOnExecuting(void *obj);
	private:
		bool bTrigerOnExecuting;
		void setData(unsigned char antenna, unsigned char  *oldPwd, unsigned char *newPwd);

 };



 class KillPwdConfig_6C  : public BaseMessage
 {
	public:	
		unsigned char tagID[128];
		unsigned char tagIDLen;
		unsigned char tagIDType;
		/// <param name="antenna">天线端口</param>, 
		/// <param name="oldPwd">原访问密码</param>
		/// <param name="newPwd">新访问密码</param>
		KillPwdConfig_6C (unsigned char antenna, unsigned char  *accessPwd,unsigned char accessPwdLen, unsigned char *killPwd,unsigned char killPwdLen);
		KillPwdConfig_6C (unsigned char antenna, unsigned char  *accessPwd,unsigned char accessPwdLen, unsigned char *killPwd,unsigned char killPwdLen,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
		virtual void TrigerOnExecuting(void *obj);
	private:
		bool bTrigerOnExecuting;
		void setData(unsigned char antenna, unsigned char  *accessPwd, unsigned char *killPwd);
		
 };


  /// 标签锁状态配置指令
 class LockMemoryBank_6C : public  BaseMessage
 {
 public:	
	 unsigned char tagID[128];
	 unsigned char tagIDLen;
	unsigned char tagIDType;
	  /// <param name="antenna">天线端口</param>
	  /// <param name="accessPwd">标签访问密码</param>
	  /// <param name="lockType">锁操作类型</param>
	  /// <param name="memoryBank">Bank类型 </param>
     LockMemoryBank_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char lockType, unsigned char  memoryBank);
	 LockMemoryBank_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char lockType, unsigned char  memoryBank,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
	 virtual void TrigerOnExecuting(void *obj);
private:
		bool bTrigerOnExecuting;
		void  setData(unsigned char antenna, unsigned char *accessPwd, unsigned char lockType, unsigned char  memoryBank);
 };


 /// 块永久锁指令
 /// </summary>
  class BlockPermalock_6C : public BaseMessage
 {
	 public:	
		 unsigned char tagID[128];
		 unsigned char tagIDLen;
		 unsigned char tagIDType;
	 /// <param name="antenna">天线端口</param>
	 /// <param name="accessPwd">密码(4字节)</param>
	 /// <param name="memoryBank">操作Bank</param>
	 /// <param name="readLock">操作类型(ReadLock):0，查询标签的块永久锁状态（目前不支持）;1，对标签的进行块永久锁操作</param>
	 /// <param name="ptr">起始块地址(BlockPtr)：每16个块（Block）为一个单元，如0x00表示从block 0开始，0x01表示从block 16开始。</param>
	 /// <param name="blockRange">块永久锁范围BlockRange：指示块永久锁操作的范围为从起始块地址（BlockPtr）到（16* BlockRange-1），最小值为1。</param>
	 /// <param name="mask">掩码（Mask）2*BlockRange字节：每一位对应一个block以指示从对应的block是否进行块永久锁操作，1为执行永久锁，0为保持原有锁状态，BlockPtr对应Mask最高位，（16* BlockRange-1）对应Mask的最低位。</param>
		BlockPermalock_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char memoryBank, unsigned char readLock, unsigned char ptr, unsigned char blockRange, unsigned char readLockbits, unsigned char *mask);
		BlockPermalock_6C(unsigned char antenna, unsigned char *accessPwd,unsigned char accessPwdLen, unsigned char memoryBank, unsigned char readLock, unsigned char ptr, unsigned char blockRange, unsigned char readLockbits, unsigned char *mask,unsigned char* tagID,unsigned char tagIDLen, MemoryBank tagIDType);
		virtual void TrigerOnExecuting(void *obj);
	 private:
		bool bTrigerOnExecuting;
		void  setData(unsigned char antenna, unsigned char *accessPwd, unsigned char memoryBank, unsigned char readLock, unsigned char ptr, unsigned char blockRange, unsigned char readLockbits, unsigned char *mask);


 };


  /// <summary>
  /// 标签灭活指令
  /// </summary>
  class KillTag_6C : public BaseMessage
  {
	  /// <param name="antenna">天线端口</param>
	  /// <param name="killPwd">灭活密码</param>
	  /// <param name="epcData">EPC码</param>
	  public:
	  KillTag_6C(unsigned char antenna, unsigned char *killPwd,unsigned char killPwdLen, unsigned char *epcData,unsigned char epcDataLen);
	          
   };

	 /// 读用户数据区指令
  /// </summary>
 class ReadUserData_6B : public  BaseMessage
  {
 public:
	 unsigned char antenna;
	  unsigned char tagID[128];
	  unsigned char tagIDLen;
	  unsigned char ptr;        
	  unsigned char lastPtr;
	  unsigned char length;
	  //List<Byte> udBuff = new List<byte>();
	  unsigned char udBuff[512];
	  unsigned char udBuffLen;
	  int maxReadLen;//单次最大读取字节数
	  unsigned char dataLength;//实际取出的数据字节数
	  /// <param name="antenna">天线端口</param>        
	  /// <param name="tagID">标签ID</param>
	  /// <param name="ptr">标签数据区起始（1～107）</param>
	  /// <param name="length">标签数据读取长度</param>
	 ReadUserData_6B(unsigned char antenna, unsigned char *tagID,unsigned char tagIDLen,unsigned char ptr, unsigned char length);
	 virtual unsigned char GetAntenna();
	 virtual bool GetUserData(unsigned char *userData,unsigned char &userdataLen);
	 virtual void TrigerOnExecuting(void *obj);

 private:

	 bool bTrigerOnExecuting;
 };


 class WriteUserData_6B : public BaseMessage
 {
 public:
	 unsigned char  antenna;
	 unsigned char ptr;
	 unsigned char tagID[128];
	 unsigned char tagIDLen;
	 unsigned char userdata[512];
	 unsigned char userdataLen;
	 int maxWriteLen ;//单次最大写入字节数
	 /// <param name="antenna">天线端口</param>
	 /// <param name="tagID">标签ID</param>
	 /// <param name="ptr">数据区写入首地址（1～107）</param>
	 /// <param name="userdata">写标签数据区数据</param>
     WriteUserData_6B(unsigned char antenna, unsigned char *tagID,unsigned char tagIDLen, unsigned char ptr, unsigned char *userdata,unsigned char userdataLen);
	 virtual void TrigerOnExecuting(void *obj);
	 
 private:
	 bool bTrigerOnExecuting;
 };

 /// 读用户数据区指令(读变长)
 /// </summary>
 class ReadUserData2_6B : public  BaseMessage
 {
 public:
	 unsigned char antenna;
	 unsigned char tagID[512];
	 unsigned char tagIDLen;

	 unsigned char ptr;
	 unsigned char length;
	 //List<Byte> udBuff = new List<byte>();
	 unsigned char udBuff[512];
	 unsigned char udBuffLen;
	 int maxReadLen;//单次最大读取字节数
	
	 /// <param name="antenna">天线端口</param>        
	 /// <param name="ptr">标签数据区首地址（EVB格式）</param>
	 /// <param name="length">标签数据读取长度</param>
	 ReadUserData2_6B(unsigned char  antenna, unsigned char *tagID,unsigned char tagIDLen, unsigned char ptr, unsigned char length);
	 virtual unsigned char GetAntenna();
     virtual bool GetUserData(unsigned char *userData,unsigned char &userDataLen);
	 virtual void TrigerOnExecuting(void *obj);
	 
 private:
	 bool bTrigerOnExecuting;

 };

 /// 写用户数据区指令(写变长)
 /// </summary>
 class WriteUserData2_6B : public BaseMessage
 {
	 unsigned char antenna;
	 unsigned char ptr;
	 unsigned char tagID[128];
	 unsigned char tagIDLen;
	 unsigned char userData[512];
	 unsigned char userDataLen;

	 int maxWriteLen;//单次最大写入字节数
	 /// <param name="antenna">天线端口</param>
	 /// <param name="tagID">标签ID</param>
	 /// <param name="ptr">数据区写入首地址</param>
	 /// <param name="userData">写标签数据区数据</param>
 public:
    WriteUserData2_6B(unsigned char antenna, unsigned char *tagID,unsigned char tagIDLen, unsigned char ptr, unsigned char *userData,unsigned char userDataLen);
	virtual void TrigerOnExecuting(void *obj);
 private:
	 bool bTrigerOnExecuting;	

 };

 /// 锁标签数据指令
 /// </summary>
class LockUserData_6B : public BaseMessage
 {
public: 
	unsigned char antenna;
	 unsigned char ptr;
	 unsigned char tagID[128];
	 unsigned char tagIDLen;
	 unsigned char lockInfo[256];
	 unsigned char lockInfoLen;
	 unsigned char lockResult[256];
	 unsigned char lockResultLen;
	 int count;
	 /// <summary>
	 /// 构造函数
	 /// </summary>
	 /// <param name="antenna">天线号</param>
	 /// <param name="tagID">标签ID号</param>
	 /// <param name="lockInfo">锁定信息，长度与标签用户数据区长度一致，每个字节信息表示是否对该字节进行锁定操作，非0为锁定该位置的用户数据
	 ///                        注意：锁定后不能解锁</param>
	LockUserData_6B(unsigned char antenna, unsigned char *tagID,unsigned char tagIDLen, unsigned char *lockInfo,unsigned char lockInfoLen);
	virtual void TrigerOnExecuting(void *obj);
private:
	 bool bTrigerOnExecuting;	


};


/// 查询标签数据锁状态指令
/// </summary>
class LockStateQuery_6B : public BaseMessage
{
public:
	unsigned char  antenna;
	unsigned char  ptr;
	unsigned char  tagID[128];
	unsigned char tagIDLen;
	unsigned char  queryInfo[512];
	unsigned char  queryInfoLen;
	unsigned char lockState[512];
	unsigned char lockStateLen;
	int count;

	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="antenna">天线号</param>
	/// <param name="tagID">标签ID号</param>
	/// <param name="queryInfo">查询信息，长度与标签用户数据区长度一致，每个字节信息表示对该字节是否执行查询操作，非0为执行操作</param>
   LockStateQuery_6B(unsigned char antenna, unsigned char *tagID,unsigned char tagIDLen, unsigned char *queryInfo,unsigned char queryInfoLen);
   virtual void TrigerOnExecuting(void *obj);
   virtual bool GetLockResult(unsigned char *LockResult,unsigned char &LockResultLen);
private:
	 bool bTrigerOnExecuting;	 		

};
/// 查询读写器系统配置指令
class SysQuery_800 : public BaseMessage
{
public:
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="parameter">查询读写器系统配置指令参数类型</param> 
    SysQuery_800(unsigned char parameter);
	
	/// 构造函数
	/// </summary>
	/// <param name="parameter">查询读写器系统配置指令参数类型</param>
	/// <param name="data">保留域默认为00H,扩展用</param>
    SysQuery_800(unsigned char parameter, unsigned char data);
	
	virtual bool GetQueryData(unsigned char *data,unsigned char &dataLen);
};


/// <summary>
/// 配置读写器系统配置指令
/// </summary>
class SysConfig_800 : public  BaseMessage
{
public:
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="parameter">配置读写器系统配置指令参数类型</param>
	/// <param name="pData">配置数据</param>
	SysConfig_800(unsigned char  parameter, unsigned char *pData,unsigned char dataLen);


 };  

/// <summary>
/// IO输出操作指令
/// </summary>
class Gpo_800 : public BaseMessage
{
public:
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="ioPort">输出端口(IO_OutputPort)</param>
	/// <param name="level">输出电平(IO_OutDianPing)</param>
	Gpo_800(unsigned char ioPort, unsigned char level);
	

};

/// IO输入查询指令
/// </summary>
class Gpi_800 : public BaseMessage
{
public:
	Gpi_800();
	Gpi_800(unsigned char  checkMode);
	virtual bool GetQueryData(unsigned char *data,unsigned char &dataLen);

};

/// <summary>
/// 读写器测试模式设置指令
/// </summary>
class TestModeConfig_800 : public BaseMessage
{
	/// <param name="parameter">读写器测试模式设置指令的测试模式</param>
	/// <param name="pData">测试参数</param>
public:
	TestModeConfig_800(unsigned char  parameter, unsigned char *pData,unsigned char DataLen);


};

/// 读写器基带升级指令
class FirmwareUpgrade_800 : public BaseMessage 
{
public:
	FirmwareUpgrade_800();
	
};

/// 读写器重启指令
class ResetReader_800 : public  BaseMessage 
{
public:
	ResetReader_800();

};

/// <summary>
/// 读写器系统参数校调指令
/// </summary>
class SysCheck_800 : public  BaseMessage
{
	/// <param name="parameter">读写器系统参数校调指令的校调类型（Sys_Check）</param>
	/// <param name="pData">校调参数</param>
public:
	SysCheck_800(unsigned char parameter, unsigned char *pData,unsigned char DataLen);
	    

};

class PowerOff_800 :  public  BaseMessage
{
public: 
	PowerOff_800();
	virtual void TrigerOnExecuting(void *obj);
	PowerOff_800(ReadTag *readTag);
private:
	bool bTrigerOnExecuting;
	ReadTag *readTag;
	
 };

class PowerOff_500 : public  BaseMessage
{
	
	public: 
		ReadTag *readTag;
		PowerOff_500();
		PowerOff_500(ReadTag *readTag);
		virtual void TrigerOnExecuting(void *obj);
	private:
		bool bTrigerOnExecuting;
};

/// <summary>
/// 开功放指令
/// </summary>
class PowerOn_500 : public BaseMessage
{
	/// 构造函数
	/// </summary>
	/// <param name="antenna">天线号</param>
public:
	PowerOn_500(unsigned char antenna);
};


/// <summary>
/// 查询读写器系统配置指令
/// </summary>
class SysQuery_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">系统配置信息类型</param>
	/// <param name="infoLength">系统配置信息类型长度</param>
public:
	SysQuery_500(unsigned char  infoType, unsigned char infoLength);
	virtual bool GetQueryData(unsigned char *data,unsigned char &dataLen);

};

/// 配置读写器系统配置指令
class SysConfig_500 : public BaseMessage
{
	/// <param name="infoType">系统配置信息类型</param>
	/// <param name="infoLength">系统配置信息类型长度</param>
	/// <param name="pData">配置数据</param>
public:
	SysConfig_500(unsigned char infoType, unsigned char infoLength, unsigned char *pData,unsigned char DataLen);

    
};

/// 频率设置/查询
/// </summary>
class FreqConfig_500 : public BaseMessage
{
	/// 构造函数
	/// </summary>
	/// <param name="infoType">信息类型：0x10设置，0x11查询</param>
	/// <param name="infoParam">信息参数</param>
public:
	FreqConfig_500(unsigned char infoType, unsigned char *infoParam,unsigned char infoParamLen);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
};

/// <summary>
/// 模式设置（原始或衍生）指令
/// </summary>
class BaudRateMode_500 : public BaseMessage
{
	/// </summary>
	/// <param name="infoType">信息类型:原始(0x00)或衍生(0x01)</param>
public:
	BaudRateMode_500(unsigned char infoType);
};

/// <summary>
/// 工作模式设置/查询
/// </summary>
class WorkModeSet_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">工作模式信息类型</param>
	/// <param name="infoParameter">工作模式信息类型参数</param>
public:
	WorkModeSet_500(unsigned char infoType, unsigned char infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);

};

class RssiLimitConfig_500  : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">工作模式信息类型</param>
	/// <param name="infoParameter">工作模式信息类型参数</param>
public:
	RssiLimitConfig_500 (unsigned char infoType, unsigned char *infoParameter,unsigned char infoParameterLen);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

class Buzzer_500 : public BaseMessage
{
	/// 构造函数
	/// </summary>
	/// <param name="infoType">蜂鸣器控制信息类型</param>
public:
	Buzzer_500(unsigned char infoType);
	
};

/// 温度/湿度检测指令
/// </summary>
 class TemperatureAndHumidityQuery_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">信息类型:温度(0x00),湿度(0x01)</param>
public:
    TemperatureAndHumidityQuery_500(unsigned char  infoType);
	virtual bool GetQueryData(unsigned char *pData,unsigned char &dataLen);
	virtual int GetTemperature();
	virtual int GetHumidity();

};

class AntennaInspect_500 : public BaseMessage
{
public:
	AntennaInspect_500();
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
};


/// <summary>
/// 工作模式设置/查询
/// </summary>
class IODevices_500  : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
  /// <param name="infoType">IO设备控制信息类型</param>
        /// <param name="infoParameter">IO设备控制信息类型参数</param>
public:
	IODevices_500 (unsigned char infoType, unsigned char infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

/// 设置/查询数据发送时间
class DataSentTime_500   : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
 /// <param name="infoType">操作类型：0,设置;1,查询（此时，时间域的两个字节无意义）</param>
        /// <param name="infoParameter">时间</param>
public:
	DataSentTime_500(unsigned char infoType, unsigned char *infoParameter,unsigned char infoParameterLen);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

/// 设置/查询继电器的初始状态
class RelayStartState_500    : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">操作类型：0,设置;1,查询（此时，状态域的一个字节无意义）</param>
        /// <param name="infoParameter">状态：    0：  继电器的常态为断开;1：  继电器的常态为接通</param>
public:
	RelayStartState_500(unsigned char infoType, unsigned char infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

 /// 调整功率参数 设置/查询
class PowerParamConfig_500    : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
 /// <param name="infoType">信息类型：0设置，1查询</param>
        /// <param name="infoParam">信息参数:其值需介于0x2000和0x2fff之间</param> 
public:
	PowerParamConfig_500 (unsigned char infoType, unsigned char *infoParameter,unsigned char infoParameterLen);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

/// <summary>
/// 恢复系统默认配置
/// </summary>
class ResetToFactoryDefault_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>       
public:
	ResetToFactoryDefault_500();
	
    
};


/// <summary>
/// 设置/查询数据的发送方式
/// </summary>
class DataSentMode_500 : public BaseMessage
{
	/// <param name="infoType">操作类型：0,设置;1,查询（此时，状态域的一个字节无意义）</param>
	/// <param name="infoParameter">状态： 0：  数据通过韦根输出------天线1-韦根1；天线2-韦根2</param>
	/// <param name="infoParameter">状态： 1：  数据通过韦根输出------天线1-韦根1；天线2-韦根1</param>
	/// <param name="infoParameter">2：  数据通过韦根输出------天线1-韦根2；天线2-韦根2</param>
	/// <param name="infoParameter">3：  数据通过韦根输出------天线1-韦根1、2；天线2-韦根1、2</param>
	/// <param name="infoParameter">4：  数据通过继电器控制------天线1-继电1；天线2-继电2</param>
	/// <param name="infoParameter">5：  数据通过继电器控制------天线1-继电1；天线2-继电1</param>
	/// <param name="infoParameter">6：  数据通过继电器控制------天线1-继电2；天线2-继电2</param>
	/// <param name="infoParameter">7：  数据通过继电器控制------天线1-继电1、2；天线2-继电1、2</param>
	
public:
	DataSentMode_500(unsigned char infoType, unsigned char  infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
};

/// <summary>
/// 白名单配置
/// </summary>
class WhiteList_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="setType">配置类型： 1=启用白名单功能；其他：取消白名单功能；</param>            
public:
	WhiteList_500(unsigned char setType);


};

/// <summary>
/// 白名单下载
/// </summary>
class WhiteListDownLoad_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="data">数据内容：wget -c -O username ftp://username:password@IP/filename </param>
public:
	WhiteListDownLoad_500(unsigned char  *data,unsigned char  dataLen);
	
};



/// <summary>
/// 开机读卡模式设置
/// </summary>
class StartReaderAndReading_500 : public BaseMessage
{

	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="data">0x00:开机不读卡；0x01:开机读卡；</param>  
public:
	StartReaderAndReading_500(unsigned char  data);

};

 /// 韦根模式设置/查询
class WiegandMode_500     : public BaseMessage
{
   /// <param name="infoType">信息类型：0设置，1查询</param>
        /// <param name="infoParam">信息参数：0韦根26工作模式，1韦根34工作模式</param>
public:
	WiegandMode_500 (unsigned char infoType, unsigned char infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

  /// 触发读卡模式设置/查询
class ReadModeTrigger_500 : public BaseMessage
{
       /// <param name="infoType">信息类型：0设置，1查询</param>
	/// <param name="infoParam">信息参数：
	/// 0不需要触发读卡；
	/// 1输入IO口1触发读卡；
	/// 2输入IO口2触发读卡；
        /// 3输入IO口1或2都可以触发读卡</param>
public:
	ReadModeTrigger_500 (unsigned char infoType, unsigned char infoParameter);
	virtual bool GetQueryData(unsigned char *pdata,unsigned char &dataLen);
	
};

/// <summary>
/// 读写器系统复位
/// </summary>
class ResetReader_500 : public BaseMessage
{
public:
	ResetReader_500();
};

/// 触发读卡模式设置/查询
class PcSendTime_500 : public BaseMessage
{
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="readerID">读写器ID，根据读写器发来的ID返回</param>
	/// <param name="time">时隙</param>
public:
	PcSendTime_500(unsigned char *readerID, unsigned char readerIDLen,unsigned char *time,unsigned char timeLen);
	PcSendTime_500();
	virtual bool GetReaderID(unsigned char *readId,unsigned char &readIdLen);
		 
};



/// 白名单查询
/// </summary>
class WhiteListQuery_500 : public BaseMessage
{
	/// <param name="data">查询信息：00，00 =查询状态和记录条数；
	/// 其他=查询具体的条目内容（条目的序号不能大于总的条目数）</param>            
public:
	WhiteListQuery_500(unsigned char *data,unsigned char dataLen);
	virtual unsigned char GetFlag();
	virtual bool GetCount(unsigned char *count,unsigned char countLen);
	virtual bool GetContent(unsigned char *Content,unsigned char ContentLen);
};

//服务器/客户端状态查询
class ServerClientQuery_500 : public BaseMessage
{
	public:
		ServerClientQuery_500();
		virtual bool GetReaderID(unsigned char *readId,unsigned char &readIdLen);
		virtual unsigned char GetTcpIpStatus();
		virtual bool GetInfo(unsigned char *info,unsigned char &infoLen);
};

//客户端模式服务器IP设置
class PcIpsConfig_500 : public BaseMessage
{
	public:
		PcIpsConfig_500(unsigned char serverCount,unsigned char *ips,unsigned char ipsLen);
};

//客户端模式服务器IP查询
class PcIpsQuery_500 : public BaseMessage
{
	public:
		PcIpsQuery_500();
		virtual unsigned char GetServerCount();
		virtual bool GetIpsInfo(unsigned char *ipsInfo,unsigned char &ipsInfoLen);
		
};

//查询DHCP功能
class QueryDhcp_500 : public BaseMessage
{
	public:
		QueryDhcp_500();
		
		virtual unsigned char GetIpsInfo();
};

/// 开启DHCP功能

class EnableDhcp_500 : public BaseMessage
{
	public:
		EnableDhcp_500();
};

//关闭DHCP功能
class DisableDhcp_500 : public BaseMessage
{
	public:
		DisableDhcp_500();
		/// <param name="netInfo">12个字节，分别为IP地址、子网掩码及网关三个部分。在关闭DHCP功能后，就是静态设置IP地址。</param>
		DisableDhcp_500(unsigned char *netInfo,unsigned char netInfoLen);
};

//配置读写器系统信息F6By
class FrequencyTableConfig_F6B : public BaseMessage
{
	public:
		 /// <param name="infoType">系统配置信息类型：00设置，01查询</param>
        /// <param name="param">信息参数（2字节）</param>
		FrequencyTableConfig_F6B(unsigned char infoType,unsigned char *param,unsigned char paramLen);
		FrequencyTableConfig_F6B();
		virtual unsigned char GetInfoType();
		virtual bool GetFrequencyTable(unsigned char *frequencyTable,unsigned char &frequencyTableLen);
};
 /// 功率表重置
 class FrequencyTableReset_F6B : public BaseMessage
 {
	public:
		FrequencyTableReset_F6B();
 };


 class AutoRead6CTagConfig_800 : public BaseMessage
 {
	public:
		 AutoRead6CTagConfig_800(unsigned char infoType);
		 /// <param name="infoType">信息类型:0为设置，1为查询</param>
		 /// <param name="infoParam">信息参数</param>
		 /// 信息参数：
		 /// 第1个字节读EPC开关，0=不读取EPC码，1=读取EPC码
		 /// 第2个字节读TID开关，0=不读取TID码，1=读取TID码
		 /// 第3个字节读TID码的字长度
		 /// 第4个字节读USR数据开关，0=不读取USR，1=读取USR
		 /// 第5个字节读USR数据区的字长度
		 /// <param name="triggerPort">触发端口</param>
		 /// <param name="triggerType">触发方式</param>
		 /// <param name="antenna">天线</param>
		 /// <param name="q">Q</param>
		 AutoRead6CTagConfig_800(unsigned char infoType, unsigned char *infoParam,unsigned char infoParamLen, unsigned char triggerPort, unsigned char triggerType, unsigned char antenna, unsigned char q);
		 virtual unsigned char GetInfoType();
		 virtual bool GetInfoParam(unsigned char *pInfoParam,unsigned char pInfoParamLen);
		 virtual unsigned char GetTriggerPort();
		 virtual unsigned char GetTriggerType();
		 virtual unsigned char GetAntenna();
		 virtual unsigned char GetQ();
 };



 /// <summary>
 /// 自动读取停止条件
 /// </summary>
 class StopTrigger_800  : public BaseMessage
 {	

	public:
		StopTrigger_800(unsigned char triggerType, unsigned char *param,unsigned char paramLen);


 };

//板机信号
class  RXD_TriggerSignal_800 : public BaseMessageNotification
{
	public:
		RXD_TriggerSignal_800();
		virtual bool GetIsReadTag();
};

//自动读卡信号
class RXD_AutoReadTagSignal_800 : public BaseMessageNotification
{

	public:
		RXD_AutoReadTagSignal_800();
		virtual bool GetIsReadTag();
};

/// I/O信号
class RXD_IOSignal_800 : public BaseMessageNotification
{
	public:
		RXD_IOSignal_800();
	/// 获取 输入状态
	virtual unsigned char GetState();	
	/// 获取 输出状态
	virtual unsigned char GetPort();
	/// 获取 查询时间
	virtual bool GetTime(unsigned char *pTime,unsigned char TimeLen);
	/// 获取 读取时间字符串
	virtual CString GetTimeStr();

private:
	 double HexConverToDouble(CString hexString);
	 CString strData(char *buf);
	 CString getMon(CString strMonValue);
	 long bytesToLong(char * buf);
};

 // 服务器/客户端状态设置
class ServerClientConfig_500 : public BaseMessage
{
	public:
		/// <param name="type">服务器/客户端模式</param>
		/// <param name="ports">两个端口号</param>
		ServerClientConfig_500(unsigned char  type, unsigned char *ports,unsigned char portsLen);
		virtual unsigned char GetReaderType();

};

//
class RXD_TID_6C_2 : public BaseMessage
{

	public:
		/// <param name="readerID">读写器ID</param>
		/// <param name="dataNO">数据序列号</param>
		RXD_TID_6C_2(unsigned char *readerID,unsigned char readerIDLen,unsigned char *dataNO,unsigned char dataNOLen);
		virtual bool GetReaderID(unsigned char *pReaderID,unsigned char readerIDLen);
		virtual bool GetDataNO(unsigned char *pDataNO,unsigned char DataNOLen);
		virtual bool GetTID(unsigned char *pTID,unsigned char TIDLen);
		virtual bool GetTime(unsigned char *pTime,unsigned char TimeLen);
		virtual unsigned char GetAntenna();
};


//806心跳包
class Keepalive : public BaseMessage
{

public:
	Keepalive();
	Keepalive(unsigned char *sequence,unsigned char sequenceLen,unsigned char *utc,unsigned char utcLen);

	virtual bool GetIntervalTime(unsigned char *intervalTime,unsigned char &intervalTimeLen);
	virtual bool GetSequence(unsigned char *sequence,unsigned char &sequenceLen);
};

class ReadTagTriggerConfig_800:public BaseMessage
{
	
	
public:
	/// <summary>
	/// 构造函数
	/// </summary>
	/// <param name="infoType">信息类型:0为设置，1为查询</param>
	/// <param name="infoParam">信息参数</param>
	/// 第1个字节6C 读卡轮数   (0表示不读6C标签)
	/// 第2个字节读6C TID码的字长度
	/// 第3个字节6C保留区长度  (单位: 字)
	/// 第4个字节读6C USR数据区起始地址(EVB格式 N字节)
	/// 第5+N个字节读6C USR数据区的字长度
	/// 第6+N个字节6C访问密码       (4字节)
	/// 第10个字节6B 读卡轮数	(0表示不读6B标签)
	/// 第11个字节读6B USR数据区起始地址(EVB格式 N字节)
	/// 第12个字节读6B USR数据区的字长度
	/// <param name="triggerPort">触发端口</param>
	/// 0x01     端口1
	/// 0x02     端口2
	/// 0x03	 端口3
	/// 0x04     端口4
	/// <param name="triggerType">触发方式</param>
	/// 0x00     永不触发
	/// 0x01     高电平触发
	/// 0x02     低电平触发
	/// 0x03     上升沿触发
	/// 0x04     下降沿触发
	/// <param name="antenna">天线</param>
	/// <param name="q">Q</param>

	ReadTagTriggerConfig_800(
		unsigned char infoType,
		unsigned char readTimes6C,
		unsigned char tidLength6C,
		unsigned char reservedMemoryLength6C,
		int userDataPtr6C,
		unsigned char userDataLength6C,
		unsigned char * accessPwd6C,
		unsigned char accessPwd6CLen,
		unsigned char readTimes6B,
		int userDataPtr6B,
		unsigned char userDataLength6B,
		unsigned char q,
		unsigned char * portInfo,
		unsigned char portInfoLen);

	ReadTagTriggerConfig_800(unsigned char  infoType);

	virtual unsigned char GetInfoType();
	virtual unsigned char GetReadTimes6C();
	virtual unsigned char GetTidLength6C();
	virtual unsigned char GetReservedMemoryLength6C();
	virtual unsigned int GetUserDataPtr6C();
	virtual unsigned char GetUserDataLength6C();
	virtual	bool GetAccessPwd6C(unsigned char* pwd,unsigned char &pwdLen);
	virtual unsigned char GetReadTimes6B();
	virtual unsigned int GetUserDataPtr6B();
	virtual unsigned char GetUserDataLength6B();
	virtual unsigned char GetQ();
	virtual bool GetPortInfo(unsigned char* portInfo,unsigned char &PortInfoLen);
private:
	int  userDataPtr6C_EvbLen ;
	unsigned int  userDataPtr6C ;
	int userDataPtr6B_EvbLen;
	unsigned int userDataPtr6B;
};

class RXD_IOTriggerSignal_800 : public BaseMessageNotification
{

	public:
		RXD_IOTriggerSignal_800();
		virtual unsigned char GetGPIPort();
		virtual bool GetIsStart();
		virtual bool GetUTCTime(unsigned char *pUTCTime,unsigned char &UTCTimeLen);
		//virtual unsigned char GetGPIState();
		//virtual unsigned char GetIsReadTag();
};

 class PowerOff : public BaseMessage
 {
	public:
		PowerOff();
		virtual void TrigerOnExecuting(void *obj);

	private:

	 	bool bTrigerOnExecuting;
		void *m_reader;
 };

 class PowerOn : public BaseMessage
 {

	public:
		PowerOn(unsigned char antenna);
		virtual void TrigerOnExecuting(void *obj);

	private:

		 bool bTrigerOnExecuting;
		 void *m_reader;
		 unsigned char m_antenna;
 };


 class CustomBusinessControl : public BaseMessage
 {

	 public:
		 CustomBusinessControl(); 
		 CustomBusinessControl(unsigned char *type,unsigned char typeLen, unsigned char param, unsigned char *data,unsigned char dataLen);
		 virtual unsigned char Result();
 private:


 };


 class CustomBusinessData : public BaseMessage
 {
	 public:
		 
		 CustomBusinessData();
		 CustomBusinessData(unsigned char *type,unsigned char typeLen, unsigned char *msgID,unsigned char msgIDLen);	
		 virtual bool GetType(unsigned char *Type,unsigned char &TypeLen);
		 virtual bool GetMessageID(unsigned char *MessageID,unsigned char &MessageIDLen);
		 virtual unsigned char GetDataType();
		 virtual bool GetData(unsigned char *Data,unsigned char &DataLen);
	
	private:

 };

 class ReaderVoltage : public BaseMessage
 {
	public:
		ReaderVoltage();
		virtual double GetVoltageValue();


	private:




 };

 /// <summary>
    /// 读写器蜂鸣
    /// </summary>
    class ReaderBeep : public BaseMessage
    {
    
        /// <summary>
        /// 构造函数
        /// </summary>
        /// <param name="enabled">是否启用</param>
        /// <param name="beepTime">蜂鸣时间（beepTime * 10ms）,0表示一直蜂鸣</param>
	public:
		ReaderBeep();
        ReaderBeep(bool enabled, int beepTime);
	
    };

    /// <summary>
    /// 读写器与hub通信设置
    /// </summary>
    class HubComm : public BaseMessage
    {
    
        /// <summary>
        /// 构造函数
        /// </summary>
        /// <param name="hubNum">Hub号,01H~04H</param>
        /// <param name="antennaNum">Hub内天线端口,自然数表示天线号</param>
        public:
		HubComm(unsigned char hubNum, unsigned char antennaNum);
        HubComm();

    };

	class ResetToFactoryDefault : public BaseMessage
    {
        
        /// <summary>
        /// 构造函数
        /// </summary>  
		public:
			ResetToFactoryDefault();

    };

    /// <summary>
    /// 温度检测指令
    /// </summary>
    class TemperatureQuery : public BaseMessage
	{
		public:
		TemperatureQuery();
		virtual CString ToTemperatureString();
	};
	class FirmwareUpgrading : public BaseMessage
	{
		public:
		 FirmwareUpgrading(int ptr, unsigned char * data,int datalen);
	};

#endif
