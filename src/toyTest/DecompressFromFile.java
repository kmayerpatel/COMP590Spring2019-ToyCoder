package toyTest;

import java.io.FileInputStream;
import java.io.IOException;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import predres.SourceUnit;
import toy.DCTMacroblock;
import toy.Frame;
import toy.FrameSubscriber;
import toy.ToyDecoder;
import toy.ToyEntropyDecoder;
import toy.ToyPredictionModel;
import toy.YUV420Writer;

public class DecompressFromFile {

	static int frames_read = 0;
	static boolean done = false;

	static void handleFrame(Frame frame, YUV420Writer yuv420_writer) {
		frames_read++;
		System.out.println("Received frame: " + frames_read);
		if (frames_read == 100) {
			done = true;
		}
		
		try {
			yuv420_writer.writeYNoChroma(frame);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}		
	}
	
	public static void main(String[] args) throws IOException, InsufficientBitsLeftException {
		String input = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/toy-compressed-q10.dat";
		String output = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/joker-teaser-q10_decompressed.yuv";

		final YUV420Writer yuv420_writer = new YUV420Writer(output);

		FileInputStream fis = new FileInputStream(input);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		ToyDecoder decoder = new ToyDecoder(848, 480, 16, 16, 8);

		decoder.setTarget(new FrameSubscriber() {
			public void publish(Frame frame) {
				handleFrame(frame, yuv420_writer);
			}
		});

		ToyEntropyDecoder entropy_decoder = new ToyEntropyDecoder(bit_source, 16, 16, 8);

		while (!done) {
			SourceUnit<ToyPredictionModel, DCTMacroblock, Integer> adu = entropy_decoder.decode();
			decoder.decode(adu);
		}
	}

}
