package com.filmflare

import Cast
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movieDao: MovieDao
    private val gson = Gson() // For converting lists to JSON

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Initialize the Room database and DAO
        val movieDatabase = MovieDatabase.getDatabase(requireContext())
        movieDao = movieDatabase.movieDao()

        recyclerView = view.findViewById(R.id.popularRecyclerView) // RecyclerView for popular movies
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // GridLayout with 2 columns

        movieAdapter = MovieAdapter(listOf(), isGenreLayout = false) // Use default layout
        recyclerView.adapter = movieAdapter

        // Fetch and cache movies
        fetchPopularMovies()

        // Set the background color based on dark mode preference
        setFragmentBackground(view)
    }

    private fun fetchPopularMovies() {
        // First check if movies are available in the local database
        lifecycleScope.launch {
            val cachedMovies = withContext(Dispatchers.IO) {
                movieDao.getAllMovies() // Get cached movies from Room
            }

            if (cachedMovies.isNotEmpty()) {
                // Display cached movies
                movieAdapter.setMovies(cachedMovies.map { movieEntity ->

                    // Convert names and images back to a list of Cast objects safely
                    val castNames = movieEntity.getCastNames().orEmpty()  // Ensure the list is not null
                    val castImages = movieEntity.getCastImages().orEmpty() // Ensure the list is not null

                    // Zip the two lists together, handling size mismatches
                    val castList = castNames.zip(castImages).map { (name, image) ->
                        Cast(name, image)
                    }

                    // Pass the cast list to the Popular object
                    Popular(
                        title = movieEntity.title,
                        poster = movieEntity.poster,
                        description = movieEntity.description,
                        rating = movieEntity.rating.toDouble(),
                        duration = movieEntity.duration,
                        year = movieEntity.year,
                        genre = movieEntity.genre,
                        cast = castList // Assign the processed cast list
                    )
                })
            } else {
                // No cached movies, fetch from API
                fetchFromApiAndCache()
            }
        }
    }


    private fun fetchFromApiAndCache() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(
                call: Call<responseDataClass>,
                response: Response<responseDataClass>
            ) {
                if (response.isSuccessful) {
                    val popularMovies = response.body()?.record?.movies?.popular ?: emptyList()

                    // Update the UI with the movies
                    movieAdapter.setMovies(popularMovies)

                    // Cache the movies in Room
                    cacheMovies(popularMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cacheMovies(popularMovies: List<Popular>) {
        val movieEntities = popularMovies.map { movie ->
            MovieEntity(
                id = movie.title.hashCode(), // Generate a pseudo ID using the title's hash code
                title = movie.title,
                poster = movie.poster,
                description = movie.description,
                rating = movie.rating.toFloat(),
                duration = movie.duration,
                year = movie.year,
                genre = movie.genre,
                castNamesJson = gson.toJson(movie.cast.map { it.name }), // Store cast names as JSON
                castImagesJson = gson.toJson(movie.cast.map { it.image }) // Store cast images as JSON
            )
        }

        lifecycleScope.launch(Dispatchers.IO) {
            movieDao.deleteAllMovies() // Clear old cached data
            movieDao.insertMovies(movieEntities) // Insert new data into the database
        }
    }

    private fun setFragmentBackground(view: View) {
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        val backgroundColor = if (isDarkMode) {
            resources.getColor(R.color.dark_background_color) // Use dark mode color
        } else {
            resources.getColor(R.color.light_background_color) // Use light mode color
        }
        view.setBackgroundColor(backgroundColor)
    }
}
