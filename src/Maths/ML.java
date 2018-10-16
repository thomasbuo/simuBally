package Maths;

import java.util.ArrayList;

import objects.Target;

public class ML 
{
	private int target;
	private int oldAng1;
	private int oldAng2;
	private double error = 0;	
	private double learning_rate = 1.05;
	private double fake_learning_rate = 2.0;
	
	public ML(Target target)
	{
		this.target = target.getX();
	}
	
	public ArrayList<Double> learn(double d, double e, int target){
		ArrayList<Double> angles = new ArrayList<Double>();
		
		if(error > 10)
		{
			if(d == d * +learning_rate)
			{
				angles.add((d * +fake_learning_rate));
			}
			else
			{
				angles.add((d * +learning_rate));
			}
			if(e == e * +learning_rate)
			{
				angles.add((e * +fake_learning_rate));
			}
			else 
			{
				angles.add((e * +learning_rate));
			}
			
			
		}
		else if(error < -10)
		{
			
			if(d == (d + d*-learning_rate))
			{
				angles.add((d + d*-fake_learning_rate));
			}
			else
			{
				angles.add((d + d*-learning_rate));
			}
			if(e == (e + e*-learning_rate))
			{
				angles.add((e + e*-fake_learning_rate));
			}
			else 
			{
				angles.add((e + e*-learning_rate));
			}
			
			angles.add( (d + d*-learning_rate));
			angles.add( (e + e*-learning_rate));
		}
		else 
		{
			angles.add(d);
			angles.add(e);
		}
		return angles;	
	}
	
	public void setErrorX(double error2) 
	{
		this.error = error2;
	}
	
}
