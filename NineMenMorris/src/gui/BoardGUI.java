package gui;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.JPanel;

import board.Board;
import board.Cell;
import engine.Engine;


public class BoardGUI extends JPanel {
	private static final long serialVersionUID = 2961261317989680041L;

	private final int[] boardPoint = {9,0,1,2,14,23,22,21,9,10,3,4,1,4,5,13,14,13,20,19,22,19,18,10,11,6,7,4,7,8,12,13,12,17,16,19,16,15,11};
	
	
	private String[] guiToBoardMap = {"a1", "d1", "g1",
	                        "b2", "d2", "f2",
	                        "c3", "d3", "e3",
	                        "a4", "b4", "c4", "e4", "f4", "g4",
	                        "c5", "d5", "e5",
	                        "b6", "d6", "f6",
	                        "a7", "d7", "g7"};
	
	public Engine gameEngine;

	private int selectedMoveMakerIndex = -1;
	
	private String statusMesg = "";
	
	public BoardGUI() {
	    gameEngine = new Engine();
		addMouseListener(new Controller());
		
	}
	
	public void setEngine(Engine gameEngine){
		this.gameEngine = gameEngine;
		repaint();
	}
	public String getStatusMesg() {
		return statusMesg;
	}
	
	Point getPositionCoords(int position) {
		Point result = new Point();

		int margin = getSize().width/15;
		int width = getSize().width - 2 * margin;
		int height = getSize().height - 2 * margin;
		int squareSide = Math.min(width, height);
		int positionSpace = squareSide / 6;
		
		int row = position / 3;
		if (row < 3) {
			result.x = row * positionSpace + (position % 3) * (squareSide - 2 * row * positionSpace) / 2;
			result.y = row * positionSpace;
		} else if (row == 3) {
			result.x = (position % 3) * positionSpace;
			result.y = row * positionSpace;
		} else {
			Point point = getPositionCoords(23 - position);
			point.x -= margin;
			point.y -= margin;
			result.x = squareSide - point.x;
			result.y = squareSide - point.y;
		}
		
		result.x += margin;
		result.y += margin;
		
		return result;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Image img = new ImageIcon(getClass().getResource("background.jpg")).getImage();
		g.drawImage(img,0,0,null);
		
		if ( gameEngine.getActivePlayer() == gameEngine.p1 ) {
			statusMesg = "White marker's turn";
		} else {
			statusMesg = "Black marker's turn";
		}
		// draw the board line
		for (int i = 0; i < boardPoint.length-1; i++) {
			    
				Point start = getPositionCoords(boardPoint[i]);
				Point end = getPositionCoords(boardPoint[i+1]);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(getSize().height/200));
				g2.setColor(new Color(130, 82, 1));
				g2.drawLine(start.x, start.y, end.x, end.y);
			
		}
		// line intersection point
		for (int i = 0; i < 24; i++) {
			Point coords = getPositionCoords(i);
			g.setColor(Color.BLACK);
			g.fillOval(coords.x - getSize().height/100, coords.y - getSize().height/100, getSize().height/50, getSize().height/50);
		}
		Board currentBoard = gameEngine.cBoard;
		Cell c = null;
		
		
			for (int i = 0; i<24;i++) {
				if (selectedMoveMakerIndex == i){
					g.setColor(Color.CYAN);
					Point coords = getPositionCoords(i);
					
					//g.fillOval(coords.x - 20, coords.y - 20, 40, 40);
					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
					Graphics2D g2 = (Graphics2D) g;
					// Draw pieces boundary
					g2.setStroke(new BasicStroke(getSize().height/400));
					g2.setColor(Color.gray);
					//g2.drawOval(coords.x - 20, coords.y - 20, 40, 40);
					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
				} else {
				    c = currentBoard.getCell(guiToBoardMap[i]);
				    if (c.isOccupied() && gameEngine.p1.isOwner(c)){
    					
    					g.setColor(Color.WHITE);
    					
    					Point coords = getPositionCoords(i);
    					
    					//g.fillOval(coords.x - 20, coords.y - 20, 40, 40);
    					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					Graphics2D g2 = (Graphics2D) g;
    					// Draw pieces boundary
    					g2.setStroke(new BasicStroke(getSize().height/400));
    					g2.setColor(Color.gray);
    					//g2.drawOval(coords.x - 20, coords.y - 20, 40, 40);
    					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    				} else if(c.isOccupied() && gameEngine.p2.isOwner(c)){
    					g.setColor(Color.BLACK);
    					Point coords = getPositionCoords(i);
    					
    					//g.fillOval(coords.x - 20, coords.y - 20, 40, 40);
    					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					Graphics2D g2 = (Graphics2D) g;
    					// Draw pieces boundary
    					g2.setStroke(new BasicStroke(getSize().height/400));
    					g2.setColor(Color.gray);
    					//g2.drawOval(coords.x - 20, coords.y - 20, 40, 40);
    					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    				}
                }
			}	
		
		
		for(int i = gameEngine.p1.placeCount; i > 0; i--){
			Point coords = getPositionCoords(2);
			g.setColor(Color.WHITE);
			int x = coords.x + getSize().height/10 - getSize().height/30 + (9-i)%3*getSize().height/15;
			int y = coords.y + getSize().height/10 - getSize().height/30 + ((9 - i)/3)*getSize().height/15;
			g.fillOval(x, y, getSize().height/15, getSize().height/15);
		}
		
		for(int i = gameEngine.p2.placeCount ; i > 0; i--){
				Point coords = getPositionCoords(14);
				g.setColor(Color.BLACK);
				int x = coords.x + getSize().height/10 - getSize().height/30 + (9-i)%3*getSize().height/15;
				int y = coords.y + getSize().height/10 - getSize().height/30 + ((9 - i)/3)*getSize().height/15;
				g.fillOval(x, y, getSize().height/15, getSize().height/15);
			}

	}
	
	private class Controller extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int x = e.getX();
			int y = e.getY();
			
			for (int i = 0; i < 24; i++) {
				Point coords = getPositionCoords(i);
				
				if (coords.x - getSize().height/30 <= x && x <= coords.x + getSize().height/30
						&& coords.y - getSize().height/30 <= y && y <= coords.y + getSize().height/30) {
					
					if (!gameEngine.inRemoveMode()&&!gameEngine.inMoveMode()) {
						gameEngine.place(guiToBoardMap[i]);
						
					} else if (gameEngine.inRemoveMode()) { 
						gameEngine.remove(guiToBoardMap[i]);	
					} else if (gameEngine.inMoveMode()) {
						if (gameEngine.getBoard().getCell(guiToBoardMap[i]).owner == gameEngine.getActivePlayer()) {
							selectedMoveMakerIndex = i;
						} else if (selectedMoveMakerIndex != -1) { 
							gameEngine.move(guiToBoardMap[selectedMoveMakerIndex], guiToBoardMap[i]);
						    selectedMoveMakerIndex = -1;
						}
					}
					repaint();
					break;
				}
			}
		}
	}
}
