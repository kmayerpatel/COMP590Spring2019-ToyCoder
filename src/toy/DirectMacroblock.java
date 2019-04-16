package toy;

public class DirectMacroblock implements Macroblock {

	private Block[][] _blocks;
	
	public DirectMacroblock(Block[][] blocks) {
		_blocks = blocks;
	}
	
	public DirectMacroblock(int width, int height, int blockWidth, int blockHeight) {
		this(makeBlocks(width, height, blockWidth, blockHeight));
	}
	
	@Override
	public int value(int x, int y) {
		int bx = x / blockWidth();
		int by = y / blockHeight();
		
		return _blocks[bx][by].value(x%blockWidth(), y%blockHeight());
	}

	@Override
	public int blockWidth() {
		return _blocks[0][0].width();
	}

	@Override
	public int blockHeight() {
		return _blocks[0][0].height();
	}

	@Override
	public Block block(int bx, int by) {
		return _blocks[bx][by];
	}

	@Override
	public Macroblock value(int x, int y, int val) {
		int bx = x / blockWidth();
		int by = y / blockHeight();
		
		_blocks[bx][by] = _blocks[bx][by].value(x%blockWidth(), y%blockHeight(), val);
		
		return this;
	}

	@Override
	public int width() {
		return _blocks.length * blockWidth();
	}

	@Override
	public int height() {
		return _blocks[0].length * blockHeight();
	}

	private static Block[][] makeBlocks(int mb_width, int mb_height, int block_width, int block_height) {
		int w = mb_width/block_width;
		int h = mb_height/block_height;
		
		Block[][] blocks = new Block[w][h];
		for (int bx=0; bx < w; bx++) {
			for (int by=0; by < h; by++) {
				blocks[bx][by] = new DirectBlock(block_width, block_height);
			}
		}
		
		return blocks;
	}
}
