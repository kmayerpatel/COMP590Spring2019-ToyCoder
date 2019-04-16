package toy;

public class IndirectComponentBlock extends IndirectBlock implements ComponentBlock {

	public IndirectComponentBlock(Block source, int xoff, int yoff, int width, int height) {
		super(source, xoff, yoff, width, height);
	}

	@Override
	public int value(int x, int y) {
		int v = super.value(x, y);
		return (v < 0) ? 0 : (v > 255) ? 255 : v;
	}

	@Override
	public ComponentBlock value(int x, int y, int val) {
		val = (val < 0) ? 0 : (val > 255) ? 255 : val;
		super.value(x, y, val);
		return this;
	}
}
