package tw.teng.practice.contact

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.support.multidex.MultiDexApplication
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import tw.teng.practice.contact.resource.network.OnApiListener
import tw.teng.practice.contact.resource.network.RoloWebApi
import tw.teng.practice.contact.resource.network.model.APIContacts
import tw.teng.practice.contact.utils.Pref

class ContactApplication : MultiDexApplication() {

    var isNetworkConnected: Boolean = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()

        (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    RoloWebApi.getInstance(applicationContext).users(object :
                        OnApiListener<APIContacts> {
                        override fun onApiTaskSuccess(responseData: APIContacts) {
                            syncLocalData(responseData)
                        }

                        override fun onApiTaskFailure() {
                        }

                    })
                    isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isNetworkConnected = false
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    isNetworkConnected = false
                }
            })
        }

    }

    private fun syncLocalData(responseData: APIContacts) {
        val local = Gson().fromJson(
            Pref.getContacts(applicationContext),
            APIContacts::class.java
        )
        Pref.setContacts(
            applicationContext, Gson().toJson(
                local.sync(responseData)
            )
        )
    }
}