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

import java.text.DateFormat;
import java.util.Date;

import applikation.nachrichten.AufgabeInformation;

import pd.regelsystem.ZugEingabe;

import dienste.observer.ObservableList;

/**
 * Der Chat. Text wird in einer ObserveableList gespeichert.
 */
public class Chat extends ObservableList<String> {
	private DateFormat dateFormat = DateFormat.getTimeInstance();

	public void neueNachricht(String absender, String nachricht) {
		add(getZeit() + ": " + absender + "> " + nachricht);
	}

	public void meldeZug(ZugEingabe zug) {
		add(getZeit() + ": " + zug.getSpieler() + " spielt " + zug.getKarte());
	}

	public void meldeAufgabe(AufgabeInformation aufgabe) {
		add(getZeit() + ": " + aufgabe.spieler + " gibt auf.");
	}

	public void meldeRundenStart() {
		add(getZeit() + ": Rundenstart");
	}

	private final String getZeit() {
		String time = dateFormat.format(new Date());
		return time;
	}
}
