package fr.univ_lille1.giraudet_hembert.bibliotheque.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import fr.univ_lille1.giraudet_hembert.bibliotheque.R;
import fr.univ_lille1.giraudet_hembert.bibliotheque.model.Book;

/**
 * Created by aymeric on 15/12/16.
 */

public class BookAdapter extends BaseAdapter {

    protected List<Book> books;
    protected Context context;
    protected LayoutInflater layoutInflater;

    public BookAdapter(Context context, List<Book> books) {
        super();
        this.books = books;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.book_list_item, null);

        final Book book = books.get(position);

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.img);

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

        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(book.getAuthors());
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(book.getTitle());
        TextView isbn = (TextView) convertView.findViewById(R.id.isbn);
        isbn.setText(book.getIsbn());

        return convertView;
    }
}
