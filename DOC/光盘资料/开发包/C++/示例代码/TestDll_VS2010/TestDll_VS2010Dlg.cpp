
#include "stdafx.h"
#include "TestDll_VS2010.h"
#include "TestDll_VS2010Dlg.h"
#include "afxdialogex.h"

#include "IRP1APIHEAD\IRP1_H\Reader.h"
#include "Public.h"
#include "IRP1API.h"

#include <sys/timeb.h>
#include <time.h>
#if !defined(_WINSOCK2API_) && !defined(_WINSOCKAPI_)
struct timeval 
{
	    long tv_sec;
		long tv_usec;
};
#endif 

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

union{
	long lngSec;
	char bufSec[4];

}nowSec;

union{
	long lngUSec;
	char bufUSec[4];

}nowUSec;

static int gettimeofday(struct timeval* tv) 
{
	union{
		long long ns100;
		FILETIME ft;

	}now;

	GetSystemTimeAsFileTime (&now.ft);
	tv->tv_usec = (long) ((now.ns100 / 10LL) % 1000000LL);
	tv->tv_sec = (long) ((now.ns100 - 116444736000000000LL) / 10000000LL);
	return (0);
}


Reader* c_reader1 = NULL;
Reader* c_reader2 = NULL; 
bool b_SingnalReader = false;
bool b_MultiReader   = false;
HMODULE g_hMod;

unsigned char utagId[128];

struct STRUCTTAG 
{
	CString strTag;
	CString antaner;
	unsigned int times;
};

struct STRUCTTAGNUMS
{
	STRUCTTAG tag[100];
	unsigned int tagCount;
};

STRUCTTAGNUMS mytag;


CTestDll_VS2010Dlg::CTestDll_VS2010Dlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CTestDll_VS2010Dlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CTestDll_VS2010Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CTestDll_VS2010Dlg, CDialogEx)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_Btn_StartTest, &CTestDll_VS2010Dlg::OnClickedBtnStartTest)
	ON_BN_CLICKED(IDC_BTN_ConnOrDisconn, &CTestDll_VS2010Dlg::OnClickedBtnConnOrDisconn)
	ON_BN_CLICKED(IDC_Btn_SelTestItem, &CTestDll_VS2010Dlg::OnClickedBtnSeltestitem)
	ON_WM_CLOSE()
	ON_BN_CLICKED(IDC_BtnMutiReaderConnOrDisc, &CTestDll_VS2010Dlg::OnClickedBtnMutiReaderConnOrDisc)
	ON_WM_TIMER()
	ON_BN_CLICKED(IDC_BTN_CHANGJING, &CTestDll_VS2010Dlg::OnBnClickedBtnChangjing)
END_MESSAGE_MAP()


// CTestDll_VS2010Dlg 消息处理程序

BOOL CTestDll_VS2010Dlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	
	SetIcon(m_hIcon, TRUE);	
	SetIcon(m_hIcon, FALSE);		

	
	//c_reader1 = CreateReaderObjA3("Reader1", "IRP1", "TCPIP_Client", "192.168.7.246:7086");//tcp
	GetDlgItem(IDC_EDIT2)->SetWindowText("TCPIP_Server");
	GetDlgItem(IDC_EDIT3)->SetWindowText("192.168.7.246:7086");
	return TRUE;  
}

void CTestDll_VS2010Dlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); 

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}
HCURSOR CTestDll_VS2010Dlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void AddTag(CString strTag,CString szAntena)
{
	if (mytag.tagCount == 0)
	{
		mytag.tag[0].strTag = strTag;
		mytag.tag[0].times = 1;
		mytag.tag[0].antaner =szAntena;
		mytag.tagCount = 1;
	}
	else
	{
		bool bFlagNew = true;
		int i;
		for (i=0; i<mytag.tagCount; i++)
		{
			if (strTag == mytag.tag[i].strTag)
			{
				bFlagNew =false;
				mytag.tag[i].times++;
				break;
			}
		}

		if (bFlagNew)
		{
			if (i > 100)
			{
				mytag.tagCount = 0;
				return;
			}
			mytag.tag[i].strTag = strTag;
			mytag.tag[i].times = 1;
			mytag.tag[i].antaner =szAntena;
			mytag.tagCount++;	

		}

	}
}

void CTestDll_VS2010Dlg::listData()
{
	CString str,szTemp;
	str = "";

	for ( int i = 0; i<mytag.tagCount; i++)
	{
		str +="标签：";
		str +=mytag.tag[i].strTag;
		str +="    天线号:";
		str +=mytag.tag[i].antaner;	
		str +="    次数：";
		szTemp.Format("%02d",mytag.tag[i].times);
		str +=szTemp;
		str +="\r\n";

	}
	GetDlgItem(IDC_LIST_CALLBACK_DATA)->SetWindowText(str);
}

int ipoweroff = 300;

void  onMessageNotify(IMessageNotification *msg,void *context)//context此时是CTestDll_VS2010Dlg对象指针
{	
	CString strTag;
	CString strTemp;
	CString strInsideMsg;
	CString strAnter;
	CTestDll_VS2010Dlg *pCTestDll_VS2010Dlg = (CTestDll_VS2010Dlg *)context;
	int len;
	len = 0;
	int len2 = msg->ReceivedDataLen;

	if (msg->msgName == "RXD_TagData")
	{

		ipoweroff = 800;
		unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
		strAnter.Format("  %d#",(int)m_antenna);
		unsigned char ucEpc[128];
		unsigned char ucEpcLen;
		ucEpcLen = 0;
		
		((RXD_TagData*)msg)->GetEPC(ucEpc,ucEpcLen);

		for (int kkk = 0; kkk < ucEpcLen; kkk++)
		{
			strTemp.Format("%02x",ucEpc[kkk]);
			strTag += strTemp;
		}
		AddTag(strTag,strAnter);

		TRACE(strTag);
		return ;



		//if (msg->ReceivedData[2] == 0x81)
		if (((RXD_TagData*)msg)->msgType == "RXD_EPC_6C")
		{
			/*unsigned char ch = ((RXD_EPC_6C*)msg)->tagAntenna;
			strAnter.Format("  %d#",(int)ch);
			len = ((RXD_EPC_6C*)msg)->tagEpcLen;
			ipoweroff = 800;*/
			
			ipoweroff = 800;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEpc[128];
			unsigned char ucEpcLen;
			ucEpcLen = 0;
			((RXD_TagData*)msg)->GetEPC(ucEpc,ucEpcLen);

			for (int kkk = 0; kkk < ucEpcLen; kkk++)
			{
				strTemp.Format("%02x",ucEpc[kkk]);
				strTag += strTemp;
			}
			//strTag +="\r\n";
			AddTag(strTag,strAnter);
			Debug_TRACE(strTag);
			//pCTestDll_VS2010Dlg->GetDlgItem(IDC_LIST_CALLBACK_DATA)->SetWindowText(strTag);
			return;
		}
		else if (((RXD_TagData*)msg)->msgType == "RXD_TID_6C")
		{
			ipoweroff = 800;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEpc[128];
			unsigned char ucEpcLen;
			ucEpcLen = 0;
			((RXD_TagData*)msg)->GetTID(ucEpc,ucEpcLen);

			for (int kkk = 0; kkk < ucEpcLen; kkk++)
			{
				strTemp.Format("%02x",ucEpc[kkk]);
				strTag += strTemp;
			}
			AddTag(strTag,strAnter);
			//strTag +="\r\n";
			Debug_TRACE(strTag);
			//pCTestDll_VS2010Dlg->GetDlgItem(IDC_LIST_CALLBACK_DATA)->SetWindowText(strTag);
			return;


		}
		else if (((RXD_TagData*)msg)->msgType == "RXD_ID_6B")
		{
			/*unsigned char ch = ((RXD_ID_6B*)msg)->tagAntenna;
			strAnter.Format("  %d#",(int)ch);
			len = ((RXD_ID_6B*)msg)->tagIDLen;
			ipoweroff = 500;*/
			ipoweroff = 500;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEpc[128];
			unsigned char ucEpcLen;
			ucEpcLen = 0;
			((RXD_TagData*)msg)->GetTID(ucEpc,ucEpcLen);

			for (int kkk = 0; kkk < ucEpcLen; kkk++)
			{
				strTemp.Format("%02x",ucEpc[kkk]);
				strTag += strTemp;
			}
			AddTag(strTag,strAnter);
			//strTag +="\r\n";
			Debug_TRACE(strTag);
			//pCTestDll_VS2010Dlg->GetDlgItem(IDC_LIST_CALLBACK_DATA)->SetWindowText(strTag);
			return;
		}
		else if (((RXD_TagData*)msg)->msgType == "RXD_EPC_TID_UserData_6C_2")
		{

			ipoweroff = 800;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEPC[128];
			unsigned char ucEPCLen;
			ucEPCLen = 0;
			strTag = "标签EPC:";
			((RXD_TagData*)msg)->GetEPC(ucEPC,ucEPCLen);

			for (int kkk = 0; kkk < ucEPCLen; kkk++)
			{
				strTemp.Format("%02x",ucEPC[kkk]);
				strTag += strTemp;
			}

			unsigned char ucTID[128];
			unsigned char ucTIDLen;
			ucTIDLen = 0;
			
			((RXD_TagData*)msg)->GetTID(ucTID,ucTIDLen);
			strTag += "标签TID:";
			for (int kkk = 0; kkk < ucTIDLen; kkk++)
			{
				strTemp.Format("%02x",ucTID[kkk]);
				strTag += strTemp;
			}

			unsigned char ucUserData[128];
			unsigned char ucUserDataLen;
			ucTIDLen = 0;

			((RXD_TagData*)msg)->GetUserdata(ucUserData,ucUserDataLen);
			strTag += "标签用户数据:";
			for (int kkk = 0; kkk < ucTIDLen; kkk++)
			{
				strTemp.Format("%02x",ucUserData[kkk]);
				strTag += strTemp;
			}
			
			AddTag(strTag,strAnter);
			return;
		}
		else if (((RXD_TagData*)msg)->msgType == "RXD_EPC_TID_UserData_6C")
		{
	

			ipoweroff = 800;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEPC[128];
			unsigned char ucEPCLen;
			ucEPCLen = 0;
			strTag = "标签EPC:";
			((RXD_TagData*)msg)->GetEPC(ucEPC,ucEPCLen);

			for (int kkk = 0; kkk < ucEPCLen; kkk++)
			{
				strTemp.Format("%02x",ucEPC[kkk]);
				strTag += strTemp;
			}

			unsigned char ucTID[128];
			unsigned char ucTIDLen;
			ucTIDLen = 0;

			((RXD_TagData*)msg)->GetTID(ucTID,ucTIDLen);
			strTag += "标签TID:";
			for (int kkk = 0; kkk < ucTIDLen; kkk++)
			{
				strTemp.Format("%02x",ucTID[kkk]);
				strTag += strTemp;
			}

			unsigned char ucUserData[128];
			unsigned char ucUserDataLen;
			ucTIDLen = 0;

			((RXD_TagData*)msg)->GetUserdata(ucUserData,ucUserDataLen);
			strTag += "标签用户数据:";
			for (int kkk = 0; kkk < ucTIDLen; kkk++)
			{
				strTemp.Format("%02x",ucUserData[kkk]);
				strTag += strTemp;
			}

			AddTag(strTag,strAnter);
			return;

		}else if (((RXD_TagData*)msg)->msgType == "RXD_EPC_PC_6C")
		{
			ipoweroff = 800;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEPC[128];
			unsigned char ucEPCLen;
			ucEPCLen = 0;
			strTag = "标签EPC:";
			((RXD_TagData*)msg)->GetEPC(ucEPC,ucEPCLen);

			for (int kkk = 0; kkk < ucEPCLen; kkk++)
			{
				strTemp.Format("%02x",ucEPC[kkk]);
				strTag += strTemp;
			}

			unsigned char ucPC[128];
			unsigned char ucPCLen;
			ucPCLen = 0;

			((RXD_TagData*)msg)->GetTID(ucPC,ucPCLen);
			strTag += "标签PC:";
			for (int kkk = 0; kkk < ucPCLen; kkk++)
			{
				strTemp.Format("%02x",ucPC[kkk]);
				strTag += strTemp;
			}

			unsigned char ucRSSI[128];
			unsigned char ucRSSILen;
			ucRSSILen = 0;

			((RXD_TagData*)msg)->GetUserdata(ucRSSI,ucRSSILen);
			strTag += "标签用户数据:";
			for (int kkk = 0; kkk < ucRSSILen; kkk++)
			{
				strTemp.Format("%02x",ucRSSI[kkk]);
				strTag += strTemp;
			}

			AddTag(strTag,strAnter);
			return;
		}
		else if (((RXD_TagData*)msg)->msgType == "RXD_ID_UserData_6B")
		{
			strTag = "标签id:";
			ipoweroff = 500;
			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucEpc[128];
			unsigned char ucEpcLen;
			ucEpcLen = 0;
			((RXD_TagData*)msg)->GetTID(ucEpc,ucEpcLen);
			for (int kkk = 0; kkk < ucEpcLen; kkk++)
			{
				strTemp.Format("%02x",ucEpc[kkk]);
				strTag += strTemp;
			}

			unsigned char ucUserData[128];
			unsigned char ucUserDataLen;
			ucUserDataLen = 0;
			strTag += "标签用户数据:";
			((RXD_TagData*)msg)->GetUserdata(ucUserData,ucUserDataLen);
			for (int kkk = 0; kkk < ucUserDataLen; kkk++)
			{
				strTemp.Format("%02x",ucUserData[kkk]);
				strTag += strTemp;
			}

			AddTag(strTag,strAnter);
			//strTag +="\r\n";
			Debug_TRACE(strTag);
			pCTestDll_VS2010Dlg->GetDlgItem(IDC_LIST_CALLBACK_DATA)->SetWindowText(strTag);
			return;
		}

	}
	else if (len2 > 0 && len2 != 0xcdcdcdcd) //output不回调出来的消息，即API内部消息
	{
		for (int i = 0; i < len2/*msg->ReceivedDataLen*/; i++)
		{
			strTemp.Format("%02x ",msg->ReceivedData[i]);
			strInsideMsg += strTemp;
		}

		strTemp.Empty();
		strTemp.Format("  API内部消息");

		strInsideMsg += strTemp;
		Debug_TRACE(strInsideMsg);
		pCTestDll_VS2010Dlg->GetDlgItem(IDC_EDIT4)->SetWindowText(strInsideMsg);	
		return;
	}

}


