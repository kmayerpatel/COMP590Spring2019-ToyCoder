package toy;

abstract public class ToyPredictionModel {
	public enum PredictionMode {INTRA_CODE, INTRA_PRED, INTER_PRED};
	
	abstract public PredictionMode mode();
	
}

class IntraCodeModel extends ToyPredictionModel {

	public PredictionMode mode() {
		return PredictionMode.INTRA_CODE;
	}
}

class InterPredModel extends ToyPredictionModel {

	private int _dx;
	private int _dy;
	
	public InterPredModel(int dx, int dy) {
		_dx = dx;
		_dy = dy;
	}
	public PredictionMode mode() {
		return PredictionMode.INTER_PRED;
	}
	
	public int dx() {
		return _dx;
	}
	
	public int dy() {
		return _dy;
	}
}
