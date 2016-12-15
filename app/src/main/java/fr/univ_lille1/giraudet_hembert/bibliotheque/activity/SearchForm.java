package fr.univ_lille1.giraudet_hembert.bibliotheque.activity;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.adapters.BookAdapter;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Search;

public class SearchForm extends AppCompatActivity {

    public static List<Book> books = new ArrayList<>();
    public static String search;
    private BaseAdapter adapter;

    private ListView listView;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.search_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SearchDetailsActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        EditText searchField = (EditText) findViewById(R.id.searchField);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    search = v.getText().toString();
                    exec();
                    if(getCurrentFocus()!=null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    public void exec(){
        String query = null;
        try {
            query = URLEncoder.encode(search, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = GOOGLE_BOOKS_API_URL + query;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        books = parseResult(response);
                        Toast.makeText(getApplicationContext(), books.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        adapter = new BookAdapter(getApplicationContext(), books);
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private List<Book> parseResult(String result){
        List<Book> resultList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(result);
            int nbBooks = json.getInt("totalItems") < 10 ? json.getInt("totalItems") : 10;

            if(nbBooks == 0) return resultList;

            JSONArray listOfBooks = json.getJSONArray("items");
            for (int i = 0; i < nbBooks; i++){
                JSONObject bookJSON = listOfBooks.getJSONObject(i);
                JSONObject bookInfos = bookJSON.getJSONObject("volumeInfo");
                String isbn;
                try{
                    isbn = bookInfos.getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier");
                } catch(JSONException e){
                    isbn = "ISBN inconnu";
                }

                String authors;
                try{
                    authors = bookInfos.getJSONArray("authors").join(", ").replace("\"","");
                } catch(JSONException e){
                    authors = "Auteurs inconnus";
                }

                String title;
                try{
                    title = bookInfos.getString("title");
                } catch(JSONException e){
                    title = "Titre inconnu";
                }

                String description;
                try{
                    description = bookInfos.getString("description");
                } catch(JSONException e){
                    description = "Description inconnue";
                }

                String image;
                try{
                    image = bookInfos.getJSONObject("imageLinks").getString("smallThumbnail");
                } catch(JSONException e){
                    image = "Image indisponible";
                }

                Book book = new Book(authors, title, isbn);
                book.setSummary(description);
                book.setImageUrl(image);

                resultList.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
