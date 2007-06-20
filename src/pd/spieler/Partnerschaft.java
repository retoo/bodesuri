package pd.spieler;

import java.io.Serializable;


public class Partnerschaft implements Serializable {
	Spieler spielerA;
	Spieler spielerB;

	public Partnerschaft(Spieler spielerA, Spieler spielerB) {
		this.spielerA = spielerA;
		this.spielerB = spielerB;
	}

	public boolean istFertig() {
		if ( spielerA.istFertig() && spielerB.istFertig() ) {
			return true;
		}
		
		return false;
	}
	
	public Spieler istEndModusMoeglich() {
		boolean aFertig = spielerA.istFertig();
		boolean bFertig = spielerB.istFertig();
		
		if (aFertig && !bFertig) {
			return spielerA;
		} else if (!aFertig && bFertig) {
			return spielerB;
		} else return null;
	}
	
	public boolean istMitglied(Spieler spieler) {
		return (spieler == spielerA || spieler == spielerB) ? true : false;
	}
	
	public Spieler getPartner(Spieler spieler) {
		return (spieler == spielerA) ? spielerB : spielerA;
	}
}
