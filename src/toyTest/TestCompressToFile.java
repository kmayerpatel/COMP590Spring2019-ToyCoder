package toyTest;

import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;
import predres.SourceUnit;
import toy.Block;
import toy.ComponentFrameMacroblockIterator;
import toy.ComponentMacroblockIterator;
import toy.DCTMacroblock;
import toy.IndirectComponentFrame;
import toy.ToyEncoder;
import toy.ToyEntropyEncoder;
import toy.ToyPredictionModel;
import toy.YUV420Reader;

public class TestCompressToFile {

	public static void main(String[] args) throws IOException {
		String input = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/joker-teaser-1_848x480p.yuv";
		String output = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/toy-compressed-q10.dat";

		int qfactor = 10;

		YUV420Reader yuv420_reader = new YUV420Reader(input, 848, 480);

		FileOutputStream fos = new FileOutputStream(output);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		ToyEncoder encoder = new ToyEncoder(848, 480, 16, 16, 8);
		ToyEntropyEncoder entropy_encoder = new ToyEntropyEncoder(bit_sink, 8);
		
		SourceUnit<ToyPredictionModel, DCTMacroblock, Integer> adu = null;

		for (int i=0; i<800; i++) { yuv420_reader.advanceFrame(); }
		int max_num_frames = 100;
		
		while(yuv420_reader.advanceFrame() && (max_num_frames-- > 0)) {
			System.out.println(yuv420_reader.frameIndex());
			
			Block y_plane = yuv420_reader.yPlane();
			
			IndirectComponentFrame y_component_frame = new IndirectComponentFrame(
					y_plane, 0, 0, y_plane.width(), y_plane.height(), 16, 16, 8, 8);
			
			ComponentMacroblockIterator mb_iter = new ComponentFrameMacroblockIterator(y_component_frame);

			while (mb_iter.hasNext()) {
				adu = encoder.encode(mb_iter.next(), qfactor);
				entropy_encoder.encode(adu);
			}			
		}
		yuv420_reader.close();
		entropy_encoder.close();
		fos.close();
	}

}
