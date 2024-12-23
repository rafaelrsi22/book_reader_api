package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    public final String method;
    public final String path;
    public final Map<String, String> params;
    public final Map<String, String> headers;

    public HttpRequest(String method, String path, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.params = new HashMap<>();
    }

    public static HttpRequest fromBufferReader(BufferedReader reader) throws IOException, Exception {
        String firstLine = reader.readLine();
        System.out.println(firstLine);

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

        HttpRequest request = new HttpRequest(method, path, headers);
        request.parseParams(path);
        return request;
    }

    public void parseParams(String routePath) {
        String regex = routePath.replaceAll(":[^/]+", "([^/]+)");
        Pattern paramPattern = Pattern.compile(regex);
        Matcher paramMatcher = paramPattern.matcher(this.path);

        if (paramMatcher.matches()) {
            List<String> paramNames = new ArrayList<>();
            Pattern namePattern = Pattern.compile(":[^/]+");
            Matcher nameMatcher = namePattern.matcher(routePath);
            while(nameMatcher.find()){
                paramNames.add(nameMatcher.group().substring(1));
            }

            for(int i = 0; i < paramNames.size(); i++){
                params.put(paramNames.get(i), paramMatcher.group(i+1));
            }
        }
    }
}