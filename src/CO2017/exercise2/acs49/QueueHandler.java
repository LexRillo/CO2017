package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class QueueHandler implements Runnable{

	String file;
	MemManager memoryManager;
	ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public QueueHandler(java.util.concurrent.ThreadPoolExecutor e,MemManager m, java.lang.String f){
		memoryManager=m;
		file=f;
		exec=e;
	}

    @Override
	public void 	run(){
		//Code to read lines from a file of Process data, pause for the specified time*100, then create a Process instance and add it to the ThreadPool.
    	Path fpath = Paths.get(file);
		try (Scanner sfile = new Scanner(fpath)) {
		      int delay, size, rt;
		      char pid;

		      while (sfile.hasNextLine()) {
		        Scanner line = new Scanner(sfile.nextLine());		        
		        line.useDelimiter(":");
		        delay = line.nextInt();
		        pid = line.next().charAt(0);
		        size = line.nextInt();
		        rt = line.nextInt();
		        line.close();

		        Thread.sleep(100*delay);
		        Process p = new Process(memoryManager,pid,size,rt);
		        exec.execute(p);
		        //System.out.printf("delay: %s, ID: %c, size: %s, runtime: %s\n",delay, pid, size, rt );

		      }
		      sfile.close();
		      exec.shutdown();
		    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFileException e) {
			      System.err.println("File not found: "+file);
			      System.exit(1);
			}catch (IOException e) {
			      System.err.println(e);
			      System.exit(1);
			}



	}
}
