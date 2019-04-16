package toy;

public class IndirectBlock implements Block {

	private Block _source;
	private int _xoff;
	private int _yoff;
	private int _width;
	private int _height;

	public IndirectBlock(Block source, int xoff, int yoff, int width, int height) {
		_source = source;
		_xoff = xoff;
		_yoff = yoff;
		_width = width;
		_height = height;
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
		return _source.value(x+_xoff, y+_yoff);
	}

	@Override
	public Block value(int x, int y, int val) {
		_source = _source.value(x+_xoff, y+_yoff, val);
		return this;
	};
}
