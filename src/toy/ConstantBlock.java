package toy;

public class ConstantBlock implements Block {

	private int _width;
	private int _height;
	private int _value;
	
	public ConstantBlock(int width, int height, int value) {
		_width = width;
		_height = height;
		_value = value;
	}

	@Override
	public int width() {
		return _width;
	}

	@Override
	public int height() {
		return _height;
	}

	@Override
	public int value(int x, int y) {
		return _value;
	}

	@Override
	public Block value(int x, int y, int val) {
		int[][] vals = new int[width()][height()];
		for (int ix=0; ix<width(); ix++) {
			for (int iy=0; iy<height(); iy++) {
				vals[ix][iy] = _value;
			}
		}
		vals[x][y] = val;
		return new DirectBlock(vals);
	}
}
