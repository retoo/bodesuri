package spielplatz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Codierer {
	private static Map<String, CodierbaresObjekt> objekte;
	static {
		objekte = new HashMap<String, CodierbaresObjekt>();
	}
	
	public static void speichere(CodierbaresObjekt objekt) {
		objekte.put(objekt.getCode(), objekt);
	}
	
	public static Object get(String code) {
		return objekte.get(code);
	}
}

class Bewegung implements Serializable {
	private static final long serialVersionUID = 1L;

	private Feld start;
	private Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel  = ziel;
    }

	public Feld getStart() {
		return start;
	}

	public Feld getZiel() {
		return ziel;
	}
}

abstract class CodierbaresObjekt implements Serializable {
	abstract public String getCode();
	
	protected Object writeReplace() throws ObjectStreamException {
		return new CodiertesObjekt(getCode());
	}
}

class Feld extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private int nummer;
	
	public Feld(int nummer) {
		this.nummer = nummer;
	}
	
	public String getCode() {
		return "Feld " + nummer;
	}
}

class CodiertesObjekt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public CodiertesObjekt(String code) {
		this.code = code;
	}
	
	private Object readResolve() throws ObjectStreamException {
		return Codierer.get(code);
	}
}

public class Serialisierung {
	public static void main(String[] args)
			throws IOException, ClassNotFoundException {
		Feld start = new Feld(0);
		Feld ziel  = new Feld(1);
		
		Codierer.speichere(start);
		Codierer.speichere(ziel);
		
		Bewegung original = new Bewegung(start, ziel);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(baos);
		oos.writeObject(original);
		oos.close();

		byte[] ausgabe = baos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(ausgabe);
		ObjectInputStream ois;
		ois = new ObjectInputStream(bais);
		Bewegung neu = (Bewegung) ois.readObject();

		System.out.println(ausgabe.length);
		System.out.println(start == neu.getStart());
		System.out.println(ziel  == neu.getZiel());
	}
}
