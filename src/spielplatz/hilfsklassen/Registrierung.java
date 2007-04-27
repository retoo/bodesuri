package spielplatz.hilfsklassen;

import spielplatz.Briefkasten;

public class Registrierung extends Nachricht {
	private static final long serialVersionUID = -495776719176123894L;

	public String name;
	public Briefkasten briefkasten;

	public Registrierung(String name, Briefkasten briefkasten) {
		this.name = name;
		this.briefkasten = briefkasten;
	}
}