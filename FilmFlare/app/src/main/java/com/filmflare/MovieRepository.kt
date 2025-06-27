package com.filmflare

import com.google.gson.Gson

class MovieRepository(private val movieDao: MovieDao, private val apiInterface: ApiInterface) {

    suspend fun fetchMoviesFromApiAndCache(): List<MovieEntity>? {
        val response = apiInterface.getData().execute()
        if (response.isSuccessful) {
            val popularMovies = response.body()?.record?.movies?.popular
            val movieEntities = popularMovies?.map { movie ->
                val gson = Gson()
                MovieEntity(
                    id = "${movie.title}_${movie.year}".hashCode(),
                    title = movie.title,
                    poster = movie.poster,
                    description = movie.description,
                    rating = movie.rating.toFloat(),
                    duration = movie.duration,
                    year = movie.year,
                    genre = movie.genre,
                    castNamesJson = gson.toJson(movie.cast.map { it.name }),   // Serialize cast names to JSON
                    castImagesJson = gson.toJson(movie.cast.map { it.image }) // Serialize cast images to JSON
                )
            } ?: emptyList()

            movieDao.deleteAllMovies()
            movieDao.insertMovies(movieEntities)
            return movieEntities
        }
        return null
    }

    fun getMoviesFromDb(): List<MovieEntity> {
        return movieDao.getAllMovies()
    }
}
