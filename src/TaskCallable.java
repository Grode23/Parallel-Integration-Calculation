import java.util.concurrent.Callable;

class TaskCallable implements Callable<Double> {

	private long start, finish;
	private double step;
	private double sum = 0.0;

	public TaskCallable(double step, long start, long finish) {
		this.step = step;
		this.start = start;
		this.finish = finish;
	}

	@Override
	public Double call() throws Exception {

		// do computation
		for (long i = start; i < finish; i++) {
			double x = ((double) i + 0.5) * step;
			sum += 4.0 / (1.0 + x * x);
		}

		return sum;
	}

}