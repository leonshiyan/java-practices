package turing;
/**
 * A class representing a Turing machine tape, which is a doubly-linked list of cells.
 * The tape is used for both input and output, and each cell can hold one character.
 * One cell on the tape is considered to be the current cell, where the machine is located.
 * As a Turing machine computes, it moves back and forth along the tape and the current cell changes.
 */
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
    
    public void moveRight() {
        if (currentCell.next == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.prev = currentCell;
            currentCell.next = newCell;
        }
        currentCell = currentCell.next;
    }
    
    public String getTapeContents() {
        Cell startCell = currentCell;
        while (startCell.prev != null) {
            startCell = startCell.prev;
        }
        StringBuilder sb = new StringBuilder();
        boolean startAdding = false;
        while (startCell != null) {
            if (!startAdding && startCell.content != ' ') {
                startAdding = true;
            }
            if (startAdding) {
                sb.append(startCell.content);
            }
            startCell = startCell.next;
        }
        return sb.toString();
    }
    
}
