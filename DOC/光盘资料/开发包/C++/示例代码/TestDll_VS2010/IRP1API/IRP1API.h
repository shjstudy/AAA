// IRP1API.h : IRP1API DLL 的主头文件
//

#pragma once

#ifndef __AFXWIN_H__
	#error "在包含此文件之前包含“stdafx.h”以生成 PCH 文件"
#endif

#define DLLEXPORT __declspec(dllexport)

#include "resource.h"		// 主符号
#include "Reader.h"
#include "Messages.h"
#include "MessageType.h"
#include "TcpIpListener.h"

// CIRP1APIApp
// 有关此类实现的信息，请参阅 IRP1API.cpp
//

class CIRP1APIApp : public CWinApp
{
public:
	CIRP1APIApp();

// 重写
public:
	virtual BOOL InitInstance();

	DECLARE_MESSAGE_MAP()
};

extern "C" DLLEXPORT Reader* CreateReaderObjA1();

extern "C" DLLEXPORT Reader* CreateReaderObjA2(CString readerName);

extern "C" DLLEXPORT Reader* CreateReaderObjA3(CString readerName,CString portType, CString connStr);

extern "C" DLLEXPORT TcpIpListener* CreateListenerObj();

extern "C" DLLEXPORT PowerOn_800* CreatePowerOn_800(unsigned char antenna);

extern "C" DLLEXPORT PowerOff_800* CreatePowerOff_800();

extern "C" DLLEXPORT PowerOff_800* CreatePowerOff_800Ex(ReadTag *readTag);

extern "C" DLLEXPORT PowerOn_500* CreatePowerOn_500(unsigned char antenna);

extern "C" DLLEXPORT PowerOff_500* CreatePowerOff_500();

extern "C" DLLEXPORT PowerOff_500* CreatePowerOff_500Ex(ReadTag *readTag);

extern "C" DLLEXPORT ReadTag* CreateReadTag6c();

extern "C" DLLEXPORT ReadTag* CreateReadTag6cBank(ReadMemoryBank rmb);

extern "C" DLLEXPORT ReadTag* CreateReadTag6cBankEx(ReadMemoryBank rmb, int readTime, int stopTime, CString stopType);

extern "C" DLLEXPORT ReadTag* CreateReadTag6cBankEx_1(ReadMemoryBank rmb,CString stopType,BOOL isGetOneTag);

extern "C" DLLEXPORT ReadTag* CreateEPC_TID_UserData_6C(unsigned char tidLen, unsigned char userdataPtr, unsigned char userdataLen);

extern "C" DLLEXPORT ReadTag* CreateEPC_TID_UserData_6CEx(unsigned char tidLen, 
														  unsigned char userdataPtr, 
														  unsigned char userdataLen, 
														  int readTime, 
														  int stopTime);

extern "C" DLLEXPORT TagOperationQuery_6C* CreateTagOperationQuery_6C(unsigned char parameter);

extern "C" DLLEXPORT TagOperationConfig_6C* CreateTagOperationConfig_6C(unsigned char parameter,
                                                                        unsigned char *pData,
																		unsigned char dataLen);

extern "C" DLLEXPORT QT_6C* CreateQT_6C(unsigned char antenna, 
										unsigned char *accessPwd,
										unsigned char accessPwdLen, 
										unsigned char opType, 
										unsigned char persistent,
										unsigned char *payload,
										unsigned char payloadLen);

extern "C" DLLEXPORT FastReadTIDConfig_6C* CreateFastReadTIDConfig_6C(unsigned char infoType, unsigned char infoParam);

extern "C" DLLEXPORT ReadUnfixedTidConfig_6C* CreateReadUnfixedTidConfig_6C(unsigned char infoType, unsigned char infoParam);

extern "C" DLLEXPORT FixedTidLengthConfig_6C* CreateFixedTidLengthConfig_6C(unsigned char infoType, unsigned char infoParam);

