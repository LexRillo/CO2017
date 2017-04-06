package CO2017.exercise3.acs49;

import java.net.*;
import java.io.*;

public class MessageClient {

	public MessageClient(){}
	
	static void	main(String[] args){
//		Main client behaviour.
//		Use the command line arguments to open a connection to a Message Server.
//
//		Repeatedly prompt the user (with a single '? ' prompt) for a command.
//
//		Valid commands are "LIST", "SEND:<msg>", "GET:<msghead>", "BYE". Anything else can be ignored.
//
//		For each command, convert it to a protocol message and send it to the server. Read the server response if one is expected, and print it for the local user.
//
//		If the command was "SEND" then need to use a new unique integer identifier for the message when it is sent over to the server. 
//		Simplest method is to use 1 for the first message, 2 for the second, etc. Each client program maintains its own list of message numbers.
//
//		Terminate the client if the command was "BYE"; otherwise wait for further user input.
		
		int host = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		
		try (Socket server = new Socket(host, port)) {
			System.out.println("Connected to " + server.getInetAddress());

		      BufferedReader in = new BufferedReader (new InputStreamReader(server.getInputStream(), "UTF-8"));

		      Writer out = new OutputStreamWriter(server.getOutputStream());

		      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		      String commando = new String();
		      int num = 999; // sentinel
		      int result;
		      do {
		        System.out.print("Enter number to square: ");
		        num = Integer.parseInt(stdin.readLine());

		        out.write(String.format("%d%n",num));
		        out.flush();

		        if (num != 999) {
		          result = Integer.parseInt(in.readLine());
		          System.out.printf("Server says %d x %d = %d%n",
		                            num,
		                            num,
		                            result);
		        }
		      } while (num!=999);
		      System.out.println("Client shutdown");
		      server.close();
		  }catch (UnknownHostException e) {
		      System.err.println("Unknown host: "+servername);
		      System.err.println(e);
		      System.exit(1);
		  }catch (IOException e) {
		      System.err.println(e);
		      System.exit(1);
		  }
		  
	}
}
