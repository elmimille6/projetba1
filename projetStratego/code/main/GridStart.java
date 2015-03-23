package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import pawn.APawn;

public class GridStart implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344347610962103007L;
	public APawn[][] grid;
	public String name;
	
	public GridStart(){
		
	}
	public void setGrid(APawn[][] ngrid){
		this.grid=ngrid;
	}
	public APawn[][] getGrid(){
		return grid;
	}
	public void setName(String str){
		this.name=str;
	}
	public String getName(){
		return name;
	}
	public void save(){
		ObjectInputStream in;
		Vector<GridStart> vector=new Vector<GridStart>();
		try {
			in = new ObjectInputStream(new FileInputStream("gridStart.save"));
			vector = (Vector) in.readObject();
			in.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectOutputStream out;
		vector.add(this);
		try {
			out = new ObjectOutputStream(
					new FileOutputStream("gridStart.save"));
			out.writeObject(vector);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		return name;
	}
}
