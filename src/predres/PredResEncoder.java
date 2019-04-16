package predres;


abstract public class PredResEncoder<T, DT, RM, QF, PM, PS> {
	// T: input type
	// DT: residual type
	// RM: residual model parameter type
	// QF: quantization factor type
	// PM: prediction model type
	// PS: prediction state type
	
	private PredResDecoder<T, DT, RM, QF, PM, PS> _decoder;

	protected PredResEncoder(PredResDecoder<T, DT, RM, QF, PM, PS> decoder) {
		_decoder = decoder;
	}
	
	public SourceUnit<PM,RM,QF> encode(T input, QF qfactor) {
		PM model = analysis(input, _decoder.getPredictionState());
		T prediction = _decoder.synthesis(model, _decoder.getPredictionState());
		DT residual = difference(input, prediction);
		RM residual_model = quantize(residual, qfactor);
		
		SourceUnit<PM, RM, QF> adu = new SourceUnit<PM, RM, QF>(model, residual_model, qfactor);		
		
		// Don't need result, but need to decode the adu with the embedded decoder
		// in order to update the prediction stat w
		_decoder.decode(adu);
		
		return new SourceUnit<PM,RM,QF>(model, residual_model, qfactor);
	}

	protected abstract PM analysis(T input, PS prediction_state);

	protected abstract DT difference(T input, T prediction);

	protected abstract RM quantize(DT residual, QF qfactor);

}
