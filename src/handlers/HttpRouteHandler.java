package handlers;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

@FunctionalInterface
public interface HttpRouteHandler {
    public void handle(HttpRequest request, HttpResponse response) throws Exception;
}