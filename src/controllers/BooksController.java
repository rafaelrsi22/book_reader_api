package controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import models.Book;

public class BooksController {
    public static ArrayList<Book> getAllBooks() throws Exception {
        DataBaseController dataBaseController = DataBaseController.getInstance();
        ArrayList<Book> books = new ArrayList<>();

        ResultSet result = dataBaseController.executeQuery("SELECT * FROM books");

        while (result.next()) {
            books.add(createBookByResultSet(result));
        }

        return books;
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
