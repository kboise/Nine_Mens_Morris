package engine;

import board.Board;
import board.Player;

public class Engine {
    Board cBoard;
    Player p1, p2, p;
    
    /*
     * Create a new game board and associated players
     */
    public void startNewGame() {
        System.out.println("Printing 9-Men's Morris board");
        
        cBoard = new Board();
        doPrint("Unplayed");
        
        p1 = new Player('1','X');
        p2 = new Player('2','O');
        
        setNextPlayer();
    }
    
    public Engine() { startNewGame(); }
    
    public boolean inPlaceMode() { return (p1.canPlace() || p2.canPlace()); }
    public boolean inMoveMode() { return !inPlaceMode(); }
    
    /*
     * Toggle player at each turn, such that:
     *   p2 gets turn after p1, and
     *   p1 gets turn after p2
     */
    public void setNextPlayer() {
        if      (p == null) { p = p1; }
        else if (p == p1) { p = p2; }
        else if (p == p2) { p = p1; }
    }
    
    /*
     * Place a Man at selected cell address for player with active turn and then toggle turn
     * @param dstCellAddr: board destination address for where to place Man
     */
    public void place(String dstCellAddr) {
        String msg = String.format("Player-%s @ %s", p.getName(), dstCellAddr);
        
        if (inPlaceMode() & p.canPlace()) {
            if (cBoard.placeMark(p, dstCellAddr)) {
                doPrint("PLACE:: " + msg + "; " + p.placeCount + " PLACE remaining");
                setNextPlayer();
            } else { System.out.println("PLACE:: " + msg + "; FAILED\n");
            }
        } else { System.out.println("Board not in PLACE state ->>  PLACE " + msg + " FAILED!");
        }
    }
    
    /*
     * For the player with active turn, move a Man from a source-cell
     * to destination cell on the 9-Men's Morris board
     * @param srcCell: source cell address
     * @param dstCell: destination cell address
     */
    public void move(String srcCell, String dstCell) {
        String msg = String.format("Player-%s @ %s -> %s", p.getName(), srcCell, dstCell);;
        if (inMoveMode() && p.canMove()) {
            if (cBoard.moveMark(p,  srcCell, dstCell)) {
                doPrint("MOVE::" + msg);
                setNextPlayer();
            } else { System.out.println("MOVE:: " + msg + "; FAILED\n");
            }
        } else { System.out.println("Board not in MOVE state ->> MOVE " + msg + " FAILED!");
        }
    }
    
    public void doPrint() { doPrint(""); }
    public void doPrint(String str) {
        System.out.println("\n== " + str);
        cBoard.printBoard();
    }
}
