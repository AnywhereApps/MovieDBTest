package com.anywhereapps.moviedb.test.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anywhereapps.moviedb.test.R
import com.anywhereapps.moviedb.test.data.api.ApiHelper
import com.anywhereapps.moviedb.test.data.api.RetrofitBuilder
import com.anywhereapps.moviedb.test.data.model.Results
import com.anywhereapps.moviedb.test.ui.base.ViewModelFactory
import com.anywhereapps.moviedb.test.ui.main.adapter.MovieAdapter
import com.anywhereapps.moviedb.test.ui.main.viewmodel.MainViewModel
import com.anywhereapps.moviedb.test.utils.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    private lateinit var layout: View
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.main_layout)
        setupViewModel()
        setupUI()

        getData(RetrofitBuilder.TOKEN)
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
           ViewModelFactory(
               ApiHelper(
                   RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {

        // recylerview setup
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(
            arrayListOf()
        ) { item ->
            goToDetailPage(item)
        }

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }




    private fun getData(token: String){

        viewModel.getMovies(token).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { data -> updateView(data.results) }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                     Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun updateView(movies : List<Results>){
        if(movies?.size == 0)
            Toast.makeText(this, R.string.empty_data_msg, Toast.LENGTH_SHORT).show()
        adapter.apply {

            addData(movies)
            notifyDataSetChanged()
        }
    }


    private  fun goToDetailPage(movie : Results){

        val nextScreenIntent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("id", movie.id)
        }
        startActivity(nextScreenIntent)
    }
}
