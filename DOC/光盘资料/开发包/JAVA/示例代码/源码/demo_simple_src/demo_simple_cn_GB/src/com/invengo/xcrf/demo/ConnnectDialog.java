package com.invengo.xcrf.demo;

import gnu.io.CommPortIdentifier;
import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import zht.title.ZHTTitle;
import zht.title.ZHTTitleBorder;

import com.invengo.xcrf.view.IPField;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class ConnnectDialog extends JFrame {

	private static final long serialVersionUID = -8911693705403384911L;

	private JPanel contentPane;

	private JRadioButton tcpRadioButton;
	private JLabel ipLabel;
	private IPField ipField;
	private JLabel portLabel;
	private JSpinner.NumberEditor portEditor;
	private JSpinner portSpinner;

	private JRadioButton commRadioButton;
	private JLabel commPortLabel;
	private JComboBox commBox;
	private JLabel baundRateLabel;
	private JComboBox baundRataBox;

	private JButton connectButton;

	static ConnnectDialog connnectDialog;

	private Properties properties = new Properties();
	private String path;

	InputStream input;
	OutputStream out;

	private Defaults defaults;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					connnectDialog = new ConnnectDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConnnectDialog() {
		NimRODTheme nt = new NimRODTheme("NimRODThemeFile.theme");
		NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
		try {
			UIManager.setLookAndFeel(NimRODLF);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();

		}
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 380, 250);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(new ZHTTitleBorder());
		setLocationRelativeTo(null);

		ZHTTitle title = new ZHTTitle(this, "请选择连接方式", null);
		contentPane.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 380, 250);
		contentPane.add(panel);
		panel.setLayout(null);

		tcpRadioButton = new JRadioButton("TCP");
		tcpRadioButton.setSelected(true);
		tcpRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tcpRadioButton.isSelected()) {
					enableTcp(true);
					enableComm(false);
				}
			}

		});
		tcpRadioButton.setBounds(27, 32, 66, 23);
		panel.add(tcpRadioButton);

		ipLabel = new JLabel("读写器IP:");
		ipLabel.setBounds(37, 61, 56, 15);
		panel.add(ipLabel);

		ipField = new IPField();
		ipField.setLocation(101, 57);
		panel.add(ipField);

		portLabel = new JLabel("端口:");
		portLabel.setBounds(275, 61, 54, 15);
		panel.add(portLabel);

		SpinnerNumberModel portModel = new SpinnerNumberModel(7086, 0, 65535, 1);
		portSpinner = new JSpinner(portModel);
		portEditor = new JSpinner.NumberEditor(portSpinner, "#####");
		portEditor.getTextField().getActionMap().put(
				portEditor.getTextField().getInputMap().get(KeyStroke.getKeyStroke("ctrl V")),
				new AbstractAction() {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {

					}
				});
		portEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(
						String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}

		});
		portEditor.getTextField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent arg0) {
				int portInt = 0;
				try {
					portInt = Integer.parseInt(portEditor.getTextField()
							.getText());
				} catch (Exception e) {
					portEditor.getTextField().setText("7086");
				}
				if (portInt > 65535) {
					portEditor.getTextField().setText("7086");
				}
			}

		});
		portSpinner.setEditor(portEditor);
		portSpinner.setBounds(311, 58, 54, 22);
		panel.add(portSpinner);

		try {
			String userdir = System.getProperty("java.io.tmpdir");
			path = userdir + "\\tcp.properties";
			path = path.replaceAll("%20", " ");
			File file = new File(path);
			if (file.exists()) {
				input = new FileInputStream(file);
				properties.load(input);
				String ip = properties.getProperty("ip");
				String port = properties.getProperty("port");
				ipField.setIpAddress(ip);
				portEditor.getTextField().setText(port);
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		commRadioButton = new JRadioButton("串口");
		commRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (commRadioButton.isSelected()) {
					enableTcp(false);
					enableComm(true);
				}
			}

		});
		commRadioButton.setBounds(27, 119, 66, 23);
		panel.add(commRadioButton);

		commPortLabel = new JLabel("串口号:");
		commPortLabel.setEnabled(false);
		commPortLabel.setBounds(37, 152, 54, 15);
		panel.add(commPortLabel);

		String[] comm = getCommPorts();
		commBox = new JComboBox(comm);
		commBox.setEnabled(false);
		commBox.setBounds(84, 148, 54, 21);
		panel.add(commBox);

		baundRateLabel = new JLabel("波特率:");
		baundRateLabel.setEnabled(false);
		baundRateLabel.setBounds(158, 152, 54, 15);
		panel.add(baundRateLabel);

		baundRataBox = new JComboBox();
		baundRataBox.setEnabled(false);
		baundRataBox.addItem(115200);
		baundRataBox.addItem(230400);
		baundRataBox.setBounds(206, 149, 83, 21);
		panel.add(baundRataBox);

		connectButton = new JButton("连接");
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String connectString = null;
				Reader reader = null;
				if (tcpRadioButton.isSelected()) {
					if (ipField.getIpAddress() == null) {
						JOptionPane.showMessageDialog(contentPane, "IP不能为空，请重试！");
						return;
					}
					connectString = ipField.getIpAddress() + ":" + portEditor.getTextField().getText();
					reader = new Reader("Reader", "TCPIP_Client", connectString);
				}
				if (commRadioButton.isSelected()) {
					connectString = commBox.getSelectedItem() + "," + baundRataBox.getSelectedItem();
					reader = new Reader("Reader", "RS232", connectString);
				}
				defaults = Defaults.getInstance(reader);
				if (tcpRadioButton.isSelected()) {
					defaults.conType = 0;
				} else if (commRadioButton.isSelected()) {
					defaults.conType = 1;
				}
				if (reader.connect()) {
					if (tcpRadioButton.isSelected()) {
						properties.put("ip", ipField.getIpAddress());
						properties.put("port", portEditor.getTextField().getText());
						try {
							out = new FileOutputStream(new File(path));
							properties.store(out, "ip");
							properties.store(out, "port");
							if (input != null) {
								input.close();
							}
							out.close();
						} catch (FileNotFoundException e2) {
							e2.printStackTrace();
						} catch (IOException mye) {
							mye.printStackTrace();
						} finally {
							try {
								if (input != null) {
									input.close();
								}
								out.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					defaults.isConnected = true;
					defaults.ip = ipField.getIpAddress();
					connnectDialog.setVisible(false);
					new MainFrame(defaults, reader);
				}else {
					if (tcpRadioButton.isSelected()) {
						JOptionPane.showMessageDialog(connnectDialog, "连接失败，请检查是否因为网络连接保护！");
					} else {
						JOptionPane.showMessageDialog(connnectDialog, "连接失败，请重试！");
					}
					defaults.isConnected = false;
				}
			}

		});
		connectButton.setBounds(295, 183, 70, 23);
		panel.add(connectButton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(tcpRadioButton);
		buttonGroup.add(commRadioButton);

		setVisible(true);
	}

	private void enableTcp(boolean flag) {
		ipLabel.setEnabled(flag);
		ipField.setEnabled(flag);
		portLabel.setEnabled(flag);
		portSpinner.setEnabled(flag);
	}

	private void enableComm(boolean flag) {
		commPortLabel.setEnabled(flag);
		commBox.setEnabled(flag);
		baundRateLabel.setEnabled(flag);
		baundRataBox.setEnabled(flag);
	}

	public final static String[] getCommPorts() {
		Enumeration<?> list = CommPortIdentifier.getPortIdentifiers();
		List<String> commList = new ArrayList<String>();
		while (list.hasMoreElements()) {
			CommPortIdentifier myport = (CommPortIdentifier) list.nextElement();
			int type = myport.getPortType();
			String port = myport.getName();
			if (type == 1) {
				commList.add(port);
			}
		}
		String[] myports = new String[commList.size()];
		commList.toArray(myports);
		return myports;
	}
}
