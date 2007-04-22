package spielplatz;

import java.rmi.Remote;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.Nachricht;

public interface NachrichtenEmpfaengerInterface extends Remote {
	void sendeNachricht(Nachricht n) throws RemoteException;
}