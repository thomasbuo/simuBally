package NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

public class Perceptron {
	
	private double bias;
	private double error;
	private String id;
	private int layer = 0;
	private int totalLayers = 0;
	
	private ArrayList<Perceptron> backConnections = new ArrayList();
	
	private double output = 0;
	
	private Random r = new Random();
	
	public Perceptron(String id, int layer, int totalLayers)
	{
		this.layer = layer;
		this.totalLayers = totalLayers;
		this.id = id;
		bias = r.nextDouble()*2 -1;
		//bias = 0;
	}
	
	public void computeOutput(double sum)
	{
		//System.out.println();
		//output = Math.signum(sum + bias);
		output = activationFunction(sum + bias);
		//.out.println("output"+ output+", "+(sum+bias));
	}
	
	public double activationFunction(double x) // x = sum of weight times input + bias
	{
		if(layer == 0)
		{
			return Math.max(0, x);
		}
		else if(layer == totalLayers)
		{
			//sigmoid
			return 1.0 / (1 + Math.exp(-x)); 
		}
		else
		{
			return Math.max(0, x);
		}
		
		
	}
	
	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getBias() {
		return bias;
		
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public ArrayList<Perceptron> getBackConnections() {
		return backConnections;
	}

	public void setBackConnections(ArrayList<Perceptron> backConnections) {
		this.backConnections = backConnections;
	}
	
	public double getError() {
		return error;
		
	}

	public void setError(double error) {
		this.error = error;
	}
	public String getID() {
		return id;
	}
	

}
