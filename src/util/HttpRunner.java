package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpRunner {
    private static final Integer PORT = 8080;
    public static ServerSocket serverSocket;
    public static Socket socket;
    public static OutputStream outputStream;
    private static InputStreamReader inputStreamReader;
    private static String _HTML;
    private static String JSON;

    public HttpRunner(){
    }

    /**
     * Does server connection and wait for acceptance
     */
    public static void run() {
        new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("listening for connection...");
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        writeData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * Gets brwoser requests
     */
    public static String getBrowserRequest() throws IOException, ParseException {
        StringBuffer stringBuffer;
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        stringBuffer = new StringBuffer();
        while (!line.isEmpty()) {
            System.out.println(line);
            stringBuffer.append(line);
            line = reader.readLine();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(stringBuffer.toString(), " ");
        while (stringTokenizer.hasMoreTokens()) {
            if (stringTokenizer.nextToken().equals("GET")) {
                String next = stringTokenizer.nextToken();
                if (next.equals("/")) {
                    _HTML = "html.html";
                    return _HTML;
                }
                if (next.equals("/json")) {
                    JSON = "json.json";
                    return JSON;
                }
                return "404.html";
            }
        }
        return "";
    }

    /**
     * Reads data from file system
     */
    public static final byte[] readData() throws IOException, ParseException {
        File file = new File("./src");
        String path = file.getAbsolutePath().replace(".", "");
        String filepath = "";
        if (path.endsWith("file/")) {
            filepath = path;
        } else {
            filepath = path + "/file/";
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath+getBrowserRequest()));
        StringBuffer stringBuffer= new StringBuffer();
        String line = bufferedReader.readLine();
        while(line != null){
            stringBuffer.append(line);
            line = bufferedReader.readLine();
        }
        if (stringBuffer.toString().startsWith("{")){
            Object obj = new JSONParser().parse(new FileReader(filepath+"json.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String json ="";
            for (Object keyStr : jsonObject.keySet()) {
                Object keyvalue = jsonObject.get(keyStr);
                json +=  keyStr+": " +keyvalue+"\n";
            }
            final String CRLF ="\n\r";
            String response = "HTTP/1.1 200 0K"+CRLF+"content-length: "+
                    json.length()+CRLF+CRLF+json+CRLF+CRLF;
            return  response.getBytes();
        }
        final String CRLF ="\n\r";
        String response = "HTTP/1.1 200 0K"+CRLF+"content-length: "+
                stringBuffer.toString().length()+CRLF+CRLF+stringBuffer.toString()+CRLF+CRLF;
        return response.getBytes();
    }

    /**
     * Writes data read from file system to socket
     */
    public static void writeData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write(readData());
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
