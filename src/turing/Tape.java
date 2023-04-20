package turing;
/**
 * A class representing a Turing machine tape, which is a doubly-linked list of cells.
 * The tape is used for both input and output, and each cell can hold one character.
 * One cell on the tape is considered to be the current cell, where the machine is located.
 * As a Turing machine computes, it moves back and forth along the tape and the current cell changes.
 */
public class Tape {
    /** A pointer to the current cell on the tape. */
    private Cell currentCell;
    
    /**
     * Constructs a new tape with a single cell that contains a blank space,
     * and sets the current cell pointer to it.
     */
    public Tape() {
        currentCell = new Cell();
        currentCell.content = ' ';
    }
    
    /**
     * Returns the pointer to the current cell on the tape.
     * @return the pointer to the current cell
     */
    public Cell getCurrentCell() {
        return currentCell;
    }
    
    /**
     * Returns the character from the current cell on the tape.
     * @return the character from the current cell
     */
    public char getContent() {
        return currentCell.content;
    }
    
    /**
     * Changes the character in the current cell on the tape to the specified value.
     * @param ch the new character to set in the current cell
     */
    public void setContent(char ch) {
        currentCell.content = ch;
    }
    
    /**
     * Moves the current cell one position to the left along the tape.
     * If the current cell is the leftmost cell that exists, then a new cell
     * is created and added to the tape at the left of the current cell, and
     * then the current cell pointer is moved to point to the new cell.
     * The content of the new cell is a blank space.
     */
    public void moveLeft() {
        if (currentCell.prev == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.next = currentCell;
            currentCell.prev = newCell;
        }
        currentCell = currentCell.prev;
    }
    
    /**
     * Moves the current cell one position to the right along the tape.
     * If the current cell is the rightmost cell that exists, then a new cell
     * is created and added to the tape at the right of the current cell, and
     * then the current cell pointer is moved to point to the new cell.
     * The content of the new cell is a blank space.
     */
    public void moveRight() {
        if (currentCell.next == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.prev = currentCell;
            currentCell.next = newCell;
        }
        currentCell = currentCell.next;
    }
    
    /**
     * Returns a String consisting of the characters from all the cells on the tape,
     * read from left to right, except that leading or trailing blank characters
     * are discarded. The current cell pointer is not moved by this method; it
     * points to the same cell after the method is called as it did before.
     * @return a String representing the contents of the tape
     */
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
