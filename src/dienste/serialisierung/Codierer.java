package dienste.serialisierung;

import java.util.HashMap;
import java.util.Map;

public class Codierer {
	private Map<String, CodierbaresObjekt> objekte;
	
	public Codierer() {
		objekte = new HashMap<String, CodierbaresObjekt>();
	}
	
	public void speichere(CodierbaresObjekt objekt) {
		objekte.put(objekt.getCode(), objekt);
	}
	
	public Object get(String code) {
		return objekte.get(code);
	}
}
