import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper {
	
	private int row, col, num_mines;
	private final char ZERO = 48, MINE = 42, FLAG = 102, QUES = 63, BOX = 45;
	
	private GameBoard back_board, front_board;
	private Mines pos_mines;
	
	private GUIFrame game_frame;
	private JPanel game_panel;
	private JButton[][] front_button;
	
	private JMenuBar menu_bar;
	private JMenu file_menu;
	private JMenuItem new_game_item, retry_item, exit_item;
	
	Minesweeper(int row, int col, int num_mines) {
		setRow(row);
		setCol(col);
		setNum_mines(num_mines);
		initialise_board();
		gameGUI();
	}
	
	private void initialise_board() {
		back_board = new GameBoard(row, col, ZERO);
		front_board = new GameBoard(row, col, BOX);
		pos_mines = new Mines(row, col, num_mines);
		
		for(int k = 0; k < num_mines; k++) {
			int mine_x = pos_mines.getPosX()[k];
			int mine_y = pos_mines.getPosY()[k];
			update_count(mine_x, mine_y);
			back_board.setBoard(mine_x, mine_y, MINE);
		}
	}
	
	private void update_count(int mine_x, int mine_y) {
		for(int i = -1; i <= 1; i++) {
			if(mine_x+i < 0 || mine_x+i == row) {
				continue;
			}
			for(int j = -1; j <= 1; j++) {
				if(mine_y+j < 0 || mine_y+j == col) {
					continue;
				}
				else if(back_board.getBoard(mine_x+i, mine_y+j) != MINE) {
					back_board.setBoard(mine_x+i, mine_y+j, (char) (back_board.getBoard(mine_x+i, mine_y+j)+1));
				}
			}
		}
	}
	
	private void open_tile(int row, int col) {
		if(back_board.getBoard(row, col) == MINE) {
			for(int k = 0; k < num_mines; k++) {
				update_button_text(front_button, pos_mines.getPosX()[k], pos_mines.getPosY()[k], MINE);
			}
			deactivate_button();
			JOptionPane.showConfirmDialog(null, "Boom!", "Retry?", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		else if(back_board.getBoard(row, col) == ZERO) {
			open_zeros(row, col);
		}
		else {
			update_button_text(front_button, row, col, back_board.getBoard(row, col));
			front_button[row][col].setEnabled(false);
			check_win();
		}
	}
	
	private void open_zeros(int row, int col) {
		for(int i = -1; i <= 1; i++) {
			if(row+i < 0 || row+i == this.row) {
				continue;
			}
			for(int j = -1; j <= 1; j++) {
				if(col+j < 0 || col+j == this.col || (front_board.getBoard(row+i, col+j) >= ZERO && front_board.getBoard(row+i, col+j) <= ZERO+8)) {
					continue;
				}
				if(back_board.getBoard(row+i, col+j) == ZERO) {
					update_button_text(front_button, row+i, col+j, back_board.getBoard(row+i, col+j));
					front_button[row+i][col+j].setEnabled(false);
					open_zeros(row+i, col+j);
				}
				else {
					update_button_text(front_button, row+i, col+j, back_board.getBoard(row+i, col+j));
					front_button[row+i][col+j].setEnabled(false);
				}
			}
		}
	}
	
	private boolean check_win() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(front_board.getBoard(i, j) != BOX && front_board.getBoard(i, j) != QUES) {
					continue;
				}
				if(back_board.getBoard(i, j) >= ZERO && back_board.getBoard(i, j) <= ZERO+8) {
					return false;
				}
			}
		}
		deactivate_button();
		JOptionPane.showConfirmDialog(null, "You Win!", "Congratulations!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		return true;
	}
	
	private void gameGUI() {
		game_panel = new JPanel();		
		game_panel.setLayout(new GridLayout(row, col));
		game_frame = new GUIFrame();
		
		menu_bar = new JMenuBar();
		file_menu = new JMenu("File");
		new_game_item = new JMenuItem("New Game");
		retry_item = new JMenuItem("Retry");
		exit_item = new JMenuItem("Exit");
		new_game_item.addActionListener((e) -> {
			game_frame.dispose();
			new SetupFrame();
		});
		retry_item.addActionListener((e) -> {
			game_frame.dispose();
			new Minesweeper(row, col, num_mines);
		});
		exit_item.addActionListener((e) -> {
			System.exit(0);
		});
		
		front_button = new JButton[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				front_button[i][j] = new JButton(" ");
				front_button[i][j].setPreferredSize(new Dimension(30, 30));
				front_button[i][j].setFocusable(false);
				front_button[i][j].setBorder(BorderFactory.createEtchedBorder());
				int tempi = i, tempj = j;
				front_button[i][j].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						if(e.getButton() == 1) {
							if(front_board.getBoard(tempi, tempj) == BOX) {
								open_tile(tempi, tempj);
							}
						}
						else if(e.getButton() == 3) {
							switch(front_board.getBoard(tempi, tempj)) {
							case BOX: 
								update_button_text((JButton) e.getComponent(), tempi, tempj, FLAG);
								break;
							case FLAG:
								update_button_text((JButton) e.getComponent(), tempi, tempj, QUES);
								break;
							case QUES:
								update_button_text((JButton) e.getComponent(), tempi, tempj, ' ');
								front_board.setBoard(tempi, tempj, BOX);
								break;
							}
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				game_panel.add(front_button[i][j]);
			}
		}
		
		game_frame.setJMenuBar(menu_bar);
		menu_bar.add(file_menu);
		file_menu.add(new_game_item);
		file_menu.add(retry_item);
		file_menu.add(exit_item);
		game_frame.add(game_panel);
		game_frame.pack();
	}
	
	private void update_button_text(JButton b, int row, int col, char c) {
		b.setText(String.valueOf(c));
		front_board.setBoard(row, col, c);
	}
	
	private void update_button_text(JButton[][] b, int row, int col, char c) {
		b[row][col].setText(String.valueOf(c));
		front_board.setBoard(row, col, c);
	}
	
	private void deactivate_button() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				front_button[i][j].setEnabled(false);
				front_button[i][j].removeMouseListener(front_button[i][j].getMouseListeners()[1]);
				front_button[i][j].removeMouseListener(front_button[i][j].getMouseListeners()[0]);
			}
		}
	}

	private void setRow(int row) {
		this.row = row;
	}

	private void setCol(int col) {
		this.col = col;
	}

	private void setNum_mines(int num_mines) {
		this.num_mines = num_mines;
	}

}
