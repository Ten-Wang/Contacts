package tw.teng.practice.contact.resource.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import tw.teng.practice.contact.BuildConfig
import tw.teng.practice.contact.resource.network.mock.Data
import tw.teng.practice.contact.resource.network.model.Users
import tw.teng.practice.contact.utils.Pref

class AppRepository private constructor(private val _application: Application) {

    companion object {
        private var INSTANCE: AppRepository? = null

        @Synchronized
        fun getInstance(application: Application): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository(application)
            }
            return INSTANCE!!
        }
    }

    var usersLiveData = MutableLiveData<Users>()

    init {
        if (BuildConfig.DEBUG)
            setSharedPref(getMockData())
        usersLiveData.value = getSharedPref()
    }

    private fun setSharedPref(mockData: Users?) {
        Pref.setContacts(_application, Gson().toJson(mockData))
    }

    private fun getSharedPref(): Users? {
        return Gson().fromJson(Pref.getContacts(_application), Users::class.java)
    }


    private fun getMockData(): Users? {
        return Gson().fromJson(Data().userList, Users::class.java)
    }
}