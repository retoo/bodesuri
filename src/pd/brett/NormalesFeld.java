package pd.brett;

/**
 * Normales Feld, welches zwischen den BankFeldern liegt und auf einem Brett am
 * meisten vorkommt.
 */
public class NormalesFeld extends Feld {
	private static final long serialVersionUID = 1L;

	/**
	 * Erstellt ein normales Feld.
	 * 
	 * @param nummer Feldnummer, siehe {@link Feld#Feld(int)}
	 */
	public NormalesFeld(int nummer) {
		super(nummer);
	}
}
