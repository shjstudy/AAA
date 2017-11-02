
#ifndef _ENUMCLASS_H
#define  _ENUMCLASS_H

struct MSG_NET 
{	
	unsigned int len;
	char buf[1024];
};

struct MSG_NET_DEAL
{
	unsigned int len;
	char buf[2048];
};
struct MSG_LIST
{
	unsigned int len;
	char buf[512];
};
enum RFIDPort
{
	RS232,
	TCPIP_Client,
	TCPIP_Server,
	USB,
	RS232_PDA
};

enum RFIDProtocol
{
	IRP1,
	IRP2,
	LLRP1_01,
	LLRP1_1
};

enum ReadMemoryBank
{
	EPC_6C,
	TID_6C,
	EPC_TID_UserData_6C,
	EPC_TID_UserData_6C_2,
	ID_6B,
	ID_UserData_6B,
	EPC_6C_ID_6B,
	TID_6C_ID_6B,
	EPC_TID_UserData_6C_ID_UserData_6B,
	EPC_TID_UserData_Received_6C_ID_UserData_6B,
	EPC_PC_6C

};

enum MemoryBank
{
	/// <summary>
	/// 保留区
	/// </summary>
	ReservedMemory,
	/// <summary>
	/// EPC数据区
	/// </summary>
	EPCMemory,
	/// <summary>
	/// TID数据区
	/// </summary>
	TIDMemory,
	/// <summary>
	/// 用户数据区
	/// </summary>
	UserMemory
};


//种类在最后加
enum RFIDClassName
{
	Class_PowerOn_800 = 0,
	Class_ReadTag,
	Class_TagOperationQuery_6C,
	Class_TagOperationConfig_6C, 
	Class_QT_6C, 
	Class_FastReadTIDConfig_EpcLength,
	Class_FastReadTIDConfig_6C,
	Class_ReadUnfixedTidConfig_6C, 
	Class_FixedTidLengthConfig_6C, 
	Class_ReadTagConfig_6C, 
	Class_ReadTagConfig_6B, 
	Class_IDFilter_6B, 
	Class_EasConfig_6C, 
	Class_EpcFilter_6C, 
	Class_FilterByTime_6C, 
	Class_EasAlarm_6C,
	Class_RXD_EPC_6C,
	Class_RXD_ID_6B, 
	Class_RXD_TID_6C,  
	Class_RXD_EPC_TID_UserData_6C ,
	Class_RXD_EPC_TID_UserData_6C_2,  
	Class_RXD_ID_UserData_6B, 
	Class_RXD_EPC_PC_6C,
	Class_SelectTag_6C,
	Class_WriteTag_6C, 
	Class_WriteEpc, 
	Class_ReadUserData_6C, 
	Class_WriteUserData_6C, 
	Class_BlockWrite_6C, 
	Class_BlockErase_6C,
	Class_AccessPwdConfig_6C, 
	Class_LockMemoryBank_6C, 
	Class_BlockPermalock_6C,
	Class_KillTag_6C,
	Class_ReadUserData_6B, 
	Class_WriteUserData_6B, 
	Class_ReadUserData2_6B,
	Class_WriteUserData2_6B, 
	Class_LockUserData_6B,
	Class_LockStateQuery_6B, 
	Class_SysQuery_800, 
	Class_SysConfig_800, 
	Class_Gpo_800, 
	Class_Gpi_800, 
	Class_TestModeConfig_800, 
	Class_FirmwareUpgrade_800, 
	Class_ResetReader_800, 
	Class_SysCheck_800, 
	Class_PowerOff_800,
	Class_PowerOff_500 ,
	Class_PowerOn_500 ,
	Class_SysQuery_500 ,
	Class_SysConfig_500 ,
	Class_FreqConfig_500,
	Class_BaudRateMode_500,
	Class_WorkModeSet_500,
	Class_RssiLimitConfig_500 , 
	Class_Buzzer_500 ,
	Class_TemperatureAndHumidityQuery_500 ,
	Class_AntennaInspect_500 ,
	Class_IODevices_500 ,
	Class_DataSentTime_500, 
	Class_RelayStartState_500,
	Class_PowerParamConfig_500, 
	Class_ResetToFactoryDefault_500 ,
	Class_DataSentMode_500,
	Class_WhiteList_500 ,
	Class_WhiteListDownLoad_500,
	Class_StartReaderAndReading_500 ,
	Class_WiegandMode_500   ,
	Class_ReadModeTrigger_500, 
	Class_ResetReader_500 ,
	Class_PcSendTime_500 ,
	Class_WhiteListQuery_500,
	Class_ServerClientQuery_500 ,
	Class_PcIpsConfig_500 ,
	Class_PcIpsQuery_500 ,
	Class_QueryDhcp_500 ,
	Class_EnableDhcp_500 ,
	Class_DisableDhcp_500 ,
	Class_FrequencyTableConfig_F6B ,
	Class_FrequencyTableReset_F6B,
	Class_KillPwdConfig_6C,

	Class_AutoRead6CTagConfig_800,
	Class_StopTrigger_800,  
    Class_RXD_TriggerSignal_800, 
	Class_RXD_AutoReadTagSignal_800, 
	Class_RXD_IOSignal_800, 
	Class_ServerClientConfig_500, 
	Class_RXD_TID_6C_2,
	Class_RXD_EPC_TID_UserData_Received_6C,
	Class_ReadTagTriggerConfig_800,
	Class_KeepAlive,
	Class_Reader = 254,
	Class_Listener = 255
};

#endif


