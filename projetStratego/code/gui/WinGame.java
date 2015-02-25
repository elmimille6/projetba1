package gui;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import main.Grid;
import main.GridIA;
import pawn.APawn;
import pawn.Marshal;

/**
 * 
 * @author giuliano
 * 
 */
public class WinGame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public PaneGame pane;
	public int posX, posY;
	public Grid grid;
	public APawn focus;
	public int[] arrow={-1,-1,-1,-1,-1,-1};
	public final int[] arrowN={-1,-1,-1,-1,-1,-1};
	public boolean att=false;

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public WinGame(Grid ngrid) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.grid = ngrid;
		
		pane = new PaneGame(grid);
		pane.setLayout(new BorderLayout());
		this.setSize(700, 700);
		this.setResizable(true);
		this.setContentPane(pane);
		this.setVisible(true);
		pane.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(posX, posY);
					int line = res[0];
					int row = res[1];
					System.out.println(line+"   "+row);
					grid.showGrid();
					APawn pawn = grid.get(line, row);
					if (focus != null) {
						System.out.println("1#");
						if (focus.movePoss(grid, line, row)) {
							System.out.println("2#");
							grid = focus.move(grid, line, row);
							att = true;
						}
					}
					if (pawn != null && !att) {
						focus=pawn;
						arrow = pawn.focus(grid);
						pane.recupArrow(arrow);
						repaint();
					} else {
						pane.recupArrow(arrowN);
						focus = null;
						repaint();
					}
				}
				att = false;
				repaint();
			}
		});
	}

	/**
	 * Transforms the coordinates of the cursor into coordinates of the grid.
	 * 
	 * @param posX
	 *    The abscissa of the cursor.
	 *    
	 * @param posY
	 *    The ordinate of the cursor.
	 *    
	 * @return
	 *    An array with the abscissa and the ordinate in the grid.
	 */
	public int[] getRes(int posX, int posY) {

		int[] res = { 0, 0 };
		res[1]=(posX-(posX%(pane.getWidth()/(grid.getLine()+1))))/(pane.getWidth()/(grid.getLine()+1));
		res[0]=(posY-(posY%(pane.getHeight()/(grid.getRow()+1))))/(pane.getHeight()/(grid.getRow()+1));
		return res;
		
	}

	
}
