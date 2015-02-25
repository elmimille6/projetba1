package pawn;

import main.Grid;

/**
 * Scout is the class that creates a "scout" pawn.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Scout extends APawn {

	/**
	 * Main Constructor of the scout pawn.
	 */
	public Scout() {
		setLevelPawn(2);
		setNamePawn("scout");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the scout pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2.
	 */
	public Scout(int team) {
		setLevelPawn(2);
		setNamePawn("scout");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team==1)
			setURI("/image/red/scout.png");
		if (team==2)
			setURI("/image/blue/scout.png");
	}

	/**
	 * Test if a move is possible.
	 * 
	 * @param grid
	 *    The grid of the game.
	 *    
	 * @param x
	 *    The abscissa of the object.
	 *    
	 * @param y
	 *    The ordinate of the object.
	 *    
	 * @return
	 *    The grid after the moving.
	 */
	public boolean movePoss(Grid grid, int x, int y) {
		
		if(x>grid.getLine()){
//			System.out.println("1");
			return false;
		}
		if(y>grid.getRow()){
//			System.out.println("2");
			return false;
		}
		APawn target = grid.get(x, y);
		if (target instanceof Lake) { // test if the target isnt a lake
//			System.out.println("3");
			return false;
		} else if (target instanceof APawn) { // test if the target isnt a pawn of the same team
			if (target.getTeam() == this.team) {
//				System.out.println("4");
				return false;
			}
		} else if (x - this.posX != 0 && y - this.posY != 0) { // test if the move is in the same lane
//			System.out.println("5");
			return false;
		} else if (y - this.posY == 0 && x - this.posX == 0) { // test if the pawn isnt already on the target
//			System.out.println("6");
			return false;
		}  if (x - this.posX != 0) {//check if the lane X is empty
			if(x < this.posX){
				for(int i =1;posX-i!=x && posX-i>=0;i++){
					if(grid.get(posX-i, posY)!=null){
						System.out.println("1");
						return false;
					}
				}
			}
			if(x > this.posX){
				for(int i =1;posX+i!=x && posX+i<grid.getLine();i++){
					if(grid.get(posX+i, posY)!=null){
						System.out.println("2");
						return false;
					}
				}
			}
		}
		 if (y - this.posY != 0) {//check if the lane X is empty
			if(y < this.posY){
				for(int i =1;posY-i!=y && posY-i>=0;i++){
					if(grid.get(posX, posY-i)!=null){
						System.out.println("3");
						return false;
					}
				}
			}
			if(y > this.posY){
				for (int i = 1; posY + i != y && posY+i<grid.getRow(); i++) {
					if (grid.get(posX, posY + i) != null) {
						System.out.println("4");
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Focus a pawn and check if move is available for him, store the result into the array 'arrow'.
	 * 
	 * @param grid
	 *    The grid of the game.
	 */
	public int[] focus(Grid grid) { //TODO retourner le nombre de case qu il peut avancer (avec des for)
		int[] arrow={-1,-1,-1,-1,posX,posY};
//		System.out.println(focus);
		if (posX != grid.getLine()) {// check down move
			System.out.println("D");
			if (this.movePoss(grid, posX + 1, posY)) {
				for(int i =1;this.movePoss(grid, posX+i, posY);i++){
					arrow[2]=i;
				}
				
//				System.out.println("ok");
			} else {
				arrow[2] = -1;
			}
		} else {
			arrow[2] = -1;
			
		}
		if (posY != grid.getRow()) {// check right move
			System.out.println("R");
//			System.out.println(posX+" tu");
			if (this.movePoss(grid, posX, posY + 1)) {
				for(int i =1;this.movePoss(grid, posX, posY+i);i++){
					arrow[1]=i;
					
				}
			} else {
				arrow[1] = -1;
			}
		} else {
			arrow[1] = -1;
		}
		
		if (posX != 0) {// check up move
			System.out.println("U");
			if (this.movePoss(grid, posX - 1, posY)) {
				boolean test = true;
				for(int i =1;test;i++){
					if (posX-i>-1){
						if(this.movePoss(grid, posX-i, posY)){
							arrow[0]=i;
						}
					}
					else{
						test=false;
					}
				}
			} else {
				arrow[0] = -1;
			}
		} else {
			arrow[0] = -1;
		}
		if (posY != 0) {// check left move
			System.out.println("L");
			if (this.movePoss(grid, posX, posY - 1)) {
				boolean test = true;
				for(int i =1;test;i++){
					if (posY-i>-1){
						if(this.movePoss(grid, posX, posY-i)){
							arrow[3]=i;
						}
					}
					else{
						test=false;
					}
				}
			} else {
				arrow[3] = -1;
			}
		} else {
			arrow[3] = -1;
		}
		for (int i =0;i<arrow.length;i++){
				System.out.print(arrow[i]+"    ");
				
		}
		System.out.println();
		return arrow;
	}
}
