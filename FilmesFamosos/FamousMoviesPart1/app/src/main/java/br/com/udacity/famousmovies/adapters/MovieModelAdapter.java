package br.com.udacity.famousmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.udacity.famousmovies.R;
import br.com.udacity.famousmovies.models.MovieModel;

public class MovieModelAdapter extends RecyclerView.Adapter<MovieModelAdapter.MovieModelViewHolder> {

    private final List<MovieModel> movieModelList;
    private Context context;
    private final MovieModelAdapterOnClickHandler movieAdapterOnClickHandler;


    public MovieModelAdapter(List<MovieModel> movieModelList, Context context, MovieModelAdapterOnClickHandler movieAdapterOnClickHandler) {
        this.movieModelList = movieModelList;
        this.context = context;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    @NonNull
    @Override
    public MovieModelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(this.context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new MovieModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieModelViewHolder viewHolder, int position) {
        MovieModel currentMovieModel = this.movieModelList.get(position);

        Picasso.with(this.context)
                .load(currentMovieModel.getPosterPath())
                .into(viewHolder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        return this.movieModelList.size();
    }

    public class MovieModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPosterImageView;

        MovieModelViewHolder(View itemView) {
            super(itemView);

            this.mPosterImageView = itemView.findViewById(R.id.iv_principal_movie_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MovieModel currentMovieModel = movieModelList.get(this.getAdapterPosition());
            movieAdapterOnClickHandler.onClick(currentMovieModel);
        }
    }

    public interface MovieModelAdapterOnClickHandler {
        void onClick(MovieModel movieModel);
    }
}
