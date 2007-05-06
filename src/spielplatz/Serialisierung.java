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
	/* Gibt's ne bijektive Map? */
	private static Map<Feld, Integer> vonFeldZuCode;
	private static Map<Integer, Feld> vonCodeZuFeld;
	
	static {
		vonFeldZuCode = new HashMap<Feld, Integer>();
		vonCodeZuFeld = new HashMap<Integer, Feld>();
	}
	
	public static void speichere(Feld feld, int code) {
		vonFeldZuCode.put(feld, code);
		vonCodeZuFeld.put(code, feld);
	}
	
	public static FeldCode getFeldCode(Feld feld) {
		return new FeldCode(vonFeldZuCode.get(feld));
	}
	
	public static Feld getFeld(int code) {
		return vonCodeZuFeld.get(code);
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

class Feld implements Serializable {
	private static final long serialVersionUID = 1L;

	Object writeReplace() throws ObjectStreamException {
		return Codierer.getFeldCode(this);
	}
}

class FeldCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int code;
	
	public FeldCode(int code) {
		this.code = code;
	}
	
	Object readResolve() throws ObjectStreamException {
		return Codierer.getFeld(code);
	}
}

public class Serialisierung {
	public static void main(String[] args)
			throws IOException, ClassNotFoundException {
		Feld start = new Feld();
		Feld ziel  = new Feld();
		
		Codierer.speichere(start, 0);
		Codierer.speichere(ziel, 1);
		
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
