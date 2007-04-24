package spielplatz;

import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.hilfsklassen.Nachricht;

public class BriefKastenImpl implements Briefkasten {

	private LinkedBlockingQueue<Nachricht> nachrichten;

	public BriefKastenImpl(LinkedBlockingQueue<Nachricht> nachrichten) {
		this.nachrichten = nachrichten;
	}

	public void sende(Nachricht n) throws RemoteException {
		System.out.println("Übertrage Nachricht " + n);
		try {
			nachrichten.put(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(99);
		}
	}

}
