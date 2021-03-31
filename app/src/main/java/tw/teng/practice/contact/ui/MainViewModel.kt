package tw.teng.practice.contact.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import tw.teng.practice.contact.resource.network.model.Users
import tw.teng.practice.contact.ui.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    //MainFragment的RecyclerView用的資料
    var listLiveData: Users? = appRepo.usersLiveData.value

    //記綠被點選的Item，在DetailFragment就取selected來使用
    var selected: MutableLiveData<Users.Contacts> = MutableLiveData()

    var openItemEvent: MutableLiveData<Users.Contacts> = MutableLiveData()

    init {
        listLiveData?.contacts?.get(0).also { openItemEvent.value = it }
    }
}