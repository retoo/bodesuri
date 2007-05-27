package dienste.automat;

/**
 * Exception die geworfen wird wenn der Zustandsautomat für einen bestimmten
 * Event keinen Hanlder definiert hat.
 * 
 * Tritt diese Exception auf sollte die handle(Event) Methode des Zustands bzw.
 * der Basis-Klasse des Zustandes geprüft werden.
 *
 */
public class UnbekannterEventException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Erstellt eine KeinUebergangException
	 * @param msg Fehlermeldung
	 */
	public UnbekannterEventException(String msg) {
	    super(msg);
    }
}
