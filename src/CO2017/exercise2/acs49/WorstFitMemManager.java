package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class WorstFitMemManager extends MemManager{
	
	int memorySize;

public WorstFitMemManager(int s) {
	super(s);
}

protected int findSpace(int s){
//Find an address space large enough for s using the worst fit strategy
//boolean counting = false;
//int address = -1;
//int ssize = 0;
//int size = 0;
//	for (int i=0; i<= _memory.length; i++){
//		if( _memory[i] =='.'){
//			counting = true;
//			size++;
//		}else{
//			if(counting == true && size >= s && size>ssize){
//				address = i-1-size;
//				ssize = size;
//			}
//			counting = false;
//			size = 0;
//		}
//	}
	int address = -1;
	for (int i=0; i<= _memory.length; i++){
		if( _memory[i] =='.'){
			if(countFreeSpacesAt(i) >= s && countFreeSpacesAt(address) < countFreeSpacesAt(i)){
				address = i;
				i = i + countFreeSpacesAt(i);
			}
		}
	}
	return address;
}

}
