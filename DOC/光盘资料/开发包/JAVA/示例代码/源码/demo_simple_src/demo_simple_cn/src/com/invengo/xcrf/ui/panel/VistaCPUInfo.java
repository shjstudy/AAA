package com.invengo.xcrf.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.invengo.xcrf.ui.MainFrame;

public class VistaCPUInfo extends JPanel {

	public float cpu;
	public float mem;

	// cpu和内存分别所占百分比
	public float percentOfCPU = 0;
	public float percentOfMemory = 0;

	// 是否第一次运行
	public boolean firstRun = true;

	public Timer timer;

	// 计算CPU针头的定时器
	public Timer timerCPU;

	// 计算内存针头的定时器
	public Timer timerMem;

	// 针头偏转的角度和当前所在的角度
	public float cpuAimAngle = 0;
	public float memAimAngle = 0;
	public float cpuCurAngle = 0;
	public float memCurAngle = 0;

	// 定义定体，绘制文本时用
	Font textFont = new Font("Arial", Font.BOLD, 12);

	// 时钟的位置距形
	private Rectangle positionRect;

	// 背景图片
	private ImageIcon Image = new ImageIcon(MainFrame.class
			.getResource("image/back_lrg.png"));

	// 大指针
	private ImageIcon ImageDial = new ImageIcon(MainFrame.class
			.getResource("image/dial_lrg.png"));
	// 小指针
	private ImageIcon ImageDialSmall = new ImageIcon(MainFrame.class
			.getResource("image/dial_lrg_sml.png"));
	// 玻璃
	private ImageIcon ImageGlass = new ImageIcon(MainFrame.class
			.getResource("image/dialdot_lrg.png"));
	// 盖板
	private ImageIcon ImageDialDot = new ImageIcon(MainFrame.class
			.getResource("image/glass_lrg.png"));

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		g.drawImage(Image.getImage(), 0, 0, width, height, this);

		Graphics2D g2 = (Graphics2D) g;

		// 指针
		AffineTransform at = g2.getTransform();
		g2.translate(positionRect.x + 62, positionRect.y + 85);
		g2.rotate(cpuCurAngle * Math.PI / 180);

		g2.drawImage(ImageDial.getImage(), -5, -49, 5, 62, this);
		g2.setTransform(at);

		g2.translate(positionRect.x + 148, positionRect.y + 94);
		g2.rotate(memCurAngle * Math.PI / 180);
		g2.drawImage(ImageDialSmall.getImage(), -5, -35, 10, 52, this);
		g2.setTransform(at);

		// 盖板
		g2.drawImage(ImageDialDot.getImage(), positionRect.x, positionRect.y,
				198, 150, this);

		// 文字
		g2.setColor(Color.white);
		g2.setFont(this.textFont);
		Rectangle rect = new Rectangle(positionRect.x + 43,
				positionRect.y + 95, 35, 15);
		// g2.drawString(((Integer)((int)percentOfCPU)).toString() + "p/s",
		// rect.x,rect.y);
		this.myDrawString(g2, rect, ((Integer) ((int) percentOfCPU)).toString()
				+ "p/s", new Color(1, 1, 0, 0), Color.white, "Arial", 9, true,
				false, false, (int) CENTER_ALIGNMENT, (int) CENTER_ALIGNMENT,
				false);
		rect = new Rectangle(positionRect.x + 130, positionRect.y + 98, 35, 13);
		// g2.drawString(((Integer)((int)percentOfMemory)).toString() + "p/s",
		// rect.x,rect.y);
		this.myDrawString(g2, rect, ((Integer) ((int) percentOfMemory))
				.toString()
				+ "p/s", new Color(1, 1, 0, 0), Color.white, "Arial", 9, true,
				false, false, (int) CENTER_ALIGNMENT, (int) CENTER_ALIGNMENT,
				false);

