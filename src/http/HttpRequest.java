package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public final String method;
    public final String path;
    Map<String, String> headers;

    public HttpRequest(String method, String path, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.headers = headers;
    }

    public static HttpRequest fromBufferReader(BufferedReader reader) throws IOException, Exception {
        String firstLine = reader.readLine();

        if (firstLine == null) {
            throw new Exception("Empty request");
        }

        String method = firstLine.split(" ")[0];
        String path = firstLine.split(" ")[1];

        Map<String, String> headers = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }

        return new HttpRequest(method, path, headers);
    }
}