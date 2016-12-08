package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        final EditText author = (EditText) findViewById(R.id.add_book_author_edittext);
        author.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(author.getText().toString().length() == 0 && !hasFocus){
                    author.setError("Veuillez remplir ce champ.");
                }
            }
        });

        final EditText title = (EditText) findViewById(R.id.add_book_name_edittext);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(title.getText().toString().length() == 0 && !hasFocus){
                    title.setError("Veuillez remplir ce champ.");
                }
            }
        });

        final EditText isbn = (EditText) findViewById(R.id.add_book_isbn_edittext);
        isbn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(isbn.getText().toString().length() != 13 && !hasFocus){
                    isbn.setError("L'ISBN doit contenir 13 chiffres.");
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ajouter un livre");
    }

    public void validateNewBook(View view) {
        EditText author = (EditText) findViewById(R.id.add_book_author_edittext);
        boolean hasError = false;

        if(author.getText().toString().length() == 0){
            hasError = true;
            author.setError("Veuillez remplir ce champ.");
        }

        EditText title = (EditText) findViewById(R.id.add_book_name_edittext);

        if(title.getText().toString().length() == 0){
            hasError = true;
            title.setError("Veuillez remplir ce champ.");
        }

        EditText isbn = (EditText) findViewById(R.id.add_book_isbn_edittext);

        if(isbn.getText().toString().length() != 13) {
            hasError = true;
            isbn.setError("L'ISBN doit contenir 13 chiffres.");
        }

        if(!hasError) {
            Book newBook = new Book(author.getText().toString(), title.getText().toString(), isbn.getText().toString());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("newBook", newBook);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
