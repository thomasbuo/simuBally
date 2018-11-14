package Maths;

import NeuralNetwork.NeuralNetwork;

public class Angle {

	private double angle1;
	private double angle2;
	private double error = 0; //to be gone
	private double score = 0;
	private NeuralNetwork nn = null;
	
	public Angle(Double angle1, Double angle2)
	{
		this.angle1 = angle1;
		this.angle2 = angle2;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public void setScore(double score)
	{
		this.score = score;
	}
	
	public NeuralNetwork getNN() 
	{
		return nn;
	}
	
	public void setNN(NeuralNetwork nn)
	{
		this.nn = nn;
	}
	
	public double getError()
	{
		return error;
	}
	public void setError(double error)
	{
		this.error = error;
	}
	public double getAngle1()
	{
		return angle1;
	}
	public double getAngle2()
	{
		return angle2;
	}
}
