package com.timkwali.starwarsapp.core.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.core.utils.Constants.BASE_URL
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverter
import com.timkwali.starwarsapp.core.utils.ErrorTypeToErrorTextConverterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideStarwarsApi(retrofit: Retrofit): StarwarsApi {
        return retrofit.create(StarwarsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideErrorTypeConverter(): ErrorTypeToErrorTextConverter {
        return ErrorTypeToErrorTextConverterImpl()
    }
}