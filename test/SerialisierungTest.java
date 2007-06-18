import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pd.ProblemDomainTestCase;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.serialisierung.BodesuriCodiertesObjekt;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import dienste.serialisierung.UnbekannterCodeException;

public class SerialisierungTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() {
		super.setUp();
		feld1 = bank(0);
		feld2 = feld1.getNaechstes();
		lager(0).versetzeFigurAuf(feld1);
	}
	
	public void testFeldSerialisieren()
			throws IOException, ClassNotFoundException {
		assertEquals(feld1, durchSerialisierung(feld1));
	}
	
	public void testBewegungSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung original = new Bewegung(feld1, feld2);
		Bewegung neu = (Bewegung) durchSerialisierung(original);

		assertEquals(feld1, neu.start);
		assertEquals(feld2, neu.ziel);
	}
	
	public void testZugEingabeSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung bewegung = new Bewegung(feld1, feld2);
		ZugEingabe ze = new ZugEingabe(spieler(0), kartenGeber.getKarte(), bewegung);
		
		ZugEingabe ze2 = (ZugEingabe) durchSerialisierung(ze);
		assertEquals(ze.getSpieler(), ze2.getSpieler());
		assertEquals(ze.getBewegung().start,
		             ze2.getBewegung().start);
		assertEquals(ze.getBewegung().ziel,
		             ze2.getBewegung().ziel);
	}
	
	public void testKarteSerialisierung()
			throws IOException, ClassNotFoundException {
		for (Karte karte : kartenGeber.getKarten(110)) {
			assertEquals(karte, durchSerialisierung(karte));
		}
	}
	
	public void testUnbekannterCodeException()
			throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(new BodesuriCodiertesObjekt("Spieler 666"));
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		try {
			ois.readObject();
			fail("Sollte UnbekannterCodeException geben.");
		} catch (UnbekannterCodeException e) {
			assertNotNull(e);
			assertEquals("'Spieler 666' unbekannt", e.getMessage());
		}
	}
	
	private Object durchSerialisierung(Object original)
			throws IOException, ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
}
