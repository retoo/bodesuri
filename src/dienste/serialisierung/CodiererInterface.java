package dienste.serialisierung;

public interface CodiererInterface {

	/**
	 * Speichert einen Code und das zugehörige codierbare Objekt.
	 * 
	 * @param code Eindeutiger Code des Objekts
	 * @param objekt Zugehöriges Objekt, welches codierbar ist
	 */
	public abstract void speichere(String code, CodierbaresObjekt objekt);

	/**
	 * Gibt von einem Code das zugehörige Objekt zurück.
	 * 
	 * @param code Code des Objekts
	 * @return Objekt, das mit dem Code gespeicher wurde
	 */
	public abstract CodierbaresObjekt get(String code);

}
