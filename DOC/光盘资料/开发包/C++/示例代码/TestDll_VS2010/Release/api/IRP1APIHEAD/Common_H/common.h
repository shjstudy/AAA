#ifndef COMMON_HHH
#define COMMON_HHH

extern void Debug_TRACE(char *format,...);
extern void debuglog(char* format,...);
extern void Debug_Output(char *szFormat, ...);
CString GetModuleFilePath();
CString GetClassName(unsigned char iMsgId);
#endif