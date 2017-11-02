package com.invengo.xcrf.ui.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class sad extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			sad dialog = new sad();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel showingPanel;
	
	/**
	 * Create the dialog.
	 */
	public sad() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		
		
		
			NavigatorPanel panel = new NavigatorPanel(false);
			getContentPane().add(panel, BorderLayout.WEST);
			panel.setPreferredSize(new Dimension(105,800));
			panel.setBorder(new LineBorder(Color.CYAN));
			
			final JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new FlowLayout());
			
			for(Entry<JLabel,JPanel> entry : panel.getButtonMapings().entrySet())
			{
				JLabel jb = entry.getKey();
				final JPanel jp = entry.getValue();
				
//				jb.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						if(showingPanel != jp)
//						{
//							if(showingPanel!=null)
//								showingPanel.setVisible(false);
//								jp.setVisible(true);
//								showingPanel = jp;
//								mainPanel.updateUI();
//						}
//					}
//				});
				mainPanel.add(jp,BorderLayout.CENTER);
			}
			
			getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		
		
	}

}
