package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;

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

		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		env.getAvailableFontFamilyNames();
		setFont(new Font("Arial", Font.BOLD, 11));
	}

	public void paint(Graphics g) {
		int posx = 0;
		Graphics2D g2d = (Graphics2D) g;
		Color color = Color.orange;
		for (int i = 0; i < 64; i++) {
			if(i > 15) color = Color.red;
			if(i > 31) color = Color.cyan;
			if(i > 47) color = Color.green;
			posx += 20;
			circle = new Ellipse2D.Double(posx, 50, 15, 15);
			g2d.setColor(color);
			g2d.fill(getCircle());
		}
		drawBigString(g2d);
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
