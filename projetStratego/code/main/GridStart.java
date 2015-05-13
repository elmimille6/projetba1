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

	private static final long serialVersionUID = -4344347610962103007L;
	public APawn[][] grid;
	public String name;

	/**
	 * 
	 */
	public GridStart() {
		this.grid = new APawn[4][10];
		this.name = "blank";
	}

	/**
	 * 
	 * @param ngrid
	 */
	public void setGrid(APawn[][] ngrid) {
		this.grid = ngrid;
	}

	/**
	 * 
	 * @return
	 */
	public APawn[][] getGrid() {
		return grid;
	}

	/**
	 * 
	 * @param str
	 */
	public void setName(String str) {
		this.name = str;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param team
	 */
	public void changeTeam(int team) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				APawn pawn = grid[i][j];
				pawn.setTeam(team);
			}
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void save() {
		ObjectInputStream in;
		Vector<GridStart> vector = new Vector<GridStart>();
		try {
			in = new ObjectInputStream(new FileInputStream("GridStart.save"));
			vector = (Vector<GridStart>) in.readObject();
			in.close();

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		ObjectOutputStream out;
		vector.add(this);
		try {
			out = new ObjectOutputStream(new FileOutputStream("GridStart.save"));
			out.writeObject(vector);
			out.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void delete() {
		ObjectInputStream in;
		Vector<GridStart> vector = new Vector<GridStart>();
		try {
			in = new ObjectInputStream(new FileInputStream("GridStart.save"));
			vector = (Vector<GridStart>) in.readObject();
			in.close();

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		ObjectOutputStream out;
		int ind = -1;
		for (int i = 0; i < vector.size(); i++) {
			System.out.println(vector.elementAt(i));
			if (this.equals(vector.elementAt(i))) {
				ind = i;
			}
		}

		vector.removeElementAt(ind);
		try {
			out = new ObjectOutputStream(new FileOutputStream("GridStart.save"));
			out.writeObject(vector);
			out.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	/**
	 * 
	 */
	public String toString() {
		return name;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj) {
		if (obj instanceof GridStart) {
			GridStart gri = (GridStart) obj;
			if (this.name.equals(gri.getName())) {
				for (int i = 0; i < this.grid.length; i++) {
					for (int j = 0; j < this.grid[0].length; j++) {
						if (this.grid[i][j] == null
								|| gri.getGrid()[i][j] == null) {
							return !(this.grid[i][j] == null && gri.getGrid()[i][j] == null);
						} else if (!this.grid[i][j].equals(gri.getGrid()[i][j])) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
}
