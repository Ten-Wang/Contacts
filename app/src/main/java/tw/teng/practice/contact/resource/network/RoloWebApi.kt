package tw.teng.practice.contact.resource.network

import android.content.Context
import tw.teng.practice.contact.resource.network.api_interface.ContactInterface
import tw.teng.practice.contact.resource.network.model.Users

class RoloWebApi constructor(context: Context) : WebApi(context) {

    companion object {
        private var _instance: RoloWebApi? = null

        @Synchronized
        fun getInstance(context: Context): RoloWebApi {
            if (_instance == null) {
                _instance = RoloWebApi(context)
            }
            return _instance!!
        }
    }

    private val _tag = RoloWebApi::class.java.simpleName

    fun users(listener: OnApiListener<Users>) {
        apiRetrofit.create(ContactInterface::class.java)
            .getUsers()
            .enqueue(ApiCallback(listener))
    }

}
