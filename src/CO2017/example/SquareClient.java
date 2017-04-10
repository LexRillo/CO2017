package CO2017.example;

import java.net.*;
import java.io.*;

public class SquareClient {

  public static void main (String args[]) {
    // Suggested code to read in arguments from command line
    // if (args.length != 3) {
    //   System.err.println
    //     ("Usage: java SquareClient <host> <port>");
    //   System.exit(1);
    // }
    // String servername = args[0];
    // int port = Integer.parseInt(args[1]);

    String servername = "localhost";
    int port = 8080;

    try (Socket server = new Socket(servername, port)) {
      System.out.println("Connected to " + server.getInetAddress());

      BufferedReader in =
        new BufferedReader (
          new InputStreamReader(server.getInputStream(),
                                "UTF-8"));

      Writer out =
        new OutputStreamWriter(server.getOutputStream());

      BufferedReader stdin =
        new BufferedReader(new InputStreamReader(System.in));

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
    }
    catch (UnknownHostException e) {
      System.err.println("Unknown host: "+servername);
      System.err.println(e);
      System.exit(1);
    }
    catch (IOException e) {
      System.err.println(e);
      System.exit(1);
    }
  }
}