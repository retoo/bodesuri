package pd.regelsystem;

import java.util.List;
import java.util.Vector;



/**
 * ZugEingabeAbnehmer, der alle ZugEingaben sammelt.
 */
public class ZugEingabeSammler implements ZugEingabeAbnehmer {
	private List<ZugEingabe> zugEingaben = new Vector<ZugEingabe>();

	public boolean nehmeEntgegen(ZugEingabe zugEingabe) {
		zugEingaben.add(zugEingabe);
		return false;
    }

	/**
	 * @return alle ZugEingaben, die gesammelt wurden
	 */
	public List<ZugEingabe> getZugEingaben() {
		return zugEingaben;
	}
}
