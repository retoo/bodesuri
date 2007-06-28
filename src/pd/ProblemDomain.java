package pd;

import dienste.serialisierung.Codierer;
import pd.karten.KartenGeber;
import pd.serialisierung.CodiererThreads;

/**
 * Einstiegspunkt der Problem-Domain, welcher den {@link Codierer}, das
 * {@link Spiel} und den {@link KartenGeber} erstellt.
 */
public class ProblemDomain {
	private Codierer codierer;
	private Spiel spiel;
	private KartenGeber kartenGeber;

	public ProblemDomain() {
		/*
		 * Der Codierer muss zuerst erstellt werden, damit Spiel und KartenGeber
		 * ihn verwenden können.
		 */
		codierer = new Codierer();
		CodiererThreads.registriere(Thread.currentThread(), codierer);

		spiel = new Spiel();
		kartenGeber = new KartenGeber();
	}

	/**
	 * @return Codierer der Problem-Domain
	 */
	public Codierer getCodierer() {
		return codierer;
	}

	/**
	 * @return Spiel der Problem-Domain
	 */
	public Spiel getSpiel() {
		return spiel;
	}

	/**
	 * @return Kartengeber der Problem-Domain, von dem Karten bezogen werden können
	 */
	public KartenGeber getKartenGeber() {
    	return kartenGeber;
    }
}
