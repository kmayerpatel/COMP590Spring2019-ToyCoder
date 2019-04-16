package toy;

public class IndirectDCTBlock extends IndirectBlock implements DCTBlock {

	public IndirectDCTBlock(Block source, int xoff, int yoff, int width, int height) {
		super(source, xoff, yoff, width, height);
	}

	@Override
	public DCTBlock value(int x, int y, int val) {
		super.value(x, y, val);
		return this;
	}
}
