package board;

import java.util.ArrayList;

public class Player {
    private final int MEN = 3;
    private char mark = 'M';
    private char name = 'N';
    public int placeCount = MEN;
    public int manCount = 0;
    private ArrayList<Cell> rowMill;
    private ArrayList<Cell> colMill;
    
    /* Add bottom-most cell as tail of mills */
    public void addRowMill(Cell rightCell) { rowMill.add(rightCell); }
    public void addColMill(Cell bottomCell) { colMill.add(bottomCell); }
    
    /*
     * Constructor for Player class
     * @param pName: player name character
     * @param pMark: player mark character
     */
    public Player(char pName, char pMark) {
        setName(pName);
        setMark(pMark);
        manCount = placeCount;
        
        rowMill = new ArrayList<Cell>();
        colMill = new ArrayList<Cell>();
    }
    
    
    public boolean isOwner(Cell c) { return (c != null) && (c.owner != null) && (getName().equals(c.owner.getName())); }
    
    private void setMark(char pMark) { mark = pMark; }
    public String getMark() { return Character.toString(mark); }
    
    private void setName(char pName) { name= pName; }
    public String getName() { return Character.toString(name); }
    
    public void doPlace() { placeCount--; }
    public boolean canPlace() { return (placeCount > 0); }
    public boolean canFly() { return (manCount == 3); }
    public boolean canMove() { return (manCount > 0); }
    
    public void killMan() { manCount--; }
    
}
