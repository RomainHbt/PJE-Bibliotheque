package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.BookList;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.SearchForm;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

/**
 * Created by hembert on 01/12/16.
 */

public class SearchDetailsFragment extends DetailsFragment {

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

        TextView author = (TextView) myInflatedView.findViewById(R.id.book_details_author_text);
        author.setText(book.getAuthor());
        TextView title = (TextView) myInflatedView.findViewById(R.id.book_details_title_text);
        title.setText(book.getTitle());
        TextView isbn = (TextView) myInflatedView.findViewById(R.id.book_details_isbn_text);
        isbn.setText(book.getIsbn());

        return myInflatedView;
    }
}
