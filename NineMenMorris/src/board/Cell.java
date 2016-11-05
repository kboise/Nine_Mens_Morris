package board;

public class Cell {
    public String label = "xx";
    public int index = -1;

    //private int millCount = 0;       // Number of Mills to which a cell belongs
    public boolean rowMill;
    public boolean columnMill;
    
    public Player owner = null;
    private enum CellState {
        INVALID, EMPTY, OCCUPIED
    }
    private CellState state = CellState.INVALID;
    
    public Cell left = null;
    public Cell right = null;
    public Cell top = null;
    public Cell bottom = null;
    
    public Cell() { setEmpty(); }
    
    public String getStateChar() {
        String stateStr;
        
        if (state == CellState.OCCUPIED) { stateStr = owner.getMark();
        } else if (state == CellState.EMPTY) { stateStr = "-";
        } else { stateStr = " ";
        }
        
        return stateStr;
    }
    
    /* Set owner of a cell to given player */
    public boolean setOwner(Player p) { 
        if ( !isOccupied() ) {
            owner = p;
            setOccupied();
            return true;
        }
        return isOccupied();
    }
    
    public void clearRowMill() { rowMill = false; }
    public void clearColumnMill() { columnMill = false; }
    public void setRowMill() { rowMill = true; }
    public void setColumnMill() { columnMill = true; }
    public boolean isInMill() { return (rowMill || columnMill); }
    
    public boolean isInvalid() { return (state == CellState.INVALID); }
    public void setInvalid() { state = CellState.INVALID; }
    
    public boolean isEmpty() { return (state == CellState.EMPTY); }

    public void setEmpty() { state = CellState.EMPTY; clearOwner(); }
    
    public boolean isOccupied() { return (state == CellState.OCCUPIED); }
    public void setOccupied() { state = CellState.OCCUPIED; }
    
    public boolean hasOwner() { return (owner != null); }
    public void clearOwner() { owner = null; }
    public boolean matchOwner(Player p) {
        return ((p != null) && (owner != null) && 
                (owner.getName().equals(p.getName()))
                );
    }
    
    /* Check if Cell has an empty neighbor */
    public boolean hasOpenNeighbor() {
        boolean status = false;
        
        status |= (left != null) && !left.hasOwner();
        status |= (right != null) && !right.hasOwner();
        status |= (top != null) && !top.hasOwner();
        status |= (bottom != null) && !bottom.hasOwner();
        
        return status;
    }
}
