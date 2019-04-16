package toy;

public class IndirectResidualMacroblock extends IndirectMacroblock implements ResidualMacroblock {

	public IndirectResidualMacroblock(Block source, int xoff, int yoff, int width, int height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height, block_width, block_height);
	}

	@Override
	public ResidualMacroblock value(int x, int y, int val) {
		super.value(x,y,val);
		return this;
	}

	@Override
	public ResidualBlock block(int bx, int by) {
		return new IndirectResidualBlock(this, bx*blockWidth(), by*blockHeight(), blockWidth(), blockHeight());
	};
}
