package applikation.client;

import javax.swing.JOptionPane;

import pd.karten.Karte;
import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.KarteWaehlen;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungSteht;
import applikation.client.zustaende.Ziehen;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.EndPunkt;

/**
 * Version des Automaten für den Client
 */
public class BodesuriClient extends Automat {
	/**
	 * EventQueue - Hier kommen neue Events rein.
	 */
	public EventQueue queue;
	/**
	 * EndPunkt - Verbindung zum Server
	 */
	public EndPunkt endpunkt;

	/**
	 * VerbindenView - Das Fenster zum sich zum Server zu verbinden
	 */
	public VerbindenView verbindenView;
	/**
	 * LobbyView - Das Fenster der Lobby
	 */
	public LobbyView lobbyView;
	/**
	 * BodesuriView - Das Spiel-Fenster
	 */
	public BodesuriView spielView;

	/**
	 * Das Spiel selbst
	 */
	public pd.Spiel spiel;
	/**
	 * Der Name des Spielers. Wir für gebraucht um spielerIch zu erstellen.
	 */
	public String spielerName;
	/**
	 * Repräsentiert den Spieler
	 */
	public Spieler spielerIch;

	// TODO sollten eigentlich nicht hier sein.
	// Sind zu fest vom Status abhängig. Müssen aber momentan so sein, da es
	// nicht möglich ist Daten von Event zu Event zu übergeben.
	/**
	 * Die Karte die der Spieler gezogen hat.
	 */
	public Karte karte;
	/**
	 * Veweis auf die akutelle zugEntgegennahme
	 */
	public ZugEntgegennahme zugentgegennahme;
	//Wenn wir aufgeben wollen
	//wird im Subautomat schöner.
	public Boolean aufgabe;

	public String defaultName;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 * @param defaultName
	 *
	 */
	public BodesuriClient(String defaultName) {
		registriere(new SchwererFehler());
		registriere(new ProgrammStart());
		registriere(new VerbindungErfassen());
		registriere(new VerbindungSteht());
		registriere(new LobbyStart());
		registriere(new Lobby());
		registriere(new SpielStart());
		registriere(new AmZug());
		registriere(new NichtAmZug());
		registriere(new KarteWaehlen());
		registriere(new Ziehen());

		setStart(ProgrammStart.class);

		this.queue = new EventQueue();
		this.defaultName = defaultName;

		setEventQuelle(queue);
	}

	public BodesuriClient() {
		this("Spieler");
	}

	/* kommt mal in den controller oder geh ganz anders */
	public void meldeFehler(String fehlermeldung) {
	    // TODO Auto-generated method stub

		JOptionPane.showMessageDialog(verbindenView, fehlermeldung);

    }

	public static void main(String[] args) {
		Automat automat = new BodesuriClient();
		// ClientController controller = new ClientController();

		try {
			automat.run();
		} catch (Exception e) {
			/* Applikation stoppen wenn ein Fehler auftritt */
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}
}
