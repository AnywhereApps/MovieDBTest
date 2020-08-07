package com.anywhereapps.moviedb.test.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anywhereapps.moviedb.test.R
import com.anywhereapps.moviedb.test.data.api.RetrofitBuilder
import com.anywhereapps.moviedb.test.data.model.Results
import com.anywhereapps.moviedb.test.ui.main.adapter.MovieAdapter.MovieViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class MovieAdapter(
    private val list: ArrayList<Results>,

    private val listener: (Results) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Results) {
            itemView.apply {
                businessName.text = movie.title
                locationText.text = movie.release_date
                Glide.with(imageViewAvatar.context)
                    .load(RetrofitBuilder.IMAGE_URL + movie.backdrop_path)
                    .into(imageViewAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    fun addData(results: List<Results>) {
        this.list.apply {
            clear()
            addAll(results)
        }

    }
}