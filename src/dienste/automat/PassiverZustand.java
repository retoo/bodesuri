package dienste.automat;

import dienste.automat.zustaende.Zustand;
import dienste.automat.zustaende.ZustandsInterface;

public interface PassiverZustand extends ZustandsInterface {
	Class<? extends Zustand> handle();
}
