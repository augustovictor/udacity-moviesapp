package app.com.augustovictor.moviesapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import app.com.augustovictor.moviesapp.fragment.MovieDetailsFragment;
import app.com.augustovictor.moviesapp.model.Movie;

/**
 * Created by victoraweb on 6/10/16.
 */
public class MovieDetailsActivity extends SingleFragmentActivity {

    private static final String EXTRA_MOVIE = "movie";

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, MovieDetailsActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        return MovieDetailsFragment.newInstance(movie);
    }
}
