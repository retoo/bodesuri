package spielplatz.hilfsklassen;

public class Registrierung extends Nachricht {
	public String name;

	public Registrierung(String txt, String name) {
		super(txt);
		this.name = name;
	}
}
