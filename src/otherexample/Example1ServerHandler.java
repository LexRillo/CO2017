package otherexample;
import java.io.*;

class Server extends Thread {
  public void run() {
    while (true) { 
      System.out.println("waiting for a new task");
      try { 
          System.in.read();
      } catch(IOException e) { e.printStackTrace(); }
      // create and start a handler
      Handler h = new Handler(); h.start(); 
    }
  }
}

class Handler extends Thread {
    private int n;
    Handler () {
        n = 0;
    }

    public void run() {
        System.out.println(n++);
    }
}

public class Example1ServerHandler {
    
    public static void main(String[] args) { 
        Server server = new Server(); 
        server.start(); 
    }
}
