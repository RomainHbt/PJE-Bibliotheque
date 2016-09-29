package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Recherche la vue affichant la liste
        ListView bookList = (ListView) findViewById(R.id.booklist);
        // Crée la liste demandée par SimpleADapter
        BookCollection books = new BookCollection();

        books.add(new Book("Giraudet", "Tondeuse", "123"));
        books.add(new Book("Hembert", "Voiture", "456"));
        books.add(new Book("Cuvilliers", "Dormir", "656"));
        books.add(new Book("Giraudet", "Tondeuse", "123"));
        books.add(new Book("Hembert", "Voiture", "456"));
        books.add(new Book("Cuvilliers", "Dormir", "656"));
        books.add(new Book("Giraudet", "Tondeuse", "123"));
        books.add(new Book("Hembert", "Voiture", "456"));
        books.add(new Book("Cuvilliers", "Dormir", "656"));
        books.add(new Book("Giraudet", "Tondeuse", "123"));
        books.add(new Book("Hembert", "Voiture", "456"));
        books.add(new Book("Cuvilliers", "Dormir", "656"));

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


}
