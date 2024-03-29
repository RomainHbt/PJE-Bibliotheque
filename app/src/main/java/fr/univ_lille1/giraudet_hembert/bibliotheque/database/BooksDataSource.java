package fr.univ_lille1.giraudet_hembert.bibliotheque.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

/**
 * Created by romainhembert on 03/11/2016.
 */

public class BooksDataSource {

    private SQLiteDatabase database;
    private DbHelper helper;
    private String[] allColumns = {DbHelper.COLUMN_ID, "author", "title", "isbn", "description", "imageUrl"};

    public BooksDataSource(Context context) {
        helper = new DbHelper(context);
        this.open();
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    /**
     * Crée un nouvel objet en BDD
     *
     * @param book Le livre à ajouter
     * @return L'ID de l'objet inséré
     */
    public long createBook(Book book) {
        // Crée un objet ContentValue utilisé pour transporter des valeurs
        ContentValues values = new ContentValues();
        values.put("author", book.getAuthors());
        values.put("title", book.getTitle());
        values.put("isbn", book.getIsbn());
        values.put("description", book.getSummary());
        values.put("imageUrl", book.getImageUrl());

        // Insert la nouvelle valeur dans la DB. Recupere l'ID en retour
        long insertId = database.insert(DbHelper.TABLE_BOOKS, null, values);
        Log.d("Log: ", ""+insertId);
        book.setId(insertId);
        return insertId;
    }

    /**
     * Détruit un livre de la base de données
     * @param book Livre à détruire
     */
    public void deleteBook(Book book) {
        long id = book.getId();
        System.out.println("Book deleted with id: " + id);
        database.delete(DbHelper.TABLE_BOOKS, DbHelper.COLUMN_ID + " = " + id, null);
    }

    /**
     * Modifie un livre existant
     * @param book Livre à modifier
     */
    public void updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("author", book.getAuthors());
        values.put("title", book.getTitle());
        values.put("isbn", book.getIsbn());

        String selection = DbHelper.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(book.getId()) };

        database.update(DbHelper.TABLE_BOOKS, values, selection, selectionArgs);
    }

    /**
     * Retourne tous les livres de la BDD
     * @return une liste de Book
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        Cursor cursor = database.query(DbHelper.TABLE_BOOKS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return books;
    }

    /**
     * Transforme un objet Cursor en Book
     * @param cursor objet Cursor à transformer
     * @return Book venant du cursor
     */
    private Book cursorToBook(Cursor cursor) {
        Book comment = new Book(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), null, null, null, 0, null, cursor.getString(4), null, null, cursor.getString(5));
        return comment;
    }

}
