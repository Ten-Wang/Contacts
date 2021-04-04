package tw.teng.practice.contact.resource.network

import android.content.Context
import tw.teng.practice.contact.resource.network.api_interface.ContactInterface
import tw.teng.practice.contact.resource.network.model.APIContacts

class RoloWebApi constructor(context: Context) : WebApi(context) {

    companion object {
        private var instance: RoloWebApi? = null

        @Synchronized
        fun getInstance(context: Context): RoloWebApi {
            if (instance == null) {
                instance = RoloWebApi(context)
            }
            return instance!!
        }
    }

    fun users(listener: OnApiListener<APIContacts>) {
        apiRetrofit.create(ContactInterface::class.java)
            .getUsers()
            .enqueue(ApiCallback(listener))
    }

}
