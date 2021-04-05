package tw.teng.practice.contact.resource.network

import android.os.Build
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*


class ApiCallback<T>(
    private val _listener: OnApiListener<T>,
) : Callback<T> {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResponse(call: Call<T>, response: Response<T>) {
        val data = response.body()
        return if (data != null)
            _listener.onApiTaskSuccess(responseData = data)
        else
            _listener.onApiTaskFailure(toString = getErrorMessage(response))
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        _listener.onApiTaskFailure(t.toString())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getErrorMessage(response: Response<T>): String {
        val errorBody = response.errorBody()
        return try {
            if (Objects.isNull(errorBody)) response.message() else errorBody!!.string()
        } catch (e: IOException) {
            throw Exception("could not read error body", e)
        }
    }
}