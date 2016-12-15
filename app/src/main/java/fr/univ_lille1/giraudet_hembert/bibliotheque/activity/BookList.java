package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.content.Context;
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
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.BookCollection;

public class BookList extends AppCompatActivity {

    private static Context context;
    public static Context get() { return context; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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
        //BookCollection.dataSource.close();
        super.onDestroy();
    }

    /**
     * Fonction démarrant l'activité de recherche simple
     * Appelée lors de l'appui sur "Recherche simple" dans le menu contextuel
     * @param menuItem
     */
    public boolean goSimpleSearch(MenuItem menuItem) {
        Intent intent = new Intent(BookList.this, SearchForm.class);
        startActivityForResult(intent, 2);
        return true;
    }
}
