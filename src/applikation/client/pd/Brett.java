package applikation.client.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

public class Brett {
	private Vector<Feld> alleFelder = new Vector<Feld>();
	private IdentityHashMap<pd.brett.Feld, Feld> feldMap;

	public Brett(pd.brett.Brett brett) {
		feldMap = new IdentityHashMap<pd.brett.Feld, Feld>();

		for (pd.brett.Feld feld :  brett.getAlleFelder()) {
			Feld appFeld = new Feld(feld);
			alleFelder.add(appFeld);
			feldMap.put(feld, appFeld);
		}
	}

	public List<Feld> getAlleFelder() {
	    return alleFelder;
    }

	public Feld getFeld(pd.brett.Feld f) {
		return feldMap.get(f);
	}

}
