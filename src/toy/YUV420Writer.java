package toy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class YUV420Writer {

	private OutputStream _sink;
	private byte[] _write_buffer;

	public YUV420Writer(OutputStream sink) {
		_sink = sink;
		_write_buffer = null;
	}

	public YUV420Writer(String filename) throws FileNotFoundException {
		this(new FileOutputStream(filename));
	}

	public void writeYUV(Block y_plane, Block u_plane, Block v_plane) throws IOException {
		// Set up write buffer if necessary
		
		int buffer_size = (y_plane.height() * y_plane.width() * 3) / 2;
		if (_write_buffer == null || _write_buffer.length < buffer_size) {
			_write_buffer = new byte[buffer_size];
		}
		
		for (int y=0; y<y_plane.height(); y++) {
			for (int x=0; x<y_plane.width(); x++) {
				_write_buffer[y*y_plane.width()+x] = (byte) (y_plane.value(x, y));
			}
		}
		
		int y_offset = y_plane.width() * y_plane.height();
		
		for (int y=0; y<u_plane.height(); y++) {
			for (int x=0; x<u_plane.width(); x++) {
				_write_buffer[y_offset+y*u_plane.width()+x] = (byte) (u_plane.value(x, y));
			}
		}

		int u_offset = u_plane.width() * u_plane.height();
		
		for (int y=0; y<v_plane.height(); y++) {
			for (int x=0; x<v_plane.width(); x++) {
				_write_buffer[y_offset+u_offset+y*v_plane.width()+x] = (byte) (v_plane.value(x, y));
			}
		}
		
		int v_offset = v_plane.width() * v_plane.height();
		
		_sink.write(_write_buffer, 0, y_offset+u_offset+v_offset);
		
	}

	public void writeYNoChroma(Block y_plane) throws IOException {
		ConstantBlock empty_uv = new ConstantBlock(y_plane.width()/2, y_plane.height()/2, 127);
		writeYUV(y_plane, empty_uv, empty_uv);
	}

	public void close() throws IOException {
		_sink.close();
	}
}
