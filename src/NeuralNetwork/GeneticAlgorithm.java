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
	
	private int height;
		
	private Random r = new Random();
	public GeneticAlgorithm()
	{		
		
	}
	
	public ArrayList<Angle> generateNewPopulation(ArrayList<Angle> angles)
	{		
		
		sort(angles);
		for(Angle a : angles)
		{
			System.out.print("Scores: "+a.getScore()+", ");
		}
		System.out.println();
		int totalScore = computeTotalScore(angles);
		
		ArrayList<Angle> newAngles = new ArrayList();
		
		ArrayList<Double> probabilities = new ArrayList<>();
		for(int i =0;i<angles.size();i++)
		{
			probabilities.add((angles.get(i).getScore()/totalScore));
		}
		
		ArrayList<Angle> batch = new ArrayList<Angle>(angles.subList(0, (int)(angles.size()*KEEP_PERCENT_TOP)));

		for(Angle a : batch)
		{
			Angle ang = new Angle(a.getAngle1(),a.getAngle2(), 1, 2, 10, 1);
			ang.setNN(a.getNN());
			newAngles.add(ang);
		}
		
		while(newAngles.size()<angles.size())
		{
			double p1 = r.nextDouble();
			double p2 = r.nextDouble();
			
			Angle a1 = null;
			Angle a2 = null;
			
			double sum=0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p1)
				{
					a1 = angles.get(j);
				}
			}
			
			sum = 0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p2)
				{
					a2 = angles.get(j);
				}
			}
			
			Angle child = breed(a1,a2);
			newAngles.add(child);
			
		}
		mutate(newAngles);
		
		/*
		ArrayList<Bird> batch = new ArrayList<Bird>(birds.subList(0, (int)(birds.size()*KEEP_PERCENT_TOP)));
		
		for(int i = 0; i< (int)(0.10*birds.size());i++)
		{
			Bird b= new Bird(height); 
			b.setNeuralNetwork(birds.get(0).getNeuralNetwork());
		}
		
		for(Bird bird : batch)
		{
			Bird b = new Bird(height);
			b.setNeuralNetwork(bird.getNeuralNetwork());
			newBirds.add(b);
		}
		
		batch =  new ArrayList<Bird>(birds.subList((int)(birds.size() - birds.size()*KEEP_PERCENT_BOTTOM), birds.size()-1));
		
		for(Bird bird : batch)
		{
			Bird b = new Bird(height);
			b.setNeuralNetwork(bird.getNeuralNetwork());
			newBirds.add(b);
		}
		
		ArrayList<Bird> parents = new ArrayList();
		
		for(Bird bird : newBirds)
		{
			Bird b = new Bird(height);
			b.setNeuralNetwork(bird.getNeuralNetwork());
			parents.add(b);
		}*/
		
		
		
		return newAngles;
	}
	
	public int computeTotalScore(ArrayList<Angle> angles)
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
				if(Math.abs(sortableList.get(i).getScore()) > Math.abs(sortableList.get(i-1).getScore()))
				{
					Collections.swap(sortableList, i, i-1);
					action = true;
				}
			}
		}
	}
	
	public void mutate(ArrayList<Angle> angles)
	{
		for(int i =0; i< angles.size();i++)
		{
			for(int j = 0; j< angles.get(i).getNN().getWeights().size(); j++)
			{
				if(r.nextDouble()>0.97)
				{
					angles.get(i).getNN().getWeights().get(j).setWeight(r.nextDouble()*2 -1);
				}
			}
		}
	}
	
	public Angle breed(Angle p1, Angle p2)
	{
		
			
			Angle child = new Angle(0.0,0.0, 1, 2, 10, 1);
			
			ArrayList<Weight> weightP1 = p1.getNN().getWeights();
			int max = weightP1.size();
			int halfMax = (int)(r.nextDouble()*max);
			for(int j = 0; j < halfMax; j++)
			{
				String id1 = weightP1.get(j).getBackPerceptron().getID();
				String id2 = weightP1.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <max;d++ )
				{
					ArrayList<Weight> newBirdsWeights = child.getNN().getWeights();
					if(newBirdsWeights.get(d).getBackPerceptron().getID().equals(id1) && newBirdsWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newBirdsWeights.get(d).setWeight(weightP1.get(j).getWeight());
					}
				}
			}
			
			ArrayList<Weight> weightP2 = p2.getNN().getWeights();
			for(int j = halfMax; j < max; j++)
			{
				String id1 = weightP2.get(j).getBackPerceptron().getID();
				String id2 = weightP2.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <max;d++ )
				{
					ArrayList<Weight> newBirdsWeights = child.getNN().getWeights();
					if(newBirdsWeights.get(d).getBackPerceptron().getID().equals(id1) && newBirdsWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newBirdsWeights.get(d).setWeight(weightP2.get(j).getWeight());
					}
				}
			}	
			
			for(int i = 0; i < child.getNN().getLayers().size(); i++)
			{
				for(int j = 0; j < child.getNN().getLayers().get(i).size();j++)
				{
					double chance = r.nextDouble();
					if(chance<0.5)
					{
						child.getNN().getLayers().get(i).get(j).setBias(p1.getNN().getLayers().get(i).get(j).getBias());
					}
					else 
					{
						child.getNN().getLayers().get(i).get(j).setBias(p2.getNN().getLayers().get(i).get(j).getBias());
					}
				}
			}
			return child;
		
		
		
	}
}
