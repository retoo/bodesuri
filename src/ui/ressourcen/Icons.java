package ui.ressourcen;

import java.net.URL;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import pd.spieler.SpielerFarbe;

import applikation.client.pd.Karte;

final public class Icons {
	public static final Icon SPIELBRETT = ladeBild("/ui/ressourcen/spielbrett.png");
	public static final Icon HINWEIS = ladeBild("/ui/ressourcen/hinweis_vertiefung.png");

	// Verbinden
	public static final Icon VERBINDEN = ladeBild("/ui/ressourcen/verbinden.png");
	public static final Icon BODESURI_START = ladeBild("/ui/ressourcen/bodesuri_starten.png");

	// Felder
	public static final Icon FIGUR_BLAU = ladeBild("/ui/ressourcen/figur_blau.png");
	public static final Icon FIGUR_GRUEN = ladeBild("/ui/ressourcen/figur_gruen.png");
	public static final Icon FIGUR_ROT = ladeBild("/ui/ressourcen/figur_rot.png");
	public static final Icon FIGUR_GELB = ladeBild("/ui/ressourcen/figur_gelb.png");

	// Figuren
	public static final Icon FELD_BLAU = ladeBild("/ui/ressourcen/feld_blau.png");
	public static final Icon FELD_GRUEN = ladeBild("/ui/ressourcen/feld_gruen.png");
	public static final Icon FELD_ROT = ladeBild("/ui/ressourcen/feld_rot.png");
	public static final Icon FELD_GELB = ladeBild("/ui/ressourcen/feld_gelb.png");
	public static final Icon FELD_NORMAL = ladeBild("/ui/ressourcen/feld.png");
	public static final Icon FELD_AUSWAHL = ladeBild("/ui/ressourcen/feld_weiss.png");
	public static final Icon FELD_HOVER = ladeBild("/ui/ressourcen/feld_hover.png");

	// Spielerfarbe
	public static final Icon SPIELER_BLAU = ladeBild("/ui/ressourcen/spieler_blau.png");
	public static final Icon SPIELER_GRUEN = ladeBild("/ui/ressourcen/spieler_gruen.png");
	public static final Icon SPIELER_ROT = ladeBild("/ui/ressourcen/spieler_rot.png");
	public static final Icon SPIELER_GELB = ladeBild("/ui/ressourcen/spieler_gelb.png");

	// Deck
	public static final Icon KARTEN_AUSWAHL = ladeBild("/ui/ressourcen/karten_auswahl.png");
	public static final Icon FILZ = ladeBild("/ui/ressourcen/filz.png");
	public static final Icon KARTEN_PLATZHALTER = ladeBild("/ui/ressourcen/karten/karte_platzhalter.png");

	private static Map<String, Icon> kartenIcons = new HashMap<String, Icon>();

	public static final IdentityHashMap<SpielerFarbe, Icon> farbeFeldMap = new IdentityHashMap<SpielerFarbe, Icon>();

	static {
		farbeFeldMap.put(SpielerFarbe.values()[0], Icons.FELD_ROT);
		farbeFeldMap.put(SpielerFarbe.values()[1], Icons.FELD_GRUEN);
		farbeFeldMap.put(SpielerFarbe.values()[2], Icons.FELD_BLAU);
		farbeFeldMap.put(SpielerFarbe.values()[3], Icons.FELD_GELB);
	}

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
		String name = karte.getIconName();

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
