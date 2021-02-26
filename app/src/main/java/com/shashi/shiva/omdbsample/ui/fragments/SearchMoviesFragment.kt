package com.shashi.shiva.omdbsample.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.shashi.shiva.omdbsample.R
import com.shashi.shiva.omdbsample.ui.MovieActivity
import com.shashi.shiva.omdbsample.ui.MovieViewModel
import com.shashi.shiva.omdbsample.utils.Constants.Companion.SEARCH_MOVIE_TIME_DELAY
import com.shashi.shiva.omdbsample.utils.Resource
import kotlinx.android.synthetic.main.display_movie_details.*
import kotlinx.android.synthetic.main.fragment_search_movies.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMoviesFragment : Fragment(R.layout.fragment_search_movies) {

    private lateinit var viewModel: MovieViewModel

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MovieActivity).viewModel
        var bundle: Bundle? = null

        // making a delay of 500 ms
        var job: Job? = null
        edit_text_search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_MOVIE_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty() && editable.toString().length >= 3){
                        viewModel.searchMovies(editable.toString())
                    }
                }
            }
        }

        viewModel.searchMovies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { movieResponse ->
                        bundle = Bundle().apply {
                            putSerializable("movie", movieResponse)
                        }
                        text_view_title.text = movieResponse.title
                        text_view_released.text = movieResponse.released
                        Glide.with(this).load(movieResponse.poster).into(image_view_poster)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        display_movie_layout.setOnClickListener {
            findNavController().navigate(
                R.id.action_searchMoviesFragment_to_movieFullViewFragment,
                bundle
            )
        }
    }

    private fun hideProgressBar() {
        pagination_progress_bar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        pagination_progress_bar.visibility = View.VISIBLE
        isLoading = true
    }
}