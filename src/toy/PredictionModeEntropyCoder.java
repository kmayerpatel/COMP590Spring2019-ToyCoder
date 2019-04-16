package toy;

import toy.ToyPredictionModel.PredictionMode;

import java.io.IOException;

import ac.ModelFreeArithmeticDecoder;
import ac.ModelFreeArithmeticEncoder;
import io.BitSink;
import io.BitSource;
import io.InsufficientBitsLeftException;

public class PredictionModeEntropyCoder {
	private static final int MAX_COUNT_TOTAL = 10000;
	
	private PredictionMode[] _modes;
	private int[] _counts;
	private int _count_total;
	
	public PredictionModeEntropyCoder() {
		_modes = new PredictionMode[3];
		_modes[0] = PredictionMode.INTRA_CODE;
		_modes[1] = PredictionMode.INTRA_PRED;
		_modes[2] = PredictionMode.INTER_PRED;
		
		_counts = new int[3];
		_counts[0] = 1;
		_counts[1] = 1;
		_counts[2] = 1;
		_count_total = 3;
	}
	
	public void encode(PredictionMode mode, ModelFreeArithmeticEncoder ac, BitSink sink) throws IOException {
		int idx = lookup(mode);

		int cdf_count_sum = 0;

		for (int i=0; i<idx; i++) {
			cdf_count_sum += _counts[i];
		}
		double cdf_low = ((double) cdf_count_sum) / ((double) _count_total);

		cdf_count_sum += _counts[idx];

		double cdf_high = ((double) cdf_count_sum) / ((double) _count_total);

		ac.encode(cdf_low, cdf_high, sink);
		update(idx);
	}
	
	public PredictionMode decode(ModelFreeArithmeticDecoder ac, BitSource source) throws IOException, InsufficientBitsLeftException {
		double[] pdf = new double[_counts.length+1];
		int cdf_count_sum = 0;
		
		for (int i=0; i< _counts.length; i++) {
			pdf[i] = ((double) cdf_count_sum) / ((double) _count_total);
			cdf_count_sum += _counts[i];
		}
		pdf[_counts.length] = ((double) cdf_count_sum) / ((double) _count_total);
		
		int idx = ac.decode(pdf, source);
		update(idx);
		return _modes[idx];
	}
		
	private void update(int idx) {	
		if (_count_total == MAX_COUNT_TOTAL) {
			for (int i=0; i < _counts.length; i++) {
				if (_counts[i] > 1) {
					_counts[i]--;
					_count_total--;
				}
			}
		}		
		_counts[idx]++;
		_count_total++;
	}

	private int lookup(PredictionMode mode) {
		for (int i=0; i<_modes.length; i++) {
			if (_modes[i] == mode) {
				return i;
			}
		}
		return -1; // Shouldn't ever happen.
	}

}
