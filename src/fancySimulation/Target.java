package fancySimulation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by sandersalahmadibapost on 02/10/2018.
 */
public class Target {

    private int distance = 800;
    private int height = 200;
    private int width = 100;
    private int startX = 250;

    public Target(double distance, double height, double width) {
        this.distance = (int) distance * 1000;
        this.height = (int) height * 1000;
        this.width = (int) width * 1000;
    }

}
