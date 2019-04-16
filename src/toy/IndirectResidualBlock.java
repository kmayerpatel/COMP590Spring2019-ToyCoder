package toy;

public class IndirectResidualBlock extends IndirectBlock implements ResidualBlock {

	public IndirectResidualBlock(Block source, int xoff, int yoff, int width, int height) {
		super(source, xoff, yoff, width, height);
	}

	@Override
	public int value(int x, int y) {
		int v = super.value(x, y);
		return (v < -128) ? -128 : (v > 127) ? 127 : v;
	}
	
	@Override
	public ResidualBlock value(int x, int y, int val) {
		val = (val < -128) ? -128 : (val > 127) ? 127 : val;
		super.value(x, y, val);
		return this;
	}
}
