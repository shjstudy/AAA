package com.invengo.xcrf.demo;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * Container for fade in,fade out displayer component.
 * 
 * @author dp732
 */
public class GlassBox extends JComponent implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// defines Constants
	private static final int ANIMATION_FRAMES = 10;
	private static final int ANIMATION_INTERVAL = 10;
	// defines frame Index
	private int frameIndex;
	// defines timer
	private Timer timer;

	// creates a new instance of GlassBox
	public GlassBox() {
	}

	public void paint(Graphics g) {
		if (isAnimating()) {
			// component for displaying current transparency based on frame index
			float alpha = (float) frameIndex / (float) ANIMATION_FRAMES;
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, alpha));
			// renderer mechanism
			super.paint(g2d);
		} else {
			// if it’s the first time，initiates animation timer
			frameIndex = 0;
			timer = new Timer(ANIMATION_INTERVAL, this);
			timer.start();
		}
	}

	/**
	 * Gets the status of animation.
	 * 
	 * @return status
	 */
	private boolean isAnimating() {
		return timer != null && timer.isRunning();
	}

	/**
	 * Closes timer,resets timer
	 */
	private void closeTimer() {
		if (isAnimating()) {
			timer.stop();
			frameIndex = 0;
			timer = null;
		}
	}

	/*
	 * Animation timer event
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// one frame forwards
		frameIndex++;
		if (frameIndex >= ANIMATION_FRAMES) {
			//stops animation after last frame.
			closeTimer();
		} else {//renews current frame
			repaint();
		}
	}
}