extern "C" DLLEXPORT ReadTagConfig_6C* CreateReadTagConfig_6C(unsigned char infoType, 
															  unsigned char *infoParam,
															  unsigned char infoParamLen);

extern "C" DLLEXPORT ReadTagConfig_6B* CreateReadTagConfig_6B(unsigned char infoType, unsigned char infoParam);

extern "C" DLLEXPORT IDFilter_6B* CreateIDFilter_6B(unsigned char  *tagID, 
													unsigned char tagIDLen,
													unsigned char *tagMask,
													unsigned char tagMaskLen);

extern "C" DLLEXPORT EasConfig_6C* CreateIEasConfig_6C(unsigned char antenna, 
														unsigned char *accessPwd, 
														unsigned char accessPwdLen, 
														unsigned char flag);


extern "C" DLLEXPORT EasConfig_6C* CreateIEasConfig_6C_2(unsigned char antenna, 
														unsigned char* accessPwd,
														unsigned char accessPwdLen, 
														unsigned char flag, 
														unsigned char *tagID,
														unsigned char tagIDLen,
														MemoryBank tagIDType);

extern "C" DLLEXPORT EpcFilter_6C* CreateEpcFilter_6C(unsigned char  opType, 
														unsigned char *epcData,
														unsigned char epcDataLen, 
														unsigned char *epcMask,
														unsigned char epcMaskLen);

extern "C" DLLEXPORT FilterByTime* CreateFilterByTime(unsigned char opType, 
															unsigned char *time,
															unsigned char timeLen);

extern "C" DLLEXPORT EasAlarm_6C* CreateEasAlarm_6C(unsigned char antenna, unsigned char easCfg);

extern "C" DLLEXPORT RXD_EPC_6C* CreateRXD_EPC_6C();//数据通过回调出去

extern "C" DLLEXPORT RXD_ID_6B* CreateRXD_ID_6B(); //数据通过回调出去

extern "C" DLLEXPORT RXD_TID_6C* CreateRXD_TID_6C();//数据通过回调出去

extern "C" DLLEXPORT RXD_TID_6C_2* CreateRXD_TID_6C_2(unsigned char *readerID,
													  unsigned char readerIDLen,
													  unsigned char *dataNO,
													  unsigned char dataNOLen);

extern "C" DLLEXPORT RXD_EPC_TID_UserData_6C* CreateRXD_EPC_TID_UserData_6C();//数据通过回调出去

extern "C" DLLEXPORT RXD_EPC_TID_UserData_6C_2* CreateRXD_EPC_TID_UserData_6C_2 ();//数据通过回调出去

extern "C" DLLEXPORT RXD_ID_UserData_6B* CreateRXD_ID_UserData_6B();//数据通过回调出去

extern "C" DLLEXPORT RXD_EPC_PC_6C* CreateRXD_EPC_PC_6C();//数据通过回调出去

extern "C" DLLEXPORT SelectTag_6C* CreateSelectTag_6C();

extern "C" DLLEXPORT SelectTag_6C* CreateSelectTag_6CEx(MemoryBank memoryBank,
														unsigned char ptr, 
														unsigned char matchBitLength, 
														unsigned char *tagData,
														unsigned char tagDataLen);

extern "C" DLLEXPORT WriteTag_6C* CreateWriteTag_6C( unsigned char antenna, 
														unsigned char *accessPwd,
														unsigned char accessPwdLen, 
														MemoryBank memoryBank, 
														unsigned char ptr, 
														unsigned char length, 
														unsigned char *data,
														unsigned char dataLen) ;
