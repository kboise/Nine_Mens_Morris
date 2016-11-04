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
    //public Player getPlayer(String name) { return (name == "p1") ? p1 : p2; }
    
    private void printStats(Player p) {
        if (p != null ) { p.printStats();
        } else { System.out.println("Player-? is yet to PLACE");
        }
        cBoard.getOwnedCells(p, true);
        cBoard.getNonMillOwnedCells(p, true);
        cBoard.getMillOwnedCells(p, true);
    }
    
    public Player getActivePlayer() { return activePlayer; }
    
    public Board getBoard(){ return cBoard; }

    public void doPrint(String str) {
        System.out.println();        
        printStats(activePlayer);
        System.out.println();
        printStats(inActivePlayer);
        
        cBoard.getVacantCells();
        
        System.out.println("== " + str);
        cBoard.printBoard();
        System.out.println();
    }
    
    /* Create a new game board and associated players */
    public void startNewGame() {
        System.out.println("Printing 9-Men's Morris board");
        
        cBoard = new Board();
        doPrint("Unplayed");
        
        p1 = new Player('1','X');
        p2 = new Player('2','O');
        p1.opponent = p2;
        p2.opponent = p1;
        
        setNextPlayer();
    }
    
    public Engine() { startNewGame(); }
    
    public boolean gameOver() { return (p1.hasLost() || p2.hasLost()); }
    
    public boolean inRemoveMode() { return !gameOver() && (cellsToRemove.length() != 0); }
    public boolean inPlaceMode() { return !gameOver() && (p1.canPlace() || p2.canPlace()); }
    public boolean inMoveMode() { return !gameOver() && !inPlaceMode(); }
    
    /*
     * Toggle player at each turn, such that:
     *   p2 gets turn after p1, and
     *   p1 gets turn after p2
     */
    public void setNextPlayer() {
        activePlayer = (activePlayer == null) ? p1 : activePlayer.opponent;
        inActivePlayer = activePlayer.opponent;
    }
    
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
                setCellsToRemove();
                doPrint("PLACE:: " + msg + "; SUCCESS!");
                showMillNotification();
            } else {
                // Failed placeMark
                System.out.println("PLACE:: " + msg + "; " + result);
            }
        } else if (inMoveMode()) { System.out.println("Board in MOVE mode ->>  PLACE " + msg + " FAILED!");
        } else if (inRemoveMode()) { System.out.println("Board in REMOVE mode ->>  PLACE " + msg + " FAILED!");
        } else if (gameOver()) { System.out.println("Game is over!!!");
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
            if (activePlayer.canFly()) {
                vacantCells = cBoard.getVacantCells();
            } else {
                vacantCells = cBoard.getVacantNeighbors(srcCellAddr);
            }
            
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
                    setCellsToRemove();
                    doPrint("MOVE:: " + msg + "; SUCCESS!");
                    showMillNotification();
                } else {
                    // Failed placeMark
                    System.out.println("MOVE:: " + msg + "; " + result);
                }
                
                if (result.equals("")) { setNextPlayer(); }
            }
        } else if (gameOver()) { System.out.println("Game is over!!!");
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
    public void setCellsToRemove() {
        // Case-1: Opponent has non-Mill-forming cells
        cellsToRemove = cBoard.getNonMillOwnedCells(inActivePlayer, false);
        
        // Check if no non-Mill cells found
        if (cellsToRemove.length() == 0) {
            // Case-2: Opponent only has Mill-forming cells
            cellsToRemove = cBoard.getMillOwnedCells(inActivePlayer, false);
        }
    }
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
