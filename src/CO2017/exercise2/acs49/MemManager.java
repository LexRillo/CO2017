package CO2017.exercise2.acs49;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public abstract class MemManager{

	boolean _changed;
	int _largestSpace;
	char[] _memory;

public MemManager(int s){
	_largestSpace = s;
	_memory = new char[s];
	for(int i=0; i<=0; i++){
		_memory[i] = '.';
	}
	_changed = true;

}

public void allocate(Process p){
//Allocate memory for a process; block until space is available
	int spacetowrite = this.findSpace(p.getSize());
	if(spacetowrite != -1){
		p.setAddress(this.findSpace(p.getSize()));
		for(int i=this.findSpace(p.getSize()); i<=(this.findSpace(p.getSize())+p.getSize()); i++){
			_memory[i] = p.getID();
		}
		_changed = true;
	}else{
		//WAIT THREADS <----------------------------------
	}

}

int countFreeSpacesAt(int pos){
//Start at address pos and calculate the size of the contiguous empty space begining there.
	int spaces = 0;
	for(int i=pos; i< _memory.length; i++){
		if(_memory[i] =='.'){
			spaces++;
		}else{
			return spaces;
		}
	}
	return spaces;
}

protected abstract int findSpace(int s);

public void free(Process p){
	//Free memory used by a process.
	//for(int i=p.getAddress(); i< (p.getAddress() + p.getSize()); i++){
	int initial = 0;
	int k = 0;
	while(initial == 0 && k!=_memory.length){
		if(_memory[k] == p.getID()){
			initial = k;
		}
		k++;
	}
	for(int i=initial; i< initial + p.getSize(); i++){
			_memory[i] = '.';
	}
	_changed = true;
	//notifyAll();
}

public boolean isChanged(){
	//Return whether the state of memory has changed since the last invocation of toString
	return _changed;
}

public String toString(){
//Generate a string representing the state of the Memory.
int freeSpace = 0;
StringBuilder tmp = new StringBuilder();
String text;
	for(int i=0; i<= (int) (_memory.length/20); i++){
		//printf might give errors
		tmp.append(String.format("%3s", i) + "|");
			for(int j=i; j < i+20; j++){
			   tmp.append(_memory[j]);
			   if(_memory[j]=='.'){
				   freeSpace++;
			   }
			}
			tmp.append("|/n");
	}
tmp.append("ls: " +freeSpace);
text = tmp.toString();
_changed = false;
return text;
}

}
