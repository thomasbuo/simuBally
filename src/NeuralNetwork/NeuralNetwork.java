package NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

	private ArrayList<ArrayList<Perceptron>> layers = new ArrayList<ArrayList<Perceptron>>();
	private ArrayList<Weight> weights = new ArrayList<Weight>();
	private int correctPercent = 0;
	private int attempts = 0;

	private int inputNodes;
	private int outputNodes;
	private int hiddenNodes;
	private int hiddenLayers;

	private static Random r = new Random();
	private final double LEARNING_RATE = 0.5;
	
	public NeuralNetwork(int inputNodes, int outputNodes, int hiddenNodes, int hiddenLayers) {

		this.inputNodes = inputNodes;
		this.outputNodes = outputNodes;
		this.hiddenNodes = hiddenNodes;
		this.hiddenLayers = hiddenLayers;

		for (int i = 0; i < hiddenLayers + 2; i++) {
			ArrayList<Perceptron> layer = new ArrayList<Perceptron>();

			String id = "";
			// input
			if (layers.size() == 0) {

				for (int j = 0; j < inputNodes; j++) {
					id += i;
					id += j;
					layer.add(new Perceptron(id, i, hiddenLayers + 1));
					layer.get(layer.size()-1).setBias(0.0);
				}
				layer.add(new Perceptron("bias", i, hiddenLayers + 1));
				layer.get(layer.size()-1).setOutput(1.0);
			}
			// output
			else if (i == hiddenLayers + 1) {

				for (int j = 0; j < outputNodes; j++) {
					id += i;
					id += j;
					layer.add(new Perceptron(id, i, hiddenLayers + 1));
				}
			}
			// hidden layers
			else {
				for (int j = 0; j < hiddenNodes; j++) {
					id += i;
					id += j;
					layer.add(new Perceptron(id,  i, hiddenLayers + 1));
				}
				layer.add(new Perceptron("bias", i, hiddenLayers + 1));
				layer.get(layer.size()-1).setOutput(1.0);

			}

			layers.add(layer);
		}

		for (int i = 1; i < layers.size(); i++) {
			for (int j = 0; j < layers.get(i).size(); j++) {
				
					layers.get(i).get(j).setBackConnections(layers.get(i - 1));
							
			}
		}

		for (int i = 1; i < layers.size(); i++) {
			for (int j = 0; j < layers.get(i).size(); j++) {
				for (int k = 0; k < layers.get(i).get(j).getBackConnections().size(); k++) {
					
					weights.add(new Weight(layers.get(i).get(j).getBackConnections().get(k), layers.get(i).get(j)));
									
				}
			}
		}

	}

	public ArrayList<Double> guess(List<Double> input) {

		if (input.size()+1 != layers.get(0).size()) {
			return null;
		}

		for (int i = 0; i < layers.size(); i++) {
			if (i == 0) {

				for (int j = 0; j < layers.get(i).size()-1; j++) {
					layers.get(i).get(j).computeOutput(input.get(j));
				}

				continue;
			}

			for (int j = 0; j < layers.get(i).size()-(i < this.layers.size()-1 ? 1 : 0); j++) {
				double sum = 0;
				for (int k = 0; k < layers.get(i).get(j).getBackConnections().size(); k++) {
					double w = 0;
					for (int p = 0; p < weights.size(); p++) {
						if (weights.get(p).getBackPerceptron() == layers.get(i).get(j).getBackConnections().get(k)
								&& weights.get(p).getCurrentPerceptron() == layers.get(i).get(j)) {
							w = weights.get(p).getWeight();
							break;
						}
					}

					sum += w * layers.get(i).get(j).getBackConnections().get(k).getOutput();
				}
				layers.get(i).get(j).computeOutput(sum);

			}
		}
		ArrayList<Double> guesses = new ArrayList<Double>();
		for (int i = 0; i < layers.get(layers.size() - 1).size(); i++) {

			guesses.add(layers.get(layers.size() - 1).get(i).getOutput());
			
		}
		//System.out.print(guesses);
		return guesses;
	}

	public void train(List<Double> input, List<Double> target) {
		ArrayList<Double> guess = guess(input);

		attempts++;

		backprop(input, target);
	}
	
	public void backprop(List<Double> input, List<Double> target) {
		ArrayList<Double> actual = guess(input);

		ArrayList<Perceptron> lastLayer = this.layers.get(this.layers.size()-1);
		//set error for output layer
		for (int l=0; l<lastLayer.size(); l++) {
			double error = actual.get(l)-target.get(l);
			Perceptron perc = lastLayer.get(l);
			perc.setError(error * perc.activationFunctionDerivative(perc.getOutput()));
		}
		//set error for hidden layers
		for (int l=this.layers.size()-2; l>0; l--) {
			ArrayList<Perceptron> layer = this.layers.get(l);
			for (int i=0; i<layer.size(); i++) {
				Perceptron perc = layer.get(i);
				double error = 0.0;
				for (int j=0; j<this.layers.get(l+1).size(); j++) {
					Perceptron outPerc = layers.get(l+1).get(j);

					// Find the weight between i and j
					Weight weight = weights.stream().filter(w -> w.getBackPerceptron()==perc
															&& w.getCurrentPerceptron()==outPerc).findFirst().get();

					error += weight.getWeight()*outPerc.getError();
				}

				error *= perc.activationFunctionDerivative(perc.getOutput());
				perc.setError(error);
			}
		}

		for (Weight weight : weights) {
			double delta = -LEARNING_RATE * weight.getCurrentPerceptron().getError() * weight.getBackPerceptron().getOutput();
			weight.setWeight(weight.getWeight()+delta);
		}
	}
	
