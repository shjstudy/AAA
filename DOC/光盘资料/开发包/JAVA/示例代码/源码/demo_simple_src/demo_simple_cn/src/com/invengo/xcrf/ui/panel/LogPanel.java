package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.ErrInfo;
import invengo.javaapi.handle.IApiExceptionHandle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.core.util.ForeasyUtil;
import com.invengo.xcrf.ui.tree.RootTree;

public class LogPanel extends JPanel implements IApiExceptionHandle , LanguageChangeListener{
	private static JTextPane logPane;

	private static Document bakContent;
	
	
	private JLabel runtimeInfo;
	private JButton button ;

	public JMenuItem bak;

	/**
	 * Create the panel.
	 */
	public LogPanel() {
		//setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(5,5));
		
		
		JPanel south = new JPanel();
		south.setOpaque(false);
		south.setPreferredSize(new Dimension(28, 5));
		//add(south,BorderLayout.SOUTH);
		
		JPanel content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new BorderLayout());
		content.setBorder(new LineBorder(Color.CYAN));
		content.setPreferredSize(new Dimension(450,100));
		add(content, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(100, 25));
		panel.setBackground(new Color(220,220,220));
		//content.add(panel, BorderLayout.NORTH);
		
		 runtimeInfo = new JLabel(BaseMessages.getString("MainForm.label6"));
		 runtimeInfo.setForeground(new Color(199,237,204));
		runtimeInfo.setHorizontalAlignment(SwingConstants.CENTER);
		runtimeInfo.setPreferredSize(new Dimension(150,25));
		//runtimeInfo.setBounds(27, 0, 131, 25);
		runtimeInfo.setOpaque(false);

		
		logPane = new JTextPane();
		logPane.setEditable(false);
		logPane.setBackground(new Color(199,237,204));
		
		
		JScrollPane panel_1 = new JScrollPane(logPane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// panel_1.setBounds(0, 14, 802, 86);
		panel_1.setPreferredSize(new Dimension(450,100));
		content.add(panel_1, BorderLayout.CENTER);

		final JPopupMenu tableRightClickMenu = new JPopupMenu();
		final JMenuItem copy = new JMenuItem(BaseMessages.getString("LogPanel.copy"));
		final JMenuItem clear = new JMenuItem(BaseMessages.getString("LogPanel.clear"));
		final JMenuItem selectall = new JMenuItem(BaseMessages.getString("LogPanel.selectall"));
		bak = new JMenuItem(BaseMessages.getString("LogPanel.back"));
		tableRightClickMenu.add(copy);
		tableRightClickMenu.add(clear);
		tableRightClickMenu.add(selectall);
		tableRightClickMenu.add(bak);

		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logPane.copy();
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bakContent = logPane.getDocument();
				Document doc = new DefaultStyledDocument();
				logPane.setDocument(doc);
			}
		});

		selectall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logPane.selectAll();
			}
		});

		bak.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logPane.setDocument(bakContent);
				bakContent = null;
			}
		});

		logPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					logPane.requestFocusInWindow();

					copy.setEnabled(ForeasyUtil.isNotNull(logPane
							.getSelectedText()));
					clear.setEnabled(ForeasyUtil.isNotNull(logPane.getText()));
					selectall.setEnabled(ForeasyUtil.isNotNull(logPane
							.getText()));
					bak.setEnabled(ForeasyUtil.isNotNull(bakContent));

					tableRightClickMenu.show(e.getComponent(), e.getX(),
							e.getY());
				}
			}
		});
		BaseMessages.addListener(this);
	}

	private static SimpleDateFormat s = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static SimpleAttributeSet attrSet = new SimpleAttributeSet();

	public static void info(String msg) {
		showMsg(msg, Color.BLACK);
	}

	public static void error(String msg) {
		showMsg(msg, Color.RED);
	}

	private static void showMsg(String msg, Color color) {
		if (ForeasyUtil.isEmptyString(msg))
			return;

		bakContent = null;

		msg = "[ " + s.format(new Date()) + " ]    " + msg;

		Document doc = logPane.getDocument();

		StyleConstants.setForeground(attrSet, color);
		try {
			doc.insertString(doc.getLength(), msg + "\n", attrSet);
			logPane.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
			// do nothing
		}

	}

	@Override
	public void apiExceptionHandle(ErrInfo e) {
		// TODO Auto-generated method stub
		error(e.getErrMsg());
		if (DemoRegistry.getDemoByName(e.getReaderName()) != null) {
			
			Demo demo = DemoRegistry.getDemoByName(e.getReaderName());

			if(demo !=null && demo.getConfig().getConnType() != null ){
				if(demo.getConfig().getConnType().equals("TCPIP_Client") && demo.isTryReconnNet() && !demo.getReader().isConnected() )
				{
					demo.scheduleReconnTask();
				}
			}
		}
		
		RootTree.getTree().updateUI();
	}

	@Override
	public void updateResource() {
		// TODO Auto-generated method stub
		runtimeInfo.setText(BaseMessages.getString("MainForm.label6"));
		button.setText(BaseMessages.getString("MainForm.btnClean"));
	}
}
