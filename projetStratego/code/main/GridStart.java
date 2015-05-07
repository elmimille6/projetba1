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

	public GridStart() {
		this.grid=new APawn[4][10];
		this.name="blank";
	}

	public void setGrid(APawn[][] ngrid) {
		this.grid = ngrid;
	}

	public APawn[][] getGrid() {
		return grid;
	}

	public void setName(String str) {
		this.name = str;
	}

	public String getName() {
		return name;
	}

	public void changeTeam(int team) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				APawn pawn = grid[i][j];
				pawn.setTeam(team);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void save() {
		ObjectInputStream in;
		Vector<GridStart> vector = new Vector<GridStart>();
		try {
			in = new ObjectInputStream(new FileInputStream("gridStart.save"));
			vector = (Vector<GridStart>) in.readObject();
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
			out = new ObjectOutputStream(new FileOutputStream("gridStart.save"));
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

	@SuppressWarnings("unchecked")
	public void delete() {
		ObjectInputStream in;
		Vector<GridStart> vector = new Vector<GridStart>();
		try {
			in = new ObjectInputStream(new FileInputStream("gridStart.save"));
			vector = (Vector<GridStart>) in.readObject();
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
		int ind = -1;
		// System.out.println(vector.size());
		// System.out.println(this.name.equals(vector.elementAt(3).getName()));
		for (int i = 0; i < vector.size(); i++) {
			System.out.println(vector.elementAt(i));
			if (this.equals(vector.elementAt(i))) {
				ind = i;
				// System.out.println("get index " + i);
			}
		}

		vector.removeElementAt(ind);
		try {
			out = new ObjectOutputStream(new FileOutputStream("gridStart.save"));
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
