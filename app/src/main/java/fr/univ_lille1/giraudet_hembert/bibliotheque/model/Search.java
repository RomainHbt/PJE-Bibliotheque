package fr.univ_lille1.giraudet_hembert.bibliotheque.model;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

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

import fr.univ_lille1.giraudet_hembert.bibliotheque.activity.SearchForm;

/**
 * Created by hembert on 03/11/16.
 */

public class Search {

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static int ALL_MODE = 0;
    public static int ISBN_MODE = 1;

    private String term;
    private Context context;
    public List<Book> resultList;
    private int mode;

    public Search(Context context, String term, int mode){
        this.context = context;
        this.term = term;
        this.resultList = null;
        this.mode = mode;
    }

    public void exec(){
        String query = null;
        try {
            query = URLEncoder.encode(this.term, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = GOOGLE_BOOKS_API_URL + query;
        if(mode == ISBN_MODE)
            url = GOOGLE_BOOKS_API_URL + "isbn:" + query;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultList = parseResult(response);
                        if(!resultList.isEmpty())
                            BookCollection.getInstance().add(resultList.get(0));
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
                    authors = bookInfos.getJSONArray("authors").join(", ");
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
