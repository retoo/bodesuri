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


package applikation.server;

import applikation.server.pd.Spiel;
import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.KartenTauschen;
import applikation.server.zustaende.ServerStart;
import applikation.server.zustaende.ServerStoppen;
import applikation.server.zustaende.ServerZustand;
import applikation.server.zustaende.SpielStart;
import applikation.server.zustaende.StartRunde;
import applikation.server.zustaende.StarteZug;
import applikation.server.zustaende.WarteAufZug;
import applikation.server.zustaende.WarteBisAlleVerbindungenWeg;
import applikation.server.zustaende.ZugAbschluss;
import dienste.automat.Automat;
import dienste.automat.EventQuelleAdapter;
import dienste.eventqueue.EventQueue;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class ServerAutomat extends Automat {
	private static final int ANZ_SPIELER = 4;
	private Spiel spiel;

	/**
	 * Initialisiert den Server-Automaten mit vier Spielern.
	 * @param debug
	 */
	public ServerAutomat(boolean debug) {
		this(ANZ_SPIELER, debug);
	}

	/**
	 * Initialisiert den Server-Automaten
	 *
	 * @param anzSpieler Anzahl der Spieler
	 * @param debug
	 */
	public ServerAutomat(int anzSpieler, boolean debug) {
		super(debug);
		EventQueue queue = new EventQueue();
		spiel = new Spiel(anzSpieler);
		spiel.queue = queue;

		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		registriere(new SpielStart());
		registriere(new StartRunde());
		registriere(new StarteZug());
		registriere(new WarteAufZug());
		registriere(new ZugAbschluss());
		registriere(new WarteBisAlleVerbindungenWeg());
		registriere(new KartenTauschen());
		registriere(new WarteAufZug());
		registriere(new ServerStoppen());

		setStart(ServerStart.class);

		setEventQuelle(new EventQuelleAdapter(queue));
	}

	private void registriere(ServerZustand zustand) {
		zustand.setSpielDaten(spiel);

		super.registriere(zustand);
	}

	/**
	 * Prüft ob der Server bereit ist um Spieler zu empfangen.
	 *
	 * @return true falls der Server bereit ist
	 */
	public boolean istBereitFuerSpieler() {
		return isZustand(EmpfangeSpieler.class);
	}

	public void run() {
		super.run();
	}

	public String toString() {
		return "Server-Automat";
	}
}