extern "C" DLLEXPORT WriteTag_6C* CreateWriteTag_6C_2(unsigned char antenna, 
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

extern "C" DLLEXPORT WriteEpc* CreateWriteEpc(unsigned char antenna, 
												unsigned char *pwd,
												unsigned char pwdLen, 
												unsigned char *epcData,
												unsigned char epcDataLen);

extern "C" DLLEXPORT WriteEpc* CreateWriteEpcEx(unsigned char  antenna,
												unsigned char *accessPwd,
												unsigned char accessPwdLen, 
												unsigned char *epcData,
												unsigned char epcDataLen, 
												unsigned char *tagID,
												unsigned char tagIDLen, 
												unsigned char tagIDType);

extern "C" DLLEXPORT ReadUserData_6C* CreateReadUserData_6C(unsigned char  antenna, 
															unsigned char ptr, 
															unsigned char  length);

extern "C" DLLEXPORT ReadUserData_6C* CreateReadUserData_6CEx(unsigned char antenna, 
																unsigned char ptr, 
																unsigned char length,
																unsigned char *tagID, 
																unsigned char tagIDLen,
																unsigned char  tagIDType);

extern "C" DLLEXPORT WriteUserData_6C* CreateWriteUserData_6C(unsigned char antenna, 
																unsigned char *pwd,
																unsigned char pwdLen,
																unsigned char ptr,
																unsigned char *userData,
																unsigned char userDataLen);

extern "C" DLLEXPORT WriteUserData_6C* CreateWriteUserData_6CEx(unsigned char antenna,
																unsigned char *pwd,
																unsigned char pwdLen,
																unsigned char ptr, 
																unsigned char *userData,
																unsigned char userDataLen,
																unsigned char* tagID,
																unsigned char tagIDLen, 
																MemoryBank  tagIDType);

extern "C" DLLEXPORT BlockWrite_6C* CreateBlockWrite_6C(unsigned char antenna, 
														unsigned char *accessPwd,
														unsigned char accessPwdLen,
														unsigned char memoryBank,
														unsigned char ptr, 
														unsigned char *data,
														unsigned char dataLen);

extern "C" DLLEXPORT BlockWrite_6C* CreateBlockWrite_6CEx(unsigned char antenna,
															unsigned char *accessPwd,
															unsigned char accessPwdLen,
															unsigned char memoryBank, 
															unsigned char ptr, 
															unsigned char *data,
															unsigned char dataLen,
															unsigned char* tagID,
															unsigned char tagIDLen, 
															MemoryBank tagIDType);

extern "C" DLLEXPORT BlockErase_6C* CreateBlockErase_6C(unsigned char antenna, 
														unsigned char *accessPwd, 
														unsigned char accessPwdLen,
														unsigned char memoryBank, 
														unsigned char ptr, 
														unsigned char dataLength);

extern "C" DLLEXPORT BlockErase_6C* CreateBlockErase_6CEx(unsigned char antenna, 
															unsigned char *accessPwd,
															unsigned char accessPwdLen,
															unsigned char memoryBank, 
															unsigned char ptr, 
															unsigned char dataLength,
															unsigned char* tagID,
															unsigned char tagIDLen, 
															MemoryBank tagIDType);

extern "C" DLLEXPORT AccessPwdConfig_6C* CreateAccessPwdConfig_6C(unsigned char antenna, 
																	unsigned char  *oldPwd,
																	unsigned char oldPwdLen,
																	unsigned char *newPwd,
																	unsigned char newPwdLen);

extern "C" DLLEXPORT AccessPwdConfig_6C* CreateAccessPwdConfig_6CEx(unsigned char antenna, 
																	unsigned char  *oldPwd, 
																	unsigned char oldPwdlen,
																	unsigned char *newPwd,
																	unsigned char newPwdLen,
																	unsigned char* tagID,
																	unsigned char tagIDLen, 
																	MemoryBank tagIDType);

extern "C" DLLEXPORT KillPwdConfig_6C* CreateKillPwdConfig_6C(unsigned char antenna, 
																unsigned char  *accessPwd, 
																unsigned char accessPwdLen,
																unsigned char *killPwd,
																unsigned char killPwdLen);

extern "C" DLLEXPORT KillPwdConfig_6C* CreateKillPwdConfig_6CEx(unsigned char antenna, 
																unsigned char  *accessPwd,
																unsigned char accessPwdLen,
																unsigned char *killPwd,
																unsigned char killPwdLen,
																unsigned char* tagID,
																unsigned char tagIDLen, 
																MemoryBank tagIDType);

extern "C" DLLEXPORT LockMemoryBank_6C* CreateLockMemoryBank_6C(unsigned char antenna, 
																unsigned char *accessPwd, 
																unsigned char accessPwdLen,
																unsigned char lockType, 
																unsigned char  memoryBank);

extern "C" DLLEXPORT LockMemoryBank_6C* CreateLockMemoryBank_6CEx(unsigned char antenna, 
																	unsigned char *accessPwd, 
																	unsigned char accessPwdLen,
																	unsigned char lockType, 
																	unsigned char  memoryBank,
																	unsigned char* tagID,
																	unsigned char tagIDLen, 
																	MemoryBank tagIDType);

extern "C" DLLEXPORT BlockPermalock_6C* CreateBlockPermalock_6C(unsigned char antenna, 
																unsigned char *accessPwd, 
																unsigned char accessPwdLen,
																unsigned char memoryBank, 
																unsigned char readLock, 
																unsigned char ptr, 
																unsigned char blockRange, 
																unsigned char readLockbits, 
																unsigned char *mask);

extern "C" DLLEXPORT BlockPermalock_6C* CreateBlockPermalock_6CEx(unsigned char antenna, 
																	unsigned char *accessPwd, 
																	unsigned char accessPwdLen,
																	unsigned char memoryBank, 
																	unsigned char readLock, 
																	unsigned char ptr, 
																	unsigned char blockRange, 
																	unsigned char readLockbits, 
																	unsigned char *mask,
																	unsigned char* tagID,
																	unsigned char tagIDLen, 
																	MemoryBank tagIDType);

extern "C" DLLEXPORT KillTag_6C* CreateKillTag_6C(unsigned char antenna, 
													unsigned char *killPwd, 
													unsigned char killPwdLen,
													unsigned char *epcData,
													unsigned char epcDataLen);

extern "C" DLLEXPORT ReadUserData_6B* CreateReadUserData_6B(unsigned char antenna, 
															unsigned char *tagID,
															unsigned char tagIDLen,
															unsigned char ptr, 
															unsigned char length);

extern "C" DLLEXPORT WriteUserData_6B* CreateWriteUserData_6B(unsigned char antenna, 
																unsigned char *tagID,
																unsigned char tagIDLen, 
																unsigned char ptr, 
																unsigned char *userdata,
																unsigned char userdataLen);

extern "C" DLLEXPORT ReadUserData2_6B* CreateReadUserData2_6B(unsigned char  antenna, 
																unsigned char *tagID,
																unsigned char tagIDLen, 
																unsigned char ptr, 
																unsigned char length);

extern "C" DLLEXPORT WriteUserData2_6B* CreateWriteUserData2_6B(unsigned char antenna, 
																unsigned char *tagID,
																unsigned char tagIDLen, 
																unsigned char ptr, 
																unsigned char *userData,
																unsigned char userDataLen);

extern "C" DLLEXPORT LockUserData_6B* CreateLockUserData_6B(unsigned char antenna, 
															unsigned char *tagID,
															unsigned char tagIDLen, 
															unsigned char *lockInfo,
															unsigned char lockInfoLen);

extern "C" DLLEXPORT LockStateQuery_6B* CreateLockStateQuery_6B(unsigned char antenna, 
																unsigned char *tagID,
																unsigned char tagIDLen, 
																unsigned char *queryInfo,
																unsigned char queryInfoLen);

extern "C" DLLEXPORT SysQuery_800* CreateSysQuery_800(unsigned char parameter);

extern "C" DLLEXPORT SysQuery_800* CreateSysQuery_800Ex(unsigned char parameter, unsigned char data);

extern "C" DLLEXPORT SysConfig_800* CreateSysConfig_800(unsigned char  parameter, 
														unsigned char *pData,
														unsigned char dataLen);

extern "C" DLLEXPORT Gpo_800* CreateGpo_800(unsigned char ioPort, unsigned char level);

extern "C" DLLEXPORT Gpi_800* CreateGpi_800();

extern "C" DLLEXPORT Gpi_800* CreateGpi_800Ex(unsigned char  checkMode);

extern "C" DLLEXPORT TestModeConfig_800* CreateTestModeConfig_800(unsigned char  parameter,
																	unsigned char *pData,
																	unsigned char DataLen);

extern "C" DLLEXPORT ResetReader_800* CreateResetReader_800();

extern "C" DLLEXPORT FirmwareUpgrade_800* CreateFirmwareUpgrade_800();

extern "C" DLLEXPORT SysCheck_800* CreateSysCheck_800(unsigned char parameter, 
														unsigned char *pData,
														unsigned char DataLen);

extern "C" DLLEXPORT SysQuery_500* CreateSysQuery_500(unsigned char  infoType, unsigned char infoLength);

extern "C" DLLEXPORT SysConfig_500* CreateSysConfig_500(unsigned char infoType, 
														unsigned char infoLength, 
														unsigned char *pData,
														unsigned char DataLen);

extern "C" DLLEXPORT FreqConfig_500* CreateFreqConfig_500(unsigned char infoType, 
															unsigned char *infoParam,
															unsigned char infoParamLen);

extern "C" DLLEXPORT BaudRateMode_500* CreateBaudRateMode_500(unsigned char infoType);

extern "C" DLLEXPORT WorkModeSet_500* CreateWorkModeSet_500(unsigned char infoType, unsigned char infoParameter);

extern "C" DLLEXPORT RssiLimitConfig_500* CreateRssiLimitConfig_500 (unsigned char infoType, 
																	unsigned char *infoParameter,
																	unsigned char infoParameterLen);

extern "C" DLLEXPORT Buzzer_500* CreateBuzzer_500(unsigned char infoType);

extern "C" DLLEXPORT TemperatureAndHumidityQuery_500* CreateTemperatureAndHumidityQuery_500(unsigned char  infoType);

extern "C" DLLEXPORT AntennaInspect_500* CreateAntennaInspect_500();

extern "C" DLLEXPORT IODevices_500* CreateIODevices_500 (unsigned char infoType, unsigned char infoParameter);

extern "C" DLLEXPORT DataSentTime_500* CreateDataSentTime_500 (unsigned char infoType, 
																unsigned char *infoParameter,
																unsigned char infoParameterLen);

extern "C" DLLEXPORT RelayStartState_500* CreateRelayStartState_500(unsigned char infoType, unsigned char infoParameter);

extern "C" DLLEXPORT PowerParamConfig_500* CreatePowerParamConfig_500(unsigned char infoType, 
																		unsigned char *infoParameter,
																		unsigned char infoParameterLen);

extern "C" DLLEXPORT ResetToFactoryDefault_500* CreateResetToFactoryDefault_500();

extern "C" DLLEXPORT DataSentMode_500* CreateDataSentMode_500(unsigned char infoType, unsigned char  infoParameter);

extern "C" DLLEXPORT WhiteList_500* CreateWhiteList_500(unsigned char setType);

extern "C" DLLEXPORT WhiteListDownLoad_500* CreateWhiteListDownLoad_500(unsigned char  *data,unsigned char  dataLen);

extern "C" DLLEXPORT StartReaderAndReading_500* CreateStartReaderAndReading_500(unsigned char  data);

extern "C" DLLEXPORT WiegandMode_500* CreateWiegandMode_500 (unsigned char infoType, unsigned char infoParameter);

extern "C" DLLEXPORT ReadModeTrigger_500* CreateReadModeTrigger_500 (unsigned char infoType, unsigned char infoParameter);

extern "C" DLLEXPORT ResetReader_500* CreateResetReader_500();

extern "C" DLLEXPORT PcSendTime_500* CreatePcSendTime_500();

extern "C" DLLEXPORT PcSendTime_500* CreatePcSendTime_500Ex(unsigned char *readerID, 
															unsigned char readerIDLen,
															unsigned char *time,
															unsigned char timeLen);

extern "C" DLLEXPORT WhiteListQuery_500* CreateWhiteListQuery_500(unsigned char *data,unsigned char dataLen);

extern "C" DLLEXPORT ServerClientQuery_500* CreateServerClientQuery_500();

extern "C" DLLEXPORT PcIpsConfig_500* CreatePcIpsConfig_500(unsigned char serverCount,unsigned char *ips,unsigned char ipsLen);

extern "C" DLLEXPORT PcIpsQuery_500* CreatePcIpsQuery_500();

extern "C" DLLEXPORT QueryDhcp_500* CreateQueryDhcp_500();

extern "C" DLLEXPORT EnableDhcp_500* CreateEnableDhcp_500();

extern "C" DLLEXPORT DisableDhcp_500* CreateDisableDhcp_500();
extern "C" DLLEXPORT DisableDhcp_500* CreateDisableDhcp_500_2(unsigned char *netInfo,unsigned char netInfoLen);

extern "C" DLLEXPORT FrequencyTableConfig_F6B* CreateFrequencyTableConfig_F6B(unsigned char infoType,
																			  unsigned char *param,
																			  unsigned char paramLen);

extern "C" DLLEXPORT FrequencyTableReset_F6B* CreateFrequencyTableReset_F6B();

extern "C" DLLEXPORT AutoRead6CTagConfig_800* CreateAutoRead6CTagConfig_800_A(unsigned char infoType);

extern "C" DLLEXPORT AutoRead6CTagConfig_800* CreateAutoRead6CTagConfig_800_B(unsigned char infoType,
																			  unsigned char *infoParam,
																			  unsigned char infoParamLen,
																			  unsigned char triggerPort,
																			  unsigned char triggerType, 
																			  unsigned char antenna, 
																			  unsigned char q);

extern "C" DLLEXPORT StopTrigger_800* CreateStopTrigger_800(unsigned char triggerType, unsigned char *param,unsigned char paramLen);

extern "C" DLLEXPORT RXD_TriggerSignal_800* CreateRXD_TriggerSignal_800();

extern "C" DLLEXPORT RXD_AutoReadTagSignal_800* CreateRXD_AutoReadTagSignal_800();

extern "C" DLLEXPORT RXD_IOSignal_800* CreateRXD_IOSignal_800();

extern "C" DLLEXPORT ServerClientConfig_500* CreateServerClientConfig_500(unsigned char  type,
																		  unsigned char *ports,
																		  unsigned char portsLen);

extern "C" DLLEXPORT bool ReleaseMem(void **pClass);


extern "C" DLLEXPORT ReadTagTriggerConfig_800* CreateReadTagTriggerConfig_800(
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

extern "C" DLLEXPORT Keepalive *CreateKeepalive(unsigned char *sequence,unsigned char sequenceLen,unsigned char *utc,unsigned char utcLen);

extern "C" DLLEXPORT PowerOff *CreatePowerOff();
extern "C" DLLEXPORT PowerOn *CreatePowerOn(unsigned char antenna);
extern "C" DLLEXPORT RXD_TagData *CreateRXD_TagData();
extern "C" DLLEXPORT ReaderVoltage *CreateReaderVoltage();
extern "C" DLLEXPORT HubComm *CreateHubComm(unsigned char hubNum, unsigned char antennaNum);
extern "C" DLLEXPORT ReaderBeep* CreateReaderBeep(bool enabled, int beepTime);
extern "C" DLLEXPORT ResetToFactoryDefault* CreateResetToFactoryDefault();
extern "C" DLLEXPORT TemperatureQuery* CreateTemperatureQuery();
extern "C" DLLEXPORT FirmwareUpgrading* CreateFirmwareUpgrading(int ptr, unsigned char * data,int datalen);
