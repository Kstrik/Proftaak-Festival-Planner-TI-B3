package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class URL {

    private String urlString;
    private URL url;
    private BufferedReader reader;

    public URL(String base, BufferedReader reader) throws IOException {
        this.urlString = base;
        this.url = new URL(urlString, reader);
        this.reader = new BufferedReader(reader,1);
    }

    public String Request(String uri) {
        this.urlString += uri;
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        return this.urlString;
    }

    public URL getUrl() {
        return url;
    }

    public String getUrlString() {
        return urlString;
    }
}
