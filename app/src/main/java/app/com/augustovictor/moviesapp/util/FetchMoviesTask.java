package app.com.augustovictor.moviesapp.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import app.com.augustovictor.moviesapp.BuildConfig;

/**
 * Created by victoraweb on 6/6/16.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {


    private static final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    @Override
    protected String[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON as a string
        String moviesJsonStr = null;

        try {
            final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
            final String QUERY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.THEMOVIEDB_API_KEY)
                    .build();
            URL url = new URL(builtUri.toString());

            Log.d(LOG_TAG, "Built URl: " + url);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[0];
    }
}
