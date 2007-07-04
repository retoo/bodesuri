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


package initialisierung;

import ui.GUIController;
import applikation.bot.Bot;
import applikation.bot.BotController;
import applikation.bot.Stupidbot;
import applikation.client.ClientAutomat;
import applikation.client.konfiguration.Konfiguration;
import dienste.automat.Automat;
import dienste.eventqueue.EventQueue;

/**
 * Applikationsstart des Bots. Benötigte Instanzen werden erstellt, Bot
 * wird initialisiert und gestartet.
 */
public class BodesuriBot extends Thread {
	private BotController controller;
	private EventQueue queue;
	private Konfiguration konfiguration;

	public BodesuriBot(Konfiguration konfig, Class<? extends Bot> typ,
	                   boolean gui) {
		super("Bot " + konfig.defaultName);
		this.konfiguration = konfig;
		this.queue = new EventQueue();

		GUIController guiController = null;

		if (gui) {
			guiController = new GUIController(queue, konfig);
		}

		Bot bot = createBot(typ);

		controller = new BotController(konfig, queue, guiController, bot);
	}

	/**
	 * Den Bot starten.
	 *
	 * @param args
	 *            Wird nicht genutzt
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Konfiguration konfig = new Konfiguration();
		konfig.defaultName = "Roboter";

		BodesuriBot bot = new BodesuriBot(konfig, Stupidbot.class, true);

		bot.start();
		bot.join();
	}

	private Bot createBot(Class<? extends Bot> typ) {
		try {
			return typ.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		Automat client = new ClientAutomat(controller, queue, konfiguration);
		client.init();
		client.run();
	}

	public int getAnzahlZuege() {
		return controller.getAnzahlZuege();
	}
}
