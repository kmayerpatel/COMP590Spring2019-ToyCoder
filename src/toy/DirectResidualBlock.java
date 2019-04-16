package toy;

public class DirectResidualBlock extends DirectBlock implements ResidualBlock {

	public DirectResidualBlock(int[][] residual_values) {
		super(residual_values);

		// Need to sweep through and clamp to component range
		
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				value(x,y,value(x,y));
			}
		}
	}
	
	public DirectResidualBlock(int width, int height) {
		this(new int[width][height]);
	}
	
	@Override
	public ResidualBlock value(int x, int y, int val) {
		val = (val < -255) ? -255 : (val > 255) ? 255 : val;
		super.value(x, y, val);
		return this;
	}
}
