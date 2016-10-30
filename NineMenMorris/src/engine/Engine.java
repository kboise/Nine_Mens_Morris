package engine;

import board.Board;
import board.Player;

public class Engine {
    Board cBoard;
    Player p1, p2, activePlayer, inActivePlayer;
    String removeCells = "";
    
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
    
    public boolean inRemoveMode() { return (removeCells.length() != 0); }
    public boolean inPlaceMode() { return (p1.canPlace() || p2.canPlace()); }
    public boolean inMoveMode() { return !inPlaceMode(); }
    
    /*
     * Toggle player at each turn, such that:
     *   p2 gets turn after p1, and
     *   p1 gets turn after p2
     */
    public void setNextPlayer() {
        if      (activePlayer == null) { activePlayer = p1; inActivePlayer = p2; }
        else if (activePlayer == p1)   { activePlayer = p2; inActivePlayer = p1; }
        else if (activePlayer == p2)   { activePlayer = p1; inActivePlayer = p2; }
    }
    
    
    
    public void setupMillCells(boolean getCells) {
        if (getCells) { removeCells = cBoard.getOpponentCells(activePlayer); }
        
        System.out.println("MILL:: New mill was formed for Player-" + activePlayer.getName() + ".");
        System.out.println("       Select a Player-" + inActivePlayer.getName() + " cell to remove from \""
                + removeCells.replace(",",", ") + "\"");
    }
    
    /*
     * Place a Man at selected cell address for player with active turn and then toggle turn
     * @param dstCellAddr: board destination address for where to place Man
     */
    public void place(String dstCellAddr) {
        String msg = String.format("Player-%s @ %s", activePlayer.getName(), dstCellAddr);
        
        if (inRemoveMode()) {
            System.out.println("Remove for Player-" + inActivePlayer.getName() + " is pending!");
        } else if (inPlaceMode() & activePlayer.canPlace()) {
            if (cBoard.placeMark(activePlayer, dstCellAddr)) {
                doPrint("PLACE:: " + msg + "; " + activePlayer.placeCount + " PLACE remaining");
                
                if (cBoard.hasNewMill()) {
                    setupMillCells(true);
                } else {
                    setNextPlayer();
                }
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
        String msg = String.format("Player-%s @ %s -> %s", activePlayer.getName(), srcCell, dstCell);;
        
        if (inRemoveMode()) {
            System.out.println("Remove for Player-" + inActivePlayer.getName() + " is pending!");
        } else if (inMoveMode() && activePlayer.canMove()) {
            if (cBoard.moveMark(activePlayer,  srcCell, dstCell)) {
                doPrint("MOVE::" + msg);
                
                if (cBoard.hasNewMill()) {
                    setupMillCells(true);
                } else {
                    setNextPlayer();
                }
            } else { System.out.println("MOVE:: " + msg + "; FAILED\n");
            }
        } else { System.out.println("Board not in MOVE state ->> MOVE " + msg + " FAILED!");
        }
    }
    
    public void remove(String dstCell) {
        removeCells = "";
        setNextPlayer();
    }
    
    public void doPrint() { doPrint(""); }
    public void doPrint(String str) {
        System.out.println("\n== " + str);
        cBoard.printBoard();
    }
}
