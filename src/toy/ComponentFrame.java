package toy;

public interface ComponentFrame extends Frame {

	public ComponentMacroblock mbTile(int mbx, int mby);
	public ComponentFrame value(int x, int y, int val);
	
	default ComponentMacroblock mbExtract(int x, int y) {
		return new IndirectComponentMacroblock(this, x, y, mbWidth(), mbHeight(), blockWidth(), blockHeight());
	}
}
