
public class Mines {
	
	private int[] pos, posX, posY;
	
	Mines(int row, int col, int num_mines) {
		generate_mines(row, col, num_mines);
	}
	
	private void generate_mines(int row, int col, int num_mines) {
		pos = new int[num_mines];
		for(int i=0; i<num_mines; i++) {
			pos[i] = (int) (Math.random()*row*col);
		}
		
		//same place?
		boolean err;
		do {
			err = false;
			int test;
			for (int i=1; i<num_mines; i++) {
				test = pos[i];
				for (int j=0; j<i; j++) {
					if (test==pos[j]) {
						err = true;
						pos[i] =  (int) (Math.random()*row*col);
					}
				}
			}
		} while (err);
		
		posX = new int[num_mines];
		posY = new int[num_mines];
		for(int i=0; i<num_mines; i++) {
			posX[i] = pos[i]/col;
			posY[i] = pos[i]%col;
		}
	}
	
	final void print() {
		for(int i=0; i<pos.length; i++) {
			System.out.println(pos[i] + " " + posX[i] + " " + posY[i]);
		}
	}

	int[] getPosX() {
		return posX;
	}

	int[] getPosY() {
		return posY;
	}

}
