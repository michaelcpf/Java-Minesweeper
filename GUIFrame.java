import java.awt.FlowLayout;

import javax.swing.JFrame;

public class GUIFrame extends JFrame {

	GUIFrame() {
		this.setTitle("Minesweeper GUI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
