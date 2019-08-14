//Class is responsible for holding a instanced grid of tile objects, it also sets the happy face locations and setting tile states
//Happy faces = -1 tile state

public class Board {
	private Tile[][] game = new Tile[16][16];
	
	public Board(){
		for(int row = 0; row < 16; row++) {//For loops setting tile objects to every place on the grid
			for(int col = 0; col < 16; col++) {
				game[row][col] = new Tile();
				
			}
			
		}
		setMine();
		setTile();
		
	}
	
	public int getTileState(int row, int col) {//Returns tile state of a specific tile
		return game[row][col].getState();
	}
	
	public void setMine() {//Sets the happy face locations
		int num = 40;
		while(num > 0) {
			for(int row = 0; row < 16; row++) {
				for(int col = 0; col < 16; col++) {
					if(num > 0 && game[row][col].getState() == -2) {
						if((int) (Math.random() * 256) < 40) {
							game[row][col].setState(-1);
							num --;
						}
					}
				
				}
			
			}
		}
	}
	public void setTile() {//Method checks neighboring tiles for -1 tile state and increases for each one. Sets the tile state
		for(int row = 0; row < 16; row++) {
			for(int col = 0; col < 16; col++) {
				int count = 0;
				if(getTileState(row, col) != -1) {
					if(row - 1 >= 0 && col - 1 >= 0 && row - 1 < 16 && col - 1 < 16) {
						if(game[row - 1][col - 1].getState() == -1) {
							count++;
						}
					}
					if(row - 1 >= 0 && row - 1 < 16) {
						if(game[row - 1][col].getState() == -1) {
							count++;
						}
					}
					if(col - 1 >= 0 && col - 1 < 16) {
						if(game[row][col - 1].getState() == -1) {
							count++;
						}
					}
					if(row - 1 >= 0 && col + 1 >= 0 && row - 1 < 16 && col + 1 < 16) {
						if(game[row - 1][col + 1].getState() == -1) {
							count++;
						}
					}
					if(row + 1 >= 0 && col - 1 >= 0 && row + 1 < 16 && col - 1 < 16) {
						if(game[row + 1][col - 1].getState() == -1) {
							count++;
						}
					}
					if(row + 1 >= 0 && col + 1 >= 0 && row + 1 < 16 && col + 1 < 16) {
						if(game[row + 1][col + 1].getState() == -1) {
							count++;
						}
					}
					if(row + 1 >= 0 && row + 1 < 16) {
						if(game[row + 1][col].getState() == -1) {
							count++;
						}
					}
					if(col + 1 >= 0 && col + 1 < 16) {
						if(game[row][col + 1].getState() == -1) {
							count++;
						}
					}
					game[row][col].setState(count);
					
				}
				count = 0;
				
			}
			
		}
	}
}
