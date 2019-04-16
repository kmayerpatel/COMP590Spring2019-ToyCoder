package toy;

public interface DCTMacroblock extends Macroblock {

	DCTBlock block(int bx, int by);
	
	default DCTMacroblock dequantize(int qfactor) {
		DCTBlock[][] dequantized_blocks = new DCTBlock[widthInBlocks()][heightInBlocks()];
		
		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				dequantized_blocks[bx][by] = block(bx,by).dequantize(qfactor);
			}
		}
		
		return new DirectDCTMacroblock(dequantized_blocks);
	}

	default ResidualMacroblock idct() {
		ResidualBlock[][] residual_blocks = new ResidualBlock[widthInBlocks()][heightInBlocks()];

		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				residual_blocks[bx][by] = block(bx,by).idct();
			}
		}
		
		return new DirectResidualMacroblock(residual_blocks);
	}

	default DCTMacroblock quantize(int qfactor) {
		DCTBlock[][] quantized_blocks = new DCTBlock[widthInBlocks()][heightInBlocks()];
		
		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				quantized_blocks[bx][by] = block(bx,by).quantize(qfactor);
			}
		}
		
		return new DirectDCTMacroblock(quantized_blocks);
	}

}
