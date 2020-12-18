package com.example.apicall

import android.app.Application
import com.example.apicall.data.network.Api
import com.example.apicall.data.network.NetworkConnectionInterceptor
import com.example.apicall.data.repository.PostRepository
import com.example.apicall.ui.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BaseApplication : Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind() from singleton {
            NetworkConnectionInterceptor(instance())
        }
        bind() from singleton { Api(instance()) }
        bind() from singleton { PostRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }

}