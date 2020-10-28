/*
 * File: LifeSimulation.java
 * Author: Will Fitch
 * Date: 02/23/2020
 */

import java.util.Random;

public class LifeSimulation {

    //this main function will set up and run the program
    public static void main(String[] args) throws InterruptedException {

        int rows;
        int cols;

        try{
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        } catch (Exception e) {

            rows = 100;
            cols = 100;

        }

        Landscape scape = new Landscape(rows,cols);
        Random gen = new Random();
        double density = .3;

        // initialize the grid to be 30% full
        for (int i = 0; i < scape.getRows(); i++) {
            for (int j = 0; j < scape.getCols(); j++ ) {
                scape.getCell( i, j ).setAlive( gen.nextDouble() <= density );
            }
        }

        LandscapeDisplay display = new LandscapeDisplay(scape, 5);

        while(true) {

            display.repaint();

            scape.advance();

            Thread.sleep(display.getSpeed());

        }

    }

}
