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


package ch.bodesuri.spielplatz;

import intelliDOG.ai.bots.SimpleBot;

import java.util.Vector;

import ch.bodesuri.applikation.bot.Bot;
import ch.bodesuri.applikation.bot.IntelliBot;
import ch.bodesuri.applikation.client.konfiguration.Konfiguration;
import ch.bodesuri.initialisierung.BodesuriBot;
import ch.bodesuri.initialisierung.BodesuriServer;


public class BodesuriArena {
	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();
		server.start();
		server.warteAufBereitschaft();

		Vector<Thread> clients = new Vector<Thread>();

		Vector<String> nicks = new Vector<String>();
		nicks.add("Anna");
		nicks.add("JC");
		nicks.add("Joseph");
		nicks.add("Walton");

		for (int i = 0; i < 4; i++) {
			Class<? extends Bot> botType = (i % 2 == 0) ? IntelliBot.class : SimpleBot.class;

			Konfiguration konfig = new Konfiguration();
			String botName = botType.getSimpleName();

			konfig.defaultName = nicks.get(i) + " " + botName;
			konfig.debugAutoLogin = true;
			konfig.debugMeldungen = false;
			konfig.debugBotsZoegernNicht = false;
			konfig.aiKeinGui = false;
			konfig.aiDebugMsg = true;

			Thread t = new BodesuriBot(konfig, botType, true);

			t.start();
			clients.add(t);

			Thread.sleep(1000);
		}

		server.join();

		for (Thread t : clients) {
			t.join();
		}

		System.out.println("Alles fertig");
	}
}
