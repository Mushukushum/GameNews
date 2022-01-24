package com.example.news.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.data.viewmodel.NewsViewModel
import com.example.news.R
import com.example.news.list.adapter.TopNewsAdapter
import com.example.news.databinding.FragmentListBinding
import com.example.news.list.adapter.NewsAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: Fragment() {

    private val mNewsViewModel: NewsViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: NewsAdapter by lazy { NewsAdapter() }
    private val topNewsAdapter: TopNewsAdapter by lazy { TopNewsAdapter() }

    lateinit var newsType: String

    fun newInstance(type: String): NewsFragment {
        val args = Bundle()
        args.putString("type", type)
        val fragment = NewsFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {

        //Variables for pagination
        var previousTotal = 0
        var loading = true
        val visibleThreshold = 5
        var firstVisibleItem: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val layoutManager = LinearLayoutManager(requireActivity())

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        val topNewsViewPager = binding.topNews
        topNewsViewPager.adapter = topNewsAdapter

        val tabLayout = binding.tabLayout





        TabLayoutMediator(tabLayout, topNewsViewPager){
                _ , _ ->
        }.attach()

        newsType = arguments?.getString("type").toString()

        mNewsViewModel.getNews(newsType)
        mNewsViewModel.newsLiveData.observe(this,
            { news ->
                adapter.setData(news)
            }
        )
        mNewsViewModel.topNewsLiveData.observe(this, {
            topNews ->
                topNewsAdapter.setData(topNews)
                //Sets visibility to GONE if no elements available for "Top news" view pager
                if(topNewsAdapter.itemCount > 0){
                    topNewsViewPager.visibility = View.VISIBLE
                    tabLayout.visibility = View.VISIBLE
                } else if(topNewsAdapter.itemCount == 0) {
                    topNewsViewPager.visibility = View.GONE
                    tabLayout.visibility = View.GONE
                }
        })

        //Pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView , dx: Int , dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = recyclerView.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount
                    <= firstVisibleItem + visibleThreshold
                ) {
                    mNewsViewModel.getNews(newsType)
                    loading = true
                }
            }
        })

        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu , inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}