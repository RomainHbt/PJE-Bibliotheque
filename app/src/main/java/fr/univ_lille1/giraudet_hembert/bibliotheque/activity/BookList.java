package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.database.BooksDataSource;
import fr.univ_lille1.giraudet_hembert.bibliotheque.fragment.BookFragment;
import fr.univ_lille1.giraudet_hembert.bibliotheque.fragment.DetailsFragment;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class BookList extends AppCompatActivity {

    //À changer
    public List<Book> books = new ArrayList<>();
    public List<Map<String, String>> listOfBook = new ArrayList<>();
    public BooksDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new BooksDataSource(this);
        setContentView(R.layout.activity_book_list);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //dataSource.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        //dataSource = new BooksDataSource(this);
    }

    @Override
    protected void onDestroy() {
        dataSource.close();
        super.onDestroy();
    }

    /**
     * Met à jour la liste des livres
     */
    public void updateBookList() {
        listOfBook.clear();

        for (Book book : books) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", book.getAuthors());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            listOfBook.add(bookMap);
        }
    }



}
