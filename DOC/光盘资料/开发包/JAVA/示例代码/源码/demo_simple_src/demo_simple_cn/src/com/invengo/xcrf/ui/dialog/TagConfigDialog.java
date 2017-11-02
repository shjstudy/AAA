package com.invengo.xcrf.ui.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.Const;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.component.DefaultJSpinner;

public class TagConfigDialog extends JDialog {
	
	private JDialog configDialog;
	
	private DefaultJSpinner spinner;
	private DefaultJSpinner spinner_1;
	private DefaultJSpinner spinner_3;
	private DefaultJSpinner spinner_2;
	private DefaultJSpinner spinner_4;
	private DefaultJSpinner spinner_5;
	private DefaultJSpinner spinner_7;
	private DefaultJSpinner spinner_6;
	private DefaultJSpinner spinner_8;
	private DefaultJSpinner spinner_9;
	private DefaultJSpinner spinner_10;
	private DefaultJSpinner spinner_11;
	private DefaultJSpinner spinner_12;
	private DefaultJSpinner spinner_13;
	private DefaultJSpinner spinner_14;
	private DefaultJSpinner spinner_15;

	/**
	 * Create the panel.
	 */
	public TagConfigDialog() {
		
		
		configDialog = this;
		setBounds(100, 100, 475, 600);
		getContentPane().setLayout(null);
		
		JButton button = new JButton(BaseMessages.getString("TagConfigForm.button1"));
		button.setBounds(291, 524, 79, 23);
		getContentPane().add(button);
		
		JButton button_1 = new JButton(BaseMessages.getString("TagConfigForm.button2"));
		button_1.setBounds(380, 524, 79, 23);
		getContentPane().add(button_1);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "6C:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_10.setBounds(10, 10, 449, 504);
		getContentPane().add(panel_10);
		panel_10.setLayout(null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBounds(10, 439, 429, 55);
		panel_10.add(panel_9);
		panel_9.setBorder(new TitledBorder(null, "6B:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_9.setLayout(null);
		
		JLabel label_14 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_14.setBounds(25, 21, 98, 15);
		panel_9.add(label_14);
		
		JLabel label_15 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_15.setBounds(232, 21, 73, 15);
		panel_9.add(label_15);
		
		spinner_14 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_14.setBounds(118, 18, 93, 22);
		panel_9.add(spinner_14);
		
		spinner_15 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_15.setBounds(320, 18, 93, 22);
		panel_9.add(spinner_15);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(10, 375, 429, 55);
		panel_10.add(panel_8);
		panel_8.setBorder(new TitledBorder(null, BaseMessages.getString("TagConfigForm.groupBox4"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_8.setLayout(null);
		
		JLabel label_12 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_12.setBounds(25, 21, 98, 15);
		panel_8.add(label_12);
		
		JLabel label_13 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_13.setBounds(232, 21, 73, 15);
		panel_8.add(label_13);
		
		spinner_12 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_12.setBounds(118, 18, 93, 22);
		panel_8.add(spinner_12);
		
		spinner_13 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_13.setBounds(320, 18, 93, 22);
		panel_8.add(spinner_13);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(10, 314, 429, 55);
		panel_10.add(panel_7);
		panel_7.setBorder(new TitledBorder(null, "TID:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_7.setLayout(null);
		
		JLabel label_10 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_10.setBounds(25, 21, 98, 15);
		panel_7.add(label_10);
		
		JLabel label_11 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_11.setBounds(232, 21, 73, 15);
		panel_7.add(label_11);
		
		spinner_10 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_10.setBounds(118, 18, 93, 22);
		panel_7.add(spinner_10);
		
		spinner_11 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_11.setBounds(320, 18, 93, 22);
		panel_7.add(spinner_11);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 137, 429, 168);
		panel_10.add(panel_3);
		panel_3.setBorder(new TitledBorder(null, "EPC:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_3.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "CRC:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_4.setLayout(null);
		panel_4.setBounds(10, 22, 208, 79);
		panel_3.add(panel_4);
		
		JLabel label_4 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_4.setBounds(10, 23, 98, 15);
		panel_4.add(label_4);
		
		JLabel label_5 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_5.setBounds(10, 45, 73, 15);
		panel_4.add(label_5);
		
		spinner_4 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_4.setBounds(108, 16, 93, 22);
		panel_4.add(spinner_4);
		
		spinner_5 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_5.setBounds(108, 45, 93, 22);
		panel_4.add(spinner_5);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "PC:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_5.setLayout(null);
		panel_5.setBounds(223, 22, 196, 79);
		panel_3.add(panel_5);
		
		JLabel label_6 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_6.setBounds(10, 25, 98, 15);
		panel_5.add(label_6);
		
		JLabel label_7 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_7.setBounds(9, 47, 73, 15);
		panel_5.add(label_7);
		
		spinner_6 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_6.setBounds(97, 47, 93, 22);
		panel_5.add(spinner_6);
		
		spinner_7 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_7.setBounds(97, 18, 93, 22);
		panel_5.add(spinner_7);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "EPC:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_6.setLayout(null);
		panel_6.setBounds(10, 103, 409, 55);
		panel_3.add(panel_6);
		
		JLabel label_8 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_8.setBounds(10, 21, 98, 15);
		panel_6.add(label_8);
		
		JLabel label_9 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_9.setBounds(222, 21, 73, 15);
		panel_6.add(label_9);
		
		spinner_8 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_8.setBounds(108, 18, 93, 22);
		panel_6.add(spinner_8);
		
		spinner_9 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_9.setBounds(310, 18, 93, 22);
		panel_6.add(spinner_9);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 23, 429, 107);
		panel_10.add(panel);
		panel.setBorder(new TitledBorder(null, BaseMessages.getString("TagConfigForm.groupBox1"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, BaseMessages.getString("TagConfigForm.groupBox7"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_1.setBounds(10, 22, 208, 79);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label.setBounds(10, 23, 98, 15);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_1.setBounds(10, 45, 73, 15);
		panel_1.add(label_1);
		
		spinner = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner.setBounds(108, 16, 93, 22);
		panel_1.add(spinner);
		
		spinner_1 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_1.setBounds(108, 45, 93, 22);
		panel_1.add(spinner_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), BaseMessages.getString("TagConfigForm.groupBox6"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_2.setBounds(222, 22, 197, 79);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label_2 = new JLabel(BaseMessages.getString("TagConfigForm.label11"));
		label_2.setBounds(10, 25, 98, 15);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel(BaseMessages.getString("TagConfigForm.label12"));
		label_3.setBounds(10, 47, 73, 15);
		panel_2.add(label_3);
		
		spinner_2 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_2.setBounds(98, 47, 93, 22);
		panel_2.add(spinner_2);
		
		spinner_3 = new DefaultJSpinner(new SpinnerNumberModel(0,0,65535,1));
		spinner_3.setBounds(98, 18, 93, 22);
		panel_2.add(spinner_3);

		setResizable(false);
		setModal(true);
		
		//初始化标签页面
		loadTagConfig();
		
		//添加监听
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				saveTagConfig();
			}
		});
		
		button_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				configDialog.setVisible(false);
			}
		});
	}
	
	
	public void loadTagConfig()
	{
		spinner.setValue(Common.killPwd_Ptr);
		spinner_1.setValue(Common.killPwd_Len);
		spinner_2.setValue(Common.accessPwd_Ptr); 
		spinner_3.setValue(Common.accessPwd_Len); 
		spinner_4.setValue(Common.EPC_CRC_Ptr); 
		spinner_5.setValue(Common.EPC_CRC_Len); 
		spinner_7.setValue(Common.EPC_PC_Ptr); 
		spinner_6.setValue(Common.EPC_PC_Len); 
		spinner_8.setValue(Common.EPC_Ptr); 
		spinner_9.setValue(Common.EPC_MaxLen); 
		spinner_10.setValue(Common.TID_Ptr);
		spinner_11.setValue(Common.TID_MaxLen);
		spinner_12.setValue(Common.userdata_Ptr);
		spinner_13.setValue(Common.Userdata_MaxLen_6C);
		spinner_14.setValue(Common.ID_MaxLen);
		spinner_15.setValue(Common.userdata_MaxLen_6B);
	}
	
	private void saveTagConfig()
	{
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(Const.fn);
			Element tagTypeE = (Element) XPath.newInstance(
			"//TagType").selectSingleNode(
					doc);
			if(tagTypeE != null)
			{
				((Element) XPath.newInstance("//Tag_6C//M0//KillPWD//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M0//KillPWD//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_1.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M0//AccessPWD//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_2.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M0//AccessPWD//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_3.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//CRC//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_4.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//CRC//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_5.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//PC//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_7.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//PC//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_6.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//EPC//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_8.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M1//EPC//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_9.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M2//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_10.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M2//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_11.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M3//Ptr")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_12.getIntValue()));
				((Element) XPath.newInstance("//Tag_6C//M3//Len")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_13.getIntValue()));
				((Element) XPath.newInstance("//Tag_6B//ID").selectSingleNode(
						tagTypeE)).setText(String.valueOf(spinner_14
						.getIntValue()));
				((Element) XPath.newInstance("//Tag_6B//Userdata")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(spinner_15.getIntValue()));
				
				Common.killPwd_Ptr   = spinner.getIntValue();
				Common.killPwd_Len   = spinner_1.getIntValue();
				Common.accessPwd_Ptr = spinner_2.getIntValue();
				Common.accessPwd_Len = spinner_3.getIntValue();
				Common.EPC_CRC_Ptr   = spinner_4.getIntValue();
				Common.EPC_CRC_Len   = spinner_5.getIntValue();
				Common.EPC_PC_Ptr    = spinner_7.getIntValue();
				Common.EPC_PC_Len    = spinner_6.getIntValue();
				Common.EPC_Ptr       = spinner_8.getIntValue();
				Common.EPC_MaxLen    = spinner_9.getIntValue();
				Common.TID_Ptr       = spinner_10.getIntValue();
				Common.TID_MaxLen    = spinner_11.getIntValue();
				Common.userdata_Ptr  = spinner_12.getIntValue();
				Common.Userdata_MaxLen_6C = spinner_13.getIntValue();
				Common.ID_MaxLen = spinner_14.getIntValue();
                Common.userdata_MaxLen_6B = spinner_15.getIntValue();
				
			}else
			{
				Element rootE = doc.getRootElement();
				tagTypeE = new Element("TagType");
				
				Element tag_6c = new Element("Tag_6C");
				
				//m0 start
				Element m0 = new Element("M0");
				
				Element killPwd = new Element("KillPWD");
				Element killPwdPtr = new Element("Ptr");
				killPwdPtr.setText(String.valueOf(spinner.getIntValue()));
				Element killPwdLen = new Element("Len");
				killPwdLen.setText(String.valueOf(spinner_1.getIntValue()));
				killPwd.addContent(killPwdPtr);
				killPwd.addContent(killPwdLen);
				m0.addContent(killPwd);
				
				Element accessPWD = new Element("AccessPWD");
				Element accessPWDPtr = new Element("Ptr");
				accessPWDPtr.setText(String.valueOf(spinner_2.getIntValue()));
				Element accessPWDLen = new Element("Len");
				accessPWDLen.setText(String.valueOf(spinner_3.getIntValue()));
				accessPWD.addContent(accessPWDLen);
				accessPWD.addContent(accessPWDPtr);
				m0.addContent(accessPWD);
				
				tag_6c.addContent(m0);
				//m0 end
				
				//m1 start			
				Element m1 = new Element("M1");
				
				Element crc = new Element("CRC");
				Element crcPtr = new Element("Ptr");
				crcPtr.setText(String.valueOf(spinner_4.getIntValue()));
				Element crcLen = new Element("Len");
				crcLen.setText(String.valueOf(spinner_5.getIntValue()));
				crc.addContent(crcPtr);
				crc.addContent(crcLen);
				m1.addContent(crc);
				
				Element pc = new Element("PC");
				Element pcPtr = new Element("Ptr");
				pcPtr.setText(String.valueOf(spinner_7.getIntValue()));
				Element pcLen = new Element("Len");
				pcLen.setText(String.valueOf(spinner_6.getIntValue()));
				pc.addContent(pcPtr);
				pc.addContent(pcLen);
				m1.addContent(pc);
				
				Element epc = new Element("EPC");
				Element epcPtr = new Element("Ptr");
				epcPtr.setText(String.valueOf(spinner_8.getIntValue()));
				Element epcLen = new Element("Len");
				epcLen.setText(String.valueOf(spinner_9.getIntValue()));
				epc.addContent(epcPtr);
				epc.addContent(epcLen);
				m1.addContent(epc);
				
				tag_6c.addContent(m1);
				//m1 end
				
				Element m2 = new Element("M2");
				Element m2Ptr = new Element("Ptr");
				m2Ptr.setText(String.valueOf(spinner_10.getIntValue()));
				Element m2Len = new Element("Len");
				m2Len.setText(String.valueOf(spinner_11.getIntValue()));
				m2.addContent(m2Ptr);
				m2.addContent(m2Len);
				tag_6c.addContent(m2);
				
				Element m3 = new Element("M3");
				Element m3Ptr = new Element("Ptr");
				m3Ptr.setText(String.valueOf(spinner_12.getIntValue()));
				Element m3Len = new Element("Len");
				m3Len.setText(String.valueOf(spinner_13.getIntValue()));
				m3.addContent(m3Ptr);
				m3.addContent(m3Len);
				tag_6c.addContent(m3);
				
				tagTypeE.addContent(tag_6c);
				
				
				Element tag_6b = new Element("Tag_6B");
				Element id = new Element("ID");
				id.setAttribute("Len",String.valueOf(spinner_14.getIntValue()));
				tag_6b.addContent(id);
				Element usrdata = new Element("Userdata");
				usrdata.setAttribute("Len", String.valueOf(spinner_15.getIntValue()));
				tag_6b.addContent(usrdata);
				tagTypeE.addContent(tag_6b);
				
				rootE.addContent(tagTypeE);
				
			}
			
			xmlOut.output(doc, new FileOutputStream(new File(Const.fn)));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
}
