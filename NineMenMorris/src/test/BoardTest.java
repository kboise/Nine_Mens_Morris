package test;

import static org.junit.Assert.*;

import org.junit.Test;

import board.Board;
import board.Player;


public class BoardTest {

	@Test
	public void test() {
	    String colLabels = "ABCDEFG";
	    String rowLabels = "1234567";
	    String pos;
	    
		Board testboard;
		
		testboard = new Board();
		testboard.printBoard();
        
        Player p1 = new Player('1','W');
        
        
	    for(int i=0;i<colLabels.length();i++){
	    	for(int j=0;j<colLabels.length();j++){
	    		StringBuilder sb = new StringBuilder();
	    		pos =  sb.append(colLabels.charAt(i)).append(rowLabels.charAt(j)).toString();
	    		System.out.println(pos);
	    		testboard.placeMark(p1, pos);
	    		if(testboard.getCell(pos)==null){
	    			assertTrue(testboard.getCell(pos)==null);
	    		} else{
	    			assertTrue(testboard.getCell(pos).isOccupied());
	    		}	
	    	}
	    } 
        testboard.printBoard();  
	}
	
	@Test
	public void test1(){
	
		
		Board testboard = new Board();
		testboard.printBoard();
        
        Player p1 = new Player('1','W');
        for(int i = 0; i< 9; i++){
        	testboard.placeMark(p1, "b1");
        }
        testboard.placeMark(p1, "a1");
        assertTrue(testboard.getCell(1,1).isOccupied());
	}
	
	@Test
	public void test2(){
        Board testboard = new Board();
        
        Player p1 = new Player('1','@');
        Player p2 = new Player('2','O');
        

        testboard.placeMark(p2, "c5"); 
        testboard.placeMark(p1, "c5"); 
        assertEquals(p2,testboard.getCell(5,3).owner);

	}
// need method to get owner's type(may add method in player to get player name) to make it testable
}
