package Maths;

import java.util.ArrayList;

import NeuralNetwork.GeneticAlgorithm;
import NeuralNetwork.NeuralNetwork;

public class NeuralCore {

	private double error = 0;
	private NeuralNetwork nn;
	private GeneticAlgorithm gn = new GeneticAlgorithm() ;
	private ArrayList<Angle> population = new ArrayList();
	public NeuralCore()
	{
		nn = new NeuralNetwork(1,2,6,2);
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
		System.out.println(angles);
		return angles;
		
		
	}
	public ArrayList<Angle>  Train(ArrayList<Angle> angles)
	{
		population = gn.generateNewPopulation(angles);
		return population;
	}
	
}
