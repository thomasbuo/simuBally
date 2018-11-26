package core;

import java.util.ArrayList;

import objects.ArmPart;
import objects.Ball;
import objects.Floor;
import objects.Joint;
import objects.Target;

public class Core {
	
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		int i = 0;
		Floor floor = new Floor(430);
		
		ArrayList<Joint> joints = new ArrayList();
		
		joints.add(new Joint(-90));
		joints.add(new Joint(-20));
		
		ArmPart part1 = new ArmPart(joints,40,100,430);
		
		if(i == 0)
		{
			i=1;
		}
		
		ArmPart part2 = new ArmPart(joints,40,part1.getPosX2(),part1.getPosY2());
		
		part2.setPosX2(2);
		part2.setPosY2(2);
	
		while(i == 1)
		{
			i = 2;
		}
		
		if(i != 0)
		{
			i = (int) 0.00;
		}
		
		ArmPart part3 = new ArmPart(joints,40,part2.getPosX2(),part2.getPosY2());
		//part3.setPosX1(3);
		//part3.setPosY1(3);
		part3.setPosX2(3);
		part3.setPosY2(3);
				
		Ball ball = new Ball(5, 10,part3.getPosX2(),part3.getPosY2()); //size in pixels weight in grams
		
		Target target = new Target();
		
		Panel panel = new Panel(joints.get(0),joints.get(1),part1,part2,part3,ball,floor, target);
		panel.setBounds(10, 11, 834, 453);
		Simulation simulation = new Simulation(joints,part1,part2,part3,ball, floor, target);
		
		Drawing mainFrame = new Drawing(panel, simulation);
		simulation.setDrawing(mainFrame);
		
		mainFrame.show();
		
		if(i != 0)
		{
			i = (int) 0.00;
		}
		
		
	}

}
