package toy;

import predres.PredResEncoder;

public class ToyEncoder extends PredResEncoder<ComponentMacroblock, 
ResidualMacroblock, DCTMacroblock, Integer, ToyPredictionModel, ToyPredictionState> {

	public ToyEncoder(int frame_width, int frame_height, 
			int mb_width, int mb_height, int block_size) {
		super(new ToyDecoder(frame_width, frame_height,
				mb_width, mb_height, block_size));
		
		assert (frame_width > 0 && frame_width%mb_width == 0 && mb_width%block_size == 0);
		assert (frame_height > 0 && frame_height%mb_height ==0 && mb_height%block_size == 0);

	}

	@Override
	protected ToyPredictionModel analysis(ComponentMacroblock input, ToyPredictionState prediction_state) {
		
		if (prediction_state.hasPrevious()) {
			
			int best_dx = 0;
			int best_dy = 0;
//			ComponentMacroblock candidate = prediction_state.extractPrediction(0, 0);
//			int best_mae = input.diff(candidate).calcMAE();
//			for (int dx=-3; dx<3; dx++) {
//				for (int dy=-3; dy<3; dy++) {
//					if ((dx == 0) && (dy == 0)) continue;
//					
//					candidate = prediction_state.extractPrediction(dx, dy);
//					if (candidate != null) {
//						int mae = input.diff(candidate).calcMAE();
//						if (mae < best_mae) {
//							best_dx = dx;
//							best_dy = dy;
//							best_mae = mae;
//						}
//					}
//				}
//			}

			return new InterPredModel(best_dx, best_dy);
			
		} else {
			return new IntraCodeModel();
		}
	}

	@Override
	protected ResidualMacroblock difference(ComponentMacroblock input, ComponentMacroblock prediction) {
		return input.diff(prediction);
	}

	@Override
	protected DCTMacroblock quantize(ResidualMacroblock residual, Integer qfactor) {
		// TODO Auto-generated method stub
		return residual.dct().quantize(qfactor);
	}
}
