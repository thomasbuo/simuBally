package fancySimulation;

import java.util.ArrayList;
import java.lang.Math;

import static java.lang.Thread.sleep;

/**
 * Created by sandersalahmadibapost on 17/11/2018.
 */
public class PhysicsEngine extends Thread{

    public static Simulation sim;

    private final double GRAVITY = 9.81;

    private int startAngle1Value;
    private int startAngle2Value;
    private int endAngle1Value;
    private int endAngle2Value;
    private double triggerAngle;
    private double targetDistance;
    private double targetHeight;
    private double targetWidth;
    private double BIAS;
    private int counter;
    private double finalDistance;


    private double currentTime;
    private double lastTime;
    private double timePassed;
    private double timeFraction;
    private double ballAngle;
    private double armAngle;
    private double armCurrentVelocity;
    private double distance;
    private boolean targetReached;
    private int timeCounter;
    private Vector2D tipVel;
    private boolean ballLaunched;
    private Ball ball;
    private ArrayList<Vector2D> constantForces = new ArrayList<Vector2D>();
    private double ballDistance;

    private static final int startX = 250;
    //from bottom up
    private Arm arm1 = new Arm(startX,0,220,90);
    private Arm arm2 = new Arm(arm1,120,0);
    private Arm arm3 = new Arm(arm2,170,0);
    private Target target;
    private Trajectory currentTrajectory;
    
    private double error;
    private double score;

    public PhysicsEngine(Simulation sim){

        this.sim = sim;
        target = new Target(sim.getTargetDistance(), sim.getTargetHeight(), sim.getTargetWidth());
//        this.startAngle1Value = sim.getStartAngle1Value();
//        this.startAngle2Value = sim.getStartAngle2Value();
//        this.triggerAngle = sim.getTriggerAngle();
//        this.endAngle1Value = sim.getEndAngle1Value();
//        this.endAngle2Value = sim.getEndAngle2Value();
//        this.targetDistance = sim.getTargetDistance();
//        this.targetHeight = sim.getTargetHeight();
//        this.targetWidth = sim.getTargetWidth();
//        System.out.println("THIS IS SIM GET TARGET DISTANCE IN ENGINE CONSTRUCTOR : " + sim.getTargetDistance());
//        System.out.println("THIS IS SIM GET START ANGLE 1 IN ENGINE CONSTRUCTOR : " + sim.getStartAngle1Value());

    }



