package controllers;

import handlers.HttpRouteHandler;
import http.HttpRequest;
import http.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpController {
    private static final Map<String, HttpRouteHandler> routes = new HashMap<>();

    public static void get(String path, HttpRouteHandler handler) {
        routes.put("GET " + path, handler);
    }

    public static void post(String path, HttpRouteHandler handler) {
        routes.put("POST " + path, handler);
    }

    public static void put(String path, HttpRouteHandler handler) {
        routes.put("PUT " + path, handler);
    }

    public static void delete(String path, HttpRouteHandler handler) {
        routes.put("DELETE " + path, handler);
    }

    public static Thread serve(int port) {
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error at serving server API");
            }
        });
        serverThread.start();
        return serverThread;
    }

    private static void handleClient(Socket clientSocket) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        HttpRequest request;
        HttpResponse response = new HttpResponse(writer);

        try {
            request = HttpRequest.fromBufferReader(reader);
        } catch(Exception e) {
            return;
        }

        handleRoute(request.method, request.path, request, response);

        reader.close();
        writer.close();
        clientSocket.close();
    }

    private static void handleRoute(String method, String path, HttpRequest request, HttpResponse response) throws Exception {
        HttpRouteHandler handler = routes.get(method + " " + path);

        System.out.println(request.method + " " + request.path);

        if (handler != null) {
            handler.handle(request, response);
        } else {
            response.setStatus(404).sendString("404 Not Found");
        }
    }
}
