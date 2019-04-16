package toy;

import java.io.IOException;

import ac.ModelFreeArithmeticEncoder;
import io.BitSink;
import predres.SourceUnit;

public class ToyEntropyEncoder {
	private BitSink _sink;
	private ModelFreeArithmeticEncoder _ac;
	private PredictionModeEntropyCoder _pred_mode_ec;
	private SignedExpGolombEntropyCoder _dx_coder;
	private SignedExpGolombEntropyCoder _dy_coder;
	private SignedExpGolombEntropyCoder[][] _coeff_encoders;
	private SignedExpGolombEntropyCoder _qfactor_coder;
	
	public ToyEntropyEncoder(BitSink sink, int block_size) {
		_sink = sink;
		_ac = new ModelFreeArithmeticEncoder(30);
		_pred_mode_ec = new PredictionModeEntropyCoder();
		_dx_coder = new SignedExpGolombEntropyCoder();
		_dy_coder = new SignedExpGolombEntropyCoder();
		_coeff_encoders = new SignedExpGolombEntropyCoder[block_size][block_size];		
		for (int i=0; i<block_size; i++) {
			for (int j=0; j<block_size; j++) {
				_coeff_encoders[i][j] = new SignedExpGolombEntropyCoder();
			}			
		}
		_qfactor_coder = new SignedExpGolombEntropyCoder();

	}

	public void encode(SourceUnit<ToyPredictionModel, DCTMacroblock, Integer> adu) throws IOException {
		
		ToyPredictionModel pm = adu.getPModel();

		_pred_mode_ec.encode(pm.mode(), _ac, _sink);
		
		if (pm.mode() == ToyPredictionModel.PredictionMode.INTER_PRED) {
			InterPredModel ipm = (InterPredModel) pm;
			
			_dx_coder.encode(ipm.dx(), _ac, _sink);
			_dy_coder.encode(ipm.dy(), _ac, _sink);
		}
		
		DCTMacroblock dct_mb = adu.getRModel();
		
		for (int bx=0; bx<dct_mb.widthInBlocks(); bx++) {
			for (int by=0; by<dct_mb.heightInBlocks(); by++) {
				
				DCTBlock dct_block = dct_mb.block(bx, by);
				
				for (int x=0; x<dct_block.width(); x++) {
					for (int y=0; y<dct_block.height(); y++) {
						_coeff_encoders[x][y].encode(dct_block.value(x, y), _ac, _sink);
					}					
				}
			}			
		}
		_qfactor_coder.encode(adu.getQFactor(), _ac, _sink);
		
	}

	public void close() throws IOException {
		_ac.emitMiddle(_sink);
		_sink.padToWord();
	}
	
}
