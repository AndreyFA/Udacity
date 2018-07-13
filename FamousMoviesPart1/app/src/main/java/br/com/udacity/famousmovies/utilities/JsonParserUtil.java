package br.com.udacity.famousmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.udacity.famousmovies.models.MovieModel;

public class JsonParserUtil {
    private static MovieModel JsonToMovieModel(String jsonContent) {
        MovieModel movieModel = null;

        try {
            String baseImageUri = "http://image.tmdb.org/t/p/w185/";

            JSONObject movieJson = new JSONObject(jsonContent);

            Integer id = movieJson.optInt("id");
            Double voteAverage = movieJson.optDouble("vote_average");
            String posterPath = baseImageUri + movieJson.optString("poster_path");
            String originalTitle = movieJson.optString("original_title");
            String originalLanguage = movieJson.optString("original_language");
            String releaseDate = movieJson.optString("release_date");
            String overview = movieJson.optString("overview");

            movieModel = new MovieModel(id, voteAverage, posterPath, originalTitle, originalLanguage, releaseDate, overview);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieModel;
    }

    public static List<MovieModel> JsonToMovieModelList(String jsonContent) {
        List<MovieModel> movieModels = new ArrayList<>();

        try {
            JSONObject jsonMoviesResult = new JSONObject(jsonContent);
            JSONArray jsonMovies = jsonMoviesResult.getJSONArray("results");

            for (int i = 0; i < jsonMovies.length(); i++) {
                movieModels.add(JsonParserUtil.JsonToMovieModel(jsonMovies.getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieModels;
    }
}
