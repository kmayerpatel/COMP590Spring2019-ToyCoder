package toy;

public class DirectBlock implements Block {

	private int _values[][];

	public DirectBlock(int[][] values) {
		_values = values;
	}

	public DirectBlock(int width, int height) {
		this(new int[width][height]);
	}
	
	@Override
	public int width() {
		return _values.length;
	}

	@Override
	public int height() {
		return _values[0].length;
	}

	@Override
	public int value(int x, int y) {
		return _values[x][y];
	}

	@Override
	public Block value(int x, int y, int val) {
		_values[x][y] = val;
		return this;
	}
	
	@Override
	public String toString() {

		// Frame top
		String str = "+";
		for (int i=0; i<width(); i++) {
			str += "----" + ((i != width()-1) ? "-" : "+\n");
		}

		for (int i=0; i<height(); i++) {
			str += "|";
			for (int j=0; j<width(); j++) {
				str += String.format("%4d", _values[j][i]);
				str += (j != width()-1) ? " " : "|\n";
			}
			if (i!= height()-1) {
				str += "|";
				for (int j=0; j<width(); j++) {
					str += "    ";
					str += (j != width()-1) ? " " : "|\n";
				}				
			}
		}

		// Frame bottom
		str += "+";
		for (int i=0; i<width(); i++) {
			str += "----" + ((i != width()-1) ? "-" : "+\n");
		}

		return str;
	}
}
