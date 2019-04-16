package toy;

public class IndirectComponentMacroblock extends IndirectMacroblock implements ComponentMacroblock {

	public IndirectComponentMacroblock(Block source, int xoff, int yoff, int width, int height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height, block_width, block_height);
	}

	@Override
	public ComponentMacroblock value(int x, int y, int val) {
		super.value(x,y,val);
		return this;
	}

	@Override
	public ComponentBlock block(int bx, int by) {
		return new IndirectComponentBlock(this, bx*blockWidth(), by*blockHeight(), blockWidth(), blockHeight());
	};
}
