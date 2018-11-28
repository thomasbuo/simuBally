package Maths;
// HI ZSOLTTTTT!! 
import java.util.ArrayList;

import NeuralNetwork.GeneticAlgorithm;
import NeuralNetwork.NeuralNetwork;

public class NeuralCore {

	private double error = 0;
	private NeuralNetwork nn;
	private GeneticAlgorithm gn = new GeneticAlgorithm() ;
	private ArrayList<Angle> population = new ArrayList();
	public NeuralCore(int popSize)
	{
		nn = new NeuralNetwork(1, 2, 20, 3);
		for(int i = 0; i < popSize; i++)
		{
			population.add(new Angle(0.0,0.0, 1, 2, 20, 4));
		}
	}
	
	public void setError(double error)
	{
		this.error = error;
	}
	
	public ArrayList<Double> getAngles(double target)
	{
		ArrayList<Double> input = new ArrayList();
		input.add(target);
		ArrayList<Double> angles = nn.guess(input);
		System.out.println("HI: " +  angles);
		return angles;
		
		
	}
	public ArrayList<Angle>  train(ArrayList<Angle> angles)
	{
		population = gn.generateNewPopulation(angles);
		return population;
	}
	public void storePopulation()
	{
		gn.storePopulation();
	}
	public ArrayList<Angle> trainOnPreviousBest(ArrayList<Angle> angles)
	{
		population = gn.generateNewPopulationFromBest(angles.size());
		return population;
	}
	public ArrayList<Angle> getPopulation()
	{
		return population;
	}
	public NeuralNetwork getNeuralNetwork()
	{
		return nn;
	}
	public ArrayList<Double> guess(ArrayList<Double> target)
	{
		ArrayList<Double> input = new ArrayList();
		for(int i = 0; i< target.size();i++)
		{
			input.add(target.get(0));
		}		
		return nn.guess(input);
	}
	public void setPopulation(ArrayList<Angle> population)
	{
		this.population = population;
	}
	
}
