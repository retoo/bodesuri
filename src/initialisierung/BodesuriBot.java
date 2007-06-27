package initialisierung;


import ui.GUIController;
import applikation.bot.Botsuri;
import applikation.bot.Stupidbot;
import applikation.client.konfiguration.Konfiguration;
import dienste.eventqueue.EventQueue;

public class BodesuriBot {

	public static void main(String[] args) throws InterruptedException {
		Konfiguration konfig = new Konfiguration();
		konfig.defaultName = "Roboter";

		EventQueue queue = new EventQueue();

		GUIController guiController = new GUIController(queue, konfig);
		Botsuri bot = new Botsuri(konfig, queue, guiController, Stupidbot.class);

		bot.start();
		bot.join();
	}
}
