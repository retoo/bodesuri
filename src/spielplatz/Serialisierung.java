package spielplatz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Codierer {
	private Map<Feld, Integer> vonFeldZuCode;
	private Map<Integer, Feld> vonCodeZuFeld;
	
	public Codierer() {
		vonFeldZuCode = new HashMap<Feld, Integer>();
		vonCodeZuFeld = new HashMap<Integer, Feld>();
	}
	
	public static Codierer von(ObjectOutputStream out) {
		if (!(out instanceof ObjectOutputStreamMitCodierer)) {
			/* TODO: Exception oder so */
		}
		ObjectOutputStreamMitCodierer s = (ObjectOutputStreamMitCodierer) out;
		return s.getCodierer();
	}
	
	public static Codierer von(ObjectInputStream in) {
		if (!(in instanceof ObjectInputStreamMitCodierer)) {
			/* TODO: Exception oder so */
		}
		ObjectInputStreamMitCodierer s = (ObjectInputStreamMitCodierer) in;
		return s.getCodierer();
	}
	
	public void speichere(Feld feld, int code) {
		vonFeldZuCode.put(feld, code);
		vonCodeZuFeld.put(code, feld);
	}
	
	public Integer getCode(Feld feld) {
		return vonFeldZuCode.get(feld);
	}
	
	public Feld getFeld(int code) {
		return vonCodeZuFeld.get(code);
	}
}

class ObjectOutputStreamMitCodierer extends ObjectOutputStream {
	private Codierer codierer;
	
	public ObjectOutputStreamMitCodierer(OutputStream os, Codierer c)
			throws IOException {
		super(os);
		codierer = c;
	}
	
	public Codierer getCodierer() {
		return codierer;
	}
}

class ObjectInputStreamMitCodierer extends ObjectInputStream {
	private Codierer codierer;
	
	public ObjectInputStreamMitCodierer(InputStream is, Codierer c)
			throws IOException {
		super(is);
		codierer = c;
	}
	
	public Codierer getCodierer() {
		return codierer;
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

	private void writeObject(ObjectOutputStream out) throws IOException {
		Codierer c = Codierer.von(out);
		out.write(c.getCode(start));
		out.write(c.getCode(ziel));
	}

	private void readObject(ObjectInputStream in) throws IOException {
		Codierer c = Codierer.von(in);
		start = c.getFeld(in.read());
		ziel  = c.getFeld(in.read());
	}

	public Feld getStart() {
		return start;
	}

	public Feld getZiel() {
		return ziel;
	}
}

class Feld {
}

public class Serialisierung {
	public static void main(String[] args) {
		Feld start = new Feld();
		Feld ziel  = new Feld();
		
		Codierer codierer = new Codierer();
		codierer.speichere(start, 0);
		codierer.speichere(ziel, 1);
		
		Bewegung original = new Bewegung(start, ziel);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStreamMitCodierer oos;
		try {
			oos = new ObjectOutputStreamMitCodierer(baos, codierer);
			oos.writeObject(original);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
        }

		byte[] ausgabe = baos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(ausgabe);
		ObjectInputStreamMitCodierer ois;
		Bewegung neu = null;
		try {
			ois = new ObjectInputStreamMitCodierer(bais, codierer);
			neu = (Bewegung) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(ausgabe.length);
		System.out.println(start == neu.getStart());
		System.out.println(ziel  == neu.getZiel());
	}
}
