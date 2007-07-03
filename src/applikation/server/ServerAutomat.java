package applikation.server;

import applikation.server.pd.Spiel;
import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.KartenTauschen;
import applikation.server.zustaende.ServerStart;
import applikation.server.zustaende.ServerStoppen;
import applikation.server.zustaende.ServerZustand;
import applikation.server.zustaende.SpielStart;
import applikation.server.zustaende.StartRunde;
import applikation.server.zustaende.StarteZug;
import applikation.server.zustaende.WarteAufZug;
import applikation.server.zustaende.WarteBisAlleVerbindungenWeg;
import applikation.server.zustaende.ZugAbschluss;
import dienste.automat.Automat;
import dienste.automat.EventQuelleAdapter;
import dienste.eventqueue.EventQueue;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class ServerAutomat extends Automat {
	private static final int ANZ_SPIELER = 4;
	private Spiel spiel;

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
		spiel = new Spiel(anzSpieler);
		spiel.queue = queue;

		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		registriere(new SpielStart());
		registriere(new StartRunde());
		registriere(new StarteZug());
		registriere(new WarteAufZug());
		registriere(new ZugAbschluss());
		registriere(new WarteBisAlleVerbindungenWeg());
		registriere(new KartenTauschen());
		registriere(new WarteAufZug());
		registriere(new ServerStoppen());

		setStart(ServerStart.class);

		setEventQuelle(new EventQuelleAdapter(queue));
	}

	private void registriere(ServerZustand zustand) {
		zustand.setSpielDaten(spiel);

		super.registriere(zustand);
	}

	public boolean istBereitFuerSpieler() {
		return isZustand(EmpfangeSpieler.class);
	}

	public void run() {
		super.run();
	}

	public String toString() {
		return "Server-Automat";
	}
}
