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


package pd.spiel;

import java.util.List;
import java.util.Vector;

import pd.spiel.brett.Brett;
import pd.spiel.spieler.Spieler;
import pd.spiel.spieler.SpielerFarbe;

/**
 * Spiel, welches ein Brett und 4 Spieler umfasst.
 */
public class Spiel {
	private static final int ANZAHL_SPIELER = 4;

	private Brett brett;
	private Vector<Spieler> spieler;
	private int beigetreteneSpieler = 0;

	/**
	 * Erstellt ein Spiel. Das Brett wird erstellt und die Spieler. Später
	 * werden den Spielern noch Namen gegeben, dann kann das Spiel beginnen!
	 */
	public Spiel() {
		/*
		 * Die Spieler müssen vor dem Brett erstellt werden, da sie für die
		 * Erstellung des Bretts bereits vorhanden sein müssen. Namen werden
		 * ihnen später mit fuegeHinzu zugewiesen.
		 */
		spieler = new Vector<Spieler>();
		for (int i = 0; i < ANZAHL_SPIELER; ++i) {
			Spieler sp = new Spieler(i, this, SpielerFarbe.values()[i]);
			spieler.add(sp);
		}

		brett = new Brett(spieler);
	}

	/**
	 * Gebe dem nächsten Spieler einen Namen.
	 *
	 * @param spielerName
	 *            Spielername
	 * @return Der neu erstellte Spieler.
	 */
	public Spieler fuegeHinzu(String spielerName) {
		Spieler neuerSpieler = spieler.get(beigetreteneSpieler);
		neuerSpieler.setName(spielerName);
		++beigetreteneSpieler;
		return neuerSpieler;
	}

	/**
	 * @return Brett, auf dem gespielt wird
	 */
	public Brett getBrett() {
    	return brett;
    }

	/**
	 * @return Liste von Spielern, die mitspielen
	 */
	public List<Spieler> getSpieler() {
    	return spieler;
    }
}