		// 玻璃
		g2.drawImage(ImageGlass.getImage(), positionRect.x, positionRect.y,
				198, 159, this);
	}

	// 加强版的Graphics的drawstring
	public void myDrawString(Graphics2D g, // Graphics2D
			Rectangle rc, // 矩形区域
			String s, // 绘制的string
			Color bkcolor, // 矩形的背影色
			Color textcolor, // 文字色
			String FontName, // 字体
			int FontSize, // 字体大小
			boolean FontBold, // 是否加粗
			boolean FontItalic, // 是否斜体
			boolean FontUnderline, // 是否下滑线
			int hAlign, // 居中方式(CENTER_ALIGNMENT,TOP_ALIGNMENT,BOTTOM_ALIGNMENT)
			int vAlign, // 同上
			boolean MultiLine) // 是否自动换行
	{
		if (s.trim().equals(" "))
			return;

		if (rc.width <= 0 || rc.height <= 0)
			return;

		// 背景颜色
		g.setColor(bkcolor);
		g.fillRect(rc.x, rc.y, rc.width, rc.height);
		g.setColor(textcolor); // 文字颜色

		LineBreakMeasurer lineMeasurer; // The LineBreakMeasurer used to
		// line-break the paragraph.
		int paragraphStart; // The index in the LineBreakMeasurer of the first
		// character in the paragraph.
		int paragraphEnd; // The index in the LineBreakMeasurer of the first
		// character after the end of the paragraph.

		Dimension size = rc.getSize();

		// 保存旧的剪裁区
		Rectangle oc = g.getClipBounds();
		// 设置新得剪裁区
		g.setClip(rc.x, rc.y, rc.width, rc.height);

		Hashtable map = new Hashtable();
		map.put(TextAttribute.FAMILY, FontName);
		map.put(TextAttribute.SIZE, new Float(FontSize));
		if (FontUnderline)
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		if (FontBold)
			map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		if (FontItalic)
			map.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);

		AttributedString ss = new AttributedString(s, map);
		AttributedCharacterIterator text = ss.getIterator();
		FontRenderContext frc = new FontRenderContext(g.getTransform(), false,
				false);

		paragraphStart = text.getBeginIndex();
		paragraphEnd = text.getEndIndex();

		// Create a new LineBreakMeasurer from the paragraph.
		lineMeasurer = new LineBreakMeasurer(text, frc);
		// Set formatting width to width of Component.

		float formatWidth = size.width;
		float drawPosY = rc.y;

		// 多行时，计算文本总高度
		if (MultiLine && vAlign != TOP_ALIGNMENT) {
			double gg = 0;
			lineMeasurer.setPosition(paragraphStart);
			while (lineMeasurer.getPosition() < paragraphEnd) {
				TextLayout layout = lineMeasurer.nextLayout(formatWidth);
				gg += layout.getDescent() + layout.getLeading()
						+ layout.getAscent();
			}

			if (gg < rc.height) {
				if (vAlign == CENTER_ALIGNMENT)
					drawPosY += (rc.height - gg) / 2;
				if (vAlign == BOTTOM_ALIGNMENT)
					drawPosY += (rc.height - gg);
			}

		}
		// Get lines from lineMeasurer until the entire
		// paragraph has been displayed.
		lineMeasurer.setPosition(paragraphStart);
		while (lineMeasurer.getPosition() < paragraphEnd) {
			// Retrieve next layout.
			TextLayout layout = lineMeasurer.nextLayout(formatWidth);
			// Move y-coordinate by the ascent of the layout.
			switch (vAlign) {
			case (int) BOTTOM_ALIGNMENT: // 底边
				if (!MultiLine) {
					drawPosY += (rc.height - layout.getDescent() - layout
							.getLeading());
					break;
				}
			case (int) CENTER_ALIGNMENT: // 居中
				if (!MultiLine) {
					drawPosY += layout.getAscent()
							+ (rc.height - layout.getAscent()
									- layout.getDescent() - layout.getLeading())
							/ 2;
					break;
				}
			default:
				drawPosY += layout.getAscent();
				break;
			}

			float drawPosX = rc.x;
			switch (hAlign) {
			case (int) RIGHT_ALIGNMENT: // 右对齐
				drawPosX = rc.x + formatWidth - layout.getAdvance();
				break;
			case (int) CENTER_ALIGNMENT: // 居中对齐
				drawPosX = rc.x + (formatWidth - layout.getAdvance()) / 2;
				break;
			default:
				drawPosX = rc.x;
				break;
			}

			layout.draw(g, drawPosX, drawPosY);
			// Move y-coordinate in preparation for next layout.
			drawPosY += layout.getDescent() + layout.getLeading();
			// 如果多行文本超出了显示区域，那么退出
			if (drawPosY >= (rc.y + rc.height))
				break;

			if (!MultiLine) // 如果不是多行，那么退出
				break;
		}
		// 恢复旧的裁减区域
		g.setClip(oc.x, oc.y, oc.width, oc.height);

	}

	private class timerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (firstRun || percentOfCPU != cpu) {
				percentOfCPU = cpu;
				float i = Math.round(percentOfCPU * 2.5 / 2) - 125f;
				timerCPU.stop();

				if ((i >= -360) && (i <= 360)) {
					cpuAimAngle = i;
				} else {
					cpuAimAngle = 0;
				}
				timerCPU.start();
			}
			if (firstRun || percentOfMemory != mem) {
				percentOfMemory = mem;
				timerMem.start();

				float i = Math.round(percentOfMemory * 2.5 / 2) - 125f;

				timerMem.stop();
				memAimAngle = i;
				timerMem.start();

			}

			if (firstRun)
				firstRun = false;
		}
	}

	private class timerMemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			float i;
			if (memCurAngle > memAimAngle) {
				float intRotateStep = Math.max(1, Math
						.round(Math.abs(Math.abs(memAimAngle)
								- Math.abs(memCurAngle)) / 10d));
				i = memCurAngle - intRotateStep;
				if (i <= memAimAngle) {
					timerMem.stop();
					memCurAngle = memAimAngle;
				} else
					memCurAngle = i;
			} else {
				float intRotateStep = Math.max(1, Math
						.round(Math.abs(Math.abs(memAimAngle)
								- Math.abs(memCurAngle)) / 10d));
				i = memCurAngle + intRotateStep;
				if (i >= memAimAngle) {
					timerMem.stop();
					memCurAngle = memAimAngle;
				} else {
					if ((i >= -360) && (i <= 360))
						memCurAngle = i;
				}
			}
			// this.Invalidate();
			repaint();
		}
	}

	private class timerCPUListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			float i;
			if (cpuCurAngle > cpuAimAngle) {
				float intRotateStep = Math.max(1, Math
						.round(Math.abs(Math.abs(cpuAimAngle)
								- Math.abs(cpuCurAngle)) / 10d));
				i = cpuCurAngle - intRotateStep;
				if (i <= cpuAimAngle) {
					timerCPU.stop();
					cpuCurAngle = cpuAimAngle;
				} else
					cpuCurAngle = i;
			} else {
				float intRotateStep = Math.max(1, Math
						.round(Math.abs(Math.abs(cpuAimAngle)
								- Math.abs(cpuCurAngle)) / 10d));
				i = cpuCurAngle + intRotateStep;
				if (i >= cpuAimAngle) {
					timerCPU.stop();
					cpuCurAngle = cpuAimAngle;
				} else {
					if ((i >= -360) && (i <= 360))
						cpuCurAngle = i;
				}
			}
			// this.Invalidate(positionRect);
			repaint();
		}

	}

	public VistaCPUInfo() {

		this.positionRect = new Rectangle(0, 0, 200, 150);

		this.timer = new Timer(100, new timerListener());
		this.timerCPU = new Timer(100, new timerCPUListener());
		this.timerMem = new Timer(100, new timerMemListener());

		this.setOpaque(false);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.cpu = 0;
		this.mem = 0;
		this.timer.start();
	}

}
