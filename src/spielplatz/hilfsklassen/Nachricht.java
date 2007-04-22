package spielplatz.hilfsklassen;

import java.io.Serializable;

public class Nachricht implements Serializable {
	private static final long serialVersionUID = -7867242348071980966L;
	
	public String nachricht;

	public Nachricht(String txt) {
		nachricht = txt;
	}
	
	public String toString() {
		return nachricht;		
	}
}
