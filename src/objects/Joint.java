package objects;

public class Joint {
//-90 and 20
	private double originalAngle1 = -90;
	private double originalAngle2 = -30;
	
	private double targetAngel;
	private double speed = 5;
	private double angle;
	
	public Joint(int angle)
	{
		this.angle = angle;
	}

	public void setAngle(double angle)
	{
		this.angle  =  angle;
	}
	public void setTargetAngle(double target)
	{
		this.targetAngel = target;
	}
	public double getTargetAngle() 
	{
		return targetAngel;
	}
	public void updateSpeed()
	{
		// update;
	}
	public double getSpeed() {
		return speed;
	}

	public double getAngle() {
		return angle;
	}
	public double getOriginalAngle1()
	{
		return originalAngle1;
	}
	public double getOriginalAngle2()
	{
		return originalAngle2;
	}
	
}
