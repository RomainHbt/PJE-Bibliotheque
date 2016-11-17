package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

    protected long id;
    protected String authors;
    protected String title;
    protected String isbn;
    protected String series;
    protected List<Integer> genreId;
    protected String editor;
    protected int cover;
    protected String year;
    protected String summary;
    protected List<Integer> illustrationsId;
    protected List<String> notes;

    public Book(String authors, String title, String isbn) {
        super();
        this.authors = authors;
        this.title = title;
        this.isbn = isbn;
    }

    public Book(long id, String authors, String title, String isbn, String series,
                List<Integer> genreId, String editor, int cover, String year, String summary,
                List<Integer> illustrationsId, List<String> notes) {
        super();
        this.id = id;
        this.authors = authors;
        this.title = title;
        this.isbn = isbn;
        this.series = series;
        this.genreId = genreId;
        this.editor = editor;
        this.cover = cover;
        this.year = year;
        this.summary = summary;
        this.illustrationsId = illustrationsId;
        this.notes = notes;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public List<Integer> getGenreId() {
        return genreId;
    }

    public void setGenreId(List<Integer> genreId) {
        this.genreId = genreId;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Integer> getIllustrationsId() {
        return illustrationsId;
    }

    public void setIllustrationsId(List<Integer> illustrationsId) {
        this.illustrationsId = illustrationsId;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (cover != book.cover) return false;
        if (authors != null ? !authors.equals(book.authors) : book.authors != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (series != null ? !series.equals(book.series) : book.series != null) return false;
        if (genreId != null ? !genreId.equals(book.genreId) : book.genreId != null) return false;
        if (editor != null ? !editor.equals(book.editor) : book.editor != null) return false;
        if (year != null ? !year.equals(book.year) : book.year != null) return false;
        if (summary != null ? !summary.equals(book.summary) : book.summary != null) return false;
        if (illustrationsId != null ? !illustrationsId.equals(book.illustrationsId) : book.illustrationsId != null)
            return false;
        return notes != null ? notes.equals(book.notes) : book.notes == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (series != null ? series.hashCode() : 0);
        result = 31 * result + (genreId != null ? genreId.hashCode() : 0);
        result = 31 * result + (editor != null ? editor.hashCode() : 0);
        result = 31 * result + cover;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (illustrationsId != null ? illustrationsId.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
