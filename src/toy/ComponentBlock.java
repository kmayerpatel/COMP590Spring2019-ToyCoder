package toy;

public interface ComponentBlock extends Block {

	ComponentBlock value(int x, int y, int val);

	default ComponentBlock add(ResidualBlock residual) {
		int[][] vals = new int[width()][height()];
		
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				vals[x][y] = value(x,y) + residual.value(x,y);
			}
		}
		return new DirectComponentBlock(vals);
	}
	
	default ResidualBlock diff(ComponentBlock other) {
		int[][] vals = new int[width()][height()];
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				vals[x][y] = value(x,y) - other.value(x,y);
			}
		}
		return new DirectResidualBlock(vals);
	}

	static ComponentBlock makeBlock(int block_width, int block_height) {
		int[][] vals = new int[block_width][block_height];		
		return new DirectComponentBlock(vals);
	}
}
