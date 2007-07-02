package pd.regelsystem;

/**
 * Abnehmer von ZugEingaben, die geliefert werden.
 * 
 * Dieser kann mit den ZugEingaben machen, was er will. Nach jeder "Lieferung"
 * muss er dem Lieferer sagen, ob er noch mehr ZugEingaben entgegennehmen will
 * oder nicht.
 */
public interface ZugEingabeAbnehmer {
	/**
	 * Nehme eine ZugEingabe entgegen und mache irgendwas damit.
	 * 
	 * @param zugEingabe gelieferte ZugEingabe
	 * @return true, wenn keine weiteren ZugEingaben erwünscht
	 */
	public boolean nehmeEntgegen(ZugEingabe zugEingabe);
}
