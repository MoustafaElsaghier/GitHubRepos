package com.moustafa.githubrepos.di

import com.moustafa.githubrepos.BuildConfig
import com.moustafa.githubrepos.data.api.GithubAPIService
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addNetworkInterceptor(invoke {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Api", "v2")
                val request = requestBuilder.build()

                it.proceed(request)
            })

        // to print the response body in logger
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return@single builder.build()
    }

    single {
        Retrofit.Builder()
            .client(get() as OkHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GithubAPIService::class.java)
    }

}

