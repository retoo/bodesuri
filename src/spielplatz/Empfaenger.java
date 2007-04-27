package spielplatz;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.hilfsklassen.Nachricht;

public class Empfaenger {
	public Briefkasten stub;
	private String registry_host;
	private LinkedBlockingQueue<Nachricht> nachrichten = new LinkedBlockingQueue<Nachricht>();
	public String name;



	public Empfaenger(String name, boolean createRegistry, String registry_host) throws RemoteException, AlreadyBoundException, MalformedURLException {
		//System.setProperty("java.security.policy", "rmi.policy");
		System.setSecurityManager(new SecurityManager());
		
		this.name = name;
		this.registry_host = registry_host;

		stub = new BriefKastenImpl(nachrichten);
		
		if (createRegistry) 
			LocateRegistry.createRegistry(1099);
		
		UnicastRemoteObject.exportObject(stub, 0);
		
		String url = "rmi://" + registry_host + "/" + name;
		System.out.println("url: " + url);
	
		System.out.println("Rebinding start");
		if (createRegistry)  {
			Naming.rebind(url, stub);
		}
			
		
		System.out.println("Rebinding finished");

	}

	/* liefert den stub zu einem gewissen namen */
	public EndPunkt schlageNach(String name) throws RemoteException, NotBoundException, MalformedURLException {
		String url = "rmi://" + registry_host + "/" + name;
		System.out.println("url: " + url);
		Briefkasten bk =  (Briefkasten) Naming.lookup(url );
		EndPunkt n = new EndPunkt(this.name, bk);
		
		return n;
	}

	public EndPunkt schlageNach(Briefkasten briefkasten) {
		return new EndPunkt(this.name, briefkasten);
	}


	protected Nachricht getNaechsteNachricht() {
		return getNaechsteNachricht(true);
	}
	
	protected Nachricht getNaechsteNachricht(boolean blocking) {
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

}
