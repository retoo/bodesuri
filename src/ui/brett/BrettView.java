/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	public BrettView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0,150,0));

		setLayout(null);
		Dimension grösse = new Dimension(600, 600);
		setPreferredSize(grösse);
		setMaximumSize(grösse);
		setMinimumSize(grösse);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel töggel = new JLabel(new ImageIcon("user.png"));
		töggel.setBounds(20, 20, 48, 48);
		töggel.addMouseListener(new MyMouseAdapter());
		this.add(töggel);
		JLabel töggel2 = new JLabel(new ImageIcon("user.png"));
		töggel2.setBounds(40, 20, 48, 48);
		töggel2.addMouseListener(new MyMouseAdapter());
		this.add(töggel2);
	}
	
	class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			System.out.println(e.getComponent().hashCode());
		}
	}
}
