package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Server extends NachrichtenEmpfänger {

	public Server() {}

	public static void main(String args[]) throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException  {
		Server server = new Server();
		server.run();
	}

	private void run() throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException {
		NachrichtenEmpfängerInterface stub = (NachrichtenEmpfängerInterface) UnicastRemoteObject.exportObject(this, 0);
		Registry registry = LocateRegistry.createRegistry(1099); 
		registry.bind("server", stub);

		Nachricht n;
		
		Vector<NachrichtenEmpfängerInterface> clients = new Vector<NachrichtenEmpfängerInterface>();
		
		
		while ( ( n = getNächsteNachricht()) != null) {
			if (n instanceof Registrierung) {
				Registrierung reg = (Registrierung) n;
				
				NachrichtenEmpfängerInterface client = (NachrichtenEmpfängerInterface) registry.lookup(reg.name);
				
				clients.add(client);
			} else {
				System.out.println("Got message " + n);
				
				for (NachrichtenEmpfängerInterface client : clients) {
					client.sendeNachricht(n);
				}				
			}
		}
		
		System.out.println("out of scope");
	}
}
