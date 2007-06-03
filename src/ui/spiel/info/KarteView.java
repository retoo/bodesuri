/**
 * @(#) KarteView.java
 */

package ui.spiel.info;

import java.awt.Dimension;
import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pd.karten.Karte;

/**
 * JLabel, dient zur Darstellung der einzelnen Karten, die im DeckView verwaltet
 * werden.
 */
public class KarteView extends JLabel {
	final Karte karte;
	private Point point;
	private String pfad;
	private boolean gedrueckt = false;

	public KarteView(Point p, Karte karte, KarteMouseAdapter mouseAdapter, DeckView view) {
		this.karte = karte;
		this.point = p;

		pfad = "/ui/ressourcen/" + karte.getName() + "_"
		              + karte.getKartenFarbe() + ".png";
		System.out.println(pfad);
		URL resource1 = KarteView.class.getResource(pfad);
		final Icon bildFigur = new ImageIcon(resource1);
		setIcon(bildFigur);

		setBounds((int) p.getX(), (int) p.getY(), bildFigur.getIconWidth(),
		          bildFigur.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
		addMouseListener(mouseAdapter);
	}

	public Point getKoordinaten(){
		return this.point;
	}

	public void setPfad(String pfad){
		this.pfad = pfad;
	}

	public void setGedrueckt(){
		this.gedrueckt = true;
	}

	public boolean getGedrueckt(){
		return this.gedrueckt;
	}
}
