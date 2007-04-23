package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Briefkasten implements EndPunkt {
	/* a Synced queue is kinda stupid here as .. wieso schreib ich das überhaupt 
	 * auf english... ach.. schaut doch selber nach :) 
	 */
	private LinkedBlockingQueue<Nachricht> nachrichten = new LinkedBlockingQueue<Nachricht>();
	private Registry telefonbuch;
	private String name;

	public Briefkasten(String name, boolean createRegistry) throws RemoteException, AlreadyBoundException {
		this.name = name;

		/* Registry anlegen falls notwendig */
		if (createRegistry) {
			/* Registry anlegen */
			telefonbuch = LocateRegistry.createRegistry(1099);
		} else {
			telefonbuch = LocateRegistry.getRegistry("localhost");
		}

		/* stub erstellen und der registry mitteilen */
		telefonbuch.bind(name, UnicastRemoteObject.exportObject(this, 0));
	}

	/* liefert den stub zu einem gewissen namen */
	public EndPunkt schlageNach(String name) throws RemoteException, NotBoundException {
		return (EndPunkt) telefonbuch.lookup(name);
	}

	/* Meldet dem übergebenen peer den eigenen Namen */
	public void registriereBei(EndPunkt endpunkt) throws RemoteException {
		endpunkt.sendeNachricht(new Registrierung(name));
	}

	public void sendeNachricht(Nachricht n)  {
		System.out.println("Übertrage Nachricht " + n);
		try {
			nachrichten.put(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(99);
		}			
	}

	protected Nachricht getNächsteNachricht() {
		Nachricht n = null;
		try {
			n = nachrichten.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(99);
		}

		return n;
	}

	public String toString() {
		return "Endpunkt: " + name;
	}
}
