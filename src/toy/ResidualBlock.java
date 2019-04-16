package toy;

public interface ResidualBlock extends Block {

	ResidualBlock value(int x, int y, int val);
	
	default public DCTBlock dct() {
		int[][] output_values = new int[width()][height()];

		// Equation used:
		// F(u,v) = 
		//    (2/N)^.5 * (2/M)^.5 *
		//    Sigma_i(0,N-1) Sigma_j(0,M-1) [
		//       D(u)*D(v) *
		//       cos((2i +1)(PI*u)/(2*N)) *
		//       cos((2j +1)(PI*v)/(2*M)) *
		//       f(i,v)
		//    ]
		// 
		// Where... 
		// N = width, M = height
		// D(a) = 1/2^0.5 for a = 0, 1 otherwise
		// F(a,b) = transformed value
		// f(a,b) = input value

		for (int u = 0; u < width(); u++) {
			for (int v=0; v < height(); v++) {
				double sum = 0.0;

				double delta_u = (u == 0) ? (1.0/Math.sqrt(2.0)) : 1.0;
				double delta_v = (v == 0) ? (1.0/Math.sqrt(2.0)) : 1.0;

				for (int i=0; i< width(); i++) {
					for (int j=0; j<height(); j++) {
						sum +=  delta_v * delta_u *
								Math.cos(((2*i+1)*Math.PI*u)/(2.0*width())) *
								Math.cos(((2*j+1)*Math.PI*v)/(2.0*height())) *
								value(i,j);
					}
				}
				output_values[u][v] = (int) 
						(Math.sqrt(2.0/width())*
								Math.sqrt(2.0/height())* 
								sum);
			}
		}

		return new DirectDCTBlock(output_values);
	}
	
	default public int calcMAE() {
		int mae = 0;
		for (int x=0; x < width(); x++) {
			for (int y=0; y<height(); y++) {
				mae += Math.abs(value(x,y));
			}
		}
		return mae;
	}

}
