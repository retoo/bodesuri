package ui.spiel.brett.felder;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import pd.spieler.SpielerFarbe;
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

	private BLabel wegNormal;

	/* TODO: Reto: Ein bissschen aufräumen hier. z.B. eine helferklasse machen
	 * die alle parameer beinhaltet die sowohl basis wie subklasse benötigen (-reto) */
	public Feld2d(Point p, Feld feld, MouseListener mouseAdapter,
	        Icon icon, BLabel hover, SpielerFarbe farbe,
	        FigurenManager figurenManager) {
		super(icon, p);
		this.icon = icon;
		this.position = p;
		this.feld = feld;
		this.hover = hover;

		this.figurenManager = figurenManager;
		this.ausgewaehlt = new BLabel(Icons.FELD_AUSWAHL);
		this.ausgewaehlt.setVisible(false);
		this.add(ausgewaehlt);

		this.wegNormal = new BLabel(Icons.getSpielerHoverIcon(farbe));
		this.wegNormal.setVisible(false);
		add(wegNormal);

		feld.addObserver(this);

		addMouseListener(mouseAdapter);

		/* Die Grafiken ein erstes mal generieren */

		zentriereAuf(position);

		update(null, null);
	}

	public Feld getFeld() {
		return feld;
	}

	/*
	 * TODO: Danilo: Zeichnen überarbeiten Das Zeichnen in ein drei-schichtiges
	 * Modell umwandeln - Einbuchtung (Loch) - Farbe - Figur - Selektion (hover) /
	 * Weg
	 *
	 * Figur nur hinschieben falls sich wirklich was verändert hat. -- reto und
	 * robin
	 */
	public void update(Observable os, Object arg) {
		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt()) {
			/* Figur drauf stellen */
			Figur2d figur = figurenManager.get(feld.getFigur());

			/*
			 * wenn nur ein oder zwei Spieler mitspielen können einige figuren
			 * null sein. Dann zeichnen wir einfach ni.
			 */
			if (figur != null) {
				figur.setzeAuf(position);
			}
		}

		/* prüfen wir ob hoern */
		if (feld.getHover()) {

			hover.setVisible(true);
			hover.zentriereAuf(this.position);
		} else {
			hover.setVisible(false);
		}

		/* Prüfen ob Selektiert */
		if (feld.getAusgewaehlt()) {
			ausgewaehlt.setVisible(true);
		} else {
			ausgewaehlt.setVisible(false);
		}

		if (feld.istWeg()) {
			wegNormal.setVisible(true);
		} else {
			wegNormal.setVisible(false);
		}
	}
}
