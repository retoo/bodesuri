import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pd.ProblemDomainTestCase;
import pd.regelsystem.ZugEingabe;
import pd.regelsystem.karten.Karte;
import pd.serialisierung.BodesuriCodiertesObjekt;
import pd.spiel.brett.Feld;
import pd.zugsystem.Bewegung;
import dienste.serialisierung.UnbekannterCodeException;

/**
 * Führt Tests für die Serialisierungs-Funktionen der Problem-Domain durch,
 * welche für die Netzwerkkommunikation verwendet wird.
 */
public class SerialisierungTest extends ProblemDomainTestCase {
	private Feld feld1;

	private Feld feld2;

	protected void setUp() {
		super.setUp();
		feld1 = bank(0);
		feld2 = feld1.getNaechstes();
		lager(0).versetzeFigurAuf(feld1);
	}

	/**
	 * Prüft, ob ein durch die Serialisierung geschicktes Feld noch identisch
	 * mit seinem Original ist.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void testFeldSerialisieren() throws IOException,
			ClassNotFoundException {
		assertEquals(feld1, durchSerialisierung(feld1));
	}

	/**
	 * Prüft, ob eine durch die Serialisierung geschickte Bewegung noch
	 * identisch mit ihrem Original ist.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void testBewegungSerialisieren() throws IOException,
			ClassNotFoundException {
		Bewegung original = new Bewegung(feld1, feld2);
		Bewegung neu = (Bewegung) durchSerialisierung(original);

		assertEquals(feld1, neu.start);
		assertEquals(feld2, neu.ziel);
	}

	/**
	 * Prüft, ob eine durch die Serialisierung geschickte Zugeingabe noch
	 * identisch mit dem Original ist.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void testZugEingabeSerialisieren() throws IOException,
			ClassNotFoundException {
		Bewegung bewegung = new Bewegung(feld1, feld2);
		ZugEingabe ze = new ZugEingabe(spieler(0), kartenGeber.getKarte(),
				bewegung);

		ZugEingabe ze2 = (ZugEingabe) durchSerialisierung(ze);
		assertEquals(ze.getSpieler(), ze2.getSpieler());
		assertEquals(ze.getBewegung().start, ze2.getBewegung().start);
		assertEquals(ze.getBewegung().ziel, ze2.getBewegung().ziel);
	}

	/**
	 * Prüft, ob die Karten noch identisch sind, wenn sie durch die
	 * Serialisierung geschickt wurden.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void testKarteSerialisierung() throws IOException,
			ClassNotFoundException {
		for (Karte karte : kartenGeber.getKarten(110)) {
			assertEquals(karte, durchSerialisierung(karte));
		}
	}

	/**
	 * Prüft, ob <Code>UnbekannteCodeException</Code> korrekt geworfen wird.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void testUnbekannterCodeException() throws IOException,
			ClassNotFoundException {
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

	/**
	 * Schickt ein Objekt durch die Serialisierung und liesst es anschliessend
	 * wieder aus.
	 * 
	 * @param original
	 *            Objekt, das serialisert werden soll.
	 * @return Aus Serialisierung zurückgelesenes Objek.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Object durchSerialisierung(Object original) throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(original);
		oos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
}
