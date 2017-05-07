package CO2017.exercise3.acs49;

import java.net.*;
import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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
		ThreadPoolExecutor exec= (ThreadPoolExecutor) Executors.newCachedThreadPool();
		try (ServerSocket server = new ServerSocket(port)) {
		      while (true) {
		        System.out.println("Waiting for client...");
		        Socket client = server.accept();
		        InetAddress clientAddress = client.getInetAddress();
		        System.out.println("Client from " + clientAddress + " connected.");
		        /////////////
		        MessageServerHandler thishandler = new MessageServerHandler(board, client);
		        exec.execute(thishandler);
		      }
		    } catch (IOException e) {
		      System.err.println(e);
		    }
	}
}
