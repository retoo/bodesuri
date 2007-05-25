package applikation.server.zustaende;

import java.util.Vector;

import applikation.server.BodesuriServer;
import applikation.server.Spieler;
import applikation.server.nachrichten.BeitrittsBestaetigung;
import applikation.server.nachrichten.SpielBeitreten;
import dienste.automat.Zustand;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.VerbindungWegException;

public class EmpfangeSpieler extends AktiverServerZustand {
	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {

		Spieler spieler = new Spieler(absender, beitreten.spielerName);
		
		Vector<Spieler> spielers = automat.spielers;
		
		automat.spielers.add(spieler);

		String[] spielers_str = new String[BodesuriServer.MAXSPIELER];

		for (int i = 0; i < spielers.size(); i++)
			spielers_str[i] = spielers.get(i).spielerName;

		try {
	        spieler.endpunkt.sende(new BeitrittsBestaetigung(spielers_str));
        } catch (VerbindungWegException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }

		String msg = "Neuer Spieler " + spieler.spielerName + ". Noch "
		             + (BodesuriServer.MAXSPIELER - spielers.size()) + " Spieler nötig.";
		try {
	        automat.broadcast(msg);
        } catch (VerbindungWegException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }

		System.out.println("Neuer Spieler: " + spieler);

		if (spielers.size() == BodesuriServer.MAXSPIELER) {
			return automat.getZustand(null);
		} else {
			return automat.getZustand(null);
		}
	}
}
