package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.SearchForm;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.BookCollection;

/**
 * Created by hembert on 01/12/16.
 */

public class SearchDetailsFragment extends DetailsFragment {

    private ImageView image;
    private Bitmap bmp;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static SearchDetailsFragment newInstance(int index) {
        SearchDetailsFragment f = new SearchDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Book book = SearchForm.books.get(getShownIndex());

        View myInflatedView = inflater.inflate(R.layout.activity_search_details, container,false);

        bmp = null;
        image = (ImageView) myInflatedView.findViewById(R.id.book_details_image);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(book.getImageUrl()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null) {
                    image.setImageBitmap(bmp);
                }
            }

        }.execute();

        TextView author = (TextView) myInflatedView.findViewById(R.id.book_details_author_text);
        author.setText(book.getAuthors());
        TextView title = (TextView) myInflatedView.findViewById(R.id.book_details_title_text);
        title.setText(book.getTitle());
        TextView isbn = (TextView) myInflatedView.findViewById(R.id.book_details_isbn_text);
        isbn.setText(book.getIsbn());
        TextView description = (TextView) myInflatedView.findViewById(R.id.book_details_description_text);
        description.setText(book.getSummary());

        Button btn = (Button) myInflatedView.findViewById(R.id.search_details_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookCollection.getInstance().add(book);
                getActivity().finish();
            }
        });



        return myInflatedView;
    }
}
