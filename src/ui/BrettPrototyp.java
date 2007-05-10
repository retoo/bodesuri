package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;

import spielplatz.Feld2d;

public class BrettPrototyp extends JFrame {

	private static final long serialVersionUID = 1L;

	public Vector<Feld2d> felder;

	public BrettPrototyp(String title, Vector<Feld2d> felder) {
		setTitle(title);
		setSize(1330, 100);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		env.getAvailableFontFamilyNames();
		setFont(new Font("Arial", Font.BOLD, 11));

		this.felder = felder;
		System.out.println("Size: " + felder.size());
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < this.felder.size(); i++) {
			if (felder.get(i) != null) {
				felder.get(i).zeichneFeld(g2d);
				felder.get(i).zeichneFigur(g2d);
			}
		}
	}

}
