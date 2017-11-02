
#include "stdafx.h"
#include "TestDll_VS2010.h"
#include "TestDll_VS2010Dlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

BEGIN_MESSAGE_MAP(CTestDll_VS2010App, CWinApp)
	ON_COMMAND(ID_HELP, &CWinApp::OnHelp)
END_MESSAGE_MAP()
CTestDll_VS2010App::CTestDll_VS2010App()
{
	m_dwRestartManagerSupportFlags = AFX_RESTART_MANAGER_SUPPORT_RESTART;

}
CTestDll_VS2010App theApp;

BOOL CTestDll_VS2010App::InitInstance()
{

	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);

	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinApp::InitInstance();


	AfxEnableControlContainer();
	CShellManager *pShellManager = new CShellManager;
	SetRegistryKey(_T("应用程序向导生成的本地应用程序"));

	CTestDll_VS2010Dlg dlg;
	//CDlagTestTime dlg;
	m_pMainWnd = &dlg;
	INT_PTR nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{

	}
	else if (nResponse == IDCANCEL)
	{

	}
	if (pShellManager != NULL)
	{
		delete pShellManager;
	}
	return FALSE;
}

