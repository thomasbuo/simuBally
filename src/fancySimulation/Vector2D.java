package fancySimulation;

/**
 * Created by sandersalahmadibapost on 26/09/2018.
 */

public class Vector2D {

    private double x;
    private double y;
    private double magnitude;

    public Vector2D(double x, double y){

        this.x = x;
        this.y = y;
        this.magnitude = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));

    }

    public void setX(double x){

        this.x = x;
        this.magnitude = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));


    }

    public void setY(double y){

        this.y = y;
        this.magnitude = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));


    }

    public double getX(){

        return x;

    }

    public double getY(){

        return y;

    }

    public double getMagnitude(){

        return magnitude;

    }

    public Vector2D cloneVector(){

        Vector2D vector = new Vector2D(this.x,this.y);
        return vector;

    }

}