bool isPowerOff=false;
void  CTestDll_VS2010Dlg::setEpc()
{

	void **poiter1=NULL;
	BOOL b_close = FALSE;
	PowerOff * powerOff= CreatePowerOff();
	b_close = c_reader1->send(powerOff);
	if (b_close)
	{
		Debug_TRACE("关功放成功！");
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放成功！");
	}
	else
	{
		Debug_TRACE("关功放失败！");
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放失败！");
	}
	poiter1 = (void **)&powerOff;
	ReleaseMem(poiter1);
	poiter1 = NULL;

	Sleep(10);

	//写epc
	unsigned char TagData[12] = {0x80,0x03,0x16,0x80,0x01,0x23,0x45,0x67,0x00,0x02,0xa0,0xf0};
	//SelectTag_6C *msg1 = CreateSelectTag_6CEx(0x01,0x00,0x60,TagData,12);//0x01为EPC，0x00为标签匹配起始地址，0x60为匹配12字节，Tagdata为EPC，12为EPC长度
	memcpy(TagData,utagId,8);
	SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagData,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
	c_reader1->send(msg1);
	ReleaseMem((void**)&msg1);
	Sleep(500);

	unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
	unsigned char EpcData[12] = {0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c};
	WriteEpc *msg2 = CreateWriteEpc(0x01,Pwd,0x04,EpcData,12);//写12byte的EPC数据
	BOOL bflag = c_reader1->send(msg2);
	if (bflag)
	{
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写epc成功\r\n");
	}
	else
	{
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写epc失败\r\n");
	}
	poiter1 = (void **)&msg2;
	ReleaseMem(poiter1);
	poiter1 = NULL;

	//设置io
	if (bflag)
	{
		unsigned char buf1[6];
		unsigned char bufLen;
		memset(buf1,2,sizeof(buf1));
		bufLen = 6;
		Gpo_800 *msg = CreateGpo_800(0x01,0x01);//0x02配置天线端口数
		BOOL bflag = c_reader1->send(msg);

		if (bflag)
		{
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("IDC_Gpo_800配置成功");
		}
		poiter1 = (void **)&msg;
		ReleaseMem(poiter1);
		poiter1 = NULL;

		Gpo_800 *msg1 = CreateGpo_800(0x01,0x00);//0x02配置天线端口数
		bflag = c_reader1->send(msg1);

		if (bflag)
		{
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("IDC_Gpo_800配置成功");
		}
		poiter1 = (void **)&msg1;
		ReleaseMem(poiter1);
		poiter1 = NULL;
	}
	isPowerOff = false;
}

void  onMessageNotify_list(IMessageNotification *msg,void *context)//context此时是CTestDll_VS2010Dlg对象指针
{
	CString strTag;
	CString strTemp;
	CString strInsideMsg;
	CString strAnter;
	CTestDll_VS2010Dlg *pCTestDll_VS2010Dlg = (CTestDll_VS2010Dlg *)context;
	int len;
	len = 0;

	if (msg->msgName == "RXD_TagData")
	{
		/*unsigned char ch = ((RXD_TagData*)msg)->tagAntenna;
		strAnter.Format("  %d#",(int)ch);
		len = ((RXD_TID_6C*)msg)->tagTIDLen;

		if (len > 0 && len != 0xcd)
		{
			strTag.Empty();
			strTemp.Empty();
			
			for (int i= 0; i < ((RXD_TID_6C*)msg)->tagTIDLen;i++)
			{
				strTemp.Format("%02x",((RXD_TID_6C*)msg)->tagTID[i]);
				utagId[i] = ((RXD_TID_6C*)msg)->tagTID[i];
				strTag+=strTemp;
			}
			TRACE(strTag);
			TRACE("\r\n");
			AddTag(strTag,strAnter);*/



			unsigned char m_antenna = ((RXD_TagData*)msg)->GetAntenna();
			strAnter.Format("  %d#",(int)m_antenna);
			unsigned char ucTID[128];
			unsigned char ucTIDLen;
			ucTIDLen = 0;
			((RXD_TagData*)msg)->GetTID(ucTID,ucTIDLen);

			for (int kkk = 0; kkk < ucTIDLen; kkk++)
			{
				strTemp.Format("%02x",ucTID[kkk]);
				strTag += strTemp;
			}
			AddTag(strTag,strAnter);
			//strTag +="\r\n";
			Debug_TRACE(strTag);



			/*if (isPowerOff)
			{
				pCTestDll_VS2010Dlg->listData();
				setEpc((void*)pCTestDll_VS2010Dlg);
			}
			//pCTestDll_VS2010Dlg->listData();
			//setEpc((void*)pCTestDll_VS2010Dlg);
		}*/
	}
	else if (msg->msgName=="RXD_IOTriggerSignal_800")
	{
		bool isStart = ((RXD_IOTriggerSignal_800*)msg)->GetIsStart();
		if (isStart)
		{
			isPowerOff = false;
			pCTestDll_VS2010Dlg->GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("start！");
			TRACE("start！\r\n");
		}else
		{
			isPowerOff = TRUE;
			pCTestDll_VS2010Dlg->GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("stop！");
			TRACE("stop！\r\n");
			
		}

	}
	return ;
}
void CTestDll_VS2010Dlg::OnClickedBtnConnOrDisconn()//单读写器的连接或断开
{
	if (b_MultiReader)
	{
		MessageBox("多读写器正在测试中，需要先断开多读写器连接","提示",MB_OK);
		return;
	}
	bool bflg = false;
	CString str = "";
	GetDlgItemText(IDC_BTN_ConnOrDisconn,str);
	CString strConKind = "";
	CString strConValue ="";
	GetDlgItem(IDC_EDIT2)->GetWindowText(strConKind);
	GetDlgItem(IDC_EDIT3)->GetWindowText(strConValue);
	ipoweroff = 300;
	if ("建立连接" == str)
	{
		//c_reader1 = CreateReaderObjA3("Reader1", "IRP1", "TCPIP_Client", "192.168.7.246:7086");//tcp方式
		//c_reader1 = CreateReaderObjA3("Reader1", "IRP1", "RS232", "COM1,115200");//串口方式
		c_reader1 = CreateReaderObjA3("Reader1", strConKind, strConValue);
		//c_reader1 = CreateReaderObjA3("Reader1", "TCPIP_Server", "192.168.7.53:7087");//tcp方式
		c_reader1->setCallBack(onMessageNotify,this,c_reader1);           //设置回调函数
		bflg = c_reader1->connect();
		if (bflg)
		{
			Debug_TRACE("建立连接成功");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("建立连接成功");
			b_SingnalReader = true;
			SetDlgItemText(IDC_BTN_ConnOrDisconn,"断开连接");
		}
		else
		{
			Debug_TRACE("建立连接失败");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("建立连接失败");
			if (c_reader1)
			{
				ReleaseMem((void**)&c_reader1);
			}
		}
	}
	else if ("断开连接" == str)
	{
		BOOL b_disconnect = FALSE;
		Sleep(100);

		b_disconnect = c_reader1->disconnect();
		if (b_disconnect)
		{
			if (c_reader1)
			{
				if (ReleaseMem((void**)&c_reader1))
				{
					Debug_TRACE("断开连接成功");
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("断开连接成功");
				}
				else
				{
					Debug_TRACE("断开连接失败");
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("断开连接失败");
					return;
				}
			}
		}
		else
		{
			Debug_TRACE("断开连接失败");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("断开连接失败");
			return;
		}

		b_SingnalReader = false;
		SetDlgItemText(IDC_BTN_ConnOrDisconn,"建立连接");
	}
}

