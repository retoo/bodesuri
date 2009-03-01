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


package ch.bodesuri.applikation.server.pd;

import ch.bodesuri.applikation.nachrichten.SpielerInfo;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.dienste.netzwerk.Nachricht;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	private Spieler partner;
	private EndPunktInterface endpunkt;
	public ch.bodesuri.pd.spiel.spieler.Spieler spieler;

	/**
	 * Erstellt einen neuen Spieler
	 *
	 * @param absender
	 *            Endpunkt des Spielers
	 * @param spieler
	 *            Spieler-Objekt aus der PD
	 */
	public Spieler(EndPunktInterface absender, ch.bodesuri.pd.spiel.spieler.Spieler spieler) {
		this.endpunkt = absender;
		this.spieler = spieler;

	}

	/**
	 * Sendet die übergebene Nachricht an den Endpunkt
	 *
	 * @param nachricht zu sendende Nachricht
	 */
	public void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}


	public EndPunktInterface getEndPunkt() {
		return endpunkt;
	}

	public SpielerInfo getSpielerInfo() {
		return new SpielerInfo(spieler.getName());
	}

	public String getName() {
		return spieler.getName();
	}

	public String toString() {
		return spieler.getName() + " (" + endpunkt + ")";
	}

	public Spieler getPartner() {
		return partner;
	}

	public void setPartner(Spieler partner) {
		this.partner = partner;
		if (partner != null) {
			spieler.setPartner(partner.spieler);
		}
	}
}
