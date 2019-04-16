package predres;

public class SourceUnit<PM, RM, QF> {
	private PM _pmodel;
	private RM _rmodel;
	private QF _qfactor;
	
	public SourceUnit(PM pmodel, RM rmodel, QF qfactor) {
		_pmodel = pmodel;
		_rmodel = rmodel;
		_qfactor = qfactor;
	}
	
	public PM getPModel() {
		return _pmodel;
	}
	
	public RM getRModel() {
		return _rmodel;
	}
	
	public QF getQFactor() {
		return _qfactor;
	}
}
