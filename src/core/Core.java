package Core;

import NeuralNetwork.NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Core {

	private static Random r = new Random();
	public static void main(String[] args) throws IOException
	{
		NeuralNetwork nn = new NeuralNetwork(1, 3, 6 ,2);
		String path2 = "LookupTable.txt";
		ArrayList<Double> input = new ArrayList();
		ArrayList<ArrayList<Double>> label = new ArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(path2))) {

			String line = br.readLine();
			String[] parts = line.split(";");
			for (int i = 0; i < parts.length; i += 4) {
				label.add(new ArrayList<>());
				input.add(Double.parseDouble(parts[i]));
				double percent1 = 0.0;
				double percent2 = 0.0;
				double percent3 = 0.0;
				if (Double.parseDouble(parts[i + 1]) >= 0) {

					percent1 = (90 + Double.parseDouble(parts[i + 1])) / 180;

					if (Double.parseDouble(parts[i + 2]) >= 0) {
						percent2 = (Double.parseDouble(parts[i + 2]) + 90) / (Double.parseDouble(parts[i + 1]) + 90);
					} else {
						percent2 = (90 + Double.parseDouble(parts[i + 2])) / (Double.parseDouble(parts[i + 1]) + 90);
					}
				} else {

					percent1 = (90 + Double.parseDouble(parts[i + 1])) / 180;

					percent2 = (90 + Double.parseDouble(parts[i + 2])) / (90 + Double.parseDouble(parts[i + 1]));


				}
				if (Double.parseDouble(parts[i + 3]) >= 0) {

					percent3 = (45 - Double.parseDouble(parts[i + 3])) / 90;
				} else {
					percent3 = (45 - Double.parseDouble(parts[i + 3])) / 90;
				}
				System.out.println("percent1 " + percent1 + " percent2 " + percent2 + " percent3 " + percent3 + " angle1 " + parts[i + 1] + " angle2 " + parts[i + 2] + " angle3 " + parts[i + 3]);
				label.get(label.size() - 1).add(percent1);
				label.get(label.size() - 1).add(percent2);
				label.get(label.size() - 1).add(percent3);

			}

		}
		for(int i =0; i < 2000; i++)
		{
			for(int j = 0; j< input.size();j++)
			{
				nn.backprop(Arrays.asList(input.get(j)/90.0), label.get(j));
			}
			System.out.println("interation "+i);
		}


		for(int j = 0; j< input.size();j++)
		{
			System.out.println(nn.guess(Arrays.asList(input.get(j)/90.0))+" label "+label.get(j));
//			System.out.printf("%s:%s\n", input.get(j), label.get(j));
		}

		nn.saveToFile("network");
	}
}
