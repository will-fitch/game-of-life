/*
 * File: Landscape.java
 * Author: Will Fitch
 * Date: 02/23/2020
 */

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class Landscape {

    private int rows;
    private int cols;
    private Cell[][] grid;
    private boolean drawGrid, running;

    private Color[] colors;

    public Landscape( int rows, int cols ) {

        this.rows = rows;
        this.cols = cols;
        grid = new Cell[rows][cols];
        drawGrid = false;
        running = true;

        colors = new Color[3];
        colors[0] = Color.green;
        colors[1] = Color.black;
        colors[2] = Color.red;

        for(int i = 0; i<rows; i++) {
            for(int j = 0; j<cols; j++) {

                grid[i][j] = new Cell();

            }
        }


    }

    //changes whether the cells are being updated
    public void updateRunning(boolean running) {

        this.running = running;

    }

    //toggles whether the grid is shown
    public boolean toggleGrid() {

        drawGrid = !drawGrid;
        return drawGrid;

    }

    //sets the color of the alive cells
    public void setAliveColor(Color c) {
        colors[0] = c;
    }

    //sets the color of the dead cells
    public void setDeadColor(Color c) {
        colors[1] = c;
    }

    //sets the color of the grid
    public void setGridColor(Color c) {
        colors[2] = c;
    }

    //advances the cells on the grid IF running is true.
    public void advance() {

        if(running) {

            Cell[][] tempGrid = new Cell[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {

                    tempGrid[i][j] = new Cell(grid[i][j].getAlive(), getNeighbors(i, j));

                }
            }

            grid = tempGrid;

        }
    }

    //resets the grid to be empty
    public void reset() {

        for(Cell[] cellArray : grid) {
            for(Cell cell : cellArray) {

                cell.reset();

            }
        }

    }

    //returns the number of rows in the grid
    public int getRows() {

        return rows;

    }

    //returns the number of cols in the grid
    public int getCols() {

        return cols;

    }

    //returns the cell at the given row and col
    public Cell getCell( int row, int col ) {

        if(row<rows && col<cols) {
            return grid[row][col];
        }
        return null;

    }

    //returns a String representation of Landscape
    public String toString() {

        String returnString = "";

        for(Cell[] cellArray : grid) {
            for(Cell cell : cellArray) {

                returnString += cell.toString();

            }
            returnString += "\n";
        }

        return returnString;

    }

    //returns an ArrayList of the cells around the cell at the given row and col
    public ArrayList<Cell> getNeighbors( int row, int col ) {

        ArrayList<Cell> neighbors = new ArrayList<Cell>();

        for( int r = Math.max( 0, row-1 ); r <= Math.min( row+1, rows-1 ); r++ ) {
            for( int c = Math.max( 0, col-1 ); c <= Math.min( col+1, cols-1 ); c++ ) {
                if( !(r==row && c==col) ) {
                    neighbors.add( grid[r][c] );
                }
            }
        }

        return neighbors;

    }


    //draws the grid of cells
    public void draw(Graphics g, int scale) {

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j ++) {

                grid[i][j].draw(g, i, j, scale, drawGrid, colors);

            }
        }

    }

}
