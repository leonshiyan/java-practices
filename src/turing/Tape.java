package turing;

public class Tape {
    private Cell currentCell;
    
    public Tape() {
        currentCell = new Cell();
        currentCell.content = ' ';
    }
    public Cell getCurrentCell() {
        return currentCell;
    }
    public char getContent() {
        return currentCell.content;
    }
}
