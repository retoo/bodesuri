
public class KartenGeberTest extends ProblemDomainTestCase {
	public void testMischen() {
		
	}
	
	public void testWechseln() {
		
	}
	
	public void testGetKarte() {		
		// Verhalten bei erneutem Mischeln
		for (int i=0; i<=55; ++i) {
			assertNotNull(kartenGeber.getKarte());
		}
		
		// Es darf nicht 2 x hintereinander das identische Objekt gezogen werden
		assertNotSame(kartenGeber.getKarte(), kartenGeber.getKarte());
	}
}
