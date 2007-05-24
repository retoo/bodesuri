package applikation.server;

/* Huere michi du..
 */
public class Klicks {

	private int limit;
	private int wert;

	public Klicks(int limit) {
		wert = 0;
	    this.limit = limit;
    }
	
	public int klick() {
		int v = wert;
		wert = (wert + 1) % limit;
		
		return v;
	}
}
