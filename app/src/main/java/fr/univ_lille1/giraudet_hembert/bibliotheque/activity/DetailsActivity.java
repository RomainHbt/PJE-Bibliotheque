package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import fr.univ_lille1.giraudet_hembert.bibliotheque.fragment.DetailsFragment;

/**
 * Created by giraudet on 13/10/16.
 */

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            DetailsFragment details = new DetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}