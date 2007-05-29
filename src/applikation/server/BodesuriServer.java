package applikation.server;

import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.KartenTauschen;
import applikation.server.zustaende.ServerStart;
import applikation.server.zustaende.ServerEnde;
import applikation.server.zustaende.StarteRunde;
import applikation.server.zustaende.StarteSpiel;
import applikation.server.zustaende.StarteZug;
import applikation.server.zustaende.VersendeZug;
import applikation.server.zustaende.WarteAufZug;
import applikation.server.zustaende.ZugAbschluss;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.server.Server;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class BodesuriServer extends Automat {
	/**
	 * Maximale Anzahl Spieler
	 *
	 * TODO: muss das wirklich so ne Konstante sein.
	 */
	public static final int MAXSPIELER = 4;
	/**
	 * Server
	 */
	public Server server;
	/**
	 * EventQueue
	 */
	public EventQueue queue;
	/**
	 * Spielerschaft
	 */
	public Spielerschaft spielerschaft;


	/**
	 * Initialisiert den Server-Automaten
	 */
	public BodesuriServer() {
		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		registriere(new StarteSpiel());
		registriere(new StarteRunde());
		registriere(new StarteZug());
		registriere(new WarteAufZug());
		registriere(new VersendeZug());
		registriere(new ZugAbschluss());
		registriere(new ServerEnde());
		registriere(new KartenTauschen());
		registriere(new WarteAufZug());

		setStart(ServerStart.class);

		this.queue = new EventQueue();

		setEventQuelle(queue);
	}


	/**
	 * Startet den Server.
	 *
	 * @param args
	 *            Wird nicht genutzt.
	 */
	public static void main(String[] args) {
		BodesuriServer server = new BodesuriServer();

		try {
				server.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server: Exception in run()");
			System.exit(99);
		}
	}
}
