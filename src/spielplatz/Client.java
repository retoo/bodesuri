package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Client extends NachrichtenEmpfänger{

	private Client() {}

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException, InterruptedException {
		Client c = new Client(); 
		
		c.run();
	}

	private void run() throws RemoteException, NotBoundException, AlreadyBoundException, InterruptedException {
		String host = "localhost";

		Registry registry = LocateRegistry.getRegistry(host);		
		NachrichtenEmpfaengerInterface server = (NachrichtenEmpfaengerInterface) registry.lookup("server");
		
		NachrichtenEmpfaengerInterface stub = (NachrichtenEmpfaengerInterface) UnicastRemoteObject.exportObject(this, 0);
		registry.bind("client", stub);
		
		server.sendeNachricht(new Registrierung("ich bin hier", "client"));
		server.sendeNachricht(new Nachricht("hallo du"));
		Nachricht n;
		
		while ( ( n = getNächsteNachricht()) != null) {
			System.out.println("Got message " + n);		
			
		}
		
		
	}
}