void CTestDll_VS2010Dlg::OnClickedBtnMutiReaderConnOrDisc()//多读写器连接or断开;
{
	if (b_SingnalReader)
	{
		MessageBox("单读写器正在测试中，需要先断开单读写器连接","提示",MB_OK);
		return;
	}
	bool bflg = false;
	CString str = "";
	GetDlgItemText(IDC_BtnMutiReaderConnOrDisc,str);
	if ("多读写器连接" == str)
	{
		c_reader1 = CreateReaderObjA2("Reader1");				//读写器1,测试多读写器;
		c_reader1->setCallBack(onMessageNotify,this,c_reader1);           //设置回调函数;
		bflg = c_reader1->connect();
		if (bflg)
		{
			Debug_TRACE("读写器1连接成功");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器1连接成功");
		}
		else
		{
			Debug_TRACE("读写器1连接失败");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器1连接失败");
			if (c_reader1)
			{
				ReleaseMem((void**)&c_reader1);
				return;
			}
		}

		c_reader2 = CreateReaderObjA2("192.168.1.120");				//读写器2,测试多读写器;
		c_reader2->setCallBack(onMessageNotify,this,c_reader2);
		bflg = c_reader2->connect();
		if (bflg)
		{
			Debug_TRACE("读写器2连接成功");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器2连接成功");
		}
		else
		{
			Debug_TRACE("读写器2连接失败");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器2连接失败");
			if (c_reader1)//如果读写器1对象已存在
			{
				c_reader1->disconnect();//先断开
				ReleaseMem((void**)&c_reader1);//再释放;
			}

			if (c_reader2)
			{
				ReleaseMem((void**)&c_reader2);
				return;
			}
		}

		b_MultiReader = true;
		SetDlgItemText(IDC_BtnMutiReaderConnOrDisc,"多读写器断开");
	}
	else if ("多读写器断开" == str)
	{
		BOOL b_disconnect = FALSE;
		Sleep(100);
		if (c_reader1)
		{
			b_disconnect = c_reader1->disconnect();
			if (b_disconnect)
			{
				if (c_reader1)
				{
					if (ReleaseMem((void**)&c_reader1))
					{
						Debug_TRACE("读写器1断开成功");
						GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器1断开成功");
					}
					else
					{
						Debug_TRACE("读写器1断开失败");
						GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器1断开失败");
						return;
					}
				}
			}
			else
			{
				Debug_TRACE("读写器1断开失败");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器1断开失败");
				return;
			}
		}

		if (c_reader2)
		{
			b_disconnect = c_reader2->disconnect();
			if (b_disconnect)
			{
				if (c_reader2)
				{
					if (ReleaseMem((void**)&c_reader2))
					{
						Debug_TRACE("读写器2断开成功");
						GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器2断开成功");
					}
					else
					{
						Debug_TRACE("读写器2断开失败");
						GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器2断开失败");
						return;
					}
				}
			}
			else
			{
				Debug_TRACE("读写器2断开失败");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("读写器2断开失败");
				return;
			}
		}
		

		b_MultiReader = false;
		SetDlgItemText(IDC_BtnMutiReaderConnOrDisc,"多读写器连接");
	}
}

void CTestDll_VS2010Dlg::OnClose()
{
	CString str = "";
	if (b_SingnalReader)
	{
		GetDlgItemText(IDC_BTN_ConnOrDisconn,str);
		if ("断开连接" == str)
		{
			MessageBox("请先断开连接再关闭","提醒",MB_OK);
			return;
		}
	}
	
	if (b_MultiReader)
	{
		GetDlgItemText(IDC_BtnMutiReaderConnOrDisc,str);
		if ("多读写器断开" == str)
		{
			MessageBox("请先断开多读写器连接再关闭","提醒",MB_OK);
			return;
		}
	}
	

	CDialogEx::OnClose();
}

void CTestDll_VS2010Dlg::OnClickedBtnSeltestitem()
{
	ParameterDlg.DoModal();
}

void CTestDll_VS2010Dlg::OnClickedBtnStartTest()
{
	GetDlgItem(IDC_Btn_StartTest)->EnableWindow(FALSE);
	if (1 == ParameterDlg.CheckedNum)
	{
		//if (ParameterDlg.CheckBoxID >= IDC_PowerOn && ParameterDlg.CheckBoxID <= IDC_TestModeConfig_800) //mark by shuang
		if (ParameterDlg.CheckBoxID >= IDC_PowerOn && ParameterDlg.CheckBoxID <= IDC_READ_6B_TID)
		{
			TestItem(ParameterDlg.CheckBoxID);
		} 
		else
		{
			GetDlgItem(IDC_Btn_StartTest)->EnableWindow(TRUE);
			return;
		}
	}
	else if (0 == ParameterDlg.CheckedNum)
	{
		MessageBox("未选择测试项，请先选择测试项目","提醒",MB_OK);
	}
	else if (ParameterDlg.CheckedNum > 1)
	{
		MessageBox("测试项目太多，只能选择一个测试项","提醒",MB_OK);
	}
	else
	{
		GetDlgItem(IDC_Btn_StartTest)->EnableWindow(TRUE);
		return;
	}
	GetDlgItem(IDC_Btn_StartTest)->EnableWindow(TRUE);
}


