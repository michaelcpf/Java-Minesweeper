import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class InputFrame {
	
	private int row = 8, col = 8;
	
	private GUIFrame input_frame;
	private JPanel input_panel;
	private JButton confirm_button;
	private JLabel label_row, label_col, label_mines;
	private JSlider slider_row, slider_col, slider_mines;

	InputFrame() {
		slider_row = new JSlider(8, 30, 8);
		slider_row.setBounds(0, 0, 300, 70);
		slider_row.setPaintTicks(true);
		slider_row.setMinorTickSpacing(2);
		slider_row.setMajorTickSpacing(4);
		slider_row.setPaintLabels(true);
		slider_row.addChangeListener((e) -> {
			label_row.setText("Row: " + slider_row.getValue());
			setRow(slider_row.getValue());
			slider_mines.setMajorTickSpacing(((row-1)*(col-1)-10)/5);
			slider_mines.createStandardLabels(((row-1)*(col-1)-10)/5, 10);
			slider_mines.setMaximum((row-1)*(col-1));
		});
		
		slider_col = new JSlider(8, 24, 8);
		slider_col.setBounds(0, 70, 223, 70);
		slider_col.setPaintTicks(true);
		slider_col.setMinorTickSpacing(2);
		slider_col.setMajorTickSpacing(4);
		slider_col.setPaintLabels(true);
		slider_col.addChangeListener((e) -> {
			label_col.setText("Col: " + slider_col.getValue());
			setCol(slider_col.getValue());
			slider_mines.setMajorTickSpacing(((row-1)*(col-1)-10)/5);
			slider_mines.createStandardLabels(((row-1)*(col-1)-10)/5, 10);
			slider_mines.setMaximum((row-1)*(col-1));
		});
		
		slider_mines = new JSlider(10, (row-1)*(col-1), 10);
		slider_mines.setBounds(0, 140, 300, 70);
		slider_mines.setPaintTicks(true);
		slider_mines.setMajorTickSpacing(10);
		slider_mines.setPaintLabels(true);
		slider_mines.addChangeListener((e) -> {
			label_mines.setText("Mines: " + slider_mines.getValue());
		});
		
		label_row = new JLabel();
		label_row.setBounds(300, 0, 70, 70);
		label_row.setText("Row:" + slider_row.getValue());
		label_col = new JLabel();
		label_col.setBounds(300, 70, 70, 70);
		label_col.setText("Col:" + slider_col.getValue());
		label_mines = new JLabel();
		label_mines.setBounds(300, 140, 70, 70);
		label_mines.setText("Mines:" + slider_mines.getValue());
		
		confirm_button = new JButton("Confirm");
		confirm_button.setBounds(150, 220, 100, 50);
		confirm_button.setFocusable(false);
		confirm_button.setBorder(BorderFactory.createEtchedBorder());
		confirm_button.addActionListener((e) -> {
			new Minesweeper(row, col, slider_mines.getValue());
			input_frame.dispose();
		});
		
		input_panel = new JPanel();
		input_panel.setLayout(null);
		input_panel.setPreferredSize(new Dimension(400, 300));
		input_panel.add(slider_row);
		input_panel.add(label_row);
		input_panel.add(slider_col);
		input_panel.add(label_col);
		input_panel.add(slider_mines);
		input_panel.add(label_mines);
		input_panel.add(confirm_button);
		
		input_frame = new GUIFrame();
		input_frame.add(input_panel);
		input_frame.pack();
	}

	private void setRow(int row) {
		this.row = row;
	}

	private void setCol(int col) {
		this.col = col;
	}

}
