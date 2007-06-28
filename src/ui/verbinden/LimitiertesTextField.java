package ui.verbinden;

import java.awt.Color;
import java.awt.event.FocusEvent;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Helferklasse für die Textfelder, die schon Formatierungshilfen und
 * Eingabeprüfung beinhaltet.
 */
class LimitiertesTextField extends JTextField {
	private final Color FOCUS_GAINED = new Color(220, 223, 228);
	private final Color FOCUS_LOST = new Color(255, 255, 255);

	/**
	 * Erstellt ein TextField, das auf eine Anzahl angegebener Zeichen limitiert
	 * wird (durch setzen des Documents).
	 */
	public LimitiertesTextField(String text, final int limit) {
		super();
		setDocument(new PlainDocument() {
			public void insertString(int offset, String text, AttributeSet attr)
					throws BadLocationException {
				if (text.length() == 0)
					return;
				if (getLength() + text.length() > limit) {
					JOptionPane.showMessageDialog(null, "Zu viele Zeichen");
				} else {
					super.insertString(offset, text, attr);
				}
			}
		});
		setText(text);
	}

	/**
	 * Beim betreten und verlassen eines TextField wird diese Methode
	 * ausgeführt.
	 * 
	 * @see javax.swing.JFormattedTextField#processFocusEvent
	 * @param e Der Focus-Event.
	 */
	protected void processFocusEvent(FocusEvent e) {
		super.processFocusEvent(e);
		switch (e.getID()) {
		case FocusEvent.FOCUS_GAINED:
			setBackground(FOCUS_GAINED);
			break;
		case FocusEvent.FOCUS_LOST:
			setBackground(FOCUS_LOST);
			break;
		}

	}
}
