package engine;

import java.util.ArrayList;
import java.util.Random;

import board.Board;
import board.Cell;

public class EngineAI {
	private Engine engine;
	
	public EngineAI(Engine engine) {
		this.engine = engine;
	}

	public void placeRandom() {
		String[] dstString = engine.cBoard.getVacantCells().split(",");
		Random rand = new Random();
		int n = rand.nextInt(dstString.length);
		engine.place(dstString[n]);
		//return dstString[n];
	}
	
	public void moveRandom() {
		Board curBoard = engine.cBoard;
		String[] srcString = engine.activePlayer.getOwnedCells().split(",");
		int n = srcString.length;
		Random randgen = new Random();
		int randstart = randgen.nextInt(n);
		int j;
		
		for (int k = randstart; k < randstart + n; k++){
			if (k < n) { 
				j = k;
			} else { 
				j = k - n;
			}

			Cell temCell = curBoard.getCell(srcString[j]);
			
			if (temCell.hasOpenNeighbor()){
				if (temCell.left != null){
					if (temCell.left.isEmpty()){
						engine.move(srcString[j],temCell.left.label);
						break;
						}
				}
				if (temCell.right != null) {
					if (temCell.right.isEmpty()){
						engine.move(srcString[j],temCell.right.label);
						break;
						}
				}
				if (temCell.top != null) {
					if (temCell.top.isEmpty()){
						engine.move(srcString[j],temCell.top.label);
						break;
						}
				}
				if (temCell.bottom != null) {
					if (temCell.bottom.isEmpty()){
						engine.move(srcString[j],temCell.bottom.label);
						break;
						}
				}
			}
		}
	}
	
	public void flyRandom() {
		String[] srcString = engine.activePlayer.getOwnedCells().split(",");
		Random randgen = new Random();
		int n = randgen.nextInt(srcString.length);
		String[] dstString = engine.cBoard.getVacantCells().split(",");
		randgen = new Random();
		int m = randgen.nextInt(dstString.length);
		engine.move(dstString[m], dstString[n]);
	}
	
	public void removeRandom() {
		ArrayList<String> rmString = engine.activePlayer.opponent.getRemovableCells();
		Random randgen = new Random();
		int n = randgen.nextInt(rmString.size());
		engine.remove(rmString.get(n));
	}
	
}
