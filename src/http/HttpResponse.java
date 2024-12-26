package http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private final BufferedWriter writer;
    private int status = 200;

    public HttpResponse(BufferedWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("Writer cannot be null");
        }
        this.writer = writer;
    }

    public HttpResponse setStatus(int status) {
        if (status < 100 || status > 599) {
            throw new IllegalArgumentException("Invalid status code");
        }
        this.status = status;
        return this;
    }

    public void sendString(String data) throws IOException {
        writer.write("HTTP/1.1 " + status + " OK\r\n");
        writer.write("Content-Type: text/html\r\n");
        writer.write("Content-Length: " + data.length() + "\r\n");
        writer.write("\r\n");
        writer.write(data);
        writer.flush();
    }

    public void sendJson(String jsonString) throws IOException {
        byte[] jsonDataBytes = jsonString.getBytes(StandardCharsets.UTF_8);

        writer.write("HTTP/1.1 " + status + " OK\r\n");
        writer.write("Content-Type: application/json; charset=UTF-8\r\n");
        writer.write("Content-Length: " + jsonDataBytes.length + "\r\n");
        writer.write("\r\n");
        writer.write(jsonString);
        writer.flush();
    }
}