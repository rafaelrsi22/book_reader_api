package routes;

import controllers.BooksController;
import controllers.HttpController;
import models.Book;

public class RouteConfig {
    public static void init() {
        HttpController.get("/teste", (request, response) -> {
            response.sendString("funciona");
        });

        HttpController.get("/:id", ((request, response) -> {
//            Book myBook = BooksController.getAllBooks().get(0);
//            response.sendJson(myBook.toString());
            String id = request.params.get("id");
            response.sendString(id);
        }));
    }
}