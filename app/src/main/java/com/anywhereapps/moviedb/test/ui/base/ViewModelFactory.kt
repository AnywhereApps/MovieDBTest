package com.anywhereapps.moviedb.test.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anywhereapps.moviedb.test.data.api.ApiHelper
import com.anywhereapps.moviedb.test.data.repository.MainRepository
import com.anywhereapps.moviedb.test.ui.main.viewmodel.DetailViewModel
import com.anywhereapps.moviedb.test.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper:ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
               MainRepository(
                    apiHelper
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                MainRepository(
                    apiHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

