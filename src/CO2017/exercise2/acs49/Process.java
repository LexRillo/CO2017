package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class Process implements Runnable {

	MemManager manag;
	char procID;
	int procMem;
	int runtime;
	int address = -1;
	
public Process(MemManager m, char i, int s, int r){
	manag = m;
	procID = i;
	procMem = s;
	runtime = r;
}

public int getAddress(){
	//Accessor for the Address
	return address;
}

public char 	getID(){
	//Accessor for ID attribute.
	return procID;
}

public int 	getSize(){
	//Accessor for size attribute.
	return procMem;
}
@Override
public void run(){
//Basic behaviour when a Process thread is started.
System.out.println(this.toString() + " waiting to run.");
manag.allocate(this);
System.out.println(this.toString() + " running.");
try {
	Thread.sleep(100*runtime);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
System.out.println(this.toString() + " has finished.");
manag.free(this);
}

public void setAddress(int a){
	//Set the memory address used by this Process
	this.address = a;
}

public String 	toString(){
	//Generate a string representing the Process.
	String text = new String();
	if(address == -1){
		text = "U";
	}else{
		text = Integer.toString(address);
	}
	//padd to the right 3 and pad 2 on the right
	text = procID + ":" + String.format("%3s", text) + "+" + String.format("%2s", procMem);
	return text;
	}

}
