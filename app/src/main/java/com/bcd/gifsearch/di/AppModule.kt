package com.bcd.gifsearch.di

import com.bcd.gifsearch.api.GIFApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

//        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val client: OkHttpClient = OkHttpClient.Builder().apply {
//            addInterceptor(interceptor)
//        }.build()

        return Retrofit.Builder()
            .baseUrl(GIFApi.BASE_URL)
//            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGIFApi(retrofit: Retrofit): GIFApi {
        return retrofit.create(GIFApi::class.java)
    }
}