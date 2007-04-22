package spielplatz.hilfsklassen;

public class Registrierung extends Nachricht {
	private static final long serialVersionUID = -495776719176123894L;

	public String name;

	public Registrierung(String txt, String name) {
		super(txt);
		this.name = name;
	}
}