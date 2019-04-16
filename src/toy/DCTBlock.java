package toy;

public interface DCTBlock extends Block {

	DCTBlock value(int x, int y, int val);

	default DCTBlock quantize(int qfactor) {
		int[][] quantized_values = new int[width()][height()];
		
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				quantized_values[x][y] = (value(x,y) + (qfactor/2)) / qfactor;
			}
			
		}
		return new DirectDCTBlock(quantized_values);
	}

	default DCTBlock dequantize(int qfactor) {
		int[][] dequantized_values = new int[width()][height()];
		
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				dequantized_values[x][y] = value(x,y) * qfactor;
			}
			
		}
		return new DirectDCTBlock(dequantized_values);
	}

	default ResidualBlock idct() {
		int[][] output_values = new int[width()][height()];

		for (int i= 0; i < width(); i++) {
			for (int j=0; j < height(); j++) {
				double sum = 0.0;

				for (int u=0; u< width(); u++) {
					for (int v=0; v<height(); v++) {
						double delta_j = (v == 0) ? (1.0/Math.sqrt(2.0)) : 1.0;
						double delta_i = (u == 0) ? (1.0/Math.sqrt(2.0)) : 1.0;
						
						sum +=  delta_j * delta_i *
								Math.cos(((2*i+1)*Math.PI*u)/(2.0*width())) *
								Math.cos(((2*j+1)*Math.PI*v)/(2.0*height())) *
								value(u,v);
					}
				}
				output_values[i][j] = (int) 
						((Math.sqrt(2.0/width())*
								Math.sqrt(2.0/height())* 
								sum));
			}
		}

		return new DirectResidualBlock(output_values);
	}	


}
