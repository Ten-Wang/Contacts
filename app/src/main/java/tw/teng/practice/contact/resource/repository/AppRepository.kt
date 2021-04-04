package tw.teng.practice.contact.resource.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import tw.teng.practice.contact.resource.network.OnApiListener
import tw.teng.practice.contact.resource.network.RoloWebApi
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
        contactsListLiveData.value = getAPIContactsFormSP()
        displayModeLiveData.value = DisplayState.ALL.state
    }

    private fun getMockData(): APIContacts? {
        return Gson().fromJson(Data().mList, APIContacts::class.java)
    }

    private fun setSharedPref(mockData: APIContacts?) {
        Pref.setContacts(_application, Gson().toJson(mockData))
    }

    private fun getAPIContactsFormSP(): APIContacts? {
        return Gson().fromJson(Pref.getContacts(_application), APIContacts::class.java)
    }

    private fun saveStarredToSharedPref(storedStarred: HashMap<Int, Boolean>) {
        Pref.insertToSP(_application, storedStarred)
    }

    private fun getStarredFormSharedPref(): HashMap<Int, Boolean> {
        return Pref.readFromSP(_application)
    }

    fun clickStarred(position: Int) {
        val apiContacts = contactsListLiveData.value
        val starred = apiContacts?.contacts?.get(position)?.starred
        apiContacts?.contacts?.get(position)?.starred = (!starred!!)
        saveStarred(apiContacts.contacts?.get(position)!!)
        Pref.setContacts(
            _application,
            Gson().toJson(apiContacts)
        )
        contactsListLiveData.postValue(apiContacts)
    }

    private fun saveStarred(contact: APIContacts.Contacts) {
        val storedStarred = getStarredFormSharedPref()
        if (storedStarred[contact.id] == true) {
            storedStarred.remove(contact.id)
        } else {
            storedStarred[contact.id] = true
        }
        saveStarredToSharedPref(storedStarred)
    }


    fun selectDisplayState(event: Int) {
        var apiContacts = contactsListLiveData.value
        when (event) {
            STARRED.state -> {
                // all to starred
                val list = getAPIContactsFormSP()?.contacts
                apiContacts?.contacts =
                    list?.filter { contacts -> (contacts.starred) }?.toMutableList()
            }
            else -> { // same as allClick
                // starred to all
                apiContacts = getAPIContactsFormSP()
            }
        }
        contactsListLiveData.postValue(apiContacts)
        displayModeLiveData.value = event
        displayModeLiveData.postValue(event)
    }

    fun onNetworkAvailable() {
        RoloWebApi.getInstance(_application).users(object :
            OnApiListener<APIContacts> {
            override fun onApiTaskSuccess(responseData: APIContacts) {
                syncLocalData(responseData)
            }

            override fun onApiTaskFailure() {
            }

        })
    }

    private fun syncLocalData(responseData: APIContacts) {
        val local = Gson().fromJson(
            Pref.getContacts(_application),
            APIContacts::class.java
        )
        Pref.setContacts(
            _application, Gson().toJson(
                local.sync(responseData, Pref.readFromSP(_application))
            )
        )
        contactsListLiveData.value = local
        contactsListLiveData.postValue(local)
    }
}