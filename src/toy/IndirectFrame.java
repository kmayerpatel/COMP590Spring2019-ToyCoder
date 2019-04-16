package toy;

public class IndirectFrame extends IndirectBlock implements Frame {

	private int _block_width;
	private int _block_height;
	private int _mb_width;
	private int _mb_height;

	public IndirectFrame(Block source, int xoff, int yoff, int width, int height, int mb_width, int mb_height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height);
		_block_width = block_width;
		_block_height = block_height;
		_mb_width = mb_width;
		_mb_height = mb_height;
	}

	@Override
	public Frame value(int x, int y, int val) {
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
	public int mbWidth() {
		return _mb_width;
	}

	@Override
	public int mbHeight() {
		return _mb_height;
	}
	
	
	@Override
	public Macroblock mbTile(int mbx, int mby) {
		return mbExtract(mbx*mbWidth(), mby*mbHeight());
	}
	

}
