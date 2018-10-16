package Maths;

public class Angle {

	private double angle1;
	private double angle2;
	private double error = 0;
	
	public Angle(int angle1, int angle2)
	{
		this.angle1 = angle1;
		this.angle2 = angle2;
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
