package app.com.augustovictor.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.activity.MovieDetailsActivity;
import app.com.augustovictor.moviesapp.model.Movie;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private static final String LOG_TAG = MoviesListAdapter.class.getSimpleName();
    private List<Movie> mMovies;

    public MoviesListAdapter(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.movie_list_item, parent, false);
        ViewHolder holder = new ViewHolder(context, movieView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentMovie = mMovies.get(position);
        holder.bindMovie(currentMovie, position);
        Log.d(LOG_TAG, "Movie binded: " + position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Constructor
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieId;
        private TextView mMovieTitle;
        private TextView mMovieVoteAverage;
        private TextView mMovieLanguage;
        private TextView mMovieReleaseDate;
        private ImageView mMoviePoster;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mMovieId = (TextView) itemView.findViewById(R.id.list_item_id_textview);
            mMovieTitle = (TextView) itemView.findViewById(R.id.list_item_title_textview);
            mMovieVoteAverage = (TextView) itemView.findViewById(R.id.list_item_vote_average_textview);
            mMovieLanguage = (TextView) itemView.findViewById(R.id.list_item_language_textview);
//            mMovieReleaseDate = (TextView) itemView.findViewById(R.id.list_item_release_date_textview);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.list_item_poster_imageview);

            this.context = context;

            // IMPORTANT
            itemView.setOnClickListener(this);
        }

        public void bindMovie(Movie currentMovie, int position) {
            this.mMovieId.setText(String.valueOf(currentMovie.getmId()));
            this.mMovieTitle.setText(currentMovie.getmTitle());
            this.mMovieVoteAverage.setText("Rating: " + String.valueOf(currentMovie.getmVotesAvg()));
            this.mMovieLanguage.setText(currentMovie.getmLanguage());
//            this.mMovieReleaseDate.setText(currentMovie.getmReleaseDate().toString());
            Picasso.with(context).load(currentMovie.getmPoster()).fit().placeholder(R.drawable.ic_loading_image).into(mMoviePoster);
            Log.d(LOG_TAG, "Movie path: " + currentMovie.getmPoster());
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Movie movie = mMovies.get(position);

            Intent i = MovieDetailsActivity.newIntent(context);
            i.putExtra("movie", movie);
            context.startActivity(i);
            Log.d(LOG_TAG, "Movie clicked: " + movie);
        }
    }

    public void addItem(Movie movie, int position) {
        mMovies.add(movie);
        notifyItemInserted(position);
    }
}
