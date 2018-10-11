package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import objects.ArmPart;
import objects.Ball;
import objects.Floor;
import objects.Joint;

import java.sql.Time;
import java.util.*;


public class Simulation {
	
	private int targetX = 200;
	private int targetY ;
	private boolean is_running = false;
	
	private long start_time;
	private double current_time;
	private long total_calculation_time;
	private double simulated_seconds_per_real_second = 0.01;
	private boolean full_speed = false;
	private int visualization_frequency = 100000;
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

	private int finalPosX;
	private int finalPosY;
	private ArrayList<Joint> joints;
	
	private Drawing mainFrame;
	
	public Simulation(ArrayList<Joint> joints, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball, Floor floor)
	{
		this.ball = ball;
		this.floor = floor;
		targetY = floor.getY();
		this.joints = joints;
		this.part2 = part2;
		this.part3 = part3;
		
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
						finalPosX = (int) ball.getPosX();
						finalPosY = floor.getY();
						is_running = false;
					}	
					

					// update graphics and statistics
					step++;
					if (step % this.visualization_frequency == 0) 
					{
						target1 = true;
						if (joints.get(0).getTargetAngle() > joints.get(0).getAngle())
						{
							//System.out.println("Angle1" + joints.get(0).getAngle());
							joints.get(0).setAngle((int) (joints.get(0).getAngle()+joints.get(0).getSpeed()));
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
							joints.get(1).setAngle((int) (joints.get(1).getAngle()+joints.get(1).getSpeed()));
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
						
						//System.out.println((joints.get(0).getAngle()+joints.get(1).getAngle())+90/**Math.PI/180*/);
						
						
						ball.updateSpeedY();
						mainFrame.redraw();
						System.out.println("X " + ball.getPosX() + " Y " + ball.getPosY());
					}
				}				
			});
	
			th.start();
		
	}
	
	public void calcSpeedXY()
	{
		speeds.clear();
		done = true;
		int angle = joints.get(0).getAngle()+joints.get(1).getAngle()+90;
		double length = Math.sqrt( ( ( part3.getPosX2() - part2.getPosX1() ) * (  part3.getPosX2() - part2.getPosX1()  ) ) + ( ( part3.getPosY2() - part2.getPosY1()  ) * (  part3.getPosY2() - part2.getPosY1()  ) ))	;
		//System.out.println("length: " + length);
		double vTan = length/70 * joints.get(0).getSpeed();
		//System.out.println("vTan: " + vTan);
		double x = Math.sin(angle*Math.PI/180)*vTan;
		//System.out.println("x: " + x);
		double y = Math.cos(angle*Math.PI/180)*vTan;
		//System.out.println("y: " + y);
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
}
