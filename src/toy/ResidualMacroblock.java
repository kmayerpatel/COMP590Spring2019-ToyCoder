package toy;

public interface ResidualMacroblock extends Macroblock {

	public ResidualMacroblock value(int x, int y, int val);
	
	public ResidualBlock block(int bx, int by);
	
	default public DCTMacroblock dct() {
		DCTBlock[][] dct_blocks = new DCTBlock[widthInBlocks()][heightInBlocks()];

		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				dct_blocks[bx][by] = block(bx,by).dct();
			}
		}
		
		return new DirectDCTMacroblock(dct_blocks);
	}

	default public int calcMAE() {
		int mae = 0;
		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by<heightInBlocks(); by++) {
				mae += block(bx,by).calcMAE();
			}
		}
		return mae;
	}
}
