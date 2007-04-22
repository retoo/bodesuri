package spielplatz;

import java.util.concurrent.SynchronousQueue;

import spielplatz.hilfsklassen.Nachricht;

public class NachrichtenEmpfänger implements NachrichtenEmpfaengerInterface  {
	/* a Synced queue is kinda stupid here as .. wieso schreib ich das überhaupt 
	 * auf english... ach.. schaut doch selber nach :) 
	 */
	SynchronousQueue<Nachricht> nachrichten = new SynchronousQueue<Nachricht>();

	public NachrichtenEmpfänger() {
		super();
	}

	public void sendeNachricht(Nachricht n)  {
			try {
				nachrichten.put(n);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
	}
	
	protected Nachricht getNächsteNachricht() throws InterruptedException {
		return nachrichten.take();
	}

}