package PD.zugsystem;

public class Bewegung {
	private Feld start;
	private Feld ziel;
	
	public Bewegung(Feld start, Feld ende) {
		this.start = start;
		this.ziel  = ende;
	}
	
	public Feld getStart() {
		return start;
	}
	
	public Feld getZiel() {
		return ziel;
	}
}
