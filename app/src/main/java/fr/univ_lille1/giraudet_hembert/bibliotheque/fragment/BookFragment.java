package fr.univ_lille1.giraudet_hembert.bibliotheque.fragment;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.AddBook;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.BookList;
import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.DetailsActivity;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.BookCollection;

import static android.app.Activity.RESULT_OK;

public class BookFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;

    public SimpleAdapter adapter;

    static final int ADD_BOOK_REQUEST = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new SimpleAdapter(getActivity(), BookCollection.getInstance().getMapList(), R.layout.book_list_item,
                new String[] {"img", "author", "title", "isbn"},
                new int[] {R.id.img, R.id.author, R.id.title, R.id.isbn});

        setListAdapter(adapter);

        final View addNewBook = getActivity().findViewById(R.id.book_list_add_button);
        addNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBook(v);
            }
        });

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.book_details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.book_details);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(index, this);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.book_details, details);
                } else {
                    ft.replace(R.id.book_details, details);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    /**
     * Fonction démarrant l'activité d'ajout de livre
     * Appelée lors de l'appui dur le bouton "+" en bas à droite de la liste
     * @param view
     */
    public void addNewBook(View view) {
        Intent intent = new Intent(getActivity(), AddBook.class);
        startActivityForResult(intent, ADD_BOOK_REQUEST);
    }

    /**
     * Appelée lors du retour à cette activité lors de la fin d'une autre activité
     * @param requestCode Code envoyé depuis l'ancienne activité
     * @param resultCode Code de résultat
     * @param data Intent de l'ancienne activité
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Ajout d'un livre
        if (requestCode == ADD_BOOK_REQUEST) {
            if (resultCode == RESULT_OK) {
                Book newBook = (Book) data.getExtras().get("newBook");
                if(!BookCollection.getInstance().add(newBook)){
                    showDetails(mCurCheckPosition+1);
                } else {
                    // Popup avertissant de l'existance du livre
                }
            }
        }
    }
}
