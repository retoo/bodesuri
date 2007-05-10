package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import spielplatz.Feld2d;

public class Prototyp extends JFrame {

	private static final long serialVersionUID = 1L;

	private Ellipse2D.Double circle;

	public Prototyp() {
		setTitle("Bodesuri Prototyp");
		setSize(1330, 100);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		//Klick endecken in J2D
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Hier müssste nun eine For-Schlaufe über alle Felder rein
				// for (Feld feld : felder)
				//    drin? = feld.contains(e.getPoint())
			}
		});

		GraphicsEnvironment env = GraphicsEnvironment
		                                             .getLocalGraphicsEnvironment();
		env.getAvailableFontFamilyNames();
		setFont(new Font("Arial", Font.BOLD, 11));
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < 64; i++) {
			Color color = Color.orange;
			if (i > 15)
				color = Color.red;
			if (i > 31)
				color = Color.cyan;
			if (i > 47)
				color = Color.green;

			Feld2d f = new Feld2d(i, g2d, color);
			f.zeichneFeld();
			f.zeichneFigur("0");
		}
		// drawBigString(g2d);
	}

	protected void drawBigString(Graphics2D g2d) {
		g2d.setPaint(Color.black);
		g2d.drawString("0", 25, 61);
		g2d.drawString("1", 345, 61);
		g2d.drawString("2", 665, 61);
		g2d.drawString("3", 985, 61);
	}

	public Ellipse2D.Double getCircle() {
		return circle;
	}

	public static void main(String[] args) {
		new Prototyp();
	}
}
