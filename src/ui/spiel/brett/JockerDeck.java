package ui.spiel.brett;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ui.spiel.SpielView;
import ui.spiel.karten.KarteMouseAdapter;
import ui.spiel.karten.KartenAuswahl;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Karten;
import dienste.observer.ListChangeEvent;
import dienste.observer.ListChangeEvent.ListChangeType;

public class JockerDeck extends JPanel implements Observer {
	public JPanel jokerView;

	public JockerDeck(SpielView view, Steuerung steuerung, Karten karten) {

		KartenAuswahl kartenAuswahl = new KartenAuswahl();
		kartenAuswahl.setVisible(false);
		add(kartenAuswahl);

		KarteMouseAdapter karteMouseAdapter = new KarteMouseAdapter(steuerung);
		jokerView = new JokerView(view, karteMouseAdapter, kartenAuswahl, this);
		view.setGlassPane(jokerView);

		karten.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.CHANGED) {
				if (change.changedObject.toString() == "Joker") {
					jokerView.setVisible(true);
				}
			}
		}
	}
}
