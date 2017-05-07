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

public class MessageServerHandler implements Runnable {

	MessageBoard board;
	Socket client;
	Writer out;
	BufferedReader in;
	public MessageServerHandler(MessageBoard b, java.net.Socket cl){
		board = b;
		client =cl;
		try {
	      out = new OutputStreamWriter(client.getOutputStream());
	      in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
	    } catch (IOException e) {
	      System.err.printf("Failed to create Data streams to %s%n",cl.getInetAddress());
	      System.err.println(e);
	      System.exit(1);
	    }
	}

	public void run(){
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


		long threadId = Thread.currentThread().getId();
		char threadChar = (char) (threadId+54);
		System.out.println("this thread's letter "+ threadChar);
		//ConcurrentHashMap<InetAddress, String> clientSet = new ConcurrentHashMap<InetAddress, String>();
		//exec= (ThreadPoolExecutor) Executors.newCachedThreadPool();
		try {

	        //for the server handler
	        //MessageServerHandler mesgH = new MessageServerHandler(board, client);
	        //exec.execute(mesgH);
	        // get and display client's IP address
	        InetAddress clientAddress = client.getInetAddress();
	        System.out.println("Client from " + clientAddress + " connected.");
	        String commando;
	        int num;
	        while(true){
	        commando = in.readLine();
	        if(commando.equalsIgnoreCase("LIST")){

	        	StringBuilder to_string = new StringBuilder();
    			Iterator<MessageHeader> it = board.ListHeaders().iterator();
    			while(it.hasNext()){
    				to_string.append(it.toString());
    				to_string.append("%n");
    			}
    			out.write(to_string.toString());
    			System.out.println("Got command list");
	        }else if (commando.startsWith("GET:")){

	        	char Cid = commando.charAt(4);
	        	num = Integer.parseInt(commando.substring(6));
	        	System.out.println("Got command get num "+ num + " Cid " + Cid);
    	        if(board.GetMessage(new MessageHeader(Cid, num))!= null){

    	        	out.write(String.format("OK:%s", board.GetMessage(new MessageHeader(Cid, num))));
    	        }else{
    	        	System.out.println("Got command error");
    	        	out.write("ERR");
    	        }

	        }else if(commando.startsWith("SEND:")){
	        	String str = commando.substring(5);
	        	str = str.substring(0,str.indexOf(":"));
	        	num = Integer.parseInt(str);
				String body = commando.substring(7);
				System.out.println("Got command send num "+ num + " body " + body);
				board.SaveMessage(new MessageHeader(threadChar, num), body);

	        }else{
	        	System.out.println("Hmmmm. This was not supposed to happen");
	        }
//		        switch(commando.substring(0,commando.indexOf(":"))){
//		        	case "SEND": num = commando.charAt(1);
//        					String body = commando.substring(1);
//        					board.SaveMessage(new MessageHeader(threadChar, num), body);
//		        		break;
//		        	case "GET": char Cid = commando.charAt(1);
//		        	        num = commando.charAt(2);
//		        	        if(board.GetMessage(new MessageHeader(Cid, num))!= null){
//		        	        	out.write(String.format("OK:%s", board.GetMessage(new MessageHeader(Cid, num))));
//		        	        }else{
//		        	        	out.write("ERR");
//		        	        }
//		        		break;
//		        	case "LIST": Set<MessageHeader> listOfHeaders = board.ListHeaders();
//		        			StringBuilder to_string = new StringBuilder();
//		        			Iterator<MessageHeader> it = board.ListHeaders().iterator();
//		        			while(it.hasNext()){
//		        				to_string.append(it.toString());
//		        				to_string.append("%n");
//		        			}
////		        			for(int i=0; i< board.ListHeaders().size(); i++){
////		        				to_string = StringUtils.join(listOfHeaders.toString(), "%n");
////		        			}
//		        			out.write(to_string.toString());
//		        		break;
//		        	default: System.out.println("Hmmmm. This was not supposed to happen");
//		        		break;
//		        }
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
