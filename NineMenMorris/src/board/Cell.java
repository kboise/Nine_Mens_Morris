package board;

public class Cell {
    public String label = "xx";
    public int index = -1;
    public int millCount = 0;       // Number of Mills to which a cell belongs 
    
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
    public boolean setOwner(Player p) { 
        if ( !isInvalid() && !isOccupied() ) {
            owner = p;
            setOccupied();
            return true;
        }
        return false;
    }
    
    public void clearMill()   { millCount--; }
    public void setMill()     { millCount++; }
    public boolean isInMill() { return (millCount > 0); }
    
    public boolean isInvalid() { return (state == CellState.INVALID); }
    public void setInvalid() { state = CellState.INVALID; }
    
    public boolean isEmpty() { return (state == CellState.EMPTY); }
    public void setEmpty() { state = CellState.EMPTY; owner = null; }
    
    public boolean isOccupied() { return (state == CellState.OCCUPIED); }
    public void setOccupied() { state = CellState.OCCUPIED; }
    
    public boolean hasOwner() { return (owner != null); }
    public boolean matchOwner(Player p) {
        return ((p != null) && (owner != null) && 
                (owner.getName().equals(p.getName()))
                );
    }
    
    public String getStateChar() {
        String stateStr;
        
        if (state == CellState.OCCUPIED) { stateStr = owner.getMark();
        } else if (state == CellState.EMPTY) { stateStr = "-";
        } else { stateStr = " ";
        }
        
        return stateStr;
    }
}
