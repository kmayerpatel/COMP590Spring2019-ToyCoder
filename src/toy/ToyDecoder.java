package toy;

import predres.PredResDecoder;
import predres.SourceUnit;

public class ToyDecoder extends 
PredResDecoder<ComponentMacroblock, ResidualMacroblock, DCTMacroblock, Integer, ToyPredictionModel, ToyPredictionState> implements FrameSubscriber {

	private ComponentMacroblock _intra_code_prediction;
	private FrameSubscriber _target;

	public ToyDecoder(int frame_width, int frame_height, int mb_width, int mb_height, int block_size) {

		super(new ToyPredictionState(frame_width, frame_height, mb_width, mb_height, block_size));

		_target = null;

		assert (frame_width > 0 && frame_width%mb_width == 0 && mb_width%block_size == 0);
		assert (frame_height > 0 && frame_height%mb_height ==0 && mb_height%block_size == 0);

		// Create a constant 128 macroblock for intracode mode prediction

		_intra_code_prediction = new IndirectComponentMacroblock(new ConstantBlock(mb_width, mb_height, 128), 
				0, 0, mb_width, mb_height, block_size, block_size);

		((ToyPredictionState) getPredictionState()).setTarget(this);
	}

	@Override
	protected ComponentMacroblock add(ComponentMacroblock prediction, ResidualMacroblock recovered_residual) {
		return prediction.add(recovered_residual);
	}

	@Override
	protected ToyPredictionState update(SourceUnit<ToyPredictionModel, DCTMacroblock, Integer> adu,
			ComponentMacroblock output, ToyPredictionState current_prediction_state) {

		return current_prediction_state.update(adu.getPModel(), output);
	}

	@Override
	protected ResidualMacroblock dequantize(DCTMacroblock residual_model, Integer qfactor) {

		return residual_model.dequantize(qfactor).idct();

	}

	@Override
	protected ComponentMacroblock synthesis(ToyPredictionModel prediction_model, ToyPredictionState prediction_state) {
		if (prediction_model.mode() == ToyPredictionModel.PredictionMode.INTRA_CODE) {
			return _intra_code_prediction;
		} else if (prediction_model.mode() == ToyPredictionModel.PredictionMode.INTER_PRED) {
			InterPredModel model = (InterPredModel) prediction_model;

			return prediction_state.extractPrediction(model.dx(), model.dy());
		}

		throw new RuntimeException("Shouldn't get here");
	}

	@Override
	public void publish(Frame frame) {
		if (_target != null) {
			_target.publish(frame);
		}
	}

	public void setTarget(FrameSubscriber target) {
		_target = target;
	}

}
