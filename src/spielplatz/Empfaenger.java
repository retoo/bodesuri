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

public class Empfaenger  extends UnicastRemoteObject implements EndPunkt {
	private static final long serialVersionUID = 1L;
	String registry_host = "localhost";
	private LinkedBlockingQueue<Nachricht> nachrichten = new LinkedBlockingQueue<Nachricht>();
	public String name;

	public Empfaenger(String name, boolean createRegistry) throws RemoteException, AlreadyBoundException, MalformedURLException {
		this.name = name;

		/* Registry anlegen falls notwendig */
		if (createRegistry) {
			/* Registry anlegen */
			LocateRegistry.createRegistry(1099);
		} 
		//UnicastRemoteObject.exportObject((EndPunkt) this);
		Naming.rebind( "rmi://" + registry_host + "/" + name, this);

	}

	/* liefert den stub zu einem gewissen namen */
	public EndPunkt schlageNach(String name) throws RemoteException, NotBoundException, MalformedURLException {
		Remote x =  Naming.lookup("rmi://" + registry_host + "/" + name );
		System.out.println("X ist " + x.getClass().toString());
		
		return (EndPunkt) x;
		
	}

	/* Meldet dem übergebenen peer den eigenen Namen */
	public void registriereBei(EndPunkt endpunkt) throws RemoteException {		
		endpunkt.sende(new Registrierung(name));
	}

	public void sende(Nachricht n)  {
		System.out.println("Übertrage Nachricht " + n);
		try {
			nachrichten.put(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(99);
		}			
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
