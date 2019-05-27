import java.awt.Container;
import javax.swing.JFrame;
 
/*
 * Haoming Zhang(Peter)
 * @date 2018/04/29
 */
public class Game {
	public static void main(String[] args) {
		//create JFrame
		JFrame w = new JFrame();
		// create Gamepanel,initialize the (20*30)windows
		GamePanel mainPanel = new GamePanel(20, 30);
		int[] a = mainPanel.returnSize();
		// the size of JFame
		w.setSize(a[0], a[1]);
		//tittle
		w.setTitle("minesweeper");
		//exit
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = w.getContentPane();
		c.add(mainPanel);
 
		w.setVisible(true);
	}
}
