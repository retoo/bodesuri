package spielplatz;

import java.rmi.RemoteException;

import spielplatz.hilfsklassen.Nachricht;

public class EndPunkt {

	private String name;
	private Briefkasten briefkasten;

	public EndPunkt(String name, Briefkasten bk) {
		this.name = name;
		this.briefkasten = bk;
	}

	
	public void sende(Nachricht n) throws RemoteException {
		if (n.getAbsender() == null) {
			n.setAbsender(name);
		}
		
		briefkasten.sende(n);
	}
}
