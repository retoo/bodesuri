package ui.spiel.brett;

import javax.swing.Icon;
import javax.swing.JLabel;

import pd.spieler.Figur;
import ui.ressourcen.Icons;
import ui.spiel.brett.felder.Feld2d;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends JLabel {
	private Icon icon;

	public Figur2d(Figur figur, Icon icon) {
		super(icon);

		this.icon = icon;
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 *
	 * @param ziel
	 *            Zielfeld
	 */
	public void setzeAuf(Feld2d ziel) {
		setBounds(getPosX(ziel), getPosY(ziel), icon.getIconWidth(), icon
				.getIconHeight());
	}

	public int getPosX(Feld2d ziel) {
		return ziel.getPointX() - (icon.getIconWidth() / 2) + 2;
	}

	public int getPosY(Feld2d ziel) {
		return ziel.getPointY()
				- (icon.getIconHeight() - (Icons.FELD_NORMAL.getIconHeight() / 2))
				+ 7;
	}
}
