package pd.zugsystem;

public class Bewegung {
	private Feld start;
	private Feld ziel;
	
	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel  = ziel;
	}
	
	public Feld getStart() {
		return start;
	}
	
	public Feld getZiel() {
		return ziel;
	}
}
