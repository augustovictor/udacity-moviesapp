package app.com.augustovictor.moviesapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.adapter.MoviesListAdapter;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesFragment extends Fragment {

    RecyclerView mRecyclerView;

    public MoviesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movies_list, container, false);
        setUpRecyclerView(v);
        return v;
    }

    public void setUpRecyclerView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.movies_list_recycler_view);
        MoviesListAdapter adapter = new MoviesListAdapter(createMovies());
        mRecyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public List<String> createMovies() {
        List<String> movies = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String movie = "Movie " + i;
            movies.add(movie);
        }
        return movies;
    }
}
