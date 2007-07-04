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


package applikation.server.zustaende;

import pd.zugsystem.Zug;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.ZugAufforderung;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Ein neuer {@link Zug} wird gestartet. Hierfür bestimmt der Server zuerst wer an der
 * Reihe ist und teilt dies dem betroffenen Spieler mittels der Nachricht
 * {@link ZugAufforderung} mit.
 *
 * Die restlichen Spielern werden mit der Nachricht {@link AktuellerSpielerInformation}
 * darüber informiert wer gerade am Zug ist.
 *
 * Anschliessend wird direkt in den Zustand {@link WarteAufZug} gewechselt.
 */
public class StarteZug extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spiel spielers = spiel;

		spielers.runde.rotiereSpieler();
		Spieler naechsterSpieler = spielers.runde.getAktuellerSpieler();

		AktuellerSpielerInformation info = new AktuellerSpielerInformation(naechsterSpieler.spieler);
		spielers.broadcast(info);

		ZugAufforderung aufforderung = new ZugAufforderung();
		naechsterSpieler.sende(aufforderung);

		return WarteAufZug.class;
	}
}
