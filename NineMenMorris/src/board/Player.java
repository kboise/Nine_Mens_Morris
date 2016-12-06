package board;

import java.util.ArrayList;

public class Player {
    private final int MEN = 9;
    private char mark = 'M';
    private char name = 'N';
    public int placeCount = MEN;
    public int manCount = 0;
    public Player opponent = null;
    
    /*
     * Constructor for Player class
     * @param pName: player name character
     * @param pMark: player mark character
     */
    public Player(char pName, char pMark) {
        setName(pName);
        setMark(pMark);
        manCount = placeCount;
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
    public boolean hasLost() { return (manCount < 3); }
    
    public void killMan() { manCount--; }

    public int getPlaceCount() { return placeCount; }
    
    public void printStats() {
        System.out.println("Player-" + getName() + ":: CanPlace [" + placeCount + "], CanMove [" + manCount + "]");
    }
}
