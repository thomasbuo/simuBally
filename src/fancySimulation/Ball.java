package fancySimulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sandersalahmadibapost on 29/09/2018.
 */

//TODO:
    //model the ball
    // calculate it's x and y components
    // once x and y components fall out of line with each other, ball will be launched

public class Ball {

    final private double M = 0.00232;
    final private double G = 9.81;
    final private double Cd = 0.47;
    final private double DENSITY = 1.225;
    private double x;
    private double realX;
    private double y;
    private double realY;
    private double vx;
    private double vy;
    private ArrayList<Vector2D> accelerations = new ArrayList<Vector2D>();
    private double magnitude;
    private double angle;
    public Simulation sim;

    public Ball(Simulation sim, double x, double y, double vx, double vy){
        this.sim = sim;
        this.x = x;
        this.realX = x/1000;
        this.y = y;
        this.realY = y/1000;
        this.vx = vx;
        this.vy = vy;
    }


    public double resolveForces(Vector2D v){

        Vector2D temp = new Vector2D(0,0);
        double y = (M*sumAccelerations().getY())-(M*G);

        temp.setX(v.getX());

        temp.setY(Math.sqrt((2*y)/M)) ;

        magnitude = Math.sqrt(Math.pow(temp.getX(),2)+Math.pow(temp.getY(),2));

        angle = Math.atan(temp.getY()/temp.getX());

        return angle;

    }

    //convert drag to vector and perform drag on both the x and the y components

    public Vector2D calcDrag(){
        Vector2D drag = new Vector2D(0,0);

        drag.setX((0.5 * DENSITY * Math.pow(vx,2)*Cd*Math.PI* (Math.pow(0.022,2)/4))/M);
        drag.setY((0.5 * DENSITY * Math.pow(vy,2)*Cd*Math.PI* (Math.pow(0.022,2)/4))/M);
//        System.out.println("THIS IS THE MAGNITUDE OF THE VELOCITY OF THE BALL : " + magnitude);
//        System.out.println("DRAG HAS BEEN CALCULATED AD IT'S X COMPONENT VALUE IS : " + drag.getX());
//        System.out.println("DRAG HAS BEEN CALCULATED AD IT'S Y COMPONENT VALUE IS : " + drag.getY());



        return drag;
    }

    public void addAcceleration(Vector2D a){
        accelerations.add(a);
    }

    public Vector2D sumAccelerations(){
        double ax = 0;
        double ay = 0;
        for (int i = 0; i < this.accelerations.size(); i++) {
            ax += this.accelerations.get(i).getX();
            ay += this.accelerations.get(i).getY();
        }
        this.accelerations.clear();
        return new Vector2D(ax,ay);
    }

    public void updateVelocity(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void updatePosition(double x, double y){
        this.x = x;
        this.realX = x/1000;
        this.y = y;
        this.realY = y/1000;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy(){
        return vy;
    }

    public Vector2D getVelocity(){
        return new Vector2D(vx, vy);
    }

    public Vector2D getPosition(){
        return new Vector2D(x,y);
    }

    public Vector2D getRealPosition() {return new Vector2D(realX,realY);}

    public double getMagnitude(){
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /*public void addDrag(Vector2D drag){
        System.out.println("THIS IS THE BALL'S VELOCITY X COMPONENT BEFORE DRAG IS ADDED : " + this.vx);
        System.out.println("THIS IS THE BALL'S VELOCITY Y COMPONENT BEFORE DRAG IS ADDED : " + this.vy);
        this.vx = drag.getX() * this.vx;
        this.vy = drag.getY() * this.vy;
        System.out.println("THIS IS THE BALL'S VELOCITY X COMPONENT AFTER DRAG IS ADDED : " + this.vx);
        System.out.println("THIS IS THE BALL'S VELOCITY Y COMPONENT AFTER DRAG IS ADDED : " + this.vy);
    }*/

}
