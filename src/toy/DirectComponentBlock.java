package toy;

public class DirectComponentBlock extends DirectBlock implements ComponentBlock {

	public DirectComponentBlock(int[][] component_values) {
		super(component_values);

		// Need to sweep through and clamp to component range
		
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				value(x,y,value(x,y));
			}
		}
	}
	
	public DirectComponentBlock(int width, int height) {
		this(new int[width][height]);
	}
	
	@Override
	public ComponentBlock value(int x, int y, int val) {
		val = (val < 0) ? 0 : (val > 255) ? 255 : val;
		super.value(x, y, val);
		return this;
	}
}
