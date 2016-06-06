package app.com.augustovictor.moviesapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.augustovictor.moviesapp.R;

/**
 * Created by victoraweb on 6/6/16.
 */
public class MoviesFragment extends Fragment {

    public MoviesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movies_list, container, false);

        return v;
    }
}
