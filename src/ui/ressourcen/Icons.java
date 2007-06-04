package ui.ressourcen;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

final public class Icons {
	public static final Icon SPIELBRETT = ladeBild("/ui/ressourcen/wasser/spielbrett.png");
	public static final Icon FIGUR_BLAU = ladeBild("/ui/ressourcen/figur.png");
	public static final Icon FIGUR_BLAU_AUSWAHL = ladeBild("/ui/ressourcen/figur_schatten.png");
	public static final Icon FELD_NORMAL  = ladeBild("/ui/ressourcen/feld.png");
	public static final Icon FELD_BANK = ladeBild("/ui/ressourcen/bankfeld.png");
	public static final Icon KARTEN_AUSWAHL = ladeBild("/ui/ressourcen/karten_auswahl.png");

	/**
	 * Ladet die oben definierten Icons.
	 * 
	 * @param pfad Pfad des zu ladenden Icons.
	 * @return icon
	 */
	private static Icon ladeBild(String pfad) {
		URL imageURL = BrettLader.class.getResource(pfad);

		if (imageURL != null) {
			return new ImageIcon(imageURL);
		} else {
			throw new RuntimeException("Unable to load icon " + pfad);
		}
	}
}
