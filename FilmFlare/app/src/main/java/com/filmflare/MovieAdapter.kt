package com.filmflare

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(
    private var movieList: List<Any>,
    private val isGenreLayout: Boolean // Flag to determine which layout to use
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflate the appropriate layout based on the flag
        val layoutId = if (isGenreLayout) R.layout.item_movie_genre else R.layout.movie_item
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        // Determine the poster URL depending on the movie type
        val posterUrl = when (movie) {
            is Popular -> movie.poster
            is ScienceFiction -> movie.poster
            is Action -> movie.poster // Handle Action movies here
            is Crime -> movie.poster // Handle Action movies here
            is Romance -> movie.poster // Handle Action movies here
            is Drama -> movie.poster // Handle Action movies here
            else -> ""
        }

        // Load the movie poster using Glide
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .into(holder.moviePoster)

        // Set a click listener on the movie poster
        holder.moviePoster.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java)

            when (movie) {
                is Popular -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
                is ScienceFiction -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
                is Action -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
                is Crime -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
                is Romance -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
                is Drama -> {
                    // Pass Action movie details to the MovieDetailActivity
                    intent.putExtra("poster", movie.poster)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.rating.toString())
                    intent.putExtra("duration", movie.duration)
                    intent.putExtra("year", movie.year.toString())
                    intent.putExtra("genre", movie.genre)
                    intent.putStringArrayListExtra("castNames", ArrayList(movie.cast.map { it.name }))
                    intent.putStringArrayListExtra("castImages", ArrayList(movie.cast.map { it.image }))
                }
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movieList.size

    // Function to update the movie list
    fun setMovies(movies: List<Any>) {
        this.movieList = movies
        notifyDataSetChanged()
    }
}
