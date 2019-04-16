package toy;

public class IndirectDCTMacroblock extends IndirectMacroblock implements DCTMacroblock {

	public IndirectDCTMacroblock(Block source, int xoff, int yoff, int width, int height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height, block_width, block_height);
	}

	@Override
	public DCTMacroblock value(int x, int y, int val) {
		super.value(x,y,val);
		return this;
	}

	@Override
	public DCTBlock block(int bx, int by) {
		return new IndirectDCTBlock(this, bx*blockWidth(), by*blockHeight(), blockWidth(), blockHeight());
	};
}
