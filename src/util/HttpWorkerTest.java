package util;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class HttpWorkerTest {

    @org.junit.jupiter.api.Test
    void connect() {
        //HttpWorker.connect();
        //if(HttpWorker.socket.isConnected()){
            //assertEquals(true, HttpWorker.socket.isBound() );
        //}
    }

    @org.junit.jupiter.api.Test
    void getBrowserRequest() throws IOException, ParseException {
        String result ="/";
        //InputStreamReader inputStreamReader = new InputStreamReader(HttpWorker.socket.getInputStream());
        assertEquals(result, HttpWorker.getBrowserRequest());
    }

    @org.junit.jupiter.api.Test
    void readHTML() {
    }

    @org.junit.jupiter.api.Test
    void writeData() throws IOException {
        //HttpWorker.connect();
        assertEquals("hello world! Running bitcoin!!!".getBytes(), HttpWorker.socket.getInputStream().readAllBytes());
    }
}