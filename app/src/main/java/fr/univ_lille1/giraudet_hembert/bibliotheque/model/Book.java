package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import java.io.Serializable;

/**
 * Created by hembert on 29/09/16.
 */

public class Book implements Serializable {

    protected long id;
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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (getAuthor() != null ? getAuthor().hashCode() : 0);
        hash = 59 * hash + (getTitle() != null ? getTitle().hashCode() : 0);
        hash = 59 * hash + (getIsbn() != null ? getIsbn().hashCode() : 0);
        return hash;
    }
}
