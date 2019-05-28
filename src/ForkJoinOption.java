import java.util.concurrent.RecursiveTask;

public class ForkJoinOption extends RecursiveTask<Double> {

	private double step;
	private long low;
	private long high;
	private long size = 500;

	public ForkJoinOption(long low, long high, double step) {
		this.low = low;
		this.high = high;
		this.step = step;
	}

	@Override
	protected Double compute() {

		double sum = 0.0;

		if (high - low <= size) {
			for (long i = low; i < high; i++) {
				double x = ((double) i + 0.5) * step;
				sum += 4.0 / (1.0 + x * x);
			}

			return sum;
		} else {
			long mid = low + (high - low) / 2;
			ForkJoinOption left = new ForkJoinOption(low, mid, step);
			ForkJoinOption right = new ForkJoinOption(mid, high, step);
			left.fork();
			double rightResult = right.compute();
			double leftResult = left.join();

			return rightResult + leftResult;
		}

	}

}
