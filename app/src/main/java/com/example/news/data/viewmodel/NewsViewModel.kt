package com.example.news.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.News
import com.example.news.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val newsLiveData: MutableLiveData<List<News>> = MutableLiveData()
    val topNewsLiveData: MutableLiveData<List<News>> = MutableLiveData()

    private var allNews = mutableListOf<News>()
    private val specificTypeOfNews = mutableListOf<News>()
    private val topNews = mutableListOf<News>()
    private var pageCount = 1

   fun getNews(type: String){
       viewModelScope.launch {
           allNews.clear()
           allNews.addAll(repository.getNews(pageCount))
           allNews.forEach {
               if(it.type == type){
                   specificTypeOfNews.add(it)
                   if(it.top == "1"){
                       topNews.add(it)
                   }
               }
           }
           newsLiveData.value = specificTypeOfNews
           topNewsLiveData.value = topNews
           pageCount++
       }
   }
}