package tw.teng.practice.contact.resource.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import tw.teng.practice.contact.resource.network.mock.Data
import tw.teng.practice.contact.resource.network.model.APIContacts
import tw.teng.practice.contact.ui.contact.DisplayState
import tw.teng.practice.contact.ui.contact.DisplayState.STARRED
import tw.teng.practice.contact.utils.Pref

class AppRepository private constructor(private val _application: Application) {

    companion object {
        private var instance: AppRepository? = null

        @Synchronized
        fun getInstance(application: Application): AppRepository {
            if (instance == null) {
                instance = AppRepository(application)
            }
            return instance!!
        }
    }

    var contactsListLiveData = MutableLiveData<APIContacts>()
    var displayModeLiveData = MutableLiveData<Int>()

    init {
//        if (BuildConfig.DEBUG)
//            setSharedPref(getMockData())
        contactsListLiveData.value = getSharedPref()
        displayModeLiveData.value = DisplayState.ALL.state
    }

    private fun getMockData(): APIContacts? {
        return Gson().fromJson(Data().mList, APIContacts::class.java)
    }

    private fun setSharedPref(mockData: APIContacts?) {
        Pref.setContacts(_application, Gson().toJson(mockData))
    }

    private fun getSharedPref(): APIContacts? {
        return Gson().fromJson(Pref.getContacts(_application), APIContacts::class.java)
    }

    fun clickStarred(position: Int) {
        val apiContacts = contactsListLiveData.value
        val starred = apiContacts?.contacts?.get(position)?.starred
        apiContacts?.contacts?.get(position)?.starred = (!starred!!)
        saveStarred(apiContacts)
        contactsListLiveData.postValue(apiContacts)
    }

    private fun saveStarred(resource: APIContacts?) {
        // read all data
        val stored = getSharedPref()
        // compare id
        for (c: APIContacts.Contacts in resource?.contacts!!) {
            for (contact: APIContacts.Contacts in stored?.contacts!!) {
                if (contact.id == c.id) {
                    contact.starred = c.starred
                }
            }
        }
        // record real star position
        Pref.setContacts(
            _application,
            Gson().toJson(stored)
        )
    }

    fun selectDisplayState(event: Int) {
        var apiContacts = contactsListLiveData.value
        when (event) {
            STARRED.state -> {
                // all to starred
                val list = getSharedPref()?.contacts
                apiContacts?.contacts =
                    list?.filter { contacts -> (contacts.starred) }?.toMutableList()
                contactsListLiveData.postValue(apiContacts)
            }
            else -> { // same as allClick
                // starred to all
                apiContacts = getSharedPref()
                contactsListLiveData.postValue(apiContacts)
            }
        }
        displayModeLiveData.value = event
        displayModeLiveData.postValue(event)
    }
}