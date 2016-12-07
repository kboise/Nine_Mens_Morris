package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import board.Board;
import board.Cell;

public class EngineAI {
	private Engine engine;
	private final String[][] millString = {{"a1", "d1", "g1"}, {"b2", "d2", "f2"}, {"c3", "d3", "e3"}, {"a4", "b4", "c4"}, 
			{"e4", "f4", "g4"}, {"c5", "d5", "e5"}, {"b6", "d6", "f6"}, {"a7", "d7", "g7"},
			{"a1", "a4", "a7"}, {"b2", "b4", "b6"}, {"c3", "c4", "c5"}, {"d1", "d2", "d3"}, 
			{"d5", "d6", "d7"}, {"e3", "e4", "e5"}, {"f2", "f4", "f6"}, {"g1", "g4", "g7"}};

	public EngineAI(Engine engine) {
		this.engine = engine;
	}

	public void placeRandom() {
		String[] dstString = engine.cBoard.getVacantCells().split(", ");
		Random rand = new Random();
		int n = rand.nextInt(dstString.length);
		engine.place(dstString[n]);
		//return dstString[n];
	}
	
	public void moveRandom() {
		Board curBoard = engine.cBoard;
		String[] srcString = engine.activePlayer.getOwnedCells().split(", ");
		int n = srcString.length;
		Random randgen = new Random();
		int randstart = randgen.nextInt(n);
		int j;
		moveLoop:
		for (int k = randstart; k < randstart + n; k++){
			if (k < n) { 
				j = k;
			} else { 
				j = k - n;
			}

			Cell temCell = curBoard.getCell(srcString[j]);
			
			if (temCell.hasOpenNeighbor()){
				int randDir = randgen.nextInt(4);
				switch (randDir) {
				case 0:
				if (temCell.left != null){
					if (temCell.left.isEmpty()){
						engine.move(srcString[j],temCell.left.label);
						break moveLoop;
						}
				}
				case 1:
				if (temCell.right != null) {
					if (temCell.right.isEmpty()){
						engine.move(srcString[j],temCell.right.label);
						break moveLoop;
						}
				}
				case 2:
				if (temCell.top != null) {
					if (temCell.top.isEmpty()){
						engine.move(srcString[j],temCell.top.label);
						break moveLoop;
						}
				}
				case 3:
				if (temCell.bottom != null) {
					if (temCell.bottom.isEmpty()){
						engine.move(srcString[j],temCell.bottom.label);
						break moveLoop;
						}
				}
				}
			}
		}
	}
	
	public void flyRandom() {
		String[] srcString = engine.activePlayer.getOwnedCells().split(", ");
		Random randgen = new Random();
		int n = randgen.nextInt(srcString.length);
		String[] dstString = engine.cBoard.getVacantCells().split(", ");
		randgen = new Random();
		int m = randgen.nextInt(dstString.length);
		engine.move(srcString[n], dstString[m]);
	}
	
	public void removeRandom() {
		ArrayList<String> rmString = engine.activePlayer.opponent.getRemovableCells();
		Random randgen = new Random();
		int n = randgen.nextInt(rmString.size());
		engine.remove(rmString.get(n));
	}
	
	public String evalPlace() {
		final String ownString = engine.activePlayer.getOwnedCells();
		final String oppString = engine.activePlayer.opponent.getOwnedCells();
		String[] emptyString = engine.cBoard.getVacantCells().split(", ");
		Random rand = new Random();
		int n = rand.nextInt(emptyString.length);
		String dstCell = null;
		int count = 0;
		
		for(String[] mills : millString) {
			count = 0;
			
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				System.out.println(dstCell + "*****1*****");
				return dstCell;
			}
		}
		
		dstCell = null;
		for(String[] mills : millString) {
			count = 0;

			for(String cell : mills) {
				if (oppString.contains(cell)) {
					count = count + 1;
				} else if (!ownString.contains(cell)){
					System.out.println(dstCell);
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell !=null) {
				System.out.println(dstCell + "*****2*****");
				return dstCell;
			}
		}
		
		dstCell = null;
		dstCell = emptyString[n];
		System.out.println(dstCell + "*****3*****");
		return dstCell;
		
	}
	
}
