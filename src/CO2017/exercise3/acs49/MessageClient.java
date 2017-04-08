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
			  int UIID = 0;
		      BufferedReader in = new BufferedReader (new InputStreamReader(server.getInputStream(), "UTF-8"));

		      Writer out = new OutputStreamWriter(server.getOutputStream());

		      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		      String commando = new String();
		      boolean ended = false;
		      do {
		        System.out.print("?");
		        //read from command line
		        commando = stdin.readLine();
		        String answer;
		        if(commando.startsWith("GET:")){
		        	char Cid;
		        	int num;
		        	try{
			        	if(Character.isLetter(commando.charAt(4)){
			        		Cid = commando.substring(3, 5);
			        	}
			        	num = Integer.parseInt(commando.substring(5));
		        	}
		        	//send it to the server to find it
		        	out.write(String.format("g%c%d", Cid, num));
		        	out.flush();
		        	//read the response and print it
		        	answer = in.readLine();
		        	printf(answer);
		        	
		        }else if(commando.startsWith("SEND:")){
		        	//piece of code for the send command with the id (which is probably a typo in the instructions)
//		        	if(Character.isNumber(commando.charAt(5) && commando.charAt(6).equals(':')){
//		        		num = commando.charAt(5);
//		        		String body = commando.substring(6);
//		        		//send it
//		        	}else{}
		        	//getting the body
	        		String body = commando.substring(4);
	        		UIID++;
	        		//sending it to the server
	        		out.write(String.format("s%d%s", UIID, body));
		        	out.flush();
		        	
		        }else if(commando.equalsIgnoreCase("LIST:")){
		        	//sending the command to the server
		        	out.write("l");
		        	out.flush();
		        	answer= in.readLine();
		        	printf(answer);
		        	
		        }else if(commando.equalsIgnoreCase("BYE")){
		        	//end the loop and end the connection
		        	ended = true;
		        }else{
		        	//nothing here. Move along.
		        }
		      } while (ended == true);
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
