package Maths;

import java.util.ArrayList;

import objects.Target;

public class ML 
{
	private int target;
	private int oldAng1;
	private int oldAng2;
	private int error;	
	
	public ML(Target target)
	{
		this.target = target.getX();
	}
	
	public ArrayList<Integer> learn(int oldAng1, int oldAng2, int target,int landX){
		ArrayList<Integer> angles = new ArrayList<Integer>();
		return angles;	
	}
	
	public void setErrorX(int x) 
	{
		this.error = x;
	}
	
}
