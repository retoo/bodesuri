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


package ch.bodesuri.initialisierung;

import ch.bodesuri.applikation.client.ClientAutomat;
import ch.bodesuri.applikation.client.controller.Controller;
import ch.bodesuri.applikation.client.konfiguration.Konfiguration;
import ch.bodesuri.dienste.ArgumentParser;
import ch.bodesuri.dienste.eventqueue.EventQueue;
import ch.bodesuri.dienste.threads.BodesuriThread;
import ch.bodesuri.ui.GUIController;

/**
 * Applikationsstart des Clients. Benötigte Instanzen werden erstellt, Client
 * wird initialisiert und gestartet.
 */
public class Bodesuri extends BodesuriThread {
	private Konfiguration konfiguration;

	public Bodesuri() {
		this(new Konfiguration());
	}

	public Bodesuri(Konfiguration konfiguration) {
		super("Bodesuri Client " + konfiguration.defaultName);
		this.konfiguration = konfiguration;
	}

	public void run() {
		EventQueue queue = new EventQueue();
		Controller controller = new GUIController(queue, konfiguration);
		ClientAutomat automat = new ClientAutomat(controller, queue,
		                                          konfiguration);
		automat.run();
	}

	/**
	 * Den Client starten.
	 * 
	 * @param args
	 *            Wird nicht genutzt
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Konfiguration konfiguration = new Konfiguration();
		if (args.length == 2) {
			konfiguration.defaultHost = ArgumentParser.parseHostname(args[0]);
			konfiguration.defaultPort = ArgumentParser.parsePort(args[1]);
		}
		Bodesuri bodesuri = new Bodesuri(konfiguration);
		bodesuri.start();
		bodesuri.join();
	}
}
