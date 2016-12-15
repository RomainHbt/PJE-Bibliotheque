package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.BookList;
import fr.univ_lille1.giraudet_hembert.bibliotheque.adapters.BookAdapter;
import fr.univ_lille1.giraudet_hembert.bibliotheque.database.BooksDataSource;

/**
 * Created by giraudet on 01/12/16.
 */
public class BookCollection extends ArrayList<Book> {
    private static BookCollection ourInstance = new BookCollection();

    private static BooksDataSource dataSource;
    private static BaseAdapter adapter;

    public static BookCollection getInstance() {
        return ourInstance;
    }

    private BookCollection() {
        super();
        dataSource = new BooksDataSource(BookList.get());
        addAll(dataSource.getAllBooks());
        adapter = new BookAdapter(BookList.get(), this);
    }

    public Book getByIsbn(String isbn) {
        for(Book b : this) {
            if(b.getIsbn().equals(isbn))
                return b;
        }
        return null;
    }

    @Override
    public boolean add(Book b) {
        if(getByIsbn(b.getIsbn()) != null)
            return false;
        boolean ret = super.add(b);
        dataSource.createBook(b);
        adapter.notifyDataSetChanged();
        return ret;
    }

    public void update(Book b1, Book b2) {
        super.set(super.indexOf(b1), b2);
        dataSource.updateBook(b2);
        adapter.notifyDataSetChanged();
    }

    public boolean remove(Book b) {
        boolean ret = super.remove(b);
        dataSource.deleteBook(b);
        adapter.notifyDataSetChanged();
        return ret;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }
}
