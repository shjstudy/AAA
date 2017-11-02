#ifndef _MESSAGES_TYPE_H
#define  _MESSAGES_TYPE_H
#include "Markup.h"
#include <map>
#include <string>
class MessageType
{
	 public:
		 MessageType();
		 
		 static CString GetKeyIntValue(unsigned int key);
		 static unsigned int GetKeyStrValue(CString key);
		 static unsigned int GetReadTagKeyValue(CString key);
		 static void ReleaseMap();
		 virtual ~MessageType();
		 static bool ParaeXml(CString strPath);
     private:
	

 };
#endif