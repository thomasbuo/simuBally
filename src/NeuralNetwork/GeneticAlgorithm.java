package NeuralNetwork;
//HELLOOOO!!
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import Maths.Angle;

public class GeneticAlgorithm {

	private final double KEEP_PERCENT_TOP = 0.10;
	private final double MUTATION_RATE = 0.03;
	private int height;
		
	private Random r = new Random();
	public GeneticAlgorithm()
	{		
		
	}
	
	public ArrayList<Angle> generateNewPopulation(ArrayList<Angle> angles)
	{		
		
		sort(angles);
		
//		double averageScore = 0;
//		for(int i = 0; i< angles.size();i++)
//		{
//			averageScore+=angles.get(i).getScore();
//		}
//		System.out.println("avg "+averageScore/angles.size()+" ");
//		
//		for(int i = 0 ; i< angles.size();i++)
//		{
//			System.out.println(angles.get(i).getScore()+" ");
//		}
		
		int totalScore = computeTotalScore(angles); //sum
		ArrayList<Angle> newAngles = new ArrayList();
		
		ArrayList<Double> probabilities = new ArrayList<>();
		
		for(int i =0;i<angles.size();i++)
		{
			probabilities.add((angles.get(i).getScore()/totalScore));
		}
		
		ArrayList<Angle> batch = new ArrayList<Angle>();
		for(int i = 0; i< KEEP_PERCENT_TOP*angles.size();i++)
		{
			batch.add(angles.get(0));
		}

		for(Angle a : batch)
		{
			Angle ang = new Angle(a.getAngle1(),a.getAngle2(), 1, 2, 20, 3);
			ang.setNN(a.getNN());
			newAngles.add(ang);
		}
		
		while(newAngles.size()<angles.size())
		{

			double p1;
			double p2;
			
			if(r.nextDouble()>0.2)
			{
				p1 = r.nextDouble()*0.1;
				p2 = r.nextDouble()*0.1;
			}
			else 
			{
				p1 = r.nextDouble();
				p2 = r.nextDouble();
			}

			Angle a1 = null;
			Angle a2 = null;
			
			double sum=0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p1)
				{
					
					a1 = angles.get(j);
					break;
				}
			}
			
			sum = 0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p2)
				{
					
					a2 = angles.get(j);
					break;
				}
			}
//			Angle ang = new Angle(a1.getAngle1(),a1.getAngle2(), 1, 2, 20, 5);
//			ang.setNN(a1.getNN());
//			newAngles.add(ang);
//			
//			Angle ang2 = new Angle(a2.getAngle1(),a2.getAngle2(), 1, 2, 20, 5);
//			ang.setNN(a2.getNN());
//			newAngles.add(ang);
//			
//			System.out.println(a1+" "+a2);
			
			Angle child = breed(a1,a2);
			newAngles.add(child);
			
		}
		
		mutate(newAngles);
		
		return newAngles;
	}
	
	public int computeTotalScore(ArrayList<Angle> angles) //sum
	{
		int score = 0;
		for(Angle a : angles)
		{
			score+= a.getScore();
		}
		return score;
	}
	
	public void sort(ArrayList<Angle> sortableList)
	{
		boolean action = true;
		while(action)
		{
			action = false;
			for(int i = 1; i < sortableList.size()-1; i++)
			{
				if(sortableList.get(i).getScore() > sortableList.get(i-1).getScore())
				{
					Collections.swap(sortableList, i, i-1);
					action = true;
				}
			}
		}
	}
	
	public void mutate(ArrayList<Angle> angles)
	{
		//weights
		for(int i =0; i< angles.size();i++)
		{
			for(int j = 0; j< angles.get(i).getNN().getWeights().size(); j++)
			{ 
				double randomv = r.nextDouble();
				
				if(randomv<MUTATION_RATE)
				{
					
					angles.get(i).getNN().getWeights().get(j).setWeight(r.nextDouble()*2 -1);
				}
				
			}
		}
		//bias
//		for(int i =0; i< angles.size();i++)
//		{
//			for(int j = 0; j< angles.get(i).getNN().getLayers().size(); j++)
//			{
//				for(int k = 0; k< angles.get(i).getNN().getLayers().get(j).size(); k++)
//				{
//					if(r.nextDouble()<MUTATION_RATE)
//					{
//						angles.get(i).getNN().getLayers().get(j).get(k).setBias(r.nextDouble()*2 -1);						
//					}
//				}
//				
//			}
//		}
		
		
	}
	
	public Angle breed(Angle p1, Angle p2)
	{
		
		
		Angle child = new Angle(0.0,0.0, 1, 2, 20, 3);
		
		ArrayList<Weight> weightP1 = p1.getNN().getWeights();
		ArrayList<Weight> weightP2 = p2.getNN().getWeights();
		
		
		ArrayList<Weight> newAngleWeights = child.getNN().getWeights();
		for(int j = 0; j<newAngleWeights.size();j++)
		{
			if(r.nextDouble()>0.5)
			{
				String id1 = weightP1.get(j).getBackPerceptron().getID();
				String id2 = weightP1.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <newAngleWeights.size();d++ )
				{
					
					if(newAngleWeights.get(d).getBackPerceptron().getID().equals(id1) && newAngleWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newAngleWeights.get(d).setWeight(weightP1.get(j).getWeight());
					}
				}
			}
			else 
			{
				String id1 = weightP2.get(j).getBackPerceptron().getID();
				String id2 = weightP2.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <newAngleWeights.size();d++ )
				{
					
					if(newAngleWeights.get(d).getBackPerceptron().getID().equals(id1) && newAngleWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newAngleWeights.get(d).setWeight(weightP2.get(j).getWeight());
					}
				}
			}
		}
			
			
			//bias
//			for(int i = 0; i < child.getNN().getLayers().size(); i++)
//			{
//				for(int j = 0; j < child.getNN().getLayers().get(i).size();j++)
//				{
//					double chance = r.nextDouble();
//					if(chance<0.5)
//					{
//						child.getNN().getLayers().get(i).get(j).setBias(p1.getNN().getLayers().get(i).get(j).getBias());
//					}
//					else 
//					{
//						child.getNN().getLayers().get(i).get(j).setBias(p2.getNN().getLayers().get(i).get(j).getBias());
//					}
//				}
//			}
			return child;
		
		
		
	}
}
