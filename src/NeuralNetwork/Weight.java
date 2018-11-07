package NeuralNetwork;

import java.util.Random;

public class Weight {
	
	private Perceptron backPerceptron;
	private Perceptron currentPerceptron;
	private double weight;
	
	private Random r = new Random();
	
	public Weight(Perceptron backPerceptron, Perceptron currentPerceptron)
	{
		this.backPerceptron = backPerceptron;
		this.currentPerceptron = currentPerceptron;
		weight = r.nextDouble()*2-1;		
	}

	public Perceptron getBackPerceptron() {
		return backPerceptron;
		
	}

	public void setBackPerceptron(Perceptron backPerceptron) {
		this.backPerceptron = backPerceptron;
	}

	public Perceptron getCurrentPerceptron() {
		return currentPerceptron;
	}

	public void setCurrentPerceptron(Perceptron currentPerceptron) {
		this.currentPerceptron = currentPerceptron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
