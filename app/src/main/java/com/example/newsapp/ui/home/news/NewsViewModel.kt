package com.example.newsapp.ui.home.news

import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.model.ApiConstants
import com.example.newsapp.api.model.ApiManager
import com.example.newsapp.api.model.newsResponce.News
import com.example.newsapp.api.model.newsResponce.NewsResponse
import com.example.newsapp.api.model.sourcesResponce.Source
import com.example.newsapp.api.model.sourcesResponce.SourcesResponse
import com.example.newsapp.ui.ViewError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel:ViewModel() {
    val shouldShowLoading=MutableLiveData<Boolean>()
    val sourceLiveData= MutableLiveData<List<Source?>?>()
    val newsLiveData=MutableLiveData<List<News?>?>()
    val errorLiveData=MutableLiveData<ViewError>()
     fun getNewsSources(category:String) {
       // shouldShowLoading.value=true
        shouldShowLoading.postValue(true)
        //viewBinding.progressBar.isVisible=true
        ApiManager.getApi()
        .getResources(ApiConstants.apiKey,category)
        .enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                shouldShowLoading.postValue(false)
                if(response.isSuccessful) {
                    //show tabs un fragment
                  //  bindTabs(response.body()?.sources)
                    sourceLiveData.postValue(response.body()?.sources)

                }else{
                    val errorBodyJasonString= response.errorBody()?.string()
                    val response= Gson().fromJson(errorBodyJasonString, SourcesResponse::class.java)
                    errorLiveData.postValue(
                        ViewError(message = response.message)
                        { getNewsSources(category) }
                    )

                }

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                shouldShowLoading.postValue(false)
                errorLiveData.postValue(
                    ViewError(throwable = t)
                    { getNewsSources(category) }
                )
            }
        })
    }

    fun getNews(sourceId: String?) {
        shouldShowLoading.postValue(true)
        ApiManager.getApi()
        .getNews(sources = sourceId?:"")
        .enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                shouldShowLoading.postValue(false)
                if (response.isSuccessful){
                    //show News
                    newsLiveData.postValue(response.body()?.articles)
                    return
                }
                val responseJasonError =response.errorBody()?.string()
                val errorResponse =Gson().fromJson(responseJasonError, NewsResponse::class.java)
                errorLiveData.postValue(
                    ViewError(message = errorResponse.message)
                    { getNews(sourceId) }
                )
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                shouldShowLoading.postValue(false)

                errorLiveData.postValue(
                    ViewError(throwable = t)
                    { getNews(sourceId) }
                )
            }
        })
    }
}