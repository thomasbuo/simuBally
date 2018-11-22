package Maths;
// HI THOMAS!! 
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
			population.add(new Angle(0.0,0.0, 1, 2, 20, 5));
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
	public void trainNeuralNetwork(double error)
	{		
		nn.seperateTrain(error);
	}
	public ArrayList<Angle> getPopulation()
	{
		return population;
	}
	public NeuralNetwork getNeuralNetwork()
	{
		return nn;
	}
	public ArrayList<Double> guess(double target)
	{
		ArrayList<Double> input = new ArrayList();
		input.add(target);
		return nn.guess(input);
	}
	
}
