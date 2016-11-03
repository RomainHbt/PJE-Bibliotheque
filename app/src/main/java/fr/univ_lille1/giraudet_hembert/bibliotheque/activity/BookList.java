package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.database.BooksDataSource;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class BookList extends AppCompatActivity {

    //À changer
    public static List<Book> books = new ArrayList<>();
    public static List<Map<String, String>> listOfBook = new ArrayList<>();
    public static BooksDataSource dataSource;
    static final int ADD_BOOK_REQUEST = 1;

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
     * Appelée lors de l'appui dur le bouton "+" en bas à droite de la liste
     * @param view
     */
    public void addNewBook(View view) {
        Intent intent = new Intent(BookList.this, AddBook.class);
        startActivityForResult(intent, ADD_BOOK_REQUEST);
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
