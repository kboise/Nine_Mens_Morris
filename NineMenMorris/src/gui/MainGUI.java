package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import engine.Engine;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = -8557749141800673771L;
    private BoardGUI boardPanel;
    private JPanel bottomBar;
    private JButton startGameButton;
    
    public MainGUI() {
        super("Nine Men's Morris");
        
        boardPanel = new BoardGUI();
        add(boardPanel, BorderLayout.CENTER);
        
        bottomBar = new JPanel();
        
        startGameButton = new JButton("Play");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boardPanel.setEngine(new Engine());
            }
        });
        bottomBar.add(startGameButton);
        add(bottomBar, BorderLayout.SOUTH);
        
    }
    

    public static void main(String[] args) {
        JFrame game = new MainGUI();
        game.setSize(900, 700);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
    }
}
