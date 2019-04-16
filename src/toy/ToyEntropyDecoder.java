package toy;

import java.io.IOException;

import ac.ModelFreeArithmeticDecoder;
import io.BitSource;
import io.InsufficientBitsLeftException;
import predres.SourceUnit;

public class ToyEntropyDecoder {
	private BitSource _source;
	private ModelFreeArithmeticDecoder _ac;
	private PredictionModeEntropyCoder _pred_mode_dc;
	private SignedExpGolombEntropyCoder _dx_coder;
	private SignedExpGolombEntropyCoder _dy_coder;
	private SignedExpGolombEntropyCoder[][] _coeff_decoders;
	private SignedExpGolombEntropyCoder _q_factor_coder;
	
	private int _block_size;
	private int _mb_width;
	private int _mb_height;
	
	public ToyEntropyDecoder(BitSource source, int mb_width, int mb_height, int block_size) {
		_source = source;
		_ac = new ModelFreeArithmeticDecoder(30);
		_pred_mode_dc = new PredictionModeEntropyCoder();
		_dx_coder = new SignedExpGolombEntropyCoder();
		_dy_coder = new SignedExpGolombEntropyCoder();
		_coeff_decoders = new SignedExpGolombEntropyCoder[block_size][block_size];
		_q_factor_coder = new SignedExpGolombEntropyCoder();
		
		_block_size = block_size;
		_mb_width = mb_width;
		_mb_height = mb_height;
		
		for (int i=0; i<block_size; i++) {
			for (int j=0; j<block_size; j++) {
				_coeff_decoders[i][j] = new SignedExpGolombEntropyCoder();
			}			
		}
	}

	public SourceUnit<ToyPredictionModel, DCTMacroblock, Integer> decode() throws IOException, InsufficientBitsLeftException {

		ToyPredictionModel.PredictionMode pred_mode = _pred_mode_dc.decode(_ac, _source);
		
		ToyPredictionModel pm = null;
		
		if (pred_mode == ToyPredictionModel.PredictionMode.INTRA_CODE) {
			pm = new IntraCodeModel();
		} else if (pred_mode == ToyPredictionModel.PredictionMode.INTER_PRED) {
			int dx = _dx_coder.decode(_ac, _source);
			int dy = _dy_coder.decode(_ac, _source);
			
			pm = new InterPredModel(dx,dy);
		}
		
		assert pm != null;

		DCTMacroblock dct_mb = new DirectDCTMacroblock(_mb_width, _mb_height, _block_size, _block_size);
		
		for (int bx=0; bx<dct_mb.widthInBlocks(); bx++) {
			for (int by=0; by<dct_mb.heightInBlocks(); by++) {
				
				for (int x=0; x<dct_mb.blockWidth(); x++) {
					for (int y=0; y<dct_mb.blockHeight(); y++) {
						int coeff = _coeff_decoders[x][y].decode(_ac, _source);
						
						dct_mb = (DCTMacroblock) dct_mb.value(bx*_block_size+x, by*_block_size+y, coeff);
					}					
				}
			}			
		}

		int qfactor = _q_factor_coder.decode(_ac, _source);
		
		return new SourceUnit<ToyPredictionModel, DCTMacroblock, Integer>(pm, dct_mb, qfactor);
 	}
}
