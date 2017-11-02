package com.invengo.xcrf.ui.panel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class UCCtrl_TagInfo extends JPanel {
	private JLabel lblImage;

	JLabel lblEpc;

	JLabel lblTid;

	JLabel lblUserdata;

	JProgressBar progressBar;

	private String epc;

	private String tid;

	private int pr;

	private String userData;

	private String imagePath;

	public UCCtrl_TagInfo(String imagePath, String epc, String tid,
			String userData, int pr, JPanel p) {
		this();
		this.imagePath = imagePath;
		this.epc = epc;
		this.tid = tid;
		this.userData = userData;
		this.pr = pr;
		updateValue();
	}

	public UCCtrl_TagInfo() {
		setForeground(Color.WHITE);
		setLayout(null);

		lblImage = new JLabel("");
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StringBuffer msg = new StringBuffer();
				msg.append("EPC:       " + epc + "\n");
				msg.append("TID/ID:    " + tid + "\n");
				msg.append("UserData:  " + userData + "\n");
				JOptionPane.showMessageDialog(null, msg.toString(), "±êÇ©ÐÅÏ¢",
						JOptionPane.INFORMATION_MESSAGE, lblImage.getIcon());
			}
		});
		lblImage.setToolTipText("\u70B9\u51FB\u67E5\u770B\u8BE6\u7EC6");
		lblImage.setBounds(10, 10, 72, 64);
		// lblImage.setIcon(new ImageIcon(getClass().getResource(imagePath)));
		add(lblImage);

		lblEpc = new JLabel("EPC:");
		lblEpc.setBounds(104, 10, 344, 15);
		// lblEpc.setText(lblEpc.getText()+" "+epc);
		add(lblEpc);

		lblTid = new JLabel("TID:");
		lblTid.setBounds(104, 30, 344, 15);
		// lblTid.setText(lblTid.getText()+" "+tid);
		add(lblTid);

		lblUserdata = new JLabel("UserData:");
		lblUserdata.setBounds(104, 50, 344, 15);
		lblUserdata.setText(lblUserdata.getText() + "  " + userData);
		add(lblUserdata);

		progressBar = new JProgressBar();
		progressBar.setMaximum(0XFF);
		progressBar.setValue(11);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBackground(Color.WHITE);
		progressBar.setBounds(104, 75, 258, 19);
		// progressBar.setValue(pr);
		add(progressBar);
	}

	public void updateValue() {
		lblImage.setIcon(new ImageIcon(getClass().getResource(imagePath)));
		lblEpc.setText("EPC:  " + epc);
		lblTid.setText("TID:  " + tid);
		lblUserdata.setText("UserData:  " + userData);
		if (pr > 0XFF) {
			progressBar.setMaximum(0xFFFF);
		}
		progressBar.setValue(pr);
		this.updateUI();
	}

	public JLabel getLblImage() {
		return lblImage;
	}

	public void setLblImage(JLabel lblImage) {
		this.lblImage = lblImage;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getPr() {
		return pr;
	}

	public void setPr(int pr) {
		this.pr = pr;
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