//  XOR test
//	public static void main(String[] args) {
//		NeuralNetwork x = new NeuralNetwork(2, 2, 3, 1);
//
//		for (int i=0; i<100000; i++) {
//////			x.backprop(Arrays.asList(0.0, 0.0), Arrays.asList(0.0));
//////			x.backprop(Arrays.asList(1.0, 0.0), Arrays.asList(1.0));
//////			x.backprop(Arrays.asList(0.0, 1.0), Arrays.asList(1.0));
//////			x.backprop(Arrays.asList(1.0, 1.0), Arrays.asList(0.0));
////			
//			ArrayList<Double> input = new ArrayList();
//			ArrayList<Double> label = new ArrayList();
//			double k = r.nextDouble();
//			double l = r.nextDouble();
//			
//			input.add(k);
//			input.add(l);
//			if(k<l)
//			{
//				label.add(1.0);
//				label.add(0.0);
//			}
//			else 
//			{
//				label.add(0.0);
//				label.add(1.0);
//			}
//			x.backprop(input, label);
//			System.out.println(x.guess(input)+" "+label+" k: "+k +" l: "+l);
//			
//		}
////
//////		System.out.println(x.guess(Arrays.asList(0.0, 0.0)));
//////		System.out.println(x.guess(Arrays.asList(1.0, 0.0)));
//////		System.out.println(x.guess(Arrays.asList(0.0, 1.0)));
//////		System.out.println(x.guess(Arrays.asList(1.0, 1.0)));
//	}

	public ArrayList<ArrayList<Perceptron>> getLayers() {
		return layers;
	}

	public void setLayers(ArrayList<ArrayList<Perceptron>> layers) {
		this.layers = layers;
	}

	public ArrayList<Weight> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Weight> weights) {
		this.weights = weights;
	}

	public int getInputNodes() {
		return inputNodes;
	}

	public int getOutputNodes() {
		return outputNodes;
	}

	public int getHiddenNodes() {
		return hiddenNodes;
	}

	public int getHiddenLayers() {
		return hiddenLayers;
	}

}
