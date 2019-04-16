package toy;

import java.io.IOException;

import ac.ModelFreeArithmeticDecoder;
import ac.ModelFreeArithmeticEncoder;
import io.BitSink;
import io.BitSource;
import io.InsufficientBitsLeftException;

public class SignedExpGolombEntropyCoder {

	static private final int MAX_SYMBOL_LENGTH = 40;
	
	private CABAC[] _cabacs;
	
	public SignedExpGolombEntropyCoder () {
		_cabacs = new CABAC[MAX_SYMBOL_LENGTH];
		
		for (int i=0; i<MAX_SYMBOL_LENGTH; i++) {
			_cabacs[i] = new CABAC();
		}
	}
	
	public void encode(int value, ModelFreeArithmeticEncoder ac, BitSink sink) throws IOException {
		if (value == 0) {
			// Special case zero.
			
			_cabacs[0].encode(0, ac, sink);
			return;
		}

		int cabac_idx = 0;
		
		int level = ((int) (Math.log(Math.abs(value)) / Math.log(2.0)))+1;
		for (int i=0; i<level; i++) {
			_cabacs[cabac_idx].encode(1, ac, sink);
			cabac_idx++;
		}
		_cabacs[cabac_idx].encode(0, ac, sink);

		int num_range_bits = level-1;
		if (num_range_bits > 0) {
			int range_base = 1 << num_range_bits;			
			int range_value = Math.abs(value) - range_base;
			for (int i=0; i<num_range_bits; i++) {
				_cabacs[cabac_idx].encode((range_value >> (num_range_bits-1-i)) & 0x1, ac, sink);
				cabac_idx++;
			}
		}
		if (value < 0) {
			_cabacs[cabac_idx].encode(1, ac, sink);
		} else {
			_cabacs[cabac_idx].encode(0, ac, sink);
		}		
	}
	
	public int decode(ModelFreeArithmeticDecoder ac, BitSource source) throws IOException, InsufficientBitsLeftException {
		
		int level = 0;

		int cabac_idx = 0;
		
		while (_cabacs[cabac_idx].decode(ac, source) == 1) {
			level++;
			cabac_idx++;
		}

		// Special case zero.
		if (level == 0) {
			return 0;
		}
		
		int num_range_bits = level-1;
		int range_base = 1 << num_range_bits;			
		
		int range_value = 0;
		for (int i=0; i< num_range_bits; i++) {
			range_value = range_value * 2 + _cabacs[cabac_idx].decode(ac, source);
			cabac_idx++;
		}

		return (range_base + range_value) * ((_cabacs[cabac_idx].decode(ac, source) == 0) ? 1 : -1);
	}

}
