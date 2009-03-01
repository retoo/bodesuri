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


package ch.bodesuri.pd.spiel.brett;

import ch.bodesuri.pd.serialisierung.BodesuriCodierbaresObjekt;
import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.Spieler;

/**
 * Basisklasse der Felder, die auf dem Spielbrett liegen.
 *
 * Jedes Feld ist mit seinem Nächsten und Vorherigen verkettet, damit man von
 * einem Feld aus auf alle anderen Felder gelangen kann.
 *
 * @see Brett
 */
public abstract class Feld extends BodesuriCodierbaresObjekt {
	private int nummer;

	private Feld naechstes;
	private Feld vorheriges;

	private Figur figur;

	private boolean geschuetzt;

	/**
	 * Erstellt ein Feld.
	 *
	 * @param nummer Eindeutige Nummer (wird für Serialisierung verwendet)
	 */
	public Feld(int nummer) {
		super("Feld " + nummer);
		this.nummer = nummer;
	}

	/**
	 * Gibt das Feld zurück, das nach n Schritten kommt.
	 *
	 * @param n Anzahl Schritte zum Feld
	 * @return Feld
	 */
	public Feld getNtesFeld(int n) {
		int i = n;
		Feld f = this;
		while (i-- > 0)
			f = f.naechstes;

		return f;
	}

	/**
	 * Versetze Figur von diesem Feld auf das Zielfeld.
	 *
	 * @param ziel Zielfeld
	 */
	public void versetzeFigurAuf(Feld ziel) {
		ziel.setFigur(getFigur());
		setFigur(null);
	}

	/**
	 * @return true, wenn keine Figur auf dem Feld ist
	 */
	public boolean istFrei() {
		return getFigur() == null;
	}

	/**
	 * @return true, wenn eine Figur auf dem Feld ist
	 */
	public boolean istBesetzt() {
		return getFigur() != null;
	}

	/**
	 * @param spieler Spieler
	 * @return true, wenn eine Figur des Spielers auf dem Feld ist
	 */
	public boolean istBesetztVon(Spieler spieler) {
		return istBesetzt() && getFigur().istVon(spieler);
	}

	/**
	 * @return true, wenn Feld geschuetzt ist (nicht darauf gezogen werden kann)
	 */
	public boolean istGeschuetzt() {
		return geschuetzt;
	}

	/**
	 * @return true, wenn Feld ein Lagerfeld ist
	 */
	public boolean istLager() {
		return false;
	}

	/**
	 * @return true, wenn Feld ein Bankfeld ist
	 */
	public boolean istBank() {
		return false;
	}

	/**
	 * @return true, wenn Feld ein Himmelfeld ist
	 */
	public boolean istHimmel() {
		return false;
	}

	/**
	 * @return true, wenn Feld im Ring ist, also Bankfeld oder normales Feld ist
	 */
	public boolean istRing() {
		return false;
	}

	/**
	 * @return true, wenn Feld ein normales Feld ist
	 */
	public boolean istNormal() {
		return false;
	}

	/**
	 * @param geschuetzt Soll Feld geschuetzt sein?
	 */
	public void setGeschuetzt(boolean geschuetzt) {
		this.geschuetzt = geschuetzt;
	}

	/**
	 * Gibt das nächste Feld zurück, das mit diesem verbunden ist.
	 *
	 * @return nächstes Feld
	 */
	public Feld getNaechstes() {
		return naechstes;
	}

	/**
	 * Verkettet dieses Feld mit seinem nächsten Feld.
	 *
	 * @param naechstesFeld Nächstes Feld, mit dem verbunden werden soll
	 */
	public void setNaechstes(Feld naechstesFeld) {
		this.naechstes = naechstesFeld;
	}

	/**
	 * Gibt das vorherige Feld zurück, das mit diesem verbunden ist.
	 *
	 * @return vorheriges Feld
	 */
	public Feld getVorheriges() {
		return vorheriges;
	}

	/**
	 * Verkettet dieses Feld mit seinem vorherigen Feld.
	 *
	 * @param vorherigesFeld Vorheriges Feld, mit dem verbunden werden soll
	 */
	public void setVorheriges(Feld vorherigesFeld) {
		this.vorheriges = vorherigesFeld;
	}

	/**
	 * @return Figur, die sich auf diesem Feld befindet
	 */
	public Figur getFigur() {
		return figur;
	}

	/**
	 * @param figur Figur, die neu auf diesem Feld sein soll.
	 */
	public void setFigur(Figur figur) {
		this.figur = figur;
		if (figur != null) {
			figur.versetzeAuf(this);
		}

		setChanged();
		notifyObservers();
	}

	/**
	 * Gibt Feld aus, zum Beispiel so: "Feld 47"
	 */
	public String toString() {
		return this.getClass().getSimpleName() + " " + nummer;
	}

	/**
	 * @return Feldnummer
	 */
	public int getNummer() {
		return nummer;
	}
}
