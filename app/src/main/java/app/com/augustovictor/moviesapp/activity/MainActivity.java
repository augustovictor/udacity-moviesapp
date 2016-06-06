package app.com.augustovictor.moviesapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import app.com.augustovictor.moviesapp.R;
import app.com.augustovictor.moviesapp.fragment.MoviesFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            createFragment();
        }
    }

    @Override
    protected Fragment createFragment() {
        return new MoviesFragment();
    }
}
