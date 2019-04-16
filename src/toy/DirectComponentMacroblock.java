package toy;

public class DirectComponentMacroblock extends DirectMacroblock implements ComponentMacroblock {

	public DirectComponentMacroblock(ComponentBlock[][] blocks) {
		super(blocks);
	}

	public DirectComponentMacroblock(int width, int height, int blockWidth, int blockHeight) {
		this(makeBlocks(width, height, blockWidth, blockHeight));
	}
	
	private static ComponentBlock[][] makeBlocks(int mb_width, int mb_height, int block_width, int block_height) {
		int w = mb_width/block_width;
		int h = mb_height/block_height;
		
		ComponentBlock[][] blocks = new ComponentBlock[w][h];
		for (int bx=0; bx < w; bx++) {
			for (int by=0; by < h; by++) {
				blocks[bx][by] = new DirectComponentBlock(block_width, block_height);
			}
		}
		
		return blocks;
	}

	@Override
	public ComponentMacroblock value(int x, int y, int val) {
		super.value(x, y, val);
		return this;
	}
	
	@Override
	public ComponentBlock block(int bx, int by) {
		return (ComponentBlock) super.block(bx, by);
	}
}
