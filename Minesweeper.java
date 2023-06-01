import java.util.Scanner;

public class Minesweeper {
	
	Scanner user_input = new Scanner(System.in);
	private int row, col, num_mines, difficulty;
	private final char ZERO = 48, MINE = 42, FLAG = 102, QUES = 63, BOX = 45;
	private boolean lose;
	
	GameBoard back_board;
	GameBoard front_board;
	Mines pos_mines;
	
	Minesweeper() {
		initialise_board();
		do {
			front_board.print_with_index();
			next_move();
		} while(!lose && !check_win());
		front_board.print_with_index();
	}
	
	private void initialise_board() {
		lose = false;
		while(true) {
			System.out.print("Enter difficult: Beginner(1), Intermediate(2), Expert(3), Custom(4): ");
			difficulty = user_input.nextInt();
			user_input.nextLine();
			if(difficulty > 0 && difficulty < 5) {
				break;
			}
			else {
				System.out.println("Invalid input!");
			}
		}
		
		switch(difficulty) {
		case 1:
			row = 9;
			col = 9;
			num_mines = 10;
			break;
		case 2:
			row = 16;
			col = 16;
			num_mines = 40;
			break;
		case 3:
			row = 16;
			col = 30;
			num_mines = 99;
			break;
		case 4:
			row = input_in_range(8, 24, "row");
			col = input_in_range(8, 30, "col");
			num_mines = input_in_range(10, (row-1)*(col-1), "mine");
			break;
		default:
			row = 9;
			col = 9;
			num_mines = 10;
		}
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
	
	private int input_in_range(int min, int max, String s) {
		while(true) {
			System.out.print("Please input " + s + " number(" + min + "~" + max + "): ");
			int input = user_input.nextInt();
			user_input.nextLine();
			if(input >= min && input <= max) {
				return input;
			}
			else {
				System.out.println("Integer is not in range");
			}
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
					back_board.setBoard(mine_x+i, mine_y+j,(char) (back_board.getBoard(mine_x+i, mine_y+j)+1));
				}
			}
		}
	}
	
	private void next_move() {
		int row_move = 0, col_move = 0;
		String move;
		do {
			System.out.print("What to do? Explore(e), Place flag(f), Place \"?\"(q): ");
			move = user_input.nextLine();
			if(!move.equals("e") && !move.equals("f") && !move.equals("q")) {
				System.out.println("Invalid input!");
				continue;
			}
			row_move = input_in_range(1, row, "row") - 1;
			col_move = input_in_range(1, col, "col") - 1;
		} while(move_invalid(row_move, col_move, move));
	}
	
	private boolean move_invalid(int row, int col, String move) {
		char target_tile = front_board.getBoard(row, col);
		if(target_tile >= ZERO && target_tile <= ZERO+8) {
			System.out.println("You have discoverd this tile");
			return true;
		}
		switch(move) {
		case "e":
			if(target_tile == BOX) {
				open_tile(row, col);
				return false;
			}
			else if(target_tile == FLAG) {
				System.out.println("A flag has been placed here");
				return true;
			}
			else if(target_tile == QUES) {
				System.out.println("A question mark has been placed here");
				return true;
			}
			break;
		case "f":
			if(target_tile == BOX || target_tile == QUES) {
				front_board.setBoard(row, col, FLAG);
				return false;
			}
			else if(target_tile == FLAG) {
				front_board.setBoard(row, col, BOX);
				return false;
			}
			break;
		case "q":
			if(target_tile == BOX || target_tile == FLAG) {
				front_board.setBoard(row, col, QUES);
				return false;
			}
			else if(target_tile == QUES) {
				front_board.setBoard(row, col, BOX);
				return false;
			}
			break;
		}
		System.out.println("Something went wrong");
		return true;
	}
	
	private void open_tile(int row, int col) {
		if(back_board.getBoard(row, col) == MINE) {
			for(int k = 0; k < num_mines; k++) {
				front_board.setBoard(pos_mines.getPosX()[k], pos_mines.getPosY()[k], MINE);
			}
			System.out.println("Boom!");
			lose = true;
		}
		else if(back_board.getBoard(row, col) == ZERO) {
			open_zeros(row, col);
		}
		else {
			front_board.setBoard(row, col, back_board.getBoard(row, col));
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
				if(front_board.getBoard(row+i, col+j) != ZERO && back_board.getBoard(row+i, col+j) == ZERO) {
					front_board.setBoard(row+i, col+j, back_board.getBoard(row+i, col+j));
					open_zeros(row+i, col+j);
				}
				else {
					front_board.setBoard(row+i, col+j, back_board.getBoard(row+i, col+j));
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
		System.out.println("You win!");
		return true;
	}

}
