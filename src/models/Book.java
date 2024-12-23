package models;

import util.MathUtil;

public class Book implements Entity {
    private final int id;
    private String name;
    private int totalPages;
    private int readPages;
    private Category category = Category.NONE;

    public Book(int id, String name, int totalPages, int readPages) {
        this.id = id;
        this.name = name;
        this.totalPages = totalPages;
        this.readPages = readPages;
    }

    public Book(int id, String name, int totalPages, int readPages, Category category) {
        this.id = id;
        this.name = name;
        this.totalPages = totalPages;
        this.readPages = readPages;
        this.category = category;
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

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getReadPages() {
        return this.readPages;
    }

    public void setReadPages(int readPages) {
        this.readPages = readPages;
    }

    public double getProgress() {
        return MathUtil.roundAvoid((double) this.readPages / this.totalPages, 3);
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{\"id\": \""+ this.id + "\", \"name\": \" " + this.name + "\", \"totalPages\": \"" + this.totalPages + "\", \"readPages\": \"" + this.readPages + "\", \"progress\": \"" + this.getProgress() + "\", \"category\": \"" + this.category.getName() + "\"}";
    }
}