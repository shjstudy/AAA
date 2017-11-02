package com.invengo.xcrf.core.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * 一个完整的IP输入框实例，只有一个文件
 */
public class IPField extends JPanel {
	private static final Dimension fixedDimension = new Dimension(140, 23);

	private static final int fixedWidth = 140; // 组件的固定长度

	private static final int fixedHeight = 23; // 组件的固定宽度

	private String ipAddress;

	JTextIPSpace digitalText1 = new JTextIPSpace();

	JTextIPSpace digitalText2 = new JTextIPSpace();

	JTextIPSpace digitalText3 = new JTextIPSpace();

	JTextIPSpace digitalText4 = new JTextIPSpace();

	JLabel jLabel1 = new JLabel();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	Border border;

	public JTextField prevComponent;

	public JTextField nextComponent;

	public IPField() {
		try {
			jbInit(); // 初始化
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * <pre>
	 *    
	 *    Function:   jbInit   
	 *    Description:   初始化，由构造函数调用   
	 * </pre>
	 * 
	 * @param void
	 * @return void
	 * @exception Exception
	 */
	private void jbInit() throws Exception {
		border = BorderFactory.createEmptyBorder(); // 创建一个不占用空间的空边框（顶线、底线、左边框线和右边框线的宽度都为零）

		digitalText1.setPrevNextComponent(digitalText4, digitalText2);
		digitalText2.setPrevNextComponent(digitalText1, digitalText3);
		digitalText3.setPrevNextComponent(digitalText2, digitalText4);
		digitalText4.setPrevNextComponent(digitalText3, digitalText1);

		this.setLayout(null);
		this.setBorder(BorderFactory.createEtchedBorder()); // 创建一个具有“浮雕化”外观效果的边框，将组件的当前背景色用于高亮显示和阴影显示
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				this_componentResized(e);
			}
		});

		this.setSize(fixedWidth, fixedHeight);
		this.setMaximumSize(fixedDimension);
		this.setMinimumSize(fixedDimension);
		this.setPreferredSize(fixedDimension);

		// 设置第一个输入框属性
		digitalText1.setBorder(border);
		digitalText1.setHorizontalAlignment(SwingConstants.CENTER);
		digitalText1.setBounds(new Rectangle(2, 2, 30, 19)); // X坐标，Y坐标，宽，高

		// 设置第二个输入框属性
		digitalText2.setBounds(new Rectangle(37, 2, 30, 19));
		digitalText2.setBorder(border);
		digitalText2.setHorizontalAlignment(SwingConstants.CENTER);

		// 设置第三个输入框属性
		digitalText3.setBounds(new Rectangle(72, 2, 30, 19));
		digitalText3.setBorder(border);
		digitalText3.setHorizontalAlignment(SwingConstants.CENTER);

		// 设置第四个输入框属性
		digitalText4.setBounds(new Rectangle(107, 2, 30, 19));
		digitalText4.setBorder(border);
		digitalText4.setHorizontalAlignment(SwingConstants.CENTER);

