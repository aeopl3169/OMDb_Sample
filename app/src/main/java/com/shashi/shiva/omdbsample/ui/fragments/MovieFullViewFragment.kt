package com.shashi.shiva.omdbsample.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.shashi.shiva.omdbsample.R
import com.shashi.shiva.omdbsample.ui.MovieActivity
import com.shashi.shiva.omdbsample.ui.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_full_view.*

class MovieFullViewFragment : Fragment(R.layout.fragment_movie_full_view) {
    private lateinit var viewModel: MovieViewModel
    private val args: MovieFullViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MovieActivity).viewModel

        val movie = args.movie

        Glide.with(this).load(movie.poster).into(image_view_poster)
        text_view_title.text = movie.title
        text_view_released.text = movie.released
        text_view_actors.text = movie.actors
        text_view_run_time.text = movie.runtime

        fab.setOnClickListener {
            viewModel.saveMovie(movie)
            Snackbar.make(view, "Movie saved successfully.", Snackbar.LENGTH_SHORT).show()
        }

    }
}