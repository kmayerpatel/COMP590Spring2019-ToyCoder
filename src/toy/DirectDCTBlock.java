package toy;

public class DirectDCTBlock extends DirectBlock implements DCTBlock {

	public DirectDCTBlock(int[][] residual_values) {
		super(residual_values);
	}
	
	public DirectDCTBlock(int width, int height) {
		this(new int[width][height]);
	}
	
	@Override
	public DCTBlock value(int x, int y, int val) {
		super.value(x, y, val);
		return this;
	}

}
