import java.util.concurrent.RecursiveTask;

public class ForkJoinOption extends RecursiveTask<Double>{

	private long numSteps;
	
	public ForkJoinOption(long numSteps) {
		this.numSteps = numSteps;
	}
	
	
	
	
	@Override
	protected Double compute() {
		
		double sum = 0.0;
		double step = 1.0 / (double)numSteps;
        
		
//        for (long i=0; i < numSteps; ++i) {
//            double x = ((double)i+0.5)*step;
//            sum += 4.0/(1.0+x*x);
//            
//        }
//        double pi = sum * step;
		
		
		return null;
	}

}
