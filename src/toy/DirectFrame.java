package toy;

public class DirectFrame implements Frame {

	public Macroblock[][] _mblocks;

	public DirectFrame(Macroblock[][] mblocks) {
		_mblocks = mblocks;
	}
	
	public DirectFrame(int width, int height, int mb_width, int mb_height, int block_width, int block_height) {
		this(makeBlocks(width, height, mb_width, mb_height, block_width, block_height));
	}
	
	private static Macroblock[][] makeBlocks(int width, int height, 
			int mb_width, int mb_height, int block_width, int block_height) {
		int w = width/mb_width;
		int h = height/mb_height;
		
		Macroblock[][] blocks = new Macroblock[w][h];
		
		for (int mbx=0; mbx<w; mbx++) {
			for (int mby=0; mby<h; mby++) {
				blocks[mbx][mby] = new DirectMacroblock(mb_width, mb_height, block_width, block_height);
			}
		}
		return blocks;
	}

	@Override
	public Frame value(int x, int y, int val) {
		int mbx = x / mbWidth();
		int mby = y / mbHeight();
		
		_mblocks[mbx][mby] = _mblocks[mbx][mby].value(x%mbWidth(), y%mbHeight(), val);
		return this;
	}

	@Override
	public int mbWidth() {
		return _mblocks[0][0].width();
	}

	@Override
	public int mbHeight() {
		return _mblocks[0][0].height();
	}

	@Override
	public int blockWidth() {
		return _mblocks[0][0].blockWidth();
	}

	@Override
	public int blockHeight() {
		return _mblocks[0][0].blockHeight();
	}

	@Override
	public Macroblock mbTile(int mbx, int mby) {
		return _mblocks[mbx][mby];
	}


	@Override
	public int width() {
		return _mblocks.length * mbWidth();
	}


	@Override
	public int height() {
		return _mblocks[0].length * mbHeight();
	}
}
