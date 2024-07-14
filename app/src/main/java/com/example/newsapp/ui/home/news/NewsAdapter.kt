package com.example.newsapp.ui.home.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.domain.entity.newsResponce.News
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(private var newsList:List<News?>?=null):RecyclerView.Adapter<NewsAdapter.ViewHolder> (){



    class ViewHolder(val itemBinding:ItemNewsBinding) :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding=ItemNewsBinding
            .inflate(LayoutInflater.from(parent.context)
                ,parent,false)

        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = newsList?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news=newsList!![position]
        holder.itemBinding.news=news    //replaced this line by 4 following lines
        holder.itemBinding.invalidateAll()
//        holder.itemBinding.tittle.text=news?.title
//        holder.itemBinding.description.text=news?.description
//        holder.itemBinding.time.text=news?.publishedAt
//        holder.itemBinding.sources.text= news?.source?.name


//       Glide.with(holder.itemView)
//           .load(news?.urlToImage)
//           .placeholder(R.drawable.splash_logo)
//           .into(holder.itemBinding.image)

        holder.itemBinding.image.setOnClickListener{onItemClickListener?.onItemClick(position,news!!)}
//mohamedragb01097434995@gmail.com
    }
    fun bindViews(articles: List<News?>?) {
        newsList=articles
        notifyDataSetChanged()
    }
    var onItemClickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(position: Int,news: News)
    }
}