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


import initialisierung.BodesuriBot;
import initialisierung.BodesuriServer;

import java.util.Vector;

import junit.framework.TestCase;
import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;

/**
 * Bietet Tests zur Prüfung der Funktionalität des Servers.
 */


public class ServerTest extends TestCase {
	/**
	 * Testet den Server auf korrektes Funktionieren, indem ein Server
	 * und vier Endpunkte erstellt werden und ein Beitritt der Spieler
	 * simuliert wird.
	 * @throws InterruptedException
	 */
	public void testServer() throws InterruptedException {
		Vector<String> nicks = new Vector<String>();

		nicks.add("Anna Navarre ");
		nicks.add("JC Denton");
		nicks.add("Joseph Manderley");
		nicks.add("Walton Simons");

		Thread server = new BodesuriServer();
		server.start();

		Vector<BodesuriBot> clients = new Vector<BodesuriBot>();

		for (String nick : nicks) {
			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = nick;
			konfig.debugKeineLobbyVerzoegerung = true;
			konfig.debugBotsZoegernNicht = true;

			BodesuriBot t = new BodesuriBot(konfig, Stupidbot.class, false);

			t.start();
			clients.add(t);
		}

		server.join();
		System.out.println("Server fertig");


		for (BodesuriBot t : clients) {
			t.join();
			System.out.println("Client fertig " + t.getAnzahlZuege());
		}
	}
}
