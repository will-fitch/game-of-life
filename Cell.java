/*
 * File: Cell.java
 * Author: Will Fitch
 * Date: 02/23/2020
 */

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class Cell {

    private boolean alive;

    public Cell() {

        alive = false;

    }

    public Cell(boolean alive) {

        this.alive = alive;

    }


    public Cell(boolean alive, ArrayList<Cell> neighbors) {

        this.alive = alive;
        updateState(neighbors);

    }

    //returns true if the cell is alive, false otherwise
    public boolean getAlive() {

        return alive;

    }

    //sets the cell to be alive or dead
    public void setAlive(boolean alive) {

        this.alive = alive;

    }

    //resets the cell to be dead
    public void reset() {

        alive = false;

    }

    //takes a list of the cells neighbors and updates its states
    public void updateState( ArrayList<Cell> neighbors ) {

        short aliveNeighbors = 0;

        for(Cell cell : neighbors) {
            if(cell.getAlive()) {

                aliveNeighbors++;

            }
        }

        if(alive && aliveNeighbors != 2 && aliveNeighbors != 3) {
            alive = false;
        } else if (!alive && aliveNeighbors == 3) {
            alive = true;
        }

    }

    //returns a String representation of the Cell
    public String toString() {

        if (alive) {
            return "X";
        }

        return "O";

    }

    //draws the cell on the screen
    public void draw(Graphics g, int y, int x, int scale, boolean drawGrid, Color[] colors) {

        if(alive) {
            g.setColor(colors[0]);
        } else {
            g.setColor(colors[1]);
        }

        g.fillRect(x*scale, y*scale, scale, scale);

        if(drawGrid) {
            g.setColor(colors[2]);
            g.drawLine(x*scale, y*scale, x*scale+scale, y*scale);
            g.drawLine(x*scale, y*scale, x*scale, y*scale+scale);
        }

    }
}
