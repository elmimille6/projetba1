package main;

public class Dic implements java.io.Serializable {

	private static final long serialVersionUID = 6856689867397939721L;
	Object[][] tab;
	int size = 0;

	public Dic() {

	}

	public void add(Object object, int num) {
		Object[][] tab2 = new Object[size + 1][2];
		for (int i = 0; i < size; i++) {
			tab2[i][0] = tab[i][0];
			tab2[i][1] = tab[i][1];
		}
		tab2[size][0] = object;
		tab2[size][1] = num;
		tab = tab2;
		size++;
	}

	public int get(Object object) {
		for (int i = 0; i < size; i++) {
			if (tab[i][0].equals(object) && tab[i][1] instanceof Integer) {
				return (Integer) tab[i][1];
			}
		}
		return 0;
	}

	public int get(int i) {
		if (tab[i][1] instanceof Integer) {
			return (Integer) tab[i][1];
		}
		return 0;
	}

	public int getIndex(Object object) {
		for (int i = 0; i < size; i++) {
			if (tab[i][0].equals(object)) {
				return i;
			}
		}
		return -1;
	}

	public Object getObject(int i) {
		return tab[i][0];
	}

	public int getSize() {
		return size;
	}

	public void set(Object obj, int j) {
		tab[this.getIndex(obj)][1] = j;
	}

	public void ser(int i, int j) {
		tab[i][1] = j;
	}

	public boolean isIn(Object obj) {
		int a = this.getIndex(obj);
		if (a == -1) {
			return false;
		}
		return true;
	}

	public void increase(Object obj) {
		if (this.isIn(obj)) {
			if (tab[this.getIndex(obj)][1] instanceof Integer) {
				tab[this.getIndex(obj)][1] = (Integer) tab[this.getIndex(obj)][1] + 1;
			}
		} else {
			this.add(obj, 1);
		}
	}

	public Object[][] getTab() {
		return tab;
	}

	public String toString() {
		String line = "{";
		for (int i = 0; i < this.size; i++) {
			line += "[" + tab[i][0] + ":" + tab[i][1] + "]";
		}
		line += "}";
		return line;
	}
}
