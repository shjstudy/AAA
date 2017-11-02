/*
*
* @author zhangtao
*
* MSN & Mail: zht_dream@hotmail.com
*/
package zht.title;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class ZHTTitleFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZHTTitleFrame() throws HeadlessException {
		super();
		initFrame();
	}

	public ZHTTitleFrame(GraphicsConfiguration gc) {
		super(gc);
		initFrame();
	}

	public ZHTTitleFrame(String title) throws HeadlessException {
		super(title);
		initFrame();
	}

	public ZHTTitleFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		initFrame();
	}

	private ZHTTitle titleComponent;
	private JPanel contentPanel = new JPanel();
	private Container content = null;
	private Point pressPoint;

	public void setContentPane(Container contentPane) {
		if (contentPane == contentPanel) {
			super.setContentPane(contentPane);
		} else {
			if (content != null) {
				contentPanel.remove(content);
			}
			content = contentPane;
			contentPanel.add(content, BorderLayout.CENTER);
		}
	}

	public Container getContentPane() {
		return content;
	}

	public void setTitle(String title) {
		super.setTitle(title);
		if (titleComponent != null) {
			titleComponent.setTitle(title);
		}
	}

	public void setIconImage(Image image) {
		super.setIconImage(image);
		if (titleComponent != null) {
			titleComponent.setIcon(image);
		}
	}

	public void setUndecorated(boolean undecorated) {
		super.setUndecorated(true);
	}

	private void initFrame() {
		titleComponent = new ZHTTitle(this);
		contentPanel.setBorder(new ZHTTitleBorder());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(titleComponent, BorderLayout.NORTH);
		setContentPane(contentPanel);
		setTitle(getTitle());
		setIconImage(getIconImage());
		this.setUndecorated(true);
		this.setResizable(true);
		initResizeListener();
	}

	public void setResizable(boolean resizable) {
		super.setResizable(false);
		contentPanel.setCursor(Cursor.getDefaultCursor());
		titleComponent.setResizable(resizable);
	}

	private boolean isInLeft(Point p) {
		Rectangle rect = contentPanel.getBounds();
		if (p.getX() > rect.getX() && p.getX() < rect.getX() + 5) {
			return true;
		}
		return false;
	}

	private boolean isInRight(Point p) {
		Rectangle rect = contentPanel.getBounds();
		if (p.getX() < rect.getX() + rect.getWidth() && p.getX() > rect.getX() + rect.getWidth() - 5) {
			return true;
		}
		return false;
	}

	private boolean isInTop(Point p) {
		Rectangle rect = contentPanel.getBounds();
		if (p.getY() > rect.getY() && p.getY() < rect.getY() + 5) {
			return true;
		}
		return false;
	}

	private boolean isInBottom(Point p) {
		Rectangle rect = contentPanel.getBounds();
		if (p.getY() < rect.getY() + rect.getHeight() && p.getY() > rect.getY() + rect.getHeight() - 5) {
			return true;
		}
		return false;
	}

	private Cursor getResizeCursor(Point p) {
		boolean l = isInLeft(p);
		boolean r = isInRight(p);
		boolean t = isInTop(p);
		boolean b = isInBottom(p);
		if (l) {
			if (t) {
				return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
			} else if (b) {
				return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
			}
			return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
		} else if (r) {
			if (t) {
				return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
			} else if (b) {
				return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
			}
			return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
		} else if (b) {
			return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
		} else if (t) {
			return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
		}

		return Cursor.getDefaultCursor();
	}

	private void resizeFrame(Point dragPoint) {
		double minWidth = 100;
		int minHeight = titleComponent.getHeight() + 10;
		if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)) {
			int offset = dragPoint.y - pressPoint.y;
			int nW = this.getWidth();
			int nH = this.getHeight() - offset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			this.setLocation(this.getX(), this.getY() + offset);
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
			int offset = dragPoint.x - pressPoint.x;
			int nW = this.getWidth() + offset;
			int nH = this.getHeight();
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			pressPoint.x += offset;
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)) {
			int offset = dragPoint.y - pressPoint.y;
			int nW = this.getWidth();
			int nH = this.getHeight() + offset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			pressPoint.y += offset;
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)) {
			int offset = dragPoint.x - pressPoint.x;
			//			pressPoint.x += offset;
			int nW = this.getWidth() - offset;
			int nH = this.getHeight();
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			this.setBounds(this.getX() + offset, this.getY(), nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR)) {
			int xoffset = dragPoint.x - pressPoint.x;
			int yoffset = dragPoint.y - pressPoint.y;
			int nW = this.getWidth() + xoffset;
			int nH = this.getHeight() - yoffset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			pressPoint.x += xoffset;
			this.setLocation(this.getX(), this.getY() + yoffset);
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)) {
			int xoffset = dragPoint.x - pressPoint.x;
			int yoffset = dragPoint.y - pressPoint.y;

			int nW = this.getWidth() + xoffset;
			int nH = this.getHeight() + yoffset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}

			pressPoint.x += xoffset;
			pressPoint.y += yoffset;
			this.setLocation(this.getX(), this.getY());
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR)) {
			int xoffset = dragPoint.x - pressPoint.x;
			int yoffset = dragPoint.y - pressPoint.y;

			int nW = this.getWidth() - xoffset;
			int nH = this.getHeight() + yoffset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}

			pressPoint.y += yoffset;
			this.setLocation(this.getX() + xoffset, this.getY());
			this.setSize(nW, nH);
		} else if (contentPanel.getCursor() == Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)) {
			int xoffset = dragPoint.x - pressPoint.x;
			int yoffset = dragPoint.y - pressPoint.y;
			int nW = this.getWidth() - xoffset;
			int nH = this.getHeight() - yoffset;
			if (nH <= minHeight || nW < minWidth) {
				return;
			}
			this.setLocation(this.getX() + xoffset, this.getY() + yoffset);
			this.setSize(nW, nH);
		}
		this.validate();
	}

	private void initResizeListener() {
		contentPanel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				if (titleComponent.isMaxed() || !titleComponent.isResizable()) {
					return;
				}
				Point point = e.getPoint();
				Cursor cursor = getCursor();
				Cursor resizeCursor = getResizeCursor(point);
				if (cursor != resizeCursor) {
					contentPanel.setCursor(resizeCursor);
				}
			}

			public void mouseDragged(MouseEvent e) {
				if (titleComponent.isMaxed() || !titleComponent.isResizable()) {
					return;
				}
				resizeFrame(e.getPoint());
			};
		});
		contentPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pressPoint = e.getPoint();
			}

			public void mouseExited(MouseEvent e) {
				content.setCursor(Cursor.getDefaultCursor());
			}
		});
	}

	public ZHTTitle getTitleComponent() {
		return titleComponent;
	}

}