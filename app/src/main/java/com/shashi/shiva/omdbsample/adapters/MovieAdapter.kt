package com.shashi.shiva.omdbsample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashi.shiva.omdbsample.R
import com.shashi.shiva.omdbsample.models.MovieResponse
import kotlinx.android.synthetic.main.single_row_item_view.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<MovieResponse>(){
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_row_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(movie.poster).into(image_view_poster)
            text_view_title.text = movie.title
            text_view_released.text = movie.released
            text_view_actors.text = movie.actors
            text_view_run_time.text = movie.runtime

            setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }
    }

    // To open on a web page
    private var onItemClickListener: ((MovieResponse) -> Unit)? = null

    fun setCustomOnItemClickListener(listener: (MovieResponse) -> Unit) {
        onItemClickListener = listener
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}