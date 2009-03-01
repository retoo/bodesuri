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


package ch.bodesuri.applikation.server;

import ch.bodesuri.applikation.server.pd.Spiel;
import ch.bodesuri.applikation.server.zustaende.EmpfangeSpieler;
import ch.bodesuri.applikation.server.zustaende.KartenTauschen;
import ch.bodesuri.applikation.server.zustaende.ServerStart;
import ch.bodesuri.applikation.server.zustaende.ServerStoppen;
import ch.bodesuri.applikation.server.zustaende.ServerZustand;
import ch.bodesuri.applikation.server.zustaende.SpielStart;
import ch.bodesuri.applikation.server.zustaende.StartRunde;
import ch.bodesuri.applikation.server.zustaende.StarteZug;
import ch.bodesuri.applikation.server.zustaende.WarteAufZug;
import ch.bodesuri.applikation.server.zustaende.WarteBisAlleVerbindungenWeg;
import ch.bodesuri.applikation.server.zustaende.ZugAbschluss;
import ch.bodesuri.dienste.automat.Automat;
import ch.bodesuri.dienste.automat.EventQuelleAdapter;
import ch.bodesuri.dienste.eventqueue.EventQueue;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class ServerAutomat extends Automat {
	private Spiel spiel;

	/**
	 * Initialisiert den Server-Automaten
	 *
	 * @param anzSpieler Anzahl der Spieler
	 * @param debug
	 */
	public ServerAutomat(int anzSpieler, int port, boolean debug) {
		super(debug);
		EventQueue queue = new EventQueue();
		spiel = new Spiel(anzSpieler);
		spiel.queue = queue;

		registriere(new ServerStart(port));
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
