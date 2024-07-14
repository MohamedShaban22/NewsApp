package com.example.newsapp.ui.home.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.domain.entity.newsResponce.News
import com.example.domain.entity.sourcesResponce.Source
import com.example.newsapp.ui.showMessage
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.ui.OnTryAgainClickListener
import com.example.newsapp.ui.ViewError
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class NewsFragment : Fragment() {
   lateinit var viewBinding:FragmentNewsBinding
   lateinit var source: Source
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


        lifecycleScope.launch {
            initViews()
            initObservers()
        }


            val args = this.arguments

        lifecycleScope.launch {
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
        }




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

    private suspend fun initObservers() {
        viewModel.shouldShowLoading
        .observe(viewLifecycleOwner)
        { isVisable ->
            viewBinding.progressBar.isVisible = isVisable }

        viewModel.sourceLiveData.observe(viewLifecycleOwner) { sources ->
            lifecycleScope.launch {
                bindTabs(sources)
            }


        }

        viewModel.newsLiveData.observe(viewLifecycleOwner){
            adapter.bindViews(it)
        }
        viewModel.newsSearchLiveData.observe(viewLifecycleOwner){
            adapter.bindViews(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            handelError(it)
        }


    }


    val adapter=NewsAdapter()
    private suspend fun initViews() {
        viewBinding.recyclerView.adapter=adapter
        viewBinding.vm=viewModel
        viewBinding.lifecycleOwner=this
    }



    suspend fun bindTabs(sources: List<Source?>?) {
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
                        lifecycleScope.launch {
                            viewModel.getNews(source.id ?:"")
                        }

                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                         source=tab?.tag as Source
                        lifecycleScope.launch {
                            viewModel.getNews(source.id ?:"")
                        }
                    }

                }
            )
            viewBinding.tabLayout.getTabAt(0)?.select()
        }
    }



    //get News Search





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
    suspend fun updateSearchQuery(newText: String?) {
        // Update the fragment's search query
        // This method will be called from the activity's onQueryTextChange callback
        searchQuery = newText
        // Perform any relevant search query update logic here
        // Update the search results in the fragment
        updateSearchResults()
    }
    private suspend fun updateSearchResults() {
        if (searchQuery!="")
            viewModel.getNewsSearch(searchQuery!!)
     //   getNewsSearch(searchQuery)
        else
        {
            viewModel.getNews(source.id ?:"")
        }



        // Use the searchQuery to update the search results in the fragment
        // For example, update the UI with the search results
    }
}


