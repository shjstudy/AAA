
// TestDll_VS2010Dlg.h : 头文件
//

#pragma once
#include "ParameterDlg.h"


// CTestDll_VS2010Dlg 对话框
class CTestDll_VS2010Dlg : public CDialogEx
{
// 构造
public:
	CTestDll_VS2010Dlg(CWnd* pParent = NULL);	// 标准构造函数
	CString StrsysQuery_500(unsigned char ikind);

		void listData();
// 对话框数据
	enum { IDD = IDD_TESTDLL_VS2010_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持


// 实现
protected:
	HICON m_hIcon;
	CParameterDlg ParameterDlg;
	void TestItem(int ItemID);
	void  setEpc();

	// 生成的消息映射函数B
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnClickedBtnStartTest();
	afx_msg void OnClickedBtnConnOrDisconn();
	afx_msg void OnClickedBtnSeltestitem();
	afx_msg void OnClose();
	afx_msg void OnClickedBtnMutiReaderConnOrDisc();
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnBnClickedBtnChangjing();
};
