package pd.spiel.spieler;

import java.io.Serializable;

public class Partnerschaft implements Serializable {
	private final Spieler spielerA;
	private final Spieler spielerB;

	public Partnerschaft(Spieler spielerA, Spieler spielerB) {
		this.spielerA = spielerA;
		this.spielerB = spielerB;
	}

	public boolean istFertig() {
		return spielerA.istFertig() && spielerB.istFertig();
	}

	public Spieler getSpielerA() {
		return spielerA;
	}

	public Spieler getSpielerB() {
		return spielerB;
	}

	public String toString() {
		return spielerA + " und " + spielerB;
	}
}
