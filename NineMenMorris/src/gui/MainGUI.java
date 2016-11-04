package gui;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;


import engine.Engine;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = -8557749141800673771L;
    private BoardGUI boardPanel;
    private JPanel bottomBar;
    private JButton startGameButton;
    private JLabel status;
    private String statusMessage = "White marker's turn";
    
    @SuppressWarnings("serial")
	public MainGUI() {
        super("Nine Men's Morris");
        
        boardPanel = new BoardGUI();
        add(boardPanel, BorderLayout.CENTER);
        
        bottomBar = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g){
        		if ( boardPanel.gameEngine.gameOver()) {
        			 if (boardPanel.gameEngine.getActivePlayer() == boardPanel.gameEngine.p1) {
        				 status.setText("Black win");
        			 } else {
        				 status.setText("white win");
        				 
        			 }
        			 
        		}

        		repaint();
        	}
        	
        };
        
        bottomBar.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 0));
        
        
        startGameButton = new JButton("Play Again");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boardPanel.setEngine(new Engine());
            }
        });

        bottomBar.add(startGameButton);
       

        status = new JLabel("");
		status.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		status.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bottomBar.add(status);
        
        add(bottomBar, BorderLayout.SOUTH);
    }
    

    public static void main(String[] args) {
        JFrame game = new MainGUI();
        game.setSize(900, 700);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
 
    }
}
