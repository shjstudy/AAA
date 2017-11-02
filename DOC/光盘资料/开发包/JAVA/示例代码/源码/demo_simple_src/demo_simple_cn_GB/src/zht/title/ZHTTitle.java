/*
 *
 * @author zhangtao
 *
 * MSN & Mail: zht_dream@hotmail.com
 */
package zht.title;

import invengo.javaapi.protocol.IRP1.PowerOff;
import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ZHTTitle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String TITLEORIGINALSIZE = "title_original_size";

	private String title;
	private Image icon;
	private Window window;
	private boolean textInCenter = false;
	private Color titleColor = Color.BLACK;
	private Color textColor = Color.white;
	private Font textFont = new Font("Dialog", Font.PLAIN | Font.BOLD, 12);
	private int xGap = 5;
	private int yGap = 5;

	private JLabel titleLabel = new JLabel();
	private JLabel iconLabel = new JLabel();
	private int titleHeight = 28;

	private Point pressPoint;

	private JButton closeButton;
	private JButton minButton;
	private Icon minIcon = new ImageIcon(this.getClass().getResource("min.png"));
	private Icon closeIcon = new ImageIcon(this.getClass().getResource(
			"close.png"));

	private boolean resizable = true;

	private boolean maxed;

	private Reader reader;
	
	public ZHTTitle(Window window) {
		this(window, null,null);
	}

	public ZHTTitle(final Window window, String title,Reader reader) {
		this.window = window;
		this.reader = reader;
		this.setTitle(title);
		installListener();

		titleLabel.setFont(textFont);
		titleLabel.setForeground(textColor);

		this.closeButton = createButton(closeIcon);
		this.minButton = createButton(minIcon);

		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == closeButton) {
					Toolkit.getDefaultToolkit().beep();
					int i = JOptionPane.showConfirmDialog(window,
							"确认关闭？", "系统信息",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (i == 0) {
						if (ZHTTitle.this.reader!=null&&ZHTTitle.this.reader.isConnected()) {
							ZHTTitle.this.reader.send(new PowerOff());
							ZHTTitle.this.reader.disConnect();
						}
						System.exit(0);
					}
				} else if (e.getSource() == minButton) {
					((JFrame) window).setExtendedState(JFrame.ICONIFIED);
				}
			}
		};
		this.closeButton.addActionListener(buttonListener);
		this.minButton.addActionListener(buttonListener);

		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 1));
		if (window instanceof JFrame) {
			add(this.minButton);
		}
		add(this.closeButton);

	}

	public JButton createButton(Icon icon) {
		final JButton button = new JButton(icon);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setContentAreaFilled(false);
		button.setBorderPainted(true);
		button.setFocusable(false);
		return button;
	}

	public boolean isMaxed() {
		return maxed;
	}

	private void installListener() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pressPoint = e.getPoint();
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				if (pressPoint != null && !isMaxed()) {
					Point point = window.getLocation();
					double xoffset = e.getX() - pressPoint.getX();
					double yoffset = e.getY() - pressPoint.getY();
					window.setLocation((int) (point.getX() + xoffset),
							(int) (point.getY() + yoffset));
				}
			}
		});
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		titleLabel.setText(title);
		this.repaint();
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image iconImage) {
		this.icon = iconImage;
		if (iconImage != null) {
			ImageIcon icon = new ImageIcon(iconImage);
			iconLabel.setIcon(icon);
			this.repaint();
		}
	}

	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		int h = titleHeight;
		int labelHeight = titleLabel.getPreferredSize().height;
		if (labelHeight > h) {
			h = labelHeight;
		}
		int iconHeight = iconLabel.getPreferredSize().height + yGap;
		if (h < iconHeight) {
			h = iconHeight;
		}
		return new Dimension(size.width, titleHeight);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(titleColor);
		Rectangle bounds = this.getBounds();
		g2d.fill(new Rectangle2D.Double(bounds.getX(), bounds.getY()
				+ bounds.getHeight() / 2, bounds.getWidth(),
				bounds.getHeight() / 2));
		g2d.setColor(Color.DARK_GRAY.brighter());
		g2d.fill(new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds
				.getWidth(), bounds.getHeight() / 2));
		int xOffset = xGap;
		if (this.icon != null) {
			Dimension imageSize = iconLabel.getPreferredSize();
			double y = bounds.height / 2 - imageSize.height / 2;
			SwingUtilities.paintComponent(g2d, iconLabel, this, xGap, (int) y,
					imageSize.width, imageSize.height);
			xOffset += imageSize.width;
		}
		if (getTitle() != null) {
			Dimension labelSize = titleLabel.getPreferredSize();
			double x = bounds.getX() + xOffset;
			if (textInCenter) {
				x = bounds.getWidth() / 2 - labelSize.width / 2.0;
			}
			double y = bounds.height / 2 - labelSize.height / 2;
			SwingUtilities.paintComponent(g2d, titleLabel, this, (int) x,
					(int) y, labelSize.width, labelSize.height);
		}
		g2d.dispose();

	}

	public Font getTextFont() {
		return textFont;
	}

	public void setTextFont(Font textFont) {
		this.textFont = textFont;
		titleLabel.setFont(this.textFont);
		this.repaint();
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
		titleLabel.setForeground(textColor);
		this.repaint();
	}

	public boolean isTextInCenter() {
		return textInCenter;
	}

	public void setTextInCenter(boolean textInCenter) {
		this.textInCenter = textInCenter;
		this.repaint();
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
}