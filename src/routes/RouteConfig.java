package routes;

import controllers.BooksController;
import controllers.HttpController;
import models.Book;

public class RouteConfig {
    public static void init() {
        HttpController.get("/", ((request, response) -> {
            Book myBook = BooksController.getAllBooks().get(0);
            response.sendJson(myBook.toString());
        }));
    }
}