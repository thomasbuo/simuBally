package Maths;

import java.util.ArrayList;

import objects.Target;

public class ML 
{
	private int target;
	private int oldAng1;
	private int oldAng2;
	private int error = 0;	
	private double learning_rate = 1.05;
	private double fake_learning_rate = 2.0;
	
	public ML(Target target)
	{
		this.target = target.getX();
	}
	
	public ArrayList<Integer> learn(int oldAng1, int oldAng2, int target){
		ArrayList<Integer> angles = new ArrayList<Integer>();
		
		if(error > 10)
		{
			if(oldAng1 == oldAng1 * +learning_rate)
			{
				angles.add((int) (oldAng1 * +fake_learning_rate));
			}
			else
			{
				angles.add((int) (oldAng1 * +learning_rate));
			}
			if(oldAng2 == oldAng2 * +learning_rate)
			{
				angles.add((int) (oldAng2 * +fake_learning_rate));
			}
			else 
			{
				angles.add((int) (oldAng2 * +learning_rate));
			}
			
			
		}
		else if(error < -10)
		{
			
			if(oldAng1 == (oldAng1 + oldAng1*-learning_rate))
			{
				angles.add((int) (oldAng1 + oldAng1*-fake_learning_rate));
			}
			else
			{
				angles.add((int) (oldAng1 + oldAng1*-learning_rate));
			}
			if(oldAng2 == (oldAng2 + oldAng2*-learning_rate))
			{
				angles.add((int) (oldAng2 + oldAng2*-fake_learning_rate));
			}
			else 
			{
				angles.add((int) (oldAng2 + oldAng2*-learning_rate));
			}
			
			angles.add((int) (oldAng1 + oldAng1*-learning_rate));
			angles.add((int) (oldAng2 + oldAng2*-learning_rate));
		}
		else 
		{
			angles.add(oldAng1);
			angles.add(oldAng2);
		}
		return angles;	
	}
	
	public void setErrorX(int x) 
	{
		this.error = x;
	}
	
}
