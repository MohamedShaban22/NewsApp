package com.example.newsapp.ui.home.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.newsResponce.News
import com.example.domain.entity.sourcesResponce.Source
import com.example.domain.usecase.GetNews
import com.example.domain.usecase.GetNewsSearch
import com.example.domain.usecase.GetSources
import com.example.newsapp.ui.ViewError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsUseCase: GetNews, val getSources: GetSources, val getSearchList: GetNewsSearch
) :
    ViewModel() {
    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourceLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val newsSearchLiveData = MutableLiveData<List<News?>?>()

    val errorLiveData = MutableLiveData<ViewError>()


     suspend fun getNewsSearch(q: String) {
        shouldShowLoading.postValue(true)
        viewModelScope.launch {
            try {
                shouldShowLoading.postValue(false)
                newsSearchLiveData.postValue(getSearchList(q = q).articles)
            } catch (e: Exception) {

                errorLiveData.postValue(
                    ViewError(message = e.localizedMessage ?: "") {
                        viewModelScope.launch {
                            getNewsSearch(q)
                        }
                    }
                )
            }
        }


    }


suspend fun getNewsSources(category: String) {
    // shouldShowLoading.value=true
    shouldShowLoading.postValue(true)
    //viewBinding.progressBar.isVisible=true
    viewModelScope.launch {
        shouldShowLoading.postValue(false)
        try {
            sourceLiveData.postValue(getSources(category = category).sources)
        } catch (e: Exception) {
            errorLiveData.postValue(
                ViewError(message = e.localizedMessage ?: "")
                {
                    viewModelScope.launch {
                        getNewsSources(category)
                    }
                }
            )
        }
    }

}

suspend fun getNews(sourceId: String) {
    shouldShowLoading.postValue(true)
    viewModelScope.launch {
        try {
            shouldShowLoading.postValue(false)
            newsLiveData.postValue(newsUseCase(sources = sourceId).articles)
        } catch (e: HttpException) {

            errorLiveData.postValue(
                ViewError(message = e.localizedMessage ?: "") {
                    viewModelScope.launch {
                        getNews(sourceId)
                    }
                })
        } catch (e: Exception) {
            Log.e("NewsEx :", e.localizedMessage ?: "")
        }
    }
}

}