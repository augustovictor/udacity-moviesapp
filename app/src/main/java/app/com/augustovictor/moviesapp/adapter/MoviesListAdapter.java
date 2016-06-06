package app.com.augustovictor.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.augustovictor.moviesapp.R;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private List<String> mMovies;

    public MoviesListAdapter(List<String> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.movie_list_item, parent, false);
        ViewHolder holder = new ViewHolder(movieView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currentMovie = mMovies.get(position);
        holder.bindMovie(currentMovie, position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Constructor
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mMovieTitle = (TextView) itemView.findViewById(R.id.list_item_title_textview);
        }

        public void bindMovie(String currentMovie, int position) {
            this.mMovieTitle.setText(currentMovie);
        }
    }
}
