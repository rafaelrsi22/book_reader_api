package routes;

import controllers.BooksController;
import controllers.HttpController;
import models.Book;

import java.util.ArrayList;

public class BooksRouter {
    public static void init() {
        HttpController.get("/books", BooksController::getAllBooks);
        HttpController.get("/books/:id", BooksController::getBookById);
        HttpController.post("/books", BooksController::createBook);
        HttpController.put("/books/:id", BooksController::updateBook);
        HttpController.delete("/books/:id", BooksController::deleteBook);
    }
}
