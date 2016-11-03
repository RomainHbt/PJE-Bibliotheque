package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hembert on 03/11/16.
 */

public class Search {

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private String term;
    private Context context;
    private String resultString;
    private List<Book> resultList;

    public Search(Context context, String term){
        this.context = context;
        this.term = term;
        this.resultString = null;
        this.resultList = null;
    }

    public void exec(){
        String query = null;
        try {
            query = URLEncoder.encode(this.term, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = GOOGLE_BOOKS_API_URL + query;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultString = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultString = "ERROR";
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public List<Book> getResult(){
        if(this.resultList == null) this.parseResult();
        return this.resultList;
    }

    private void parseResult(){
        try {
            JSONObject json = new JSONObject(this.resultString);
            int nbBooks = json.getInt("totalItems");

            this.resultList = new ArrayList<>();

            JSONArray listOfBooks = json.getJSONArray("items");
            for (int i = 0; i < nbBooks; i++){
                JSONObject bookJSON = listOfBooks.getJSONObject(i);
                JSONObject bookInfos = bookJSON.getJSONObject("volumeInfo");
                String isbn = bookInfos.getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier");

                Book book = new Book(bookInfos.getJSONArray("authors").join(", "), bookInfos.getString("title"), isbn);
                book.setDescription(bookInfos.getString("description"));
                book.setImageUrl(bookInfos.getJSONObject("imageLinks").getString("smallThumbnail"));

                resultList.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
