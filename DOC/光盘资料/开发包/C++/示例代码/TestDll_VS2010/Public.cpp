#include "stdafx.h"
#include "Public.h"

//OutPut窗口字串跟踪显示，参数为CString型，在TRACE基础上加了时间显示;
void Debug_TRACE(CString strInBuf)
{
	CString strBuf;
	unsigned long time = GetTickCount();

	strBuf.Format("%d <====> ",time);
	strBuf += strInBuf;
	strBuf += "\n";
	TRACE(strBuf);
}
