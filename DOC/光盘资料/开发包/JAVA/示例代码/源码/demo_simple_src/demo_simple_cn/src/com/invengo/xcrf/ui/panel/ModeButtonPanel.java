package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.ui.MainFrame;

public class ModeButtonPanel extends JPanel implements LanguageChangeListener{

	
	private MainFrame frame;
	public JLabel lblReadDataMode;
	public JLabel lblScanMode;
	public JLabel lblTestMode;
	public MyMouseListener ReadDate_MouseListener;
	public MyMouseListener Scan_MouseListener;
	public MyMouseListener Test_MouseListener;
	
//	private Color noFcuk = new Color(5,123,180);
//	private Color fcuk = new Color(201,159,33);
	public String currentMode  =  "pic_Demo";
	
	final ImageIcon noFcuk = new ImageIcon(MainFrame.class
			.getResource("image/OpBtn1.png"));
	final ImageIcon fcuk = new ImageIcon(MainFrame.class
			.getResource("image/OpBtn2.png"));
	final ImageIcon offFcuk = new ImageIcon(MainFrame.class
			.getResource("image/OpBtn3.png"));
	
	public ModeButtonPanel(JFrame frame){
		
		this.frame = (MainFrame)frame;
		
		lblReadDataMode = new JLabel(BaseMessages.getString("MainForm.pic_Demo"));
		lblScanMode = new JLabel(BaseMessages.getString("MainForm.pic_Scan"));
		lblTestMode = new JLabel(BaseMessages.getString("MainForm.pic_Test"));
		//演示模式
		lblReadDataMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblReadDataMode.setPreferredSize(new Dimension(120, 40));
		lblReadDataMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblReadDataMode.setOpaque(true);
		lblReadDataMode.setIconTextGap(-85);
		//lblReadDataMode.setBackground(fcuk);
		lblReadDataMode.setIcon(fcuk);
		//扫描模式
		lblScanMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblScanMode.setPreferredSize(new Dimension(120, 40));
		lblScanMode.setOpaque(true);
		//lblScanMode.setBackground(noFcuk);
		lblScanMode.setIconTextGap(-85);
		lblScanMode.setIcon(noFcuk);
		//测试模式
		lblTestMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblTestMode.setPreferredSize(new Dimension(120, 40));
		lblTestMode.setOpaque(true);
		//lblTestMode.setBackground(noFcuk);
		lblTestMode.setIcon(noFcuk);
		lblTestMode.setVisible(false);
		
		
		
		
		JPanel whitePanel1 = new JPanel();
		whitePanel1.setOpaque(false);
		
		JPanel whitePanel2 = new JPanel();
		whitePanel2.setOpaque(false);
		
		JPanel modebuttonPanel = new JPanel();
		modebuttonPanel.setOpaque(false);
		
		setLayout(new BorderLayout());
		add(whitePanel1,BorderLayout.WEST);
		add(modebuttonPanel,BorderLayout.CENTER);
		whitePanel2.setPreferredSize(new Dimension(27,40));
		//add(whitePanel2,BorderLayout.EAST);
		
		modebuttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,1,1));
		modebuttonPanel.add(lblReadDataMode);
		modebuttonPanel.add(lblScanMode);
		modebuttonPanel.add(lblTestMode);
		
		
		setOpaque(false);
		setPreferredSize(new Dimension(400,40));
		
		
		ReadDate_MouseListener = new MyMouseListener("pic_Demo");
		Scan_MouseListener = new MyMouseListener("pic_Scan");
		Test_MouseListener = new MyMouseListener("pic_Test");
		
		
		lblReadDataMode.addMouseListener(ReadDate_MouseListener);
		lblScanMode.addMouseListener(Scan_MouseListener);
		lblTestMode.addMouseListener(Test_MouseListener);
	
		BaseMessages.addListener(this);
	}
public class MyMouseListener implements MouseListener{
		
		public ImageIcon c;
		public String panelName;
		public MyMouseListener(String panelName){
			this.panelName = panelName;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			modeShow(panelName);
			c = fcuk;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if(panelName.equals("pic_Demo")){
				c = (ImageIcon) lblReadDataMode.getIcon();
				lblReadDataMode.setIcon(fcuk);
			}else if(panelName.equals("pic_Scan")){
				c = (ImageIcon) lblScanMode.getIcon();
				lblScanMode.setIcon(fcuk);
			}
			else if(panelName.equals("pic_Test")){
				c = (ImageIcon) lblTestMode.getIcon();
				lblTestMode.setIcon(fcuk);
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

			if(panelName.equals("pic_Demo")){
				lblReadDataMode.setIcon(c);
			}else if(panelName.equals("pic_Scan")){
				lblScanMode.setIcon(c);
			}
			else if(panelName.equals("pic_Test")){
				lblTestMode.setIcon(c);
			}
			
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	private void modeShow(String modeName){
		if(modeName.equals("pic_Demo")){
			if(lblReadDataMode.getIcon() != offFcuk){
				MainFrame.mainPanel.panel.readDataPanelShow();
				lblReadDataMode.setIcon(fcuk);
				currentMode = "pic_Demo";
				
				if(lblScanMode.getIcon() != offFcuk)
					lblScanMode.setIcon(noFcuk);
				if(lblTestMode.getIcon() != offFcuk)
					lblTestMode.setIcon(noFcuk);
			}
		}
		else if(modeName.equals("pic_Scan")){
			if(lblScanMode.getIcon() != offFcuk){
				MainFrame.mainPanel.panel.scanPanelShow();
				lblScanMode.setIcon(fcuk);
				currentMode = "pic_Scan";
				if(lblReadDataMode.getIcon() != offFcuk)
					lblReadDataMode.setIcon(noFcuk);
				if(lblScanMode.getIcon() != offFcuk)
					lblTestMode.setIcon(noFcuk);
			}
		}
		else if(modeName.equals("pic_Test")){
			if(lblTestMode.getIcon() != offFcuk){
				MainFrame.mainPanel.panel.testModePanelShow();
				lblTestMode.setIcon(fcuk);
				currentMode = "pic_Test";
				if(lblScanMode.getIcon() != offFcuk)
					lblScanMode.setIcon(noFcuk);
				if(lblReadDataMode.getIcon() != offFcuk)
					lblReadDataMode.setIcon(noFcuk);
			}
		}
	}
	@Override
	public void updateResource() {
		// TODO Auto-generated method stub
		lblReadDataMode.setText(BaseMessages.getString("MainForm.pic_Demo"));
		lblScanMode.setText(BaseMessages.getString("MainForm.pic_Scan"));
		lblTestMode.setText(BaseMessages.getString("MainForm.pic_Test"));
	}
	
	
	public void setButtonDisable(JLabel label,boolean enable){
		if(enable){
			label.setIcon(noFcuk);
		}else
			label.setIcon(offFcuk);
	}
}
