package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.ErrInfoList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.core.i18n.LanguageChoice;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.dialog.AboutDialog;
import com.invengo.xcrf.ui.dialog.TagConfigDialog;

public class SettingPanel extends JPanel implements LanguageChangeListener{

	private static final long serialVersionUID = -7949396684730560866L;
	JPopupMenu tableRightClickMenu;
	JPopupMenu localChoicePopupMenu;
	JPopupMenu themeChoicePopupMenu;
	JMenuItem codetypeBtn;
	JMenuItem tagSettingBtn;
	JMenu localBtn;
	JMenu themeBtn;
	JMenuItem helpBtn;
	JMenuItem voiceBtn;

	JMenuItem us;
	JMenuItem zh;
	
	JMenuItem content;
	JMenuItem about ;
	JMenuItem update;

	/**
	 * Create the panel.
	 */
	public SettingPanel() {
		setPreferredSize(new Dimension(100, 150));
		setLayout(null);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setOpaque(false);

		final JLabel label = new JLabel(new ImageIcon(MainFrame.class
				.getResource("image/toolStripSplitButton.png")));
		// label.setBounds(480, 120, 74, 39);
		// label.setBackground(Color.black);
		buttonPanel.add(label);

		buttonPanel.setBounds(25, 107, 50, 39);
		// buttonPanel.setBackground(Color.black);
		add(buttonPanel);

		tableRightClickMenu = new JPopupMenu();
		codetypeBtn = new JMenu("ASCII");
		tagSettingBtn = new JMenuItem(BaseMessages.getString("MainForm.MI_Tag"));
		localBtn = new JMenu(BaseMessages.getString("MainForm.MI_Language"));
		themeBtn = new JMenu(BaseMessages.getString("MainForm.MI_Theme"));
		helpBtn = new JMenu(BaseMessages.getString("MainForm.MI_Help"));
		voiceBtn = new JCheckBoxMenuItem(BaseMessages.getString("MainForm.MI_Beep"));
		voiceBtn.setSelected(Common.Console_Beep);
		voiceBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Common.Console_Beep = !Common.Console_Beep;
				voiceBtn.setSelected(Common.Console_Beep);
			}
		});

		final JMenuItem epcAscii = new JCheckBoxMenuItem("EPC");
		final JMenuItem userdataAscii = new JCheckBoxMenuItem("Userdata");

		epcAscii.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Common.EPC_ASCII = !Common.EPC_ASCII;
				epcAscii.setSelected(Common.EPC_ASCII);
			}
		});
		userdataAscii.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Common.UserData_ASCII = !Common.UserData_ASCII;
				userdataAscii.setSelected(Common.UserData_ASCII);
			}
		});

		codetypeBtn.add(epcAscii);
		codetypeBtn.add(userdataAscii);

		 content = new JMenuItem(BaseMessages.getString("MainForm.MI_Content"));
		 about = new JMenuItem(BaseMessages.getString("MainForm.MI_About"));
		 update = new JMenuItem(BaseMessages.getString("MainForm.MI_LevelUp"));

		final AboutDialog aboutDialog = new AboutDialog();

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(true);
			}

		});

		helpBtn.add(content);
		helpBtn.add(about);
		helpBtn.add(update);

		tableRightClickMenu.add(codetypeBtn);
		tableRightClickMenu.add(tagSettingBtn);
		tableRightClickMenu.add(localBtn);
		tableRightClickMenu.add(themeBtn);
		tableRightClickMenu.add(helpBtn);
		tableRightClickMenu.add(voiceBtn);

		tagSettingBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TagConfigDialog tagconfigPanel = new TagConfigDialog();
				tagconfigPanel.setLocationRelativeTo(null);
				tagconfigPanel.loadTagConfig();
				tagconfigPanel.setVisible(true);
			}

		});

		localChoicePopupMenu = new JPopupMenu();
		us = new JMenuItem("English");
		zh = new JMenuItem("ÖÐÎÄ");
		localBtn.add(us);
		localBtn.add(zh);

		us.addActionListener(new LanguageListener("en_us"));
		zh.addActionListener(new LanguageListener("zh_cn"));

		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				tableRightClickMenu.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				label.setOpaque(true);
				label.setBackground(Color.white);
				buttonPanel.setBackground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setOpaque(false);
				buttonPanel.setBackground(getBackground());
			}

		});
		themeChoicePopupMenu = new JPopupMenu();
		BaseMessages.addListener(this);
	}

	public class LanguageListener implements ActionListener {
		private String language;

		public LanguageListener(String language) {
			this.language = language;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LanguageChoice choice = LanguageChoice.getInstance();
			String currentLanguage = choice.getDefaultLocale().toString()
					.toLowerCase();
			if (currentLanguage.equals(language)) {
				return;
			}

			choice.setFailoverLocale(choice.getDefaultLocale());
			choice.setDefaultLocale(new Locale(language));
			choice.saveSettings();

			BaseMessages.getInstance().changeLang();
			if (language.equals("zh_cn")) {
				ErrInfoList.reset(2052);
			} else if (language.equals("en_us")) {
				ErrInfoList.reset(1033);
			}
		}
	}

	@Override
	public void updateResource() {
		// TODO Auto-generated method stub
		 content.setText(BaseMessages.getString("MainForm.MI_Content"));
		 about.setText(BaseMessages.getString("MainForm.MI_About"));
		 update.setText(BaseMessages.getString("MainForm.MI_LevelUp"));
		 
		tagSettingBtn.setText(BaseMessages.getString("MainForm.MI_Tag"));
		localBtn.setText(BaseMessages.getString("MainForm.MI_Language"));
		themeBtn.setText(BaseMessages.getString("MainForm.MI_Theme"));
		helpBtn.setText(BaseMessages.getString("MainForm.MI_Help"));
		voiceBtn.setText(BaseMessages.getString("MainForm.MI_Beep"));
	}

}
