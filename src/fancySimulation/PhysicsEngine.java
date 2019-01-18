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
    private boolean abnormal;
    private Ball ball;
    private ArrayList<Vector2D> constantForces = new ArrayList<>();
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
    private int armspeed1 = 500;
    private int armspeed2 = 0;

    public PhysicsEngine(Simulation sim){

        this.sim = sim;
        target = new Target(sim.getTargetDistance(), sim.getTargetHeight(), sim.getTargetWidth());

    }



    public void run(){


        currentTime = System.currentTimeMillis();
        initializeConstantForces();
        ballLaunched = false;
//        this.ball = new Ball(sim, arm3.getEndPos().getX(), arm3.getEndPos().getY(), getRealCurrentVelocity().getX(), getRealCurrentVelocity().getY());
        this.counter = 0;
        tipVel = new Vector2D(0,0);
        this.ball = new Ball(sim, arm3.getEndPos().getX(), arm3.getEndPos().getY(), 0, 0);
        this.finalDistance = 0;
        arm2.setSelfAngle(startAngle1Value);
        arm3.setSelfAngle(startAngle2Value);
        this.BIAS = 0.5;
        sim.repaint();
        while(Main.isRunning){


//            PRINTS
//            System.out.println("X COMPONENT OF VELOCITY:" + tipVel.getX());
//            System.out.println("Y COMPONENT OF VELOCITY:" + tipVel.getY());
//            System.out.println("ARM 2 SELF ANGLE:"+ arm2.selfAngle);
//            System.out.println("ARM 3 SELF ANGLE:"+ arm3.selfAngle);
//            System.out.println();































//            if(timeCounter == 0 ){
//            }

            timeCounter++;
            updateTime();
            //double proportion = ((triggerAngle+90)/(Math.abs(endAngle1Value-startAngle1Value)));
//            arm3.selfAngle >= (startAngle2Value + (0.33*Math.abs(endAngle2Value-startAngle2Value)))
//            arm2.selfAngle >= triggerAngle + 0.05*Math.abs(endAngle1Value-startAngle1Value)



          /*  if(Math.abs(endAngle2Value-startAngle2Value) <=10)
            {
                if (arm3.selfAngle >= endAngle2Value - 3) {
                    ballLaunched = true;

                }
            }
            if(Math.abs(endAngle2Value-startAngle2Value) >10){
                if(arm3.selfAngle >= startAngle2Value + 0.5*Math.abs(endAngle2Value-startAngle2Value)){
                    ballLaunched = true;
                }
            }*/
            if(arm2.selfAngle >= 40){
                ballLaunched = true;
            }
            if(arm2.selfAngle >= triggerAngle){
                
                    ballLaunched = true;

            }



//            if(arm2.selfAngle>= triggerAngle+0.2*(Math.abs(endAngle1Value-startAngle1Value))){
//                ballLaunched = true;
//            }
            /*if(arm3.selfAngle >= startAngle2Value + 0.5*(Math.abs(endAngle2Value-startAngle2Value))){
                ballLaunched = true;
            }*/
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





            /*if(Math.abs(endAngle1Value-startAngle1Value) <= 5){
                ballLaunched = true;
                abnormal = true;
            }
            if(Math.abs(endAngle2Value-startAngle2Value) <= 5) {
                ballLaunched = true;
                abnormal = true;
            }
            if(triggerAngle >= endAngle1Value-1){
                if(triggerAngle <= endAngle1Value) {
                    triggerAngle = triggerAngle - 1;

                }
            }*/









            calcCurrentVelocity();
            updateArm(timeCounter);

            if(ballLaunched) {
                if(this.counter==0){
                    /*if(Math.abs(endAngle1Value-startAngle1Value) <= 10){
                        this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), 0.3, 0.3);
                        if(Math.abs(endAngle2Value-startAngle2Value) <= 5) {
                            this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), 0.1, 0.1);
                        }
                    } else {
                    }*/

//                    System.out.println("THIS IS THE SPEED OF THE ARM X COMPONENT WHEN THE BALL IS CREATED : " + tipVel.getX());
//                    System.out.println("THIS IS THE SPEED OF THE ARM Y COMPONENT WHEN THE BALL IS CREATED : " + tipVel.getY());
//                    System.out.println("THIS IS START ANGLE 1 VALUE : " + startAngle1Value);
//                    System.out.println("THIS IS END ANGLE 1 VALUE : " + endAngle1Value);

                	/*if(Math.abs(endAngle2Value-startAngle2Value)<=10) {
                		if(Math.abs(endAngle1Value-startAngle1Value)<= 25) {
                			tipVel.setX(0.01);
                			tipVel.setY(0.01);
                		}
                	}*/

                	/*if(Math.abs(startAngle1Value-triggerAngle)<= 30){
                	    tipVel.setX(0.01);
                	    tipVel.setX(0.01);
                    }*/

                    if(abnormal){
                        this.ball = new Ball(sim, -0.12, 0.05, 0, 0);
                    } else{
                        this.ball = new Ball(sim, arm3.getRealEndPos().getX(), arm3.getRealEndPos().getY(), tipVel.getX(), tipVel.getY());
                                    System.out.println("X COMPONENT OF VELOCITY:" + tipVel.getX());
                                    System.out.println("Y COMPONENT OF VELOCITY:" + tipVel.getY());

                    }


                }
                this.counter++;
                resolveConstantForces();
                resolveForces();
                moveBall();

            }


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

        double vx = ball.getVx() - (d.getX() * timeFraction);
        double vy = ball.getVy() - (a.getY() * timeFraction) - (d.getY() * timeFraction);

        ball.setMagnitude(Math.sqrt(Math.pow(ball.getVx(),2)+Math.pow(ball.getVy(),2)));


            ball.updateVelocity(vx, vy);

    }

    private synchronized void moveBall(){

        double previousX = this.ball.getX();
        double previousY = this.ball.getY();

        double newX = previousX + (this.ball.getVx()*timeFraction);
        double newY = previousY + (this.ball.getVy()*timeFraction);

        this.ball.updatePosition(newX,newY);

            if(newY<= 0.02){
                if(newY >= -0.02){
                    this.finalDistance = newX-0.25;
                    System.out.println("THIS IS THE FINAL DISTANCE !!!!!!!!!!! : " + finalDistance);

                    if(finalDistance >= (targetDistance/1000)){
                        if((targetDistance/1000) + (targetWidth/1000) >= finalDistance){
                            targetReached = true;
                        }
                    }
                    double score2 = Math.pow(((targetDistance + targetWidth/2)*1000),2);
                    this.error =Math.pow(((finalDistance - (targetDistance + targetWidth/2))*1000),2);
					this.score = Math.pow(((targetDistance + targetWidth/2)*1000),2) - error;
					System.out.println("error "+error+" score "+score+" score2 "+score2);
					System.out.println("distance "+targetDistance+" max score PH: "+ (this.score+this.error));
                }
            }

            this.ballDistance = newX;

    }

    public void calcCurrentVelocity(){

        //double y = (-arm2.getRealLen()*Math.toRadians(arm2.getAngularVelocity()*timeFraction)*Math.sin(Math.toRadians(arm2.getSelfAngle())) - arm3.getRealLen()*((Math.toRadians((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))))*Math.sin(Math.toRadians(arm2.getSelfAngle()+arm3.getSelfAngle())));

        double x =   ((arm2.getRealLen() * Math.toRadians(armspeed1) * Math.cos(Math.toRadians(arm2.selfAngle))) + (arm3.getRealLen() * Math.toRadians( (armspeed1 ) + (armspeed2 ) ) * Math.cos(Math.toRadians(arm2.selfAngle + arm3.selfAngle))));

        //double x = (arm2.getRealLen()*Math.toRadians(arm2.getAngularVelocity()*timeFraction)*Math.cos(Math.toRadians(arm2.getSelfAngle())) + arm3.getRealLen()*(Math.toRadians((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction)))*Math.cos(Math.toRadians(arm2.getSelfAngle()+arm3.getSelfAngle())));

        double y = -((arm2.getRealLen() * Math.toRadians(armspeed2) * Math.sin(Math.toRadians(arm2.selfAngle))) + (arm2.getRealLen() * Math.toRadians((armspeed1 ) + (armspeed2 ))* Math.sin(Math.toRadians(arm2.selfAngle+arm3.selfAngle ))));
        tipVel.setX(x);
        tipVel.setY(y);
        if(!Double.isFinite(x)){
            tipVel.setX(0);
            ballLaunched = true;
        }

        if(!Double.isFinite(y)){
            tipVel.setY(0);
            ballLaunched = true;
        }



    }

    public Vector2D getRealCurrentVelocity(){

        double x = (arm2.getRealLen()*arm2.getAngularVelocity()*timeFraction*Math.sin(Math.toRadians(arm2.getSelfAngle()+90)) + arm3.getRealLen()*((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))*Math.sin(Math.toRadians(arm2.getSelfAngle()+180+arm3.getSelfAngle())));

        double y = (arm2.getRealLen()*arm2.getAngularVelocity()*timeFraction*Math.cos(Math.toRadians(arm2.getSelfAngle()+90)) + arm3.getRealLen()*((arm2.getAngularVelocity()*timeFraction)+(arm3.getAngularVelocity()*timeFraction))*Math.cos(Math.toRadians(arm2.getSelfAngle()+180+arm3.getSelfAngle())));

        arm2.setvX(x);
        arm3.setvX(x);
        arm2.setvY(y);
        arm3.setvX(y);
        return new Vector2D(x,y);
    }

    private void performTimeStep(Arm arm){


        arm.selfAngle =  arm.selfAngle + (armspeed1*timeFraction);
        arm.update();
        this.armAngle = Math.atan(tipVel.getY()/tipVel.getX());
        this.ballAngle = Math.atan(ball.getVy()/ball.getVx());

        if(arm.getChild()!=null)
            arm.getChild().update();

    }

    public void updateArm(int time){

        double realAngle1 = endAngle1Value ;
        double realAngle2 = endAngle2Value;

        double jump = Math.abs(endAngle1Value-startAngle1Value)/10;
        double delay = (5*jump)/1000;

        boolean a = false;
        boolean b = false;

        //for(int i =0; i<=time;i++ ){

            /*if(a && b)
                break;*/


                if((arm2.getSelfAngle() >= realAngle1)) {
                    a = true;
                    arm2.setAngularVel(0);
                } else {

                    if(Math.abs(endAngle2Value-startAngle2Value)<= 10){

                    }
                    arm2.setAngularVel(500);



                    /*if(Math.abs(endAngle1Value-startAngle1Value)<=90){
                        arm2.setAngularVel(225);
                    }
                    if(Math.abs(endAngle1Value-startAngle1Value)<=60){
                        arm2.setAngularVel(200);
                    }
                    if(Math.abs(endAngle1Value-startAngle1Value)<=45){
                        arm2.setAngularVel(175);
                    }
                    if(Math.abs(endAngle1Value-startAngle1Value)<= 20){
                        arm2.setAngularVel(150);
                    }
                    if(Math.abs(endAngle1Value-startAngle1Value)<= 10){
                        arm2.setAngularVel(125);
                    }
                    if(Math.abs(endAngle1Value-startAngle1Value)<= 5){
                        arm2.setAngularVel(10);
                    }*/




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

            //if b has reached target angle, set velocity to 0, else if trigger angle is reached start performing time steps

                if((arm3.getSelfAngle() >= realAngle2)) {
                    b = true;
                    arm3.setAngularVel(0);
                }

                if(arm2.getSelfAngle() >= triggerAngle-1){
                    arm3.setAngularVel(500);

                    /*if(Math.abs(endAngle2Value-startAngle2Value)<=40){
                        arm3.setAngularVel(225);
                    }
                    if(Math.abs(endAngle2Value-startAngle2Value)<=30){
                        arm3.setAngularVel(200);
                    }
                    if(Math.abs(endAngle2Value-startAngle2Value)<=20){
                        arm3.setAngularVel(175);
                    }
                    if(Math.abs(endAngle2Value-startAngle2Value)<=10){
                        arm3.setAngularVel(150);
                    }
                    if(Math.abs(endAngle2Value-startAngle2Value)<= 5){
                        arm3.setAngularVel(10);
                    }*/

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

            vX = ball.getVx();
            vY = ball.getVy();
        }

        this.armCurrentVelocity = vX+vY;

        double tRise = vY/9.81;
        double height = (arm3.getRealEndPos().getY()) - targetHeight/1000 + vY*tRise - 0.5 * 9.81 * tRise*tRise;
        double tFall = Math.sqrt(2*height/9.81);




        double range = (tFall+tRise)*vX;


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
