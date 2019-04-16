package predres;

import predres.DifferentialIntegerDecoder.PredFunc;

public class DifferentialIntegerEncoder 
extends PredResEncoder<Integer, Integer, Integer, Integer,  DifferentialIntegerDecoder.PredFunc, Integer[]> {

	protected DifferentialIntegerEncoder() {
		super(new DifferentialIntegerDecoder());
	}
	
	@Override
	protected Integer quantize(Integer residual, Integer qfactor) {
		return residual / qfactor;
	}

	@Override
	protected Integer difference(Integer input, Integer prediction) {
		return input - prediction;
	}

	@Override
	protected PredFunc analysis(Integer input, Integer[] prediction_state) {
		int best_diff = Math.abs(input-((prediction_state[0] + prediction_state[1] + prediction_state[2])/3));
		PredFunc best_func = PredFunc.AVG;
		
		if (Math.abs(input-prediction_state[0]) < best_diff) {
			best_diff = Math.abs(input-prediction_state[0]);
			best_func = PredFunc.FIRST;
		}
		if (Math.abs(input-prediction_state[2]) < best_diff) {
			best_diff = Math.abs(input-prediction_state[2]);
			best_func = PredFunc.LAST;
		}
		if (Math.abs(input-(prediction_state[2] + (prediction_state[2]-prediction_state[1]))) < best_diff) {
			best_diff = Math.abs(input-(prediction_state[2] + (prediction_state[2]-prediction_state[1])));
			best_func = PredFunc.LINEAR;
		}
		return best_func;
	}

	
}
