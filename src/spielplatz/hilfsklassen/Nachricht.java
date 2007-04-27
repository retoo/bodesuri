package spielplatz.hilfsklassen;

import java.io.Serializable;

public abstract class Nachricht implements Serializable {
	private String absender;

	public void setAbsender(String name) {
		absender = name;
	}

	public String getAbsender() {
		return absender;
	}
	

	public String toString() {
		return "(" + absender + ")";
	}
}