package toy;

public class DirectResidualMacroblock extends DirectMacroblock implements ResidualMacroblock {

	public DirectResidualMacroblock(ResidualBlock[][] blocks) {
		super(blocks);
	}

	public DirectResidualMacroblock(int width, int height, int blockWidth, int blockHeight) {
		this(makeBlocks(width, height, blockWidth, blockHeight));
	}

	private static ResidualBlock[][] makeBlocks(int mb_width, int mb_height, int block_width, int block_height) {
		int w = mb_width/block_width;
		int h = mb_height/block_height;
		
		ResidualBlock[][] blocks = new ResidualBlock[w][h];
		for (int bx=0; bx < w; bx++) {
			for (int by=0; by < h; by++) {
				blocks[bx][by] = new DirectResidualBlock(block_width, block_height);
			}
		}
		
		return blocks;
	}

	@Override
	public ResidualMacroblock value(int x, int y, int val) {
		super.value(x, y, val);
		return this;
	}

	@Override
	public ResidualBlock block(int bx, int by) {
		return (ResidualBlock) super.block(bx, by);
	}
}
