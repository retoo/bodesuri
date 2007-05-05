package spielplatz;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class FeldNummerierung {
	private static Map<Feld, Integer> vonFeldZuInteger;
	private static Map<Integer, Feld> vonIntegerZuFeld;
	static {
		vonFeldZuInteger = new HashMap<Feld, Integer>();
		vonIntegerZuFeld = new HashMap<Integer, Feld>();
	}
	
	public static void speichere(Feld f, int i) {
		vonFeldZuInteger.put(f, i);
		vonIntegerZuFeld.put(i, f);
	}
	
	public static Feld getFeld(int i) {
		return vonIntegerZuFeld.get(i);
	}
	
	public static int getInt(Feld f) {
		return vonFeldZuInteger.get(f);
	}
}

class Bewegung implements Serializable {
	private static final long serialVersionUID = 1L;

	public Feld start;
	public Feld ziel;

	private void writeObject(ObjectOutputStream out) throws IOException {
		System.out.println("writeObject");
		out.write(FeldNummerierung.getInt(start));
		out.write(FeldNummerierung.getInt(ziel));
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		System.out.println("readObject");
		/*
		 * Scheisse, dass FeldNummerierung global sein muss :/. Es geht aber
		 * nicht anders, denn ich kann dem readObject keine Argumente übergeben
		 * und das neue Bewegung-Objekt enthält noch keine Instanzvariablen oder
		 * könnte nur serialisierbare Instanzvariablen enthalten.
		 * 
		 * Ne andere Möglichkeit wäre, eigene ObjectOutputStream- und
		 * ObjectInputStream-Klassen zu schreiben, hmm...
		 */
		start = FeldNummerierung.getFeld(in.read());
		ziel  = FeldNummerierung.getFeld(in.read());
	}
}

class Feld {
}

public class Serialisierung {
	public static void main(String[] args) {
		Feld start = new Feld();
		Feld ziel  = new Feld();
		FeldNummerierung.speichere(start, 0);
		FeldNummerierung.speichere(ziel, 1);
		
		Bewegung original = new Bewegung();
		original.start = start;
		original.ziel  = ziel;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(original);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
        }

		byte[] ausgabe = baos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(ausgabe);
		ObjectInputStream ois;
		Bewegung neu = null;
		try {
			ois = new ObjectInputStream(bais);
			neu = (Bewegung) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(ausgabe.length);
		System.out.println(start == neu.start);
		System.out.println(ziel  == neu.ziel);
	}
}
