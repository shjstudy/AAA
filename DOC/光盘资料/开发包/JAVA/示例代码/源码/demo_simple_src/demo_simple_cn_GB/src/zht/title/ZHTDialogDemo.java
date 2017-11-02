/*
*
* @author zhangtao
*
* MSN & Mail: zht_dream@hotmail.com
*/
package zht.title;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class ZHTDialogDemo extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame frame = new JFrame("title");
		frame.setContentPane(new ZHTDialogDemo());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}

	ZHTTitleFrame frame;
	ZHTTitleDialog dialog;

	public ZHTDialogDemo() {
		initGUI();
	}

	private void initGUI() {
		JButton frameBtn = new JButton("Show Frame");
		frameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (frame == null) {
					initFrame();
				}
				frame.setVisible(true);
			}
		});
		this.add(frameBtn);

		JButton dialogBtn = new JButton("Show Dialog");
		dialogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dialog == null) {
					initDialog();
				}
				dialog.setVisible(true);
			}
		});
		this.add(dialogBtn);
	}

	private void initFrame() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(new JTree()));
		frame = new ZHTTitleFrame("Title Frame");
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("title.png"));
		frame.setIconImage(imageIcon.getImage());
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
	}

	private void initDialog() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(new JTree()));
		dialog = new ZHTTitleDialog();
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("title2.png"));
		dialog.getTitleComponent().setTextInCenter(true);
		dialog.setIconImage(imageIcon.getImage());
		dialog.setTitle("Title Dialog");
		dialog.setModal(true);
		dialog.setContentPane(panel);
		dialog.setSize(500, 400);
		dialog.setLocation(400, 200);
	}
}