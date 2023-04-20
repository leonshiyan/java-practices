package turing;

public class Tape {
    private Cell currentCell;
    
    public Tape() {
        currentCell = new Cell();
        currentCell.content = ' ';
    }

}
