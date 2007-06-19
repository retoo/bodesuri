package ui.spiel.brett.felder;

import javax.swing.Icon;
import javax.swing.JLabel;

import pd.spieler.Figur;
import ui.ressourcen.Icons;

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
	 * @param x
	 * @param y
	 *
	 */
	public void setzeAuf(int x, int y) {
		int posX = x - (icon.getIconWidth() / 2) + 1;
		int posY = y - (icon.getIconHeight() - (Icons.FELD_NORMAL.getIconHeight() / 2)) - 3;
		setBounds(posX, posY, icon.getIconWidth(), icon
				.getIconHeight());
	}


}
