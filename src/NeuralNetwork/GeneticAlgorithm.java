package NeuralNetwork;
//HELLOOOO!!!
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import Maths.Angle;




public class GeneticAlgorithm {

	private final double KEEP_PERCENT_TOP = 0.3;
	private final double MUTATION_RATE = 0.03;
	
	
	private ArrayList<ArrayList<Angle>> bestPopulations = new ArrayList();
	private ArrayList<Angle> temporaryBest =  new ArrayList();

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
		
//		for(int i = 0 ; i< birds.size();i++)
//		{
//			System.out.println(birds.get(i).getScore()+" ");
//		}
//		
//		System.out.println("top score: "+birds.get(0).getScore()+" ");
		
		
		int totalScore = computeTotalScore(angles); //sum
    	ArrayList<Angle> newAngles = new ArrayList();
	
		ArrayList<Double> probabilities = new ArrayList<>();
		
		for(int i =0;i<angles.size();i++)
		{
			probabilities.add((angles.get(i).getScore()/totalScore));
		}
		
		
		for(int i =0;i<KEEP_PERCENT_TOP*angles.size();i++)
		{
			Angle ang = new Angle(0.0,0.0,1,3,20,4);
			ang.setNN(angles.get(i).getNN());
			newAngles.add(ang);
		}

		//to only change the worst performing bird, also keep percent is changed to 1
		

		
		
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
//			Bird ang = new Bird(height);
//			ang.setNeuralNetwork(a1.getNeuralNetwork());
//			newBirds.add(ang);
//			
//			Bird ang2 = new Bird(height);
//			ang2.setNeuralNetwork(a2.getNeuralNetwork());
//			newBirds.add(ang2);
//			
//		System.out.println(birds.get(0)+" "+a1+" "+a2);
			
			Angle child = breed(a1,a2);
			newAngles.add(child);
			
		}
		
		mutate(newAngles);
		temporaryBest = newAngles;
	
		return newAngles;
	}
	
	public ArrayList<Angle> generateNewPopulationFromBest(int populationSize)
	{
		
		ArrayList<Angle> breedingPool = new ArrayList();
		ArrayList<Angle> newPopulation = new ArrayList();
		ArrayList<Angle> angles  = new ArrayList();
		for(int i = 0; i < bestPopulations.size(); i++)
		{
			sort(bestPopulations.get(i));
			angles = bestPopulations.get(i);
			
			if(bestPopulations.get(i).size()>0)
			{		
				System.out.println(" score " + bestPopulations.get(i).get(0).getScore() );
//				Angle ang = new Angle(0.0,0.0,1,2,20,4);
//				ang.setNN(bestPopulations.get(i).get(0).getNN());
				newPopulation.add(bestPopulations.get(i).get(0));
			}
			for(int j =0;j<KEEP_PERCENT_TOP*angles.size();j++)
			{
				Angle ang = new Angle(0.0,0.0,1,2,20,4);
				ang.setNN(bestPopulations.get(i).get(j).getNN());
				breedingPool.add(ang);
			}
			for(int j =0;j<KEEP_PERCENT_TOP*angles.size()/2;j++)
			{
				Angle ang = new Angle(0.0,0.0,1,2,20,4);
				ang.setNN(bestPopulations.get(i).get(j).getNN());
				breedingPool.add(ang);
			}	
			for(int j =0;j<KEEP_PERCENT_TOP*angles.size()/4;j++)
			{
				Angle ang = new Angle(0.0,0.0,1,2,20,4);
				ang.setNN(bestPopulations.get(i).get(j).getNN());
				breedingPool.add(ang);
			}	
			
		}
		
		ArrayList<Double> probabilities = new ArrayList<>();
		
		for(int i =0;i<breedingPool.size();i++)
		{
			probabilities.add(1.0/breedingPool.size());
		}
		
		while(newPopulation.size()<populationSize)
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
					
					a1 = breedingPool.get(j);
					break;
				}
			}
			
			sum = 0;
			
			for(int j = 0 ; j < probabilities.size();j++)
			{
				sum+=probabilities.get(j);
				
				if(sum > p2)
				{
					
					a2 = breedingPool.get(j);
					break;
				}
			}
			
			Angle child = breed(a1,a2);
			newPopulation.add(child);			
		}
		
		temporaryBest = newPopulation;
		bestPopulations.clear();
		return newPopulation;
		
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
		for(int i = (int)(angles.size() * KEEP_PERCENT_TOP); i< angles.size();i++)
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
		
		
		Angle child = new Angle(0.0,0.0,1,3,20,4);
		
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
	
	public void storePopulation()
	{		
		bestPopulations.add(temporaryBest);
		
	
	}
}
