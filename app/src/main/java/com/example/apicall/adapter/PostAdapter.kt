package com.example.apicall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apicall.R
import com.example.apicall.data.entity.Post

class PostAdapter(private var postList: ArrayList<Post>) : RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false
    companion object{
        private const val LOADING_VIEW = 0
        private const val NORMAL_VIEW = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            NORMAL_VIEW -> MyViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
            LOADING_VIEW -> ProgressHolder(layoutInflater.inflate(R.layout.loading, parent, false))
            else -> ProgressHolder(layoutInflater.inflate(R.layout.loading,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible){
            if (position == postList.size -1)
                LOADING_VIEW
            else
                NORMAL_VIEW
        }else{
            NORMAL_VIEW
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun addItems(postItems: ArrayList<Post>) {
        postList.addAll(postItems)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = postList.size - 1
        getItem(position)
    }

    private fun getItem(position: Int): Post {
        return postList[position]
    }

    fun clear() {
        postList.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val body: TextView = itemView.findViewById(R.id.body)


        override fun clear() {
        }

        override fun onBind(position: Int) {
            //super(itemView)
            val item: Post = postList[position]
            body.text = item.title.toString()
        }
    }

    inner class ProgressHolder(itemView: View) : BaseViewHolder(itemView){
        private val progress : ProgressBar = itemView.findViewById(R.id.progressBar)
        override fun clear() {
        }

        override fun onBind(position: Int) {
            progress.visibility = View.VISIBLE
        }

    }
}