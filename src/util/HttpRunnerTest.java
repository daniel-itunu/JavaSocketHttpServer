package util;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRunnerTest {

    @org.junit.jupiter.api.Test
    void connect() {
        //HttpRunner.connect();
        //if(HttpRunner.socket.isConnected()){
            //assertEquals(true, HttpRunner.socket.isBound() );
        //}
    }

    @org.junit.jupiter.api.Test
    void getBrowserRequest() throws IOException, ParseException {
        String result ="/";
        //InputStreamReader inputStreamReader = new InputStreamReader(HttpRunner.socket.getInputStream());
        assertEquals(result, HttpRunner.getBrowserRequest());
    }

    @org.junit.jupiter.api.Test
    void readHTML() {
    }

    @org.junit.jupiter.api.Test
    void writeData() throws IOException {
        //HttpRunner.connect();
        assertEquals("hello world! Running bitcoin!!!".getBytes(), HttpRunner.socket.getInputStream().readAllBytes());
    }
}