package dienste.netzwerk;

/**
 * Interface für einen Briefkasten der durch den Empfänger für
 * für das ablegen eingehender Nachrichten verwendet werden kann.
 *
 */
public interface BriefKastenInterface {
	/**
	 * Wirft den übergebenen Brief ein
	 * 
	 * @param brief einzuwerfendr Brief
	 */
	public void einwerfen(Brief brief);
}
