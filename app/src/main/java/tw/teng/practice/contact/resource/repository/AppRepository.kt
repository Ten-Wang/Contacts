package tw.teng.practice.contact.resource.repository

import android.app.Application
import android.widget.Toast
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

    private fun getAPIContactsFormSP(): APIContacts {
        return Pref.getContacts(_application)
    }

    private fun saveStarredToSP(storedStarred: HashMap<Int, Boolean>) {
        Pref.setStars(_application, storedStarred)
    }

    private fun getStarredFormSP(): HashMap<Int, Boolean> {
        return Pref.getStars(_application)
    }

    fun clickStarred(position: Int) {
        val apiContacts = contactsListLiveData.value
        val contact = apiContacts?.contacts?.get(position)
        val starred = contact?.starred
        contact?.starred = (!starred!!)

        saveStarred(contact)

        if (displayModeLiveData.value == DisplayState.ALL.state) {
            Pref.setContacts(
                _application,
                Gson().toJson(apiContacts)
            )
        } else {
            val local = Pref.getContacts(_application)
            for (local_contact: APIContacts.Contacts in local.contacts!!) {
                if (local_contact.id == contact.id) {
                    local_contact.starred = contact.starred
                    break
                }
            }
            Pref.setContacts(
                _application,
                Gson().toJson(local)
            )
        }

        contactsListLiveData.postValue(apiContacts)
    }

    private fun saveStarred(contact: APIContacts.Contacts) {
        val storedStarred = getStarredFormSP()
        if (storedStarred[contact.id] == true) {
            storedStarred.remove(contact.id)
        } else {
            storedStarred[contact.id] = true
        }
        saveStarredToSP(storedStarred)
    }


    fun selectDisplayState(event: Int) {
        var apiContacts = contactsListLiveData.value
        when (event) {
            STARRED.state -> {
                // all to starred
                val list = getAPIContactsFormSP().contacts
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

            override fun onApiTaskFailure(toString: String) {
                Toast.makeText(_application, toString, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun syncLocalData(responseData: APIContacts) {
        val local = Pref.getContacts(_application)
        Pref.setContacts(
            _application, Gson().toJson(
                local.sync(responseData, Pref.getStars(_application))
            )
        )
        contactsListLiveData.value = local
        contactsListLiveData.postValue(local)
    }
}