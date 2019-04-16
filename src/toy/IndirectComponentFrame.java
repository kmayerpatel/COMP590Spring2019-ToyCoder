package toy;

public class IndirectComponentFrame extends IndirectFrame implements ComponentFrame {

	public IndirectComponentFrame(Block source, int xoff, int yoff, int width, int height, int mb_width, int mb_height, int block_width, int block_height) {
		super(source, xoff, yoff, width, height, mb_width, mb_height, block_width, block_height);
	}

	@Override
	public ComponentFrame value(int x, int y, int val) {
		super.value(x,y,val);
		return this;
	}

	@Override
	public ComponentMacroblock mbTile(int mbx, int mby) {
		return mbExtract(mbx*mbWidth(), mby*mbHeight());
	}
	

}
