package com.example.newsapp.ui.home.news

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.model.ApiConstants
import com.example.newsapp.api.model.ApiManager
import com.example.newsapp.api.model.SearchResponse.SearchResponse
import com.example.newsapp.api.model.newsResponce.News
import com.example.newsapp.api.model.newsResponce.NewsResponse
import com.example.newsapp.api.model.sourcesResponce.Source
import com.example.newsapp.api.model.sourcesResponce.SourcesResponse
import com.example.newsapp.ui.showMessage
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.OnTryAgainClickListener
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.home.HomeActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
   lateinit var viewBinding:FragmentNewsBinding
   lateinit var source:Source
    lateinit var viewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[NewsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding=FragmentNewsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

        val args = this.arguments
        val inputData = args?.getInt("id")
        if (inputData==R.id.sports_img)
            viewModel.getNewsSources("sports")
        else if (inputData==R.id.entertainment_img)
            viewModel. getNewsSources("entertainment")
        else if (inputData==R.id.health_img)
            viewModel. getNewsSources("health")
        else if (inputData==R.id.science_img)
            viewModel. getNewsSources("science")
        else if (inputData==R.id.business_img)
            viewModel. getNewsSources("business")
        else if (inputData==R.id.technology_img)
            viewModel.getNewsSources("technology")



        adapter.onItemClickListener= object : NewsAdapter.OnItemClickListener{


            override fun onItemClick(position: Int, news: News) {
                val intent = Intent(context, Details::class.java)
                intent.putExtra("tittle", news.title)
                intent.putExtra("description", news.description)
                intent.putExtra("image",news.urlToImage)
                intent.putExtra("time",news.publishedAt)
                intent.putExtra("source",news.source?.name)
                intent.putExtra("news_url",news.url)
                startActivity(intent)
            }

        }
    }

    private fun initObservers() {
//        viewModel.shouldShowLoading
//        .observe(viewLifecycleOwner
//        ) { isVisable -> viewBinding.progressBar.isVisible = isVisable }

        viewModel.sourceLiveData.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)

        }

        viewModel.newsLiveData.observe(viewLifecycleOwner){
            adapter.bindViews(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            handelError(it)
        }
    }


    val adapter=NewsAdapter()
    private fun initViews() {
        viewBinding.recyclerView.adapter=adapter
        viewBinding.vm=viewModel
        viewBinding.lifecycleOwner=this
    }



    private fun bindTabs(sources: List<Source?>?) {
        if(sources==null)return
        else{
            sources.forEach{Source->
                val tab =viewBinding.tabLayout.newTab()
                tab.text=Source?.name
                tab.tag=Source

                viewBinding.tabLayout.addTab(tab)
            }
            viewBinding.tabLayout.addOnTabSelectedListener(
                object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                         source=tab?.tag as Source
                        viewModel.getNews(source.id)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                         source=tab?.tag as Source
                         viewModel. getNews(source.id)
                    }

                }
            )
            viewBinding.tabLayout.getTabAt(0)?.select()
        }
    }



    //get News Search

    private fun getNewsSearch(q: String?) {
        viewBinding.progressBar.isVisible=true
        ApiManager.getApi()
            .search(q = q?:"")
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    viewBinding.progressBar.isVisible=false
                    if (response.isSuccessful){
                        //show News
                        adapter.bindViews(response.body()?.articles)
                        return
                    }
                    val responseJasonError =response.errorBody()?.string()
                    val errorResponse =Gson().fromJson(responseJasonError,NewsResponse::class.java)
                    handelError(errorResponse.message?:""){
                        getNewsSearch(q)
                    }

                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    viewModel.shouldShowLoading.postValue(false)
                    //viewBinding.progressBar.isVisible=false
                    //***********************************************
                    viewModel.errorLiveData.postValue(ViewError(throwable = t){
                        getNewsSearch(q)
                    })
//                    handelError(t){
//                        getNewsSearch(q)
//                    }
                }
            })
    }




     fun handelError(viewError:ViewError){
        showMessage(message = viewError.message?:viewError.throwable?.localizedMessage?:"Some thing went wrong"
            , posActionName = "try again"
            , posAction = { dialogInterface, i ->
                dialogInterface.dismiss()
                viewError.onTryAgainClickListener?.onTryAgainClick()
            }, negActionName = "Cansel"
            , negAction = { dialogInterface, i ->
                dialogInterface.dismiss()

            }
        )
    }

    fun handelError(message:String,onClick: OnTryAgainClickListener){
        showMessage(message = message
            , posActionName = "try again"
            , posAction = { dialogInterface, i ->
                dialogInterface.dismiss()
                onClick.onTryAgainClick()
            }, negActionName = "Cansel"
            , negAction = { dialogInterface, i ->
                dialogInterface.dismiss()

            }
        )
    }


    private var searchQuery: String? = null
    fun updateSearchQuery(newText: String?) {
        // Update the fragment's search query
        // This method will be called from the activity's onQueryTextChange callback
        searchQuery = newText
        // Perform any relevant search query update logic here
        // Update the search results in the fragment
        updateSearchResults()
    }
    private fun updateSearchResults() {
        if (searchQuery!="")
        getNewsSearch(searchQuery)
        else
            viewModel.getNews(source.id)


        // Use the searchQuery to update the search results in the fragment
        // For example, update the UI with the search results
    }
}