    public void run(){


        currentTime = System.currentTimeMillis();
        initializeConstantForces();
        ballLaunched = false;
//        this.ball = new Ball(sim, arm3.getEndPos().getX(), arm3.getEndPos().getY(), getRealCurrentVelocity().getX(), getRealCurrentVelocity().getY());
        this.counter = 0;
        tipVel = new Vector2D(0,0);
//        System.out.println("THIS IS THE START ANGLE 1 VALUE IN THE RUN METHOD :" + startAngle1Value);
//        System.out.println("THIS IS THE START ANGLE 2 VALUE IN THE RUN METHOD :" + startAngle2Value);
        this.ball = new Ball(sim, arm3.getEndPos().getX(), arm3.getEndPos().getY(), 0, 0);
        this.finalDistance = 0;
        arm2.setSelfAngle(startAngle1Value);
        arm3.setSelfAngle(startAngle2Value);
        //System.out.println("THIS IS SELF ANGLE OF ARM 3 IN THE RUN METHOD : " + arm3.getSelfAngle());
        this.BIAS = 0.5;
        sim.repaint();
        while(Main.isRunning){
            //System.out.println("WE ARE IN THE LOOP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            if(timeCounter == 0 ){
//            }

            timeCounter++;
            updateTime();
            //double proportion = ((triggerAngle+90)/(Math.abs(endAngle1Value-startAngle1Value)));
//            arm3.selfAngle >= (startAngle2Value + (0.33*Math.abs(endAngle2Value-startAngle2Value)))
//            arm2.selfAngle >= triggerAngle + 0.05*Math.abs(endAngle1Value-startAngle1Value)
            if(arm3.selfAngle >= (startAngle2Value + (0.33*Math.abs(endAngle2Value-startAngle2Value)))) {
                ballLaunched = true;
//                System.out.println("THIS IS ARM 3 SELF ANGLE : " + arm3.getSelfAngle());
//                System.out.println("THIS IS START ANGLE 2 VALUE : " + startAngle2Value);
//                System.out.println("THIS IS END ANGLE 2 VALUE : " + endAngle2Value);
            }
            if(arm2.selfAngle >= 0){
                ballLaunched = true;
            }
            /*if(Math.abs(endAngle1Value-startAngle1Value) <= 6){
                this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), 0, 0);
                ballLaunched = true;
                counter++;
            }
            if(Math.abs(endAngle2Value-startAngle2Value) <= 4){
                this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), 0, 0);
                ballLaunched = true;
                counter++;
            }*/

            /*if(ballLaunched == false) {
                if (arm3.selfAngle >= endAngle2Value - 1) {
                    this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(),0,0);
                    ballLaunched = true;
                }
                if (arm2.selfAngle >= endAngle1Value - 1) {
                    this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(),0,0);
                    ballLaunched = true;
                }
            }*/
//            System.out.println(" THIS IS ARM 1 END POS X COMPONENT : " + arm1.getRealEndPos().getX());
//            System.out.println(" THIS IS ARM 1 END POS Y COMPONENT : " + arm1.getRealEndPos().getY());
//            System.out.println(" THIS IS ARM 2 END POS X COMPONENT : " + arm2.getRealEndPos().getX());
//            System.out.println(" THIS IS ARM 2 END POS Y COMPONENT : " + arm2.getRealEndPos().getY());
//            System.out.println(" THIS IS ARM 3 END POS X COMPONENT : " + arm3.getRealEndPos().getX());
//            System.out.println(" THIS IS ARM 3 END POS Y COMPONENT : " + arm3.getRealEndPos().getY());
//            System.out.println("THIS IS ARM 2 SELF ANGLE : " + arm2.selfAngle);
//            System.out.println("THIS IS ARM 3 SELF ANGLE : " + arm3.selfAngle);
//            System.out.println("THIS IS THE NEW X COMPONENT OF THE TOOL TIP OF THE ARM : " + tipVel.getX());
//            System.out.println("THIS IS THE NEW Y COMPONENT OF THE TOOL TIP OF THE ARM : " + tipVel.getY());
            /*if(tipVel.getX() == Double.NaN){
              if(tipVel.getY() == Double.NaN){
                  ballLaunched = true;
              }
            }
              if(triggerAngle >= endAngle1Value-2){
                if(arm2.selfAngle >= triggerAngle-1){
                    ballLaunched = true;
                }
            }
            if(arm2.selfAngle >= 5){
                ballLaunched = true;
            }

            if(arm2.selfAngle >= triggerAngle - 0.5){
                ballLaunched = true;
            }*/
            if(arm2.selfAngle>= endAngle1Value-1){
                ballLaunched = true;
            }


            if(ballLaunched) {
                if(this.counter==0){
                    if(Math.abs(endAngle1Value-startAngle1Value) <= 10){
                        this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), 0, 0);
                    } else {
                        this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), tipVel.getX(), tipVel.getY());
                    }

