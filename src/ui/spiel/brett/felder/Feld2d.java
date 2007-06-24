package ui.spiel.brett.felder;


import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import ui.geteiltes.BLabel;
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
	private BLabel geist;
	private BLabel wegNormal;
	private Figur aktuelleFigur;

	public Feld2d(Icon icon, Feld2dKonfiguration konfig) {
		super(icon, konfig.position);
		this.icon = icon;
		this.position = konfig.position;
		this.feld = konfig.feld;
		this.hover = konfig.hover;

		this.figurenManager = konfig.figurenManager;
		this.ausgewaehlt = new BLabel(Icons.FELD_AUSWAHL);
		this.ausgewaehlt.setVisible(false);
		this.add(ausgewaehlt);

		this.geist = new BLabel(Icons.getSpielerGeist(konfig.farbeIch), -1, -1);
		this.geist.zentriereAuf(this.getRelativerMittelpunkt());
		this.geist.setVisible(false);
		this.add(geist);

		this.wegNormal = new BLabel(Icons.getSpielerHoverIcon(konfig.farbeIch));
		this.wegNormal.setVisible(false);
		add(wegNormal);

		feld.addObserver(this);

		addMouseListener(konfig.mouseAdapter);

		/* Das Feld ein erstes mal zeichnen*/
		zentriereAuf(position);

		positioniereFigur(false);
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
		positioniereFigur(true);

		/* prüfen wir ob hoern */
		if (feld.istHover()) {

			hover.setVisible(true);
			hover.zentriereAuf(this.position);
		} else {
			hover.setVisible(false);
		}

		ausgewaehlt.setVisible(feld.istAusgewaehlt());
		wegNormal.setVisible(feld.istWeg());
		geist.setVisible(feld.istGeist());
	}

	private void positioniereFigur(boolean animation) {
	    Figur figur = feld.getFigur();

		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt() && aktuelleFigur != figur) {

			/* Figur drauf stellen */
			Figur2d figur2d = figurenManager.get(figur);

			/*
			 * wenn nur ein oder zwei Spieler mitspielen können einige figuren
			 * null sein. Dann zeichnen wir einfach ni.
			 */
			if (figur2d != null) {
				if (animation)
					figur2d.bewegeNach(position);
				else
					figur2d.zentriereAuf(position);

				aktuelleFigur = figur;
			}
		}
    }

	public static class Feld2dKonfiguration {
		public final Point position;
		public final Feld feld;
		public final FeldMouseAdapter mouseAdapter;
		public final BLabel hover;
		public final SpielerFarbe farbeIch;
		public final FigurenManager figurenManager;

		public Feld2dKonfiguration(Point position, Feld feld,
		        FeldMouseAdapter mouseAdapter, BLabel hover,
		        SpielerFarbe farbeIch, FigurenManager figurenManager) {
			this.position = position;
			this.feld = feld;
			this.mouseAdapter = mouseAdapter;
			this.hover = hover;
			this.farbeIch = farbeIch;
			this.figurenManager = figurenManager;
		}
	}
}
