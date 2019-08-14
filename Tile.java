//Class is responsible for holding tile states
// -2 = unassigned
// -1 = happy face
// 0 = no happy faces neighboring
// 1-8 = Amount of happy faces neighboring
public class Tile {
	private int state = -2;
	public void setState(int num) {
		state = num;
	}
	public int getState() {
		return state;
	}
}
