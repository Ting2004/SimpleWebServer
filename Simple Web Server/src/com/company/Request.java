package com.company;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<String> header = null;
    private Socket socket;
    public String getRequestURL() {
        return requestURL;
    }
    public Request(Socket socket){
        this.socket=socket;
    }

    private String requestURL = null;

    public void read() throws Exception {
        this.header = new ArrayList<>();
        //construct reader
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //begin to read data
        String msg = br.readLine();
        //check if socket close
        if (msg == null)
            throw new Exception();
        //read all data
        while (!msg.equals("")) {
            this.header.add(msg);
            //gey requestURL
            if (msg.startsWith("GET")) {
                requestURL = msg.substring(4, msg.length() - 8).trim();
            }
            msg = br.readLine();
        }
    }

    public List<String> getHeader() {
        return header;
    }
}
