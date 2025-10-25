package model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;
    private String title;
    private String author;
    private int year;

    public Book(String isbn, String title, String author, int year) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return String.format("ISBN: %s, Title: %s, Author: %s, Year: %d",
                isbn != null ? isbn : "",
                title != null ? title : "",
                author != null ? author : "",
                year);
    }
}