package toy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class YUV420Reader {

	private InputStream _source;
	private int _width;
	private int _height;
	private int _cur_frame_idx;
	private Block _y_plane;
	private Block _u_plane;
	private Block _v_plane;
	private int _max_frame_idx;
	private byte[] _read_buffer;

	public YUV420Reader(InputStream source, int width, int height, int max_frame_idx) throws IOException {
		_source = source;
		_width = width;
		_height = height;
		_cur_frame_idx = -1;
		_max_frame_idx = max_frame_idx;
		_y_plane = null;
		_u_plane = null;
		_v_plane = null;
		_read_buffer = new byte[(_width*_height*3)/2];
	}

	public YUV420Reader(String filename, int width, int height) throws IOException {
		this(new FileInputStream(filename), width, height, -1);
		_max_frame_idx = (int) (((FileInputStream) _source).getChannel().size()/((width*height*3)/2))-1;
	}

	public boolean advanceFrame() throws IOException {
		if (_max_frame_idx >= 0 && _cur_frame_idx == _max_frame_idx) {
			return false;
		}

		int[][] y_vals = new int[_width][_height];
		int[][] u_vals = new int[_width/2][_height/2];
		int[][] v_vals = new int[_width/2][_height/2];

		_source.read(_read_buffer);

		for (int y=0; y<_height; y++) {
			for (int x=0; x<_width; x++) {
				y_vals[x][y] = ((int) _read_buffer[y*_width+x]) & 0xff;
			}
		}

		int y_offset = _width*_height;

		for (int y=0; y<_height/2; y++) {
			for (int x=0; x<_width/2; x++) {
				u_vals[x][y] = ((int) _read_buffer[y_offset+(y*(_width/2))+x]) & 0xff;
			}
		}

		int u_offset = _width*_height/4;

		for (int y=0; y<_height/2; y++) {
			for (int x=0; x<_width/2; x++) {
				v_vals[x][y] = ((int) _read_buffer[y_offset+u_offset+(y*(_width/2))+x]) & 0xff;
			}
		}

		_y_plane = new DirectBlock(y_vals);
		_u_plane = new DirectBlock(u_vals);
		_v_plane = new DirectBlock(v_vals);

		_cur_frame_idx++;

		return true;
	}

	public Block yPlane() {
		return _y_plane;
	}

	public Block uPlane() {
		return _u_plane;
	}

	public Block vPlane() {
		return _v_plane;
	}

	public int frameIndex() {
		return _cur_frame_idx;
	}

	public void close() throws IOException {
		_source.close();
	}
}
