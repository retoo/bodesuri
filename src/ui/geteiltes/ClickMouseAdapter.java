package ui.geteiltes;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseAdapter, der das gleiche Klickverhalten wie ein JButton hat.
 * 
 * Bei einem JButton ist es egal, ob zwischen dem Drücken der Maustaste
 * (mousePressed) und dem Loslassen (mouseReleased) eine Bewegung stattfindet,
 * oder sogar der ursprünglich angeklickte Bereich verlassen wird (mouseExited),
 * sofern er wieder betreten wird (mouseEntered).
 * 
 * Diese Klasse implementiert das JButton-Verhalten. Eine abgeleitete Klasse
 * muss die Methode {@link #clicked} überschreiben, die bei Klicks aufgerufen
 * wird. Wenn andere Methoden wie {@link #mouseEntered} überschrieben werden,
 * sollte super aufgerufen werden.
 */
public abstract class ClickMouseAdapter extends MouseAdapter {
	private boolean aktiv;
	private Component component;

	/**
	 * Methode, die in der abgeleiteten Klasse überschrieben werden muss und bei
	 * Klicks (nach JButton-Art) aufgerufen wird.
	 * 
	 * @param e
	 *            MouseEvent des Klicks
	 */
	public abstract void clicked(MouseEvent e);

	public void mouseExited(MouseEvent e) {
		aktiv = false;
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getComponent() == component) {
			aktiv = true;
		}
	}

	public void mousePressed(MouseEvent e) {
		aktiv = true;
		component = e.getComponent();
	}

	public void mouseReleased(MouseEvent e) {
		if (aktiv) {
			clicked(e);
		}
		aktiv = false;
		component = null;
	}
}
