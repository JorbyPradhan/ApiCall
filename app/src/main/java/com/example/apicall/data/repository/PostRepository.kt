package com.example.apicall.data.repository

import com.example.apicall.data.entity.Post
import com.example.apicall.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(
    private val api: Api
) {
        fun getPost(): Flow<List<Post>> = flow {
            val response = api.getPost()
            emit(response)
        }.flowOn(Dispatchers.IO)
}
