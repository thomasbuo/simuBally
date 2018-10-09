package core;

import java.util.ArrayList;

import objects.ArmPart;
import objects.Ball;
import objects.Joint;

public class Core {
	
	public static void main(String[] args)
	{
		System.out.println("start");
		ArrayList<Joint> joints = new ArrayList();
		joints.add(new Joint(-90));
		joints.add(new Joint(-20));
		ArmPart part1 = new ArmPart(joints,40,100,481);
		System.out.println("part1 "+ part1.getPosX2()+" "+part1.getPosY2());
		ArmPart part2 = new ArmPart(joints,40,part1.getPosX2(),part1.getPosY2());
		part2.setPosX2(2);
		part2.setPosY2(2);
		System.out.println("part2 "+ part2.getPosX1()+" "+part2.getPosY1());
		System.out.println("part2 "+ part2.getPosX2()+" "+part2.getPosY2());
		ArmPart part3 = new ArmPart(joints,40,part2.getPosX2(),part2.getPosY2());
		part3.setPosX2(3);
		part3.setPosY2(3);
		System.out.println("part3 "+ part3.getPosX1()+" "+part3.getPosY1());
		Ball ball = new Ball(5, 10,part3.getPosX2(),part3.getPosY2()); //size in pixels weight in grams
		Panel panel = new Panel(joints.get(0),joints.get(1),part1,part2,part3,ball);
		Simulation simulation = new Simulation(joints,part1,part2,part3,ball);
		Drawing mainFrame = new Drawing(panel);
		mainFrame.show();
		
	}

}
