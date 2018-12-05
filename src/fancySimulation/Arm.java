package fancySimulation;

/**
 * Created by sandersalahmadibapost on 01/10/2018.
 */

//CONVERT ARM POSITIONS TO REAL POSITIONS!!!!!!!!
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Arm {

    private Vector2D startPos;
    private Vector2D endPos;
    //Do the real calculations as well as the sim world calculations but make sure that the calculations themselves occur completely independently but still do translate the real world to the sim world
    //As well as this we also need to debug all the graphics errors
    //Once this is all done we can start playing with the biases and try to make them as realistic as possible, develop argumentation for bias in relation to the curvature of the spoon
    private Vector2D realStartPos;
    private Vector2D realEndPos;
    private String name;
    private double angularVel;
    private double angle;
    private double startAngle;
    private double endAngle;
    private Arm parent;
    private Arm child;
    private double len;
    private double realLen;
    public double selfAngle;

    private double vX;
    private double vY;

    public Arm(double x, double y, double len, double angle) {

        this.startPos = new Vector2D(x,y);
        this.realStartPos = new Vector2D(x/1000,y/1000);
        this.len = len;
        this.realLen = len/1000;
        this.angle = angle;
        selfAngle = angle;
        calcEndPos();
        parent = null;
        child = null;
    }

    public Arm(Arm parent, double len, double angle) {

        this.parent = parent;
        this.parent.child = this;
        this.startPos = this.parent.endPos.cloneVector();
        this.realStartPos = new Vector2D(this.parent.endPos.getX()/1000,this.parent.endPos.getY()/1000);
        this.len = len;
        selfAngle = angle - 90;
        //multiply with -1 to mirror the catapult, should not mess with the results
        // but keep this in mind
        selfAngle *= -1;
        this.angle = selfAngle + this.parent.angle;
        calcEndPos();

    }

    public void calcEndPos(){

            double dx = len * Math.cos(Math.toRadians(angle));
            double dy = len * Math.sin(Math.toRadians(angle));

            this.endPos = new Vector2D(startPos.getX() + dx, startPos.getY() + dy);
            this.realEndPos = new Vector2D((startPos.getX() + dx)/1000,(startPos.getY() + dy)/1000);

    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Line2D.Double line = new Line2D.Double(startPos.getX(), Simulation.FLOORHEIGHT - startPos.getY(),endPos.getX(),Simulation.FLOORHEIGHT - endPos.getY());
        g2d.setStroke(new BasicStroke(30));
        g2d.setColor(Color.GREEN);
        g2d.draw(line);
        g2d.setColor(Color.RED);

        Ellipse2D.Double jointA = new Ellipse2D.Double(startPos.getX()-15, Simulation.FLOORHEIGHT - startPos.getY() -15, 30, 30);
        Ellipse2D.Double jointB = new Ellipse2D.Double(endPos.getX()-15, Simulation.FLOORHEIGHT - endPos.getY() -15, 30, 30);
        g2d.fill(jointA);
        g2d.fill(jointB);

    }

    public void setAngularVel(double angularVel) {
        this.angularVel = angularVel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStartPos(Vector2D startPos) {
        this.startPos = startPos;
        this.realStartPos = new Vector2D(startPos.getX()/1000, startPos.getY()/1000);
        calcEndPos();
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Arm getParent() {
        return parent;
    }

    public Arm getChild() {
        return child;
    }

    public void update(){
        startPos = parent.endPos.cloneVector();
        realStartPos = new Vector2D(parent.endPos.getX()/1000, parent.endPos.getY()/1000);
        angle = selfAngle + parent.angle;
        calcEndPos();
    }

    public void setSelfAngle(double selfAngle) {
        this.selfAngle = selfAngle;

        //multiply with -1 to mirror the catapult, should not mess with the results
        // but keep this in mind

        if(child!=null)
            child.update();
    }

    public Vector2D getEndPos() {
        return endPos;
    }

    public double getAngle() {
        return angle;
    }

    public double getRealLen(){

        return len/1000;
    }

    public double getLen() {
        return len;
    }

    public double getSelfAngle() {
        return selfAngle;
    }

    public double getRealAngularVelocity(){
        return Math.toRadians(angularVel);
    }

    public double getAngularVelocity(){
        return angularVel;
    }

    public double getStartAngle(){
        return startAngle;
    }

    public double getEndAngle(){
        return endAngle;
    }

    public void setStartAngle(int startAngle){
        this.startAngle = startAngle;
    }

    public void setEndAngle(int endAngle){
        this.endAngle = endAngle;
    }

    /*public Vector2D getRealEndPos(){

        Vector2D start = new Vector2D(0,0);

        if(parent!=null) {
            start = parent.getRealEndPos();
        }

        double dx = (len/1000) * Math.cos(Math.toRadians(angle));
        double dy = (len/1000) * Math.sin(Math.toRadians(angle));

        return new Vector2D(start.getX()+dx,start.getY()+dy);
    }*/

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public Vector2D getRealStartPos() {
        return realStartPos;
    }

    public Vector2D getRealEndPos() {
        return realEndPos;
    }

    public void setRealStartPos(Vector2D realStartPos) {
        this.realStartPos = realStartPos;
    }

    public void setRealEndPos(Vector2D realEndPos) {
        this.realEndPos = realEndPos;
    }
}
