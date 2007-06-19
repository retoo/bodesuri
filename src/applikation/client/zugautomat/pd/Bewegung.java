package applikation.client.zugautomat.pd;

import pd.zugsystem.Weg;
import applikation.client.pd.Feld;

public class Bewegung {
	public final Feld start;
	public final Feld ziel;
	private pd.zugsystem.Bewegung bewegung;
	
	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel = ziel;
		this.bewegung = new pd.zugsystem.Bewegung(start.getFeld(), ziel.getFeld());
	}

	public int getWegLaenge() {
	    return getWeg().size() -1;
    }
	
	public pd.zugsystem.Bewegung getBewegung() {
		return bewegung;
	}

	public Weg getWeg() {
	    return bewegung.getWeg();
    }

}
