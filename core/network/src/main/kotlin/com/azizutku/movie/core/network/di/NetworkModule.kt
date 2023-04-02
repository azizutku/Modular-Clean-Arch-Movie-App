package com.azizutku.movie.core.network.di

import android.content.Context
import com.azizutku.movie.core.network.BuildConfig
import com.azizutku.movie.core.network.ResultCallAdapterFactory
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

private const val CONTENT_TYPE = "application/json"
private const val QUERY_NAME_API_KEY = "api_key"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_API_BASE_URL)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(httpClient)
        .build()

    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
                addInterceptor(chuckerInterceptor)
            }
        }.build()

    @Singleton
    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideConverterFactory(json: Json): Converter.Factory = json.asConverterFactory(CONTENT_TYPE.toMediaType())

    @Singleton
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = ResultCallAdapterFactory()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector,
    ): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()

    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector =
        ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder().addQueryParameter(
            QUERY_NAME_API_KEY,
            BuildConfig.TMDB_API_KEY,
        ).build()
        chain.proceed(originalRequest.newBuilder().url(url).build())
    }
}
