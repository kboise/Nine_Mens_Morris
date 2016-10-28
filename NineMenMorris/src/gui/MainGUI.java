package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainGUI extends JFrame {
	private static final long serialVersionUID = -8557749141800673771L;
	private BoardGUI boardPanel;
	
	public MainGUI() {
		super("Nine Men's Morris");
		
		boardPanel = new BoardGUI();
		
		add(boardPanel, BorderLayout.CENTER);
	}
	

	public static void main(String[] args) {
		JFrame game = new MainGUI();
		
		game.setSize(900, 700);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	}
}
