package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.GUIController;

public class BrettMouseAdapter extends MouseAdapter {
//	private BrettView brettView;
//	private Controller controller;
	
	public BrettMouseAdapter(BrettView brettView, GUIController controller) {
//		this.brettView = brettView;
//		this.controller = controller;
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("Ins Leere geklickt...");
	}
}