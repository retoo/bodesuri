package dienste.serialisierung;

import java.io.ObjectStreamException;
import java.io.Serializable;

abstract public class CodierbaresObjekt implements Serializable {
	abstract public String getCode();
	
	protected Object writeReplace() throws ObjectStreamException {
		return new CodiertesObjekt(getCode());
	}
}
