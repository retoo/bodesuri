package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.client.zugautomat.ZugAutomat;
import dienste.automat.Automat;
import dienste.automat.zustaende.PassiverZustand;

public abstract class PassiverZugZustand extends PassiverZustand {
	protected ZugAutomat automat;
	protected Controller controller;
	
	public void setAutomat(Automat automat) {
		this.automat = (ZugAutomat) automat;

	}
}
