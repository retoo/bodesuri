package ui.ressourcen;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import pd.spieler.SpielerFarbe;
import applikation.client.pd.Karte;

final public class Icons {
	public static final Icon BRETT = ladeBild("/ui/ressourcen/brett.png");
	public static final Icon HINWEIS = ladeBild("/ui/ressourcen/hinweis_vertiefung.png");

	// Verbinden
	public static final Icon VERBINDEN = ladeBild("/ui/ressourcen/verbinden.png");
	public static final Icon BODESURI_START = ladeBild("/ui/ressourcen/bodesuri_starten.png");

	// Figuren
	public static final Icon FELD_NORMAL = ladeBild("/ui/ressourcen/feld.png");
	public static final Icon FELD_AUSWAHL = ladeBild("/ui/ressourcen/feld_weiss.png");
	public static final Icon FELD_HOVER = ladeBild("/ui/ressourcen/feld_hover.png");

	// Deck
	public static final Icon KARTEN_AUSWAHL = ladeBild("/ui/ressourcen/karten_auswahl.png");
	public static final Icon FILZ = ladeBild("/ui/ressourcen/filz.png");
	public static final Icon KARTEN_PLATZHALTER = ladeBild("/ui/ressourcen/karten/karte_platzhalter.png");

	private static Map<String, Icon> icons = new HashMap<String, Icon>();

	/**
	 * Lädt das angegeben Icon.
	 *
	 * @param pfad Pfad des zu ladenden Icons.
	 * @return icon
	 */
	private static Icon ladeBild(String pfad) {
		URL imageURL = Icons.class.getResource(pfad);

		if (imageURL != null) {
			return new ImageIcon(imageURL);
		} else {
			throw new RuntimeException("Unable to load icon " + pfad);
		}
	}

	public static Icon getIcon(Karte karte) {
		String name = "karten/" + karte.getIconName() + ".png";
		return getIcon(name);
	}
	
	public static Icon getSpielerFeldIcon(SpielerFarbe farbe) {
		String name = "feld_" + farbe.toString().toLowerCase() + ".png";
		return getIcon(name);
	}
	
	public static Icon getFigurIcon(SpielerFarbe farbe) {
		String name = "figur_" + farbe.toString().toLowerCase() + ".png";
		return getIcon(name);
	}
	
	public static Icon getSpielerIcon(SpielerFarbe farbe) {
		String name = "spieler_" + farbe.toString().toLowerCase() + ".png";
		return getIcon(name);
	}
	
	private static Icon getIcon(String name) {
		Icon icon = icons.get(name);
		if (icon == null) {
			URL url = Icons.class.getResource("/ui/ressourcen/" + name);
			if (url == null) {
				throw new RuntimeException("Bild " + name + " nicht gefunden.");
			}
			icon = new ImageIcon(url);
			icons.put(name, icon);
		}
		return icon;
	}
}