                    System.out.println("THIS IS THE TIP VEL X COMPONENT WHEN THE BALL IS CREATED : " + tipVel.getX());
                    System.out.println("THIS IS THE TIP VEL Y COMPONENT WHEN THE BALL IS CREATED : " + tipVel.getY());
                    System.out.println("THIS IS THE BALL VELOCITY X COMPONENT WHEN IT IS CREATED : " + ball.getVx());
                    System.out.println("THIS IS THE BALL VELOCITY Y COMPONENT WHEN IT IS CREATED : " + ball.getVy());

                }
                this.counter++;
                resolveConstantForces();
                resolveForces();
                moveBall();

            }
            calcCurrentVelocity();
            updateArm(timeCounter);

            calcDistance();
            sim.updateSim();
            BIAS -= 0.025;
            if(finalDistance != 0){
                break;
            }


            //updateTime(time);
            currentTrajectory = new Trajectory(getRealCurrentVelocity(),arm3.getEndPos(),arm3.getRealEndPos(), target, ball);
            // ball.updateVelocity(getRealCurrentVelocity().getX(),getRealCurrentVelocity().getY());



            sim.repaint();


            try{
                sleep(1);
            } catch(InterruptedException e){

            }
        }
    }

    private void updateTime(){

        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timePassed = (currentTime - lastTime);
        timeFraction = (timePassed / 1000.0);
//        System.out.println("TIME FRACTION IS : " + timeFraction);

    }

    private void initializeConstantForces(){

        constantForces.add(new Vector2D(0 , GRAVITY));

    }

    private synchronized void resolveConstantForces(){

        double ax = 0;
        double ay = 0;

        for (int i = 0; i < constantForces.size(); i++) {

            ax += constantForces.get(i).getX();
            ay += constantForces.get(i).getY();

        }

        ball.addAcceleration(new Vector2D(ax, ay));

    }

    private synchronized void resolveForces(){

        Vector2D a = ball.sumAccelerations();
        Vector2D d = ball.calcDrag();

        double vx = ball.getVx() - (d.getX()*timeFraction);
        double vy = ball.getVy() - (a.getY() * timeFraction) + (d.getY()*timeFraction);
//        System.out.println("THIS IS THE X COMPONENT OF ACCELERATION : " + a.getX());
//        System.out.println("THIS IS THE Y COMPONENT OF ACCELERATION : " + a.getY());
//        System.out.println("THIS IS THE UPDATED BALL VX : " + vx);
//        System.out.println("THIS IS THE UPDATED BALL VY : " + vy);

        ball.setMagnitude(Math.sqrt(Math.pow(ball.getVx(),2)+Math.pow(ball.getVy(),2)));


            ball.updateVelocity(vx, vy);

        //Vector2D drag = ball.calcDrag();
        //ball.addDrag(new Vector2D(1-(timeFraction*(-drag.getX())), 1-(timeFraction*(-drag.getY()))));
    }

    private synchronized void moveBall(){

        double previousX = this.ball.getX();
        double previousY = this.ball.getY();

//        System.out.println("THIS IS THE BALL's CURRENT X POSITION : " + this.ball.getX());
//        System.out.println("THIS IS THE BALL's CURRENT Y POSITION : " + this.ball.getY());

        double newX = previousX + (this.ball.getVx()*timeFraction);
        double newY = previousY + (this.ball.getVy()*timeFraction);

//        System.out.println("THIS IS THE BALL's NEW X POSITION : " + ball);

        this.ball.updatePosition(newX,newY);

            if(newY<= 0.02){
                if(newY >= -0.02){
                    this.finalDistance = newX-0.25;
                    System.out.println("THIS IS THE FINAL DISTANCE !!!!!!!!!!! : " + finalDistance);
//                    System.out.println("THIS IS THE TARGET DISTANCE: " + targetDistance);
//                    System.out.println("THIS IS THE TARGET WIDTH : " + targetWidth);
                    if(finalDistance >= (targetDistance/1000)){
                        if((targetDistance/1000) + (targetWidth/1000) >= finalDistance){
                            targetReached = true;
                        }
                    }
                    System.out.println("HAS THE TARGET BEEN REACHED (TRUE OR FALSE) : " + targetReached);
                    double error =(finalDistance - (targetDistance/1000 + targetWidth/2000))*((finalDistance - (targetDistance/1000 + targetWidth/2000)));
					double score = Math.abs((targetDistance/1000 + targetWidth/2000)*(targetDistance/1000 + targetWidth/2000) - error);
					System.out.println("score "+score);
                }
            }
 //       System.out.println("TEST");

            this.ballDistance = newX;
//            System.out.println("THIS IS THE FINAL DISTANCE OF THE BALL WHEN IT HITS THE GROUND : " + ballDistance);

    }

    public void calcCurrentVelocity(){

        //double y = (-arm2.getRealLen()*Math.toRadians(arm2.getAngularVelocity()*timeFraction)*Math.sin(Math.toRadians(arm2.getSelfAngle())) - arm3.getRealLen()*((Math.toRadians((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))))*Math.sin(Math.toRadians(arm2.getSelfAngle()+arm3.getSelfAngle())));
//        System.out.println("THIS IS ARM 2 GET REAL LEN : " + arm2.getRealLen());
//        System.out.println("THIS IS ARM 2 GET REAL ANGULAR VELOCITY : " + arm2.getRealAngularVelocity());
//        System.out.println("THIS IS ARM 2 GET SELF ANGLE : " + arm2.getSelfAngle());
//        System.out.println("THIS IS ARM 3 GET REAL LEN : " + arm3.getRealLen());
//        System.out.println("THIS IS ARM 3 GET REAL ANGULAR VELOCITY : " + arm3.getRealAngularVelocity());
//        System.out.println("THIS IS ARM 3 GET SELF ANGLE : " + arm3.getSelfAngle());
        double x =  (arm2.getRealLen() * Math.toRadians(arm2.getAngularVelocity() * timeFraction) * Math.cos(Math.toRadians(arm2.selfAngle))) + (arm3.getRealLen() * Math.toRadians( (arm2.getAngularVelocity() * timeFraction) + (arm3.getAngularVelocity() * timeFraction) ) * Math.cos(Math.toRadians(arm2.selfAngle + arm3.selfAngle)));

        //double x = (arm2.getRealLen()*Math.toRadians(arm2.getAngularVelocity()*timeFraction)*Math.cos(Math.toRadians(arm2.getSelfAngle())) + arm3.getRealLen()*(Math.toRadians((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction)))*Math.cos(Math.toRadians(arm2.getSelfAngle()+arm3.getSelfAngle())));

        double y = -(arm2.getRealLen() * Math.toRadians(arm2.getAngularVelocity()*timeFraction) * Math.sin(Math.toRadians(arm2.selfAngle))) - (arm2.getRealLen() * Math.toRadians((arm2.getAngularVelocity() * timeFraction) + (arm3.getAngularVelocity() * timeFraction))* Math.sin(Math.toRadians(arm2.selfAngle+arm3.selfAngle)));
        tipVel.setX(x/timeFraction);
        tipVel.setY(y/timeFraction);


    }

    public Vector2D getRealCurrentVelocity(){

        double x = (arm2.getRealLen()*arm2.getAngularVelocity()*timeFraction*Math.sin(Math.toRadians(arm2.getSelfAngle()+90)) + arm3.getRealLen()*((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))*Math.sin(Math.toRadians(arm2.getSelfAngle()+180+arm3.getSelfAngle())));
//        System.out.println("THIS IS THE NEW X COMPONENT OF THE TOOL TIP OF THE ARM : " + x);
//        System.out.println("THIS IS ARM 2 GET REAL LEN : " + arm2.getRealLen());
//        System.out.println("THIS IS ARM 2 GET REAL ANGULAR VELOCITY : " + arm2.getRealAngularVelocity());
//        System.out.println("THIS IS ARM 2 GET SELF ANGLE : " + arm2.getSelfAngle());
//        System.out.println("THIS IS ARM 3 GET REAL LEN : " + arm3.getRealLen());
//        System.out.println("THIS IS ARM 3 GET REAL ANGULAR VELOCITY : " + arm3.getRealAngularVelocity());
//        System.out.println("THIS IS ARM 3 GET SELF ANGLE : " + arm3.getSelfAngle());


        double y = (arm2.getRealLen()*arm2.getAngularVelocity()*timeFraction*Math.cos(Math.toRadians(arm2.getSelfAngle()+90)) + arm3.getRealLen()*((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))*Math.cos(Math.toRadians(arm2.getSelfAngle()+180+arm3.getSelfAngle())));
//        System.out.println("THIS IS THE NEW Y COMPONENT OF THE TOOL TIP OF THE ARM : " + y);


        arm2.setvX(x);
        arm3.setvX(x);
        arm2.setvY(y);
        arm3.setvX(y);
        return new Vector2D(x,y);
    }

    private void performTimeStep(Arm arm){

//        (Math.abs(endAngle1Value-startAngle1Value)/5)

        arm.selfAngle =  arm.selfAngle + ((Math.abs(endAngle1Value-startAngle1Value)/5)*timeFraction);
//        System.out.println("THIS IS END ANGLE 1 VALUE : " + endAngle1Value);
//        System.out.println("THIS IS START ANGLE 1 VALUE : " + startAngle1Value);
//        System.out.println("THIS IS THE ADDITION TO ARM SELF ANGLE VALUE : " + (Math.abs(endAngle1Value-startAngle1Value)/5)*timeFraction);
//        System.out.println("THIS IS THE NEW ARM ANGULAR VEL VALUE : " + arm.getAngularVelocity());
        arm.update();
        this.armAngle = Math.atan(tipVel.getY()/tipVel.getX());
        this.ballAngle = Math.atan(ball.getVy()/ball.getVx());

//DEBUG
//        System.out.println("this is BALL ANGLE : " + ballAngle);
//        System.out.println("this is ARM ANGLE : " + armAngle);
//        System.out.println("this is ARM ANGLE - BALL ANGLE : " + (armAngle-ballAngle));
//        System.out.println("this is Ball VY : " + ball.getVy());
//        System.out.println("this is Ball VX : " + ball.getVx());
//        System.out.println("this is Current Velocity X : " + getRealCurrentVelocity().getX() + " and Y : " + getRealCurrentVelocity().getY());
//        System.out.println("THIS IS ARM 1 START ANGLE : " + arm1.getStartAngle());
//        System.out.println("THIS IS ARM 2 START ANGLE : " + arm2.getStartAngle());
//        System.out.println("THIS IS ARM 1 END ANGLE : " + arm1.getEndAngle());
//        System.out.println("THIS IS ARM 2 END ANGLE : " + arm2.getEndAngle());
//        System.out.println("THIS IS ARM 1 selfAngle : " + arm1.getSelfAngle());
//        System.out.println("THIS IS ARM 2 selfAngle : " + arm2.getSelfAngle());
//        System.out.println("THIS IS ARM 1 realAngularVelocity : " + arm1.getRealAngularVelocity());
//        System.out.println("THIS IS ARM 2 realAngularVelocity : " + arm2.getRealAngularVelocity());
//        System.out.println("THIS IS ARM SELF ANGLE : " + arm.selfAngle);
//        System.out.println("THIS IS THE STUFF ADDED TO ARM SELF ANGLE : " + -(Math.abs(arm.getEndAngle()-arm.getStartAngle())/5)*timeFraction);
//
//        System.out.println("THIS IS START ANGLE 1 VALUE : " + this.startAngle1Value);

        if(arm.getChild()!=null)
            arm.getChild().update();

    }

    public void updateArm(int time){

        double realAngle1 = endAngle1Value ;
        double realAngle2 = endAngle2Value;

        double jump = Math.abs(endAngle1Value-startAngle1Value)/10;
        double delay = (7*jump)/1000;

        boolean a = false;
        boolean b = false;

        //for(int i =0; i<=time;i++ ){

            /*if(a && b)
                break;*/


                if((arm2.getSelfAngle() >= realAngle1)) {
                    a = true;
                    arm2.setAngularVel(0);
                } else {

                    arm2.setAngularVel(jump/delay);
                    /*if(arm2.getSelfAngle()>= -90+(0.7*(Math.abs(endAngle1Value-startAngle1Value)))){
                        arm2.setAngularVel(jump/delay/2);
                    }
                    if(arm2.getSelfAngle()>= -90+(0.8*(Math.abs(endAngle1Value-startAngle1Value)))){
                        arm2.setAngularVel(jump/delay/4);
                    }
                    if(arm2.getSelfAngle()>= -90+(0.9*(Math.abs(endAngle1Value-startAngle1Value)))){
                        arm2.setAngularVel(jump/delay/8);
                    }*/
                    performTimeStep(arm2);
                }


//        System.out.println("TRIGGER ANGLE IS : " + triggerAngle);
//        System.out.println("ARM 3 SELF ANGLE IS : " + arm3.getSelfAngle());
            //if b has reached target angle, set velocity to 0, else if trigger angle is reached start performing time steps

                if((arm3.getSelfAngle() >= realAngle2)) {
                    b = true;
                    arm3.setAngularVel(0);
                }else if(arm2.getSelfAngle() >= triggerAngle-0.5){
                    arm3.setAngularVel(Math.abs(endAngle2Value-startAngle2Value)/delay);
                    performTimeStep(arm3);
                }



    }

    public void calcDistance(){

        double angle = Math.atan(tipVel.getY()/tipVel.getX());


        double vX = tipVel.getX();
        double vY = tipVel.getY();




        if(ballLaunched){

            double v = Math.sqrt(Math.pow(vX,2)+Math.pow(vY,2));

            double d = (Math.pow(v,2)*Math.sin(2*Math.atan(vX/vY)))/GRAVITY;

            this.distance = d;
//            System.out.println("THIS IS THE CURRENT ARM ANGLE - BALL ANGLE ABSOLUTE VALUE : " + Math.abs(this.armAngle-this.ballAngle));
//            System.out.println("THIS IS THE CURRENT DISTANCE : " + distance );
//            System.out.println("THIS IS THE CURRENT BALL DISTANCE : " + ballDistance);
            vX = ball.getVx();
            vY = ball.getVy();
        }

        this.armCurrentVelocity = vX+vY;

        double tRise = vY/9.81;
        double height = (arm3.getRealEndPos().getY()) - targetHeight/1000 + vY*tRise - 0.5 * 9.81 * tRise*tRise;
        double tFall = Math.sqrt(2*height/9.81);




        double range = (tFall+tRise)*vX;


        //System.out.println("THIS IS THE CURRENT BIAS PLEASE TAKE NOTE OF THIS!!!!!!!!!!!!!!!!! : " + BIAS);



        if(this.distance >= (targetDistance/1000)+0.25){
            if(this.distance <= (targetDistance/1000)+(targetWidth/1000)+0.25){
                this.targetReached = true;

                //sim.setTargetReached(true);
            }
        } else {
            this.targetReached = false;
            //sim.setTargetReached(false);
        }
    }

    public Vector2D cloneVector(Vector2D x){
        Vector2D y = new Vector2D(x.getX(),x.getY());
        return y;
    }

    public int getStartAngle1Value() {
        return startAngle1Value;
    }

    public void setStartAngle1Value(int startAngle1Value) {
        this.startAngle1Value = startAngle1Value;
    }

    public int getStartAngle2Value() {
        return startAngle2Value;
    }

    public void setStartAngle2Value(int startAngle2Value) {
        this.startAngle2Value = startAngle2Value;
    }

    public int getEndAngle1Value() {
        return endAngle1Value;
    }

    public void setEndAngle1Value(int endAngle1Value) {
        this.endAngle1Value = endAngle1Value;
    }

    public int getEndAngle2Value() {
        return endAngle2Value;
    }

    public void setEndAngle2Value(int endAngle2Value) {
        this.endAngle2Value = endAngle2Value;
    }

    public double getTriggerAngle() {
        return triggerAngle;
    }

    public void setTriggerAngle(int triggerAngle) {
        this.triggerAngle = triggerAngle;
    }

    public double getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance = targetDistance;
    }

    public double getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(double targetHeight) {
        this.targetHeight = targetHeight;
    }

    public double getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(double targetWidth) {
        this.targetWidth = targetWidth;
    }
    
    public double getScore()
    {
    	return score;
    }
    public double getError()
    {
    	return error;
    }
}
