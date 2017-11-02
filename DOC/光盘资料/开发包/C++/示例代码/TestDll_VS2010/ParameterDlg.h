#pragma once


// CParameterDlg 对话框

class CParameterDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CParameterDlg)

public:
	CParameterDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CParameterDlg();

	virtual void OnFinalRelease();

// 对话框数据
	enum { IDD = IDD_DlgParameter };
public:
	int  CheckedNum;
	int  CheckBoxID;
	CString strTestItemName;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	DECLARE_DISPATCH_MAP()
	DECLARE_INTERFACE_MAP()
	virtual BOOL OnCommand(WPARAM wParam, LPARAM lParam);
public:
	afx_msg void OnBnClickedOk();
	virtual BOOL OnInitDialog();
};
