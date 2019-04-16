package toy;

public class IndirectMacroblock extends IndirectBlock implements Macroblock {

	private int _block_width;
	private int _block_height;

	public IndirectMacroblock(Block source, int xoff, int yoff, int width, int height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height);
		_block_width = block_width;
		_block_height = block_height;
	}

	@Override
	public Macroblock value(int x, int y, int val) {
		super.value(x,y,val);
		return this;
	}

	@Override
	public int blockWidth() {
		return _block_width;
	}

	@Override
	public int blockHeight() {
		return _block_height;
	}

	@Override
	public Block block(int bx, int by) {
		return new IndirectBlock(this, bx*blockWidth(), by*blockHeight(), blockWidth(), blockHeight());
	};
}
