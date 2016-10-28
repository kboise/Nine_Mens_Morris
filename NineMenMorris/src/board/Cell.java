package board;

public class Cell {
    public boolean mill = false;
    
    public Player owner = null;
    public enum CellState {
        INVALID, EMPTY, OCCUPIED
    }
    public CellState state = CellState.INVALID;
    
    public Cell left = null;
    public Cell right = null;
    public Cell top = null;
    public Cell bottom = null;
    
    public Cell() { setInvalid(); setEmpty(); }
    
    /* Set owner of a cell to given player */
    public void setOwner(Player p) { 
        if ( !isInvalid() && !isOccupied() ) {
            owner = p;
            setOccupied();
        }
    }
    
    public void clearMill()   { mill = false; }
    public void setMill()     { mill = true; }
    public boolean isInMill() { return mill; }
    
    public boolean isInvalid() { return (state == CellState.INVALID); }
    public void setInvalid() { state = CellState.INVALID; }
    
    public boolean isEmpty() { return (state == CellState.EMPTY); }
    public void setEmpty() { state = CellState.EMPTY; }
    
    public boolean isOccupied() { return (state == CellState.OCCUPIED); }
    public void setOccupied() { state = CellState.OCCUPIED; }
    
    public char getStateChar() {
        char stateCh;
        
        switch (state) {
            case OCCUPIED:
                stateCh = owner.getMark();
                break;
            case EMPTY:
                stateCh = '-';
                break;
            default:
                stateCh = ' ';
                break;
        }
        
        return stateCh;
    }
}
