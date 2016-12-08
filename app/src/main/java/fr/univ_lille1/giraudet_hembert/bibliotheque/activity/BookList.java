package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.database.BooksDataSource;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class BookList extends AppCompatActivity {

    public static List<Book> books = new ArrayList<>();
    public static List<Map<String, String>> listOfBook = new ArrayList<>();
    public static BooksDataSource dataSource;
    static final int ADD_BOOK_REQUEST = 1;
    static final int SIMPLE_SEARCH_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new BooksDataSource(this);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booklist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.simple_search:

                return true;

            case R.id.avanced_search:

                return true;

            case R.id.barcode_search:

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
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
    public static void updateBookList() {
        listOfBook.clear();

        for (Book book : books) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            listOfBook.add(bookMap);
        }
    }

    /**
     * Fonction démarrant l'activité d'ajout de livre
     * Appelée lors de l'appui sur le bouton "+" en bas à droite de la liste
     * @param view
     */
    public void addNewBook(View view) {
        Intent intent = new Intent(BookList.this, AddBook.class);
        startActivityForResult(intent, ADD_BOOK_REQUEST);
    }

    /**
     * Fonction démarrant l'activité de recherche simple
     * Appelée lors de l'appui sur "Recherche simple" dans le menu contextuel
     * @param menuItem
     */
    public boolean goSimpleSearch(MenuItem menuItem) {
        Intent intent = new Intent(BookList.this, SearchForm.class);
        startActivityForResult(intent, SIMPLE_SEARCH_REQUEST);
        return true;
    }

    /**
     * Appelée lors du retour à cette activité lors de la fin d'une autre activité
     * @param requestCode Code envoyé depuis l'ancienne activité
     * @param resultCode Code de résultat
     * @param data Intent de l'ancienne activité
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Ajout d'un livre
        if (requestCode == ADD_BOOK_REQUEST) {
            if (resultCode == RESULT_OK) {
                Book newBook = (Book) data.getExtras().get("newBook");
                if(!books.contains(newBook)){
                    books.add(newBook);
                    dataSource.createBook(newBook);
                    updateBookList();
                } else {
                    // Popup avertissant de l'existance du livre
                }
            }
        }
    }

}
