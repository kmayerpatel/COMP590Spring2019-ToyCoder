package toy;

public interface ComponentMacroblock extends Macroblock {

	ComponentMacroblock value(int x, int y, int val);

	default ComponentMacroblock add(ResidualMacroblock residual) {
		ComponentBlock[][] blocks = new ComponentBlock[widthInBlocks()][heightInBlocks()];

		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				blocks[bx][by] = block(bx,by).add(residual.block(bx, by));
			}
		}

		return new DirectComponentMacroblock(blocks);
	}

	default ResidualMacroblock diff(ComponentMacroblock other) {
		ResidualBlock[][] blocks = new ResidualBlock[widthInBlocks()][heightInBlocks()];

		for (int bx=0; bx < widthInBlocks(); bx++) {
			for (int by=0; by < heightInBlocks(); by++) {
				blocks[bx][by] = block(bx,by).diff(other.block(bx, by));
			}
		}

		return new DirectResidualMacroblock(blocks);
	}

	ComponentBlock block(int bx, int by);

	static ComponentMacroblock makeMacroblock(int mb_width, int mb_height, int block_width, int block_height) {
		ComponentBlock[][] blocks = new ComponentBlock[mb_width/block_width][mb_height/block_height];
		for (int bx=0; bx<mb_width/block_width; bx++) {
			for (int by=0; by<mb_height/block_height; by++) {
				blocks[bx][by] = ComponentBlock.makeBlock(block_width, block_height);
			}
		}
		
		return new DirectComponentMacroblock(blocks);
	}
}
