package toyTest;

import java.io.IOException;

import predres.SourceUnit;
import toy.DirectComponentMacroblock;
import toy.Block;
import toy.ComponentFrameMacroblockIterator;
import toy.ComponentMacroblockIterator;
import toy.DCTMacroblock;
import toy.Frame;
import toy.FrameSubscriber;
import toy.IndirectComponentFrame;
import toy.ToyDecoder;
import toy.ToyEncoder;
import toy.ToyPredictionModel;
import toy.YUV420Reader;
import toy.YUV420Writer;

public class TestEncoderDecoder {

	public static void main(String[] args) throws IOException {
		String input = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/joker-teaser-1_848x480p.yuv";
		String output = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/writer-test-out.yuv";
		
		YUV420Reader yuv420_reader = new YUV420Reader(input, 848, 480);
		final YUV420Writer yuv420_writer = new YUV420Writer(output);

		ToyEncoder encoder = new ToyEncoder(848, 480, 16, 16, 8);
		
		ToyDecoder decoder = new ToyDecoder(848, 480, 16, 16, 8);

		decoder.setTarget(new FrameSubscriber() {

			@Override
			public void publish(Frame frame) {
				System.out.println("Received a frame!");
				try {
					yuv420_writer.writeYNoChroma(frame);
				} catch (Exception e) {
					throw new RuntimeException("Something went wrong");
				}
			}
			
		});
		
		int qfactor = 1;
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
				decoder.decode(adu);
			}			
		}
		yuv420_reader.close();
		yuv420_writer.close();
	}

}
