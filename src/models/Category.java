package models;

public class Category implements Entity {
    public static final Category ALL = new Category(0, "Todas");
    public static final Category READING = new Category(1, "Lendo");
    public static final Category READ = new Category(2, "Lido");
    public static final Category NONE = new Category(3, "Nenhuma");

    private final int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}