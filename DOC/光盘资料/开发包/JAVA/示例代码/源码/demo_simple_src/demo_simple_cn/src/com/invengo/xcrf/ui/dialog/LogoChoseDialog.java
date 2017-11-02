package com.invengo.xcrf.ui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.core.Const;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;

public class LogoChoseDialog extends JDialog implements LanguageChangeListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txfLogo;

	public LogoChoseDialog() {

		setBounds(100, 100, 391, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// 标题
		setTitle("Logo选择");

		JLabel lblLogo = new JLabel("图片路径:");
		lblLogo.setBounds(10, 20, 60, 24);
		contentPanel.add(lblLogo);

		txfLogo = new JTextField();
		txfLogo.setBounds(70, 20, 220, 24);
		contentPanel.add(txfLogo);

		JButton btnLogo = new JButton("打开");
		btnLogo.setBounds(300, 20, 80, 24);
		contentPanel.add(btnLogo);

		JButton btnOK = new JButton("设置");
		btnOK.setBounds(300, 50, 80, 24);
		contentPanel.add(btnOK);

		btnLogo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Images", "jpg", "gif", "png");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File("C:\\"));
				int result = chooser.showOpenDialog(null);
				String Path = null;
				if (result == JFileChooser.APPROVE_OPTION) {
					Path = chooser.getSelectedFile().getPath();
				}
				txfLogo.setText(Path);
			}

		});

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (txfLogo.getText().trim() != "") {

					File f = new File(txfLogo.getText().trim());
					if (f.exists()) {
						// Common.LogoPath = txfLogo.getText().trim();

						// 保存到XML
						saveLogoConfig();

						MessageDialog.showInfoMessage(contentPanel,
								BaseMessages.getString("", "Message.MSG_179"));
						LogoChoseDialog.this.setVisible(false);
					} else {
						MessageDialog.showInfoMessage(contentPanel,
								BaseMessages.getString("", "Message.MSG_178"));
					}
				}
			}

		});

		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	@Override
	public void updateResource() {
		// TODO Auto-generated method stub

	}

	private void saveLogoConfig() {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(Const.fn);
			Element tagTypeE = (Element) XPath.newInstance("//TagType")
					.selectSingleNode(doc);
			if (tagTypeE != null) {
				((Element) XPath.newInstance("//LogoPath//Path")
						.selectSingleNode(tagTypeE)).setText(String
						.valueOf(txfLogo.getText().trim()));
			}
			xmlOut.output(doc, new FileOutputStream(new File(Const.fn)));

		} catch (Exception e) {
			System.out.println("Path Not Find!");
		}
	}
}
