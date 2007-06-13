package dienste.automat.zustaende;

/**
 * Exception die meldet, dass der zu varbeitende Event zwar bekannt ist aber der
 * dafür zuständige Handler nicht implementiert ist.
 *
 * Tritt diese Exception auf sollte geprüft werden ob die zuständige handle()
 * Methoden in dem broffenen Zustand implementiert wurde.
 *
 */
public class KeinUebergangException extends RuntimeException {
	/**
	 * Erstellt eine KeinUebergangException
	 * @param msg Fehlermeldung
	 */
	public KeinUebergangException(String msg) {
		super(msg);
	}
}
