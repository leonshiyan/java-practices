package turing;
import turing.Cell;

public class Tape {

    private Cell currentCell;

    public Tape() {
        Cell newCell = new Cell();
        newCell.content = ' ';
        newCell.next = null;
        newCell.prev = null;
        currentCell = newCell;
    }

    public Cell getCurrentCell() {
    // returns the pointer that points to the current cell.
        return currentCell;
    }

    public char getContent() {
    // returns the char from the current cell
        return currentCell.content;
    }

    public void setContent(char ch) {
    // changes the char in the current cell to the specified value
        currentCell.content = ch;
    }

    public void moveLeft() {
    // moves the current cell one position to the left along the tape
        if (currentCell.prev == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.prev = null;
            newCell.next = currentCell;
            currentCell.prev = newCell;
        }
        currentCell = currentCell.prev;
    }

    public void moveRight() {
    // moves the current cell one position to the right along the tape.
        if (currentCell.next == null) {
            Cell newCell = new Cell();
            newCell.content = ' ';
            newCell.next = null;
            newCell.prev = currentCell;
            currentCell.next = newCell;
        }
        currentCell = currentCell.next;
    }

    public String getTapeContents() {
    // returns a String consisting of the chars from all the cells on the tape, read from left to right, except that
    // leading or trailing blank characters should be discarded.
        Cell pointer = currentCell;
        String tapeContents = "";

        // Rewind to the start of the Tape
        while (pointer.prev != null) {
            pointer = pointer.prev;
        }

        while (pointer != null) {
            tapeContents += pointer.content;
            pointer = pointer.next;
        }
        tapeContents = tapeContents.trim();
        return tapeContents;
   }
}