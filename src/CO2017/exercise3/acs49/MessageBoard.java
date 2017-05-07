package CO2017.exercise3.acs49;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBoard {

	ConcurrentHashMap<MessageHeader, String> wholeMessage;

	MessageBoard(){
	//Create a new empty message board
		wholeMessage = new ConcurrentHashMap<MessageHeader, String>();
	}


	String GetMessage(MessageHeader mh){
	//Get the body of a message
		return wholeMessage.get(mh);
	}

	synchronized void SaveMessage(MessageHeader mh, String msg){
	//Add a message to the board
	//This method will need to be synchronized.
		if(GetMessage(mh) == null){
			wholeMessage.put(mh, msg);
		}
	}

	Set<MessageHeader> ListHeaders(){
		//the Set of all message headers
		return wholeMessage.keySet();
	}

}
