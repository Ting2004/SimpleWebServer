package com.company;

import java.io.*;
import java.util.HashMap;

public class Response {
    private final String baseDir = "E:";
    private static final HashMap<Integer, String> statusString;

    static {
        statusString = new HashMap<>();
        statusString.put(200, "200 OK");
        statusString.put(404, "404 not found");
        statusString.put(500, "500 Server internal error");
    }

    //process content
    public String processRequest(Request request) throws IOException {
        //construct file and file path
        String fileFullPath = baseDir + request.getRequestURL();
        File file = new File(fileFullPath);
        try {
            //if url is fs
            if (request.getRequestURL().equals("/fs")) {
                return writeResponse(200, retrieveDir());
            }
            //else if file exists
            else if (file.exists()) {
                return writeResponse(200, retrieveFile(fileFullPath));
            }
            //else error
            else {
                return writeResponse(404, String.format("cannot find %s", fileFullPath));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return writeResponse(500, e.getMessage());
        }
    }


    public String retrieveDir() {
        //read directory
        StringBuilder tmp = new StringBuilder();
        File file = new File(baseDir + "/");
        tmp.append("<html lang='zh-CN'>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "</head>" +
                "<body>");
        for (File listFile : file.listFiles()) {
            tmp.append(String.format("<a href=\'%s\'>", listFile.getName()));
            tmp.append(listFile.getAbsolutePath());
            tmp.append("</a>");
            tmp.append("<br>");
        }
        tmp.append("</body></html>");
        return tmp.toString();
    }

    public String retrieveFile(String fileName) throws IOException {
        //read file
        String tmp = "<html lang='zh-CN'>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "</head>" +
                "<body>" + getFile(fileName) +
                "</body>" +
                "</html>";
        return tmp;
    }

    //write response
    public String writeResponse(Integer status, String tmp) {
        StringBuilder html = new StringBuilder();
        html.append(String.format("HTTP/1.1 %s \r\n", statusString.get(status)));
        html.append("Server: TingServer/2. 2.22 (Win32) JAVA/5.3. 13\r\n");
        html.append(String.format("Content-Length: %d\r\n", tmp.length()));
        html.append("Content-Type: text/html\r\n");
        html.append("\r\n");
        html.append(tmp);

        return html.toString();

    }

    public String getFile(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
