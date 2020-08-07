package com.anywhereapps.moviedb.test.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anywhereapps.moviedb.test.data.model.Genre
import com.anywhereapps.moviedb.test.data.repository.MainRepository
import com.anywhereapps.moviedb.test.utils.Resource
import kotlinx.coroutines.Dispatchers


class DetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getDetail(token: String, movieId :  Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getDetail(token, movieId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getGenre(gen : List<Genre>): String {
        val sb = StringBuilder()

        gen.forEach {
            sb.append(it.name).append(", ")
        }
        return  sb.toString()
    }
}