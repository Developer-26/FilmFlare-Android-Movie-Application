package com.filmflare

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenresFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var scienceFictionRecyclerView: RecyclerView
    private lateinit var actionRecyclerView: RecyclerView
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var romanceRecyclerView: RecyclerView
    private lateinit var dramaRecyclerView: RecyclerView
    private lateinit var scienceFictionAdapter: MovieAdapter
    private lateinit var actionAdapter: MovieAdapter
    private lateinit var crimeAdapter: MovieAdapter
    private lateinit var romanceAdapter: MovieAdapter
    private lateinit var dramaAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences **before** using it
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Initialize RecyclerViews
        scienceFictionRecyclerView = view.findViewById(R.id.scienceFictionRecyclerView)
        actionRecyclerView = view.findViewById(R.id.actionRecyclerView)
        crimeRecyclerView = view.findViewById(R.id.crimeRecyclerView)
        romanceRecyclerView = view.findViewById(R.id.romanceRecyclerView)
        dramaRecyclerView = view.findViewById(R.id.dramaRecyclerView)

        // Set up RecyclerViews with horizontal layout
        setUpRecyclerViews()

        // Set the background based on dark mode preference
        setFragmentBackground(view)

        // Fetch movies for each genre
        fetchScienceFictionMovies()
        fetchActionMovies()
        fetchCrimeMovies()
        fetchRomanceMovies()
        fetchDramaMovies()

    }

    private fun setUpRecyclerViews() {
        // Set Layout Managers
        scienceFictionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        actionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        crimeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        romanceRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dramaRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Initialize Adapters
        scienceFictionAdapter = MovieAdapter(listOf(), isGenreLayout = true)
        actionAdapter = MovieAdapter(listOf(), isGenreLayout = true)
        crimeAdapter = MovieAdapter(listOf(), isGenreLayout = true)
        romanceAdapter = MovieAdapter(listOf(), isGenreLayout = true)
        dramaAdapter = MovieAdapter(listOf(), isGenreLayout = true)

        // Set Adapters
        scienceFictionRecyclerView.adapter = scienceFictionAdapter
        actionRecyclerView.adapter = actionAdapter
        crimeRecyclerView.adapter = crimeAdapter
        romanceRecyclerView.adapter = romanceAdapter
        dramaRecyclerView.adapter = dramaAdapter
    }

    private fun setFragmentBackground(view: View) {
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        val backgroundColor = if (isDarkMode) {
            resources.getColor(R.color.dark_background_color, null) // Use dark mode color
        } else {
            resources.getColor(R.color.light_background_color, null) // Use light mode color
        }
        view.setBackgroundColor(backgroundColor)
    }

    // Fetch movie methods for each genre
    private fun fetchScienceFictionMovies() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(call: Call<responseDataClass>, response: Response<responseDataClass>) {
                if (response.isSuccessful) {
                    val scienceFictionMovies = response.body()?.record?.movies?.science_fiction ?: emptyList()
                    scienceFictionAdapter.setMovies(scienceFictionMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Science Fiction movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchActionMovies() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(call: Call<responseDataClass>, response: Response<responseDataClass>) {
                if (response.isSuccessful) {
                    val actionMovies = response.body()?.record?.movies?.action ?: emptyList()
                    actionAdapter.setMovies(actionMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Action movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchCrimeMovies() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(call: Call<responseDataClass>, response: Response<responseDataClass>) {
                if (response.isSuccessful) {
                    val crimeMovies = response.body()?.record?.movies?.crime ?: emptyList()
                    crimeAdapter.setMovies(crimeMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Crime movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRomanceMovies() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(call: Call<responseDataClass>, response: Response<responseDataClass>) {
                if (response.isSuccessful) {
                    val romanceMovies = response.body()?.record?.movies?.romance ?: emptyList()
                    romanceAdapter.setMovies(romanceMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Romance movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchDramaMovies() {
        val apiInterface = RetroInstance.api

        apiInterface.getData().enqueue(object : Callback<responseDataClass> {
            override fun onResponse(call: Call<responseDataClass>, response: Response<responseDataClass>) {
                if (response.isSuccessful) {
                    val dramaMovies = response.body()?.record?.movies?.drama ?: emptyList()
                    dramaAdapter.setMovies(dramaMovies)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch Drama movies", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<responseDataClass>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
