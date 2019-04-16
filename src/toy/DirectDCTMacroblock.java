package toy;

public class DirectDCTMacroblock extends DirectMacroblock implements DCTMacroblock {

	public DirectDCTMacroblock(DCTBlock[][] blocks) {
		super(blocks);
	}
	
	public DirectDCTMacroblock(int width, int height, int block_width, int block_height) {
		this(makeBlocks(width, height, block_width, block_height));
	}

	private static DCTBlock[][] makeBlocks(int mb_width, int mb_height, int block_width, int block_height) {
		int w = mb_width/block_width;
		int h = mb_height/block_height;
		
		DCTBlock[][] blocks = new DCTBlock[w][h];
		for (int bx=0; bx < w; bx++) {
			for (int by=0; by < h; by++) {
				blocks[bx][by] = new DirectDCTBlock(block_width, block_height);
			}
		}
		
		return blocks;
	}

	@Override
	public DCTMacroblock value(int x, int y, int val) {
		return (DCTMacroblock) super.value(x, y, val);
	}

	@Override
	public DCTBlock block(int bx, int by) {
		return (DCTBlock) super.block(bx, by);
	}
}
