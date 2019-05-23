import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NumIntSeq {

	// Number of cores in the PC
	final static int CORES = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		long numSteps = 0;
		double sum = 0.0;

		/* parse command line */
		if (args.length != 1 && args.length != 2) {
			System.out.println("args[0]:  number_of_steps");
			System.out.println("args[1]:  number_of_tasks (default == 20)");
			System.exit(1);
		}

		try {
			numSteps = Long.parseLong(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("argument " + args[0] + " must be long int");
			System.exit(1);
		}

		// Number of tasks by default
		int tasks = 5;

		// Number of tasks, if it's not the default one
		if (args.length == 2) {

			try {
				tasks = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println("argument " + args[1] + " must be int");
				System.exit(1);
			}

		}

		// Pool with as much threads as the number of available cores
		ExecutorService pool = Executors.newFixedThreadPool(CORES);

		// Variable that holds the results one by one
		Future<Double> result;
		// Loops the task is going to take care of
		long loops = numSteps / tasks;
		// First and last values of loops per task
		long start = 0, finish = 0;

		/* start timing */
		long startTime = System.currentTimeMillis();

		double step = 1.0 / (double) numSteps;
		for (int i = 0; i < tasks; i++) {

			finish += loops;
			result = pool.submit(new TaskCallable(step, start, finish));
			start += loops;

			sum += result.get();
		}

		// My pi
		double pi = sum * step;

		/* end timing and print result */
		long endTime = System.currentTimeMillis();

		// Kill the threads
		pool.shutdown();

		System.out.printf("sequential program results with %d steps\n", numSteps);
		System.out.printf("computed pi = %22.20f\n", pi);
		System.out.printf("difference between estimated pi and Math.PI = %.20f\n", Math.abs(pi - Math.PI));
		System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
	}
}
