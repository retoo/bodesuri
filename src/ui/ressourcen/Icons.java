/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ui.ressourcen;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import pd.spiel.spieler.SpielerFarbe;
import applikation.client.pd.Karte;

/**
 * Dient zum Laden der einzelnen Icons, die für die grafische Darstellung des
 * Spieles benötigt werden.
 */
final public class Icons {
	public static final Icon BRETT = ladeBild("/ui/ressourcen/brett.png");
	public static final Icon HINWEIS = ladeBild("/ui/ressourcen/hinweis_vertiefung.png");
	public static final Icon JOKERSCHLIESSEN = ladeBild("/ui/ressourcen/joker_schliessen.png");
	public static final Icon LOGO = ladeBild("/ui/ressourcen/bodesuri_logo.png");
	public static final Icon AM_ZUG = ladeBild("/ui/ressourcen/am_zug.png");
	public static final Icon FAHNE = ladeBild("/ui/ressourcen/fahne.png");

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
	 * @param pfad
	 *            Pfad des zu ladenden Icons.
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

	public static Icon getSpielerHoverIcon(SpielerFarbe farbe) {
		String name = "feld_hover_" + farbe.toString().toLowerCase() + ".png";
		return getIcon(name);
	}

	public static Icon getSpielerGeist(SpielerFarbe farbe) {
		String name = "figur_geist_" + farbe.toString().toLowerCase() + ".png";
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
