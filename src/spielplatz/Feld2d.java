package spielplatz;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Feld2d {
	private int x;
	private int y;
	private int radius;
	private Graphics2D g;
	private Color color;

	public Feld2d(int i, Graphics2D g2d, Color color){
		this.x = i * 20;
		this.y = 50;
		this.radius = 15;
		this.g = g2d;
		this.color = color;
	}
	
	public void zeichneFeld(){
		Ellipse2D.Double circle = new Ellipse2D.Double(x, y, radius, radius);
		g.setColor(color);
		g.fill(circle);
	}

	public void zeichneFigur(String string) {
		g.setColor(Color.black);
		g.drawString(string, x+5, y+12);
		
	}
}
