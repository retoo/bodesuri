import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;

public class SerialisierungTest extends ProblemDomainTestCase {
	private Object durchSerialisierung(Object original)
			throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(baos);
		oos.writeObject(original);
		oos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois;
		ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
	
	public void testFeldSerialisieren()
			throws IOException, ClassNotFoundException {
		assertEquals(bankFeld, durchSerialisierung(bankFeld));
	}
	
	public void testBewegungSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung original = new Bewegung(bankFeld, zielFeld);
		Bewegung neu = (Bewegung) durchSerialisierung(original);

		assertEquals(bankFeld, neu.getStart());
		assertEquals(zielFeld, neu.getZiel());
	}
	
	public void testZugSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung bewegung = new Bewegung(bankFeld, zielFeld);
		Zug zug = new Zug(spieler, kartenGeber.getKarte(), bewegung);
		
		Zug zug2 = (Zug) durchSerialisierung(zug);
		assertEquals(zug.getSpieler(), zug2.getSpieler());
		assertEquals(zug.getBewegung().getStart(),
		             zug2.getBewegung().getStart());
		assertEquals(zug.getBewegung().getZiel(),
		             zug2.getBewegung().getZiel());
	}
	
	public void testKarteSerialisierung()
			throws IOException, ClassNotFoundException {
		for (Karte karte : kartenGeber.getKarten(108)) {
			assertEquals(karte, durchSerialisierung(karte));
		}
	}
}
