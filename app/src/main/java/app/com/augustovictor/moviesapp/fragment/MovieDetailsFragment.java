package app.com.augustovictor.moviesapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.model.Movie;

/**
 * Created by victoraweb on 6/10/16.
 */
public class MovieDetailsFragment extends Fragment {

    public static final String ARG_MOVIE = "movie";
    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    private Movie mMovie;
    private TextView mMovieTitleTextView;
    private TextView mMovieVoteAvg;
    private TextView mMovieLanguageTextView;
    private TextView mMovieOverviewTextView;
    private TextView mMovieReleaseDateTextView;
    private ImageView mMovieBackdropImageView;
    private ImageView mMoviePosterImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = (Movie) getArguments().getSerializable("movie");
    }

    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details, container, false);

        mMovieTitleTextView = (TextView) v.findViewById(R.id.movie_details_title);
        mMovieVoteAvg = (TextView) v.findViewById(R.id.movie_details_vote_avarage);
        mMovieLanguageTextView = (TextView) v.findViewById(R.id.movie_details_vote_language);
        mMovieOverviewTextView = (TextView) v.findViewById(R.id.movie_details_overview);
        mMovieReleaseDateTextView = (TextView) v.findViewById(R.id.movie_details_release_date);
        mMovieBackdropImageView = (ImageView) v.findViewById(R.id.movie_details_backdrop);
        mMoviePosterImageView = (ImageView) v.findViewById(R.id.movie_details_poster_imageview);

        mMovieTitleTextView.setText(mMovie.getmTitle());
        mMovieVoteAvg.setText(String.valueOf(mMovie.getmVotesAvg()));
        mMovieLanguageTextView.setText(mMovie.getmLanguage());
        mMovieOverviewTextView.setText(mMovie.getmOverview());
        mMovieReleaseDateTextView.setText(mMovie.getmReleaseDate().toString());
        Picasso.with(getActivity()).load(mMovie.getmPoster()).fit().placeholder(R.drawable.ic_loading_image).into(mMoviePosterImageView);
        Picasso.with(getActivity()).load(mMovie.getmBackdropPath()).fit().placeholder(R.drawable.ic_loading_image).into(mMovieBackdropImageView);

        return v;
    }
}
