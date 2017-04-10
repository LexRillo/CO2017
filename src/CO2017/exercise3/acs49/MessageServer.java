package CO2017.exercise3.acs49;

import java.net.*;
import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;


public class MessageServer {

	public MessageServer(){}
	
	public static void main(java.lang.String[] args) throws java.io.IOException{
//	Message Server main method.
//	Should listen on port specified for incoming client connections. For each incoming connection, create and start a new MessageServerHandler 
//  to actually handle the client; use a ThreadPoolExecutor to manage the handler threads.
//	
//	Note that all clients share the same message board and can see and retieve each other's messages.
//	
//	There is no need to setup a mechanism to shut down the server (the user will KILL or Control-C the process).
		int port = Integer.parseInt(args[1]);
		MessageBoard board = new MessageBoard();
		ConcurrentHashMap<InetAddress, String> clientSet = new ConcurrentHashMap<InetAddress, String>();
		//exec= (ThreadPoolExecutor) Executors.newCachedThreadPool();
		try (ServerSocket server = new ServerSocket(port)) {
		      while (true) {
		        System.out.println("Waiting for client...");
		        Socket client = server.accept();
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
