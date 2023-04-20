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
    public void setContent(char ch) {
        currentCell.content = ch;
    }
    public void moveLeft() {
        if (currentCell.prev == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.next = currentCell;
            currentCell.prev = newCell;
        }
        currentCell = currentCell.prev;
    }
}
