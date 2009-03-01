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

package ch.bodesuri.dienste;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Schreibt den Status des Servers in eine Datei. Diese kann dann auf einer
 * Homepage eingebunden werden.
 */
public class ServerStatus {
	private final int port;
	private File datei;

	/**
	 * Schreibt den Zustand des Servers in eine Datei.
	 * 
	 * @param port
	 *            Der Port des Servers. Aus ihm wird der Dateinamen gebildet.
	 */
	public ServerStatus(int port) {
		this.port = port;
		datei = new File(port + ".txt");
	}

	/**
	 * Schreibt <code>status</code> in die Datei. Wenn die Datei bereits
	 * existiert wird sie überschrieben.
	 * 
	 * @param status
	 *            Der Statusmeldung die geschrieben werden soll.
	 */
	public void schreibeStatus(String status) {
		status = "<p>Der Server läuft auf Port " + port + ".</p>\n" + status;
		try {
			FileWriter fstream = new FileWriter(datei);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(status);
			out.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Löscht die Statusdatei
	 * 
	 * @return Ob die Datei erfolgreich gelöscht wurde.
	 */
	public boolean loescheDatei() {
		return datei.delete();
	}
}
