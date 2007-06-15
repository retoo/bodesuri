package ui.spiel.brett.felder;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import ui.spiel.brett.Figur2d;
import ui.spiel.brett.FigurenManager;
import applikation.client.pd.Feld;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public abstract class Feld2d extends javax.swing.JLabel implements Observer {
	private Point position;
	private final Feld feld;
	protected Icon icon;
	private FigurenManager figurenManager;

	public Feld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, Icon icon, FigurenManager figurenManager) {
		super(icon);
		this.icon = icon;
		this.position = p;
		this.feld = feld;
		this.figurenManager = figurenManager;

		feld.addObserver(this);

		addMouseListener(mouseAdapter);
		update(null, null); /* FIXME: evtl. anders machen */
	}

	private void zeichne(Icon icon) {
		setIcon(icon);
		setBounds(getPosX(), getPosY(), icon.getIconWidth(), icon.getIconHeight());
		updateUI(); /* TODO: Was soll das? (-reto) */
	}

	public int getPointX() {
		return (int) this.position.getX();
	}

	public int getPointY() {
		return (int) this.position.getY();
	}

	public int getPosX() {
		int pos = (int) position.getX() - (this.icon.getIconWidth() / 2);
		return pos;
	}

	public int getPosY() {
		int pos = (int) position.getY() - (this.icon.getIconHeight() / 2);
		return pos;
	}

	public Feld getFeld() {
		return feld;
	}

	public void update(Observable os, Object arg) {
		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt()) {
			System.out.println("Feld " + this + " Feld: " + feld.getFigur());
			/* Figur drauf stellen */
			Figur2d figur = figurenManager.get(feld.getFigur());

			if (figur != null) {
				figur.setzeAuf(this);
			} else {
				/* ungenutzte figur */
			}
		} else {
			/* Figur entfernen */

			System.out.println("Feld " + this + "Keine Figur!");

			/* FIXME: muss glaubs nix machen */
		}

		Icon icon;
		/* prüfen wir ob selektiert */
		if (feld.getAusgewaehlt()) {
			System.out.println("Aktiviere...");
			icon = getAktivesIcon();
		} else {
			icon = getPassivesIcon();
		}

		zeichne(icon);
	}

	public abstract Icon getAktivesIcon();
	public abstract Icon getPassivesIcon();
}
