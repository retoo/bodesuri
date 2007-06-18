package applikation.client.pd;

import java.util.List;
import java.util.Vector;

public class Brett {
	private Vector<Feld> alleFelder = new Vector<Feld>();

	public Brett (pd.brett.Brett brett) {
		for (pd.brett.Feld feld :  brett.getAlleFelder()) {
			alleFelder.add(new Feld(feld));
		}
	}

	public List<Feld> getAlleFelder() {
	    return alleFelder;
    }

}
