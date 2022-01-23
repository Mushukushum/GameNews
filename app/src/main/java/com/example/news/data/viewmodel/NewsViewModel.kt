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

    val listOfNews: MutableLiveData<List<News>> = MutableLiveData()

   fun getNews(page: Int){
       viewModelScope.launch {
           listOfNews.value = repository.getNews(page)
       }
   }
}