package com.example.f32.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.f32.BuildConfig
import com.example.f32.di.dao.CartDao
import com.example.f32.net.api.IndexApi
import com.example.f32.net.repository.IndexRepository
import com.example.f32.util.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.*

val networkModule = module {
    single { provideCache(androidApplication()) }
    single { provideHttpLoggingInterceptor() }
    single { provideChuck(androidApplication()) }

    single { provideHttpClient(get(), get(), get()) }

    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }

    factory { provideIndexApi(get()) }

    factory {
        provideIndexRepository(get(), get())
    }
}

/****************************************************************************************************
 ** Cache/Interceptors
 ****************************************************************************************************/
private fun provideCache(application: Application): Cache {
    val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
    return Cache(cacheDir, Constant.MAX_MEMORY_CACHE)
}

private fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}

private fun provideChuck(application: Application): ChuckerInterceptor = ChuckerInterceptor.Builder(application)
    .collector(ChuckerCollector(application))
    .maxContentLength(250000L)
    .redactHeaders(emptySet())
    .alwaysReadResponseBody(false)
    .build()

/****************************************************************************************************
 ** OkHttp
 ****************************************************************************************************/
private fun provideHttpClient(
    cache: Cache,
    chuck: ChuckerInterceptor,
    logger: HttpLoggingInterceptor,
): OkHttpClient {
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(chuck)
        .addInterceptor(logger)
    return okHttpClientBuilder.build()
}

/****************************************************************************************************
 ** Retrofit and Moshi
 ****************************************************************************************************/
private fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private fun provideRetrofit(okHttpClient: OkHttpClient, mosh: Moshi): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(mosh))
        .build()
}

/****************************************************************************************************
 ** Api
 ****************************************************************************************************/
private fun provideIndexApi(retrofit: Retrofit): IndexApi = retrofit.create(IndexApi::class.java)

/****************************************************************************************************
 ** Repository
 ****************************************************************************************************/
fun provideIndexRepository(
    indexApi: IndexApi,
    cartDao: CartDao
): IndexRepository = IndexRepository(indexApi, cartDao)