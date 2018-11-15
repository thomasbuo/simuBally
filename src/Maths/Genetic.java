package Maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.Simulation;

public class Genetic {

	private ArrayList<Angle> angles = new ArrayList();
	private ArrayList<Angle> batch1 = new ArrayList();
	private ArrayList<Angle> batch2 = new ArrayList();
	private ArrayList<Angle> batch3 = new ArrayList();
	private ArrayList<Double> averages = new ArrayList();
	
	private int generation = 0;
	
	private Random r = new Random();
	private int low1 = -120;
	private int high1 = 50;
	private int low2 = -30;
	private int high2 = 120;
	
	private Simulation s;
	
	public Genetic(Simulation s)
	{
		this.s = s;
		s.togglePaint();
		for(int i = 0; i< s.getPopulation(); i++)
		{
			double a1 = r.nextInt(high1-low1) + low1;
			double a2 = r.nextInt(high2-low2) + low2;
			angles.add(new Angle(a1, a2,1,2,4,1));
		}
	}
	
	public void compute()
	{
		generation++;
		
		boolean action = true;
		while(action)
		{
			action = false;
			for(int i = 1; i < angles.size()-1; i++)
			{
				if(Math.abs(angles.get(i).getError()) < Math.abs(angles.get(i-1).getError()))
				{
					//System.out.println("swap: "+ angles.get(i).getError()+", "+ angles.get(i).getAngle1()+", "+angles.get(i).getAngle2()+" with: "+ angles.get(i-1).getError()+", "+ angles.get(i-1).getAngle1()+", "+angles.get(i-1).getAngle2());
					Collections.swap(angles, i, i-1);
					action = true;
				}
			}
		}
		
		double avg = 0;
		for(int i=0;i<angles.size()-1;i++)
		{
			//System.out.println(angles.get(i).getError());
			avg = avg+angles.get(i).getError();
			//System.out.println("hjbsdvkjhbasvjhbalfdjvbsdjvbdajv: "+i+", "+angles.size()+", "+((i*1.00)/angles.size()) +", "+i/angles.size()*100);
		}
		
		
		
		int b1end = (int)(s.getPopulation() * 0.15);
		int b2end = (int)(s.getPopulation() * 0.35);
		
		batch1 = new ArrayList<Angle>(angles.subList(0, b1end));
		batch2 = new ArrayList<Angle>(angles.subList(b1end+1, b2end));
		batch3 = new ArrayList<Angle>(angles.subList(b2end+1, angles.size()-1));
		
		angles = new ArrayList();
		
		System.out.println("b1");
		for(Angle a : batch1)
		{
			System.out.println(a.getError()+", "+a.getAngle1()+", "+a.getAngle2());
			
		}
		/*
		System.out.println("b2");
		for(Angle a : batch2)
		{
			System.out.println(a.getError());
			
		}
		System.out.println("b3");
		for(Angle a : batch3)
		{
			System.out.println(a.getError());
			
		}*/
		System.out.println("avg: "+ avg);
		averages.add(avg/s.getPopulation());
		
		for(int i = 0; i< s.getPopulation(); i++)
		{
			int a1 = 0;
			int a2 = 0;
			
			/*for(int j = 0 ; j < 2; j++)
			{
				if((r.nextInt(10-1) +1) <= 7)
				{
					if(j==0)
						a1 = batch1.get(r.nextInt((batch1.size()-1))).getAngle1();
					else
						a2 = batch1.get(r.nextInt((batch1.size()-1))).getAngle2();
				}
				else if((r.nextInt(10-1) +1) > 7 && (r.nextInt(10-1) +1) <= 9)
				{
					if(j==0)
						a1 = batch2.get(r.nextInt((batch2.size()-1))).getAngle1();
					else
						a2 = batch2.get(r.nextInt((batch2.size()-1))).getAngle2();
				}
				else 
				{
					if(j==0)
						a1 = batch3.get(r.nextInt((batch3.size()-1))).getAngle1();
					else
						a2 = batch3.get(r.nextInt((batch3.size()-1))).getAngle2();
				}
			}*/
			
			int z = 0;
			if((r.nextInt(100-1)+1) > 2) 
			{		
				int rand = r.nextInt(100-1) +1;
				if(rand <= 90)
				{	
					//System.out.println("batch1");
					z = r.nextInt((batch1.size()-1));
					
					angles.add(batch1.get(z));
				}
				else if(rand> 90 && rand <= 97)
				{
					//System.out.println("batch2");
					z = r.nextInt((batch2.size()-1));
					
					angles.add(batch2.get(z));
				}
				else 
				{		
					//System.out.println("batch3");
					z = r.nextInt((batch3.size()-1));
					
					angles.add(batch3.get(z));
				}
				
				//angles.add(new Angle(a1, a2));
			}
			else
			{
				double a3 = r.nextInt(high1-low1) + low1;
				double a4 = r.nextInt(high2-low2) + low2;
				angles.add(new Angle(a3, a4,1,2,4,1));
			}			
		}
		
	}
	public ArrayList<Angle> getAngles()
	{
		return angles;
	}
	public int getGeneration()
	{
		return generation;
	}
	public ArrayList<Double> getAverrages()
	{
		return averages;
	}
}