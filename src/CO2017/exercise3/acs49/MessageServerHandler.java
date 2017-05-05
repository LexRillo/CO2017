package CO2017.exercise3.acs49;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageServerHandler /*implements Runnable*/ {

	public MessageServerHandler(MessageBoard b, java.net.Socket cl){}
	
	void run(){
//		Main interaction with the client.
//		Needs to (not necessarily complete list):
//
//		Repeatedly read in commands from the client and process them.
//		Valid commands are any of:
//		"LIST": Send back a list of the message headers, one per line, and terminated by a single "." on a line.
//		"BYE": Close the connection and terminate the handler thread.
//		"SEND:<id>:<msg>": Convert <id> into a full message header, and store the supplied message. Ignore the message if the header is already in use. 
//		No response expected.
//		
//		"GET:<msghead": retrieve the specified message and return it to the client in format OK:<msg>; if it does not exist, send ERR.
//		Anything else can be ignored.
		
		MessageBoard board = new MessageBoard();
		ConcurrentHashMap<InetAddress, String> clientSet = new ConcurrentHashMap<InetAddress, String>();
		//exec= (ThreadPoolExecutor) Executors.newCachedThreadPool();
		try (ServerSocket cl = new ServerSocket()) {
		      while (true) {
		        System.out.println("Waiting for client...");
		        Socket client = cl.accept();
		        //for the server handler
		        //MessageServerHandler mesgH = new MessageServerHandler(board, client);
		        //exec.execute(mesgH);
		        // get and display client's IP address
		        InetAddress clientAddress = client.getInetAddress();
		        System.out.println("Client from " + clientAddress + " connected.");
		        if(clientSet.get(clientAddress)== null){
		        	clientSet.put(clientAddress, Character.toString((char) (clientSet.size()+1)));
		        }
		        Writer out = new OutputStreamWriter(client.getOutputStream());
		        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		        String commando;
		        int num;
		        commando = in.toString();
		        switch(commando.charAt(0)){
		        	case 's': num = commando.charAt(1);
        					String body = commando.substring(1);
        					board.SaveMessage(new MessageHeader(clientSet.get(clientAddress).charAt(0), num), body);
		        		break;
		        	case 'g': char Cid = commando.charAt(1);
		        	        num = commando.charAt(2);
		        	        if(board.GetMessage(new MessageHeader(Cid, num))!= null){
		        	        	out.write(String.format("OK:%s", board.GetMessage(new MessageHeader(Cid, num))));
		        	        }else{
		        	        	out.write("ERR");	
		        	        }
		        		break;
		        	case 'l': Set<MessageHeader> listOfHeaders = board.ListHeaders();
		        			StringBuilder to_string = new StringBuilder();
		        			Iterator<MessageHeader> it = board.ListHeaders().iterator();
		        			while(it.hasNext()){
		        				to_string.append(it.toString());
		        				to_string.append("%n");
		        			}
//		        			for(int i=0; i< board.ListHeaders().size(); i++){
//		        				to_string = StringUtils.join(listOfHeaders.toString(), "%n");
//		        			}
		        			out.write(to_string.toString());
		        		break;
		        	default: System.out.println("Hmmmm. This was not supposed to happen");
		        		break;
		        }
		        //exec.shutdown();
		      }
	    } catch (IOException e) {
	      System.err.println(e);
	    }	
	}
}
