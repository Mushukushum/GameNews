package com.example.news.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.data.News
import com.example.news.databinding.RowLayoutBinding

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var newsList = mutableListOf<News>()

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(news: News){
            binding.gameNews = news
            binding.executePendingBindings()
            Glide.with(binding.root.context).load(news.img).into(binding.imageView)
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder , position: Int) {
        val currentItem = newsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setData(news: News){
        this.newsList.add(news)
        notifyDataSetChanged()
    }

}