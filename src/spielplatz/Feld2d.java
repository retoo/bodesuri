package spielplatz;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

@Deprecated
public class Feld2d extends Ellipse2D {
	private int x;

	private int y;

	private int radius;

	private Color color;

	private int feldnr;

	private String spieler;

	public static final int BankFeld = 1;

	public Feld2d(int i, Color color) {
		this.x = i * 20 + 10;
		this.y = 50;
		this.radius = 15;
		this.color = color;
		this.feldnr = i;
		this.spieler = "";
	}

	public void zeichneFeld(Graphics2D g2d) {
		Ellipse2D.Double circle = new Ellipse2D.Double(x, y, radius, radius);
		g2d.setColor(color);
		g2d.fill(circle);
	}

	public void zeichneFigur(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.drawString(spieler, x + 5, y + 12);

	}

	public int getFeldNr() {
		return this.feldnr;
	}

	@Override
	public double getHeight() {
		return this.radius * 2;
	}

	@Override
	public double getWidth() {
		return this.radius * 2;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFrame(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub

	}

	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSpieler(int i) {
		this.spieler = String.valueOf(i);
	}

	public void setBankFeld() {
		this.color = Color.red;
	}
}
