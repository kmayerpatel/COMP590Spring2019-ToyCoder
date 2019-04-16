package toy;

public interface Frame extends Block {

	/* Macroblock geometry */
	
	public int mbWidth();
	public int mbHeight();
	
	/* Block geometry with macroblocks. */
	
	public int blockWidth();
	public int blockHeight();
	
	/* Geometry in terms of macroblocks */

	default int widthInMacroblocks() {		
		return width() / mbWidth();
	}

	default int heightInMacroblocks() {		
		return height() / mbWidth();
	}

	public Macroblock mbTile(int mbx, int mby);
	
	default Macroblock mbExtract(int x, int y) {
		return new IndirectMacroblock(this, x, y, width(), height(), blockWidth(), blockHeight());
	}
	
	/* Value retrieved via macroblock. */
	
	default int value(int x, int y) {
		int mbx = x / mbWidth();
		int mby = y / mbHeight();

		return mbTile(mbx, mby).value(x % mbWidth(), y% mbHeight());
	}

	public Frame value(int x, int y, int val);
	
}
