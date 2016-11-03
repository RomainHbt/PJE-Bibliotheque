package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

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

        // Supply index input as an argument.
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

        EditText author = (EditText) myInflatedView.findViewById(R.id.book_details_author_edittext);
        author.setText(book.getAuthor());
        EditText title = (EditText) myInflatedView.findViewById(R.id.book_details_title_edittext);
        title.setText(book.getTitle());
        EditText isbn = (EditText) myInflatedView.findViewById(R.id.book_details_isbn_edittext);
        isbn.setText(book.getIsbn());
        Button button = (Button) myInflatedView.findViewById(R.id.book_details_delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookList.books.remove(book);
                BookList.dataSource.deleteBook(book);
                BookList.updateBookList();
                BookFragment.adapter.notifyDataSetChanged();
            }
        });

        return myInflatedView;
    }
}
