package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import pd.deck.Karte;
import pd.deck.Vier;
import pd.spielerverwaltung.Spieler;
import pd.zugsystem.BankFeld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Brett;
import pd.zugsystem.Feld;
import pd.zugsystem.Spiel;
import pd.zugsystem.Zug;

public class Prototyp {

	Spiel spiel;
	Brett brett;
	Spieler spieler;
	BankFeld bankFeld;
	Feld startFeld;
	Feld zielFeld;
	Vector<Feld> felder;
	Bewegung bewegung;
	Zug zug;
	Karte karte;
	
	public Prototyp(Spiel spiel){
		this.spiel = spiel;
		this.brett = spiel.getBrett();
		this.spieler = spiel.getSpieler().get(0);
		bankFeld = brett.getBankFeldVon(spieler);
		bankFeld.setFigur(spieler.getFiguren().get(0));		
		felder = new Vector<Feld>();
		Feld feld = bankFeld;
		for (int i = 0; i <= 10; i++) {
			felder.add(feld);
			feld = feld.getNaechstes();
		}
		
	}
	
	public void zeichneBrett() {
		System.out.println("===========");
		for (Feld feld : felder){
			zeichneFeld(feld);
		}
		System.out.println("\n===========");
	}
	
	public void zeichneFeld(Feld feld){
		if (feld.getFigur() != null) {
			System.out.print("1");
		} else if (feld instanceof BankFeld) {
			System.out.print("X");
		} else {
			System.out.print("0");
		}
	}
	
	public void begruessungAusgaben(){
		System.out.println("Willkommen im Spiel Bodesuri (Prototyp).");
	}
	
	public void auswahlSpieler(){
		System.out.println("Wähle eine Spieler aus.");
		System.out.println("Spielfigur [1 - 4]: ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String spielerString;
		try {
			if((spielerString = in.readLine()) != ""){
				int spielerNr = Integer.parseInt(spielerString);
				this.spieler = spiel.getSpieler().get(spielerNr);
				System.out.println(spieler.getName() + " wurde ausgewählt.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void auswahlKarte(){
		System.out.println("Wähle eine Karte aus.");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String karteString;
		try {
			if((karteString = in.readLine()) != ""){
				if(Integer.parseInt(karteString) == 4){
					this.karte = new Vier();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void auswahlFigur(){
		System.out.println("Wähle eine Figur aus.");
	}
	
	public void auswahlStartfeld(){
		System.out.println("Wähle das Feld der Figur aus.");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String feldString;
		try {
			if((feldString = in.readLine()) != ""){
				this.startFeld = felder.get(Integer.parseInt(feldString));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void auswahlZielfeld() {
		System.out.println("Wähle das Feld der Figur aus.");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String feldString;
		try {
			if((feldString = in.readLine()) != ""){
				this.zielFeld = felder.get(Integer.parseInt(feldString));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void zeichneKarten(Spieler spieler){
		//spieler.getKarten();
		System.out.println();
	}
	
	public void ausfuerenZug(){
		this.bewegung = new Bewegung(this.startFeld, this.zielFeld);
		this.zug = new Zug(spieler, karte, bewegung);
		if(zug.validiere() == true){
			zug.ausfuehren();
		}else {
			ausfuehrenRunde();
		}
		// new Zug()
		// zug.validieren if false neue Eingabe
		// zug.ausführen		
	}
	
	
	private void ausfuehrenRunde() {
		auswahlKarte();
		auswahlStartfeld();
		auswahlZielfeld();
		ausfuerenZug();
		
	}

	public static void main(String[] args){
		Spiel spiel = new Spiel();
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu(new Spieler("Spieler" + i));
		}
		spiel.brettAufstellen();
		Prototyp spielBrett = new Prototyp(spiel);
		spielBrett.begruessungAusgaben();
		spielBrett.zeichneBrett();
		spielBrett.ausfuehrenRunde();
		spielBrett.zeichneBrett();
	}
}
