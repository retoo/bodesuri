package applikation.client.pd;

import java.util.List;
import java.util.Vector;

public class Brett {
	pd.brett.Brett brett;

	private Vector<Feld> alleFelder = new Vector<Feld>();

	public Brett (pd.brett.Brett brett) {
		/* TODO: brett is never read (-reto)*/
		this.brett = brett;
		for (pd.brett.Feld feld :  brett.getAlleFelder()) {
			alleFelder.add(new Feld(feld));
		}
	}

	public List<Feld> getAlleFelder() {
	    return alleFelder;
    }

}
