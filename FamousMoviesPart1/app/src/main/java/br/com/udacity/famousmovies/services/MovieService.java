package br.com.udacity.famousmovies.services;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.com.udacity.famousmovies.interfaces.AsyncTaskDelegate;
import br.com.udacity.famousmovies.models.MovieModel;
import br.com.udacity.famousmovies.utilities.JsonParserUtil;
import br.com.udacity.famousmovies.utilities.NetworkUtils;

public class MovieService extends AsyncTask<URL, String, List<MovieModel>> {

    private AsyncTaskDelegate delegate;

    public MovieService(AsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<MovieModel> doInBackground(URL... urls) {
        URL url = urls[0];
        String jsonContent = null;
        try {
            jsonContent = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonParserUtil.JsonToMovieModelList(jsonContent);
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);

        if (this.delegate != null)
            delegate.processFinish(movieModels);
    }
}
