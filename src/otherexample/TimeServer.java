package otherexample;

import java.net.*;
import java.io.*;
import java.util.*;  // Date class

public class TimeServer {
  public static void main (String args[]) throws IOException {
    int port = 8080;
    ServerSocket server = new ServerSocket(port);

    while (true) {
      System.out.println("Waiting for client...");
      Socket client = server.accept();

      System.out.println("Client from "+client.getInetAddress()+" connected.");
      Writer out = new OutputStreamWriter(client.getOutputStream());
      Date date = new Date();
      out.write(String.format("%s%n",date));
      out.flush();
    }
  }
}

