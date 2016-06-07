package app.com.augustovictor.moviesapp.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.com.augustovictor.moviesapp.BuildConfig;
import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.adapter.MoviesListAdapter;
import app.com.augustovictor.moviesapp.model.Movie;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MoviesListAdapter adapter;
    private List<Movie> mMovies;

    public MoviesFragment() {
        this.mMovies = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute("popularity.desc");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movies_list, container, false);

        updateMovies();

        setUpRecyclerView(v,  mMovies);

        return v;
    }

    public void setUpRecyclerView(View v, List<Movie> movies) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.movies_list_recycler_view);

        adapter = new MoviesListAdapter(movies);
        mRecyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    // ASYNCTASK

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        private List<String> mMoviesString;

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                for(Movie movie : movies) {
                    adapter.addItem(movie, mMovies.size());
                }
            }
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

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

                // Create the request to TheMovieDb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do
                    moviesJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    moviesJsonStr = null;
                }
                moviesJsonStr = buffer.toString();

                Log.d(LOG_TAG, "Movies json String: " + moviesJsonStr);


            } catch (IOException e) {
                // If the code did not get successfully the data, there's no point in attempting to parse it
                moviesJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            // Parse values
            try {
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

//        This will happen if there is an error getting/parsing the movies
            return null;
        }

        private List<Movie> getMoviesDataFromJson(String moviesJsonStr) throws JSONException{

            // Declare the names of the JSON objects that need to be extracted
            final String OBJ_LIST = "results";
            final String OBJ_TITLE = "title";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(OBJ_LIST);

            List<Movie> moviesList = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                Movie movie = new Movie();

                // Get the json object representing the data you want
                JSONObject movieObject = moviesArray.getJSONObject(i);
                movie.setmTitle(movieObject.getString(OBJ_TITLE));

                moviesList.add(movie);
                Log.d(LOG_TAG, "Movie added: " + movie.getmTitle());
            }

            return moviesList;
        }
    }
}
