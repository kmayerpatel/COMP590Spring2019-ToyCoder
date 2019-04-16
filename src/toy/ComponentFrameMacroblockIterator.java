package toy;

public class ComponentFrameMacroblockIterator implements ComponentMacroblockIterator {

	private ComponentFrame _source;
	private int _cx;
	private int _cy;
	
	public ComponentFrameMacroblockIterator(ComponentFrame source) {
		_source = source;
		_cx = 0;
		_cy = 0;
	}

	@Override
	public boolean hasNext() {
		return (_cx < _source.widthInMacroblocks() && _cy < _source.heightInMacroblocks());
	}

	@Override
	public ComponentMacroblock next() {
		if (!hasNext()) {
			throw new RuntimeException("No more macroblocks");
		}
		
		ComponentMacroblock next_mb = _source.mbTile(_cx, _cy);
		
		_cx++;
		if (_cx >= _source.widthInMacroblocks()) {
			_cx = 0;
			_cy++;
		}
		
		return next_mb;
	}
	
	
}
