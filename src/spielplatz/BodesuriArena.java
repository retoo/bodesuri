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


package spielplatz;

import initialisierung.BodesuriBot;
import initialisierung.BodesuriServer;

import java.util.Vector;

import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;

public class BodesuriArena {
	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();
		server.start();
		server.warteAufBereitschaft();

		Vector<Thread> clients = new Vector<Thread>();

		Vector<String> nicks = new Vector<String>();
		nicks.add("Anna Navarre ");
		nicks.add("JC Denton");
		nicks.add("Joseph Manderley");
		nicks.add("Walton Simons");

		for (int i = 0; i < 4; i++) {
			Konfiguration konfig = new Konfiguration();
			konfig.defaultName = nicks.get(i);
			konfig.debugAutoLogin = true;
			konfig.debugMeldungen = true;
			konfig.debugBotsZoegernNicht = true;

			Thread t = new BodesuriBot(konfig, Stupidbot.class, true);

			t.start();
			clients.add(t);
		}

		server.join();

		for (Thread t : clients) {
			t.join();
		}

		System.out.println("Alles fertig");
	}
}
