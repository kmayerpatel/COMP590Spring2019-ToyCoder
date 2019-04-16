package toy;

import java.io.IOException;

import ac.ModelFreeArithmeticEncoder;
import ac.ModelFreeArithmeticDecoder;
import io.BitSink;
import io.BitSource;
import io.InsufficientBitsLeftException;

public class CABAC {

	private static final int MAX_PROB = 100;

	private int _zero_prob;

	public CABAC() {
		_zero_prob = MAX_PROB/2;
	}

	public void encode(int value, ModelFreeArithmeticEncoder ac, BitSink sink) throws IOException {
		double cdf_break = ((double) _zero_prob) / ((double) MAX_PROB);

		if (value == 0) {
			ac.encode(0, cdf_break, sink);
		} else {
			ac.encode(cdf_break, 1.0, sink);
		}
		update(value);
	}

	public int decode(ModelFreeArithmeticDecoder ac, BitSource source) throws IOException, InsufficientBitsLeftException {
		int result = ac.decode(new double[] {0.0, ((double) _zero_prob)/((double) MAX_PROB), 1.0}, source);
		update(result);
		return result;
	}

	public void update(int bit) {
		if (bit == 0) {
			if (_zero_prob < MAX_PROB-1) {
				_zero_prob++;
			}			
		} else {
			if (_zero_prob > 1) {
				_zero_prob--;
			}
		}
	}


}
