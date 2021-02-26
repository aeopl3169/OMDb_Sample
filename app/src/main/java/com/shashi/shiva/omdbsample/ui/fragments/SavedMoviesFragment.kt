package com.shashi.shiva.omdbsample.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shashi.shiva.omdbsample.R
import com.shashi.shiva.omdbsample.adapters.MovieAdapter
import com.shashi.shiva.omdbsample.ui.MovieActivity
import com.shashi.shiva.omdbsample.ui.MovieViewModel
import kotlinx.android.synthetic.main.fragment_saved_movies.*

class SavedMoviesFragment : Fragment(R.layout.fragment_saved_movies) {
    lateinit var viewModel: MovieViewModel
    lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MovieActivity).viewModel
        setupRecyclerView()
        movieAdapter.setCustomOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(
                R.id.action_savedMoviesFragment_to_movieFullViewFragment,
                bundle
            )
        }

        // To delete movies on swipe.
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get the position
                val position = viewHolder.adapterPosition
                val movie = movieAdapter.differ.currentList[position]
                viewModel.deleteSavedMovie(movie) // deleting the movie.
                Snackbar.make(view, "Deleted.", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO") {
                        viewModel.saveMovie(movie) // saving the deleted movie by undo in Snackbar.
                    }.show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recycler_view_saved_movies)
        }

        // calling the saved movies from DB.
        viewModel.getAllSavedMovies().observe(viewLifecycleOwner, { movies ->
            movieAdapter.differ.submitList(movies)
        })
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        recycler_view_saved_movies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}