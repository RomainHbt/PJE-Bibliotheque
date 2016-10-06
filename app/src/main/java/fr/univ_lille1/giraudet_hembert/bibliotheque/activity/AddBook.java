package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class AddBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void validateNewBook(View view) {
        TextView author = (TextView) findViewById(R.id.add_book_author_edittext);
        TextView title = (TextView) findViewById(R.id.add_book_name_edittext);
        TextView isbn = (TextView) findViewById(R.id.add_book_isbn_edittext);
        Book newBook = new Book(author.getText().toString(), title.getText().toString(), isbn.getText().toString());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("newBook", newBook);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
