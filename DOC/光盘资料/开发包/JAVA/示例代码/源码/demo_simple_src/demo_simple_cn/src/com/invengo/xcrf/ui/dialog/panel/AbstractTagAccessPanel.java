package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.Util;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.invengo.xcrf.core.i18n.BaseMessages;

public abstract class AbstractTagAccessPanel extends JPanel implements ICallBack{
	protected JTable dt;

	protected List<Integer> successLst=null;
	protected List<Integer> failLst = null;	
	
	private JPasswordField pwdField;
	
	public void setPwdField(JPasswordField pwdField) {
		this.pwdField = pwdField;
	}

	public byte[] getPwd() {
		String pwdText=this.pwdField.getText();
		if (pwdText.equals(""))
			return new byte[4];
		byte[] pwd = Util.convertHexStringToByteArray(pwdText);
		if (pwd.length < 4)
		{
			byte[] p = new byte[4];
			System.arraycopy(pwd, 0, p, 4 - pwd.length, pwd.length);
			return p;
		}
		return pwd;
	}
	
	public byte[] getPwd(String pwdText)
	{
		if (pwdText.equals(""))
			return new byte[4];
		byte[] pwd = Util.convertHexStringToByteArray(pwdText);
		if (pwd.length < 4)
		{
			byte[] p = new byte[4];
			System.arraycopy(pwd, 0, p, 4 - pwd.length, pwd.length);
			return p;
		}
		return pwd;
	}
	
	protected String getTextString(String name){
		return BaseMessages.getString(name);
	}

	public AbstractTagAccessPanel(JTable dt) {
		super();
		this.dt = dt;
		init();
	}

	public AbstractTagAccessPanel() {
		super();
		init();
		setSize(1009, 230);
	}	
	
	
	public AbstractTagAccessPanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super();
		this.dt = dt;
		this.successLst = successLst;
		this.failLst = failLst;
		init();
		setSize(790, 230);
	}

	/**
	 * 初始化操作
	 */
	protected abstract void init();
	
	public void makeFace(JTable table) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					if(successLst.contains(row)){
						setBackground(new Color(0,115,0)); 
					}
					else if(failLst.contains(row)){
						setBackground(new Color(115,0,0)); 
					}
					else
					{
						setBackground(Color.white); 
					}
					return super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			table.getSelectionModel().clearSelection();
			table.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public List<Integer> getSuccessLst() {
		return successLst;
	}

	public void setSuccessLst(List<Integer> successLst) {
		this.successLst = successLst;
	}

	public List<Integer> getFailLst() {
		return failLst;
	}

	public void setFailLst(List<Integer> failLst) {
		this.failLst = failLst;
	}

	public void updateView(IMessage message) {
		System.out.println("更新");
	}	
	
	
	byte[] getWriteData(String str)
	{
		byte[] data = Util.convertHexStringToByteArray(str);
		if (data.length % 2 == 1)
		{
			byte[] d = new byte[data.length + 1];
			System.arraycopy(data, 0, d, 0, data.length);
			return d;
		}
		return data;
	}
	
	protected byte[] bytesAdd(byte[] bytes, byte[] index)
    {
        byte[] b = new byte[bytes.length];
        System.arraycopy(bytes, 0, b, 0, b.length);
        int pre = 0;
        for (int i = 0; i < index.length; i++)
        {
            int bs = bytes[bytes.length - i - 1] + index[index.length - i - 1] + pre;
            pre = bs / 256;
            b[b.length - i - 1] = (byte)(bs % 256);
        }
        int p = index.length;
        if (pre > 0 && b.length > p)
        {
            while (b[b.length - p - 1] == 0xff)
            {
                b[b.length - p - 1] = 0;
                p++;
                if (b.length <= p)
                    break;
            }
            if (b.length > p)
                b[b.length - p - 1] += 1;
        }
        return b;
    }
	
	
	public void initResultLst() {
		successLst.clear();
		failLst.clear();
	}
	
}
