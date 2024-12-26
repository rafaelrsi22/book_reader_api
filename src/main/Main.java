package main;

import controllers.DataBaseController;
import controllers.HttpController;
import routes.BooksRouter;

public class Main {
    public static final int SERVER_PORT = 3000;
    public static final String DATABASE_HOST = "localhost";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "bookdb";
    public static final String DATABASE_USER = "admin";
    public static final String DATABASE_PASSWORD = "admin";

    public static void main(String[] args) {
        DataBaseController dataBaseController = DataBaseController.getInstance();

        try {
            dataBaseController.connect(DATABASE_HOST, DATABASE_PORT, DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
            HttpController.serve(SERVER_PORT);
            BooksRouter.init();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error at starting server API");
        }
    }
}