package Maths;

import java.util.ArrayList;

import objects.Target;

public class GD 
{
	private int target;

	private double ang1;
	private double ang2;
	
	private double land = 0;	
	
	private double gamma = 0.005;


	
	public GD(Target target)
	{
		this.target = target.getX();

	}
	
	public ArrayList<Double> learn(double d, double e, int target){
		ArrayList<Double> angles = new ArrayList<Double>();
		this.ang1 = d;
		this.ang2 = e;
		
		//curX -= gamma * f.apply(prevX);
		//previousStepSize = abs(curX - prevX);
		if(Math.abs(target-land) > 10)
		{
			if(target - land < 0) 
			{
				ang1 -= gamma*land;
				ang2 -= gamma/2*land;
			}
			else
			{
				ang1 +=gamma/2*land;
				ang2 +=gamma*land;
			}
		}
		else
		{
			ang1 = d;
			ang2 = e;
			System.out.println("Perfect");
		}
		angles.add(ang1);
		angles.add(ang2);
		
		
		return angles;	
	}
	
	public void setLandX(double x) 
	{
		this.land = x;
	}
	
}
