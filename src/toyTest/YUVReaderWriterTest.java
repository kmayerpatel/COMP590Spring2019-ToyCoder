package toyTest;

import java.io.IOException;

import toy.Block;
import toy.YUV420Reader;
import toy.YUV420Writer;

public class YUVReaderWriterTest {

	public static void main(String[] args) throws IOException {
		String input = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/joker-teaser-1_848x480p.yuv";
		String output = "/Users/kmp/teaching/sp19/comp590-data-compression/toy/writer-test-out.yuv";
		int max_num_frames = 10000;
		
		YUV420Reader yuv420_reader = new YUV420Reader(input, 848, 480);
		YUV420Writer yuv420_writer = new YUV420Writer(output);

		while(yuv420_reader.advanceFrame() && (max_num_frames-- > 0)) {
			System.out.println(yuv420_reader.frameIndex());
			Block y_plane = yuv420_reader.yPlane();
			yuv420_writer.writeYNoChroma(y_plane);
		}
		yuv420_reader.close();
		yuv420_writer.close();
	}
}
