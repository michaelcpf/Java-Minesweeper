
public class GameBoard {
	
	private char[][] board;
	private final int ROW, COL;
	
	GameBoard(int row, int col, char c) {
		ROW = row;
		COL = col;
		create_board(c);
	}
	
	private final void create_board(char c) {
		board = new char[ROW][COL];
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				setBoard(i, j, c);
			}
		}
	}
	
	final void print() {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				System.out.print(getBoard(i, j) + " ");
			}
			System.out.println();
		}
	}
	
	final void print_with_index() {
		for(int i = -2; i < ROW; i++) {
			for(int j = -2; j < COL; j++) {
				if((i == -2 && j == -2) || i == -1 || j == -1) {
					System.out.print("  ");
				}
				else if(i == -2) {
					System.out.print((char) (49 + j) + " ");
				}
				else if(j == -2) {
					System.out.print((char) (49 + i) + " ");
				}
				else {
					System.out.print(getBoard(i, j) + " ");
				}
			}
			System.out.println();
		}
	}
	
	void setBoard(int row, int col, char c) {
		board[row][col] = c;
	}
	
	char getBoard(int row, int col) {
		return board[row][col];
	}

	int getRow() {
		return ROW;
	}

	int getCol() {
		return COL;
	}

}
