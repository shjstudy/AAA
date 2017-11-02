// ParameterDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "TestDll_VS2010.h"
#include "ParameterDlg.h"
#include "afxdialogex.h"


// CParameterDlg 对话框

IMPLEMENT_DYNAMIC(CParameterDlg, CDialogEx)

CParameterDlg::CParameterDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CParameterDlg::IDD, pParent)
{
	CheckedNum = 0;
	CheckBoxID = -1;
	EnableAutomation();

}

CParameterDlg::~CParameterDlg()
{
}

void CParameterDlg::OnFinalRelease()
{
	// 释放了对自动化对象的最后一个引用后，将调用
	// OnFinalRelease。基类将自动
	// 删除该对象。在调用该基类之前，请添加您的
	// 对象所需的附加清理代码。

	CDialogEx::OnFinalRelease();
}

void CParameterDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CParameterDlg, CDialogEx)
	ON_BN_CLICKED(IDOK, &CParameterDlg::OnBnClickedOk)
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(CParameterDlg, CDialogEx)
END_DISPATCH_MAP()

// 注意: 我们添加 IID_IParameterDlg 支持
//  以支持来自 VBA 的类型安全绑定。此 IID 必须同附加到 .IDL 文件中的
//  调度接口的 GUID 匹配。

// {40E2AB2E-F975-488A-95FD-86C30DDC25F5}
static const IID IID_IParameterDlg =
{ 0x40E2AB2E, 0xF975, 0x488A, { 0x95, 0xFD, 0x86, 0xC3, 0xD, 0xDC, 0x25, 0xF5 } };

BEGIN_INTERFACE_MAP(CParameterDlg, CDialogEx)
	INTERFACE_PART(CParameterDlg, IID_IParameterDlg, Dispatch)
END_INTERFACE_MAP()


BOOL CParameterDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	//if (CheckBoxID >= IDC_PowerOn && CheckBoxID <= IDC_TestModeConfig_800)//mark by shuangIDC_POWEROFF_500
	//if (CheckBoxID >= IDC_PowerOn && CheckBoxID <= IDC_ServerClientConfig_500)
	//if (CheckBoxID >= IDC_PowerOn && CheckBoxID <= IDC_POWEROFF_500)
	if (CheckBoxID >= IDC_PowerOn && CheckBoxID <= IDC_READ_6B_TID)
	{
		((CButton*)GetDlgItem(CheckBoxID))->SetCheck(1);
	}

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}


// CParameterDlg 消息处理程序


BOOL CParameterDlg::OnCommand(WPARAM wParam, LPARAM lParam)
{
	// TODO: 在此添加专用代码和/或调用基类

	return CDialogEx::OnCommand(wParam, lParam);
}


void CParameterDlg::OnBnClickedOk()
{
	CheckedNum = 0;
	//for (int i = IDC_PowerOn; i <= IDC_TestModeConfig_800; i++)//mark by shuangIDC_POWEROFF_500
	//for (int i = IDC_PowerOn; i <= IDC_ServerClientConfig_500; i++)
	//for (int i = IDC_PowerOn; i <= IDC_POWEROFF_500; i++)
	for (int i = IDC_PowerOn; i <= IDC_READ_6B_TID; i++)	
	{
		UINT nState =  IsDlgButtonChecked(i);
		if (1 == nState)
		{
			CheckedNum++;
			CheckBoxID = i;
		}
	}
	if (CheckedNum > 1)
	{
		MessageBox("只能选择一个测试项","提醒",MB_OK);
		return;
	}

	GetDlgItemText(CheckBoxID,strTestItemName);

	CDialogEx::OnOK();
}
