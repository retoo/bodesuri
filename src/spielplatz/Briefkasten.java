package spielplatz;

import java.rmi.Remote;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.Nachricht;

public interface Briefkasten extends Remote {
	void sende(Nachricht n) throws RemoteException;
}