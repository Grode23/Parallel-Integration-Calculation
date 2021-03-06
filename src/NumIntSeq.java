import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * numSteps, step, pi, sum and x are parameters of the algorithm that is used,
 * so they are not going to be explained
 */
public class NumIntSeq {

	// Number of cores in the PC
	final static int CORES = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		long numSteps = 0;
		double pi = 0.0;

		/* parse command line */
		if (args.length != 1) {
			System.out.println("args[0]:  number_of_steps");
			System.exit(1);
		}

		try {
			numSteps = Long.parseLong(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("argument " + args[0] + " must be long int");
			System.exit(1);
		}

		// Choose between the available options
		Scanner sc = new Scanner(System.in);
		int decision;

		do {
			System.out.println("Choose option: (1 or 2)");
			System.out.println("1. ThreadPool");
			System.out.println("2. ForkJoin");
			System.out.print("Your answer: ");
			decision = sc.nextInt();

		} while (decision != 1 && decision != 2);

		/* start timing */
		long startTime = System.currentTimeMillis();

		// Run a solution, depending on your decision
		switch (decision) {
		case 1:
			pi = runThreadPoolOption(numSteps);
		case 2:
			pi = runForkJoinOption(numSteps);
		default:
			break;
		}

		/* end timing and print result */
		long endTime = System.currentTimeMillis();

		sc.close();

		// Result displayed in console
		System.out.printf("sequential program results with %d steps\n", numSteps);
		System.out.printf("computed pi = %22.20f\n", pi);
		System.out.printf("difference between estimated pi and Math.PI = %.20f\n", Math.abs(pi - Math.PI));
		System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
	}

	// ThreadPool (executors) solution
	private static double runThreadPoolOption(long numSteps) throws InterruptedException, ExecutionException {

		double sum = 0.0;
		// Number of tasks by default
		// Definitely not an ideal way of handling tasks, but I want to keep the user
		// interface (and the project in general) as simple as possible
		int tasks = 20;

		// Pool with as much threads as the number of available cores
		ExecutorService pool = Executors.newFixedThreadPool(CORES);

		// Variable that holds the results one by one
		Future<Double> result;
		// Loops the task is going to take care of
		long loops = numSteps / tasks;
		// First and last values of loops per task
		long start = 0, finish = 0;

		double step = 1.0 / (double) numSteps;
		for (int i = 0; i < tasks; i++) {

			finish += loops;
			result = pool.submit(new TaskCallable(step, start, finish));
			start += loops;

			sum += result.get();
		}

		// My pi
		double pi = sum * step;

		// Kill the threads
		pool.shutdown();

		return pi;

	}

	// ForkJoin solution
	private static double runForkJoinOption(long numSteps) {

		double step = 1.0 / (double) numSteps;

		ForkJoinPool forkJoinPool = new ForkJoinPool(CORES);
		Double sum = forkJoinPool.invoke(new ForkJoinOption(0, numSteps, step));

		double pi = sum * step;

		return pi;
	}
}
