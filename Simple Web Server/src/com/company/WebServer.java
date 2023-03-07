package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private final int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //begin to listen
        System.out.println("== server start ==");
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            //waiting for connection
            Socket socket = serverSocket.accept();
            System.out.println("connected");
            Request request = new Request(socket);
            Response response = new Response();
            while (true) {
                try {
                    //read request
                    request.read();
                    //write response
                    write(socket, response.processRequest(request));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }
    }


    public void write(Socket socket, String msg) throws IOException {
        OutputStream os = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        bw.write(msg);
        bw.flush();
    }


}
