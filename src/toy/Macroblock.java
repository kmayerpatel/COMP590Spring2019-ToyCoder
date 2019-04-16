package toy;

public interface Macroblock extends Block {

	/* Geometry of the blocks within the macroblock */
	
	public int blockWidth();
	public int blockHeight();

	/* Macroblock geometry expressed in blocks. */
	
	default public int widthInBlocks() {
		return width() / blockWidth();
	};
	default public int heightInBlocks() {
		return height() / blockHeight();
	};

	/* Retrieve component block by block index. */
	
	public Block block(int bx, int by);

	/* Mutating value setter. */
	
	public Macroblock value(int x, int y, int val);

}
