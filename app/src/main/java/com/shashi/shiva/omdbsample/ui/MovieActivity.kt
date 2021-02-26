package com.shashi.shiva.omdbsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shashi.shiva.omdbsample.R
import com.shashi.shiva.omdbsample.db.MoviesDatabase
import com.shashi.shiva.omdbsample.repository.MovieRepository
import kotlinx.android.synthetic.main.activity_main.*

class MovieActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieRepository = MovieRepository(MoviesDatabase(this))
        val viewModelProviderFactory = MovieViewModelProviderFactory(application, movieRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MovieViewModel::class.java)
        // setting up navigation controller
        bottom_navigation_view.setupWithNavController(movie_nav_host_fragment. findNavController())
        navController = findNavController(R.id.movie_nav_host_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.searchMoviesFragment -> {
                Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                bottom_navigation_view.menu.findItem(R.id.searchMoviesFragment).isChecked = true
                navController?.navigate(R.id.searchMoviesFragment)
                return true
            }
            R.id.savedMoviesFragment -> {
                Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                bottom_navigation_view.menu.findItem(R.id.savedMoviesFragment).isChecked = true
                navController?.navigate(R.id.savedMoviesFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}