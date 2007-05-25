package applikation.server;

import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.ServerStart;
import applikation.server.zustaende.SpieleSpiel;
import applikation.server.zustaende.StarteSpiel;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.server.Server;

/**
 * Der Server. Wird vom Benutzer gestartet.
 */
public class BodesuriServer extends Automat {
	public static final int MAXSPIELER = 4;
	public Server server;
	public EventQueue queue;
	public Spielerschaft spielerschaft;
	
	
	public BodesuriServer() {
		registriere(new ServerStart());
		registriere(new EmpfangeSpieler());
		registriere(new StarteSpiel());
		registriere(new SpieleSpiel());
		
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
