package ui.spiel.karten;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;

import pd.karten.Karte;
import ui.GUIController;
import dienste.observer.ListChangeEvent;
import dienste.observer.ListChangeEvent.ListChangeType;
import dienste.observer.ObservableList;

/**
 * JPanel, das DeckView wird zur Darstellung der Karten verwendet. Hier werden
 * die Karten der Spieler verwaltet und dargestellt.
 */
public class DeckView extends JPanel implements Observer {
	private ObservableList<Karte> karten;
	private Vector<KarteView> karteViews;

	public DeckView(GUIController controller,
	                KartenAuswahlView kartenAuswahlView) {
		setLayout(null);
		
		Dimension groesse = new Dimension(190, 340);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);

		KartenAuswahl kartenAuswahl = new KartenAuswahl(new Point());
		KarteMouseAdapter karteMouseAdapter =
			new KarteMouseAdapter(controller, kartenAuswahlView, kartenAuswahl);
		
		karteViews = new Vector<KarteView>();
		for (int i = 0; i < 6; ++i) {
			int x = i % 2;
			int y = i / 2;
			Point position = new Point(10 + x*90, 10 + y*110);
			KarteView kv = new KarteView(position, karteMouseAdapter);
			karteViews.add(kv);
			add(kv);
		}
		
		karten = controller.getSpielerIch().getKarten();
		karten.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.ADDED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == null) {
						kv.setKarte((Karte) change.changedObject);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.REMOVED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == change.changedObject) {
						kv.setKarte(null);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.EVERYTHING) {
				for (int i = 0; i < karten.size(); ++i) {
					karteViews.get(i).setKarte(karten.get(i));
				}
			}
		}
    }
}
