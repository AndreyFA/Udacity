package br.com.udacity.famousmovies.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieModel implements Parcelable {

    private Integer id;
    private Double voteAverage;
    private String posterPath;
    private String originalTitle;
    private String originalLanguage;
    private String releaseDate;
    private String overview;

    public MovieModel() {

    }

    public MovieModel(Integer id, Double voteAverage, String posterPath, String originalTitle, String originalLanguage, String releaseDate, String overview) {
        this.setId(id);
        this.setVoteAverage(voteAverage);
        this.setPosterPath(posterPath);
        this.setOriginalTitle(originalTitle);
        this.setOriginalLanguage(originalLanguage);
        this.setReleaseDate(releaseDate);
        this.setOverview(overview);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public static final String PARCELABLE_KEY = "movieModel";

    public  static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {

        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    private MovieModel(Parcel in) {
        this.setPosterPath(in.readString());
        this.setVoteAverage(in.readDouble());
        this.setOriginalTitle(in.readString());
        this.setOriginalLanguage(in.readString());
        this.setOverview(in.readString());
        this.setReleaseDate(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getPosterPath());
        dest.writeDouble(this.getVoteAverage());
        dest.writeString(this.getOriginalTitle());
        dest.writeString(this.getOriginalLanguage());
        dest.writeString(this.getOverview());
        dest.writeString(this.getReleaseDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
