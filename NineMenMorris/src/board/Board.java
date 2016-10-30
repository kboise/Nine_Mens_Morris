package board;

//import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int DIMENSION = 7;
    private Cell [][] board;
    private boolean placing = true;     // true: placing; false: moving
    
    public enum PlaceOrMoveStates { UNINITIALIZED, FAILED, SUCCESS, FORMEDMILL }
    private PlaceOrMoveStates boardStatus = PlaceOrMoveStates.UNINITIALIZED;
    
    /* Console print variables */
    boolean colIsNumeric = false;
    boolean rowIsNumeric = !colIsNumeric;
    private boolean ROWADDRFIRST = false;
    private String LABELS = "ABCDEFG1234567abcdefg";
    
    /* Board's active Mills */
    //private ArrayList<Cell> rowMill;
    //private ArrayList<Cell> colMill;
    
    /*
     * Board constructor: initialize board X-by-X grid of Cells and
     * then setup Cell links based on adjacency/neighbor matrix
     */
    public Board() {
        board = new Cell[DIMENSION][DIMENSION];
        setNeighbors();
    }
    
    /*
     * Setup left, right, top and bottom neighbors of each cell on
     * a 9-Men's-Morris game board (adjacency matrix for all 24-cells)
     */
    private void setNeighbors() {
        String[] cellMap = "a1,d1,g1,b2,d2,f2,c3,d3,e3,a4,b4,c4,e4,f4,g4,c5,d5,e5,b6,d6,f6,a7,d7,g7".split(",");
        int [][][] adjacency =  {
            // cell, lft,  rht,  top,  bot
            { {1,1}, {0,0}, {1,4}, {0,0}, {4,1} },
            { {1,4}, {1,1}, {1,7}, {0,0}, {2,4} },
            { {1,7}, {1,4}, {0,0}, {0,0}, {4,7} },
            { {2,2}, {0,0}, {2,4}, {0,0}, {4,2} },
            { {2,4}, {2,2}, {2,6}, {1,4}, {3,4} },
            { {2,6}, {2,4}, {0,0}, {0,0}, {4,6} },
            { {3,3}, {0,0}, {3,4}, {0,0}, {4,3} },
            { {3,4}, {3,3}, {3,5}, {2,4}, {0,0} },
            { {3,5}, {3,4}, {0,0}, {0,0}, {4,5} },
            { {4,1}, {0,0}, {4,2}, {1,1}, {7,1} },
            { {4,2}, {4,1}, {4,3}, {2,2}, {6,2} },
            { {4,3}, {4,2}, {0,0}, {3,3}, {5,3} },
            { {4,5}, {0,0}, {4,6}, {3,5}, {5,5} },
            { {4,6}, {4,5}, {4,7}, {2,6}, {6,6} },
            { {4,7}, {4,6}, {0,0}, {1,7}, {7,7} },
            { {5,3}, {0,0}, {5,4}, {4,3}, {0,0} },
            { {5,4}, {5,3}, {5,5}, {0,0}, {6,4} },
            { {5,5}, {5,4}, {0,0}, {4,5}, {0,0} },
            { {6,2}, {0,0}, {6,4}, {4,2}, {0,0} },
            { {6,4}, {6,2}, {6,6}, {5,4}, {7,4} },
            { {6,6}, {6,4}, {0,0}, {4,6}, {0,0} },
            { {7,1}, {0,0}, {7,4}, {4,1}, {0,0} },
            { {7,4}, {7,1}, {7,7}, {6,4}, {0,0} },
            { {7,7}, {7,4}, {0,0}, {4,7}, {0,0} }
        };
        
        Cell currCell;
        for (int index = 0; index < adjacency.length; index++) {
            currCell = createCell(adjacency[index][0][0], adjacency[index][0][1]);
            currCell.index = index;             // integer index
            currCell.label = cellMap[index];    // string label
            
            currCell.left = createCell(adjacency[index][1][0], adjacency[index][1][1]);
            currCell.right = createCell(adjacency[index][2][0], adjacency[index][2][1]);
            currCell.top = createCell(adjacency[index][3][0], adjacency[index][3][1]);
            currCell.bottom = createCell(adjacency[index][4][0], adjacency[index][4][1]);
        }
    }
    

    
    
    
    
    
    
    /* Get and return leftmost cell from current cell */
    private Cell leftMostCell(Cell cell) {
        Cell leftCell = cell;
        
        // Go all the way to the left
        while((leftCell != null) && (leftCell.left != null)) { leftCell = leftCell.left; }
        // return the left-most Cell
        return leftCell;
    }

    /* Get and return topmost cell from current cell */
    private Cell topMostCell(Cell cell) {
        Cell topCell = cell;
        
        // Go all the way to the top
        while((topCell != null) && (topCell.top != null)) { topCell = topCell.top; }
        // return the top-most Cell
        return topCell;
    }
    

    private void setToMill(Cell cell, String direction) {
        if (direction.equals("top")) {
            // set current cell and all top cells as part of a Mill
            while (cell != null) { cell.setMill(); cell = cell.top; }
        } else if (direction.equals("left")) {
            // set current cell and all left cells as part of a Mill
            while (cell != null) { cell.setMill(); cell = cell.left; }
        }
    }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board row/col position
     * @param cellAddress: 2-character string cell address
     * @param rowOrCol: "row" or "col" strings
     * @return integer: board Cell array row/col
     */
    private int getIntIndex(String cellAddress, String rowOrCol) {
        int addrIndex = -1;
        
        if (ROWADDRFIRST) {
            addrIndex = rowOrCol.equals("row") ? 0 : 1;
        } else {
            addrIndex = rowOrCol.equals("row") ? 1 : 0;
        }
        
        int cRow = LABELS.indexOf(cellAddress.charAt(addrIndex));
        cRow = (cRow >= 0) ? (cRow % DIMENSION) : cRow;
        
        return cRow + 1;
    }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board row position (2nd character)
     * @param cellAddress: cell address
     * @return integer: board Cell array row
     */
    private int getRow(String cellAddress) { return getIntIndex(cellAddress, "row"); }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board column position (1st character)
     * @param cellAddress: cell address
     * @return integer: board Cell array column
     */
    private int getCol(String cellAddress) { return getIntIndex(cellAddress, "col"); }
    
    /*
     * Check if 3 board cells on the same row form a mill, else
     * Check if 3 board cells in the same column form a mill
     * @param cell: most recently placed cell 
     */
    private boolean isNewMill(Cell cell, Player p) {
        Cell tCell = cell;
        int rowCount = 0;
        
        // Check right-to-left if MILL formed on row
        tCell = leftMostCell(cell);
        // Count 3 same-player cells to the right == MILL
        while(tCell != null) {        
            // check each right cell if belonging to same owner
            if ((tCell.owner != null) && (cell.owner != null)) {
                rowCount = ( tCell.owner.getName().equals(cell.owner.getName()) ) ? rowCount + 1 : rowCount;
            }
            tCell = tCell.right;
        }
        if (rowCount == 3) { setToMill(cell, "left"); }
        
        
        // Check top-to-bottom if MILL formed in column
        tCell = topMostCell(cell);
        int colCount = 0;
        // Count 3 same-player cells to the bottom == MILL
        while(tCell != null) {        
            // check each bottom cell if belonging to same owner
            if ((tCell.owner != null) && (cell.owner != null)) {
                colCount = ( tCell.owner.getName().equals(cell.owner.getName()) ) ? colCount + 1 : colCount;
            }
            tCell = tCell.bottom;
        }        
        if (colCount == 3) { setToMill(cell, "top"); }
        
        return ((rowCount == 3) || (colCount == 3));
    }
    
    /*
     * Set a given cell address as belonging to a player
     * @param player: player of class Player
     * @param dstCellAddr: cell coordinate, e.g. A1, C3, F4, etc.
     * @return None
     */
    public boolean placeMark(Player player, String dstCellAddr) {
        Cell cell = getCell(getRow(dstCellAddr), getCol(dstCellAddr));
        
        if ((cell != null) && !cell.isOccupied()) {
            cell.setOwner(player);      // take ownership of cell
            player.doPlace();           // update place count
            
            // Check if place formed a Mill
            if (isNewMill(cell, player)) {
                System.out.println("Formed MILL at " + dstCellAddr);
                setNewMill();
            }
            
            return true;
        } else if (cell == null) { System.out.println("Cell " + dstCellAddr + " is invalid!");
        } else if (cell.isOccupied()) { System.out.println("Cell " + dstCellAddr + " is occupied!");
        } else { System.out.println("PLACE:: Unhandled placeMark() error #1");
        }
        
        return false;
    }
    
    /*
     * Move a player's mark from one cell to another
     * @param player: player of class Player
     * @param srcCellAddr: source cell coordinate, e.g. A1, C3, F4, etc.
     * @param dstCellAddr: destination cell coordinate, e.g. A1, C3, F4, etc.
     * @return boolean true/false for successful/failed move
     */
    public boolean moveMark(Player player, String srcCellAddr, String dstCellAddr) {
        boolean status = false;
        
        Cell dstCell = getCell(getRow(dstCellAddr), getCol(dstCellAddr));
        Cell srcCell = getCell(getRow(srcCellAddr), getCol(srcCellAddr));
        
        if ((srcCell != null) && (dstCell != null)) {
            if (player.isOwner(srcCell) && !dstCell.isOccupied()) {
                dstCell.setOwner(player);       // Update destination owner
                srcCell.setEmpty();             // Clear source owner
                status = true;
                
                // Check if move formed a Mill
                if (isNewMill(dstCell, player)) {
                    System.out.println("Formed MILL at " + dstCellAddr);
                    //removeMark(player, getCell(getRow("e4"), getCol("e4")));
                }
            } else if (!player.isOwner(srcCell)) {
                System.out.println("Wrong owner for cell " + srcCellAddr + "!");
            } else if (dstCell.isOccupied()) {
                System.out.println("Cell " + dstCellAddr + " is not empty!");
            }
        } else {
            if (srcCell == null) { System.out.println("Cell " + srcCellAddr + " is invalid!");
            } else if (dstCell == null) { System.out.println("Cell " + dstCellAddr + " is invalid!");
            } else { System.out.println("MOVE:: Unhandled moveMark() error #1");
            }
        }
        
        return status;
    }
    
    public boolean removeMark(Player p, Cell c) {
        if (c.isOccupied() && (!c.owner.getName().equals(p.getName()))) {
            c.setEmpty();
            p.killMan();
        }
        return false;
    }
    

    /* Adjust an row-/col-index for proper base-0 indexing into board array */
    private int adjustIndex(int index) { return index - 1; }
    
    /*
     * Create a cell at board's row and column 
     * @param boardRow: integer type row# (base-1)
     * @param boardCol: integer type column# (base-1)
     */
    private Cell createCell(int boardRow, int boardCol) {
        Cell cell = null;
        
        if (isValidCellAddr(boardRow, boardCol)) {
            cell = getCell(boardRow, boardCol);
        
            if (cell == null) { cell = new Cell(); }
        
            board[adjustIndex(boardRow)][adjustIndex(boardCol)] = cell;
        }
        return cell;
    }
    
    /* Check if row and column coordinates are valid */
    private boolean isValidCellAddr(int boardRow, int boardCol) {
        return (boardRow > 0) && (boardCol > 0);
    }
    
    /*
     * Given a 1-based row and column address, get the board cell
     * referenced by the row and column parameters
     * @param boardRow: integer type row# (base-1)
     * @param boardCol: integer type column# (base-1)
     * @return: referenced board cell 
     */
    private Cell getCell(int boardRow, int boardCol) {
        Cell cell = null;
        
        if (isValidCellAddr(boardRow, boardCol)) {
            cell = board[adjustIndex(boardRow)][adjustIndex(boardCol)];
        }
        return cell;
    }
    
    /* Print game board column header */
    public void printColumnHeader(int spacer) {
        String str = " ";
        int chOffset = colIsNumeric ? DIMENSION : 0;
        
        // print column headers
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", repeatChar(' ', spacer), 
                        Character.toString(LABELS.charAt(i + chOffset)));
        }
        System.out.println(str);
    }
    
    /* Console-based board printing for non-gui debug */
    public void printBoard() {
        int numSpacer = 1;
        int rowChOffset = rowIsNumeric ? DIMENSION : 0;
        printColumnHeader(numSpacer);
        
        String str = "";
        // print column headers
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", Character.toString(LABELS.charAt(i + rowChOffset)), 
                        repeatChar(' ', numSpacer));
            
            for (int j = 0; j < DIMENSION; j++) {
                if (board[i][j] != null) {
                    str += String.format("%s", board[i][j].getStateChar());
                } else {
                    str += " ";
                }
                
                if (j < DIMENSION - 1) {
                    str += repeatChar(' ', numSpacer);
                }
            }
            System.out.println(str);
            str = "";
        }
        System.out.println();
    }
    
    /* Useful for console printing of a character a certain number of times */
    private static final String repeatChar(char c, int length) {
        char[] data = new char[length];
        Arrays.fill(data, c);
        return new String(data);
    }
    
    public boolean hasNewMill() { return (boardStatus == PlaceOrMoveStates.FORMEDMILL); }
    public void setNewMill() { boardStatus = PlaceOrMoveStates.FORMEDMILL; }
    public void clearStatus() { boardStatus = PlaceOrMoveStates.UNINITIALIZED; }
    
    /*
     * Given a player p, get all cells belonging p's opponent
     */
    public String getOpponentCells(Player p) {
        String cellLabels = "";
        Cell c;
        
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                c = board[i][j];
                
                if ((c == null) || c.isEmpty()) {
                    // skip all invalid cell iterations
                    continue;
                } else if (!c.owner.getName().equals(p.getName())) {
                    // a1,b1,c3,etc
                    cellLabels = (cellLabels.length() == 0) ? c.label : cellLabels + "," + c.label;
                }
            }
        }
        
        return cellLabels;
    }
}
