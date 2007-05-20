import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pd.brett.Feld;
import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class SerialisierungTest extends ProblemDomainTestCase {
	private Feld feld1;
	private Feld feld2;
	
	protected void setUp() throws Exception {
		super.setUp();
		feld1 = brett.getBankFeldVon(spieler1);
		lagerFeld1.versetzeFigurAuf(feld1);
		feld2 = feld1.getNaechstes();
	}
	
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
		assertEquals(feld1, durchSerialisierung(feld1));
	}
	
	public void testBewegungSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung original = new Bewegung(feld1, feld2);
		Bewegung neu = (Bewegung) durchSerialisierung(original);

		assertEquals(feld1, neu.getStart());
		assertEquals(feld2, neu.getZiel());
	}
	
	public void testZugSerialisieren()
			throws IOException, ClassNotFoundException {
		Bewegung bewegung = new Bewegung(feld1, feld2);
		ZugEingabe ze = new ZugEingabe(spieler1, kartenGeber.getKarte(), bewegung);
		
		ZugEingabe ze2 = (ZugEingabe) durchSerialisierung(ze);
		assertEquals(ze.getSpieler(), ze2.getSpieler());
		assertEquals(ze.getBewegung().getStart(),
		             ze2.getBewegung().getStart());
		assertEquals(ze.getBewegung().getZiel(),
		             ze2.getBewegung().getZiel());
	}
	
	public void testKarteSerialisierung()
			throws IOException, ClassNotFoundException {
		for (Karte karte : kartenGeber.getKarten(110)) {
			assertEquals(karte, durchSerialisierung(karte));
		}
	}
}
