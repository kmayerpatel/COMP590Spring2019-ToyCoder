package toy;

public interface Block {

	public int width();
	public int height();
	
	public int value(int x, int y);
	public Block value(int x, int y, int val);

}
