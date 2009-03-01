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


package ch.bodesuri.applikation.server.zustaende;

import ch.bodesuri.applikation.nachrichten.Aufgabe;
import ch.bodesuri.applikation.nachrichten.BeitrittVerweigert;
import ch.bodesuri.applikation.nachrichten.ChatNachricht;
import ch.bodesuri.applikation.nachrichten.KartenTausch;
import ch.bodesuri.applikation.nachrichten.SpielAbbruch;
import ch.bodesuri.applikation.nachrichten.SpielBeitreten;
import ch.bodesuri.applikation.nachrichten.ZugInformation;
import ch.bodesuri.applikation.server.pd.Spiel;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.eventqueue.Event;
import ch.bodesuri.dienste.netzwerk.Brief;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.dienste.netzwerk.Nachricht;
import ch.bodesuri.dienste.netzwerk.VerbindungGeschlossen;
import ch.bodesuri.dienste.netzwerk.server.NetzwerkEvent;
import ch.bodesuri.dienste.netzwerk.server.NeueVerbindung;
import ch.bodesuri.dienste.netzwerk.server.SchwererDaemonFehler;

/**
 * Spezifischer Server-Zustand
 */
public abstract class ServerZustand extends Zustand {
	protected Spiel spiel;

	/*
	 * (non-Javadoc)
	 *
	 * @see dienste.automat.zustaende.AktiverZustand#handle(dienste.automat.Event)
	 */
	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof NetzwerkEvent) {
			NetzwerkEvent ne = (NetzwerkEvent) event;

			Brief brief = ne.brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten)
				return spielBeitreten(brief.absender,
				                      (SpielBeitreten) nachricht);
			else if (nachricht instanceof ZugInformation)
				return zugInfo(brief.absender, (ZugInformation) nachricht);
			else if (nachricht instanceof VerbindungGeschlossen)
				return verbindungGeschlossen(brief.absender);
			else if (nachricht instanceof KartenTausch)
				return kartenTausch(brief.absender, (KartenTausch) nachricht);
			else if (nachricht instanceof Aufgabe)
				return aufgabe(brief.absender, (Aufgabe) nachricht);
			else if (nachricht instanceof ChatNachricht)
				return chatnachricht(brief.absender, (ChatNachricht) nachricht);
			else
				throw new RuntimeException("Unbekannte Nachricht");
		} /* Systemnachrichten */
		else if (event instanceof NeueVerbindung)
			return neueVerbindung((NeueVerbindung) event);
		else if (event instanceof SchwererDaemonFehler)
			return schwererDaemonFehler((SchwererDaemonFehler) event);

		return super.handle(event);
	}

	public void handleException(RuntimeException exception) {
		/* Es trat ein schwerer Serverfehler auf. Nun werden alle Clients informiert
		 * und anschliessend beendet sich der Server
		 */

		Nachricht sa = new SpielAbbruch(
				"Auf dem Server ist ein Fehler aufgetreten ("
						+ exception.getMessage()
						+ "). Das Spiel wird abgebrochen");

		spiel.broadcast(sa);

		super.handleException(exception);
	}

	/* Die Handler sind bereits in den jeweiligen Event-Klassen beschrieben */

	private Class<? extends Zustand> chatnachricht(EndPunktInterface absender,
	                                               ChatNachricht nachricht) {
		spiel.broadcast(nachricht);
		return this.getClass();
	}

	Class<? extends Zustand> aufgabe(EndPunktInterface absender, Aufgabe aufgabe) {
		return keinUebergang();
	}

	Class<? extends Zustand> kartenTausch(EndPunktInterface absender,
	                                      KartenTausch tausch) {
		return keinUebergang();
	}

	Class<? extends Zustand> verbindungGeschlossen(EndPunktInterface absender) {
		/* Spieler aus dem Spiel entfernen, falls vorhanden */
		boolean res = spiel.entferne(absender);

		if (!res) {
			/* Endpunkt war nicht bekannt. Das Spiel muss somit nicht abgebrochen werden */
			return this.getClass();
		}

		spiel.broadcast(new SpielAbbruch("Die Verbindung zu "
		                                 + spiel.getSpieler(absender).spieler
		                                 + " ist abgebrochen. Das Spiel"
		                                 + " wird geschlossen."));

		return ServerStoppen.class;
	}

	Class<? extends Zustand> zugInfo(EndPunktInterface absender,
	                                 ZugInformation information) {
		return keinUebergang();
	}

	Class<? extends Zustand> neueVerbindung(NeueVerbindung verbindung) {
		return this.getClass();
	}

	Class<? extends Zustand> schwererDaemonFehler(SchwererDaemonFehler fehler) {
		throw new RuntimeException(fehler.exception);
	}

	/**
	 * Wenn wir uns nicht im Zustande {@link EmpfangeSpieler} befinden
	 * werden alle Clients abgewiesen.
	 *
	 * @param absender
	 * @param beitreten
	 * @return Zustand wird unverändert beibehalten
	 */
	Class<? extends Zustand> spielBeitreten(EndPunktInterface absender,
	                                        SpielBeitreten beitreten) {
		absender.sende(new BeitrittVerweigert("Der Server ist bereits voll."));
		absender.ausschalten();
		return this.getClass();
	}

	public void setSpielDaten(Spiel spielDaten) {
		this.spiel = spielDaten;
	}
}
