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


package applikation.client.zugautomat.pd;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;

public class SpielDaten {
	public Karte karte;
	/**
	 * Wenn der Joker ausgewählt ist, wird hier die Karte für die der Joker
	 * steht gespeichert.
	 */
	public Karte konkreteKarte;
	public Spiel spiel;
	public Weg weg;

	public LinkedList<Bewegung> bewegungen;

	public SpielDaten(Spiel spiel) {
		this.spiel = spiel;

		bewegungen = new LinkedList<Bewegung>();
		bewegungen.addLast(new Bewegung());

		weg = new Weg(spiel.getBrett());
	}

	public boolean neueBewegungHinzufuegen() {
		if (bewegungen.getLast().getStart() != null && bewegungen.getLast().getZiel() != null) {
			bewegungen.addLast(new Bewegung());
			return true;
		} else {
			return false;
		}
	}

	public Feld getStart() {
    	return bewegungen.getLast().getStart();
    }

	public void setStart(Feld start) {
		deaktivereAuswahl(getStart());
		bewegungen.getLast().setStart(start);
		aktivereAuswahl(getStart());
    }


	public Feld getZiel() {
    	return bewegungen.getLast().getZiel();
    }

	public void setZiel(Feld ziel) {
		deaktivereAuswahl(getZiel());
		bewegungen.getLast().setZiel(ziel);
		aktivereAuswahl(getZiel());
    }

	private void aktivereAuswahl(Feld feld) {
		if (feld != null)
			feld.setAusgewaehlt(true);
	}

	private void deaktivereAuswahl(Feld feld) {
		if (feld != null)
			feld.setAusgewaehlt(false);
	}

	public void felderDeaktivieren() {
		deaktivereAuswahl(getStart());
		deaktivereAuswahl(getZiel());
	}

	public List<pd.zugsystem.Bewegung> getPdBewegungen() {
		List<pd.zugsystem.Bewegung> pdBewegungen = new Vector<pd.zugsystem.Bewegung>();
		for (Bewegung bewegung : bewegungen) {
			pdBewegungen.add(bewegung.toBewegung());
		}
		return pdBewegungen;
	}
}
