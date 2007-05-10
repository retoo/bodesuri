package pd.zugsystem;

import java.io.Serializable;

import pd.brett.Feld;

public class Bewegung implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
