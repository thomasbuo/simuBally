package NeuralNetwork;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import Objects.Bird;

public class GeneticAlgorithm {

	private final double KEEP_PERCENT_TOP = 0.10;
	
	private int height;
		
	private Random r = new Random();
	public GeneticAlgorithm(int height)
	{		
		this.height = height;
	}
	
	public ArrayList<Bird> generateNewPopulation(ArrayList<Bird> birds)
	{		
		
		sort(birds);
		for(Bird b : birds)
		{
			System.out.print(b.getScore()+", ");
		}
		System.out.println();
		int totalScore = computeTotalScore(birds);
		
		ArrayList<Bird> newBirds = new ArrayList();
		
		ArrayList<Double> probabilities = new ArrayList<>();
		for(int i =0;i<birds.size();i++)
		{
			probabilities.add((birds.get(i).getScore()/totalScore));
		}
		
		ArrayList<Bird> batch = new ArrayList<Bird>(birds.subList(0, (int)(birds.size()*KEEP_PERCENT_TOP)));

		for(Bird bird : batch)
		{
			Bird b = new Bird(height);
			b.setNeuralNetwork(bird.getNeuralNetwork());
			newBirds.add(b);
		}
		
		while(newBirds.size()<birds.size())
		{
			double p1 = r.nextDouble();
			double p2 = r.nextDouble();
			
			Bird b1 = null;
			Bird b2 = null;
			
			double sum=0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p1)
				{
					b1 = birds.get(j);
				}
			}
			
			sum = 0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p2)
				{
					b2 = birds.get(j);
				}
			}
			
			Bird child = breed(b1,b2);
			newBirds.add(child);
			
		}
		mutate(newBirds);
		
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
		
		
		
		return newBirds;
	}
	
	public int computeTotalScore(ArrayList<Bird> birds)
	{
		int score = 0;
		for(Bird b : birds)
		{
			score+= b.getScore();
		}
		return score;
	}
	
	public void sort(ArrayList<Bird> sortableList)
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
	
	public void mutate(ArrayList<Bird> newBirds)
	{
		for(int i =0; i< newBirds.size();i++)
		{
			for(int j = 0; j< newBirds.get(i).getNeuralNetwork().getWeights().size(); j++)
			{
				if(r.nextDouble()>0.99)
				{
					newBirds.get(i).getNeuralNetwork().getWeights().get(j).setWeight(r.nextDouble()*2 -1);
				}
			}
		}
	}
	
	public Bird breed(Bird p1, Bird p2)
	{
		
			
			Bird child = new Bird(height);
			
			ArrayList<Weight> weightP1 = p1.getNeuralNetwork().getWeights();
			int max = weightP1.size();
			int halfMax = (int)(r.nextDouble()*max);
			for(int j = 0; j < halfMax; j++)
			{
				String id1 = weightP1.get(j).getBackPerceptron().getID();
				String id2 = weightP1.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <max;d++ )
				{
					ArrayList<Weight> newBirdsWeights = child.getNeuralNetwork().getWeights();
					if(newBirdsWeights.get(d).getBackPerceptron().getID().equals(id1) && newBirdsWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newBirdsWeights.get(d).setWeight(weightP1.get(j).getWeight());
					}
				}
			}
			
			ArrayList<Weight> weightP2 = p2.getNeuralNetwork().getWeights();
			for(int j = halfMax; j < max; j++)
			{
				String id1 = weightP2.get(j).getBackPerceptron().getID();
				String id2 = weightP2.get(j).getCurrentPerceptron().getID();
				for(int d = 0; d <max;d++ )
				{
					ArrayList<Weight> newBirdsWeights = child.getNeuralNetwork().getWeights();
					if(newBirdsWeights.get(d).getBackPerceptron().getID().equals(id1) && newBirdsWeights.get(d).getCurrentPerceptron().getID().equals(id2) )
					{
						newBirdsWeights.get(d).setWeight(weightP2.get(j).getWeight());
					}
				}
			}	
			
			return child;
		
		
		
	}
}
