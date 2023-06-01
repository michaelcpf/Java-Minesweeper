import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetupFrame {
	
	private GUIFrame setup_frame;
	private JPanel setup_panel;
	private JButton confirm_button;
	private JComboBox comboBox;
	
	SetupFrame() {
		String[] list = {"Beginner", "Intermediate", "Expert", "Custom"};
		comboBox = new JComboBox(list);
		comboBox.setPreferredSize(new Dimension(200, 50));
		((JLabel) comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		confirm_button = new JButton("Confirm");
		confirm_button.setPreferredSize(new Dimension(100, 30));
		confirm_button.setFocusable(false);
		confirm_button.setBorder(BorderFactory.createEtchedBorder());
		confirm_button.addActionListener((e) -> {
			initiate(comboBox.getSelectedIndex() + 1);
			setup_frame.dispose();
		});
		setup_panel = new JPanel();
		setup_panel.setLayout(new FlowLayout());
		setup_panel.setPreferredSize(new Dimension(200, 100));
		setup_panel.add(comboBox);
		setup_panel.add(confirm_button);
		setup_frame = new GUIFrame();
		setup_frame.add(setup_panel);
		setup_frame.pack();
	}
	
	private void initiate(int difficulty) {
		switch(difficulty) {
		case 1:
			new Minesweeper(9, 9, 10);
			break;
		case 2:
			new Minesweeper(16, 16, 40);
			break;
		case 3:
			new Minesweeper(16, 30, 99);
			break;
		case 4:
			new InputFrame();
			break;
		default:
			new Minesweeper(9, 9, 10);
		}
	}

}
