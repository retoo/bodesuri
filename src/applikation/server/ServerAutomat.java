package applikation.server;

import applikation.server.pd.SpielDaten;
import applikation.server.zustaende.ServerZustand;
import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.KartenTauschen;
import applikation.server.zustaende.ServerEnde;
import applikation.server.zustaende.ServerStart;
import applikation.server.zustaende.StartRunde;
import applikation.server.zustaende.StarteSpiel;
import applikation.server.zustaende.StarteZug;
import applikation.server.zustaende.VersendeZug;
import applikation.server.zustaende.WarteAufZug;
import applikation.server.zustaende.ZugAbschluss;
import dienste.automat.Automat;
import dienste.automat.EventQuelleAdapter;
import dienste.eventqueue.EventQueue;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class ServerAutomat extends Automat {
	private static final int ANZ_SPIELER = 4;
	private SpielDaten spielDaten;

	public ServerAutomat() {
		this(ANZ_SPIELER);
	}

	/**
	 * Initialisiert den Server-Automaten
	 *
	 * @param anzSpieler Anzahl der Spieler
	 */
	public ServerAutomat(int anzSpieler) {
		EventQueue queue = new EventQueue();
		spielDaten = new SpielDaten(anzSpieler);

		spielDaten.queue = queue;

		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		registriere(new StarteSpiel());
		registriere(new StartRunde());
		registriere(new StarteZug());
		registriere(new WarteAufZug());
		registriere(new VersendeZug());
		registriere(new ZugAbschluss());
		registriere(new ServerEnde());
		registriere(new KartenTauschen());
		registriere(new WarteAufZug());

		setStart(ServerStart.class);

		setEventQuelle(new EventQuelleAdapter(queue));
	}

	public void registriere(ServerZustand zustand) {
		zustand.setSpielDaten(spielDaten);

		super.registriere(zustand);
	}

	public void run() {
		super.run();
	}

	public String toString() {
		return "Server-Automat";
	}
}
