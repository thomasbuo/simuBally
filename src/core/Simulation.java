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
	private float simulated_seconds_per_real_second = 1;
	private boolean full_speed = false;
	private int visualization_frequency;
	private double ns_used;
	
	private double realistic_time_in_seconds;
	private int days_simulated;
	
	private Ball ball;
	private Floor floor;
	private int finalPosX;
	private int finalPosY;
	
	public Simulation(ArrayList<Joint> joints, ArmPart part1, ArmPart part2, ArmPart part3, Ball ball, Floor floor)
	{
		this.ball = ball;
		this.floor = floor;
		targetY = floor.getY();
		
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
						finalPosX = ball.getPosX();
						finalPosY = floor.getY();
						
					}
					
					
					
					// update graphics and statistics
					step++;
					if (step % this.visualization_frequency == 0) {
						
						ball.setSpeedY();
						

						
					}
				}				
			});
	
			th.start();
		
	}
	public void setTarget(int target)
	{
		targetX = target;
	}

}
