package com.filmflare

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WatchlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var watchlistAdapter: MovieAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var watchlist: MutableList<Popular>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Find RecyclerView by ID
        recyclerView = view.findViewById(R.id.watchlistRecyclerView)

        // Set up RecyclerView with a GridLayoutManager (2 columns)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        watchlist = mutableListOf()

        // Load user's watchlist from Firebase
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userWatchlistRef = database.child("users").child(currentUser.uid).child("watchlist")
            userWatchlistRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    watchlist.clear()
                    for (movieSnapshot in snapshot.children) {
                        val movie = movieSnapshot.getValue(Popular::class.java)
                        if (movie != null) {
                            watchlist.add(movie)
                        }
                    }
                    // Set the adapter with the updated watchlist
                    watchlistAdapter = MovieAdapter(watchlist, isGenreLayout = false)  // Use the default movie layout
                    recyclerView.adapter = watchlistAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if any
                }
            })
        }

        // Set the background color based on dark mode preference
        setFragmentBackground(view)
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
