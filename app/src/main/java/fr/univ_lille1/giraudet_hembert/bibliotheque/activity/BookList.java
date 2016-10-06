package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.BookCollection;

public class BookList extends AppCompatActivity {

    //À changer
    public BookCollection books = new BookCollection();
    static final int ADD_BOOK_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        books.add(new Book("Giraudet", "Tondeuse", "123"));
        books.add(new Book("Hembert", "Voiture", "456"));
        books.add(new Book("Cuvilliers", "Dormir", "656"));

        updateBookList();
    }

    /**
     * Met à jour la liste des livres
     */
    public void updateBookList() {
        // Recherche la vue affichant la liste
        ListView bookList = (ListView) findViewById(R.id.booklist);
        List<Map<String, String>> listOfBook = new ArrayList<Map<String, String>>();

        for (Book book : books.getBooks()) {
            Map<String, String> bookMap = new HashMap<String, String>();
            bookMap.put("img", String.valueOf(R.mipmap.ic_launcher)); // use available img
            bookMap.put("author", book.getAuthor());
            bookMap.put("title", book.getTitle());
            bookMap.put("isbn", book.getIsbn());
            listOfBook.add(bookMap);
        }

        // Cree un adapter faisant le lien entre la liste d'élément et la ListView servant à l'affichage.
        SimpleAdapter listAdapter = new SimpleAdapter(this.getBaseContext(), listOfBook, R.layout.book_detail,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});
        //Associe l’adapter et le ListView
        bookList.setAdapter(listAdapter);
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
                    updateBookList();
                } else {
                    // Popup avertissant de l'existance du livre
                }
            }
        }
    }

}
