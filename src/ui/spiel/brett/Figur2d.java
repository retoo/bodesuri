package ui.spiel.brett;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;

import pd.spieler.Figur;
import ui.ressourcen.Icons;
import ui.spiel.brett.felder.Feld2d;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends JLabel implements Observer {
	private Figur figur;
	private BrettView brett;
	private Icon icon;

	public Figur2d(Figur figur, Icon icon, BrettView brett) {
		super(icon);
		this.figur = figur;
		this.brett = brett;
		this.icon = icon;
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		figur.addObserver(this);
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 * 
	 * @param ziel
	 *            Zielfeld
	 */
	public void setzeAuf(Feld2d ziel) {
		setBounds(getPosX(ziel), getPosY(ziel), icon.getIconWidth(), icon.getIconHeight());
	}

	public void update(Observable o, Object arg) {
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		// brett.getFeld2d(figur.getFeld()).setAusgewaehlt(false);
	}

	public int getPosX(Feld2d ziel) {
		return ziel.getPointX() - (icon.getIconWidth() / 2) + 2;
	}

	public int getPosY(Feld2d ziel) {
		return ziel.getPointY()
				- (icon.getIconHeight() - (Icons.FELD_NORMAL
						.getIconHeight() / 2)) + 7;
	}
}
