package spielplatz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.nachrichten.ChatNachricht;
import dienste.netzwerk.nachrichten.SpielStartNachricht;
import dienste.netzwerk.nachrichten.VerbindungWegException;


public class Client {
	public static void main(String[] args) throws UnknownHostException,
			IOException, VerbindungWegException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String hostname = "localhost";
		int port = 3334;

		EndPunkt kom = new EndPunkt(hostname, port);

		while (true) {
			Brief brief = kom.briefkasten.getBrief();

			if (brief.nachricht instanceof ChatNachricht) {
				System.out.println("ChatNachricht: " + brief);
			} else if (brief.nachricht instanceof SpielStartNachricht) {
				System.out.println("Spiel startet!");
				break;
			}
		}

		while (true) {
			Brief brief;

			String eingabe = in.readLine();

			kom.sende(new ChatNachricht(eingabe));

			while ((brief = kom.briefkasten.getBrief(false)) != null) {
				if (brief.nachricht instanceof ChatNachricht) {
					System.out.println(brief);
				} else {
					throw new RuntimeException("Unbekannte Nachricht");
				}
			}
		}
	}
}
