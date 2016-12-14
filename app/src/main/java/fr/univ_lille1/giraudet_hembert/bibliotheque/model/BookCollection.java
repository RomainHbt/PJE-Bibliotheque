package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.BookList;
import fr.univ_lille1.giraudet_hembert.bibliotheque.database.BooksDataSource;

/**
 * Created by giraudet on 01/12/16.
 */
public class BookCollection extends ArrayList<Book> {
    private static BookCollection ourInstance = new BookCollection();

    private static BooksDataSource dataSource;
    private static SimpleAdapter adapter;
    private static List<Map<String, String>> mapList;

    public static BookCollection getInstance() {
        return ourInstance;
    }

    private BookCollection() {
        super();
        dataSource = new BooksDataSource(BookList.get());
        addAll(dataSource.getAllBooks());
        mapList = new ArrayList<>();
        adapter = new SimpleAdapter(BookList.get(), mapList, R.layout.book_list_item,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});
        updateMapList();
    }

    private void updateMapList() {
        mapList.clear();

        for (Book book : this) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher));
            bookMap.put("author", book.getAuthors());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            mapList.add(bookMap);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean add(Book b) {
        boolean ret = super.add(b);
        dataSource.createBook(b);
        updateMapList();
        return ret;
    }

    public void update(Book b1, Book b2) {
        super.set(super.indexOf(b1), b2);
        dataSource.updateBook(b2);
        updateMapList();
    }

    public boolean remove(Book b) {
        boolean ret = super.remove(b);
        dataSource.deleteBook(b);
        updateMapList();
        return ret;
    }

    public SimpleAdapter getAdapter() {
        return adapter;
    }
}
