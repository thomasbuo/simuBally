package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import Maths.Angle;
import Maths.Genetic;

import Maths.GD;

import Maths.ML;
import Maths.NeuralCore;
import objects.ArmPart;
import objects.Ball;
import objects.Floor;
import objects.Joint;
import objects.Target;

import java.sql.Time;
import java.util.*;


public class Simulation {
	
	private boolean paint = false;
	
	private int targetX = 200;
	private int targetY ;
	private boolean is_running = false;
	//genetic
	private int iterations = 20;
	private int population = 20;
	private int population_counter = 0;
	private boolean plop = false;
	private long start_time;
	private double current_time;
	private long total_calculation_time;
	private double simulated_seconds_per_real_second =100 /0.01;
	private boolean full_speed = false;
	private int visualization_frequency = 100;
	private double ns_used;
	private ArrayList<Double> speeds = new ArrayList<Double>();
	
	private double realistic_time_in_seconds;
	private int days_simulated;
	
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
	private int finalPosY;
	private ArrayList<Joint> joints;
	
	private Drawing mainFrame;
	
	private double hitCount = 0;
	private int populationIteration = 0;
	private Random r = new Random();
	
	private int generation=-1;
	public Simulation(ArrayList<Joint> joints, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball, Floor floor, Target target)
	{
		this.ball = ball;
		this.floor = floor;
		targetY = floor.getY();
		this.joints = joints;
		this.part2 = part2;
		this.part3 = part3;
		//this.gd = new GD(target);
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
						double error = Math.abs(finalPosX - target.getX());
						
						if(15 >= error)
						{
							hitCount ++;
							
						}
						double score = - error;

						//commented for GD
						/*
						if(15 >= Math.abs((finalPosX - target.getX())))
						{
							if(g.getGeneration()>iterations)
								is_running = false;
							System.out.println(" perfect "+joints.get(0).getTargetAngle()+" "+joints.get(1).getTargetAngle());
						}
						*/
						// *
					//	gd.setLandX(finalPosX/* - target.getX()*/);

						
						if(paint)
						{
							gd.setLandX(finalPosX);
							ArrayList<Double> angles = gd.learn(joints.get(0).getTargetAngle(), joints.get(1).getTargetAngle(), target.getX());
							joints.get(0).setTargetAngle(angles.get(0));
							joints.get(1).setTargetAngle(angles.get(1));
							System.out.println("an1: "+angles.get(0)+" ang2: "+angles.get(1)+" error: "+error);
						}
						else
						{
							//commented for GD
							/*
							g.getAngles().get(population_counter).setError(error);
							population_counter++;
							/////////////////////////////////////////////////////////////////////////////////////////////
							if(population_counter == (population-1))
							{
								population_counter = 0;
								System.out.println();
								for(int p = 0; p<g.getAverrages().size()-1;p++)
								{
									System.out.print(g.getAverrages().get(p)+", ");
								}
								System.out.println();
								
								g.compute();											
								
							}
							Angle a = g.getAngles().get(population_counter);
							joints.get(0).setTargetAngle(a.getAngle1());
							joints.get(1).setTargetAngle(a.getAngle2());
							System.out.println("an1: "+a.getAngle1()+" ang2: "+a.getAngle2()+" error: "+ error+ " generation: "+ g.getGeneration()+" popc: "+population_counter);						
						*/
							
							
							
							nc.setError(error);
							nc.getPopulation().get(population_counter).setScore(nc.getPopulation().get(population_counter).getScore()+score);
							ArrayList<Double> targetlist = new ArrayList<>();
							targetlist.add(target.getX()/1.0);
							ArrayList<Double> angles = nc.getPopulation().get(population_counter).getNN().guess(targetlist);
							//System.out.println("Angles: " + angles.get(0)*maxRotation + " Second: " + angles.get(1)*maxRotation);
							joints.get(0).setTargetAngle(-(int)(maxRotation1/2) + angles.get(0)*maxRotation1);
							joints.get(1).setTargetAngle(-(int)(maxRotation2/2)  + angles.get(1)*maxRotation2);
							population_counter++;
							if(population_counter == (population-1))
							{
								populationIteration++;
								if((hitCount/population)>=1.00)
								{
									is_running = false;
								}
								population_counter = 0;
								if(populationIteration == 20)
								{
									generation++;
									System.out.println("gen: "+generation+" Hit count: " + hitCount +" %: "+(double)(hitCount/population)/populationIteration*100);

									for(int i = 0; i< nc.getPopulation().size();i++)
									{
										nc.getPopulation().get(i).setScore(nc.getPopulation().get(i).getScore()/populationIteration);
									}
									populationIteration = 0;	
									target.setX((int)(r.nextDouble()*300)+100);
									nc.train(nc.getPopulation());	
									
								}
								//target.setX((int)(r.nextDouble()*300)+100);
								target.setX((int)(r.nextDouble()*300)+100);
								
								//nc.train(nc.getPopulation());	
								hitCount=0;
								
							}
							//new angles!!!
							
						}					
						if(paint)
						{
							ArrayList<Double> angles = gd.learn(joints.get(0).getTargetAngle(), joints.get(1).getTargetAngle(), target.getX());
						
							joints.get(0).setTargetAngle(angles.get(0));
							joints.get(1).setTargetAngle(angles.get(1));
						}
						
						//System.out.println("ang1: "+angles.get(0)+" ang2: "+angles.get(1));
						//System.out.println("error: "+(finalPosX - target.getX())+ " Landed: " + finalPosX);
						ball.setPos(ball.getOriginalPosX(), ball.getOriginalPosY());
						
						//use nn set error;
						
						joints.get(0).setAngle(joints.get(0).getOriginalAngle1());
						joints.get(1).setAngle(joints.get(1).getOriginalAngle2());
						part2.setPosX2(2);
						part2.setPosY2(2);
						part3.setPosY1(part2.getPosY2());
						part3.setPosX1(part2.getPosX2());
						
						part3.setPosX2(3);
						part3.setPosY2(3);
						//is_running = false;
						
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
							//System.out.println("Angle1" + joints.get(0).getAngle());
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
							//System.out.println("Angle2" + joints.get(1).getAngle());
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
							//System.out.println("X " + ball.getSpeedX()+ " Y " + ball.getSpeedY());
						}
						
						//System.out.println((joints.get(0).getAngle()+joints.get(1).getAngle())+90/*Math.PI/180/);
						
						
						ball.updateSpeedY();
						if(paint)
						{
							mainFrame.redraw();
						}
						mainFrame.redraw();
						//System.out.println("X " + ball.getPosX() + " Y " + ball.getPosY());
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
		//System.out.println("length: " + length);
		double vTan = length/70 * joints.get(0).getSpeed();
		//System.out.println("vTan: " + vTan);
		double x = Math.sin(angle*Math.PI/180)*vTan;
		//System.out.println("x: " + x);
		double y = Math.cos(angle*Math.PI/180)*vTan;
		//System.out.println("x: " + x +" y: " + y);
		speeds.add(x);
		speeds.add(y);
	}
	public void setTarget(int target)
	{
		targetX = target;
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
}