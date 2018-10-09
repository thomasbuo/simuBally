package objects;

public class Joint {
//-90 and 20
private double speed;
private int angle;
	
	public Joint(int angle)
	{
		this.angle = angle;
	}

	public void setAngle(int angle)
	{
		this.angle  =  angle;
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
	
}
