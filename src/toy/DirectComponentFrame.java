package toy;

public class DirectComponentFrame extends DirectFrame implements ComponentFrame {

	public DirectComponentFrame(ComponentMacroblock[][] mblocks) {
		super(mblocks);
	}
	
	public DirectComponentFrame(int width, int height, int mb_width, int mb_height, int block_width, int block_height) {
		this(makeBlocks(width, height, mb_width, mb_height, block_width, block_height));
	}
	
	private static ComponentMacroblock[][] makeBlocks(int width, int height, 
			int mb_width, int mb_height, int block_width, int block_height) {
		int w = width/mb_width;
		int h = height/mb_height;
		
		ComponentMacroblock[][] blocks = new ComponentMacroblock[w][h];
		
		for (int mbx=0; mbx<w; mbx++) {
			for (int mby=0; mby<h; mby++) {
				blocks[mbx][mby] = new DirectComponentMacroblock(mb_width, mb_height, block_width, block_height);
			}
		}
		return blocks;
	}

	@Override
	public ComponentFrame value(int x, int y, int val) {
		return (ComponentFrame) super.value(x,y,val);
	}


	@Override
	public ComponentMacroblock mbTile(int mbx, int mby) {
		return (ComponentMacroblock) super.mbTile(mbx, mby);
	}
	
}
