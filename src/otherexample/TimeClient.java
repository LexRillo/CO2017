package otherexample;

import java.net.*;
import java.io.*;

public class TimeClient {
  public static void main (String args[]) throws IOException {
    Socket server = new Socket("143.210.72.82", 8080);
    // Socket server = new Socket("pc90.mcs.le.ac.uk", 1236);
    // Socket server = new Socket("serverDNS", 1236);
    System.out.println("Connected to " + server.getInetAddress());
    BufferedReader in = 
      new BufferedReader(new InputStreamReader(server.getInputStream(),
                                               "UTF-8"));

    String date = in.readLine();

    System.out.println("Server said: " + date);
  }
}
