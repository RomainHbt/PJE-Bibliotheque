package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

/**
 * Created by hembert on 29/09/16.
 */

public class Book {

    protected String author;
    protected String title;
    protected String isbn;

    /**
     * @param author
     * @param title
     * @param isbn
     */
    public Book(String author, String title, String isbn) {
        super();
        this.author = author;
        this.title = title;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
