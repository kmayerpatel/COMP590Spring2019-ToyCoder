package predres;

abstract public class PredResDecoder<T, DT, RM, QF, PM, PS> {
	// T: input type
	// DT: residual type
	// RM: residual model parameter type
	// QF: quantization factor type
	// PM: prediction model type
	// PS: prediction state type
	
	private PS prediction_state;
	
	protected PredResDecoder(PS init_pstate) {
		prediction_state = init_pstate;
	}
	
	public T decode(SourceUnit<PM, RM, QF> adu) {
		DT recovered_residual = dequantize(adu.getRModel(), adu.getQFactor());
		T prediction = synthesis(adu.getPModel(), prediction_state);
		T output = add(prediction, recovered_residual);		
		prediction_state = update(adu, output, prediction_state);
		
		return output;
	}

	public PS getPredictionState() {
		return prediction_state;
	}
	
	protected abstract T add(T prediction, DT recovered_residual);

	protected abstract PS update(SourceUnit<PM, RM,QF> adu, T output, PS current_prediction_state);

	protected abstract DT dequantize(RM residual_model_parameters, QF qfactor);
	
	protected abstract T synthesis(PM model_parameters, PS prediction_state);
}
