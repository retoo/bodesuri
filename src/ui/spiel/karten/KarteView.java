package ui.spiel.karten;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import pd.karten.Karte;
import ui.ressourcen.Icons;

/**
 * JLabel, dient zur Darstellung der einzelnen Karten, die im DeckView verwaltet
 * werden.
 */
public class KarteView extends JLabel {
	private Karte karte;
	private Point position;
	private MouseListener mouseListener;

	public KarteView(Point position, MouseListener mouseListener) {
		this.position = position;
		this.mouseListener = mouseListener;

		Dimension groesse = new Dimension(80, 100);

		setBounds(position.x, position.y, groesse.width, groesse.height);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
	}

	public void setKarte(Karte karte) {
		this.karte = karte;
		if (karte != null) {
			setIcon(Icons.getIcon(karte));
			addMouseListener(mouseListener);
		} else {
			removeMouseListener(mouseListener);
			setIcon(null);
		}
	}

	public Karte getKarte() {
		return karte;
	}

	public Point getPosition(){
		return position;
	}
}
