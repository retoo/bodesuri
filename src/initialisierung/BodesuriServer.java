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

import applikation.server.ServerAutomat;
import dienste.threads.BodesuriThread;

/**
 * Applikationsstart des Servers. Benötigte Instanzen werden erstellt, Server
 * wird initialisiert und gestartet.
 */
public class BodesuriServer extends BodesuriThread {
	private ServerAutomat server;
	private int anzahlSpieler = 4;
	private static final int VERZOEGERUNG = 100;
	private static final int VERSUCHE = 55;

	public BodesuriServer() {
		super("Bodesuri Server");
	}

	public BodesuriServer(int anzahlSpieler) {
		this();

		this.anzahlSpieler = anzahlSpieler;
	}

	public void run() {
		server = new ServerAutomat(anzahlSpieler, false);
		server.run();
	}

	/**
	 * Blockiert bis der Server bereit ist.
	 */
	public void warteAufBereitschaft() {
		boolean serverBereit = false;
		for (int i = 0; i < VERSUCHE; i++) {

			try {
				Thread.sleep(VERZOEGERUNG);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			if (server != null && server.istBereitFuerSpieler()) {
				serverBereit = true;
				break;
			}
		}

		if (!serverBereit) {
			int pruefdauer = (VERSUCHE * VERZOEGERUNG) / 1000;
			System.out.println("ERROR: Server startete nicht innert "
			                   + pruefdauer + " Sekunden.");
			System.exit(99);
		}
	}

	/**
	 * Den Server starten.
	 *
	 * @param args
	 *            Wird nicht genutzt
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer();

		server.start();
		server.join();
	}
}
