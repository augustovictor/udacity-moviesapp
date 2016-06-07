package app.com.augustovictor.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.com.augustovictor.moviesapp.R;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private static final String LOG_TAG = MoviesListAdapter.class.getSimpleName();
    private List<String> mMovies;

    public MoviesListAdapter(List<String> mMovies) {
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
        String currentMovie = mMovies.get(position);
        holder.bindMovie(currentMovie, position);
        Log.d(LOG_TAG, "Movie binded: " + position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Constructor
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieTitle;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.list_item_title_textview);

            this.context = context;

            // IMPORTANT
            itemView.setOnClickListener(this);
        }

        public void bindMovie(String currentMovie, int position) {
            this.mMovieTitle.setText(currentMovie);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            String movie = mMovies.get(position);
            Toast.makeText(context, movie, Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Movie clicked: " + movie);
        }
    }

    public void addItem(String movie, int position) {
        mMovies.add(movie);
        notifyItemInserted(position);
    }
}
