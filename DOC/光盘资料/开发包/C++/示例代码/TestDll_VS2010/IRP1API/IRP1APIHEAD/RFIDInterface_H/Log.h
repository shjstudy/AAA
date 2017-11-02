// Log.h: interface for the Log class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_LOG_H__994E038A_F3FD_4CEC_82AD_2D17CDBA59CE__INCLUDED_)
#define AFX_LOG_H__994E038A_F3FD_4CEC_82AD_2D17CDBA59CE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <iostream>


class Log  
{
public:
	Log();
	virtual ~Log();
	
	//typedef enum {EMERG  = 0, 
	//				FATAL  = 0,
	//                ALERT  = 100,
	///              CRIT   = 200,
	//           ERROR  = 300, 
	//         WARN   = 400,
	//       NOTICE = 500,
	//     INFO   = 600,
	//   DEBUG  = 700,
	// NOTSET = 800
//} PriorityLevel;
 
	static void iniLog(const char *pOutputFilename,bool bWriteLog);
	static void realseLog();
	static void writeDebugLog(CString strInfo,CString strHead);
	static void writeInfoLog(CString strInfo,CString strHead);
	static void writeWarnLog(CString strInfo,CString strHead);
	static void writeErrorLog(CString strInfo,CString strHead);
	static void writeAlertLog(CString strInfo,CString strHead);
	static void writeFatalLog(CString strInfo,CString strHead);



private:
	static void writeLog(CString strInfo,CString strHead,int type);


};

#endif // !defined(AFX_LOG_H__994E038A_F3FD_4CEC_82AD_2D17CDBA59CE__INCLUDED_)
