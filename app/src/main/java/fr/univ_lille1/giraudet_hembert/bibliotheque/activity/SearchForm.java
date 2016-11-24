package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Search;

public class SearchForm extends AppCompatActivity {

    public static List<Book> books = new ArrayList<>();
    public static List<Map<String, String>> listOfBook = new ArrayList<>();
    public static String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText searchField = (EditText) findViewById(R.id.searchField);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    search = v.getText().toString();
                    sendSearch();
                    /*if(getCurrentFocus()!=null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }*/

                    return true;
                }
                return false;
            }
        });
    }

    public void sendSearch(){
        Search s = new Search(this, search);
        s.exec();
    }

    /**
     * Met à jour la liste des livres
     */
    public static void updateBookList(List<Book> res) {
        listOfBook.clear();

        books = res;

        for (Book book : res) {
            Log.d("Book", book.toString());
            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            listOfBook.add(bookMap);
        }
    }

}
