package com.invengo.xcrf.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 * This class is defined used to show a dialog like a window that show tips when this program is running
 * 
 * @author dp732
 *
 */
public class Messagedialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	private JLabel label;
	private Defaults defaults;
	private int x;//x-coordinate of the dialog
	private int y;//y-coordinate of the dialog
	private int w;//width of the dialog
	private int h;//height of the dialog

	/**
	 * Create the dialog.
	 */
	public Messagedialog(Defaults defaults) {
		//makes the dialog fade in and fade out
		setContentPane(new GlassBox());
		//makes the dialog don't have title bar
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setUndecorated(true);//disables decorations for this dialog
		setModal(true);//specifies this dialog should not be modal
		setResizable(false);//sets this dialog isn't resizable
		this.defaults = defaults;
		this.x = (this.defaults.appletX+this.defaults.appletW * 10 / 100);
		this.y = (this.defaults.appletY+this.defaults.appletH * 45 / 100);
		this.w = (this.defaults.appletW - (this.x * 2));
		this.h = (this.defaults.appletH - (this.y*13/8));
		setBounds(this.x, this.y, this.w, this.h);
		getContentPane().setLayout(null);
		contentPanel = new JPanel(){//creates a JPanel with background image
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				 try {
					   BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/image/background.jpg"));
					   g.drawImage(img, 0, 0, w, h,null);
					  } catch (IOException e) {
					   e.printStackTrace();
					  }
			}
		};
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBounds(0, 0, this.w, this.h);
		contentPanel.setLayout(null);
		label = new JLabel();
		label.setBounds(0, 0, this.w, this.h);
		Font font = new Font("Serif",0,15);
		label.setFont(font);
		label.setForeground(Color.white);
		label.setHorizontalAlignment(0);//makes the label's contents horizontal centre
		label.setVerticalAlignment(0);//makes the label's contents vertical centre
		contentPanel.add(label);
		
	}
	
	/**
	 * Shows tips
	 * 
	 * @param msg The tips that want to show
	 * @param time How long this dialog will show
	 */
	public void showMessage(String msg,final long time){
		label.setText(msg);
		Thread t = new Thread(){
			public void run(){
				try {
					sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dispose();
			}
		};
		t.start();
		this.setVisible(true);
	}
	
	public void showMessage(String msg){
		label.setText(msg);
		this.setVisible(true);
	}
	
	public void hideMessage(){
		dispose();
	}
}