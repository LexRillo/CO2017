package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SimController implements Runnable{

	static MemManager memo;
	static ThreadPoolExecutor exec;
	
public static void main(java.lang.String[] args){
//Main simulator program Process the command line arguments.
if (args.length!=3){
      System.err.println("Usage: SimController <file>");
      System.exit(1);
    }
    //Process the command line arguments.
    String policy = args[0];
    String memorysize = args[1];
    String fname = args[2];
    Path fpath = Paths.get(fname);
	//Create an appropriate MemManager instance and print a message "Policy: PPPPP fit" indicating which policy will be used
	
	switch (policy){
	case "b":System.out.println("Policy: BEST fit");
	memo = new BestFitMemManager(Integer.parseInt(memorysize));
	break;
	case "w":System.out.println("Policy: WORST fit");
	memo = new WorstFitMemManager(Integer.parseInt(memorysize));
	break;
	case "f":System.out.println("Policy: FIRST fit");
	memo = new FirstFitMemManager(Integer.parseInt(memorysize));
	break;
	default: System.out.println("Incorrect Argument");
	break;
    }
	//Create a suitable ThreadPoolExecutor
	exec= (ThreadPoolExecutor) Executors.newCachedThreadPool();
	//Use the run method to start a watcher thread
	Thread watcher = new Thread(new SimController());
	watcher.start();

	//Create a thread for the QueueHandler and start it so that it processes the input file
	Thread qh = new Thread(new QueueHandler(exec, memo, fname));
	qh.start();
	try {
		watcher.join();
		qh.join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	//Wait for the watcher and queue handling threads to terminate, and for the ThreadPoolExecutor to terminate
    if(exec.isTerminated()){
	//Print out the termination message
	System.out.printf("All threads have terminated");
    }
}

@Override
public void run(){
//Run method for a watcher thread.

	double prev = 0;
	while(!exec.isTerminated()){
	double second = System.currentTimeMillis() / 1000;
		if (second != prev) {
			prev = second;
			if(memo.isChanged()== true){
				memo.toString();
			}
		}
	}
}

}
