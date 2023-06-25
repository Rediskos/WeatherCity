package com.test.unlimitedproduction.weathercity.di

import android.content.Context
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.unlimitedproduction.weathercity.BuildConfig
import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import dagger.Module
import dagger.Provides
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_CLIENT = "baseClient"
private const val BASE_INTERCEPTORS = "baseInterceptors"
private const val CITY_CLIENT ="cityClient"
private const val CITY_INTERCEPTORS = "cityInterceptors"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherApi(@Named(BASE_CLIENT)okHttpClient: OkHttpClient, gson: Gson): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCityRetrofit(@Named(CITY_CLIENT)okHttpClient: OkHttpClient, gson: Gson): CityApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.CITY_BASE_URL)
            .client(okHttpClient)
            .build()
            .create(CityApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    @Named(BASE_CLIENT)
    fun provideOkHttpClient(
        @Named(BASE_INTERCEPTORS) interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    @Named(CITY_CLIENT)
    fun provideCityOkHttpClient(
       @Named(CITY_INTERCEPTORS) interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    @Named(BASE_INTERCEPTORS)
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    @Named(CITY_INTERCEPTORS)
    fun provideCityInterceptor(@Named(BASE_INTERCEPTORS) interceptor: ArrayList<Interceptor>): ArrayList<Interceptor> {
        val headerInterceptor = Interceptor { chain ->
            chain.request()
                .newBuilder()
                .header("X-Api-Key", BuildConfig.CITY_API_KEY)
                .build()
                .let {
                    chain.proceed(it)
                }
        }
        interceptor.add(headerInterceptor)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideImageCasher(context: Context) = Glide.with(context)
}
