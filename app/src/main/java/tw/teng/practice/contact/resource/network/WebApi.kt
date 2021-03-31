package tw.teng.practice.contact.resource.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tw.teng.practice.contact.R
import java.util.concurrent.TimeUnit

abstract class WebApi(context: Context) {
    private val _apiUrlPrefix: String = context.getString(R.string.api_url_prefix)

    companion object {
        private const val READ_TIMEOUT = 30 // seconds
    }

    private fun initRetrofit(
        baseUrl: String,
        factory: Converter.Factory,
        timeout: Int = READ_TIMEOUT
    ): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient().newBuilder()
            .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    val apiRetrofit: Retrofit by lazy {
        return@lazy initRetrofit(_apiUrlPrefix, GsonConverterFactory.create())
    }
}
