package ui.spiel.karten;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import applikation.client.pd.Karte;

import ui.ressourcen.Icons;

/**
 * JLabel, dient zur Darstellung der einzelnen Karten, die im DeckView verwaltet
 * werden.
 */
public class KarteView extends JLabel implements Observer {
	private Karte karte;
	private Point position;
	private MouseListener mouseListener;
	private KartenAuswahl kartenAuswahl;

	public KarteView(Point position, MouseListener mouseListener,
	                 KartenAuswahl kartenAuswahl) {
		this.position = position;
		this.mouseListener = mouseListener;
		this.kartenAuswahl = kartenAuswahl;

		Dimension groesse = new Dimension(80, 100);

		setBounds(position.x, position.y, groesse.width, groesse.height);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
	}

	public void update(Observable o, Object arg) {
		if (karte.istAusgewaehlt()) {
			kartenAuswahl.setPosition(position);
			kartenAuswahl.setVisible(true);
		} else {
			kartenAuswahl.setVisible(false);
		}
	}

	public void setKarte(Karte karte) {
		if (this.karte != null) {
			this.karte.deleteObserver(this);			
		}
		this.karte = karte;
		if (karte != null) {
			setIcon(Icons.getIcon(karte));
			addMouseListener(mouseListener);
			karte.addObserver(this);
		} else {
			removeMouseListener(mouseListener);
			setIcon(Icons.KARTEN_PLATZHALTER);
		}
	}

	public Karte getKarte() {
		return karte;
	}
}
