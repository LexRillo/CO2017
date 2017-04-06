package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;


public class FirstFitMemManager extends MemManager{
	
	int memorySize;
	
public FirstFitMemManager (int s){
	super(s);
}

protected int findSpace(int s){
//Find an address space large enough for s using the first fit strategy http://blog.hakzone.info/posts-and-articles/java/first-fit-algorithm-in-java/
//  int i = 0;
//	int address = -1;
//	boolean flag = false;
//	while (flag == false && i<=_memory.length){
//		if(_memory[i] =='.'){
//			i++;
//			if(i+s < _memory.length){
//				int j = 0;
//				while(_memory[i+j]=='.' && flag == false){
//					if(j==s){
//						flag =true;
//					}
//				}
//				if(flag == false){
//					i += j;
//				}else{
//					address = i;
//				}
//			}
//		}else{
//			i++;
//			//counter =0;
//		}
//	}
	int address = -1;
	for (int i=0; i<= _memory.length -1; i++){
		if( _memory[i] =='.'){
			if(countFreeSpacesAt(i) >= s){
				return address;
			}
		}
	}
	return address;
}
}
