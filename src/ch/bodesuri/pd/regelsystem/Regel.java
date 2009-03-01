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


package ch.bodesuri.pd.regelsystem;

import java.util.List;

import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.spiel.spieler.Spieler;
import ch.bodesuri.pd.zugsystem.Zug;


/**
 * Basisklasse für Regeln.
 */
public abstract class Regel {
	private String beschreibung;

	/**
	 * @return true, wenn Regel mit Weg arbeitet
	 */
	public boolean arbeitetMitWeg() {
		return false;
	}

	/**
	 * Validiere eine Zugeingabe. Bei einer gültigen Eingabe wird ein Zug
	 * zurückgegeben und sonst wird eine RegelVerstoss-Exception geworfen.
	 *
	 * @param zugEingabe Zugeingabe, die validiert werden soll
	 * @return Zug, der dann ausgeführt werden kann
	 * @throws RegelVerstoss Tritt bei Regelwidrigkeit auf und enthält Grund
	 */
	public abstract Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss;

	/**
	 * Finde heraus, ob der Spieler mit dieser Regel noch ziehen kann, oder ob
	 * er aufgeben muss.
	 *
	 * @param spieler Spieler, dessen Figuren überprüft werden
	 * @return true, wenn Spieler noch Zugmöglichkeit hat
	 */
	public boolean istZugMoeglich(Spieler spieler) {
		ZugEingabeHoffender hoffender = new ZugEingabeHoffender();
		liefereZugEingaben(spieler, null, hoffender);
		return hoffender.hatBekommen();
	}

	/**
	 * Gib alle ZugEingaben zurück, mit denen der Spieler noch ziehen könnte.
	 * 
	 * @param spieler Spieler, dessen Figuren überprüft werden
	 * @param karte Karte, die den ZugEingaben zugewiesen werden soll
	 * @return Liste von ZugEingaben
	 */
	public List<ZugEingabe> getMoeglicheZuege(Spieler spieler, Karte karte) {
		ZugEingabeSammler sammler = new ZugEingabeSammler();
		liefereZugEingaben(spieler, karte, sammler);
		return sammler.getZugEingaben();
	}

	/**
	 * Muss dem Abnehmer alle möglichen ZugEingaben über
	 * {@link ZugEingabeAbnehmer#nehmeEntgegen} liefern. Wenn dieses true
	 * zurückgibt, kann abgebrochen werden.
	 * 
	 * @param spieler Spieler, für den ZugEingaben gesucht werden
	 * @param karte Karte, mit der die ZugEingaben assoziiert werden
	 * @param abnehmer Abnehmer der ZugEingaben
	 */
	protected abstract void liefereZugEingaben(Spieler spieler, Karte karte,
	                                           ZugEingabeAbnehmer abnehmer);

	/**
	 * @return Beschreibung der Regel
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	protected void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
}
