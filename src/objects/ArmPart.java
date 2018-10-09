package objects;

import java.util.ArrayList;

public class ArmPart {

private int lenght;
private int width = 10;
private ArrayList<Joint> joint;
private int posX1;
private int posY1;
private int posX2;
private int posY2;

public ArmPart(ArrayList<Joint> joint, int length, int posX, int posY)
{
	posX1 = posX;
	posY1 = posY;
	posX2 = posX1;
	posY2 = posY-length;
	System.out.println(posY2);
	this.joint = joint;
	this.lenght = length;
}
public int getWidth()
{
	return width;
}
public int getLenght() {
	return lenght;
}

public void setLenght(int lenght) {
	this.lenght = lenght;
}

public ArrayList<Joint> getJoint() {
	return joint;
}

public void setJoint(ArrayList<Joint> joint) {
	this.joint = joint;
}

public int getPosX1() {
	return posX1;
}

public void setPosX1(int posX1) {
	this.posX1 = posX1;
}

public int getPosY1() {
	return posY1;
}

public void setPosY1(int posY1) {
	this.posY1 = posY1;
}

public int getPosX2() {
	return posX2;
}

public void setPosX2(int armNumber) {
	if(joint!=null)
	{
		if(armNumber == 2) {
			this.posX2 = (int) (getPosX1() + (getLenght() * Math.sin(joint.get(0).getAngle()*Math.PI/180)));
			System.out.println("ang " + joint.get(0).getAngle() + " X1 " + getPosX1() + " X2 " + getPosX2());
		}
		else if(armNumber == 3)
		{
			this.posX2 = (int) (getPosX1() + (getLenght() * Math.sin((joint.get(0).getAngle()+joint.get(1).getAngle())*Math.PI/180)));
		}
	}
	
}

public int getPosY2() {
	return posY2;
}

public void setPosY2(int armNumber) {
	if(joint!=null)
	{
		System.out.println(getLenght()+" loloolo");
		if(armNumber == 2)
		{
			this.posY2 = (int) (getPosY1() - (getLenght() * Math.cos(joint.get(0).getAngle()*Math.PI/180)));
		
			System.out.println("ang " + joint.get(0).getAngle() + " Y1 " + getPosY1() + " Y2 " + getPosY2());
		}
		else if(armNumber == 3)
		{
			this.posY2 = (int) (getPosY1() - (getLenght() * Math.cos((joint.get(0).getAngle()+joint.get(1).getAngle())*Math.PI/180)));
		}
	}
}

	
}
