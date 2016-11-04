package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
    private JLabel turnsStatus;
    private JLabel modeStatus;
    
    @SuppressWarnings("serial")
	public MainGUI() {
        super("Nine Men's Morris");
        
        boardPanel = new BoardGUI();
        add(boardPanel, BorderLayout.CENTER);
        
        bottomBar = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g){
        		super.paintComponent(g);
        		if ( boardPanel.gameEngine.gameOver()) {
        			 if (boardPanel.gameEngine.getActivePlayer() == boardPanel.gameEngine.p1) {
        				 modeStatus.setForeground(Color.CYAN);
        				 modeStatus.setText("Game over - Black wins!!!");
        				 turnsStatus.setText("");
        			 } else {
        				 modeStatus.setText("Game over - White wins!!!");
        				 turnsStatus.setText("");
        			 } 
        		} else if ( boardPanel.gameEngine.inMoveMode() && !boardPanel.gameEngine.inRemoveMode() ) {
        			if (boardPanel.gameEngine.getActivePlayer().canFly()) {
        				modeStatus.setForeground(Color.RED);
            			modeStatus.setText("Flying!!!!!");
        			} else { modeStatus.setForeground(Color.BLACK);
        					 modeStatus.setText("Mode: Moving");
        			}
        		} else if ( boardPanel.gameEngine.inRemoveMode() && !boardPanel.gameEngine.gameOver()) {
        			modeStatus.setForeground(Color.BLUE);
        			//modeStatus.setFont(new Font(modeStatus.getFont().getName(), Font.BOLD, 30));
        			modeStatus.setText("Mill formed! Remove opponent");
        		} else {
        			modeStatus.setForeground(Color.BLACK);
        			modeStatus.setText("Mode: Placing");
        		}
        		
        		if ( !boardPanel.gameEngine.gameOver() && boardPanel.gameEngine.getActivePlayer() == boardPanel.gameEngine.p1) {
        			turnsStatus.setText("Turn: White");
        		} else if ( !boardPanel.gameEngine.gameOver() ) {
        			turnsStatus.setText("Turn: Black");
        		}
        		
        		repaint();
        	}
        	
        };
        
        bottomBar.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 1));
        
        
        startGameButton = new JButton("Play Again");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boardPanel.setEngine(new Engine());
            }
        });

        bottomBar.add(startGameButton);
       
        turnsStatus = new JLabel("");
        modeStatus = new JLabel("");
		turnsStatus.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		modeStatus.setFont(new Font(modeStatus.getFont().getName(), Font.PLAIN, 30));
		turnsStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		modeStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bottomBar.add(turnsStatus);
		bottomBar.add(modeStatus);
        add(bottomBar, BorderLayout.SOUTH);
    }
    

    public static void main(String[] args) {
        JFrame game = new MainGUI();
        game.setSize(950, 700);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
 
    }
}
