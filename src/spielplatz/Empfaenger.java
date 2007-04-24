package spielplatz;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Empfaenger {
	String registry_host = "localhost";
	
	private LinkedBlockingQueue<Nachricht> nachrichten = new LinkedBlockingQueue<Nachricht>();
	
	public String name;

	public Empfaenger(String name, boolean createRegistry) throws RemoteException, AlreadyBoundException, MalformedURLException {
		this.name = name;

		Briefkasten bk = new BriefKastenImpl(nachrichten);
		
		if (createRegistry) 
			LocateRegistry.createRegistry(1099);
		
		UnicastRemoteObject.exportObject(bk, 0);
		
		String url = "rmi://" + registry_host + "/" + name;
		System.out.println("url: " + url);
	
		Naming.rebind(url, bk);

	}

	/* liefert den stub zu einem gewissen namen */
	public Briefkasten schlageNach(String name) throws RemoteException, NotBoundException, MalformedURLException {
		Remote x =  Naming.lookup("rmi://" + registry_host + "/" + name );
		System.out.println("X ist " + x.getClass().toString());
		
		return (Briefkasten) x;
	}

	/* Meldet dem übergebenen peer den eigenen Namen */
	public void registriereBei(Briefkasten endpunkt) throws RemoteException {		
		endpunkt.sende(new Registrierung(name));
	}



	protected Nachricht getNächsteNachricht() {
		return getNächsteNachricht(true);
	}
	
	protected Nachricht getNächsteNachricht(boolean blocking) {
		Nachricht n = null;
		try {
			
			if (blocking)
				n = nachrichten.take();
			else 
				n = nachrichten.poll();
			
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
