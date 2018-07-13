package br.com.udacity.famousmovies.services;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.net.URL;
import java.util.List;

import br.com.udacity.famousmovies.interfaces.AsyncTaskDelegate;
import br.com.udacity.famousmovies.models.MovieModel;
import br.com.udacity.famousmovies.utilities.JsonParserUtil;
import br.com.udacity.famousmovies.utilities.NetworkUtils;

public class MovieLoaderService implements LoaderManager.LoaderCallbacks<List<MovieModel>> {

    private AsyncTaskDelegate delegate;
    private final Context context;
    private final String URL_TO_GET_MOVIES;

    public MovieLoaderService(Context context, AsyncTaskDelegate delegate, String url) {
        this.context = context;
        this.delegate = delegate;
        this.URL_TO_GET_MOVIES = url;
    }

    @Override
    public Loader<List<MovieModel>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<MovieModel>>(this.context) {

            List<MovieModel> movieModels;

            @Override
            protected void onStartLoading() {

                if (args == null) {
                    return;
                }

                if (movieModels == null || movieModels.size() == 0) {
                    this.forceLoad();
                }
            }

            @Override
            public List<MovieModel> loadInBackground() {

                if (args == null) {
                    return null;
                }

                String jsonContent = null;
                try {
                    URL url = new URL(args.getString(URL_TO_GET_MOVIES));
                    jsonContent = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return JsonParserUtil.JsonToMovieModelList(jsonContent);

            }

            @Override
            public void deliverResult(List<MovieModel> data) {
                movieModels = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<MovieModel>> loader, List<MovieModel> data) {
        if (data == null) {
            return;
        } else {
            this.delegate.processFinish(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieModel>> loader) {

    }
}
