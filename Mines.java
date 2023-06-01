import java.util.HashMap;

public class Mines {
	
	private int[] posX, posY;
	private HashMap<Integer, Integer> pos;
	
	Mines(int row, int col, int num_mines) {
		generate_mines(row, col, num_mines);
	}
	
	private void generate_mines(int row, int col, int num_mines) {
		pos = new HashMap<>();
		for(int i = 0; i < num_mines; i++) {
			int rng;
			do {
				rng = (int) (Math.random()*row*col);
			}while(pos.containsValue(rng));
			pos.put(i, rng);
		}
		
		posX = new int[num_mines];
		posY = new int[num_mines];
		for(int i=0; i<num_mines; i++) {
			posX[i] = pos.get(i)/col;
			posY[i] = pos.get(i)%col;
		}
	}
	
	final void print() {
		for(int i=0; i<pos.size(); i++) {
			System.out.println(pos.get(i) + " " + posX[i] + " " + posY[i]);
		}
	}

	int[] getPosX() {
		return posX;
	}

	int[] getPosY() {
		return posY;
	}

}
