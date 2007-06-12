package ui.ressourcen;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import pd.karten.Joker;
import pd.karten.Karte;

final public class Icons {
	public static final Icon SPIELBRETT = ladeBild("/ui/ressourcen/spielbrett.png");
	public static final Icon FIGUR_BLAU = ladeBild("/ui/ressourcen/figur_gelb.png");
	public static final Icon FIGUR_BLAU_AUSWAHL = ladeBild("/ui/ressourcen/figur_schatten.png");
	public static final Icon FELD_NORMAL  = ladeBild("/ui/ressourcen/feld.png");
	public static final Icon FELD_BANK = ladeBild("/ui/ressourcen/feld_blau.png");
	public static final Icon FELD_AUSWAHL = ladeBild("/ui/ressourcen/feld_weiss.png");
	public static final Icon KARTEN_AUSWAHL = ladeBild("/ui/ressourcen/karten_auswahl.png");
	public static final Icon FILZ = ladeBild("/ui/ressourcen/filz.png");

	private static Map<String, Icon> kartenIcons = new HashMap<String, Icon>();
	
	/**
	 * Lädt das angegeben Icon.
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
	
	public static Icon getIcon(Karte karte) {
		String name;
		if (karte instanceof Joker) {
			name = "joker";
		} else {
			name = karte.getClass().getSimpleName() + "_" + karte.getKartenFarbe();
			name = name.toLowerCase();
		}
		
		Icon icon = kartenIcons.get(name);
		if (icon == null) {
			String pfad = "/ui/ressourcen/karten/" + name + ".png";
			URL url = Icons.class.getResource(pfad);
			if (url == null) {
				throw new RuntimeException("Bild " + pfad + " nicht gefunden.");
			}
			icon = new ImageIcon(url);
			kartenIcons.put(name, icon);
		}
		return icon;
	}
}
