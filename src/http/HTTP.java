package http;

import java.io.IOException;
import java.net.HttpURLConnection;

public class HTTP {

    private HttpURLConnection http;

    public HTTP(){
        this.http = new HttpURLConnection() {
            @Override
            public void disconnect() {

            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() throws IOException {

            }
        };
    }

}
