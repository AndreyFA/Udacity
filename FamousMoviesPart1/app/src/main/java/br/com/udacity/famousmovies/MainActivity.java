package br.com.udacity.famousmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

import br.com.udacity.famousmovies.adapters.MovieModelAdapter;
import br.com.udacity.famousmovies.interfaces.AsyncTaskDelegate;
import br.com.udacity.famousmovies.models.MovieModel;
import br.com.udacity.famousmovies.services.MovieLoaderService;
import br.com.udacity.famousmovies.services.MovieService;
import br.com.udacity.famousmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieModelAdapter.MovieModelAdapterOnClickHandler, AsyncTaskDelegate {

    private RecyclerView mMovieItemsRecycleView;
    private ProgressBar mLoadMoviesProgressBar;

    private final static int MOVIE_LOADER_ID = 78;
    private final String URL_TO_MOVIES = "popularMovies";

    // TODO: Informar a chave de autenticação com a API MovieDB.
    private final String API_KEY = "ba4fcf2bf797969f4e675d1acc23c969";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMovieItemsRecycleView = this.findViewById(R.id.rv_movie_items);
        this.mLoadMoviesProgressBar = this.findViewById(R.id.pb_load_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        this.mMovieItemsRecycleView.setLayoutManager(layoutManager);

        this.getPopularMovies();
    }

    private void loadMovies(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(this.URL_TO_MOVIES, url);

        LoaderManager loaderManager = this.getSupportLoaderManager();
        Loader<List<MovieModel>> movieLoader = loaderManager.getLoader(MOVIE_LOADER_ID);

        MovieLoaderService loader = new MovieLoaderService(this, this, this.URL_TO_MOVIES);

        if (movieLoader == null) {
            loaderManager.initLoader(MOVIE_LOADER_ID, bundle, loader);
        } else {
            loaderManager.restartLoader(MOVIE_LOADER_ID, bundle, loader);
        }
    }

    private void getPopularMovies() {
        this.mLoadMoviesProgressBar.setVisibility(View.VISIBLE);
        if (NetworkUtils.isNetworkConnected(this)) {
            URL apiUrl = NetworkUtils.buildUrl(NetworkUtils.DestinationPathUri.POPULAR, this.API_KEY);
//            new MovieService(this).execute(apiUrl);
            loadMovies(apiUrl.toString());
        } else {
            Toast toast = Toast.makeText(this, R.string.message_no_internet_connection, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void getTopRatedMovies() {
        this.mLoadMoviesProgressBar.setVisibility(View.VISIBLE);
        if (NetworkUtils.isNetworkConnected(this)) {
            URL apiUrl = NetworkUtils.buildUrl(NetworkUtils.DestinationPathUri.TOP_RATED, this.API_KEY);
//            new MovieService(this).execute(apiUrl);
            this.loadMovies(apiUrl.toString());
        } else {
            Toast toast = Toast.makeText(this, R.string.message_no_internet_connection, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onClick(MovieModel movieModel) {
        Class movieDetailsActivity = MovieDetailsActivity.class;
        Intent movieDetailsIntent = new Intent(this, movieDetailsActivity);
        movieDetailsIntent.putExtra(MovieModel.PARCELABLE_KEY, movieModel);
        this.startActivity(movieDetailsIntent);
    }

    @Override
    public void processFinish(Object output) {
        if (output != null) {
            List<MovieModel> movieModels = (List<MovieModel>) output;

            MovieModelAdapter adapter = new MovieModelAdapter(movieModels, MainActivity.this, MainActivity.this);
            mMovieItemsRecycleView.setAdapter(adapter);
        }

        this.mLoadMoviesProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_menu_popular_movies:
                this.getPopularMovies();
                break;
            case R.id.action_menu_top_rated_movies:
                this.getTopRatedMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
