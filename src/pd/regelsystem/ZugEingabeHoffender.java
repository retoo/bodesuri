package pd.regelsystem;

/**
 * ZugEingabeAbnehmer, der nur auf die erste ZugEingabe hofft und dann keine
 * mehr entgegennimmt.
 */
public class ZugEingabeHoffender implements ZugEingabeAbnehmer {
	private boolean hatBekommen = false;

	public boolean nehmeEntgegen(ZugEingabe zugEingabe) {
		hatBekommen = true;
		return true;
	}

	/**
	 * @return true, wenn mal eine ZugEingabe geliefert wurde
	 */
	public boolean hatBekommen() {
		return hatBekommen;
	}
}