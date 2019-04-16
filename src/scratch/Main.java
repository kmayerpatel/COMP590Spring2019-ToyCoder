package scratch;

import java.util.Scanner;

import scratch.DifferentialIntegerDecoder.PredFunc;

public class Main {

	public static void main(String[] args) {
		DifferentialIntegerEncoder encoder = new DifferentialIntegerEncoder();
		DifferentialIntegerDecoder decoder = new DifferentialIntegerDecoder();
		
		Scanner s = new Scanner(System.in);
		int qfactor = 7;
		
		while (s.hasNextInt()) {
			int i = s.nextInt();
			SourceUnit<PredFunc, Integer, Integer> adu = encoder.encode(i, qfactor);
			System.out.println("ADU produced:");
			System.out.println("  Prediction mode: " + adu.getPModel());
			System.out.println("  Residual model: " + adu.getRModel());
			System.out.println("  QFactor: " + adu.getQFactor());
			int recovered = decoder.decode(adu);
			
			System.out.println(recovered);
		}
	}
}
