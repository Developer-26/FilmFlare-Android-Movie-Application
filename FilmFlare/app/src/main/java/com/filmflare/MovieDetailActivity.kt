package com.filmflare

import Cast
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        var watchlist: MutableList<Popular> = mutableListOf() // Watchlist that stores movie objects
    }

    // Firebase reference
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var watchlistButton: Button

    private var isMovieInWatchlist = false // To track whether the movie is already in the watchlist
    private var movieKey: String? = null  // Firebase key of the movie, if it exists in the watchlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        // Get the intent data
        val poster = intent.getStringExtra("poster")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val rating = intent.getStringExtra("rating")
        val duration = intent.getStringExtra("duration")
        val year = intent.getStringExtra("year")
        val genre = intent.getStringExtra("genre")
        val castNames = intent.getStringArrayListExtra("castNames") ?: arrayListOf()
        val castImages = intent.getStringArrayListExtra("castImages") ?: arrayListOf()

        // Find views
        val ivMovieDetailPoster: ImageView = findViewById(R.id.ivMovieDetailPoster)
        val tvMovieDetailTitle: TextView = findViewById(R.id.tvMovieDetailTitle)
        val tvMovieDetailDescription: TextView = findViewById(R.id.tvMovieDetailDescription)
        val tvMovieDetailGenre: TextView = findViewById(R.id.tvMovieDetailGenre)
        val tvMovieDetailRating: TextView = findViewById(R.id.tvMovieDetailRating)
        val tvMovieDetailDuration: TextView = findViewById(R.id.tvMovieDetailDuration)
        val tvMovieDetailYear: TextView = findViewById(R.id.tvMovieDetailYear)
        val castRecyclerView: RecyclerView = findViewById(R.id.castRecyclerView)
        watchlistButton = findViewById(R.id.Watchlist)

        // Populate views with data
        Glide.with(this).load(poster).into(ivMovieDetailPoster)
        tvMovieDetailTitle.text = title
        tvMovieDetailDescription.text = description
        tvMovieDetailGenre.text = "Genre: $genre" // Set the genre
        tvMovieDetailRating.text = "Rating: ${String.format("%.1f", rating?.toDoubleOrNull() ?: 0.0)}"
        tvMovieDetailDuration.text = "Duration: $duration"
        tvMovieDetailYear.text = "Year: $year"

        // Set up the cast RecyclerView
        castRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerView.adapter = CastAdapter(castNames, castImages)

        // Check if the movie is already in the user's watchlist and update the button
        checkIfMovieInWatchlist(title)

        // Handle watchlist button click (toggle functionality)
        watchlistButton.setOnClickListener {
            if (isMovieInWatchlist) {
                removeFromWatchlist(movieKey)
            } else {
                addToWatchlist(title, poster, description, rating, duration, year, genre, castNames, castImages)
            }
        }
    }

    private fun checkIfMovieInWatchlist(title: String?) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userWatchlistRef = database.child("users").child(currentUser.uid).child("watchlist")

            // Query Firebase to check if the movie is already in the watchlist
            userWatchlistRef.orderByChild("title").equalTo(title).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    // Movie already exists in the watchlist, store the movie key
                    for (snapshot in dataSnapshot.children) {
                        movieKey = snapshot.key // Store the movie's key
                    }
                    isMovieInWatchlist = true
                    updateWatchlistButtonState() // Update the button state
                } else {
                    isMovieInWatchlist = false
                    updateWatchlistButtonState() // Update the button state
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to check watchlist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToWatchlist(
        title: String?,
        poster: String?,
        description: String?,
        rating: String?,
        duration: String?,
        year: String?,
        genre: String?,
        castNames: ArrayList<String>,
        castImages: ArrayList<String>
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userWatchlistRef = database.child("users").child(currentUser.uid).child("watchlist")

            // Create a new movie object
            val movie = Popular(
                poster = poster ?: "",
                title = title ?: "",
                description = description ?: "",
                rating = rating?.toDoubleOrNull() ?: 0.0,
                duration = duration ?: "",
                year = year?.toIntOrNull() ?: 0,
                genre = genre ?: "",
                cast = castNames.mapIndexed { index, name ->
                    Cast(name = name, image = castImages.getOrNull(index) ?: "")
                }
            )

            // Add the movie to Firebase
            userWatchlistRef.push().setValue(movie).addOnSuccessListener {
                Toast.makeText(this, "$title added to your watchlist", Toast.LENGTH_SHORT).show()
                isMovieInWatchlist = true
                updateWatchlistButtonState() // Update button state after adding
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to add $title to your watchlist", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromWatchlist(movieKey: String?) {
        val currentUser = auth.currentUser
        if (currentUser != null && movieKey != null) {
            val userWatchlistRef = database.child("users").child(currentUser.uid).child("watchlist").child(movieKey)

            // Remove the movie from Firebase
            userWatchlistRef.removeValue().addOnSuccessListener {
                Toast.makeText(this, "Movie removed from your watchlist", Toast.LENGTH_SHORT).show()
                isMovieInWatchlist = false
                updateWatchlistButtonState() // Update button state after removal
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to remove the movie from your watchlist", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Movie not found in your watchlist", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to update the button text and background color based on watchlist status
    private fun updateWatchlistButtonState() {
        if (isMovieInWatchlist) {
            // Change text and icon to "Added To Watchlist" and set the "added" icon
            watchlistButton.text = "Added To Watchlist"
            watchlistButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_bookmark_added_24, 0, 0, 0)
            watchlistButton.setBackgroundColor(resources.getColor(R.color.added_to_watchlist_color, null))
        } else {
            // Change text and icon to "Add To Watchlist" and set the "add" icon
            watchlistButton.text = "Add To Watchlist"
            watchlistButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_bookmark_add_24, 0, 0, 0)
            watchlistButton.setBackgroundColor(resources.getColor(R.color.add_to_watchlist_color, null))
        }
    }
}
