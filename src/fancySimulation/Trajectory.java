package fancySimulation;

/**
 * Created by sandersalahmadibapost on 28/09/2018.
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Trajectory {

    //gravity vector in metres per millisecond
    private static final Vector2D GRAVITY = new Vector2D(0, -9.81/1000);
    //weight of the ball
    private static final double TIMESTEP = 1;
    private Vector2D velocity;
    private Vector2D position;
    private Vector2D realPos;
    private Ball ball;
    double distance;
    double maxDistance;
    Target target;


    public Trajectory(Vector2D velocity, Vector2D position, Vector2D realPos, Target target, Ball ball) {

        this.velocity = velocity;
        this.position = position;
        this.realPos = realPos;
        this.maxDistance = 0;
        this.target = target;
        this.ball = ball;

    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);

        //clone vectors for manipulation
        Vector2D drawVel = ball.getVelocity();
        //drawVel = drawVel.factorVector(1000);
        Vector2D drawPos = ball.getPosition();
        //drawPos = drawPos.factorVector();
        double maxY = 0;
        double maxX = 0;

        while (drawPos.getY() >= 0){

            vectorAdd(drawVel, TIMESTEP, GRAVITY);
            vectorAdd(drawPos, TIMESTEP, drawVel);
            //vectorAdd(drawPos, TIMESTEP, new Vector2D(-ball.calcDrag().getX(), -ball.calcDrag().getY()));


            g2d.fill(new Ellipse2D.Double(drawPos.getX(), Simulation.FLOORHEIGHT - drawPos.getY(), 4, 4));

            if(drawPos.getY() > maxY){

                maxY = drawPos.getY();
                maxX = drawPos.getX();

            }


        }

        setInfo();

        this.distance = Math.round(distance * 100.0) / 100.0;
        g2d.drawString("Current distance: "+distance+"m", (int)maxX-50, (int)(Simulation.FLOORHEIGHT - (maxY+30)));


        if(distance > maxDistance){

            maxDistance = distance;

        }
    }

    private static void vectorAdd(Vector2D result, double factor, Vector2D addend){

        double x = result.getX() + factor * addend.getX();
        double y = result.getY() + factor * addend.getY();
        result.setX(x);
        result.setY(y);

    }

    public double getDistance(){
        return distance;
    }



    public void setInfo(){

        double angle = Math.atan(ball.getVx()/ball.getVy());

        double vX = ball.getMagnitude()*Math.cos(angle);
        double vY = ball.getMagnitude()*Math.sin(angle);

        double tRise = vY/9.81;
        double height = realPos.getY() + vY*tRise - 0.5 * 9.81 * tRise*tRise;
        double tFall = Math.sqrt(2*height/9.81);

        double range = (tFall+tRise)*vX;

        this.distance = range+realPos.getX();
        System.out.println("this is the range "+ distance);

    }

}
