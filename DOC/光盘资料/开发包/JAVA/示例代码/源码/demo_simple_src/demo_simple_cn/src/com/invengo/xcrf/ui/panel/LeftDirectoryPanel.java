package com.invengo.xcrf.ui.panel;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.invengo.xcrf.core.demo.DemoPanelRegistry;
import com.invengo.xcrf.core.event.IDemoModeChangeEvent;

public class LeftDirectoryPanel extends JPanel implements IDemoModeChangeEvent
{
	
	private final static int TREE_MODE = 0;
	private final static int SINGLE_MODE = 1;
	
	private int viewMode;
	
	private JComponent treeModePanel;
	private JComponent singleModePanel;
	
	
	public LeftDirectoryPanel(JFrame frame)
	{
		setLayout(new BorderLayout());
		viewMode = TREE_MODE;
		
		
		
		Box vBox = Box.createVerticalBox();
		
		treeModePanel = new LeftTreeModePanel(frame);
		singleModePanel = new LeftSingleModePanel();
		vBox.add(treeModePanel);
		vBox.add(singleModePanel);
		add(vBox,BorderLayout.CENTER);
		
		choicePanelVisible();
		
		
		this.setPreferredSize(new Dimension(200, 1024));
		setBorder(BorderFactory.createEtchedBorder());
		
		
		
		
		DemoPanelRegistry.registryDemoModePanel(this);
	}
	
	//ÇÐ»»Ä¿Â¼ÐÎÊ½
	public void changeViewMode()
	{
		viewMode  =  viewMode==TREE_MODE ? SINGLE_MODE : TREE_MODE;
		choicePanelVisible();
	}
	
	private void choicePanelVisible()
	{
		if(viewMode == TREE_MODE)
		{
			treeModePanel.setVisible(true);
			singleModePanel.setVisible(false);
		}else
		{
			treeModePanel.setVisible(false);
			singleModePanel.setVisible(true);
		}
		
	}


}
