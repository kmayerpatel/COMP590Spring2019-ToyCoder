package predres;

public class DifferentialIntegerDecoder 
extends PredResDecoder<Integer, Integer, Integer, Integer,  DifferentialIntegerDecoder.PredFunc, Integer[]> {

	enum PredFunc {AVG, FIRST, LAST, LINEAR};

	protected DifferentialIntegerDecoder() {
		super(new Integer[] {0, 0, 0});
	}

	@Override
	protected Integer add(Integer prediction, Integer recovered_residual) {
		return prediction + recovered_residual;
	}

	@Override
	protected Integer[] update(SourceUnit<DifferentialIntegerDecoder.PredFunc, Integer, Integer> adu, Integer output, Integer[] current_prediction_state) {
		return new Integer[] {current_prediction_state[1], current_prediction_state[2], output};
	}

	@Override
	protected Integer dequantize(Integer residual_model_parameters, Integer qfactor) {
		return residual_model_parameters * qfactor;
	}

	@Override
	public Integer synthesis(PredFunc model, Integer[] prediction_state) {
		switch (model) {
		case AVG:
			return (prediction_state[0] + prediction_state[1] + prediction_state[2]) / 3;
		case FIRST:	
			return (prediction_state[0]);
		case LAST:
			return (prediction_state[2]);
		case LINEAR:
			return (prediction_state[2] + (prediction_state[2]-prediction_state[1]));
		}
		throw new RuntimeException("Shouldn't be able to get here.");
	}
}
