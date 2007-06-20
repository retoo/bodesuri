package ui.spiel.brett;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.ressourcen.Icons;
import ui.spiel.karten.KartenAuswahlView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SpielView extends JPanel {
	private Paint paint;
	
	public SpielView(Steuerung steuerung, Spiel spiel) {
		// Layout setzen
		setLayout(new GridBagLayout());
		setOpaque(false);

		paint = erstellePaint();

		// Views
		BrettView brettView = new BrettView(steuerung, spiel);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(steuerung, spiel);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(brettView, c);
		add(kartenAuswahlView, c);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(paint);
		g2.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	private Paint erstellePaint() {
		Image filz = ((ImageIcon) Icons.FILZ).getImage();
		int width = filz.getWidth(null);
		int height = filz.getHeight(null);

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(filz, 0, 0, null);
		bg.dispose();

		Rectangle2D tr = new Rectangle2D.Double(0, 0, width, height);
		return new TexturePaint(bi, tr);
	}
}
