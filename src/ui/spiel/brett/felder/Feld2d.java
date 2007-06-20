package ui.spiel.brett.felder;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import ui.erweiterungen.BLabel;
import ui.ressourcen.Icons;
import applikation.client.pd.Feld;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public abstract class Feld2d extends BLabel implements Observer {
	private Point position;
	private final Feld feld;
	protected Icon icon;
	private FigurenManager figurenManager;
	private BLabel hover;
	private BLabel ausgewaehlt;

	public Feld2d(Point p, Feld feld, MouseListener mouseAdapter, Icon icon, BLabel hover, FigurenManager figurenManager) {
		super(icon, p);
		this.icon = icon;
		this.position = p;
		this.feld = feld;
		this.hover = hover;
		this.figurenManager = figurenManager;
		this.ausgewaehlt = new BLabel(Icons.FELD_AUSWAHL);
		this.ausgewaehlt.setVisible(false);
		this.add(ausgewaehlt);

		feld.addObserver(this);

		addMouseListener(mouseAdapter);

		/* Die Grafiken ein erstes mal generieren */

		zentriereAuf(position);

		update(null, null);
	}

	public Feld getFeld() {
		return feld;
	}

	/* TODO: Danilo: Zeichnen überarbeiten
	 * Das Zeichnen in ein drei-schichtiges Modell umwandeln
	 *  - Einbuchtung (Loch)
	 *  - Farbe
	 *  - Figur
	 *  - Selektion (hover) / Weg
	 *
	 *  Figur nur hinschieben falls sich wirklich was verändert hat.
	 *  -- reto und robin
	 */
	public void update(Observable os, Object arg) {
		System.out.println("fuu");
		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt()) {
			/* Figur drauf stellen */
			Figur2d figur = figurenManager.get(feld.getFigur());


			/* wenn nur ein oder zwei Spieler mitspielen können
			 * einige figuren null sein. Dann zeichnen wir einfach ni.
			 */
			if (figur != null) {
				figur.setzeAuf(position);
			}
		}

		/* prüfen wir ob selektiert */
		if (feld.getHover()) {
			hover.setVisible(true);
			hover.zentriereAuf(this.position);
		} else {
			hover.setVisible(false);
		}

		if (feld.getAusgewaehlt()) {
			System.out.println("ausgewählt");
			ausgewaehlt.setVisible(true);
		} else {
			ausgewaehlt.setVisible(false);
		}
	}
}