void CTestDll_VS2010Dlg::TestItem(int ItemID)
{
	
	if (!c_reader1)
	{
		MessageBox("请先建立连接","提醒",MB_OK);
		return;
	}
	GetDlgItem(IDC_EDIT4)->SetWindowText("");	
	void **poiter1 = NULL;

	if (ipoweroff == 800)
	{
		/*KillTimer(1);
		BOOL b_close = FALSE;
		PowerOff_800 * powerOff_800= CreatePowerOff_800();
		b_close = c_reader1->send(powerOff_800);
		if (b_close)
		{
			Debug_TRACE("关功放成功！");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放成功！");
		}
		else
		{
			Debug_TRACE("关功放失败！");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放失败！");
		}
		poiter1 = (void **)&powerOff_800;


		if (poiter1 && *poiter1)
		{
			Sleep(100);
			CString str;
			if (ReleaseMem(poiter1))
			{
				str.Format("成功释放\"%s\"",ParameterDlg.strTestItemName);
				str += "对象内存";
				Debug_TRACE(str);
			} 
			else
			{
				str.Format("释放\"%s\"",ParameterDlg.strTestItemName);
				str += "对象内存失败";
				//MessageBox(str.GetBuffer(str.GetLength()),"提示",MB_OK);
			}
			poiter1 = NULL;
		}

		Sleep(500);
		ipoweroff = 300;*/
		if (ItemID == IDC_PowerOff || ItemID ==IDC_POWEROFF_500)
		{
			ipoweroff = 300;
		}else 
		{

			AfxMessageBox("请先关功放");
			return;

		}
			 
	
	}else if (ipoweroff == 500)
	{
		/*KillTimer(1);
		BOOL b_close = FALSE;
		PowerOff_500 * powerOff_500= CreatePowerOff_500();
		b_close = c_reader1->send(powerOff_500);
		if (b_close)
		{
			Debug_TRACE("关功放成功！");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放成功！");
		}
		else
		{
			Debug_TRACE("关功放失败！");
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放失败！");
		}
		poiter1 = (void **)&powerOff_500;
		

		if (poiter1 && *poiter1)
		{
			Sleep(100);
			CString str;
			if (ReleaseMem(poiter1))
			{
				str.Format("成功释放\"%s\"",ParameterDlg.strTestItemName);
				str += "对象内存";
				Debug_TRACE(str);
			} 
			else
			{
				str.Format("释放\"%s\"",ParameterDlg.strTestItemName);
				str += "对象内存失败";
				//MessageBox(str.GetBuffer(str.GetLength()),"提示",MB_OK);
			}
			poiter1 = NULL;
		}

		Sleep(500);
		ipoweroff = 300;*/

		if (ItemID == IDC_PowerOff || ItemID ==IDC_POWEROFF_500)
		{
			ipoweroff = 300;
		}else 
		{

			AfxMessageBox("请先关功放");
			return;

		}
	}

	switch(ItemID)
	{
	case IDC_PowerOn:
		{
			BOOL b_open = FALSE;
			PowerOn_800 * powerOn_800= CreatePowerOn_800(0x01); //不用先开功放一样读标签PowerOff_800* CreatePowerOff_800()
			b_open = c_reader1->send(powerOn_800);
			if (b_open)
			{
				Debug_TRACE("开功放成功！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("开功放成功！");
			} 
			else
			{
				Debug_TRACE("开功放失败！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("开功放失败！");
			}
			poiter1 = (void **)&powerOn_800;
		}
		break;
	case  IDC_POWEROFF_500:
		{
			KillTimer(1);
			BOOL b_close = FALSE;
			PowerOff_500 * powerOff_500= CreatePowerOff_500();
			b_close = c_reader1->send(powerOff_500);
			if (b_close)
			{
				Debug_TRACE("关功放成功！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放成功！");
			}
			else
			{
				Debug_TRACE("关功放失败！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放失败！");
			}
			poiter1 = (void **)&powerOff_500;
		}
		Sleep(500);
		ipoweroff = 300;
		break;
	case IDC_PowerOff:
		{
			KillTimer(1);
			BOOL b_close = FALSE;
			PowerOff_800 * powerOff_800= CreatePowerOff_800();
			b_close = c_reader1->send(powerOff_800);
			if (b_close)
			{
				Debug_TRACE("关功放成功！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放成功！");
			}
			else
			{
				Debug_TRACE("关功放失败！");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("关功放失败！");
			}
			poiter1 = (void **)&powerOff_800;
		}
		Sleep(500);
		ipoweroff = 300;
		break;
	case IDC_ReadEPC:
		{
			ReadTag* read_tag_6c = CreateReadTag6cBank(EPC_6C);
			read_tag_6c->SetIsLoop(0x01);
			mytag.tagCount = 0;
			c_reader1->send(read_tag_6c);
			
			poiter1 = (void **)&read_tag_6c;
			SetTimer(1,900,NULL);
		}
		break;
	case IDC_ReadEPC2:
		{
			ReadTag* msg = CreateReadTag6cBankEx(EPC_6C,3000,1000,"800");
			
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
			SetTimer(1,900,NULL);
		}
		break;
	case IDC_ReadTID:
		{
			ReadTag* read_tag_6c_tid = CreateReadTag6cBank(TID_6C);
			read_tag_6c_tid->SetIsLoop(0x01);
			mytag.tagCount = 0;
			c_reader1->send(read_tag_6c_tid);
			poiter1 = (void **)&read_tag_6c_tid;
			SetTimer(1,900,NULL);

			//CreateReadTag6cBankEx_1(ReadMemoryBank rmb,CString stopType,BOOL isGetOneTag)
			/*ReadTag* read_tag_6c_tid = CreateReadTag6cBankEx_1(TID_6C,"800",FALSE);
			c_reader1->send(read_tag_6c_tid);
			poiter1 = (void **)&read_tag_6c_tid;
			unsigned int len;
			len = 0;
			RXD_TID_6C *rxd_tid_6c;
			rxd_tid_6c = NULL;
			unsigned char tid[128];
			unsigned char tidlen;

			do 
			{
				memset(tid,'\0',sizeof(tid));
				tidlen = 0;
				rxd_tid_6c = read_tag_6c_tid->List_RXD_TID_6C(len);
				
				if (rxd_tid_6c != NULL)
				{
					rxd_tid_6c->GetTID(tid,tidlen);
					CString str,strTid;
					strTid="tid为：";
					for (int i=0; i<tidlen; i++)
					{
						str.Format("%02x",tid[i]);
						strTid+=str;
					}
					strTid+="\r\n";
					TRACE(strTid);
				}
			} while (rxd_tid_6c !=NULL);
			//
			read_tag_6c_tid->releaseMen();
			poiter1 = (void **)&read_tag_6c_tid;*/
		}
		break;
	case IDC_SysQuery_800://Ex，连非Ex一起测试下吧;
		{
			//SysQuery_800(unsigned char parameter, unsigned char data)
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x05,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写mac地址
			SysQuery_800 *msg = CreateSysQuery_800Ex(0x06,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写IP地址
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x04,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写跳频表
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x02,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写天线端口数
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x03,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写天线端口功率
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x11,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写读写器产品型号
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x13,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写读写器处理器软件版本号
			//SysQuery_800 *msg = CreateSysQuery_800Ex(0x14,0x00);//最后一个参数默认为0x00 保留域   0x06为查询读写读写器FPGA软件版本号
			c_reader1->send(msg);
			unsigned char buf[128];
			unsigned char len;
			len = 0;
			msg->GetQueryData(buf,len);
			CString str;
			CString strTemp;
		
			if (len > 0)
			{
				strTemp = "";
				for (int i= 0; i< len; i++)
				{
					str.Format("%02d ",(unsigned char )buf[i]);
					strTemp += str;
				}
				TRACE(strTemp);
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(strTemp);
			}else
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("查询读写器参数失败");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_SysConfig_800:
		{
			unsigned char buf[12] = {0xC0,0xA8,0x01,0x79,0xFF,0xFF,0xFF,0x00,0xC0,0xA8,0x01,0x01};
			SysConfig_800* msg = CreateSysConfig_800(0X06,buf,12);//0x06为设置IP代号，buf为IP、掩码、路由三者一起转换成char数组了。;
			bool bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置成功");
			}
			else
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置失败");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_ReadUserData6C://回调显示数据
		{
			ReadTag *msg = CreateEPC_TID_UserData_6C(8,0,4);
			bool bflag = c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_ReadUserData6CEx://回调显示数据
		{
			ReadTag *msg = CreateEPC_TID_UserData_6CEx(8,0,4,3000,1000);
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_TagOperationQuery_6C:
		{
			TagOperationQuery_6C *msg = CreateTagOperationQuery_6C(0x10);//查询时隙Q值
			BOOL bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				char buf[128];
				unsigned char len;
				bflag = msg->GetQueryData(buf,len);
				CString str,strTemp;
				
				if (bflag)
				{
					for (int i =0; i< len; i++)
					{
						strTemp.Format("%02x",(unsigned char)buf[i]);
						str += strTemp;
					}
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_TagOperationConfig_6C:
		{
			unsigned char data[1] = {0x04};
			TagOperationConfig_6C *msg = CreateTagOperationConfig_6C(0x10,data,1);//把读写器时隙Q值改为0x03
			bool bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置成功");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_QT_6C:
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char PayLoad[2];
			char ch = 0x01 << 6; //PayLoad第一字节的第7、6位分别置0、1
			PayLoad[0] = ch;
			PayLoad[1] = 0x00;
			QT_6C *msg = CreateQT_6C(0x01,Pwd,0x04,0x00,0x01,PayLoad,2);//这样传参不知对不对;
			bool bflag = c_reader1->send(msg);
			CString str,strTemp;
			if (bflag )
			{
				unsigned char cAntenna = msg->GetAntenna();
				str.Format("天线==%02d",cAntenna);
				unsigned char buf[128];
				unsigned char len;
				bflag = msg->GetPayload(buf,len);
				str+="\r\nPayload为：";
				if (bflag)
				{
					for (int i=0; i<len;i++)
					{
						strTemp.Format("%02x",buf[i]);
						str+=strTemp;
					}
				}
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_EasConfig_6C:
		{
			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			EasConfig_6C *msg = CreateIEasConfig_6C(0x01,AccessPwd,4,0x01);//后面的0X01表示设置EPA
			BOOL bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置成功");
			}else
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置失败");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_EasAlarm_6C:
		{
			EasAlarm_6C *msg = CreateEasAlarm_6C(0x01,0x01);//后面0x01表示开启
			
			BOOL bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				unsigned char cAntenna = msg->GetAntenna();
				unsigned char cAnswerType= msg->GetAnswerType();
				CString str;
				
				str.Format("设置成功\r\n天线号:%02d,AnswerType:%02d",cAntenna,cAnswerType);
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);

			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_SelectTag_6C://标签选择指令，可以不测
		{
			unsigned char TagData[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			SelectTag_6C *msg = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagData,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，Tagdata为TID，8为TID长度
			c_reader1->send(msg);
			Sleep(500);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_WriteUserData_6C:
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			//unsigned char UserData[10] = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09};
			unsigned char UserData[10] = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			WriteUserData_6C *msg = CreateWriteUserData_6C(0x01,Pwd,4,0x00,UserData,10);//0x01为天线，Pwd为访问密码，0x00为写用户数据的起始地址，UserData为写入的用户数据，10为写UserData的字节数，API里面会转化为word数量，10byte就是5个word
			bool bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写标签数据0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_WriteUserData_6CEx:
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char UserData[10] = {0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01};
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			WriteUserData_6C *msg = CreateWriteUserData_6CEx(0x01,Pwd,4,0x00,UserData,10,TagID,8,TIDMemory);
			//Pwd为访问密码，0x00为写用户数据的起始地址，UserData为写入的用户数据，10个byte，TagID为标签ID，8为标签长度，0x02TID代号
			BOOL bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写用户数据成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_ReadUserData_6C:
		{
			ReadUserData_6C *msg = CreateReadUserData_6C(0x01,0x00,14);//0x00为读用户数据起始地址，14为读取的字节数
			c_reader1->send(msg);
			unsigned char Userdata[512] = {0};
			unsigned char len = 0;	
			Sleep(200);
			bool bflag = msg->GetUserData(Userdata,len);
			CString str,strTemp;
			if(bflag)
			{
				for (int i = 0; i<len; i++)
				{
					strTemp.Format("%02x",Userdata[i]);
					str+=strTemp;
				}
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_ReadUserData_6CEx://读用户数据Ex，指令类型84;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			ReadUserData_6C *msg = CreateReadUserData_6CEx(0x01,0x00,14,TagID,8,0x02);//01天线，00起始地址，读10个字节，TagID所选择的标签，8标签长度，02TID标签类型代号
			c_reader1->send(msg);
			unsigned char Userdata[512] = {0};
			unsigned char len = 0;		
			Sleep(200);
			BOOL bflag = msg->GetUserData(Userdata,len);
			if (bflag)
			{
				CString str,strTemp;
				str="用户数据：";
				for (int i =0; i<len; i++)
				{
					strTemp.Format("%02x",Userdata[i]);
					str +=strTemp;
				}
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
			}


			poiter1 = (void **)&msg;
		}
		break;
	case IDC_WriteTag_6C://通用写6c指令，指令类型99;
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char UserData[10] = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			WriteTag_6C *msg = CreateWriteTag_6C(0x01,Pwd,4,UserMemory,0x00,10,UserData,10);//01天线，00起始地址，读10个字节，TagID所选择的标签，8标签长度，02TID标签类型代号
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_WriteEpc://普通写EPC，指令类型83;
		{
			unsigned char TagData[12] = {0x80,0x03,0x16,0x80,0x01,0x23,0x45,0x67,0x00,0x02,0xa0,0xf0};
			//SelectTag_6C *msg1 = CreateSelectTag_6CEx(0x01,0x00,0x60,TagData,12);//0x01为EPC，0x00为标签匹配起始地址，0x60为匹配12字节，Tagdata为EPC，12为EPC长度
			memcpy(TagData,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagData,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char EpcData[12] = {0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c};
			WriteEpc *msg2 = CreateWriteEpc(0x01,Pwd,0x04,EpcData,12);//写12byte的EPC数据
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写epc成功");
			}
			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_WriteEpcEx://普通写EPC_Ex，指令类型83;
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char EpcData[12] = {0x00,0x11,0x22,0x33,0x44,0x44,0x66,0x77,0x88,0x99,0x11,0x22};
			unsigned char TagID[12] = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			//WriteEpc *msg = CreateWriteEpcEx(0x01,Pwd,0x04,EpcData,12,TagID,12,0x01);//可以通过制定TID改EPC，也可以通过指定EPC改EPC
			//TagID所选定的标签的数据，如果是TID,则8,0x02，现在是指定EPC，所以是12,0x01。EpcData则为想把EPC修改为EpcData，12字节
			memcpy(TagID,utagId,8);
			WriteEpc *msg = CreateWriteEpcEx(0x01,Pwd,0x04,EpcData,12,TagID,8,0x02);
			BOOL bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("写epc成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_BlockWrite_6C://块写用户数据指令，指令类型86;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char TagData[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			unsigned char Data[12] = {0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c};
			BlockWrite_6C *msg2 = CreateBlockWrite_6C(0x01,Pwd,4,0x03,0x00,Data,12);//第三参数0x03为用户数据(表示要操作的memblock为用户数据区)，0x00写用户数据起始地址，data写的数据，后面是长度;
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("块写用户数据成功");
			}
			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_BlockWrite_6CEx://块写用户数据指令Ex，指令类型86;
		{
			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			unsigned char Data[12] = {0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1a,0x1b,0x1c};
			BlockWrite_6C *msg = CreateBlockWrite_6CEx(0x01,Pwd,4,0x03,0x00,Data,12,TagID,8,TIDMemory);
			BOOL bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("块写用户数据成功");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_BlockErase://块擦用户数据指令，指令类型87;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度;
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char Pwd[4] = {0x00,0x00,0x00,0x00};
			BlockErase_6C *msg2 = CreateBlockErase_6C(0x01,Pwd,4,0x03,0x00,8);//0x02为TID，密码，用户数据块代号，起始地址，擦除长度;
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("块块擦用户数据成功");
			}
			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_AccessPwdConfig_6C://配置访问密码指令指令88;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char OldPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char NewPwd[4] = {0x11,0x11,0x11,0x11};
			AccessPwdConfig_6C *msg2 = CreateAccessPwdConfig_6C(0x01,OldPwd,4,NewPwd,4);//
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置标签访问密码成功");
			}
			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_AccessPwdConfig_6CEx://配置访问密码指令指令Ex 88
		{
			unsigned char OldPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char NewPwd[4] = {0x11,0x11,0x11,0x11};
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			AccessPwdConfig_6C *msg = CreateAccessPwdConfig_6CEx(0x01,OldPwd,4,NewPwd,4,TagID,8,TIDMemory);//
			BOOL bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置标签访问密码成功");
			}

			poiter1 = (void **)&msg;
		}
		break;
	case IDC_KillPwdConfig_6C://配置销毁密码指令指令89;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char KillPwd[4] = {0x11,0x11,0x11,0x11};
			KillPwdConfig_6C *msg2 = CreateKillPwdConfig_6C(0x01,AccessPwd,4,KillPwd,4);//
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置标签销毁密码成功");
			}
			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_KillPwdConfig_6CEx://配置销毁密码指令指令Ex 89;
		{
			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char KillPwd[4] = {0x11,0x11,0x11,0x11};
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			KillPwdConfig_6C *msg = CreateKillPwdConfig_6CEx(0x01,AccessPwd,4,KillPwd,4,TagID,8,TIDMemory);//
			BOOL bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置标签销毁密码成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_LockMemoryBank_6C://标签锁状态配置指令 8A;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);

			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			LockMemoryBank_6C *msg2 = CreateLockMemoryBank_6C(0x01,AccessPwd,4,0x00,0x03);//
			
			BOOL bflag = c_reader1->send(msg2);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("标签锁状态配置成功");
			}

			poiter1 = (void **)&msg2;
		}
		break;
	case IDC_LockMemoryBank_6CEx://标签锁状态配置指令Ex 8A;
		{
			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x01,0xc5,0x93,0x69};
			memcpy(TagID,utagId,8);
			LockMemoryBank_6C *msg = CreateLockMemoryBank_6CEx(0x01,AccessPwd,4,0x00,0x03,TagID,8,TIDMemory);//第三个从参数0x00是锁定，第四0x03是锁定用户数据区
			BOOL bflag = c_reader1->send(msg);
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("标签锁状态配置成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_KillTag_6C://灭活标签指令 8B------用虚拟器的标签测试的;
		{
			unsigned char TagID[8] = {0xe2,0x00,0x60,0x01,0x51,0x82,0x18,0x85};
			memcpy(TagID,utagId,8);
			SelectTag_6C *msg1 = CreateSelectTag_6CEx(TIDMemory,0x00,0x40,TagID,8);//0x02为TID，0x00为标签匹配起始地址，0x40为匹配8字节，TagID为TID，8为TID长度;
			c_reader1->send(msg1);
			ReleaseMem((void**)&msg1);
			Sleep(500);//第一步、先选择标签;

			unsigned char AccessPwd[4] = {0x00,0x00,0x00,0x00};
			unsigned char KillPwd1[4] = {0x11,0x11,0x11,0x11};
			KillPwdConfig_6C *msg2 = CreateKillPwdConfig_6C(0x01,AccessPwd,4,KillPwd1,4);//
			c_reader1->send(msg2);//第二步、然后设置标签的毁灭密码;
			ReleaseMem((void**)&msg2);

			unsigned char EpcData[30] = {0x46,0xe6,0xdd,0x34,0x5d,0x69,0x33,0xc9,0x0c,0x9d,0x5b,0xc8,0xd3,0x5e,0xdc,0xe4,0xda,0x6c,0x73,0x4c,0x39,0x1b,0x2a,0x48,0x91,0xd5,0xb1,0x33,0x7e,0x3a};
			unsigned char KillPwd2[4] = {0x11,0x11,0x11,0x11};
			KillTag_6C* msg3 = CreateKillTag_6C(0x01,KillPwd2,4,EpcData,30);//指令文档上面是有访问密码，这里传参为什么没有体现？;
			BOOL bflag = c_reader1->send(msg3);//第三步、用第二步的毁灭密码，再结合标签的EPC来灭活标签，也就是销毁标签;
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("灭活标签指令成功");
			}
			poiter1 = (void **)&msg3;
		}
		break;
	case IDC_EpcFilter_6C:
		{
			unsigned char Epcdata[30] = {0x09,0x66,0x36,0xe8,0xe0,0x38,0xfa,0xd1,0xa0,0xef,0x9c,0x51,0xe2,0xb0,0x4e,0xb5,0x47,0x84,0x5f,0x03,0x8e,0xda,0x1b,0x85,0xaf,0x1d,0x10,0x91,0xae,0xa5};
			unsigned char EpcMask[12] = {0xff,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};//ff代表匹配，如果前面1字节匹配0x09，就读取，其他标签不理会;
			EpcFilter_6C *msg = CreateEpcFilter_6C(0x00,Epcdata,(unsigned char)12,EpcMask,(unsigned char)12);//0x00代表设置,取消是多少还得从以前的demo去找。虽然EPC有30字节，但也可以只传12字节，再说，现在还只是匹配第1字节而已;
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_Gpo_800:
		{
			//Gpo_800(unsigned char ioPort, unsigned char level) //ioPort 输出端口, level 输出电平;
			unsigned char buf1[6];
			unsigned char bufLen;
			memset(buf1,2,sizeof(buf1));
			bufLen = 6;
			Gpo_800 *msg = CreateGpo_800(0x01,0x01);//0x02配置天线端口数
			BOOL bflag = c_reader1->send(msg);
			
			if (bflag)
			{
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("IDC_Gpo_800配置成功");
			}
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_Gpi_800://没写非Ex，连非Ex也测一下;
		{
			//Gpi_800(unsigned char  checkMode) checkMode查询方式 0x00立即返回当前io输入状态;
			unsigned char buf[128];
			unsigned char bufLen;
			memset(buf,'\0',sizeof(buf));
			bufLen = 0;
			Gpi_800 *msg = CreateGpi_800Ex(0x00);//0x02配置天线端口数;
			c_reader1->send(msg);
			poiter1 = (void **)&msg;

			msg->GetQueryData(buf,bufLen);
			if (bufLen > 0)
			{
				TRACE("it is ok");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("IDC_Gpi_800配置成功");
			}
		}
		break;
	case IDC_ResetReader_800:
		{
			unsigned char buf[128];
			unsigned char bufLen;

			memset(buf,'\0',sizeof(buf));
			bufLen = 0;
			ResetReader_800 *msg = CreateResetReader_800();//0x02配置天线端口数

			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_FirmwareUpgrade_800:
		{
			unsigned char buf[128];
			unsigned char bufLen;

			memset(buf,'\0',sizeof(buf));
			bufLen = 0;
			FirmwareUpgrade_800 *msg = CreateFirmwareUpgrade_800();//0x02配置天线端口数

			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_SysCheck_800:
		{
			unsigned char buf[128];
			unsigned char bufLen;

			memset(buf,'2',sizeof(buf));
			bufLen = 1;
			SysCheck_800 *msg = CreateSysCheck_800(0x00,buf,bufLen);//需要对协义重新写参数;
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;
	case IDC_TestModeConfig_800:
		{
			unsigned char buf[128];
			unsigned char bufLen;
			memset(buf,0,sizeof(buf));
			bufLen = 1;
			TestModeConfig_800 *msg = CreateTestModeConfig_800(0x00,buf,bufLen);//需要对协义重新写参数;

			poiter1 = (void **)&msg;
			c_reader1->send(msg);
			poiter1 = (void **)&msg;
		}
		break;

	//add by shuang
	case IDC_SysQuery_500:
		{
			CString strRet;
			strRet = "";
			CString strTemp;
			strTemp = "ip地址: ";
			//ip地址 
			strRet = StrsysQuery_500(0x00);
			strRet +="\r\n";
			Debug_TRACE(strRet);
			strTemp += strRet;
			Sleep(100);
			strRet = "";
			
			//天线数
			strTemp +="天线数:";
			strRet = StrsysQuery_500(0x02);
			strRet +="\r\n";
			Debug_TRACE(strRet);
			strTemp += strRet;
			Sleep(100);
			strRet = "";

			//MAC地址
			strTemp +="MAC地址:";
			strRet = StrsysQuery_500(0x04);
			strRet +="\r\n";
			Debug_TRACE(strRet);
			strTemp +=strRet;
			Sleep(100);
			strRet = "";

			//设备地址
			strTemp += "设备地址:";
			strRet = StrsysQuery_500(0x05);
			strRet +="\r\n";
			Debug_TRACE(strRet);
			strTemp += strRet;
			Sleep(100);
			strRet = "";

			//调频
			strTemp +="调频";
			strRet = StrsysQuery_500(0x01);
			strRet +="\r\n";
			strTemp +=strRet;
			Debug_TRACE(strRet);
			GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(strTemp);
			
		}
		break;
	case IDC_SysConfig_500:
		{
			/*typedef SysConfig_500*(*PFNCCreateSysConfig_500)(unsigned char infoType, unsigned char infoLength, 
				unsigned char *pData,unsigned char DataLen); //配置500读写器参数
			PFNCCreateSysConfig_500 pFNCCreateSysConfig_500= (PFNCCreateSysConfig_500)GetProcAddress(g_hMod,"CreateSysConfig_500");
			*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,2,sizeof(buf));
			len = 6;
			//SysConfig_500 * sysConfig_500= (pFNCCreateSysConfig_500)(0x04,0x06,buf,len); //设置MAC地址
			SysConfig_500 * sysConfig_500= CreateSysConfig_500(0x04,0x06,buf,len); //设置MAC地址
			BOOL b_open = c_reader1->send(sysConfig_500);	

			if (b_open)
			{
				TRACE("配置成功\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置成功\r\n");
			} 
			else
			{	
				TRACE("配置失败\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("配置失败\r\n");
			}
			poiter1 = (void **)&sysConfig_500;
			
		}
		break;
	case IDC_FreqConfig_500:
		{
			//修改了API，，是双命令字
			/*typedef FreqConfig_500*(*PFNCCreateFreqConfig_500)(unsigned char infoType, unsigned char *infoParam,unsigned char infoParamLen); //配置500读写器频段
			PFNCCreateFreqConfig_500 pFNCCreateFreqConfig_500= (PFNCCreateFreqConfig_500)GetProcAddress(g_hMod,"CreateFreqConfig_500");
			*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0] = 0x00;
			buf[1] = 0x00;
			len = 1;
			//FreqConfig_500 * freqConfig_500= (pFNCCreateFreqConfig_500)(0x11,buf,len); //设置MAC地址
			FreqConfig_500 * freqConfig_500= CreateFreqConfig_500(0x11,buf,len); //设置MAC地址
			BOOL b_open = c_reader1->send(freqConfig_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				bool bflag = freqConfig_500->GetQueryData(buf,len);
				if (bflag)
				{
					CString str;
					CString strtemp;
					for (int i=0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&freqConfig_500;
		}
		break;
	case IDC_BaudRateMode_500:
		{
			/*typedef BaudRateMode_500*(*PFNCCreateBaudRateMode_500)(unsigned char infoType); //模式设置
			PFNCCreateBaudRateMode_500 pFNCCreateBaudRateMode_500= (PFNCCreateBaudRateMode_500)GetProcAddress(g_hMod,"CreateBaudRateMode_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0] = 0x00;
			buf[1] = 0x00;
			len = 1;
			//BaudRateMode_500 * baudRateMode_500= (pFNCCreateBaudRateMode_500)(0x01); 
			BaudRateMode_500 * baudRateMode_500= CreateBaudRateMode_500(0x01); 
			BOOL b_open = c_reader1->send(baudRateMode_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&baudRateMode_500;
		}
		break;

	case  IDC_WorkModeSet_500:
		{
			/*typedef WorkModeSet_500*(*PFNCCreateWorkModeSet_500)(unsigned char infoType, unsigned char infoParameter); //读写器工作模式
			PFNCCreateWorkModeSet_500 pFNCCreateWorkModeSet_500= (PFNCCreateWorkModeSet_500)GetProcAddress(g_hMod,"CreateWorkModeSet_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			len = 0;
			//WorkModeSet_500 * workModeSet_500= (pFNCCreateWorkModeSet_500)(0x01,0x00); //查询
			//WorkModeSet_500 * workModeSet_500= (pFNCCreateWorkModeSet_500)(0x00,0x02); //查询
			WorkModeSet_500 * workModeSet_500= CreateWorkModeSet_500(0x00,0x02); //查询
			BOOL b_open = c_reader1->send(workModeSet_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				bool bflag = workModeSet_500->GetQueryData(buf,len);

				if (bflag)
				{
					CString str;
					CString strtemp;
					for (int i=0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}


			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&workModeSet_500;
		}
		break;

	case IDC_RssiLimitConfig_500:
		{
			/*typedef RssiLimitConfig_500*(*PFNCCreateRssiLimitConfig_500)(unsigned char infoType, unsigned char *infoParameter,unsigned char infoParameterLen); //读写器工作模式
			PFNCCreateRssiLimitConfig_500 pFNCCreateRssiLimitConfig_500= (PFNCCreateRssiLimitConfig_500)GetProcAddress(g_hMod,"CreateRssiLimitConfig_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//RssiLimitConfig_500 * rssiLimitConfig_500= (pFNCCreateRssiLimitConfig_500)(0x00,buf,2); //设置
			RssiLimitConfig_500 * rssiLimitConfig_500= CreateRssiLimitConfig_500(0x00,buf,2); //设置
			BOOL b_open = c_reader1->send(rssiLimitConfig_500);	

			if (b_open)
			{
				TRACE("it is send RssiLimitConfig_500 ok\r\n");
				b_open = rssiLimitConfig_500->GetQueryData(buf,len);
				if (b_open)
				{
					Debug_TRACE("设置成功\r\n");
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置成功\r\n");
				}

			} 
			else
			{
				TRACE("it is send fail RssiLimitConfig_500 \r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail RssiLimitConfig_500 \r\n");
			}
			Sleep(200);

			//RssiLimitConfig_500 * rssiLimitConfig_500_Query = (pFNCCreateRssiLimitConfig_500)(0x01,buf,2); //查询
			RssiLimitConfig_500 * rssiLimitConfig_500_Query = CreateRssiLimitConfig_500(0x01,buf,2); //查询
			b_open = c_reader1->send(rssiLimitConfig_500_Query);

			if (b_open)
			{
				b_open = rssiLimitConfig_500_Query->GetQueryData(buf,len);
				if (b_open)
				{
					Debug_TRACE("查询成功\r\n");
					CString str;
					CString strtemp;
					str += "查询成功\r\n";
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str +="\r\n";
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}
			}
			poiter1 = (void **)&rssiLimitConfig_500_Query;
		}
		break;

	case  IDC_Buzzer_500:
		{
			/*typedef Buzzer_500*(*PFNCCreateBuzzer_500)(unsigned char infoType); 
			PFNCCreateBuzzer_500 pFNCCreateBuzzer_500= (PFNCCreateBuzzer_500)GetProcAddress(g_hMod,"CreateBuzzer_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;
			//Buzzer_500 * buzzer_500= (pFNCCreateBuzzer_500)(0x01); //关蜂鸣器
			Buzzer_500 * buzzer_500= CreateBuzzer_500(0x01); //关蜂鸣器
			BOOL b_open = c_reader1->send(buzzer_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&buzzer_500;
		}
		break;

	case  IDC_TemperatureAndHumidityQuery_500:
		{
			/*typedef TemperatureAndHumidityQuery_500*(*PFNCCreateTemperatureAndHumidityQuery_500)(unsigned char  infoType); //读写器工作模式
			PFNCCreateTemperatureAndHumidityQuery_500 pFNCCreateTemperatureAndHumidityQuery_500= (PFNCCreateTemperatureAndHumidityQuery_500)GetProcAddress(g_hMod,"CreateTemperatureAndHumidityQuery_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;
			//RssiLimitConfig_500 * rssiLimitConfig_500= (pFNCCreateRssiLimitConfig_500)(0x01,buf,2); //查询
			TemperatureAndHumidityQuery_500 * temperatureAndHumidityQuery_500= CreateTemperatureAndHumidityQuery_500(0x00); //温度
			//TemperatureAndHumidityQuery_500 * temperatureAndHumidityQuery_500= (pFNCCreateTemperatureAndHumidityQuery_500)(0x01); //温度
			BOOL b_open = c_reader1->send(temperatureAndHumidityQuery_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				int iTemperature=temperatureAndHumidityQuery_500->GetTemperature();
				CString str;
				str.Format("温度为%02d",iTemperature);
				Debug_TRACE(str);
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				//int iHumidity = temperatureAndHumidityQuery_500->Humidity();
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&temperatureAndHumidityQuery_500;
		}
		break;

	case  IDC_AntennaInspect_500:
		{
			/*typedef AntennaInspect_500*(*PFNCCreateAntennaInspect_500)(); //读写器工作模式
			PFNCCreateAntennaInspect_500 pFNCCreateAntennaInspect_500= (PFNCCreateAntennaInspect_500)GetProcAddress(g_hMod,"CreateAntennaInspect_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//AntennaInspect_500 * antennaInspect_500= (pFNCCreateAntennaInspect_500)(); //温度
			AntennaInspect_500 * antennaInspect_500= CreateAntennaInspect_500(); //温度
			BOOL b_open = c_reader1->send(antennaInspect_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = antennaInspect_500->GetQueryData(buf,len);

				if (b_open)
				{
					CString str,strtemp;

					for (int i =0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void**)&antennaInspect_500;
		}
		break;

	case  IDC_IODevices_500:
		{
			/*typedef IODevices_500*(*PFNCCreateIODevices_500)(unsigned char infoType, unsigned char infoParameter); //
			PFNCCreateIODevices_500 pFNCCreateIODevices_500= (PFNCCreateIODevices_500)GetProcAddress(g_hMod,"CreateIODevices_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//IODevices_500 * iODevices_500Open= (pFNCCreateIODevices_500)(0x10,0x01); //开
			IODevices_500 * iODevices_500Open= CreateIODevices_500(0x10,0x01); //开
			BOOL b_open = c_reader1->send(iODevices_500Open);	
			CString str,strtemp;
			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = iODevices_500Open->GetQueryData(buf,len);
				if (b_open)
				{
					//CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}

			//IODevices_500 * iODevices_500= (pFNCCreateIODevices_500)(0x10,0x00); //关
			IODevices_500 * iODevices_500= CreateIODevices_500(0x10,0x00); //关
			b_open = c_reader1->send(iODevices_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = iODevices_500->GetQueryData(buf,len);
				if (b_open)
				{
					//CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&iODevices_500;
		}
		break;

	case IDC_DataSentTime_500:
		{
			/*typedef DataSentTime_500 *(*PFNCCreateDataSentTime_500)(unsigned char infoType, unsigned char *infoParameter,unsigned char infoParameterLen); //
			PFNCCreateDataSentTime_500 pFNCCreateDataSentTime_500= (PFNCCreateDataSentTime_500)GetProcAddress(g_hMod,"CreateDataSentTime_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//DataSentTime_500 * dataSentTime_500= (pFNCCreateDataSentTime_500)(0x01,buf,2); //关
			DataSentTime_500 * dataSentTime_500= CreateDataSentTime_500(0x01,buf,2); //关
			BOOL b_open = c_reader1->send(dataSentTime_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = dataSentTime_500->GetQueryData(buf,len);
				if (b_open)
				{
					CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}   //502E读写器不支持  没有查到对应的指令 后续要测试
			poiter1 = (void **)&dataSentTime_500;
		}
		break;

	case IDC_RelayStartState_500:
		{
			/*typedef RelayStartState_500 *(*PFNCCreateRelayStartState_500)(unsigned char infoType, unsigned char infoParameter); //
			PFNCCreateRelayStartState_500 pFNCCreateRelayStartState_500= (PFNCCreateRelayStartState_500)GetProcAddress(g_hMod,"CreateRelayStartState_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//RelayStartState_500* relayStartState_500= (pFNCCreateRelayStartState_500)(0x01,0x00); //关
			RelayStartState_500* relayStartState_500= CreateRelayStartState_500(0x01,0x00); //关
			BOOL b_open = c_reader1->send(relayStartState_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = relayStartState_500->GetQueryData(buf,len);

				if (b_open)
				{
					CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}//502E不支持这个指令 后续要测试

			poiter1 = (void **)&relayStartState_500;
		}
		break;

	case IDC_ResetToFactoryDefault_500:
		{
			/*typedef ResetToFactoryDefault_500 *(*PFNCCreateResetToFactoryDefault_500)(); //
			PFNCCreateResetToFactoryDefault_500 pFNCCreateResetToFactoryDefault_500= (PFNCCreateResetToFactoryDefault_500)GetProcAddress(g_hMod,"CreateResetToFactoryDefault_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//ResetToFactoryDefault_500* resetToFactoryDefault_500= (pFNCCreateResetToFactoryDefault_500)(); //关
			ResetToFactoryDefault_500* resetToFactoryDefault_500= CreateResetToFactoryDefault_500(); //关
			BOOL b_open = c_reader1->send(resetToFactoryDefault_500);	

			if (b_open)
			{
				TRACE("it is send ResetToFactoryDefault_500 ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ResetToFactoryDefault_500 ok\r\n");
			} 
			else
			{
				TRACE("it is send ResetToFactoryDefault_500 fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ResetToFactoryDefault_500 fail\r\n");
			}
			poiter1 = (void **)&resetToFactoryDefault_500;
		}
		break;

	case  IDC_DataSentMode_500:
		{
			/*typedef DataSentMode_500 * (*PFNCCreateDataSentMode_500)(unsigned char infoType, unsigned char  infoParameter); //
			PFNCCreateDataSentMode_500 pFNCCreateDataSentMode_500= (PFNCCreateDataSentMode_500)GetProcAddress(g_hMod,"CreateDataSentMode_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//DataSentMode_500 * dataSentMode_500 = (pFNCCreateDataSentMode_500)(0x01,0x00); //关
			DataSentMode_500 * dataSentMode_500 = CreateDataSentMode_500(0x01,0x00); //关
			BOOL b_open = c_reader1->send(dataSentMode_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = dataSentMode_500->GetQueryData(buf,len);

				if (b_open)
				{
					CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&dataSentMode_500;
		}
		break;

	case IDC_WhiteList_500:
		{
			/*typedef WhiteList_500 * (*PFNCCreateWhiteList_500)(unsigned char setType); //
			PFNCCreateWhiteList_500 pFNCCreateWhiteList_500= (PFNCCreateWhiteList_500)GetProcAddress(g_hMod,"CreateWhiteList_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//WhiteList_500  * whiteList_500 = (pFNCCreateWhiteList_500)(0x01); //关
			WhiteList_500  * whiteList_500 = CreateWhiteList_500(0x01); //关
			BOOL b_open = c_reader1->send(whiteList_500 );	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send IDC_WhiteList_500 ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send IDC_WhiteList_500 fail\r\n");
			}//未测，，不知什么意思
			poiter1 = (void **)&whiteList_500;
		}
		break;
	case IDC_WhiteListDownLoad_500:
		{
			/*typedef WhiteListDownLoad_500 * (*PFNCCreateWhiteListDownLoad_500)(unsigned char  *data,unsigned char  dataLen); //
			PFNCCreateWhiteListDownLoad_500 pFNCCreateWhiteListDownLoad_500= (PFNCCreateWhiteListDownLoad_500)GetProcAddress(g_hMod,"CreateWhiteListDownLoad_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//WhiteListDownLoad_500* whiteListDownLoad_500= (pFNCCreateWhiteListDownLoad_500)(buf,2); //关
			WhiteListDownLoad_500* whiteListDownLoad_500= CreateWhiteListDownLoad_500(buf,2); //关
			BOOL b_open = c_reader1->send(whiteListDownLoad_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send IDC_WhiteListDownLoad_500 ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send IDC_WhiteListDownLoad_500 fail\r\n");
			} //未测，不知什么意思
			poiter1 = (void **)&whiteListDownLoad_500;
		}
		break;

	case  IDC_WhiteListQuery_500:
		{
			/*typedef WhiteListQuery_500* (*PFNCCreateWhiteListQuery_500)(unsigned char *data,unsigned char dataLen); //
			PFNCCreateWhiteListQuery_500 pFNCCreateWhiteListQuery_500= (PFNCCreateWhiteListQuery_500)GetProcAddress(g_hMod,"CreateWhiteListQuery_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//WhiteListQuery_500* whiteListQuery_500= (pFNCCreateWhiteListQuery_500)(buf,2); //关
			WhiteListQuery_500* whiteListQuery_500= CreateWhiteListQuery_500(buf,2); //关
			BOOL b_open = c_reader1->send(whiteListQuery_500);	

			if (b_open)
			{
				CString str,strTemp;
				
				TRACE("it is send ok\r\n");
				unsigned char ret = whiteListQuery_500->GetFlag();
				strTemp.Format("%02d",ret);
				str = "flag==";
				str += strTemp;
				str += "\r\n count: ";
				b_open = whiteListQuery_500->GetCount(buf,len);
				if (b_open)
				{
					for (int i = 0; i< len ; i++)
					{
						strTemp.Format("%02d",buf[i]);
						str += strTemp;
					}
				}

				b_open = whiteListQuery_500->GetContent(buf,len);

				str += "\r\n Content: ";
				if (b_open)
				{
					for (int i = 0; i< len ; i++)
					{
						strTemp.Format("%02d",buf[i]);
						str += strTemp;
					}
				}
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);

			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}//未测不知什么意思
			poiter1 = (void **)&whiteListQuery_500;
		}
		break;

	case IDC_StartReaderAndReading_500:
		{
			/*typedef WiegandMode_500* (*PFNCCreateWiegandMode_500)(unsigned char infoType, unsigned char infoParameter); //
			PFNCCreateWiegandMode_500 pFNCCreateWiegandMode_500= (PFNCCreateWiegandMode_500)GetProcAddress(g_hMod,"CreateWiegandMode_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//WiegandMode_500* wiegandMode_500= (pFNCCreateWiegandMode_500)(0x00,0x01); //关
			WiegandMode_500* wiegandMode_500= CreateWiegandMode_500(0x00,0x01); //关
			BOOL b_open = c_reader1->send(wiegandMode_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				b_open = wiegandMode_500->GetQueryData(buf,len);
				if (b_open)
				{
					CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}


			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}  //不知参数什么意

			poiter1 = (void **)&wiegandMode_500;
		}
		break;

	case  IDC_ReadModeTrigger_500:
		{
			/*typedef ReadModeTrigger_500* (*PFNCCreateReadModeTrigger_500)(unsigned char infoType, unsigned char infoParameter); //
			PFNCCreateReadModeTrigger_500 pFNCCreateReadModeTrigger_500= (PFNCCreateReadModeTrigger_500)GetProcAddress(g_hMod,"CreateReadModeTrigger_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//ReadModeTrigger_500* readModeTrigger_500= (pFNCCreateReadModeTrigger_500)(0x00,0x01); //关
			ReadModeTrigger_500* readModeTrigger_500= CreateReadModeTrigger_500(0x00,0x01); //关
			BOOL b_open = c_reader1->send(readModeTrigger_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");

				b_open = readModeTrigger_500->GetQueryData(buf,len);

				if (b_open)
				{
					CString str,strtemp;
					for (int i = 0; i<len; i++)
					{
						strtemp.Format("%02x",buf[i]);
						str+=strtemp;
					}
					str+="\r\n";
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it sends fail\r\n");
			}
			poiter1 = (void **)&readModeTrigger_500;
		}
		break;

	case  IDC_ResetReader_500:
		{
			/*typedef ResetReader_500* (*PFNCCreateResetReader_500)(); //
			PFNCCreateResetReader_500 pFNCCreateResetReader_500= (PFNCCreateResetReader_500)GetProcAddress(g_hMod,"CreateResetReader_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//ResetReader_500* resetReader_500= (pFNCCreateResetReader_500)(); //关
			ResetReader_500* resetReader_500= CreateResetReader_500(); //关
			BOOL b_open = c_reader1->send(resetReader_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&resetReader_500;
		}
		break;
		
	case IDC_PcSendTime_500:
		{
			/*typedef PcSendTime_500* (*PFNCCreatePcSendTime_500)(unsigned char *readerID, unsigned char readerIDLen,unsigned char *time,unsigned char timeLen); //
			PFNCCreatePcSendTime_500 pFNCCreatePcSendTime_500= (PFNCCreatePcSendTime_500)GetProcAddress(g_hMod,"CreatePcSendTime_500Ex");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//PcSendTime_500* pcSendTime_500= (pFNCCreatePcSendTime_500)(buf,2,buf,2); //关
			//PcSendTime_500* pcSendTime_500= CreatePcSendTime_500Ex(buf,2,buf,2); //关
			PcSendTime_500* pcSendTime_500= CreatePcSendTime_500(); //关
			BOOL b_open = c_reader1->send(pcSendTime_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&pcSendTime_500;
		}
		break;

	case  IDC_Serverclientquery500:
		{
			/*typedef ServerClientQuery_500* (*PFNCCreateServerClientQuery_500)(); //
			PFNCCreateServerClientQuery_500 pFNCCreateServerClientQuery_500 = (PFNCCreateServerClientQuery_500)GetProcAddress(g_hMod,"CreateServerClientQuery_500");*/
			unsigned char buf[10];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 0;

			//ServerClientQuery_500* serverClientQuery_500 = (pFNCCreateServerClientQuery_500)(); //关
			ServerClientQuery_500* serverClientQuery_500 = CreateServerClientQuery_500(); //关

			BOOL b_open = c_reader1->send(serverClientQuery_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				unsigned char readIdLen;
				unsigned char readId[128];

				b_open = serverClientQuery_500->GetReaderID(readId,readIdLen);

				CString str,strtemp;
				int i;

				if (b_open)
				{
					str = "tag id: ";

					for (i = 0; i<readIdLen; i++ )
					{
						strtemp.Format("%02x",readId[i]);
						str +=strtemp;
					}
					str+="\r\n";
					Debug_TRACE(str);

				}

				unsigned char infoLen;
				unsigned char info[128];
				b_open = serverClientQuery_500->GetInfo(info,infoLen);


				if (b_open)
				{
					str += "info value: ";

					for (i = 0; i<infoLen; i++ )
					{
						strtemp.Format("%02x",info[i]);
						str +=strtemp;
					}

					Debug_TRACE(str);
				}
				str += "\r\n";
				unsigned char ucTcpIpStataus;
				ucTcpIpStataus = serverClientQuery_500->GetTcpIpStatus();

				strtemp.Format("TcpIpStatus is %02d",ucTcpIpStataus);
				Debug_TRACE(strtemp);
				str +=strtemp;
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);

			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&serverClientQuery_500;

		}
		break;

	case  IDC_Pcipsconfig500:
		{
			/*typedef PcIpsConfig_500* (*PFNCCreatePcIpsConfig_500)(unsigned char serverCount,unsigned char *ips,unsigned char ipsLen); 
			PFNCCreatePcIpsConfig_500 pFNCCreatePcIpsConfig_500= (PFNCCreatePcIpsConfig_500)GetProcAddress(g_hMod,"CreatePcIpsConfig_500");*/
			unsigned char buf[12];
			unsigned char len;
			memset(buf,'\0',sizeof(buf));
			buf[0]=0x00;
			buf[1] = 0x00;
			len = 2;
			unsigned char severCount;
			severCount = 0;
			//PcIpsConfig_500* pcIpsConfig_500= (pFNCCreatePcIpsConfig_500)(severCount,buf,len); //关
			PcIpsConfig_500* pcIpsConfig_500= CreatePcIpsConfig_500(severCount,buf,len); //关

			BOOL b_open = c_reader1->send(pcIpsConfig_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&pcIpsConfig_500;
		}
		break;

	case  IDC_PcIpsQuery_500:
		{
			/*typedef PcIpsQuery_500* (*PFNCCreatePcIpsQuery_500)(); 
			PFNCCreatePcIpsQuery_500 pFNCCreatePcIpsQuery_500= (PFNCCreatePcIpsQuery_500)GetProcAddress(g_hMod,"CreatePcIpsQuery_500");	*/

			//PcIpsQuery_500* pcIpsQuery_500= (pFNCCreatePcIpsQuery_500)(); //关
			PcIpsQuery_500* pcIpsQuery_500= CreatePcIpsQuery_500(); //关
			BOOL b_open = c_reader1->send(pcIpsQuery_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				unsigned char ucServerCount;
				ucServerCount = pcIpsQuery_500->GetServerCount();

				CString str,strTemp;
				str.Format("ServerCount: %02d\r\n",ucServerCount);
				Debug_TRACE(str);

				unsigned char pIpsInfo[128];
				unsigned char len;
				b_open = pcIpsQuery_500->GetIpsInfo(pIpsInfo,len);

				if (b_open)
				{
					str = "IpsInfo: ";
					for (int i = 0; i<len; i++)
					{
						strTemp.Format("%02x",pIpsInfo[i]);
						str +=strTemp;
					}
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}

			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&pcIpsQuery_500;
		}
		break;

	case IDC_QueryDhcp_500:
		{
			/*typedef QueryDhcp_500* (*PFNCCreateQueryDhcp_500)(); 
			PFNCCreateQueryDhcp_500 pFNCCreateQueryDhcp_500= (PFNCCreateQueryDhcp_500)GetProcAddress(g_hMod,"CreateQueryDhcp_500");	

			QueryDhcp_500 * queryDhcp_500= (pFNCCreateQueryDhcp_500)(); //关*/
			QueryDhcp_500 * queryDhcp_500= CreateQueryDhcp_500(); //关
			BOOL b_open = c_reader1->send(queryDhcp_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				unsigned char ucIpsInfo;
				ucIpsInfo = queryDhcp_500->GetIpsInfo();

				CString str,strTemp;
				str.Format("QueryDhcp_500 IpsInfo: %02d\r\n",ucIpsInfo);
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				Debug_TRACE(str);

			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&queryDhcp_500;
		}
		break;

	case  IDC_EnableDhcp_500:
		{
			/*typedef EnableDhcp_500* (*PFNCCreateEnableDhcp_500)(); 
			PFNCCreateEnableDhcp_500 pFNCCreateEnableDhcp_500 = (PFNCCreateEnableDhcp_500)GetProcAddress(g_hMod,"CreateEnableDhcp_500");	
			EnableDhcp_500 * enableDhcp_500= (pFNCCreateEnableDhcp_500)(); //关*/
			EnableDhcp_500 * enableDhcp_500= CreateEnableDhcp_500(); //关
			BOOL b_open = c_reader1->send(enableDhcp_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");

			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&enableDhcp_500;
		}
		break;

	case IDC_DisableDhcp_500:
		{

			/*typedef DisableDhcp_500* (*PFNCCreateDisableDhcp_500)(); 
			PFNCCreateDisableDhcp_500 pFNCCreateDisableDhcp_500 = (PFNCCreateDisableDhcp_500)GetProcAddress(g_hMod,"CreateDisableDhcp_500");	
			DisableDhcp_500* disableDhcp_500= (pFNCCreateDisableDhcp_500)(); //关*/

			//DisableDhcp_500* disableDhcp_500= CreateDisableDhcp_500(); //关
			unsigned char buf[64];
			memset(buf,'\0',sizeof(buf));
			unsigned char bufLen;
			bufLen = 10;
			buf[0] = 0xC0;
			buf[1] = 0xA8;
			buf[2] = 0x07;
			buf[3] = 0xF6;
			buf[4] = 0xFF;
			buf[5] = 0xFF;
			buf[6] = 0xFF;
			buf[7] = 0x00;
			buf[8] = 0xC0;
			buf[9] = 0xA8;
			buf[10] = 0x07;
			buf[11] = 0x01;
			bufLen = 12;
			DisableDhcp_500* disableDhcp_500= CreateDisableDhcp_500_2(buf,bufLen); //关
			BOOL b_open = c_reader1->send(disableDhcp_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&disableDhcp_500;
		}
		break;

	case  IDC_FrequencyTableConfig_F6B:
		{
			/*typedef FrequencyTableConfig_F6B* (*PFNCCreateFrequencyTableConfig_F6B)(); 
			PFNCCreateFrequencyTableConfig_F6B pFNCCreateFrequencyTableConfig_F6B = (PFNCCreateFrequencyTableConfig_F6B)GetProcAddress(g_hMod,"CreateFrequencyTableConfig_F6B");	
			FrequencyTableConfig_F6B* frequencyTableConfig_F6B= (pFNCCreateFrequencyTableConfig_F6B)(); //关*/

			unsigned char buf[2];
			FrequencyTableConfig_F6B* frequencyTableConfig_F6B= CreateFrequencyTableConfig_F6B(0x01,buf,2); //关
			BOOL b_open = c_reader1->send(frequencyTableConfig_F6B);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");

				unsigned char ucInfoType;
				ucInfoType = frequencyTableConfig_F6B->GetInfoType();

				CString str,strTemp;
				str.Format("ucInfoType: %02d\r\n",ucInfoType);
				Debug_TRACE(str);

				unsigned char pFrequencyTable[128];
				unsigned char len;
				b_open = frequencyTableConfig_F6B->GetFrequencyTable(pFrequencyTable,len);

				if (b_open)
				{
					str = "FrequencyTable: ";
					for (int i = 0; i<len; i++)
					{
						strTemp.Format("%02x",pFrequencyTable[i]);
						str +=strTemp;
					}
					Debug_TRACE(str);
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}

			poiter1 = (void **)&frequencyTableConfig_F6B;
		}
		break;
		
	case  IDC_FrequencyTableReset_F6B:
		{
			/*typedef FrequencyTableReset_F6B* (*PFNCCreateFrequencyTableReset_F6B)(); 
			PFNCCreateFrequencyTableReset_F6B pFNCCreateFrequencyTableReset_F6B = (PFNCCreateFrequencyTableReset_F6B)GetProcAddress(g_hMod,"CreateFrequencyTableReset_F6B");	
			FrequencyTableReset_F6B* frequencyTableReset_F6B= (pFNCCreateFrequencyTableReset_F6B)(); //关*/

			FrequencyTableReset_F6B* frequencyTableReset_F6B= CreateFrequencyTableReset_F6B(); //关
			BOOL b_open = c_reader1->send(frequencyTableReset_F6B);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&frequencyTableReset_F6B;
		}
		break;

	case IDC_AutoRead6CTagConfig_800:
		{
			/*typedef AutoRead6CTagConfig_800* (*PFNCCreateAutoRead6CTagConfig_800)(unsigned char infoType); 
			PFNCCreateAutoRead6CTagConfig_800 pFNCCreateAutoRead6CTagConfig_800 = (PFNCCreateAutoRead6CTagConfig_800)GetProcAddress(g_hMod,"CreateAutoRead6CTagConfig_800_A");	
			*/
			unsigned char infoType;
			infoType = 0x01;
			//AutoRead6CTagConfig_800 * autoRead6CTagConfig_800= (pFNCCreateAutoRead6CTagConfig_800)(infoType); //关
			AutoRead6CTagConfig_800 * autoRead6CTagConfig_800= CreateAutoRead6CTagConfig_800_A(infoType); //关
			BOOL b_open = c_reader1->send(autoRead6CTagConfig_800);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				CString str,strTemp;

				unsigned char ucInfoType;
				ucInfoType = autoRead6CTagConfig_800->GetInfoType();

				unsigned char ucTriggerPort;
				ucTriggerPort = autoRead6CTagConfig_800->GetTriggerPort();

				unsigned char ucTriggerType;
				ucTriggerType = autoRead6CTagConfig_800->GetTriggerType();

				unsigned char ucQ;
				ucQ = autoRead6CTagConfig_800->GetQ();

				unsigned char ucAntenna;
				ucAntenna = autoRead6CTagConfig_800->GetAntenna();

				str.Format("InfoType:%02d  TriggerPort:%02d  TriggerType:%02d Q:%02d  Antenna:%02d",ucInfoType,ucTriggerPort,ucTriggerType,ucQ,ucAntenna);
				Debug_TRACE(str);

				unsigned char pInfoParam[128];
				unsigned char pInfoParamLen;
				b_open = autoRead6CTagConfig_800->GetInfoParam(pInfoParam,pInfoParamLen);

				if (b_open)
				{
					str += "InfoParam:";
					for (int i = 0; i<pInfoParamLen;i++)
					{
						strTemp.Format("%02x",pInfoParam[i]);
						str += strTemp;
					}
					GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
					Debug_TRACE(str);

				}
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&autoRead6CTagConfig_800;
		}
		break;

	case IDC_ServerClientConfig_500:
		{
			/*typedef ServerClientConfig_500* (*PFNCCreateServerClientConfig_500)(unsigned char  type, unsigned char *ports,unsigned char portsLen); 
			PFNCCreateServerClientConfig_500 pFNCCreateServerClientConfig_500 = (PFNCCreateServerClientConfig_500)GetProcAddress(g_hMod,"CreateServerClientConfig_500");	
			*/
			unsigned char infoType;
			infoType = 0x01;

			unsigned char ports[4];
			unsigned char portsLen;//需要确认在测试
			memset(ports,0,sizeof(ports));
			portsLen = 4;
			//ServerClientConfig_500 * serverClientConfig_500= (pFNCCreateServerClientConfig_500)(infoType,ports,portsLen); //关
			ServerClientConfig_500 * serverClientConfig_500= CreateServerClientConfig_500(infoType,ports,portsLen); //关
			BOOL b_open = c_reader1->send(serverClientConfig_500);	

			if (b_open)
			{
				TRACE("it is send ok\r\n");
				unsigned char ucReaderType;
				ucReaderType = serverClientConfig_500->GetReaderType();
				CString str;
				str.Format("ReaderType:%02d",ucReaderType);
				Debug_TRACE(str);
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText(str);
			} 
			else
			{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
			poiter1 = (void **)&serverClientConfig_500;
		}
		break;

	case IDC_PowerParamConfig_500:
		{
			/*PowerParamConfig_500* CreatePowerParamConfig_500(unsigned char infoType, 
			  unsigned char *infoParameter,
			  unsigned char infoParameterLen)*/
			unsigned char infoType;
			infoType = 0x01;
			unsigned char infoParameter[2];
			unsigned char infoParameterLen;
			infoParameterLen = 2;
			memset(infoParameter,0,sizeof(infoParameter));
			PowerParamConfig_500* powerParamConfig_500= CreatePowerParamConfig_500(infoType,infoParameter,infoParameterLen); //关
			BOOL b_open = c_reader1->send(powerParamConfig_500);	
			if (b_open)
			{
				TRACE("it is send ok\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send ok\r\n");
			}
			else{
				TRACE("it is send fail\r\n");
				GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("it is send fail\r\n");
			}
		}
		break;
	case IDC_READ_6B_TID:
		{
			ReadTag* read_tag_6c_tid = CreateReadTag6cBank(ID_6B);
			read_tag_6c_tid->SetIsLoop(0x01);
			mytag.tagCount = 0;
			c_reader1->send(read_tag_6c_tid);
			poiter1 = (void **)&read_tag_6c_tid;
			SetTimer(1,900,NULL);

		}
		break;
	default:
		break;
	}

	if (poiter1 && *poiter1)
	{
		Sleep(100);
		CString str;
		if (ReleaseMem(poiter1))
		{
			str.Format("成功释放\"%s\"",ParameterDlg.strTestItemName);
			str += "对象内存";
			Debug_TRACE(str);
		} 
		else
		{
			str.Format("释放\"%s\"",ParameterDlg.strTestItemName);
			str += "对象内存失败";
			MessageBox(str.GetBuffer(str.GetLength()),"提示",MB_OK);
		}
		poiter1 = NULL;
	}

}

CString CTestDll_VS2010Dlg::StrsysQuery_500(unsigned char ikind)
{
	/*typedef SysQuery_500*(*PFNCCreateSysQuery_500)(unsigned char  infoType, unsigned char infoLength); //500读写器参数
	PFNCCreateSysQuery_500 pFNCCreateSysQuery_500= (PFNCCreateSysQuery_500)GetProcAddress(g_hMod,"CreateSysQuery_500");*/
	SysQuery_500 * sysQuery_500;
	
	if (ikind == 0x00)
	{
		//sysQuery_500= (pFNCCreateSysQuery_500)(0x00,0x0C); //ip地址 
		sysQuery_500= CreateSysQuery_500(0x00,0x0C); //ip地址 
	}
	else if(ikind == 0x02)
	{
		//sysQuery_500= (pFNCCreateSysQuery_500)(0x02,0x01); //天线数
		sysQuery_500= CreateSysQuery_500(0x02,0x01); //天线数
	}
	else if (ikind ==0x04 )
	{
		//sysQuery_500= (pFNCCreateSysQuery_500)(0x04,0x06); //MAC地址
		sysQuery_500= CreateSysQuery_500(0x04,0x06); //MAC地址
	}
	else if (ikind == 0x05)
	{
		//sysQuery_500= (pFNCCreateSysQuery_500)(0x05,0x08); //设备地址
		sysQuery_500= CreateSysQuery_500(0x05,0x08); //设备地址
	}
	else if (ikind == 0x01)
	{
		//sysQuery_500= (pFNCCreateSysQuery_500)(0x01,0x01E); //调频
		sysQuery_500= CreateSysQuery_500(0x01,0x01E); //调频
	}

	BOOL b_open = c_reader1->send(sysQuery_500);	

	CString str;
	CString strTemp;

	if (b_open)
	{
		unsigned char buf[128];
		unsigned char dataLen;
		dataLen = 0x00;

		b_open = sysQuery_500->GetQueryData(buf,dataLen);

		str = "";
		if (b_open)
		{
			for (int i = 0; i < dataLen ;i++)
			{
				strTemp.Format("%02x",buf[i]);
				str += strTemp;

			}

			TRACE("查询数据成功\n");

		}
	} 
	else
	{
		TRACE("发送指令失败\r\n");
	}

	/*delete sysQuery_500;
	sysQuery_500 = NULL;*/
	ReleaseMem((void **)&sysQuery_500);
	return str;
}


void CTestDll_VS2010Dlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO: 在此添加消息处理程序代码和/或调用默认值
	if (nIDEvent == 1)
	{
		listData();
	}
	else if (nIDEvent == 2)
	{
		KillTimer(2);
		listData();
		
	}
	else if(nIDEvent == 3)
	{
		
		if (isPowerOff)
		{
			KillTimer(3);
			listData();
			setEpc();
			
		}
	}
	CDialogEx::OnTimer(nIDEvent);
}

//配置io触发
void CTestDll_VS2010Dlg::OnBnClickedBtnChangjing()
{
	// TODO: 在此添加控件通知处理程序代码
	void **poiter1=NULL;
	c_reader1->setCallBack(onMessageNotify_list,this,c_reader1);           //设置回调函数

	ReadTag* read_tag_6c_tid = CreateReadTag6cBank(TID_6C);
	read_tag_6c_tid->SetIsLoop(0x01);
	mytag.tagCount = 0;
	
	int i_tag_tid = read_tag_6c_tid->TransmitterData(TCPIP_Client);
	PowerOff  * powerOff = CreatePowerOff();
	int i_poweroff = powerOff->TransmitterData(TCPIP_Client);
	
	unsigned char portInfo[256];
	memset(portInfo,0,sizeof(portInfo));
	portInfo[0] = 0x01;//I/O端口1
	portInfo[1] = 0x01;//上升沿触发
	portInfo[2] = 0x01;//定时停止
	portInfo[3] = 0x00;//时间
	portInfo[4] = 0x1e;//时间（30*100ms = 3秒）
	SysConfig_800* msg = NULL;

	memcpy(portInfo+5,((BaseMessage*)read_tag_6c_tid)->txData,i_tag_tid);
	memcpy(portInfo+5+i_tag_tid,((BaseMessage*)powerOff)->txData,i_poweroff);
	msg = CreateSysConfig_800(0xE2,portInfo,5+i_poweroff+i_tag_tid);//0x06为设置IP代号，buf为IP、掩码、路由三者一起转换成char数组了。;
	bool bflag = c_reader1->send(msg);

	if (bflag)
	{
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置成功");
	}
	else
	{
		GetDlgItem(IDC_EDIT_STATIC)->SetWindowText("设置失败");
	}

	poiter1 = (void **)&msg;
	ReleaseMem(poiter1);
	poiter1= NULL;

	poiter1 = (void **)&read_tag_6c_tid;
	ReleaseMem(poiter1);
	poiter1= NULL;

	poiter1 = (void **)&powerOff;
	ReleaseMem(poiter1);
	poiter1= NULL;

	SetTimer(3,900,NULL);

}
