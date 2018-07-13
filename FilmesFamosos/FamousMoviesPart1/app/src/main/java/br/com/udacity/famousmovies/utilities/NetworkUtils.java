package br.com.udacity.famousmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private final static String API_BASE_URL = "http://api.themoviedb.org/3/movie";
    private final static String PARAM_API_KEY = "api_key";

    public static URL buildUrl(DestinationPathUri destinationPathUri, String apiKey) {

        Uri buildUri = Uri.parse(API_BASE_URL).buildUpon()
                .appendPath(destinationPathUri.name().toLowerCase())
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnectedOrConnecting());
    }

    public enum DestinationPathUri {
        POPULAR,
        TOP_RATED
    }
}
