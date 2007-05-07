import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pd.zugsystem.Bewegung;

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
}
