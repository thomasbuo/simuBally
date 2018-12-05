package fancySimulation;

/**
 * Created by sandersalahmadibapost on 26/09/2018.
 */

//TODO:
//separate method to check if target has been reached and update boolean value every time step
// find out how to incorporate height of the box into trajectory calculations
// think about adding gravity to velocity some more
// check the set info methods???


//sum forces in here and update velocity of the projectile


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Simulation extends JPanel{

    private final int TIMESTEPS = 10;
    private final double TIMESTEPSIZE = 15;

    private Trajectory currentTrajectory;
    //public static Ball ball;

    private int startAngle1Value;
    private int startAngle2Value;
    private int endAngle1Value;
    private int endAngle2Value;
    private int triggerAngle;
    private int timer1Value;
    private double distance;
    private double trajDistance;
    private double targetDistance;
    private double targetHeight;
    private double targetWidth;
    private double magnitude;
    private double angle;
    private double currentVelocity;
    //angle of resulting velocity of the end effector
    private double a;
    //angle of the resulting velocity of the ball
    private double b;
    private boolean targetReached = false;

    private Font small;
    private Font big;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1300;
    public static final int FLOORHEIGHT = HEIGHT - 40;

    private static final int startX = 250;
    private Arm arm1 = new Arm(startX,0,220,90);
    private Arm arm2 = new Arm(arm1,120,0);
    private Arm arm3 = new Arm(arm2,170,0);
    private Target target = new Target(targetDistance, targetHeight, targetWidth);


    public Simulation() {

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        arm2.setName("Arm2");
        arm3.setName("Arm3");
        //ball = new Ball(this, this.arm3.getRealEndPos().getX(), this.arm3.getRealEndPos().getY(),getRealCurrentVelocity().getX(),getRealCurrentVelocity().getY());
    }

    @Override
    protected void paintComponent(Graphics g) {

        if(small == null){
            small = this.getGraphics().getFont();
            big = small.deriveFont(small.getSize() * 1.4F);
        }

        super.paintComponent(g);

        drawFloor(g);
        drawDistance(g);
        //calcDistance();
        drawTarget(g);

        arm1.draw(g);
        arm2.draw(g);
        arm3.draw(g);
        //diff between the angles of the resulting velocities of the ball and the end effector of the robot
        double diff = Math.abs(a-b);

        System.out.println("this is diff : " + diff);
        //check if the angles are misaligned
        if(currentTrajectory!=null && diff > 0.75 && diff < 1.00) {
            //remember to change this method so that it does not multiply by magnitude
            currentTrajectory.draw(g);
            this.trajDistance = currentTrajectory.getDistance();
        }

        Toolkit.getDefaultToolkit().sync();

    }

    // take note that i changed the dimensions of the whole simulation by changing the scale of the floor
    // change the dimensions of the robot arm appropriately
    // and make sure to standardize dimensions for usage with equation calculation too

    public void drawFloor(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        Line2D.Double line = new  Line2D.Double(-1000, FLOORHEIGHT,10000,FLOORHEIGHT);
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.WHITE);
        g2d.draw(line);
        g2d.setFont(small);

        for (float i=0;i<=1.1;i+=0.1){
            double roundOff = Math.round(i * 100.0) / 100.0;
            g2d.drawString("|", startX + (i*1000),FLOORHEIGHT+5);
            g2d.drawString(roundOff+"m", startX + (i*1000) - 13,FLOORHEIGHT+30);
        }

    }

    public void drawDistance(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString("Distance = " +trajDistance+ "m", 100, 400);
    }

    public void drawTarget(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        if(targetReached == true){
            g2d.setColor(Color.red);
            repaint();

        }else {
            g2d.setColor(Color.red);
            repaint();

        }
        g2d.fill(new Rectangle2D.Double(startX+targetDistance, FLOORHEIGHT-targetHeight, targetWidth, targetHeight));


    }

    public void setStartAngle1Value(int startAngle1Value) {
        this.startAngle1Value = startAngle1Value;
        arm2.setStartAngle(startAngle1Value);
    }

    public void setStartAngle2Value(int startAngle2Value) {
        this.startAngle2Value = startAngle2Value;
        arm3.setStartAngle(startAngle2Value);
    }

    public void setEndAngle1Value(int endAngle1Value) {
        this.endAngle1Value = endAngle1Value;
        arm2.setEndAngle(endAngle1Value);
    }

    public void setEndAngle2Value(int endAngle2Value) {
        this.endAngle2Value = endAngle2Value;
        arm3.setEndAngle(endAngle2Value);
    }

    public void setTriggerAngle(int triggerAngle){
        this.triggerAngle = triggerAngle;
    }

    public  double getTargetDistance() {
        return targetDistance;
    }

    public double getTargetHeight() {
        return targetHeight;
    }

    public double getTargetWidth() {
        return targetWidth;
    }

    public void setTargetDistance(double targetDistance){
        this.targetDistance = targetDistance;
    }

    public void setTargetHeight(double targetHeight){
        this.targetHeight = targetHeight;
    }

    public void setTargetWidth(double targetWidth){
        this.targetWidth = targetWidth;
    }

    public int getStartAngle1Value() {
        return startAngle1Value;
    }

    public int getStartAngle2Value() {
        return startAngle2Value;
    }

    public int getEndAngle1Value() {
        return endAngle1Value;
    }

    public int getEndAngle2Value() {
        return endAngle2Value;
    }

    public int getTriggerAngle() {
        return triggerAngle;
    }

    public void setTargetReached(boolean targetReached) {
        this.targetReached = targetReached;
    }

    // find x and y components of velocity
    // dx = -l1*da*sin(a) - l2(da + db)*sin(a+b)
    // dy = l1*da*cos(a) + l2(da + db)*cos(a+b)
    // where:
    // dx - x component of velocity
    // dy - y component of velocity
    // da - differentiation of angle of joint a w.r.t time
    // db - differentiation of angle of joint b w.r.t time

    public Vector2D getRealCurrentVelocity(){

        double newXOne = (-arm1.getRealLen()*Math.sin(Math.toRadians(arm1.getSelfAngle())) - arm2.getRealLen()*Math.sin(Math.toRadians(arm1.getSelfAngle()+arm2.getSelfAngle())))* arm1.getRealAngularVelocity();
        double newXTwo = (-arm2.getRealLen()*Math.sin(Math.toRadians(arm1.getSelfAngle()+arm2.getSelfAngle())))*arm2.getRealAngularVelocity();
        double x = newXOne+newXTwo;

        double newYOne = (arm1.getRealLen()*Math.cos(Math.toRadians(arm1.getSelfAngle())) + arm2.getRealLen()*Math.cos(Math.toRadians(arm1.getSelfAngle()+arm2.getSelfAngle())))*arm1.getRealAngularVelocity();
        double newYTwo = (arm2.getRealLen()*Math.cos(Math.toRadians(arm1.getSelfAngle()+arm2.getSelfAngle())))*arm2.getRealAngularVelocity();
        double y = newYOne+newYTwo;


        return new Vector2D(x,y);
    }

    public void updateSim(){

        //arm2.setSelfAngle(startAngle1Value);
        //arm3.setSelfAngle(startAngle2Value);
        //updateTime(time);
        //currentTrajectory = new Trajectory(getRealCurrentVelocity(),arm3.getEndPos(),arm3.getRealEndPos(), target, ball);****************************************************************
       // ball.updateVelocity(getRealCurrentVelocity().getX(),getRealCurrentVelocity().getY());
        repaint();
        //getInfo();
    }

   /* public void performTimeStep(Arm arm){

        arm.setAngularVel(-(Math.abs(arm.getEndAngle()-arm.getStartAngle())/100));

        arm.selfAngle =  arm.selfAngle + (-(Math.abs(arm.getEndAngle()-arm.getStartAngle())/100));
        arm.setStartPos(cloneVector(arm.getParent().getEndPos()));
        arm.setAngle(arm.getSelfAngle() + arm.getParent().getAngle());

        arm.calcEndPos();
        calcDistance();
        this.a = resolveAngle();
        this.b = Math.atan(ball.getVy()/ball.getVx());


        System.out.println("this is B : " + b);
        System.out.println("this is A : " + a);
        System.out.println("this is A - B : " + (a-b));
        System.out.println("this is Ball VY : " + ball.getVy());
        System.out.println("this is Ball VX : " + ball.getVx());
        //if(a == b){
            //System.out.println("A IS EQUAL TO B!!!!!!!!!!!!!!!!!!!! ");

        //}
        //System.out.println("THIS IS A : " + a);
        //System.out.println("THIS IS B : " + b);
        //double c = Math.abs(a-b);
        //System.out.println("THIS IS A - B" + c);


        if(arm.getChild()!=null)
            arm.getChild().update();

    }*/

   /* public Vector2D cloneVector(Vector2D x){
        Vector2D y = new Vector2D(x.getX(),x.getY());
        return y;
    }*/

    /*public void updateTime(int time){

        double realAngle1 = (endAngle1Value - 90)* -1 ;
        double realAngle2 = (endAngle2Value - 90)* -1 ;

        boolean a = false;
        boolean b = false;

        arm2.setAngularVel(0);
        arm3.setAngularVel(0);

        for(int i =0; i<=time;i++ ){

            if(a && b)
                break;

            if(!a){
                if(arm2.getSelfAngle() <= realAngle1) {
                    a = true;
                    arm2.setAngularVel(0);
                }else if(i>=timer1Value){
                    performTimeStep(arm2);
                }
            }

            //if b has reached target angle, set velocity to 0, else if trigger angle is reached start performing time steps
            if(!b){
                if(arm3.getSelfAngle() <= realAngle2) {
                    b = true;
                    arm3.setAngularVel(0);
                }else if(arm2.getSelfAngle() <= triggerAngle){
                    performTimeStep(arm3);
                }
            }
        }

    }*/

   /* public void calcDistance(){

        double angle = Math.atan(getRealCurrentVelocity().getY()/getRealCurrentVelocity().getX());


        double vX = getRealCurrentVelocity().getMagnitude()*Math.cos(angle);
        double vY = getRealCurrentVelocity().getMagnitude()*Math.sin(angle);

        currentVelocity = vX+vY;

        double tRise = vY/9.81;
        double height = (arm3.getEndPos().getY()/1000) - targetHeight/1000 + vY*tRise - 0.5 * 9.81 * tRise*tRise;
        double tFall = Math.sqrt(2*height/9.81);

        double range = (tFall+tRise)*vX;

        this.distance = range+(arm3.getEndPos().getX()/1000);

        if(this.distance >= (targetDistance/1000)+0.25){
            if(this.distance <= (targetDistance/1000)+(targetWidth/1000)+0.25){
                this.targetReached = true;
            }
        } else {
            this.targetReached = false;
        }
    }*/

    /*public double resolveAngle(){

        magnitude = Math.sqrt(Math.pow(getRealCurrentVelocity().getX(),2)+Math.pow(getRealCurrentVelocity().getY(),2));

        angle = Math.atan(getRealCurrentVelocity().getY()/getRealCurrentVelocity().getX());

        return angle;

    }*/

    /*public double getCurrentVelocity(){
        return currentVelocity;
    }
*/
 /*   public void getInfo(){

        //in here you can print whatever you like

    }*/

}
