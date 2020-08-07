package com.anywhereapps.moviedb.test.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anywhereapps.moviedb.test.R
import com.anywhereapps.moviedb.test.data.api.ApiHelper
import com.anywhereapps.moviedb.test.data.api.RetrofitBuilder
import com.anywhereapps.moviedb.test.data.model.DetailData
import com.anywhereapps.moviedb.test.ui.base.ViewModelFactory
import com.anywhereapps.moviedb.test.ui.main.viewmodel.DetailViewModel
import com.anywhereapps.moviedb.test.utils.Status
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_layout.*


class MovieDetailActivity : AppCompatActivity(){


//    private lateinit var layout: View
    private lateinit var viewModel: DetailViewModel

    private  var movieId : Int?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_layout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        getData(RetrofitBuilder.TOKEN)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
           ViewModelFactory(
               ApiHelper(
                   RetrofitBuilder.apiService
                )
            )
        ).get(DetailViewModel::class.java)
    }

    private fun updateUI(data : DetailData) {
       titleTxt.text = data?.title
        overview.text = data?.overview
        genres.text = viewModel.getGenre(data?.genres)

        Glide.with(this)
            .load(RetrofitBuilder.IMAGE_URL + data?.backdrop_path)
            .into(image)
    }




    private fun getData(token: String){

        movieId = intent?.getIntExtra("id", 0)
        viewModel.getDetail(token, movieId!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progress.visibility = View.GONE
                        resource.data?.let { data -> updateUI(data) }
                    }
                    Status.ERROR -> {
                        progress.visibility = View.GONE
                     Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progress.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
