package main;

import pawn.*;

public class Main {

	public static void main(String[] args){ 
//		Window fenetre = new Window();
		Grid test = new Grid(10);
		
		Captain cap = new Captain(2);
		System.out.println(cap);
		test.set(0,0,cap);
		test.showGrid();
	}
}