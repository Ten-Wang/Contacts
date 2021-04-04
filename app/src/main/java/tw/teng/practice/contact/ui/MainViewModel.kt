package tw.teng.practice.contact.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import tw.teng.practice.contact.resource.network.model.APIContacts
import tw.teng.practice.contact.ui.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    var contactsListLiveData: MutableLiveData<APIContacts> = appRepo.contactsListLiveData
    var selected: MutableLiveData<APIContacts.Contacts> = MutableLiveData()
    var selectDisplayLivedata = appRepo.displayModeLiveData

    fun select(position: Int) {
        selected.value = contactsListLiveData.value?.contacts!![position]
    }

    fun selectDisplayState(event: Int) {
        appRepo.selectDisplayState(event)
    }

    fun clickStarred(position: Int) {
        appRepo.clickStarred(position)
    }
}