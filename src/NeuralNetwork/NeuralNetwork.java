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
//  XOR test
	public static void main(String[] args) {
		NeuralNetwork x = new NeuralNetwork(2, 2, 3, 1);

		for (int i=0; i<100000; i++) {
//			x.backprop(Arrays.asList(0.0, 0.0), Arrays.asList(0.0));
//			x.backprop(Arrays.asList(1.0, 0.0), Arrays.asList(1.0));
//			x.backprop(Arrays.asList(0.0, 1.0), Arrays.asList(1.0));
//			x.backprop(Arrays.asList(1.0, 1.0), Arrays.asList(0.0));
			
			ArrayList<Double> input = new ArrayList();
			ArrayList<Double> label = new ArrayList();
			double k = r.nextDouble();
			double l = r.nextDouble();
			
			input.add(k);
			input.add(l);
			if(k<l)
			{
				label.add(1.0);
				label.add(0.0);
			}
			else 
			{
				label.add(0.0);
				label.add(1.0);
			}
			System.out.println(x.guess(input)+" "+label+" k: "+k +" l: "+l);
			
		}

//		System.out.println(x.guess(Arrays.asList(0.0, 0.0)));
//		System.out.println(x.guess(Arrays.asList(1.0, 0.0)));
//		System.out.println(x.guess(Arrays.asList(0.0, 1.0)));
//		System.out.println(x.guess(Arrays.asList(1.0, 1.0)));
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
//				if (i == hiddenNodes) {
//					for (int j = 0; j < hiddenNodes; j++) {
//						id += i;
//						id += j;
//						layer.add(new Perceptron(id, hiddenLayers + 1, hiddenLayers + 1));
//					}
//				} else {
//					for (int j = 0; j < hiddenNodes; j++) {
//						id += i;
//						id += j;
//						layer.add(new Perceptron(id, 0, hiddenLayers + 1));
//					}
//				}
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

		return guesses;
	}

	public void train(List<Double> input, List<Double> target) {
		ArrayList<Double> guess = guess(input);

		attempts++;

		if(true)
		{
			backprop(input, target);
			return;
		}
		// compute error
		for (int i = 0; i < guess.size(); i++) {
			double e = target.get(i) - guess.get(i);
			layers.get(layers.size() - 1).get(i).setError(e);
		}

		for (int i = layers.size() - 2; i >= 0; i--) {
			for (int j = 0; j < layers.get(i).size(); j++) {
				double e = 0;

				for (int k = 0; k < layers.get(i + 1).size(); k++) {

					double tempE = layers.get(i + 1).get(k).getError();

					double weightSum = 0;
					double currentWeight = 0;
					for (int t = 0; t < layers.get(i + 1).get(k).getBackConnections().size(); t++) {
						double w = 0;
						for (int p = 0; p < weights.size(); p++) {

							if (weights.get(p).getBackPerceptron() == layers.get(i + 1).get(k).getBackConnections()
									.get(t) && weights.get(p).getCurrentPerceptron() == layers.get(i + 1).get(k)
									&& layers.get(i + 1).get(k).getBackConnections().get(t) == layers.get(i).get(j)) {

								currentWeight = weights.get(p).getWeight();

							}

							if (weights.get(p).getBackPerceptron() == layers.get(i + 1).get(k).getBackConnections()
									.get(t) && weights.get(p).getCurrentPerceptron() == layers.get(i + 1).get(k)) {
								w = weights.get(p).getWeight();
							}
						}
						weightSum += w;
					}

					e += (currentWeight / weightSum) * tempE;

				}

				layers.get(i).get(j).setError(e);
			}
		}

		if (Math.round(guess.get(0)) == Math.round(target.get(0)))
			correctPercent++;

		System.out.println("guess: " + Math.round(guess.get(0)) + " target: " + target + " " + (double) correctPercent / attempts + " error "
				+ (target.get(0) - guess.get(0)));
		//backrpopagation weights
		for (int i = 0; i < weights.size(); i++) {
			weights.get(i).setWeight(weights.get(i).getWeight() + weights.get(i).getCurrentPerceptron().getError()
					* LEARNING_RATE * weights.get(i).getBackPerceptron().getOutput());
		}
//		//backrpopagation bias
//		for (int i = 0; i < layers.size(); i++) {
//			for (int j = 0; j < layers.get(i).size(); j++) {
//				double delta = LEARNING_RATE * layers.get(i).get(j).getError()
//						* (layers.get(i).get(j).getOutput() * (1 - layers.get(i).get(j).getOutput()));
//
//				layers.get(i).get(j).setBias(layers.get(i).get(j).getBias() + delta);
//			}
//		}
	}

	

	public void seperateTrain(double e) {
		attempts++;		

		ArrayList<Perceptron> lastLayer = this.layers.get(this.layers.size()-1);
		//set error for output layer
		for (int l=0; l<lastLayer.size(); l++) {
			Perceptron perc = lastLayer.get(l);
			perc.setError(e * perc.activationFunctionDerivative(perc.getOutput()));
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
			double delta = -LEARNING_RATE/1.00 * weight.getCurrentPerceptron().getError()/1.00 * weight.getBackPerceptron().getOutput()/1.00;
			System.out.print (delta +" ");
			weight.setWeight(weight.getWeight()+delta);
		}	
		System.out.println();
	}

	public void train2(ArrayList<Double> input, ArrayList<Double> label) {
		// System.out.println(input.size()+" " +layers.get(0).size()+" " +label.size());
		attempts += 2;
		if (input.size() != layers.get(0).size() || label.size() != layers.get(layers.size() - 1).size()) {
			System.out.println("fails");
			return;
		}

		ArrayList<Double> output = guess(input);

		for (int i = 0; i < output.size(); i++) {
			if (Math.round(output.get(i)) == Math.round(label.get(i))) {

				correctPercent++;
			}
			System.out.print("  guess: " + output.get(i) + ", label: " + label.get(i) + ", "
					+ (double) correctPercent / attempts);

		}
		System.out.println();

		// compute error
		for (int i = 0; i < output.size(); i++) {
			double e = label.get(i) - output.get(i);
			layers.get(layers.size() - 1).get(i).setError(e);
		}

		for (int i = layers.size() - 2; i >= 0; i--) {
			for (int j = 0; j < layers.get(i).size(); j++) {

				double e = 0;

				for (int k = 0; k < layers.get(i + 1).size(); k++) {
					// System.out.println("neuron: "+ k);

					double tempE = layers.get(i + 1).get(k).getError();

					double weightSum = 0;
					double currentWeight = 0;
					for (int t = 0; t < layers.get(i + 1).get(k).getBackConnections().size(); t++) {
						double w = 0;
						for (int p = 0; p < weights.size(); p++) {

							if (weights.get(p).getBackPerceptron() == layers.get(i + 1).get(k).getBackConnections()
									.get(t) && weights.get(p).getCurrentPerceptron() == layers.get(i + 1).get(k)
									&& layers.get(i + 1).get(k).getBackConnections().get(t) == layers.get(i).get(j)) {

								currentWeight = weights.get(p).getWeight();

							}

							if (weights.get(p).getBackPerceptron() == layers.get(i + 1).get(k).getBackConnections()
									.get(t) && weights.get(p).getCurrentPerceptron() == layers.get(i + 1).get(k)) {
								w = weights.get(p).getWeight();
							}
						}
						weightSum += w;
					}

					e += (currentWeight / weightSum) * tempE;

				}

				layers.get(i).get(j).setError(e);
			}
		}

		// backpropogation
		for (int i = 0; i < weights.size(); i++) {
			double delta = LEARNING_RATE * weights.get(i).getCurrentPerceptron().getError()
					* (weights.get(i).getCurrentPerceptron().getOutput()
							* (1 - weights.get(i).getCurrentPerceptron().getOutput())
							* weights.get(i).getBackPerceptron().getOutput());

		}
		for (int i = 0; i < layers.size(); i++) {
			for (int j = 0; j < layers.get(i).size(); j++) {
				double delta = LEARNING_RATE * layers.get(i).get(j).getError()
						* (layers.get(i).get(j).getOutput() * (1 - layers.get(i).get(j).getOutput()));

				layers.get(i).get(j).setBias(layers.get(i).get(j).getBias() + delta);
			}
		}

	}

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
