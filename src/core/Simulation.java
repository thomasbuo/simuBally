package core;

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
	private int population = 100;
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
	private boolean generated = false;
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
	private int targetPos = 0;
			//private GD gd;

	private double finalPosX;
	private int finalPosY;
	private ArrayList<Joint> joints;
	
	private Drawing mainFrame;
	
	private double hitCount = 0;
	private int populationIteration = 0;
	private Random r = new Random();
	
	private double currentSummedError=0;
	private double bestSummedError=10000;
	private ArrayList<Angle> bestGen = new ArrayList();
	
	private int generation=0;
	
	private NeuralCore nc = new NeuralCore(population); 
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
			
				
				ArrayList<Double> targetlist = new ArrayList<>();
				targetlist.add((double)target.getX()/1000);
				//ArrayList<Double> angles = nc.getPopulation().get(population_counter).getNN().guess(targetlist);
				ArrayList<Double> angles = nc.guess(targetlist);
				joints.get(0).setTargetAngle(-(int)(maxRotation1/2) + angles.get(0)*maxRotation1);
				joints.get(1).setTargetAngle(-(int)(maxRotation2/2)  + angles.get(1)*maxRotation2);	
					
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
						
						double actualError =finalPosX - target.getX();
						double error =(finalPosX - target.getX())*(finalPosX - target.getX());
						double score = Math.abs(target.getX()*target.getX() - error);
					
						if(15 >= error)
						{
							hitCount ++;
							
						}
						
						
						
						//System.out.println(finalPosX);
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
							angles = gd.learn(joints.get(0).getTargetAngle(), joints.get(1).getTargetAngle(), target.getX());
							joints.get(0).setTargetAngle(angles.get(0));
							joints.get(1).setTargetAngle(angles.get(1));
							System.out.println("an1: "+angles.get(0)+" ang2: "+angles.get(1)+" error: "+error);
						}
						else
						{
							
							//
						//this is for genetic	
							nc.setError(error);
							nc.getPopulation().get(population_counter).setScore(score);
							nc.getPopulation().get(population_counter).setError(error+nc.getPopulation().get(population_counter).getError());
							
							population_counter++;
							if(population_counter == (population))
							{								
								
								
//								generation++;
								populationIteration++;
								if((hitCount/population)>=1.00)
								{
									is_running = false;
								}
								population_counter = 0;
								
//								if(populationIteration == 50)
//								{
//									generation++;
//									
//									populationIteration = 0;	
//									if(target.getX()>300)
//									{
//										target.setX(130);
//									}
//									target.setX(target.getX()+20);									
//									
//								}
								double percent = 0.1;
								double averageScore = 0;
								sort(nc.getPopulation());
								for(int i = 0; i< nc.getPopulation().size()*percent;i++)
								{
									averageScore+=nc.getPopulation().get(i).getScore();
								}
								double average = averageScore/(nc.getPopulation().size()*percent);
								
								currentSummedError += Math.abs(average-target.getX()*target.getX());
								
								System.out.println("avg "+generation+": "+average+" target: "+target.getX()+ " error: "+Math.abs(average-target.getX()*target.getX()));
								if((average> target.getX()*target.getX()-500) &&( averageScore/(nc.getPopulation().size()*percent)< target.getX()*target.getX()+500) )
								{
									
									nc.storePopulation();
									if(target.getX()>=300)
									{
										if(currentSummedError/6 < bestSummedError)
										{
											bestSummedError = currentSummedError/6;
											bestGen=nc.getPopulation();
										}
										
										System.out.println("currenterror "+ currentSummedError/6+" besterror "+bestSummedError);
										currentSummedError = 0;
										generation++;
										generated = true;
										nc.trainOnPreviousBest(nc.getPopulation());
										target.setX(180);
									}
									target.setX(target.getX()+20);	
								}
								
								
								
								if(!generated)
								{
									nc.train(nc.getPopulation());	
								}
								
								generated = false;
								targetlist.clear();
								targetlist.add((double)target.getX()/1000);								
								
								hitCount=0;
							}
							
							angles = nc.getPopulation().get(population_counter).getNN().guess(targetlist);
							joints.get(0).setTargetAngle(-(int)(maxRotation1/2) + angles.get(0)*maxRotation1);
							joints.get(1).setTargetAngle(-(int)(maxRotation2/2)  + angles.get(1)*maxRotation2);	
							
							
							//new angles!!!
							//end genetic
						}	
						
						if(paint)
						{
							angles = gd.learn(joints.get(0).getTargetAngle(), joints.get(1).getTargetAngle(), target.getX());
						
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
		this.target.setX(target);
	}
	public int getTarget()
	{
		return target.getX();
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
	
	public NeuralCore getNeuralCore()
	{
		return nc;
	}
	public void sort(ArrayList<Angle> sortableList)
	{
		boolean action = true;
		while(action)
		{
			action = false;
			for(int i = 1; i < sortableList.size(); i++)
			{
				if(sortableList.get(i).getScore() > sortableList.get(i-1).getScore())
				{
					Collections.swap(sortableList, i, i-1);
					action = true;
				}
			}
		}
	}
}