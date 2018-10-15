package objects;

public class Joint {
//-90 and 20
	private int originalAngle1 = -90;
	private int originalAngle2 = -30;
	
	private int targetAngel;
	private double speed = 5;
	private int angle;
	
	public Joint(int angle)
	{
		this.angle = angle;
	}

	public void setAngle(int angle)
	{
		this.angle  =  angle;
	}
	public void setTargetAngle(int target)
	{
		this.targetAngel = target;
	}
	public int getTargetAngle() 
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

	public int getAngle() {
		return angle;
	}
	public int getOriginalAngle1()
	{
		return originalAngle1;
	}
	public int getOriginalAngle2()
	{
		return originalAngle2;
	}
	
}
