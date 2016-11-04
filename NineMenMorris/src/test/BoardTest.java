package test;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import board.Board;
import board.Player;


public class BoardTest {
	
	// Test assignment of marker after the first placement
	@Test
	public void testMarkerAssignment(){
        Board testboard = new Board();
        
        Player p1 = new Player('1','W');
        
        testboard.placeMark(p1, "c5");  
        
        assertEquals(p1, testboard.getCell("c5").owner);

	}
	
	
	// Test method to check valid placement 
	@Test
	public void testValidPlacement() {
		Board testBoard = new Board();
		testBoard.printBoard();
		
		Player p1 = new Player('1', 'W');
		String position = "c5";
		testBoard.placeMark(p1, position);
		assertTrue(testBoard.getCell(position).isOccupied());
	}
	
	// Test method to check placement in invalid cells
	@Test
	public void testInValidPlacement() {
		Board testBoard = new Board();
		
		Player p1 = new Player('1', 'W');
		String position = "c10";
		assertThat(testBoard.placeMark(p1, position), CoreMatchers.is("FAILED"));
	}
	
	// Test method to check the ownership of markers
	@Test
	public void testOwnership(){
        Board testboard = new Board();
        
        Player p1 = new Player('1','W');
        Player p2 = new Player('2','B');
        
        testboard.placeMark(p1, "c5"); 
        testboard.placeMark(p2, "c5"); 
        
        assertEquals(p1, testboard.getCell("c5").owner);

	}
	
	// Test method to check if a mill is formed across a row
	@Test
	public void testHorizontalMillFormation() {
		Board testBoard = new Board();
		
		Player p1 = new Player('1', 'W');
		
		testBoard.placeMark(p1, "a1");
		testBoard.placeMark(p1, "d1");
		
		assertEquals("a1,d1,g1", testBoard.placeMark(p1, "g1"));
	}
	
	// Test method to check if a mill is formed under a column
	@Test
	public void testVerticalMillFormation() {
		Board testBoard = new Board();
		testBoard.printBoard();
		
		Player p1 = new Player('1', 'W');
		
		testBoard.placeMark(p1, "a1");
		testBoard.placeMark(p1, "a4");
		
		assertEquals("a1,a4,a7", testBoard.placeMark(p1, "a7"));
	}
	

	@Test
	public void testAllCells() {
	    String colLabels = "abcdefg";
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
	    		
	    		if(testboard.getCell(pos) == null){
	    			assertTrue(testboard.getCell(pos) == null);
	    		} else{
	    			assertTrue(testboard.getCell(pos).isOccupied());
	    		}	
	    	}
	    } 
        testboard.printBoard();  
	}
	

	
}
