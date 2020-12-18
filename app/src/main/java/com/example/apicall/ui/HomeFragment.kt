package com.example.apicall.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.apicall.R
import com.example.apicall.adapter.PostAdapter
import com.example.apicall.data.entity.Post
import com.example.apicall.ui.PaginationListener.PAGE_START
import kotlinx.android.synthetic.main.home_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.Exception

class HomeFragment : Fragment(),KodeinAware,SwipeRefreshLayout.OnRefreshListener{
    override val kodein by kodein()

    private companion object{
        const val ITEM_COUNT = 10
    }

    private lateinit var viewModel: HomeViewModel
    private var isLoad  = false
    private var isLast  = false
    private var count =0
    private var currentItem = PAGE_START
    private val factory: HomeViewModelFactory by instance()
    private lateinit var postAdapter: PostAdapter
    private lateinit var postList : ArrayList<Post>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        postList = ArrayList()
        //paginationList = ArrayList()
        swipeRefresh.setOnRefreshListener(this)
        val layoutManager = LinearLayoutManager(context)
        rec_flow.layoutManager = layoutManager
        postAdapter = PostAdapter(ArrayList())
        rec_flow.apply {
            setHasFixedSize(true)
            adapter = postAdapter
        }
        bindApi()

        rec_flow.run {
            rec_flow.addOnScrollListener(object : PaginationListener(layoutManager){
                override fun loadMoreItems() {
                    isLoad = true
                    currentItem++
                    loadNextList()
                }

                override fun isLastPage(): Boolean {
                    return isLast
                }

                override fun isLoading(): Boolean {
                   return isLoad
                }

            })
        }
    }

    private fun bindApi() {
        viewModel.getPost()
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            postList.addAll(it)
            rec_flow.visibility = View.VISIBLE
            loadNextList()
        })

    }

    private fun loadNextList(){
        try {
            val paginationList : ArrayList<Post> = ArrayList()
            Handler().postDelayed({
                for (i in 0 until ITEM_COUNT){
                    paginationList.add(postList[count])
                    count++
                }
                if (currentItem != PAGE_START) postAdapter.removeLoading()
                postAdapter.addItems(paginationList)
                swipeRefresh.isRefreshing = false


                if (currentItem < ITEM_COUNT){
                    postAdapter.addLoading()
                }else{
                    isLast = true
                }
                isLoad = false

            },1500)

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onRefresh() {
        count = 0
        currentItem = PAGE_START
        isLast = false
        postAdapter.clear()
        loadNextList()
    }

}