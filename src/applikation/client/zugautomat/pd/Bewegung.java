package applikation.client.zugautomat.pd;

import applikation.client.pd.Feld;

public class Bewegung {
	private Feld start;
	private Feld ziel;
	
	public pd.zugsystem.Bewegung toBewegung() {
		return new pd.zugsystem.Bewegung(start.getFeld(), ziel.getFeld());
	}

	public Feld getStart() {
    	return start;
    }

	public Feld getZiel() {
    	return ziel;
    }

	public void setStart(Feld start) {
    	this.start = start;
    }

	public void setZiel(Feld ziel) {
    	this.ziel = ziel;
    }
}
