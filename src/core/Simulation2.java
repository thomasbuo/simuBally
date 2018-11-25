package core;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.*;

import Maths.GD;
import Maths.NeuralCore;

import objects.ArmPart;
import objects.Ball;
import objects.Floor;
import objects.Joint;
import objects.Target;




public class Simulation2 {
	
	private boolean paint = false;
	private boolean is_running = false;
	//genetic
	private int population = 50;
	private long start_time;
	private double current_time;
	private long total_calculation_time;
	private int finalPosY = 0;
	private boolean full_speed = false;
	private int visualization_frequency = 1000;

	private ArrayList<Double> speeds = new ArrayList<Double>();
	
	private Ball ball;
	private Floor floor;
	private ArmPart part2;
	private ArmPart part3;
	private boolean target1, target2;
	private boolean done = false;
	private Target target;
	private double maxRotation1 = 90;
	private double maxRotation2 = 180;
	
			//private GD gd;

	private double finalPosX;
	private ArrayList<Joint> joints;
	
	private Drawing mainFrame;
	
	private double hitCount = 0;
	private int populationIteration = 0;
	
	private int generation=-1;
	public Simulation2(ArrayList<Joint> joints, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball, Floor floor, Target target)
	{
		this.ball = ball;
		this.floor = floor;
		
		this.joints = joints;
		this.part2 = part2;
		this.part3 = part3;
		this.target = target;
		
	}
	public boolean checkCollision() 
	{ 
		boolean collision = false;
		if((ball.getPosY() + ball.getDiameter()) >= floor.getY()) 
		{
			collision = true;
		}
		return collision;
	} 
	
	public void simulate()
	{
		
			if (this.is_running) {
				System.out.println("Already Running.");
				return;
			}
	
			this.is_running = true;
	
			Thread th = new Thread(() -> {
				// Initialize
				double delta_t = 0.05; // in seconds
				this.total_calculation_time = 0;
				int step = 0;
				
				//comment out to use other ml;
				//Genetic g = new Genetic(this);
				GD gd = new GD(target);
				NeuralCore nc = new NeuralCore(population);
				ArrayList<Double> newAngles = nc.guess(target.getX());
				joints.get(0).setTargetAngle(-(int)(maxRotation1/2) + newAngles.get(0)*maxRotation1);
				joints.get(1).setTargetAngle(-(int)(maxRotation2/2)  + newAngles.get(1)*maxRotation2);
				
				while (this.is_running) {
					start_time = System.nanoTime();
	
					// Wait for time step to be over
					double ns_used = (System.nanoTime() - start_time);
					total_calculation_time += ns_used;
					
	
					this.current_time += delta_t;
					ns_used = (System.nanoTime() - start_time);
					total_calculation_time += ns_used;
					if (!full_speed) {
						double ns_to_wait = (delta_t);
						try {
							TimeUnit.NANOSECONDS.sleep((int) Math.max(0, ns_to_wait - ns_used));
						} catch (InterruptedException e) {
							System.out.println("Simulation sleeping (" + ns_to_wait + "ns) got interrupted!");
						}
					}
					
					if(checkCollision())
					{						
						ball.nullSpeed();
						finalPosX = ball.getPosX();
						finalPosY = floor.getY();
						double actualError = finalPosX - target.getX();
						double error = (finalPosX - target.getX())*(finalPosX - target.getX());
						
						if(15 >= error)
						{
							hitCount ++;							
						}
						
						populationIteration++;
						
						if(populationIteration == 400)
						{
							generation++;
							System.out.println("gen: "+ generation+" "+ actualError);
							if(target.getX()>300)
							{
								target.setX(0);
							}
							target.setX(target.getX()+100);
							populationIteration=0;				
						}	
							
						nc.trainNeuralNetwork(actualError);
						newAngles = nc.guess(target.getX());
						joints.get(0).setTargetAngle(-(int)(maxRotation1/2) + newAngles.get(0)*maxRotation1);
						joints.get(1).setTargetAngle(-(int)(maxRotation2/2)  + newAngles.get(1)*maxRotation2);
							
						
						
						ball.setPos(ball.getOriginalPosX(), ball.getOriginalPosY());
												
						joints.get(0).setAngle(joints.get(0).getOriginalAngle1());
						joints.get(1).setAngle(joints.get(1).getOriginalAngle2());
						part2.setPosX2(2);
						part2.setPosY2(2);
						part3.setPosY1(part2.getPosY2());
						part3.setPosX1(part2.getPosX2());
						
						part3.setPosX2(3);
						part3.setPosY2(3);
					
						
						done = false;
						continue;
					}	
					

					// update graphics and statistics
					step++;
					if (step % this.visualization_frequency == 0) 
					{
						target1 = true;
						if (joints.get(0).getTargetAngle() > joints.get(0).getAngle())
						{
							
							joints.get(0).setAngle( (joints.get(0).getAngle()+joints.get(0).getSpeed()));
							target1 = false;
						}
						part2.setPosX2(2);
						part2.setPosY2(2);
						part3.setPosY1(part2.getPosY2());
						part3.setPosX1(part2.getPosX2());
						
						target2 = true;
						if (joints.get(1).getTargetAngle() > joints.get(1).getAngle())
						{
					
							joints.get(1).setAngle( (joints.get(1).getAngle()+joints.get(1).getSpeed()));
							target2 = false;
						}
						part3.setPosX2(3);
						part3.setPosY2(3);
						
						if(!target1 || !target2)
						{
							ball.setPos(part3.getPosX2(), part3.getPosY2());
						}
						
						if((target1 && target2) && !done)
						{
							calcSpeedXY();
							ball.setSpeedX(speeds.get(0));
							ball.setSpeedY(-speeds.get(1));
							
						}					
						
						ball.updateSpeedY();
						if(paint)
						{
							mainFrame.redraw();
						}
						mainFrame.redraw();
					
					}
				}				
			});
	
			th.start();
		
	}
	
	public void calcSpeedXY()
	{
		speeds.clear();
		done = true;
		double angle = joints.get(0).getAngle()+joints.get(1).getAngle()+90;
		double length = Math.sqrt( ( ( part3.getPosX2() - part2.getPosX1() ) * (  part3.getPosX2() - part2.getPosX1()  ) ) + ( ( part3.getPosY2() - part2.getPosY1()  ) * (  part3.getPosY2() - part2.getPosY1()  ) ))	;
		double vTan = length/70 * joints.get(0).getSpeed();
		double x = Math.sin(angle*Math.PI/180)*vTan;
		double y = Math.cos(angle*Math.PI/180)*vTan;
		
		speeds.add(x);
		speeds.add(y);
	}
	public void setTarget(int target)
	{
		this.target.setX(target);
	}
	public void setDrawing(Drawing frame)
	{
		mainFrame = frame;
	}
	public void togglePaint()
	{
		paint = !paint;
	}
	public int getPopulation()
	{
		return population;
	}
	public int getTarget()
	{
		return target.getX();
	}
}