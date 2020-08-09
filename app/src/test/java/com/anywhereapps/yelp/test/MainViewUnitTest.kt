package com.anywhereapps.moviedb.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anywhereapps.moviedb.test.data.api.ApiHelper
import com.anywhereapps.moviedb.test.data.model.MovieData
import com.anywhereapps.moviedb.test.data.model.Results
import com.anywhereapps.moviedb.test.data.repository.MainRepository
import com.anywhereapps.moviedb.test.ui.main.viewmodel.MainViewModel
import com.anywhereapps.moviedb.test.utils.Resource
import com.anywhereapps.yelp.test.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewUnitTest {

    val token = "abcded"

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper


    private lateinit var repository: MainRepository


    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<MovieData>>


    @Before
    fun setUp() {

    }

    @Test
    fun onSuccessTest() {
        repository = MainRepository(apiHelper)
        var responseData = dummyData()

        testCoroutineRule.runBlockingTest {
            doReturn(responseData)
                .`when`(apiHelper)
                .getMovies(token)
            val viewModel = MainViewModel(repository)
            viewModel.getMovies(token).observeForever(apiUsersObserver)
            verify(repository).getMovies(token)
            verify(apiUsersObserver).onChanged(Resource.success(responseData))
            viewModel.getMovies(token).removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun onErrorTest() {
        repository = MainRepository(apiHelper)
        var responseData = dummyData()

        testCoroutineRule.runBlockingTest {
            val errorMessage = "Bingo Error ...."
            doThrow(RuntimeException(errorMessage))
                .`when`(repository)
                .getMovies(token)
            val viewModel = MainViewModel(repository)
            viewModel.getMovies(token).observeForever(apiUsersObserver)
            verify(repository).getMovies(token)
            verify(apiUsersObserver).onChanged(Resource.error(null, errorMessage))
            viewModel.getMovies(token).removeObserver(apiUsersObserver)
        }
    }


    fun  dummyData() : MovieData {
        val page = 1
        val results: List<Results> = mutableListOf()
        val total_pages = 2
        val total_results = 25

        return MovieData(page, results, total_pages, total_results)
    }
}
