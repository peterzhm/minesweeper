import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
/*
 * Haoming Zhang(Peter)
 * @date 2018/04/29
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int rows;
	private int cols;
	//the number of bomb
	private int bombCount;
	private final int BLOCKWIDTH = 20;
	private final int BLOCKHEIGHT = 20;
	//information in every cube
	private JLabel[][] labels;
	private  MyButton[][] buttons;
	private final int[][] offset = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
 
	//initialize
	public GamePanel(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.bombCount = rows * cols / 10;
		this.labels = new JLabel[rows][cols];
		this.buttons = new MyButton[rows][cols];
		this.setLayout(null);
		this.initButtons();
		this.initLabels();
	}
 
	//initialize
	private void initLabels(){
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				JLabel l = new JLabel("", JLabel.CENTER);
				//Bounds of each cube
				l.setBounds(j * BLOCKWIDTH, i * BLOCKHEIGHT, BLOCKWIDTH, BLOCKHEIGHT);
				//draw border
				l.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				//Opaque for easy fill colour
				l.setOpaque(true);
				//yellow background
				l.setBackground(Color.YELLOW);
				//add to Jpanel 
				this.add(l);
				labels[i][j] = l;
			}
		}
		randomBomb();
		writeNumber();
	}
 
 
	// create bomb,in labels set as"*"
	private void randomBomb() {
		for (int i = 0; i < this.bombCount; i++) {
			// row number
			int rRow = (int) (Math.random() * this.rows);
			// column number
			int rCol = (int) (Math.random() * this.cols);
			// label as "*"
			this.labels[rRow][rCol].setText("*");
			// background colour
			this.labels[rRow][rCol].setBackground(Color.DARK_GRAY);
			// "*" color
			this.labels[rRow][rCol].setForeground(Color.RED);
		}
	}
 
	//lable number around bomb
	private void writeNumber() {
		for (int  i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				//if it is bomb, do not label number
				if (labels[i][j].getText().equals("*")) {
					continue;
				}
				// if it is not bomb, traverse 8 cube around it and count
				// number of bomb, showing the number in the cube
				int bombCount = 0;
				for (int[] off: offset) {
					int row = i + off[1];
					int col = j + off[0];
					//tell if it is bomb and cross border
					if (verify(row, col) && labels[row][col].getText().equals("*")) {
						bombCount++;
					}
				}
				// if has bomb
				if (bombCount > 0) {
					labels[i][j].setText(String.valueOf(bombCount));
				}
			}
		}
	}
 
	//tell if cross border
	private boolean verify(int row, int col) {
		return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
	}
 
	//initialize buttons to cover Jpanel
	private void initButtons() {
		// loop create button
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				MyButton btn = new MyButton();
				//initialize button
				btn.setBounds(j * BLOCKWIDTH, i * BLOCKHEIGHT, BLOCKWIDTH, BLOCKHEIGHT);
				this.add(btn);
				//store buttons
				buttons[i][j] = btn;
				btn.row = i;
				btn.col = j;
				// add listener
				// (click button,doing method in actionPerformed(ActionEvent e) in ActionListener() )
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						open((MyButton) e.getSource());
					}});
			}
		}
	}
 
	//open by click
	private void open(MyButton btn) {
		////set invisible to open button
		btn.setVisible(false);
		//tell is number or empty in button
		switch (labels[btn.row][btn.col].getText()) {
			// if it is bomb, open all and game over
			case "*" :
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						buttons[i][j].setVisible(false);
					}
				}
				break;
			//if it is empty, open empty cube around it, and recursion
			case "" :
				for (int[] off: offset) {
					int newRow = btn.row + off[0];
					int newCol = btn.col + off[1];
					if (verify(newRow, newCol)) {
						MyButton sButton = buttons[newRow][newCol];
						if (sButton.isVisible()) {
							open(sButton);
						}
					}
				}
			default:
		}
	}
 
	// calculate size
	public int[] returnSize() {
		int[] a = {this.cols * BLOCKWIDTH + 20, this.rows * BLOCKHEIGHT + 40};
		return a;
	}
}