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


package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import pd.ProblemDomain;
import pd.serialisierung.CodiererThreads;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dekoriert das Spiel aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie der Spieler der am Zug ist,
 * der Spieler bei dem der Client läuft, die ausgewählte Karte usw.
 * Verfügt ausserdem über eigene Spieler und ein eigenes Brett, die Spieler und
 * Brett aus der PD dekorieren.
 */
public class Spiel extends Observable implements SerialisierungsKontext {
	private ProblemDomain problemDomain;
	private pd.spiel.Spiel spiel;
	private Brett brett;
	private Vector<Spieler> spieler;

	public EventQueue queue;
	public EndPunktInterface endpunkt;
	public Automat zugAutomat;
	public Konfiguration konfiguration;

	public String spielerName;
	/** Der Spieler der den Client bedient */
	public Spieler spielerIch;
	/** Der Spieler der am Zug ist */
	public applikation.client.pd.Spieler aktuellerSpieler;
	/** Der Button welcher im Steuerungsview (rechts unten) angezeigt wird */
	public SteuerungsZustand steuerungsZustand;
	/** Der Inhalt des Hinweisliste rechts unten */
	public HinweisSender hinweis;
	public Karte ausgewaehlteKarte;

	/** Abbildung von PD-Spielern zu Applikations-Spielern */
	private IdentityHashMap<pd.spiel.spieler.Spieler, Spieler> spielerRegister;
	public Chat chat;

	public Spiel(Konfiguration konfig) {
		this.konfiguration = konfig;

		problemDomain = new ProblemDomain();
		spiel = problemDomain.getSpiel();

		// Assoziiere PD-Spieler nach App-Spieler
		spielerRegister = new IdentityHashMap<pd.spiel.spieler.Spieler, Spieler>();
		spieler = new Vector<Spieler>();
		for (int i = 0; i < spiel.getSpieler().size(); i++) {
			pd.spiel.spieler.Spieler pdSpieler = spiel.getSpieler().get(i);
			Spieler appSpieler = new Spieler(pdSpieler);
			spieler.add(appSpieler);
			spielerRegister.put(pdSpieler, appSpieler);
		}

		brett = new Brett(spiel.getBrett());
		chat = new Chat();
		hinweis = new HinweisSender();
		steuerungsZustand = SteuerungsZustand.NICHTS;
	}
	
	/**
	 *  Ctor used by JUnit test: ParserTest 
	 */ 
	public Spiel(ProblemDomain pd) 
	{
		this.problemDomain = pd; 
		this.spiel = pd.getSpiel(); 
		
		spielerRegister = new IdentityHashMap<pd.spiel.spieler.Spieler, Spieler>();
		spieler = new Vector<Spieler>();
		for (int i = 0; i < spiel.getSpieler().size(); i++) {
			pd.spiel.spieler.Spieler pdSpieler = spiel.getSpieler().get(i);
			Spieler appSpieler = new Spieler(pdSpieler);
			spieler.add(appSpieler);
			spielerRegister.put(pdSpieler, appSpieler);
		}
		this.brett = new Brett(spiel.getBrett());
		
	}

	/**
	 * Sucht nach dem passenden {@link Spieler} zu einem
	 * {@link pd.spiel.spieler.Spieler}.
	 *
	 * @param spieler
	 *            Der bekannte PD-Spieler
	 * @return Der gesuchte Applikations-Spieler
	 */
	public Spieler findeSpieler(pd.spiel.spieler.Spieler spieler) {
		Spieler s = spielerRegister.get(spieler);

		if (s == null) {
			throw new RuntimeException("Kann app.Spieler für den Spieler "
			                           + spieler + " nicht finden!");
		}
		return s;
	}

	public Brett getBrett() {
		return brett;
	}

	public List<Spieler> getSpieler() {
		return spieler;
	}

	public pd.spiel.Spiel getSpiel() {
		return spiel;
	}

	public SteuerungsZustand getSteuerungsZustand() {
		return steuerungsZustand;
	}

	public void setSteuerungsZustand(SteuerungsZustand steuerungsZustand) {
		this.steuerungsZustand = steuerungsZustand;
		setChanged();
		notifyObservers();
	}

	public void registriere(Thread thread) {
		CodiererThreads.registriere(thread, problemDomain.getCodierer());
	}
}