		jLabel1.setBounds(new Rectangle(30, 2, 8, 19));
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 13)); // 设置"."的字体
		jLabel1.setOpaque(true);
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setText(".");

		jLabel2.setBounds(new Rectangle(65, 2, 8, 19));
		jLabel2.setText(".");
		jLabel2.setOpaque(true);
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel2.setFont(new java.awt.Font("Dialog", 0, 13));

		jLabel3.setBounds(new Rectangle(100, 2, 8, 19));
		jLabel3.setText(".");
		jLabel3.setOpaque(true);
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setFont(new java.awt.Font("Dialog", 0, 13));

		this.add(digitalText1);
		this.add(jLabel1);
		this.add(digitalText2);
		this.add(jLabel2);
		this.add(digitalText3);
		this.add(jLabel3);
		this.add(digitalText4);

	}

	@Override
	public void setEnabled(boolean valueSel) {
		if (valueSel == false) {
			digitalText1.setEnabled(false);
			digitalText2.setEnabled(false);
			digitalText3.setEnabled(false);
			digitalText4.setEnabled(false);
		} else {
			digitalText1.setEnabled(true);
			digitalText2.setEnabled(true);
			digitalText3.setEnabled(true);
			digitalText4.setEnabled(true);
		}
	}

	/**
	 * <pre>
	 *    
	 *    Function:   setIpAddress   
	 *    Description:   设置IP地址   
	 * </pre>
	 * 
	 * @param newIpAddress
	 * @return void
	 * @exception void
	 */
	public void setIpAddress(String newIpAddress) {

		// 以"/"为分割符
		StringTokenizer strToken = new StringTokenizer(newIpAddress, ".");
		String[] strIPAddress = new String[strToken.countTokens()];

		if (strToken.countTokens() != 4)
			return;

		// 分割IP地址串
		int k = 0;
		while (strToken.hasMoreTokens()) {
			strIPAddress[k] = strToken.nextToken();
			k++;
		}

		// 给数组赋值
		try {
			digitalText1.setText(strIPAddress[0]);
			digitalText2.setText(strIPAddress[1]);
			digitalText3.setText(strIPAddress[2]);
			digitalText4.setText(strIPAddress[3]);

			ipAddress = getIpAddress();
		} catch (Exception e) {
			return;
		}
	}

	public void setText(String newIpAddress) {

		// 以"/"为分割符
		StringTokenizer strToken = new StringTokenizer(newIpAddress, ".");
		String[] strIPAddress = new String[strToken.countTokens()];

		if (strToken.countTokens() != 4)
			return;

		// 分割IP地址串
		int k = 0;
		while (strToken.hasMoreTokens()) {
			strIPAddress[k] = strToken.nextToken();
			k++;
		}

		// 给数组赋值
		try {
			digitalText1.setText(strIPAddress[0]);
			digitalText2.setText(strIPAddress[1]);
			digitalText3.setText(strIPAddress[2]);
			digitalText4.setText(strIPAddress[3]);

			ipAddress = getIpAddress();
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * <pre>
	 *    
	 *    Function:   getIpAddress   
	 *    Description:   从4个输入框获取IP地址   
	 * </pre>
	 * 
	 * @param void
	 * @return void
	 * @exception void
	 */
	public String getIpAddress() {
		if (digitalText1.getText().length() != 0
				&& digitalText2.getText().length() != 0
				&& digitalText3.getText().length() != 0
				&& digitalText4.getText().length() != 0) {
			ipAddress = digitalText1.getText() + "." + digitalText2.getText()
					+ "." + digitalText3.getText() + "."
					+ digitalText4.getText();
			return ipAddress;
		} else {
			return null;
		}
	}

	public String getText() {
		ipAddress = digitalText1.getText() + "." + digitalText2.getText() + "."
				+ digitalText3.getText() + "." + digitalText4.getText();
		return ipAddress;
	}

	void this_componentResized(ComponentEvent e) {
		this.setSize(fixedWidth, fixedHeight);
		validate();
	}

	@Override
	public void setBounds(int l, int t, int w, int h) {
		super.setBounds(l, t, w, h);
		return;
	}

	@Override
	public void requestFocus() {
		digitalText1.requestFocus();
	}
	
	public void clear(){
		digitalText1.setText("");
		digitalText2.setText("");
		digitalText3.setText("");
		digitalText4.setText("");
	}

	class JTextIPSpace extends JTextField {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int minValue = 0;
		public int maxValue = 255;
		public JTextIPSpace textipspace;
		public boolean bFirstLostFocus = true;
		public boolean isCanFocus = false;
		public JTextField prevComponent;
		public JTextField nextComponent;

		public JTextIPSpace() {
			this(null, 255, true);
		}

		public JTextIPSpace(String str) {
			this(str, 255, true);
		}

		public JTextIPSpace(int maxValue, boolean isCanFocus) {
			this(null, maxValue, isCanFocus);
		}

		public JTextIPSpace(String str, int maxValue, boolean isCanFocus) {
			super(str);
			setNoEdge();
			setMidHorizontal();
			addKeySet();
//			setBackground(new Color(6710886));
			textipspace = this;
			this.maxValue = maxValue;
			this.isCanFocus = isCanFocus;
		}

		public void setNoEdge() {
			setBorder(null);
			setOpaque(true);
		}

		public void setPrevNextComponent(JTextField prev, JTextField next) {
			this.prevComponent = prev;
			this.nextComponent = next;
		}

		private void addKeySet() {
			KeyAdapter ka = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						int pos = getCaretPosition();
						if (pos == 0) {
							gotoComponent(prevComponent);
						}
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						if (prevComponent != null)
							prevComponent.selectAll();
						gotoComponent(prevComponent);
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						if (nextComponent != null)
							nextComponent.selectAll();
						gotoComponent(nextComponent);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						int pos = getCaretPosition();
						int maxPos = getText().trim().length();
						if (pos == maxPos || maxPos == 0) {
							gotoComponent(nextComponent);
						}
					} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						int pos = getCaretPosition();
						if (pos == 0) {
							gotoComponent(prevComponent);
						}
					} else if (e.getKeyChar() == '.') {
						int maxPos = getText().trim().length();
						if (maxPos > 0) {
							if (getSelectedText() == null) {
								if (nextComponent != null)
									nextComponent.selectAll();
								gotoComponent(nextComponent);
							}
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (Character.isDigit(e.getKeyChar())) {
						int pos = getCaretPosition();
						if (pos >= 3) {
							gotoComponent(nextComponent);
						}
					}
				}
			};
			addKeyListener(ka);
		}

		public void gotoComponent(JComponent jc) {
			if (jc != null)
				jc.requestFocus();
		}

		public void setMidHorizontal() {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		protected Document createDefaultModel() {
			return new DigitalTextDocument(this);
		}

		@Override
		public boolean isFocusTraversable() {
			return isCanFocus;
		}
	}

	class DigitalTextDocument extends PlainDocument {
		private JTextField JTF;

		public DigitalTextDocument(JTextField tmpJTF) {
			JTF = tmpJTF;
		}

		// 重载父类的insertString函数
		@Override
		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			int valueAfterInsert = 0;
			String strBeforeInsert = getText(0, getLength());
			String strAfterInsert = strBeforeInsert.substring(0, offset) + str
					+ strBeforeInsert.substring(offset);

			// 首先保证插入该字符串后，是整数；如果不是，则不进行插入操作
			try {
				valueAfterInsert = Integer.parseInt(strAfterInsert);
			} catch (NumberFormatException e) {

				return;
			}

			// 如果插入字符串str后，文档超长，则插入失败
			if (strAfterInsert.length() > 3)
				return;

			// 如果插入后数据超出范围，插入失败，弹出警告
			else if (valueAfterInsert > 255) {
				JTF.setText("255");
				JOptionPane.showConfirmDialog(null, valueAfterInsert
						+ "不是一个有效项目。请指定一个介于 0 和 255 之间的数值", "提示",
						JOptionPane.CLOSED_OPTION);
				JTF.setCaretPosition(0);
			} else {
				super.insertString(offset, str, a);
				return;
			}
		}
	}
}
