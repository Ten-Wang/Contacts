package tw.teng.practice.contact.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import tw.teng.practice.contact.resource.repository.AppRepository

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected var appRepo: AppRepository = AppRepository.getInstance(application)

    fun onNetworkAvailable() {
        appRepo.onNetworkAvailable()
    }
}