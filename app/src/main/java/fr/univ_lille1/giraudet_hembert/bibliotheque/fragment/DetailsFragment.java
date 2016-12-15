package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import java.io.InputStream;
import java.net.URL;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.BookCollection;

public class DetailsFragment extends Fragment {

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DetailsFragment newInstance(int index, BookFragment list) {
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
        BookCollection collec = BookCollection.getInstance();

        if(getShownIndex() < 0 || collec.size() == 0) { return inflater.inflate(R.layout.book_detail, container,false); }

        final Book book = collec.get(getShownIndex());

        View myInflatedView = inflater.inflate(R.layout.book_detail, container,false);

        final ImageView imageView = (ImageView) myInflatedView.findViewById(R.id.book_details_img);

        if(book.getImageUrl() == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            final Bitmap[] bmp = new Bitmap[1];
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(book.getImageUrl()).openStream();
                        bmp[0] = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        // log error
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp[0] != null) {
                        imageView.setImageBitmap(bmp[0]);
                    } else {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                    }
                }

            }.execute();
        }

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
        EditText year = (EditText) myInflatedView.findViewById(R.id.book_details_year_edittext);
        year.setText(book.getYear());
        EditText editor = (EditText) myInflatedView.findViewById(R.id.book_details_editor_edittext);
        editor.setText(book.getEditor());
        EditText summary = (EditText) myInflatedView.findViewById(R.id.book_details_summary_edittext);
        summary.setText(book.getSummary());
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
                    Book newBook = new Book(author.getText().toString(), title.getText().toString(), isbn.getText().toString());
                    BookCollection.getInstance().update(book,newBook);
                }
            }
        });
        Button delete = (Button) myInflatedView.findViewById(R.id.book_details_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookCollection.getInstance().remove(book);
                getActivity().finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(myInflatedView.getContext(),
                android.R.layout.simple_dropdown_item_1line, GENRE);
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) myInflatedView.findViewById(R.id.book_details_genre_autocomplete);
        textView.setAdapter(adapter);
        textView.setThreshold(1);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        return myInflatedView;
    }

    private static final String[] GENRE = new String[] {
            "Science-Fiction", "Fantasy", "Thriller", "Essai", "Polar"
    };
}
