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


package applikation.client.zustaende;

import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Karte;
import applikation.client.pd.SteuerungsZustand;
import applikation.nachrichten.KartenTausch;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in dem wir warten bis der Spieler die Karte, die er tauschen möchte
 * ausgewählt hat. Tritt {@link KarteGewaehltEvent} ein, gehen wir in den Zustand
 * {@link KarteTauschenBekommen} über.
 */
public class KarteTauschenAuswaehlen extends ClientZustand {
	public void onEntry() {
		spiel.hinweis.neuerHinweis("Zu tauschende Karte wählen.", true, spiel.spielerIch.getFarbe());
		spiel.spielerIch.getKarten().setAktiv(true);
		spiel.spielerIch.setAmZug(true);
		controller.karteTauschenAuswaehlen();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		if (spiel.ausgewaehlteKarte != null && spiel.ausgewaehlteKarte.istAusgewaehlt()) {
			spiel.ausgewaehlteKarte.setAusgewaehlt(false);
		}
		spiel.ausgewaehlteKarte = event.karte;
		spiel.ausgewaehlteKarte.setAusgewaehlt(true);
		spiel.setSteuerungsZustand(SteuerungsZustand.TAUSCHEN);
		return this.getClass();
	}

	Class<? extends Zustand> kartenTauschBestaetigt() {
		Karte karte = spiel.ausgewaehlteKarte;
		if (karte == null) {
			return this.getClass();
		}
		spiel.ausgewaehlteKarte = null;
		karte.setAusgewaehlt(false);
		spiel.spielerIch.getKarten().remove(karte);

		spiel.endpunkt.sende(new KartenTausch(karte.getKarte()));

		return KarteTauschenBekommen.class;
	}

	public void onExit() {
		spiel.spielerIch.getKarten().setAktiv(false);
		spiel.setSteuerungsZustand(SteuerungsZustand.NICHTS);
	}
}
