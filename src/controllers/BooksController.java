package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;

import com.google.gson.Gson;
import http.HttpRequest;
import http.HttpResponse;
import models.Book;

public class BooksController {
    private static Gson gson = new Gson();
    private static DataBaseController dataBaseController = DataBaseController.getInstance();

    public static void getAllBooks(HttpRequest request, HttpResponse response) throws Exception {
        ArrayList<Book> books = new ArrayList<>();

        ResultSet result = dataBaseController.executeQuery("SELECT * FROM books");

        while (result.next()) {
            books.add(createBookByResultSet(result));
        }

        if (books.isEmpty()) {
            response.setStatus(200).sendJson("{\"data\": \"[]\"}");
            return;
        }

        String jsonData = gson.toJson(books);

        System.out.println("{\"data\":" + jsonData + "}");
        response.setStatus(200).sendJson("{\"data\":" + jsonData + "}");
    }

    public static void getBookById(HttpRequest request, HttpResponse response) throws Exception {
        int bookId = Integer.valueOf(request.params.get("id"));

        ResultSet result = dataBaseController.executeQuery("SELECT * FROM books WHERE id = " + bookId);

        if (result.next()) {
            Book book = createBookByResultSet(result);
            String jsonData = gson.toJson(book);

            response.sendJson("{\"data\": " + book + "}");
            return;
        }

        response.setStatus(200).sendJson("{\"data\": \"undefined\"}");
    }

    public static void createBook(HttpRequest request, HttpResponse response) throws Exception {
        String title = request.body.get("title");
        String totalPages = request.body.get("totalpages");

        if (title == null || totalPages == null) {
            response.setStatus(400).sendString("Invalid data");
            return;
        }

        try {
            ResultSet resultSet = dataBaseController.executeQuery("INSERT INTO books (title, totalpages, readpages) VALUES ('" + title + "', " + totalPages + ", 0) RETURNING id");
            if (resultSet.next()) {
                int createdBookId = resultSet.getInt(1);
                response.setStatus(201).sendJson("{\"data\": {" + "\"id\":" + createdBookId + "}}");
            } else {
                throw new Exception("Book creation failed, no ID obtained.");
            }
        }
        catch(Exception e) {
            response.setStatus(400).sendString("Invalid data");
        }
    }

    public static void updateBook(HttpRequest request, HttpResponse response) throws Exception {
        int bookId = Integer.valueOf(request.params.get("id"));
        String title = request.body.get("title");
        String totalPages = request.body.get("totalpages");
        String readPages = request.body.get("readpages");

        if (title == null || totalPages == null || readPages == null) {
            response.setStatus(400).sendString("Empty data");
            return;
        }

        try {
            dataBaseController.executeQuery("UPDATE books b SET title = '" + title + "', totalpages = " + totalPages + ", readPages = " + readPages + " WHERE b.id = " + bookId + " RETURNING *");
            response.setStatus(200).sendString("Book updated successfully");
        } catch (Exception e) {
            response.setStatus(400).sendString("Invalid data");
        }
    }

    public static void deleteBook(HttpRequest request, HttpResponse response) throws Exception {
        int bookId = Integer.valueOf(request.params.get("id"));

        try {
            dataBaseController.executeQuery("DELETE FROM books WHERE id = " + bookId + " RETURNING id");
            response.setStatus(200).sendString("Book deleted successfully");
        } catch (SQLException e) {
            response.setStatus(400).sendString("Book not found");
        }
    }

    private static Book createBookByResultSet(ResultSet result) throws Exception {
        return new Book(
                result.getInt("id"),
                result.getString("title"),
                result.getInt("totalpages"),
                result.getInt("readpages")
        );
    }
}
