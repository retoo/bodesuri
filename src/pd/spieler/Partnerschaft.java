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
		boolean aFertig = spielerA.istFertig();
		boolean bFertig = spielerB.istFertig();
		
		if ( aFertig && bFertig ) {
			return true;
		} else if ( aFertig && !bFertig ) {
			spielerA.addPartnerFiguren( spielerB.getFiguren() );
		} else if ( !aFertig && bFertig ) {
			spielerB.addPartnerFiguren( spielerA.getFiguren() );
		}
		
		return false;
	}

	public boolean istMitglied(Spieler spieler) {
		return (spieler == spielerA || spieler == spielerB) ? true : false;
	}
	
	public Spieler getPartner(Spieler spieler) {
		return (spieler == spielerA) ? spielerB : spielerA;
	}
}
