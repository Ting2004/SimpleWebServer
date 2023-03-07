package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(80);
        webServer.run();
    }
}
