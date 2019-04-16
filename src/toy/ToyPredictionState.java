package toy;

public class ToyPredictionState {
	
	private ComponentFrame _prior_frame;
	private ComponentFrame _current_frame;
	private ToyPredictionModel[][] _prior_models;
	private ToyPredictionModel[][] _current_models;
	private int _next_mbx;
	private int _next_mby;
	private int _frame_width;
	private int _frame_height;
	private int _mb_width;
	private int _mb_height;
	private int _block_size;
	private FrameSubscriber _target;
	
	public ToyPredictionState(int frame_width, int frame_height, int mb_width, int mb_height, int block_size) {
		_frame_width = frame_width;
		_frame_height = frame_height;
		_mb_width = mb_width;
		_mb_height = mb_height;
		_block_size = block_size;
		
		_prior_frame = null;
		_current_frame = new DirectComponentFrame(frame_width, frame_height, 
				mb_width, mb_height, block_size, block_size);
		
		_prior_models = null;
		_current_models = new ToyPredictionModel[frame_width/mb_width][frame_height/mb_height];
		
		_next_mbx = 0;
		_next_mby = 0;
		_target = null;
	}

	public void setTarget(FrameSubscriber frame_subscriber) {
		_target = frame_subscriber;
	}
	
	public ToyPredictionState update(ToyPredictionModel model, ComponentMacroblock output) {
		_current_models[_next_mbx][_next_mby] = model;

		int xoff =  _next_mbx * _mb_width;
		int yoff =  _next_mby * _mb_height;
		
		for (int x=0; x<output.width();  x++) {
			for (int y=0; y<output.height(); y++) {
				_current_frame = _current_frame.value(x+xoff, y+yoff, output.value(x, y));
			}
		}
		
		_next_mbx++;
		if (_next_mbx >= _frame_width/_mb_width) {
			_next_mbx = 0;
			_next_mby++;
		}
		if (_next_mby >= _frame_height/_mb_height) {
			_prior_frame = _current_frame;
			_current_frame = new DirectComponentFrame(_frame_width, _frame_height, 
					_mb_width, _mb_height, _block_size, _block_size);
			
			_prior_models = _current_models;
			_current_models = new ToyPredictionModel[_frame_width/_mb_width][_frame_height/_mb_height];

			_next_mbx = 0;
			_next_mby = 0;
			
			// Need to publish prior frame to target if any
			if (_target != null) {
				_target.publish(_prior_frame);
			}
		}
		
		return this;
	}

	public boolean hasPrevious() {
		return _prior_frame != null;
	}

	public ComponentMacroblock extractPrediction(int dx, int dy) {
		
		int cx = (_next_mbx * _mb_width)+dx;
		int cy = (_next_mby * _mb_height)+dy;
		
		if (cx < 0 || cy < 0 || cx + _mb_width > _frame_width || cy + _mb_height > _frame_height) {
			return null;
		}
		
		ComponentBlock[][] blocks = new ComponentBlock[_mb_width/_block_size][_mb_height/_block_size];
		
		for (int bx=0; bx < _mb_width/_block_size; bx++) {
			for (int by=0; by < _mb_width/_block_size; by++) {
				int xoff = cx + (bx * _block_size);
				int yoff = cy + (by * _block_size);
				
				blocks[bx][by] = new IndirectComponentBlock(_prior_frame, cx, cy, _block_size, _block_size);
			}
		}
		
		return new DirectComponentMacroblock(blocks);
	}
}
