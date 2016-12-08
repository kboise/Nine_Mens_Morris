package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


import engine.Engine;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = -8557749141800673771L;
    private BoardGUI boardPanel;
    private JPanel bottomBar;
    private JButton startGameButton;
    private JLabel turnsStatus;
    private JLabel modeStatus;
    private final String[] messageStrings = {"Human","Computer"};

    JComboBox cmbMessageList = new JComboBox(messageStrings);
    
    @SuppressWarnings("serial")
	public MainGUI() {
        super("Nine Men's Morris");
        
        boardPanel = new BoardGUI();
        add(boardPanel, BorderLayout.CENTER);
        
        bottomBar = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g){
        		super.paintComponent(g);
//        		if ( boardPanel.gameEngine.gameOver()) {
//        			 if (boardPanel.gameEngine.p2.hasLost()) {
//        				 modeStatus.setForeground(Color.RED);
//        				 modeStatus.setText("Game over - Black wins!!!");
//        				 turnsStatus.setText("");
//        			 } else {
//        				 modeStatus.setText("Game over - White wins!!!");
//        				 turnsStatus.setText("");
//        			 } 
//        		}  else if ( boardPanel.gameEngine.activePlayer.isFlying() ) {        		
//        			modeStatus.setForeground(Color.RED);
//            		modeStatus.setText("Flying!!!!!");
//        		} else if ( boardPanel.gameEngine.activePlayer.isMoving() ) { 
//        			modeStatus.setForeground(Color.BLACK);
//        			modeStatus.setText("Mode: Moving");
//        			} else if ( boardPanel.gameEngine.activePlayer.removePending()) {
//        			modeStatus.setForeground(Color.BLUE);
//        			modeStatus.setText("Mill formed! Remove opponent");
//        		} else if ( boardPanel.gameEngine.activePlayer.isPlacing()) {
//        			modeStatus.setForeground(Color.BLACK);
//        			modeStatus.setText("Mode: Placing");
//        		}
        		switch (boardPanel.gameEngine.activePlayer.getCurrentPlayState()) {
        		case GAMEOVER: 
        			if (boardPanel.gameEngine.p2.hasLost()) {
        				modeStatus.setForeground(Color.RED);
        				modeStatus.setText("Game over - White wins!!!");
        				turnsStatus.setText("");
        			} else {
        				modeStatus.setText("Game over - Black wins!!!");
        				turnsStatus.setText("");
        			} 
        			break;
        		case FLYING: 
        			modeStatus.setForeground(Color.RED);
        			modeStatus.setText("Flying!!!!!");
        			break;
        		case MOVING:
        			modeStatus.setForeground(Color.BLACK);
        			modeStatus.setText("Mode: Moving");
        			break;
        		case REMOVING:
        			modeStatus.setForeground(Color.BLUE);
        			modeStatus.setText("Mill formed! Remove opponent");
        			break;
        		case PLACING:
        			modeStatus.setForeground(Color.BLACK);
        			modeStatus.setText("Mode: Placing");
        			break;
        		case NOTSTARTED:
        			break;
        		default:
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
        startGameButton.setPreferredSize(new Dimension(200,40));
        startGameButton.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boardPanel.setEngine(new Engine());
            }
        });
        cmbMessageList.setPreferredSize(new Dimension(150, 30));
        cmbMessageList.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
        cmbMessageList.addActionListener(new ActionListener() {
        	

			@Override
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == cmbMessageList) {
        			JComboBox cb = (JComboBox) e.getSource();
        			String msg = (String) cb.getSelectedItem();
        			switch (msg) {
        			case "Computer": boardPanel.isAIMode = 1;
        			break;
        			case "Human": boardPanel.isAIMode = 0;
        			break;
        			default: boardPanel.isAIMode = 0;
        			}
        		}
        	}
        });

        turnsStatus = new JLabel("");
        modeStatus = new JLabel("");
		turnsStatus.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		modeStatus.setFont(new Font(modeStatus.getFont().getName(), Font.PLAIN, 30));
		turnsStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		modeStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		bottomBar.add(startGameButton);
		bottomBar.add(turnsStatus);
		bottomBar.add(modeStatus);
		bottomBar.add(cmbMessageList); 
        add(bottomBar, BorderLayout.SOUTH);
    }
    

    public static void main(String[] args) {
        JFrame game = new MainGUI();
        game.setSize(950, 700);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
 
    }
}
