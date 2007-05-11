package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class BrettPrototyp extends JFrame {

	private static final long serialVersionUID = 1L;

	public BrettPrototyp(String title) {
		setTitle(title);
		setSize(1330, 100);
		setVisible(true);
		setLayout(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}
}
