package tw.teng.practice.contact.resource.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCallback<T>(
    private val _listener: OnApiListener<T>,
) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val data = response.body()
        return if (data != null)
            _listener.onApiTaskSuccess(responseData = data)
        else
            _listener.onApiTaskFailure()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        _listener.onApiTaskFailure()
    }
}