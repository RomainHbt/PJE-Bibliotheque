package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.BookList;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

public class DetailsFragment extends Fragment {

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(BookList.books.isEmpty()) { return inflater.inflate(R.layout.book_detail, container,false); }

        final Book book = BookList.books.get(getShownIndex());

        View myInflatedView = inflater.inflate(R.layout.book_detail, container,false);


        ImageView image = (ImageView) myInflatedView.findViewById(R.id.book_details_img);
        image.setImageResource(R.mipmap.ic_launcher);
        final EditText author = (EditText) myInflatedView.findViewById(R.id.book_details_author_edittext);
        author.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(author.getText().toString().length() == 0 && !hasFocus){
                    author.setError("Veuillez remplir ce champ.");
                }
            }
        });
        author.setText(book.getAuthors());
        final EditText title = (EditText) myInflatedView.findViewById(R.id.book_details_title_edittext);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(title.getText().toString().length() == 0 && !hasFocus){
                    title.setError("Veuillez remplir ce champ.");
                }
            }
        });
        title.setText(book.getTitle());
        final EditText isbn = (EditText) myInflatedView.findViewById(R.id.book_details_isbn_edittext);
        isbn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(isbn.getText().toString().length() != 13 && !hasFocus){
                    isbn.setError("L'ISBN doit contenir 13 chiffres.");
                }
            }
        });
        isbn.setText(book.getIsbn());
        Button modify = (Button) myInflatedView.findViewById(R.id.book_details_modify_button);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasError = false;
                if(author.getText().toString().length() == 0){
                    hasError = true;
                    author.setError("Veuillez remplir ce champ.");
                }
                if(title.getText().toString().length() == 0){
                    hasError = true;
                    title.setError("Veuillez remplir ce champ.");
                }
                if(isbn.getText().toString().length() != 13) {
                    hasError = true;
                    isbn.setError("L'ISBN doit contenir 13 chiffres.");
                }
                if(!hasError) {
                    book.setAuthors(author.getText().toString());
                    book.setTitle(title.getText().toString());
                    book.setIsbn(isbn.getText().toString());
                    BookList.dataSource.updateBook(book);
                    BookList.updateBookList();
                    BookFragment.adapter.notifyDataSetChanged();
                }
            }
        });
        Button delete = (Button) myInflatedView.findViewById(R.id.book_details_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList.books.remove(book);
                BookList.dataSource.deleteBook(book);
                BookList.updateBookList();
                BookFragment.adapter.notifyDataSetChanged();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(myInflatedView.getContext(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) myInflatedView.findViewById(R.id.book_details_genre_autocomplete);
        textView.setAdapter(adapter);
        textView.setThreshold(1);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        return myInflatedView;
    }

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
}
