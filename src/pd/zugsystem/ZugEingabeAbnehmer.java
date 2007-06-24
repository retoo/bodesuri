package pd.zugsystem;


/**
 * Abnehmer von ZugEingaben, die geliefert werden.
 * 
 * Er macht mit den ZugEingaben was er will und sagt dem Lieferer, wann er keine
 * ZugEingaben mehr entgegennehmen mehr will.
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
