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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.com.augustovictor.moviesapp.BuildConfig;
import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.adapter.MoviesListAdapter;
import app.com.augustovictor.moviesapp.model.Movie;
import app.com.augustovictor.moviesapp.util.FilterEnum;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesFragment extends Fragment {

    private static final String ARG_MOVIE = "movie";
    private RecyclerView mRecyclerView;
    private MoviesListAdapter adapter;
    private List<Movie> mMovies;

    public MoviesFragment() {
        this.mMovies = new ArrayList<>();
    }

    public static MoviesFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void updateMovies(String filter) {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute(filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movies_list, container, false);

        updateMovies(FilterEnum.POPULARITY.toString());

        setUpRecyclerView(v, mMovies);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_filter_popularity:
                updateMovies(FilterEnum.POPULARITY.toString());
                return true;

            case R.id.menu_item_filter_rated:
                updateMovies(FilterEnum.VOTE_AVARAGE.toString());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                mMovies.clear();
                adapter.notifyDataSetChanged();
                for (Movie movie : movies) {
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
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
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
            } catch (ParseException e) {
                e.printStackTrace();
            }

//        This will happen if there is an error getting/parsing the movies
            return null;
        }

        private List<Movie> getMoviesDataFromJson(String moviesJsonStr) throws JSONException, ParseException {

            // Declare the names of the JSON objects that need to be extracted
            final String
                    OBJ_LIST = "results",
                    OBJ_ID = "id",
                    OBJ_TITLE = "title",
                    OBJ_OVERVIEW = "overview",
                    OBJ_POSTER_PATH = "poster_path",
                    OBJ_BACKDROP_PATH = "backdrop_path",
                    OBJ_LANGUAGE = "original_language",
                    OBJ_VOTES = "vote_count",
                    OBJ_VOTES_AVG = "vote_average",
                    OBJ_RELEASE_DATE = "release_date",
                    OBJ_ADULT = "adult",
                    OBJ_HAS_VIDEO = "video";


            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(OBJ_LIST);

            List<Movie> moviesList = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                Movie movie = new Movie();

                // Get the json object representing the data you want
                JSONObject movieObject = moviesArray.getJSONObject(i);

                // Integer
                movie.setmId(movieObject.getInt(OBJ_ID));
                movie.setmVotes(movieObject.getInt(OBJ_VOTES));

                // String
                movie.setmTitle(movieObject.getString(OBJ_TITLE));
                movie.setmOverview(movieObject.getString(OBJ_OVERVIEW));
                movie.setmPoster(movieObject.getString(OBJ_POSTER_PATH));
                movie.setmBackdropPath(movieObject.getString(OBJ_BACKDROP_PATH));
                movie.setmLanguage(movieObject.getString(OBJ_LANGUAGE));

                // Double
                movie.setmVotesAvg(movieObject.getDouble(OBJ_VOTES_AVG));

                // Date
                String releaseDate = movieObject.getString(OBJ_RELEASE_DATE);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
                Date formatedDate = dateFormat.parse(releaseDate);
                movie.setmReleaseDate(formatedDate);

                // Boolean
                movie.setmAdult(movieObject.getBoolean(OBJ_ADULT));
                movie.setmHasVideo(movieObject.getBoolean(OBJ_HAS_VIDEO));

                moviesList.add(movie);
                Log.d(LOG_TAG, "Movie added: " + movie.toString());
            }

            return moviesList;
        }
    }
}
