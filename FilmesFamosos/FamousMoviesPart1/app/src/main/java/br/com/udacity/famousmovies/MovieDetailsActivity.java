package br.com.udacity.famousmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.udacity.famousmovies.models.MovieModel;
import br.com.udacity.famousmovies.utilities.ImageUtils;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView mDetailOriginalTitleTextView;
    private ImageView mDetailMoviePosterImageView;
    private TextView mDetailVoteAverageTitleTextView;
    private TextView mDetailOriginalLanguageTextView;
    private TextView mDetailReleaseDateTextView;
    private TextView mDetailOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.mDetailOriginalTitleTextView = this.findViewById(R.id.tv_detail_movie_original_title);
        this.mDetailMoviePosterImageView = this.findViewById(R.id.iv_detail_movie_poster);
        this.mDetailVoteAverageTitleTextView = this.findViewById(R.id.tv_detail_movie_vote_average);
        this.mDetailOriginalLanguageTextView = this.findViewById(R.id.tv_detail_movie_original_language);
        this.mDetailReleaseDateTextView = this.findViewById(R.id.tv_detail_movie_release_date);
        this.mDetailOverviewTextView = this.findViewById(R.id.tv_detail_movie_overview);

        Intent intent = this.getIntent();

        if (intent != null && intent.hasExtra(MovieModel.PARCELABLE_KEY)) {
            MovieModel movieModel = intent.getExtras().getParcelable(MovieModel.PARCELABLE_KEY);
            this.renderMovieDetails(movieModel);
        }
    }

    private void renderMovieDetails(MovieModel movieModel) {
        this.mDetailOriginalTitleTextView.setText(movieModel.getOriginalTitle());
        this.mDetailVoteAverageTitleTextView.setText(String.valueOf(movieModel.getVoteAverage()));
        this.mDetailOriginalLanguageTextView.setText(movieModel.getOriginalLanguage().toUpperCase());
        this.mDetailReleaseDateTextView.setText(movieModel.getReleaseDate());
        this.mDetailOverviewTextView.setText(movieModel.getOverview());

        Picasso.with(this)
                .load(movieModel.getPosterPath())
                .into(this.mDetailMoviePosterImageView);

    }
}
