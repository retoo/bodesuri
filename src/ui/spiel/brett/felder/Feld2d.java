package ui.spiel.brett.felder;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import ui.ressourcen.Icons;
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

	public Feld2d(Point p, Feld feld, MouseListener mouseAdapter, Icon icon, FigurenManager figurenManager) {
		super(icon);
		this.icon = icon;
		this.position = p;
		this.feld = feld;
		this.figurenManager = figurenManager;

		feld.addObserver(this);

		/* TODO: Wollen wir das nicht hier rausnehmen (-reto)
		 * könnte ja auch der ersteller von Feld2d machen */
		addMouseListener(mouseAdapter);

		/* Die Grafiken ein erstes mal generieren */
		update(null, null);
	}

	private void zeichne(Icon icon) {
		int posx = position.x - (icon.getIconWidth() / 2);
		int posy = position.y - (icon.getIconHeight() / 2);
		setIcon(icon);
		setBounds(posx, posy, icon.getIconWidth(), icon.getIconHeight());
	}

	public int getPointX() {
		return position.x;
	}

	public int getPointY() {
		return position.y;
	}

	public Feld getFeld() {
		return feld;
	}

	/* TODO: (von reto und robin)
	 * das Zeichenn in ein drei-schichtes modell umwandeln
	 *  - Einbuchtung
	 *  - Farbcodierung
	 *  - Figur
	 *  - Selektion (hover) / Weg
	 *
	 *  figur nur hinschieben falls sich wirklic hwas verändert hat.
	*
	 */
	public void update(Observable os, Object arg) {
		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt()) {
			/* Figur drauf stellen */
			Figur2d figur = figurenManager.get(feld.getFigur());


			/* wenn nur ein oder zwei Spieler mitspielen können
			 * einige figuren null sein. Dann zeichnen wir einfach ni.
			 */
			if (figur != null) {
				figur.setzeAuf(position.x, position.y);
			}
		}

		Icon icon;
		/* prüfen wir ob selektiert */
		if (feld.getAusgewaehlt()) {
			icon = getAktivesIcon();
		} else if (feld.getHover()) {
			icon = getHoverIcon();
		} else {
			icon = getPassivesIcon();
		}

		zeichne(icon);
	}

	public Icon getAktivesIcon() {
		return Icons.FELD_AUSWAHL;
	}

	public Icon getHoverIcon() {
		return Icons.FELD_HOVER;
	}

	public Icon getPassivesIcon() {
		return icon;
	}
}
