package engine;

import board.Board;
import board.Player;

public class Engine {
    public Board cBoard;
    public Player p1, p2, activePlayer, inActivePlayer;
    String cellsToRemove = "";
    String placeCells = "";
    String newMillCells = "";
    
    /* Get a player's class */
    public Player getPlayer(String name) { return (name == "p1") ? p1 : p2; }
    
    public void doPrint() { doPrint(""); }
    public void doPrint(String str) {
        System.out.println("\n== " + str);
        cBoard.printBoard();
    }
    
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
    
    public boolean inRemoveMode() { return (cellsToRemove.length() != 0); }
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
    
    
    /*
    public void setupMillCells(boolean getCells) {
        if (getCells) { cellsToRemove = cBoard.getOpponentCells(activePlayer); }
        
        System.out.println("MILL:: New mill was formed for Player-" + activePlayer.getName() + ".");
        System.out.println("       Select a Player-" + inActivePlayer.getName() + " cell to remove from \""
                + cellsToRemove.replace(",",", ") + "\"");
    }
    */
    
    /*
     * Place a Man/Mark for activePlayer at destination cell address;
     * - toggle turn if place was successful but does not result in a Mill
     * - set board to removeMode() if Mill was formed
     * @param dstCellAddr: board destination address for where to place Man
     */
    public void place(String dstCellAddr) {
        String msg = String.format("Player-%s @ %s", activePlayer.getName(), dstCellAddr);
        String result = "";
        
        if (inRemoveMode()) {
            System.out.println("\nRemove for Player-" + inActivePlayer.getName() + " is pending!");
            System.out.println("PLACE:: " + msg + "; ABORTED!");
        } else if (inPlaceMode() & activePlayer.canPlace()) {
            result = cBoard.placeMark(activePlayer, dstCellAddr);
            
            if (result.equals("SUCCESS")) {
                // placeMark was successful but did not result in a Mill for activePlayer
                doPrint("PLACE:: " + msg + "; " + result);
                setNextPlayer();
            } else if (result.contains(",")) {
                // placeMark was successful and resulted in a Mill for activePlayer
                // result == copy comma-separated cells to cellsToRemove
                setCellsToRemove(cBoard.getOpponentCells(activePlayer));
                doPrint("PLACE:: " + msg + "; SUCCESS!");
                showMillNotification();
            } else {
                // Failed placeMark
                System.out.println("PLACE:: " + msg + "; " + result);
            }
        } else if (inMoveMode()) { System.out.println("Board in MOVE mode ->>  PLACE " + msg + " FAILED!");
        } else if (inRemoveMode()) { System.out.println("Board in REMOVE mode ->>  PLACE " + msg + " FAILED!");
        } else { System.out.println("Board in UNKNOWN state ->>  PLACE " + msg + " FAILED!");
        }
    }
    
    /*
     * For the player with active turn, move a Man from a source-cell
     * to destination cell on the 9-Men's Morris board
     * @param srcCell: source cell address
     * @param dstCell: destination cell address
     */
    public void move(String srcCellAddr, String dstCellAddr) {
        String msg = String.format("Player-%s @ %s -> %s", activePlayer.getName(), srcCellAddr, dstCellAddr);;
        String vacantCells = "";
        String result = "";
        
        if (inRemoveMode()) {
            System.out.println("Remove for Player-" + inActivePlayer.getName() + " is pending!");
            System.out.println("MOVE:: " + msg + "; ABORTED!");
        } else if (inMoveMode() && activePlayer.canMove()) {
            vacantCells = cBoard.getVacantNeighbors(srcCellAddr);
            if (!vacantCells.contains(dstCellAddr)) {
                System.out.println("Cell-" + dstCellAddr + " not in possible move set");
                System.out.println("MOVE:: " + msg + "; " + " FAILED");
            } else {
                result = cBoard.moveMark(activePlayer, srcCellAddr, dstCellAddr);
                
                if (result.equals("SUCCESS")) {
                    // moveMark was successful but did not result in a Mill for activePlayer
                    doPrint("MOVE:: " + msg + "; " + result);
                    setNextPlayer();
                } else if (result.contains(",")) {
                    // moveMark was successful and resulted in a Mill for activePlayer
                    // result == copy comma-separated cells to cellsToRemove
                    setCellsToRemove(cBoard.getOpponentCells(activePlayer));
                    doPrint("MOVE:: " + msg + "; SUCCESS!");
                    showMillNotification();
                } else {
                    // Failed placeMark
                    System.out.println("MOVE:: " + msg + "; " + result);
                }
                
                if (result.equals("")) { setNextPlayer(); }
            }
        } else { System.out.println("Board not in MOVE state ->> MOVE " + msg + " FAILED!");
        }
    }
    
    /* Remove a cell's occupant from Board */
    public void remove(String dstCellAddr) {
        String msg = String.format("Player-%s remove Player-%s cell %s", activePlayer.getName(), 
                        inActivePlayer.getName(), dstCellAddr);
        
        if (cellsToRemove.contains(dstCellAddr)) {
            cBoard.removeMark(inActivePlayer, dstCellAddr);
            clearCellsToRemove();
            doPrint("REMOVE:: " + msg);
            setNextPlayer();
        } else {
            System.out.println("\nRemove FAILED; " + dstCellAddr + " not part of valid cells to remove");
            System.out.println("-- Need to attempt Remove from " + getCellsToRemove());
        }
    }
    
    /* Set string value to cellsToRemove */
    public void setCellsToRemove(String commaSeparatedString) { cellsToRemove = commaSeparatedString; }
    /* Set string value to cellsToRemove */
    public void clearCellsToRemove() { cellsToRemove = ""; }
    public String getCellsToRemove() { return cellsToRemove; }
    
    /* Set string value to newMillCells */
    public void setNewMillCells(String commaSeparatedString) { newMillCells = commaSeparatedString; }
    
    /* Show Mill-formed notification and cells that can be removed */
    public void showMillNotification() {
        System.out.println("MILL:: New mill was formed for Player-" + activePlayer.getName() + ".");
        System.out.println("       Select a Player-" + inActivePlayer.getName() + " cell to remove from \""
                + cellsToRemove.replace(",",", ") + "\"");
    }
}
