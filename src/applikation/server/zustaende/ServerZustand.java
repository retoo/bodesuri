package applikation.server.zustaende;

import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.BeitrittVerweigert;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.SpielAbbruch;
import applikation.nachrichten.SpielBeitreten;
import applikation.nachrichten.ZugInformation;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungGeschlossen;
import dienste.netzwerk.server.NetzwerkEvent;
import dienste.netzwerk.server.NeueVerbindung;
import dienste.netzwerk.server.SchwererDaemonFehler;

/**
 * Spezifischer aktiver Server-Zustand
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
			/* Endpunkt war nicht bekannt. Das Spiel muss somit nicht abegbrochen werden */
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
		System.out.println("Neue Verbindung von " + verbindung.endpunkt);
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

		/* TODO: Philippe: kannst du das noch im Client einbauen (-reto) */
		absender.sende(new BeitrittVerweigert());
		absender.ausschalten();
		return this.getClass();
	}

	public void setSpielDaten(Spiel spielDaten) {
		this.spiel = spielDaten;
	}
}
